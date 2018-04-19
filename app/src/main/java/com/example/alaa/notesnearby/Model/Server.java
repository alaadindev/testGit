package com.example.alaa.notesnearby.Model;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.alaa.notesnearby.Control.UserSession;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Server {
    Context context;
    public Server(Context context){
        this.context = context;
    }
    public void login(String user, String pass){

    }
    public void signup(String user, String pass, String phone){
        Log.v("out","user:"+user+" pass:"+pass + " phone:"+phone);
        try {
            URL url = new URL("http://10.0.2.2:8888/testGit/php/webproject/project/control/createuser.php");
            HttpURLConnection urlcon = (HttpURLConnection)url.openConnection();
                    urlcon.setDoOutput(true);
                    urlcon.setRequestMethod("POST");
                    urlcon.setDoInput(true);
                    urlcon.setConnectTimeout(15000);
                    String req = "username=" + user + "&password=" + pass + "&phone=" + phone;
                    urlcon.setFixedLengthStreamingMode(req.getBytes().length);
                    PrintWriter out = new PrintWriter(urlcon.getOutputStream());
                    out.print(req);
                    out.flush();
                    out.close();
                    BufferedReader in = new BufferedReader(new InputStreamReader(urlcon.getInputStream()));
                    String data = in.readLine();
                    in.close();
                    sendResult(data);
                    Log.v("in",data);

                }

                catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


    }
    public void createnote(String title, String content, String lat, String lng){
        
    }

    public void sendResult(String data) throws JSONException {
        Intent intent = new Intent();
        intent.putExtra("data",data);
        JSONObject jsdata = new JSONObject(data);
        String success = jsdata.getString("success");
        Log.v("success","" + success);
        if(success.equals("true")){
            intent.setAction("signup_true");
            context.sendBroadcast(intent);
        }else{
            intent.setAction("signup_false");
            context.sendBroadcast(intent);
        }

    }

}
