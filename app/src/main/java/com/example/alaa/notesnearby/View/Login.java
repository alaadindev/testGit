package com.example.alaa.notesnearby.View;

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
import com.example.alaa.notesnearby.R;

public class Login extends AppCompatActivity {
    EditText userlogin,passlogin,usersignup,passsignup,phonesignup;
    Button login,signup;
    TextView loginstatus,signupstatus;
    private IntentFilter intentfilter;
    private ReceiverServer receiverserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userlogin = findViewById(R.id.userlogin);
        passlogin = findViewById(R.id.passlogin);
        usersignup = findViewById(R.id.usersignup);
        passsignup = findViewById(R.id.passsignup);
        phonesignup = findViewById(R.id.phonesignup);

        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);

        loginstatus = findViewById(R.id.loginstatus);
        signupstatus = findViewById(R.id.signupstatus);

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

    intentfilter = new IntentFilter();
        intentfilter.addAction("signup_true");
        intentfilter.addAction("signup_false");
        intentfilter.addAction("login_true");
        intentfilter.addAction("login_false");
        intentfilter.addAction("update");
    receiverserver = new ReceiverServer();
    registerReceiver(receiverserver,intentfilter);

        //LocalData.clean(this);
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

    public void onSignup(boolean value){
        if(value){
            signupstatus.setText("true");
        }else{
            signupstatus.setText("false");
        }
    }

    public void onLogin(boolean value){
        if(value){
            Intent intent = new Intent(Login.this,MapsView.class);
            startActivity(intent);
        }else{
            loginstatus.setText("false");
        }
    }
    public void update(Intent intent){
        String notes = intent.getStringExtra("data");
        //result.setText(notes);
    }
private class ReceiverServer extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String act = intent.getAction();
        Log.v("login",act);
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
            case "update":
                Log.v("log","test update receiver reached");
                update(intent);
                break;

        }

    }

}
}
