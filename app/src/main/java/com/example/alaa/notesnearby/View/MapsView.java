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

import com.example.alaa.notesnearby.Model.LocalData;
import com.example.alaa.notesnearby.Model.Note;
import com.example.alaa.notesnearby.Model.Tracker;
import com.example.alaa.notesnearby.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsView extends FragmentActivity implements OnMapReadyCallback {

    private static GoogleMap mMap;
    static ArrayList<Note> notes =new ArrayList<>();
    Double lat,lng;
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

        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                updateLocation();
                updateNotes();
                handler.postDelayed(this, 15000);
            }
        };

//Start
        handler.postDelayed(runnable, 1000);
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
    public static void addNotes(ArrayList<Note> notes){
        for(int i=0;i<notes.size();i++){
            mMap.addMarker(new MarkerOptions().position(
                    new LatLng(notes.get(i).getLng(),notes.get(i).getLng())).title(notes.get(i).getTitle()));
        }
    }
    public void updateLocation(){
        SharedPreferences share = getSharedPreferences("log",MODE_PRIVATE);
        double lat =Double.parseDouble(share.getString("lat",null));
        double lng = Double.parseDouble(share.getString("lng",null));
        mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lng)).title("me"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng),1));
    }
    public void updateNotes(){
        ArrayList<Note> notes =LocalData.getNotes(this);
        for(int i=0;i<notes.size();i++){
            mMap.addMarker(new MarkerOptions().position(
                    new LatLng(notes.get(i).getLat(), notes.get(i).getLng())).title(notes.get(i).getTitle()));
        }
    }
}
