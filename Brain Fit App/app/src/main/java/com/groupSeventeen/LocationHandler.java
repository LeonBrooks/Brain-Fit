package com.groupSeventeen;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;

public class LocationHandler implements LocationListener {
    Context context;
    private double speed;
    private double latitude;
    private double longitude;

    public LocationHandler(Context context) {
        this.context = context;

        LocationManager locationManager;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        Location lastLocation=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        this.onLocationChanged(lastLocation);
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location == null)
            return;
        speed = location.getSpeed() * 3.6;
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        //System.out.println(speed);
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

    public double getSpeed() {
        return speed;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
