package com.example.alaa.notesnearby;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.alaa.notesnearby.Model.LocalData1;
import com.example.alaa.notesnearby.View.Login;

public class MainActivity extends AppCompatActivity {
    static boolean isset=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setup();
        test();

    }
    public void test(){
        Intent intent=new Intent(this,Login.class);
        startActivity(intent);
    }
    public void setup(){
        if(!isset){
            LocalData1 localData = new LocalData1(this);
            //LocalData.clean(this);
            isset=true;
        }
    }
}
