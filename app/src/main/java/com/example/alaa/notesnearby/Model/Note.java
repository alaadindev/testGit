package com.example.alaa.notesnearby.Model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Note {
    private String NOTEID;
    private String TITLE;
    private String CONTENT;
    private String DATE;
    private double LAT;
    private double LNG;
    private String OWNER;

    public static List<Note> explored;
    public static List<Note> owner;

    public String getNoteId(){ return NOTEID;}
    public String getTitle(){ return TITLE;}
    public String getContent(){ return CONTENT;}
    public String getDate(){ return DATE;}
    public double getLat(){ return LAT;}
    public double getLng(){ return LNG;}
    public String getOwner(){return OWNER;}

    public Note(JSONObject json){
        setFieldByJSON(json);
    }
    public Note(String noteid,String title, String content, String date, double lat, double lng, String owner){
        setFieldByString(noteid, title, content, date, lat, lng, owner);
    }

    public void setNoteID(String noteid){
        NOTEID = noteid;
    }
    public void setTitle(String title){
        TITLE = title;
    }
    public void setContent(String content){
        CONTENT = content;
    }
    public void setDate(String date){
        DATE = date;
    }
    public void setLat(double lat){
        LAT = lat;
    }
    public void setLng(double lng){ LNG = lng; }
    public void setOwner(String owner){OWNER=owner;}

    public void setFieldByString(String noteid, String title, String content, String date, double lat, double lng,String owner){
        setNoteID(noteid);
        setTitle(title);
        setContent(content);
        setDate(date);
        setLat(lat);
        setLng(lng);
        setOwner(owner);
    }
    public void setFieldByJSON(JSONObject json){
        try {
            setNoteID(json.getString("noteID"));
            setTitle(json.getString("title"));
            setContent(json.getString("contents"));
            setDate(json.getString("dates"));
            setLat(Double.parseDouble(json.getString("lat")));
            setLng(Double.parseDouble(json.getString("lng")));
            setOwner(json.getString("username"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static Note getNoteFromSQL(String noteid, String title, String content, String date, double lat, double lng, String owner){
        Log.v("note","getnotefromsql"+noteid+" title: "+title+" content: "+content+" date: "+date+" lat:"+lat+" lng:"+lng+" owner: "+owner);

        return new Note(noteid,title,content,date,lat,lng, owner);
    }
    public static ArrayList<Note> getNoteFromJSON(String data){
        try {

            JSONObject JSON = new JSONObject(data);
            Log.v("note","getnotefrom json: "+JSON);
            JSONArray jsonarray =JSON.getJSONArray("data");
            ArrayList<Note> notes =new ArrayList<Note>();
            for(int i=0;i<jsonarray.length();i++){
                Note note = new Note(jsonarray.getJSONObject(i));
                notes.add(note);
            }
            return notes;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static ArrayList<ArrayList<Note>> getOwnerExploredFromJSON(String data){
        try {

            JSONObject JSON = new JSONObject(data);
            String isowner =JSON.getString("hasowned");
            String isexplored = JSON.getString("hasexplored");
            ArrayList<Note> owner =new ArrayList<Note>();
            ArrayList<Note> explored = new ArrayList<Note>();
            JSONArray jsonowner =JSON.getJSONArray("owned");
            JSONArray jsonexplored =JSON.getJSONArray("explored");
            if(isowner=="true"){

            }
            if(isexplored=="true"){

            }

            ArrayList<ArrayList<Note>> notes = new ArrayList<ArrayList<Note>>();

            for(int i=0;i<jsonowner.length();i++){
                Note note = new Note(jsonowner.getJSONObject(i));
                owner.add(note);
            }
            for(int i=0;i<jsonexplored.length();i++){
                Note note = new Note(jsonexplored.getJSONObject(i));
                explored.add(note);
            }
            notes.add(owner);
            notes.add(explored);
            return notes;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
