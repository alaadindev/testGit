package com.example.alaa.notesnearby.Model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Note {
    private String NOTEID;
    private String TITLE;
    private String CONTENT;
    private String DATE;
    private double LAT;
    private double LNG;


    public String getNoteId(){ return NOTEID;}
    public String getTitle(){ return TITLE;}
    public String getContent(){ return CONTENT;}
    public String getDate(){ return DATE;}
    public double getLat(){ return LAT;}
    public double getLng(){ return LNG;}

    public Note(JSONObject json){
        setFieldByJSON(json);
    }
    public Note(String noteid,String title, String content, String date, double lat, double lng){
        setFieldByString(noteid, title, content, date, lat, lng);
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

    public void setFieldByString(String noteid, String title, String content, String date, double lat, double lng){
        setNoteID(noteid);
        setTitle(title);
        setContent(content);
        setDate(date);
        Log.v("laterr","lat:"+lat+" lng:"+lng);
        setLat(lat);
        setLng(lng);
    }
    public void setFieldByJSON(JSONObject json){
        try {
            Log.v("noteid",json.getString("noteID")+""+json.getString("lng"));
            setNoteID(json.getString("noteID"));
            setTitle(json.getString("title"));
            setContent(json.getString("contents"));
            setDate(json.getString("dates"));
            setLat(Double.parseDouble(json.getString("lat")));
            setLng(Double.parseDouble(json.getString("lng")));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static Note getNoteFromSQL(String noteid, String title, String content, String date, double lat, double lng){
        Log.v("notefromsql",noteid+" title: "+title+" content: "+content+" date: "+date+" lat:"+lat+" lng:"+lng);

        return new Note(noteid,title,content,date,lat,lng);
    }
    public static ArrayList<Note> getNoteFromJSON(String data){
        try {
            JSONObject JSON = new JSONObject(data);
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
}
