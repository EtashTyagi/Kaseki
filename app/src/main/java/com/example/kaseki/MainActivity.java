package com.example.kaseki;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.widget.SeekBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        Utils.changeStatusBarColor(     this, R.color.transparent_black);
        Utils.changeNavBarColor(this, R.color.black);
        setContentView(R.layout.activity_main);

        // For just checking
        SeekBar bar = findViewById(R.id.seekBar);
        bar.setProgressTintList(ColorStateList.valueOf(Color.WHITE));
    }
}