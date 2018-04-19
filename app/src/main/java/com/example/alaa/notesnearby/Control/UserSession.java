package com.example.alaa.notesnearby.Control;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.example.alaa.notesnearby.Model.Server;

public class UserSession extends Service {
    public UserSession() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String act =intent.getAction();
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
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        String lat = intent.getStringExtra("lat");
        String lng = intent.getStringExtra("lng");



    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;

    }
    public void onSignup(boolean value){
        if(value){

        }else{

        }
    }
    public void onLogin(boolean value){

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
                    onLogin(true);
                    break;
                case "login_false":
                    onLogin(false);
                    break;

            }

        }

    }
}
