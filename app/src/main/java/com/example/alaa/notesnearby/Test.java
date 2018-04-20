package com.example.alaa.notesnearby;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.alaa.notesnearby.Control.UserSession;

public class Test extends AppCompatActivity {
    EditText userlogin,passlogin;
    EditText usersignup,passsignup,phonesignup;
    EditText ownername,ownerpass,notetitle,notecontent,notelat,notelng;
    Button login,signup,createnote;
    TextView result;
    ReceiverServer receiverserver;
    IntentFilter intentfilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        userlogin = findViewById(R.id.userlogin);
        passlogin = findViewById(R.id.passlogin);
        usersignup = findViewById(R.id.usersignup);
        passsignup = findViewById(R.id.passsignup);
        phonesignup = findViewById(R.id.phonesignup);
        ownername = findViewById(R.id.ownername);
        ownerpass = findViewById(R.id.ownerpass);
        notetitle = findViewById(R.id.notetitle);
        notecontent = findViewById(R.id.notecontent);
        notelat = findViewById(R.id.notelat);
        notelng = findViewById(R.id.notelng);

        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        createnote = findViewById(R.id.createnote);

        result = findViewById(R.id.result);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
        createnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createnote();
            }
        });
        intentfilter = new IntentFilter();
        intentfilter.addAction("signup_true");
        intentfilter.addAction("signup_false");
        intentfilter.addAction("login_true");
        intentfilter.addAction("login_false");
        intentfilter.addAction("createnote_true");
        intentfilter.addAction("createnote_false");
        receiverserver = new ReceiverServer();
        registerReceiver(receiverserver,intentfilter);
    }
    public void login(){
        String user = userlogin.getText().toString();
        String pass = passlogin.getText().toString();
        Intent intent = new Intent(this,UserSession.class);
        intent.setAction("login");
        intent.putExtra("user",user);
        intent.putExtra("pass",pass);
        startService(intent);

    }
    public void signup(){
        Intent intent = new Intent(this,UserSession.class);
        intent.setAction("signup");
        String user = usersignup.getText().toString();
        String pass = passsignup.getText().toString();
        String phone = phonesignup.getText().toString();
        intent.putExtra("user",user);
        intent.putExtra("pass",pass);
        intent.putExtra("phone",phone);
        startService(intent);
    }
    public void onPause() {
        super.onPause();
        unregisterReceiver(receiverserver);
    }
    public void onResume() {
        super.onResume();
        registerReceiver(receiverserver,intentfilter);
    }
    public void createnote(){
        Intent intent = new Intent(this, UserSession.class);
        intent.setAction("createnote");
        String user = ownername.getText().toString();
        String pass = ownerpass.getText().toString();
        String title = notetitle.getText().toString();
        String content = notecontent.getText().toString();
        String lat = notelat.getText().toString();
        String lng = notelng.getText().toString();
        intent.putExtra("user",user);
        intent.putExtra("pass",pass);
        intent.putExtra("title",title);
        intent.putExtra("content",content);
        intent.putExtra("lat", lat);
        intent.putExtra("lng",lng);
        startService(intent);

    }

    public void onSignup(boolean value){
        if(value){
            result.setText("true");
        }else{
            result.setText("false");
        }
    }
    public void onCreateNote(boolean value){
        if(value){
            result.setText("true");
        }else{
            result.setText("false");
        }
    }
    public void onLogin(boolean value){
        if(value){
            result.setText("true");
        }else{
            result.setText("false");
        }
    }
    private class ReceiverServer extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String act = intent.getAction();
            System.out.print("onreceive"+act);
            switch(act) {
                case "signup_true":
                    onSignup(true);
                    break;
                case "signup_false":
                    onSignup(false);
                    break;
                case "login_true":
                    onLogin(true);
                    break;
                case "login_false":
                    onLogin(false);
                    break;
                case "createnote_true":
                    onCreateNote(true);
                    break;
                case "createnote_false":
                    onCreateNote(false);

                    break;



            }

        }

    }

}
