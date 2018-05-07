package com.example.alaa.notesnearby.Model;

import android.content.Context;
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

    public static List<Note> explored = new ArrayList<Note>();
    public static List<Note> owner = new ArrayList<Note>();

    public String getNoteId(){ return NOTEID;}
    public String getTitle(){ return TITLE;}
    public String getContent(){ return CONTENT;}
    public String getDate(){ return DATE;}
    public double getLat(){ return LAT;}
    public double getLng(){ return LNG;}
    public String getOwner(){return OWNER;}

    public Note(){

    }

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
            Log.v("Note"," noteid:"+json.getString("noteID")+" title: "+
                    json.getString("title")+" contents: "+json.getString("contents")+
                    " dates: "+json.getString("dates")+" lat: "+json.getString("lat")+
                    " lng: "+json.getString("lng")+" username: "+json.getString("username"));

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
            JSONArray jsondata =JSON.getJSONArray("data");

            ArrayList<Note> notes =new ArrayList<Note>();
            for(int i=0;i<jsondata.length();i++){
                Note note = new Note(jsondata.getJSONObject(i));
                notes.add(note);
            }
            return notes;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static ArrayList<ArrayList<Note>> getOwnerExploredFromJSON(String data){

        JSONObject JSON;
        ArrayList<ArrayList<Note>> notes = new ArrayList<ArrayList<Note>>();
        try {
            JSON = new JSONObject(data);


            String isowner = JSON.getString("hasowned");
            String isexplored = JSON.getString("hasexplored");
            ArrayList<Note> newowner = new ArrayList<Note>();
            ArrayList<Note> newexplored = new ArrayList<Note>();

            JSONArray jsonowner;
            JSONArray jsonexplored;


            if (isowner.equals("true")) {
                jsonowner = JSON.getJSONArray("owned");
                for (int i = 0; i < jsonowner.length(); i++) {
                    Note note = new Note(jsonowner.getJSONObject(i));
                    newowner.add(note);
                }
            }
            if (isexplored.equals("true")) {

                jsonexplored = JSON.getJSONArray("explored");
                for (int i = 0; i < jsonexplored.length(); i++) {
                    Note note = new Note(jsonexplored.getJSONObject(i));
                    newexplored.add(note);
                }
            }

            notes.add(newowner);
            notes.add(newexplored);
            Log.v("note-OwnerExplored","owner: " + owner.get(0).getNoteId()+" explore: " + explored.get(0).getNoteId());
            return notes;
        }catch (Exception e){
            Log.v("error","note: "+e);
            return notes;
        }

    }
    public static ArrayList<Note> getExplorable(Context context) {


        List<Note> list1 = Note.owner;
        List<Note> list2 = LocalData1.getNotes(context);
        if (list1 == null){
            list1 = new ArrayList<Note>();
            list1.add(new Note());
        }
        if (list2 == null){
            list2 = new ArrayList<Note>();
            list2.add(new Note());
        }
// Prepare a union
            List<Note> union = new ArrayList<Note>(list1);
            union.addAll(list2);
// Prepare an intersection
            List<Note> intersection = new ArrayList<Note>(list1);
            intersection.retainAll(list2);
// Subtract the intersection from the union
            union.removeAll(intersection);

            list1 = Note.explored;
            list2 = union;
        if (list1 == null){
            list1 = new ArrayList<Note>();
            list1.add(new Note());
        }


// Prepare a union
            union = new ArrayList<Note>(list1);
            union.addAll(list2);
// Prepare an intersection
            intersection = new ArrayList<Note>(list1);
            intersection.retainAll(list2);
// Subtract the intersection from the union
            union.removeAll(intersection);
            return (ArrayList<Note>) union;

    }
}
