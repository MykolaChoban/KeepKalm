package com.example.keepkalm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class RandomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Print Log
        Log.i("debug","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Print Log
        Log.i("debug","onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Print Log
        Log.i("debug","onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Print Log
        Log.i("debug","onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Print Log
        Log.i("debug","onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // Print Log
        Log.i("debug","onRestart");
    }
}