package com.example.alaa.notesnearby.Model;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.alaa.notesnearby.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class LocalData {

    static Context context;
    static SQLiteDatabase db;


    private static List<Note> notes = new ArrayList<Note>();

    public LocalData(Context context1){
        db = context.openOrCreateDatabase("local.db", Context.MODE_PRIVATE,null);
        }
    public static void addUser(User user){
        String name = user.getName();
        String password = user.getPassword();
        String phone = user.getPhone();
        setup();
        String sql2 = "INSERT INTO user (username, password, phone) VALUES ('"
                +name+"','"+password+"','"+phone+"')";
        db.execSQL(sql2);

    }
    public  static void openData(Context context){
        db = context.openOrCreateDatabase("local.db", Context.MODE_PRIVATE,null);
    }
    public static void storeNotes(ArrayList<Note> note,Context context){
        openData(context);
        notes=note;
        Log.v("local","storenote 1");
        String sql="drop table if exists notes";
        Log.v("local","storenote 2");
        db.execSQL(sql);
        Log.v("local","storenote 3");
        setup();
        Log.v("local","storenote 4");
        for(int i=0;i<notes.size();i++) {
            Log.v("local","storenote 5");
            String sql2 = "INSERT INTO notes(noteID, title, contents, date,lat, lng)" +
                    " VALUES('" + notes.get(i).getNoteId() + "','" +
                    notes.get(i).getTitle() + "','" + notes.get(i).getContent() + "','" +
                    notes.get(i).getDate() + "','" + notes.get(i).getLat() + "','" +
                    notes.get(i).getLng() + "')";
            Log.v("local","storenote 6");
            Log.v("local",notes.get(i).getLat()+"");
            db.execSQL(sql2);
            Log.v("local","storenote 7");

        }
    }
    public static void explored(String noteid){

    }

    public static User getUser(){
        return null;
    }

    public static ArrayList<Note> getNotes(Context context1){
        openData(context1);
        setup();
        ArrayList<Note> notes = new ArrayList<Note>();
        String sql ="SELECT noteID, title, contents, date, lat, lng FROM notes";


        Cursor cursor =db.rawQuery(sql,null);

        while(cursor.moveToNext()){
            Log.v("lng",cursor.getString(cursor.getColumnIndex("lng")));
            notes.add(Note.getNoteFromSQL(
                    cursor.getString(cursor.getColumnIndex("noteID")),
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getString(cursor.getColumnIndex("contents")),
                    cursor.getString(cursor.getColumnIndex("date")),
                    Double.parseDouble(cursor.getString(cursor.getColumnIndex("lat"))),
                    Double.parseDouble(cursor.getString(cursor.getColumnIndex("lng")))));

        }

        return notes;
    }

public static void setup(){

    String sql1 = "CREATE TABLE if not exists notes(noteID Integer PRIMARY KEY," +
            "title String, contents String, date String, lat REAL, lng REAL)";
    db.execSQL(sql1);
    String sql2 = "CREATE TABLE if not exists user(username String PRIMARY KEY"+
            ", password String , phone INTEGER)";
    db.execSQL(sql2);
}

}


