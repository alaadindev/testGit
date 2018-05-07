package com.example.alaa.notesnearby.Model;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Server {
    Context context;
    public Server(Context context){
        this.context = context;
    }
    public void login(String user, String pass){
        try {
            URL url = new URL("http://10.0.2.2:8888/testGit/php/webproject/project/control/getuser.php");
            HttpURLConnection urlcon = (HttpURLConnection)url.openConnection();
            urlcon.setDoOutput(true);
            urlcon.setRequestMethod("POST");
            urlcon.setDoInput(true);
            urlcon.setConnectTimeout(15000);
            String req = "username=" + user + "&password=" + pass;
            urlcon.setFixedLengthStreamingMode(req.getBytes().length);
            PrintWriter out = new PrintWriter(urlcon.getOutputStream());
            out.print(req);
            out.flush();
            out.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlcon.getInputStream()));
            String data = in.readLine();
            in.close();
            Log.v("server","Login: "+ data);
            updateOwnerExploredNote(user,pass);
            sendResult(data,"login");

        }

        catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
                    sendResult(data,"signup");
                    Log.v("in",data);

                }

                catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


    }
    public void createnote(String user, String pass, String title, String content, String lat, String lng){
        try {
            URL url = new URL("http://10.0.2.2:8888/testGit/php/webproject/project/control/createnote.php");
            HttpURLConnection urlcon = (HttpURLConnection)url.openConnection();
            urlcon.setDoOutput(true);
            urlcon.setRequestMethod("POST");
            urlcon.setDoInput(true);
            urlcon.setConnectTimeout(15000);
            String req = "username="+user+"&password="+pass+"&title="+title+"&contents="+content+"&lat="+lat+"&lng="+lng;
            urlcon.setFixedLengthStreamingMode(req.getBytes().length);
            PrintWriter out = new PrintWriter(urlcon.getOutputStream());
            out.print(req);
            out.flush();
            out.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlcon.getInputStream()));
            String data = in.readLine();
            in.close();
            Log.v("createnoteserver:","username="+user+"&password="+pass+"&title="+title+"&contents="+content+"&lat="+lat+"&lng="+lng);
            sendResult(data,"createnote");
            Log.v("serverin",data);
            updateOwnerExploredNote(user,pass);

        }

        catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void updateNote(String user, String pass, String lat, String lng){
        try{
            URL url = new URL("http://10.0.2.2:8888/testGit/php/webproject/project/control/getnotes.php");
            HttpURLConnection urlcon = (HttpURLConnection)url.openConnection();
            urlcon.setDoInput(true);
            urlcon.setRequestMethod("POST");
            urlcon.setConnectTimeout(15000);
            String req = "username="+user+"&password="+pass+"&lat="+lat+"&lng="+lng;
            urlcon.setFixedLengthStreamingMode(req.getBytes().length);
            PrintWriter out = new PrintWriter(urlcon.getOutputStream());
            out.print(req);
            out.flush();
            out.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlcon.getInputStream()));
            String data = in.readLine();
            in.close();

            saveUpdate(data);


        }catch (Exception e){

        }
    }
    public void addExploreNote(Note note,String user,String pass){
        try{
            URL url = new URL("http://10.0.2.2:8888/testGit/php/webproject/project/control/explore.php");
            HttpURLConnection urlcon = (HttpURLConnection)url.openConnection();
            urlcon.setDoInput(true);
            urlcon.setRequestMethod("POST");
            urlcon.setConnectTimeout(15000);
            String req = "noteID="+note.getNoteId()+"&username="+user;
            urlcon.setFixedLengthStreamingMode(req.getBytes().length);
            PrintWriter out = new PrintWriter(urlcon.getOutputStream());
            out.print(req);
            out.flush();
            out.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlcon.getInputStream()));
            String data = in.readLine();
            in.close();
            Log.v("server","addexplorenote: "+data);
            updateOwnerExploredNote(user,pass);
        }catch (Exception e){

        }
    }


    public void updateOwnerExploredNote(String user, String pass){
        try{
            Log.v("server","updateOwnerExploreNotes");
            URL url = new URL("http://10.0.2.2:8888/testGit/php/webproject/project/control/getusernotes.php");
            HttpURLConnection urlcon = (HttpURLConnection)url.openConnection();
            urlcon.setDoInput(true);
            urlcon.setRequestMethod("POST");
            urlcon.setConnectTimeout(15000);
            String req = "username="+user+"&password="+pass;
            urlcon.setFixedLengthStreamingMode(req.getBytes().length);
            PrintWriter out = new PrintWriter(urlcon.getOutputStream());
            out.print(req);
            out.flush();
            out.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlcon.getInputStream()));
            String data = in.readLine();
            in.close();

            LocalData1.storeOwnerExploredNotes(data,context);

        }catch (Exception e){

        }
    }
    public void saveExploredupdate(Note note){

    }
    public void saveUserUpdate(String data){
        ArrayList<Note> notes =Note.getNoteFromJSON(data);
        Log.v("log",notes.get(0).getLng()+"");
        LocalData1.storeOwnerNotes(notes,context);
        Log.v("log",data);
    }

    public void saveUpdate(String data){
        ArrayList<Note> notes =Note.getNoteFromJSON(data);
        Log.v("log",notes.get(0).getLng()+"");
        LocalData1.storeNotes(notes,context);
        Log.v("log23",data);
    }

    public void sendResult(String data,String method) throws JSONException {
        Intent intent = new Intent();
        intent.putExtra("data",data);
        JSONObject jsdata = new JSONObject(data);
        String success = jsdata.getString("success");
        String owner = jsdata.getString("hasowned");
        String explored = jsdata.getString("hasexplored");
        Log.v("success","" + method);
        switch (method){
            case "signup":
                if(success.equals("true")){
                intent.setAction("signup_true");
                context.sendBroadcast(intent);
            }else{
                intent.setAction("signup_false");
                context.sendBroadcast(intent);
            }
                break;
            case "login":
                if(success.equals("true")){
                    intent.setAction("login_true");
                    context.sendBroadcast(intent);
                }else{
                    intent.setAction("login_false");
                    context.sendBroadcast(intent);
                }
                break;
            case "createnote":
                if(success.equals("true")){
                    intent.setAction("createnote_true");
                    context.sendBroadcast(intent);
                }else{
                    intent.setAction("createnote_false");
                    context.sendBroadcast(intent);

                }
                break;
            case "update":
                intent.setAction("update");
                context.sendBroadcast(intent);
                Log.v("Log","Server update sendBroadcast");
                break;

            case "ownerexplored":
                Log.v("server","ownerexplored");
                if (success.equals("true")) {

                    //intent.setAction("ownerexplored");
                    //LocalData.storeOwnerExploredNotes(data,context);
                    //ArrayList<Note> notes1=LocalData.getExploredNotes(context);
                    //Note.explored = notes1;
                    //context.sendBroadcast(intent);
                }
                break;

        }


    }

}
