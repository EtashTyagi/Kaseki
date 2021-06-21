package com.example.kaseki;

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
    }
}