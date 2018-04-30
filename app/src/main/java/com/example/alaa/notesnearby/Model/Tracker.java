package com.example.alaa.notesnearby.Model;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.alaa.notesnearby.MainActivity;
import com.example.alaa.notesnearby.View.MapsView;

import java.util.ArrayList;

public class Tracker extends Service implements LocationListener {
    final Context context = this;
    boolean isGPSenable = false;
    boolean isnetworkenable = false;
    boolean cangetlocation = false;

    Location location;
    double latitude;
    double longitude;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATE = 10;
    private static final long MIN_TIME_CHANGE_FOR_UODATE = 1000 * 60;

    protected LocationManager locationManager;
    @SuppressLint("MissingPermission")
    public Tracker() {

    }

    @SuppressLint("MissingPermission")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.v("log","work from Tracker onstartcommand");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return START_STICKY;
        }
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 7000L, 10, this);

        //getLocation();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    public void checkNearBy(double lat, double lng){
        Location locationA= new Location("A");
        locationA.setLatitude(lat);
        locationA.setLongitude(lng);
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

    @Override
    public void onLocationChanged(Location location) {

        final Server server = new Server(context);
        SharedPreferences share = getSharedPreferences("log",MODE_PRIVATE);
        SharedPreferences.Editor edit = share.edit();
        edit.putString("lat",location.getLatitude()+"");
        edit.putString("lng",location.getLongitude()+"");
        edit.apply();
        final String user = share.getString("user",null);
        final String pass = share.getString("pass",null);
        final String lat = location.getLatitude()+"";
        final String lng = location.getLongitude()+"";
        checkNearBy(location.getLatitude(),location.getLongitude());
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                server.updateNote(user, pass, lat,lng);
            }
        });

        thread.start();

        String currentLocation = "The location is changed to Lat: " + lat + " Lng: " + lng;
        Log.v("loc",currentLocation);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @SuppressLint("MissingPermission")
    public Location getLocation() {
        try {
            locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
            isnetworkenable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            isGPSenable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (!isnetworkenable && !isGPSenable) {

            } else {
                this.cangetlocation = true;
                if (isnetworkenable) {

                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            MIN_DISTANCE_CHANGE_FOR_UPDATE, MIN_TIME_CHANGE_FOR_UODATE,
                            this);
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                if (isGPSenable) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            MIN_DISTANCE_CHANGE_FOR_UPDATE, MIN_TIME_CHANGE_FOR_UODATE,
                            this);
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
            }
        }catch (Exception e){

        }

        return location;
    }
}
