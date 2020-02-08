package com.dec04.geoshareapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.Arrays;

public class GoogleMapActivity extends AppCompatActivity implements
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int PERMISSION_REQUEST_CODE = 1;
    GoogleMap googleMap;
    PermissionUtils permissionUtils;
    Context applicationContext;
    private final String[] permissions = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.fragmentGoogleMap);
        mapFragment.getMapAsync(this);

        this.applicationContext = GoogleMapActivity.this;
        permissionUtils = new PermissionUtils(applicationContext);
    }

    @Override
    public void onMapReady(GoogleMap _googleMap) {
        googleMap = _googleMap;

        // Sets the map type to be "NORMAL"
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        googleMap.setOnMyLocationButtonClickListener(this);
        googleMap.setOnMyLocationClickListener(this);

        if (permissionUtils.check(permissions)) {
            googleMap.setMyLocationEnabled(true);
            /*
             * TODO: Сделать так, что бы после получения разрешений
             *  и включения слоя локации пользователя,
             *  камера сразу двигалась к последней локации пользователя
             *  */
        } else {
            permissionUtils.requestPermission(permissions);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (permissionUtils.flagPermissionDenied) {
            // Permission was not granted, display error dialog.
            permissionUtils.showMissingPermissionError(permissions);
            permissionUtils.flagPermissionDenied = false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != PERMISSION_REQUEST_CODE) {
            return;
        }

        if (permissions.length != 0) {
            if (!permissionUtils.check(permissions)) {
                // Display the missing permission error dialog when the fragments resume.
                permissionUtils.flagPermissionDenied = true;
            } else {
                // Enable the my location layer if the permission has been granted.
                googleMap.setMyLocationEnabled(true);
                /*
                 * TODO: Сделать так, что бы после получения разрешений
                 *  и включения слоя локации пользователя,
                 *  камера сразу двигалась к последней локации пользователя
                 *  */
            }
        }
    }
}
