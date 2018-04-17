package com.example.alaa.notesnearby.Control;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

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
        if (Server.login(user,pass)){

        }
    }
    public void signup(Intent intent){
        String user = intent.getStringExtra("user");
        String pass = intent.getStringExtra("pass");
        String phone = intent.getStringExtra("title");


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
}
