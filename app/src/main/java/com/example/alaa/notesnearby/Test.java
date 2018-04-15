package com.example.alaa.notesnearby;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Test extends AppCompatActivity {
    EditText user,pass,username,password,phone,newuser,newpass,title,content,lat,lng;
    Button login,signup,createnote;
    TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        user = findViewById(R.id.user);
        pass = findViewById(R.id.pass);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        phone = findViewById(R.id.phone);
        newuser = findViewById(R.id.newuser);
        newpass = findViewById(R.id.newpass);
        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        lat = findViewById(R.id.lat);
        lng = findViewById(R.id.lng);

        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        createnote = findViewById(R.id.createnote);

        result = findViewById(R.id.username);

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
    }
    public void login(){

    }
    public void signup(){

    }
}
