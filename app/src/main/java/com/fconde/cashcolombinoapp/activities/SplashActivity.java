package com.fconde.cashcolombinoapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.fconde.cashcolombinoapp.models.Comunicador;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intentMain = new Intent(this, MainActivity.class);
        startActivity(intentMain);

        finish();
    }
}
