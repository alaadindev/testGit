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
    private static List<Note> owner = new ArrayList<>();
    private static List<Note> explored = new ArrayList<>();
    private static User user = null;
    static int num=0;

    public LocalData(Context context1){
        db = context.openOrCreateDatabase("local.db", Context.MODE_PRIVATE,null);
    }

    public  static void openData(Context context){
        if (!db.isOpen())
        db = context.openOrCreateDatabase("local.db", Context.MODE_PRIVATE,null);
    }
    public static void storeNotes(ArrayList<Note> note,Context context){

        try {
            openData(context);
            notes = note;
            String sql = "drop table if exists notes";
            db.execSQL(sql);
            setup();
            for (int i = 0; i < notes.size(); i++) {
                String sql2 = "INSERT INTO notes(noteID, title, contents, date,lat, lng,owner)" +
                        " VALUES('" + notes.get(i).getNoteId() + "','" +
                        notes.get(i).getTitle() + "','" + notes.get(i).getContent() + "','" +
                        notes.get(i).getDate() + "','" + notes.get(i).getLat() + "','" +
                        notes.get(i).getLng() + "','" + notes.get(i).getOwner() + "')";
                db.execSQL(sql2);


            }

            db.close();
            Log.v("local", "storenotes");
        }catch (Exception e){

            db.close();
            Log.v("local", "storenotes");
        }
    }


    public static ArrayList<Note> getNotes(Context context1){
        Cursor cursor=null;
        try {
            openData(context1);
            setup();
            ArrayList<Note> notes = new ArrayList<Note>();
            String sql = "SELECT noteID, title, contents, date, lat, lng, owner FROM notes";


            cursor = db.rawQuery(sql, null);

            while (cursor.moveToNext()) {
                notes.add(Note.getNoteFromSQL(
                        cursor.getString(cursor.getColumnIndex("noteID")),
                        cursor.getString(cursor.getColumnIndex("title")),
                        cursor.getString(cursor.getColumnIndex("contents")),
                        cursor.getString(cursor.getColumnIndex("date")),
                        Double.parseDouble(cursor.getString(cursor.getColumnIndex("lat"))),
                        Double.parseDouble(cursor.getString(cursor.getColumnIndex("lng"))),
                        cursor.getString(cursor.getColumnIndex("owner"))));
            }
            Log.v("local", "get notes");
            cursor.close();
            db.close();
            return notes;
        }catch (Exception e){
            Log.v("local", "getnotes"+e);
            //db.close();
            return new ArrayList<Note>();
        }

    }

    public static ArrayList<Note> getOwnerNotes(Context context1){
        openData(context1);
        setup();
        ArrayList<Note> notes = new ArrayList<Note>();

        String sql ="SELECT noteID, title, contents, date, lat, lng, owner FROM owner";


        Cursor cursor =db.rawQuery(sql,null);

        while(cursor.moveToNext()){
            notes.add(Note.getNoteFromSQL(
                    cursor.getString(cursor.getColumnIndex("noteID")),
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getString(cursor.getColumnIndex("contents")),
                    cursor.getString(cursor.getColumnIndex("date")),
                    Double.parseDouble(cursor.getString(cursor.getColumnIndex("lat"))),
                    Double.parseDouble(cursor.getString(cursor.getColumnIndex("lng"))),
                    cursor.getString(cursor.getColumnIndex("owner"))));

        }

        cursor.close();
        db.close();
        return notes;
    }
    public static ArrayList<Note> getExploredNotes(Context context1){

        Cursor cursor=null;
        try {
            openData(context1);

            setup();
            ArrayList<Note> notes = new ArrayList<Note>();
            String sql = "SELECT noteID, title, contents, date, lat, lng, owner FROM explored";


            cursor = db.rawQuery(sql, null);

            while (cursor.moveToNext()) {
                notes.add(Note.getNoteFromSQL(
                        cursor.getString(cursor.getColumnIndex("noteID")),
                        cursor.getString(cursor.getColumnIndex("title")),
                        cursor.getString(cursor.getColumnIndex("contents")),
                        cursor.getString(cursor.getColumnIndex("date")),
                        Double.parseDouble(cursor.getString(cursor.getColumnIndex("lat"))),
                        Double.parseDouble(cursor.getString(cursor.getColumnIndex("lng"))),
                        cursor.getString(cursor.getColumnIndex("owner"))));

            }

            cursor.close();
            db.close();
            return notes;
        }catch (Exception e) {
            cursor.close();
            db.close();
            Log.v("local","close issues getExploredNotes: "+e);
            return null;
        }
    }
    public static void storeOwnerNotes(List<Note> note,Context context){
        try {
            openData(context);

            notes = note;
            String sql = "drop table if exists owner";
            db.execSQL(sql);
            setup();
            for (int i = 0; i < notes.size(); i++) {
                String sql2 = "INSERT INTO owner(noteID, title, contents, date,lat, lng,owner)" +
                        " VALUES('" + notes.get(i).getNoteId() + "','" +
                        notes.get(i).getTitle() + "','" + notes.get(i).getContent() + "','" +
                        notes.get(i).getDate() + "','" + notes.get(i).getLat() + "','" +
                        notes.get(i).getLng() + "','" + notes.get(i).getOwner() + "')";
                db.execSQL(sql2);


            }
            db.close();
        }catch (Exception e){
            db.close();
            Log.v("local","store owner notes"+e);
        }
        Log.v("local","store owner notes");

    }
    public static void storeExploredNotes(List<Note> note,Context context){
        try {
            openData(context);
            notes = note;
            String sql = "drop table if exists explored";
            db.execSQL(sql);
            setup();
            for (int i = 0; i < notes.size(); i++) {
                String sql2 = "INSERT INTO explored(noteID, title, contents, date,lat, lng,owner)" +
                        " VALUES('" + notes.get(i).getNoteId() + "','" +
                        notes.get(i).getTitle() + "','" + notes.get(i).getContent() + "','" +
                        notes.get(i).getDate() + "','" + notes.get(i).getLat() + "','" +
                        notes.get(i).getLng() + "','" + notes.get(i).getOwner() + "')";
                db.execSQL(sql2);


            }
            db.close();
        }catch (Exception e){
            db.close();
            Log.v("local","store explored notes"+e);
        }
        Log.v("local","store explored notes");

    }
    public static void storeOwnerExploredNotes(String data, Context context){
        num++;

        //try {
            Log.v("local", "store OwnerExplore Notes: " + data);
            ArrayList<Note> newowner = Note.getOwnerExploredFromJSON(data).get(0);
            ArrayList<Note>newexplored = Note.getOwnerExploredFromJSON(data).get(1);
            if(!owner.equals(newowner)) {
                storeOwnerNotes(owner, context);
                Note.owner = owner;
            }
            if(!explored.equals(newexplored)) {
                storeExploredNotes(explored, context);
                Note.explored = explored;
            }
          //  db.close();
        //}catch (Exception e){
            //Log.v("local", "close issues "+e );
            //db.close();
        //}
        num--;
        Log.v("num", ""+num );
        Intent intent =new Intent();
        intent.setAction("explore");
        context.sendBroadcast(intent);
    }


public static void setup(){

    String sqlnotes = "CREATE TABLE if not exists notes(noteID Integer PRIMARY KEY," +
            "title String, contents String, date String, lat REAL, lng REAL,owner String)";
    db.execSQL(sqlnotes);
    String sqluser = "CREATE TABLE if not exists user(username String PRIMARY KEY"+
            ", password String , phone INTEGER)";
    db.execSQL(sqluser);
    String sqlowner = "CREATE TABLE if not exists owner(noteID Integer PRIMARY KEY," +
            "title String, contents String, date String, lat REAL, lng REAL, owner String)";
    db.execSQL(sqlowner);
    String sqlexplored = "CREATE TABLE if not exists explored(noteID Integer PRIMARY KEY," +
            "title String, contents String, date String, lat REAL, lng REAL, owner String)";
    db.execSQL(sqlexplored);

}
public static void getOwnerExploredNotes(){

}
public static void clean(Context c){
        openData(c);
        String notes="drop table if exists notes";
        db.execSQL(notes);
        String user="drop table if exists user";
        db.execSQL(user);
        String owner="drop table if exists owner";
        db.execSQL(owner);
        String explored="drop table if exists explored";
        db.execSQL(explored);
        db.close();
}
}


