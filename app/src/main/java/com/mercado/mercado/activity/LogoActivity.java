package com.mercado.mercado.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.mercado.mercado.on.R;

public class LogoActivity extends AppCompatActivity {

    Intent intentMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        intentMain = new Intent(getApplicationContext(), MainActivity.class);

        new Handler().postDelayed(() -> {

            startActivity(intentMain);
            finish();
        },3000);
    }
}