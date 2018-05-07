package com.example.alaa.notesnearby.Control;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import com.example.alaa.notesnearby.MainActivity;
import com.example.alaa.notesnearby.Model.Server;
import com.example.alaa.notesnearby.Model.Tracker;

import org.json.JSONException;
import org.json.JSONObject;

public class UserSession extends Service {
    static String userlog="";
    static String passlog="";
    static boolean islogin=false;
    static boolean isupdating=false;
    ReceiverServer receiverserver = new ReceiverServer();
    IntentFilter intentfilter = new IntentFilter();

    public UserSession() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String act =intent.getAction();
        IntentFilter intentfilter = new IntentFilter();
        intentfilter.addAction("signup_true");
        intentfilter.addAction("signup_false");
        intentfilter.addAction("login_true");
        intentfilter.addAction("login_false");
        intentfilter.addAction("createnote_true");
        intentfilter.addAction("createnote_false");

        registerReceiver(receiverserver,intentfilter);
        switch (act){
            case "login":
                login(intent);
                break;
            case "signup":
                signup(intent);
                break;
            case "createnote":
                createnote(intent);
                break;
        }

        return START_STICKY;
    }
    public void login(Intent intent){
        String user = intent.getStringExtra("user");
        String pass = intent.getStringExtra("pass");
        final String user1 = user;
        final String pass1 = pass;
        final Context context = this;
        Thread thread = new Thread(new Runnable() {
            Server server= new Server(context);
            @Override
            public void run() {

                server.login(user1, pass1);
            }
        });
        thread.start();


    }
    public void signup(Intent intent){
        String user = intent.getStringExtra("user");
        String pass = intent.getStringExtra("pass");
        String phone = intent.getStringExtra("phone");
        final String user1=user,pass1=pass,phone1=phone;
        final Context context = this;
        Thread thread = new Thread(new Runnable() {
            Server server= new Server(context);
            @Override
            public void run() {
                server.signup(user1, pass1, phone1);
            }
        });
        thread.start();

    }
    public void createnote(Intent intent){
        SharedPreferences shared = getSharedPreferences("log",MODE_PRIVATE);

        final String user = shared.getString("user","");
        final String pass = shared.getString("pass","");

        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        String lat = intent.getStringExtra("lat");
        String lng = intent.getStringExtra("lng");
        final String title1=title;
        final String content1 = content;
        final String lat1 = lat;
        final String lng1 = lng;
        final Context context = this;
        Log.v("createnoteusersession:",user +"  "+ pass +"  "+ title + "  " + content +"  "+ lat +"  "+ lng);
        Thread thread = new Thread(new Runnable() {
            Server server= new Server(context);
            @Override
            public void run() {

                server.createnote(user,pass,title1,content1,lat1,lng1);
            }
        });
        thread.start();


    }
    public void isLogin(){

    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;

    }
    public void onSignup(boolean value){

    }
    public void onLogin(boolean value, Intent intent)  {
        if(true){
            String data = intent.getStringExtra("data");
            try {
                JSONObject jsob =new JSONObject(data);
                SharedPreferences shared = getSharedPreferences("log",MODE_PRIVATE);
                SharedPreferences.Editor edit =shared.edit();
                edit.putString("user",jsob.getString("user"));
                edit.putString("pass",jsob.getString("pass"));
                edit.apply();
                Log.v("onlogin",jsob.getString("user")+jsob.getString("user"));
                startUpdate();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{

        }
    }
    public void onCreateNote(boolean value){

    }
    public void startUpdate(){
        if(!isupdating){
            Log.v("log","work from usersession");
            Intent intent = new Intent(UserSession.this,Tracker.class);
            startService(intent);
            isupdating =true;

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiverserver);
    }

    private class ReceiverServer extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String act = intent.getAction();
            switch(act) {
                case "signup_true":
                    onSignup(true);
                    break;
                case "signup_false":
                    onSignup(false);
                    break;
                case "login_true":
                    onLogin(true,intent);
                    break;
                case "login_false":
                    onLogin(false,intent);
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
