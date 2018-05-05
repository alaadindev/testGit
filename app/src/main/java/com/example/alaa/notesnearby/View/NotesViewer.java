package com.example.alaa.notesnearby.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.alaa.notesnearby.R;

public class NotesViewer extends AppCompatActivity {
    TextView titleview,dateview,contentview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_viewer);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String date = intent.getStringExtra("date");
        String content = intent.getStringExtra("content");
        titleview = (TextView)findViewById(R.id.title);
        dateview = (TextView)findViewById(R.id.date);
        contentview = (TextView)findViewById(R.id.content);
        titleview.setText(getTitle(title));
        dateview.setText(getDate(date));
        contentview.setText(getContent(content));
    }
    public static String getTitle(String msg){
        return "Title: "+msg;
    }
    public static String getDate(String msg){
        return "Date: "+msg;
    }
    public static String getContent(String msg){
        return msg;
    }
}
