package com.example.alaa.notesnearby.Model;

public class Note {
    private static String noteID;
    private static String title;
    private static String content;
    private static double lat;
    private static double lng;
    private static String date;

    public static String getNoteId(){ return noteID;}
    public static String getTitle(){ return title;}
    public static String getContent(){ return content;}
    public static double getLat(){ return lat;}
    public static double getLng(){ return lng;}
    public static String getDate(){ return date;}

    public static void setNoteID(String noteID){
        this.noteID = noteID;
    }
    public static void setTitle(String title){
        this.title = title;
    }
    public static void setContent(String content){
        this.content = content;
    }
    public static void setLat(double lat){
        this.lat = lat;
    }
    public static void setLng(double lng){
        this.lng = lng;
    }
    public static void setDate(String date){
        this.date = date;
    }
}
