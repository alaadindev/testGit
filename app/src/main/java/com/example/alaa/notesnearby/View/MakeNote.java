package com.example.alaa.notesnearby.View;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.alaa.notesnearby.Control.UserSession;
import com.example.alaa.notesnearby.R;

public class MakeNote extends AppCompatActivity {
    EditText notetitle, notecontents;
    Button makenote;
    TextView result;
    private IntentFilter intentfilter;
    private ReceiverServer receiverserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_note);
        notetitle = findViewById(R.id.title);
        notecontents = findViewById(R.id.contents);

        makenote = findViewById(R.id.makenote);

        result = findViewById(R.id.result);

        makenote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createnote();
            }
        });
        intentfilter = new IntentFilter();
        intentfilter.addAction("createnote_true");
        intentfilter.addAction("createnote_false");
        receiverserver = new ReceiverServer();
        registerReceiver(receiverserver,intentfilter);
    }
    public void createnote(){
        Intent intent = new Intent(this, UserSession.class);
        intent.setAction("createnote");
        SharedPreferences share = getSharedPreferences("log",MODE_PRIVATE);
        String user = share.getString("user",null);
        String pass = share.getString("pass",null);
        String title = notetitle.getText().toString();
        String content = notecontents.getText().toString();
        String lat = share.getString("lat",null);
        String lng = share.getString("lng",null);
        intent.putExtra("user",user);
        intent.putExtra("pass",pass);
        intent.putExtra("title",title);
        intent.putExtra("content",content);
        intent.putExtra("lat", lat);
        intent.putExtra("lng",lng);
        startService(intent);

    }
    public void onCreateNote(boolean value){
        if(value){
            Intent intent = new Intent(MakeNote.this, MapsView.class);
            startActivity(intent);
        }else{
            result.setText("false");
        }
    }
    public void onPause() {
        super.onPause();
        unregisterReceiver(receiverserver);
    }
    public void onResume() {
        super.onResume();
        registerReceiver(receiverserver,intentfilter);
    }
    private class ReceiverServer extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String act = intent.getAction();
            System.out.print("onreceive"+act);
            switch(act) {
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
