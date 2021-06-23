package com.example.kaseki;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.Console;
import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private BottomBarController bottomBarController;
    private SecondarySongDisplayController secondarySongDisplayController;
    private MainDisplayFlipperController mainDisplayFlipperController;
    private static Download_Manager Downloader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        Utils.changeStatusBarColor(this, R.color.transparent_black);
        Utils.changeNavBarColor(this, R.color.black);
        setContentView(R.layout.activity_main);
        Downloader = new Download_Manager();
        Downloader.initialize(getApplication(),getApplicationInfo().dataDir);

        //Bottom Bar
        bottomBarController =new BottomBarController(this);

        //Song Display Bar
        secondarySongDisplayController=new SecondarySongDisplayController(this);

        mainDisplayFlipperController=new MainDisplayFlipperController(this);

        //example
        Downloader.download("sAHbwzkrlmg");

    }
    public void changeMainDisplayController(Class<? extends DisplayController> type) {
        mainDisplayFlipperController.flipToDisplay(type);
    }
}