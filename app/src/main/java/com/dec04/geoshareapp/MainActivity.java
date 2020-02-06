package com.dec04.geoshareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onMapActivity();
    }

    public void onMapActivity() {
        Intent intentLogout = new Intent(MainActivity.this, GoogleMapActivity.class);
        startActivity(intentLogout);
    }
}
