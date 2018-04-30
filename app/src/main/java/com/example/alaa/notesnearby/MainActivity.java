package com.example.alaa.notesnearby;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.alaa.notesnearby.View.Login;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test();

    }
    public void test(){
        Intent intent=new Intent(this,Login.class);
        startActivity(intent);
    }
}
