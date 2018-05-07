package com.example.alaa.notesnearby.View;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.alaa.notesnearby.Model.LocalData;
import com.example.alaa.notesnearby.Model.Note;
import com.example.alaa.notesnearby.Model.Tracker;
import com.example.alaa.notesnearby.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsView extends FragmentActivity implements OnMapReadyCallback {

    private static GoogleMap mMap;
    static ArrayList<Note> notes =new ArrayList<>();
    Double lat,lng;
    private Marker user;
    Button makenote,explored;
    private static boolean islooping=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_view);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent intent2 = new Intent(MapsView.this, Tracker.class);
        startService(intent2);
        //LocalData localData =new LocalData(this);
        //notes = LocalData.getNotes(this);


        makenote = findViewById(R.id.makenote);
        explored = findViewById(R.id.explored);

        makenote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsView.this,MakeNote.class);
                startActivity(intent);
            }
        });
        explored.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsView.this, Explore.class);
                startActivity(intent);
            }
        });
        if(!islooping){
            loop();
            islooping=true;
        }
    }
    public void loop(){
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                updateNotes();
                handler.postDelayed(this,25000);
            }
        };
        final Handler handler1 = new Handler();
        final Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                updateLocation();
                //checkNearBy();
                handler1.postDelayed(this,9000);
            }
        };
//Start
        handler.postDelayed(runnable, 25000);
        handler1.postDelayed(runnable1, 9000);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

    }
    public static void updateMarker(Location location){
        double lat =location.getLatitude();
        double lng = location.getLongitude();
        mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lng)).title("updated"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng),1));
    }
    public static void addNotes(ArrayList<Note> newnotes){
        for(int i=0;i<notes.size();i++){
            if(!notes.contains(newnotes.get(i))) {
                mMap.addMarker(new MarkerOptions().position(
                        new LatLng(notes.get(i).getLng(), notes.get(i).getLng())).title(notes.get(i).getTitle()));
            }
        }
    }
    public void updateLocation() {
        SharedPreferences share = getSharedPreferences("log", MODE_PRIVATE);
        double lat = Double.parseDouble(share.getString("lat", null));
        double lng = Double.parseDouble(share.getString("lng", null));
        if (user == null) {
            user = mMap.addMarker(new MarkerOptions().position(
                    new LatLng(lat, lng)).title("me").
                    icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        } else {
            user.setPosition(new LatLng(lat, lng));
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 13));
        Log.v("loc1", lat + " : " + lng);

    }
    public void updateNotes(){
        ArrayList<Note> newnotes =LocalData.getNotes(this);
        for(int i=0;i<newnotes.size();i++){
            if(!notes.contains(newnotes.get(i))) {
                float color=BitmapDescriptorFactory.HUE_RED;
                if(Note.owner!=null&&Note.explored!=null)
                if(Note.owner.contains(newnotes.get(i))){
                    color=BitmapDescriptorFactory.HUE_BLUE;
                }else if(Note.explored.contains(newnotes.get(i))){
                    color=BitmapDescriptorFactory.HUE_GREEN;
                }

                mMap.addMarker(new MarkerOptions().position(
                        new LatLng(newnotes.get(i).getLat(), newnotes.get(i).getLng())).title(newnotes.get(i).getTitle()).
                        icon(BitmapDescriptorFactory.defaultMarker(color))).showInfoWindow();
                Log.v("owner", newnotes.get(i).getOwner());
                notes.add(newnotes.get(i));
            }
        }
    }
    public void checkNearBy(){
        double latstart = user.getPosition().latitude;
        double lngstart = user.getPosition().longitude;
        Location locationA= new Location("A");
        locationA.setLatitude(latstart);
        locationA.setLongitude(lngstart);
        Location locationB = new Location("B");


        double latnote ,lngnote;
        float result[] = new float[0];
        ArrayList<Note> notes =LocalData.getNotes(this);
        for(int i=0;i<notes.size();i++){
            locationB.setLatitude(notes.get(i).getLat());
            locationB.setLongitude(notes.get(i).getLng());
            float distance = locationB.distanceTo(locationB);
            if(distance<20){
                explore(notes.get(i));
            }
        }
    }
    public void explore(Note note){

    }
}
