package com.example.admn.blockthem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Medical_Financial_Help extends AppCompatActivity {

    String[] mobileArray = {"Sir Ratan Tata Trust Bombay House, Homi Mody Street, Mumbai 400 001 Call:  022-66658282",
            "Reliance Foundation (Previously Ambani Public Charitable Trust) 222 Maker Chambers IV, 3rd Floor, Nariman Point, Mumbai 400021 Call: 022-44770000, 022-30325000",
            "Asha Kiran Charitable Trust C/o Radium Keysoft Solutions Ltd, Call: 022-26358290,  101, Raigad Darshan, Opposite Indian oil Colony J.P. Road, Andheri (w) Mumbai 400 053",
            "Aured Charitable Trust 1-B-1 Giriraj, Altamount Road Mumbal 400 026, Call: 022-23821452, 022-24926721",
            "Dhirubhai Ambani Foundation, Reliance Industries Limited Reliance Centre, 19, A; A Waichand Hirachand Marg, Ballard Estate, Mumbal 400 038. Tel : 022-30327000",
            "Helping Hand Charitable Trust 3, Vidarbha Samrat Co-op Hsg. Society 93-c, V.P.Road, Vile Parte (West) Mumbai 400 056 Tel: 022-6147448",
            "Herdillia Charitable Foundation Air India Building, 13th Floor Nariman Point Mumbai 400 031, Call: 022-22024224",
            "Jamnalal Bajaj Foundation Bajaj Bhavan 2nd Floor, Jamnalal Bajaj Marg, 226 Nariman Point, Mumbai 400 021, Call: 022-22023626",
            "Shree Siddhivinayak Temple Trust Prabhadevi, Mumbal – 400 028, Tel. 022-24373626 "};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical__financial__help);


        ListView simpleList = (ListView) findViewById(R.id.list);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_medical__financial__help, R.id.textView, mobileArray);
        simpleList.setAdapter(arrayAdapter);


        simpleList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String no = String.valueOf(parent.getItemAtPosition(position));
                        Toast.makeText(Medical_Financial_Help.this, no, Toast.LENGTH_LONG).show();


                        switch (position){
                            case 0:
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: 022-66658282 " ));
                                startActivity(intent);
                                break;

                            case 1:
                                Intent intent1 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: 022-44770000 " ));
                                startActivity(intent1);
                                break;

                            case 2:
                                Intent intent2 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: 022-26358290 " ));
                                startActivity(intent2);
                                break;

                            case 3:
                                Intent intent3 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: 022-24926721 " ));
                                startActivity(intent3);
                                break;

                            case 4:
                                Intent intent4 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: 022-30327000 " ));
                                startActivity(intent4);
                                break;

                            case 5:
                                Intent intent5 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: 022-6147448 " ));
                                startActivity(intent5);
                                break;

                            case 6:
                                Intent intent6 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: 022-22024224 " ));
                                startActivity(intent6);
                                break;

                            case 7:
                                Intent intent7 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: 022-22023626 " ));
                                startActivity(intent7);
                                break;

                            case 8:
                                Intent intent8 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: 022-24373626 " ));
                                startActivity(intent8);
                                break;


                        }
                    }


                });

    }

}

