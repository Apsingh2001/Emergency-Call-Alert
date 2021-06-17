package com.example.admn.blockthem;

import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Emergency_Call_Alert_Activity extends AppCompatActivity  {

    DataBaseHelper helper= new DataBaseHelper(this);
    static String cnt="";
    String num = "+91",label="+91";
    EditText number,countbyUser,delNumber;
    Button addbtn,btnDelete,moverHome,moverMFH;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency__call__alert_);

        number = findViewById(R.id.PhoneNumber);
        addbtn = findViewById(R.id.btnAdd);
        countbyUser = findViewById(R.id.count);
        spinner = findViewById(R.id.spinner);
        btnDelete =  findViewById(R.id.btn_delete);
        delNumber = findViewById(R.id.delPhoneNumber);
        moverHome = findViewById(R.id.moverHome);
        moverMFH = findViewById(R.id.moverMFH);

        moverHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Emergency_Call_Alert_Activity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        moverMFH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Emergency_Call_Alert_Activity.this,Medical_Financial_Help.class);
                startActivity(intent);
            }
        });

        // Loading spinner data from database
        loadSpinnerData();



        //adds Number to database       //num var acts as an parameter to check for validatation work
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                num += number.getText().toString();
                cnt=countbyUser.getText().toString();

                if(cnt.equalsIgnoreCase("0"))
                {
                 countbyUser.setText("1");
                    Toast.makeText(Emergency_Call_Alert_Activity.this,"Counter cannot be set to 0 minimum value can be 1",Toast.LENGTH_LONG).show();

                }
                else
                {
                    Toast.makeText(Emergency_Call_Alert_Activity.this, "Counter set to  " + cnt , Toast.LENGTH_SHORT).show();

                }


                if (validatePhoneNumber(num)) {
                    if ((helper.validateCheck(num))) {
                        AddContact();
                        Toast.makeText(Emergency_Call_Alert_Activity.this, "Number " + num +" added", Toast.LENGTH_SHORT).show();

                        // making input filed text to blank
                        number.setText("");
                        num="+91";
                        Log.d("numnum",""+num+number);

                        // loading spinner with newly added data
                        loadSpinnerData();
                    } else {
                        Toast.makeText(Emergency_Call_Alert_Activity.this, "Contact Number already exists", Toast.LENGTH_SHORT).show();
                        number.setText("");
                        num="+91";
                    }
                } else {
                    Toast.makeText(Emergency_Call_Alert_Activity.this, "Invalid Phone Number!Please Enter again", Toast.LENGTH_SHORT).show();
                    number.setText("");
                    num="+91";
                }
            }
        });



        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                label+= delNumber.getText().toString();

                Log.d("deldil",""+label+delNumber);
                if (validatePhoneNumber(label)) {
                    Log.d("tagger","inside if1");

                    if (!(helper.validateCheck(label))) {
                        Log.d("tagger","inside if2");
                        helper.deleteNumber(label);
                        Toast.makeText(Emergency_Call_Alert_Activity.this, "number deleted"+label, Toast.LENGTH_SHORT).show();

                        label="+91";
                        delNumber.setText("");
                        loadSpinnerData();

                    } else {
                        Toast.makeText(Emergency_Call_Alert_Activity.this, "contact no not exists,Please enter again!!", Toast.LENGTH_SHORT).show();
                        delNumber.setText("");
                        label="+91";
                    }
                } else {
                    Toast.makeText(Emergency_Call_Alert_Activity.this, "Invalid Number!Please Enter again", Toast.LENGTH_SHORT).show();
                    delNumber.setText("");
                    label="+91";
                }
            }
        });



    }//end of onCreate method

    //Get an alarm sound. Try for an alarm. If none set, try notification,
    //Otherwise, ringtone.
    static public Uri getAlarmUri() {
        Uri alert = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_ALARM);
        return alert;
//end of alarm
    }

    //validation function start
    private static final String PhoneNumber_PATTERN = "^(?:(?:\\+|0{0,2})91(\\s*[\\ -]\\s*)?|[0]?)?[789]\\d{9}|(\\d[ -]?){10}\\d$";
    private Pattern pattern = Pattern.compile(PhoneNumber_PATTERN);
    private Matcher matcher;

    public boolean validatePhoneNumber(String number)
    {
        matcher=pattern.matcher(number);
        return matcher.matches();

    }
    //validation function ends



    //getter-add number & count to contact & from there to insert contact ie to the table columns number & count
    public void AddContact() {
        Contact c = new Contact();
        c.setNumber(num);
        c.setCounter("0");
        helper.insertContact(c);
    }
    //end of AddContact

    private void loadSpinnerData() {

        List<String> labels = helper.returnNumberFromDatabase();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labels);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }
}
