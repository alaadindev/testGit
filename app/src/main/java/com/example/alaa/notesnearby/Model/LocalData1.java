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

public class LocalData1 {

    static Context context;
    static SQLiteDatabase db;


    private static List<Note> notes = new ArrayList<Note>();
    private static List<Note> owner = new ArrayList<Note>();
    private static List<Note> explored = new ArrayList<Note>();
    private static User user = null;
    private static List<Note> cursorlist = new ArrayList<Note>();

    public LocalData1(Context context1){
        context=context1;
        db = context.openOrCreateDatabase("local.db", Context.MODE_PRIVATE,null);
    }

    public  static void openData(){
        if(!db.isOpen())
            db = context.openOrCreateDatabase("local.db", Context.MODE_PRIVATE,null);
    }
    public static void jop(String sql){
        try {
            openData();
            db.execSQL(sql);
            db.close();
            Log.v("local","jop");
        }catch (Exception e){
            Log.v("error","local: "+e);
        }
    }
    public static void jopCursor(String sql){
        try {
            openData();
            Cursor cursor = db.rawQuery(sql, null);
            cursorlist=new ArrayList<Note>();
            while (cursor.moveToNext()) {
                cursorlist.add(Note.getNoteFromSQL(
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
            Log.v("local","jopcursor "+cursorlist.get(0).getOwner()+" : "+cursorlist.get(0).getNoteId());
        }catch (Exception e){
            Log.v("error","local: "+e);
            e.printStackTrace();
        }
    }

    public static ArrayList<Note> getNotes(Context context1){
        setup();

        cursorlist = new ArrayList<Note>();
        String sql ="SELECT noteID, title, contents, date, lat, lng, owner FROM notes";
        jopCursor(sql);

        Log.v("local","get notes" + cursorlist.get(0).getTitle());

        notes=cursorlist;

        return (ArrayList<Note>) notes;
    }

    public static ArrayList<Note> getOwnerNotes(Context context1){
        setup();
        cursorlist = new ArrayList<Note>();
        String sql ="SELECT noteID, title, contents, date, lat, lng, owner FROM owner";
        jopCursor(sql);

        owner=cursorlist;
        return (ArrayList<Note>)owner;
    }
    public static ArrayList<Note> getExplordNotes(Context context1){
        setup();

        cursorlist = new ArrayList<Note>();
        String sql ="SELECT noteID, title, contents, date, lat, lng, owner FROM owner";
        jopCursor(sql);

        explored =cursorlist;
        return (ArrayList<Note>)explored;
    }
    public static void getOwnerExploredNotes(){
        explored=getExplordNotes(context);
        owner=getOwnerNotes(context);
        //Note.explored=explored;
        //Note.owner=owner;
    }

    public static void storeNotes(ArrayList<Note> note,Context context){
        notes=note;
        String sql="drop table if exists notes";
        jop(sql);

        setup();

        for(int i=0;i<notes.size();i++) {

            String sql2 = "INSERT INTO notes (noteID, title, contents, date,lat, lng,owner)" +
                    " VALUES('" + notes.get(i).getNoteId() + "','" +
                    notes.get(i).getTitle() + "','" + notes.get(i).getContent() + "','" +
                    notes.get(i).getDate() + "','" + notes.get(i).getLat() + "','" +
                    notes.get(i).getLng() + "','"+ notes.get(i).getOwner()+"')";
            jop(sql2);
        }

    }
    public static void storeOwnerNotes(ArrayList<Note> note,Context context){

        owner=note;
        String sql="drop table if exists owner";
        jop(sql);
        setup();
        openData();
        for(int i=0;i<owner.size();i++) {
            String sql2 = "INSERT INTO owner(noteID, title, contents, date,lat, lng,owner)" +
                    " VALUES('" + owner.get(i).getNoteId() + "','" +
                    owner.get(i).getTitle() + "','" + owner.get(i).getContent() + "','" +
                    owner.get(i).getDate() + "','" + owner.get(i).getLat() + "','" +
                    owner.get(i).getLng() + "','" + owner.get(i).getOwner()+"')";

            db.execSQL(sql2);
        }
        db.close();
        Log.v("local","store owner notes");

    }
    public static void storeExploredNotes(ArrayList<Note> note,Context context){

        explored=note;
        String sql="drop table if exists explored";
        jop(sql);
        setup();
        openData();
        for(int i=0;i<explored.size();i++) {
            String sql2 = "INSERT INTO explored(noteID, title, contents, date,lat, lng,owner)" +
                    " VALUES('" + explored.get(i).getNoteId() + "','" +
                    explored.get(i).getTitle() + "','" + explored.get(i).getContent() + "','" +
                    explored.get(i).getDate() + "','" + explored.get(i).getLat() + "','" +
                    explored.get(i).getLng() + "','" + explored.get(i).getOwner()+"')";
            db.execSQL(sql2);
        }

        db.close();
        Log.v("local","store explored notes");

    }
    public static void storeOwnerExploredNotes(String data, Context context){

        Log.v("local", "store OwnerExplore Notes: " + data);
        ArrayList<Note> newowner = Note.getOwnerExploredFromJSON(data).get(0);
        ArrayList<Note> newexplored = Note.getOwnerExploredFromJSON(data).get(1);
        if(!owner.equals(newowner)) {
            storeOwnerNotes(newowner, context);
            Note.owner = owner;
        }
        if(!explored.equals(newexplored)) {
            storeExploredNotes(newexplored, context);
            Note.explored = explored;
        }
        Log.v("local-OwnerExplored","owner: " + newowner.toString()+" explore: " + newexplored.toString());
        Intent intent =new Intent();
        intent.setAction("explore");
        context.sendBroadcast(intent);
    }


    public static void setup(){

        String sqlnotes = "CREATE TABLE if not exists notes(noteID Integer PRIMARY KEY," +
                "title String, contents String, date String, lat REAL, lng REAL,owner String)";
        jop(sqlnotes);

        String sqluser = "CREATE TABLE if not exists user(username String PRIMARY KEY"+
                ", password String , phone INTEGER)";
        jop(sqluser);

        String sqlowner = "CREATE TABLE if not exists owner(noteID Integer PRIMARY KEY," +
                "title String, contents String, date String, lat REAL, lng REAL,owner String)";
        jop(sqlowner);

        String sqlexplored = "CREATE TABLE if not exists explored(noteID Integer PRIMARY KEY," +
                "title String, contents String, date String, lat REAL, lng REAL,owner String)";
        jop(sqlexplored);

    }
    public static void clean(Context c){

        String notes="drop table if exists notes";
        jop(notes);
        String user="drop table if exists user";
        jop(user);
        String owner="drop table if exists owner";
        jop(owner);
        String explored="drop table if exists explored";
        jop(explored);

    }
}


