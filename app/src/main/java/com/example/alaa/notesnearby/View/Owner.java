package com.example.alaa.notesnearby.View;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.alaa.notesnearby.Model.Note;
import com.example.alaa.notesnearby.R;

import java.util.ArrayList;


public class Owner extends AppCompatActivity {

    private IntentFilter intentfilter;
    private ReceiverServer receiverserver;
    private ArrayList<Note> owner;
    private ArrayList<Note> explored;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
        intentfilter = new IntentFilter();
        intentfilter.addAction("owner");
        receiverserver = new ReceiverServer();
        registerReceiver(receiverserver,intentfilter);
    }

    public void onPause() {
        super.onPause();
        unregisterReceiver(receiverserver);
    }
    public void onResume() {
        super.onResume();
        registerReceiver(receiverserver,intentfilter);
    }
    public void onOwner(Intent intent){
        owner = Note.getNoteFromJSON(intent.getStringExtra("data"));
        if(owner!=null){

        }

    }
    public void onExplored(Intent intent){
        explored = Note.getNoteFromJSON(intent.getStringExtra("data"));
    }

    private class ReceiverServer extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String act = intent.getAction();
            String success = intent.getStringExtra("success");
            if(act == "ownerexplored" && success=="true"){
                onExplored(intent);
                onOwner(intent);
            }

        }

    }
}
