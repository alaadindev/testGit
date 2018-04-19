package com.example.alaa.notesnearby.Model;

import android.util.Log;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Server {
    public static boolean login(String user, String pass){
        return true;
    }
    public boolean signup(String user, String pass, String phone){
        final String user1=user, pass1=pass, phone1=phone;
        Log.v("out","user:"+user+" pass:"+pass + " phone:"+phone);
        Thread thread =new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    URL url = new URL("http://10.0.2.2:8888/testGit/php/webproject/project/control/createuser.php");
                    HttpURLConnection urlcon = (HttpURLConnection)url.openConnection();
                    urlcon.setDoOutput(true);
                    urlcon.setRequestMethod("POST");
                    String req = "username=" + user1 + "&password=" + pass1 + "&phone=" + phone1;

                    urlcon.setFixedLengthStreamingMode(req.getBytes().length);
                    PrintWriter out = new PrintWriter(urlcon.getOutputStream());
                    out.print(req);
                    out.flush();
                    out.close();
                    Log.v("out",req);
                    System.out.print(req);
                }

                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        return true;
    }
    public static boolean createnote(String title, String content, String lat, String lng){
        return true;
    }


}
