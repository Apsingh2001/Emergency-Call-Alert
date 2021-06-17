package com.example.admn.blockthem;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.telephony.TelephonyManager;//importANT
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Inet4Address;


public class IncomingCallReceiverT extends BroadcastReceiver  {

    static String callerPhoneNumber ="";

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i("Broadcast Listened", "Service tried to stop");
        Toast.makeText(context, "Service restarted", Toast.LENGTH_SHORT).show();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(new Intent(context, Myservice.class));
        } else {
            context.startService(new Intent(context, Myservice.class));
        }

            DataBaseHelper helper = new DataBaseHelper(context);
        try {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
            Log.d("callreceiver","number fetched is "+number);
            if(state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                Bundle bundle = intent.getExtras();
                callerPhoneNumber = bundle.getString("incoming_number");
                helper.updateCounter(callerPhoneNumber);
                int cntUpdate= Integer.parseInt(Emergency_Call_Alert_Activity.cnt)+1;
                Log.d("callreceiver","compare count values"+(Integer.parseInt(helper.returnCount(callerPhoneNumber))>cntUpdate));
                Log.d("callreceiver","return counter value"+helper.returnCount(callerPhoneNumber));
                Log.d("callreceiver","return cntupdate value"+cntUpdate);
                if (helper.returnCount(callerPhoneNumber).equalsIgnoreCase(Emergency_Call_Alert_Activity.cnt)|| helper.returnCount(callerPhoneNumber).equalsIgnoreCase(String.valueOf(cntUpdate))) {
                 playSound(context,Emergency_Call_Alert_Activity.getAlarmUri());
                 Log.d("callreceiver","compare count values inside if"+(Integer.parseInt(helper.returnCount(callerPhoneNumber))>cntUpdate));
                 }
                if(Integer.parseInt(helper.returnCount(callerPhoneNumber))>cntUpdate)
                {
                    Log.d("callreceiver","inside if used to reset count value to 0");
                    helper.resetCount(callerPhoneNumber);
                }
            }
            if(state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_OFFHOOK)){
                Toast.makeText(context, "Answered " + number, Toast.LENGTH_SHORT).show();
            }
            if(state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_IDLE)){
                Toast.makeText(context,"it was a missed call from:"+callerPhoneNumber,Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void playSound(Context context, Uri alert) {
        //media player help to ring our alarm using alarm volume range only instead of default rinbgtone volume range
        MediaPlayer  mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setDataSource(context, alert);
            final AudioManager audioManager = (AudioManager) context
                    .getSystemService(Context.AUDIO_SERVICE);
            if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                mMediaPlayer.prepare();
                mMediaPlayer.start();
            }
        } catch (IOException e) {
            System.out.println("OOPS");
        }
    }
}