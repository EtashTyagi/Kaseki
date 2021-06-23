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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
        initiateBottomBar();
        //Song Display Bar
        initiateSecondarySongDisplay();
        //Main Display
        initiateMainDisplay();
        //Download example
        Downloader.download("sAHbwzkrlmg");
        Search search = new Search();
    }
    private void initiateBottomBar() {
        bottomBarController =new BottomBarController(this);
    }
    private void initiateSecondarySongDisplay() {
        secondarySongDisplayController=new SecondarySongDisplayController(this);
    }
    private void initiateMainDisplay() {
        mainDisplayFlipperController=new MainDisplayFlipperController(this);
        View test = findViewById(R.id.searchDisplayInclude);
        ArrayList<Song> searchList=new ArrayList<>();
        RecyclerView scroller = test.findViewById(R.id.scroller);
        SongDisplayAdapter scrollerAdapter = new SongDisplayAdapter(searchList);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        scroller.setLayoutManager(layoutManager);
        scroller.setAdapter(scrollerAdapter);
        mainDisplayFlipperController.initiateHomeDisplay();
        mainDisplayFlipperController.initiateSearchDisplay(searchList, scrollerAdapter);
        mainDisplayFlipperController.initiateLibraryDisplay();
    }
    public void changeMainDisplayController(Class<? extends DisplayController> type) {
        mainDisplayFlipperController.flipToDisplay(type);
    }
}