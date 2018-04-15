package com.example.alaa.notesnearby;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test();

    }
    public void test(){
        Intent intent=new Intent(this,Test.class);
        startActivity(intent);
    }
}
