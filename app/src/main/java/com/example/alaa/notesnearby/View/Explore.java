package com.example.alaa.notesnearby.View;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.alaa.notesnearby.Model.LocalData;
import com.example.alaa.notesnearby.Model.Note;
import com.example.alaa.notesnearby.Model.Server;
import com.example.alaa.notesnearby.R;
import com.example.alaa.notesnearby.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Explore extends AppCompatActivity {

    private IntentFilter intentfilter;

    private ArrayList<Note> owner;
    private ArrayList<Note> explored = new ArrayList<Note>();
    private ListView listview;
    private List<String> listitem;
    private String[] items = new String[]{
            ""
    };
    List<Integer> listid =new ArrayList<Integer>();
    private ArrayAdapter<String> adapter;
    private Server server;
    private static boolean isupdate =false;
    private static boolean isexplore = true;
    static List<Note> oldlist=new ArrayList<Note>();
    Button mynotes, explorednotes;

    Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
        mynotes = (Button)findViewById(R.id.owned);
        explorednotes = (Button)findViewById(R.id.explored);
        listview =(ListView)findViewById(R.id.listexplored);


        listitem = new ArrayList<String>(Arrays.asList(items));
        adapter = new ArrayAdapter<String>(Explore.this,
                android.R.layout.simple_expandable_list_item_1,
                listitem);
        server= new Server(this);

        SharedPreferences share =getSharedPreferences("log",MODE_PRIVATE);
        final String user =share.getString("user",null);

        final String pass = share.getString("pass",null);
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                server.updateOwnerExploredNote(user,pass);
                Log.v("explore","update");
            }
        });
        thread.start();
        Log.v("explore","update OwnerExplore Notes");
        listview.setAdapter(adapter);
        listview.setClickable(true);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =new Intent(Explore.this,NotesViewer.class);
                Note opennote = oldlist.get(position);
                intent.putExtra("title", opennote.getTitle());
                intent.putExtra("content", opennote.getContent());
                intent.putExtra("date", opennote.getDate());
                intent.putExtra("lat", opennote.getLat());
                intent.putExtra("lng", opennote.getLng());
                startActivity(intent);
            }
        });
        mynotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isexplore=false;
                update();
            }
        });
        explorednotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               isexplore=true;
               update();
            }
        });
        if(!isupdate){
            //loop();
            //isupdate=true;
            Log.v("explore","increate");
        }
    }
    public void loop(){
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                update();
                handler.postDelayed(this,5000);
                Log.v("explore","loop");
            }
        };
        final Handler handler1 = new Handler();

        handler.postDelayed(runnable, 5000);

    }
    public void update() {

        ArrayList<Note> list;
        if (isexplore) {

            list = (ArrayList<Note>) Note.explored;
        } else {
            list = (ArrayList<Note>) Note.owner;
        }
        if (!list.equals(oldlist)) {
            adapter.clear();
            listid.clear();
            for (int i = 0; i < list.size(); i++) {
                listitem.add(list.get(i).getTitle());
                listid.add(Integer.parseInt(list.get(i).getNoteId()));

                adapter.notifyDataSetChanged();

            }
            Log.v("explore","oldlist"+list.toString());
            oldlist=list;

        }
    }

    public void onPause() {
        super.onPause();

        isupdate=false;

    }
    public void onResume() {
        super.onResume();



    }




}
