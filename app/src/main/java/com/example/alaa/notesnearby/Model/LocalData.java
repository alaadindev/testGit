package com.example.alaa.notesnearby.Model;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class LocalData {
    Context context;
    static SQLiteDatabase db;
    private static List<Note> notes = new ArrayList<Note>();
    public LocalData(Context context){
        db = this.context.openOrCreateDatabase("local.db", Context.MODE_PRIVATE,null);
    }
    public static void addUser(User user){
        String name = user.getName();
        String password = user.getPassword();
        String phone = user.getPhone();
        String sql1 = "CREATE TABLE IF NOT EXISTS user(username String PRIMARY KEY"+
                ", password String , phone INTEGER)";
        db.execSQL(sql1);
        String sql2 = "INSERT INTO user (username, password, phone) VALUES ('"
                +name+"','"+password+"','"+phone+"')";
        db.execSQL(sql2);

    }
    public static void storeNotes(String strnotes){
        notes = getNoteJSON(strnotes);
        String sql1 = "CREATE TABLE IF NOT EXISTS notes(noteID Integer PRIMARY KEY," +
                "title String, content String,lat REAL,lng REAL, date String)";
        db.execSQL(sql1);
        for(int i=0;i<notes.size();i++) {
            String sql2 = "INSERT INTO notes(noteID, title, content, lat, lng, date)" +
                    " VALUES('" + notes.get(i).getNoteId() + "','" +
                    notes.get(i).getTitle() + "','" + notes.get(i).getContent() + "','" +
                    notes.get(i).getLat() + "','" + notes.get(i).getLng() + "','" +
                    notes.get(i).getDate() + "')";

            db.execSQL(sql2);
        }
    }
    public static void explored(String note){

    }
    public static void updateLocation(double lat, double lng){

    }
    public static User getUser(){
        return null;
    }
    public static Note getNotes(){
        return null;
    }
    public static List<Note> getNoteJSON(String note){
        return null;
    }


}


