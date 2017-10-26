package com.example.khokhlovart_loftschool.moneytracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "KhokhlovArt log: ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "i start!");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "i pause!");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "i stop!");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "i destroy! :(");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "i resume!");
    }
}
