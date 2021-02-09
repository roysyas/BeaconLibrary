package io.quadrant.beaconlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import io.quadrant.beaconlib.BluetoothUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}