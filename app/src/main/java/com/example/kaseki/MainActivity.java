package com.example.kaseki;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private BottomBarController bottomBarController;
    private SecondarySongDisplayController secondarySongDisplayController;
    private MainDisplayFlipperController mainDisplayFlipperController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        Utils.changeStatusBarColor(this, R.color.transparent_black);
        Utils.changeNavBarColor(this, R.color.black);
        setContentView(R.layout.activity_main);

        bottomBarController =new BottomBarController(this);

        secondarySongDisplayController=new SecondarySongDisplayController(this);

        mainDisplayFlipperController=new MainDisplayFlipperController(this);
    }
    public void changeMainDisplayController(Class<? extends DisplayController> type) {
        mainDisplayFlipperController.flipToDisplay(type);
    }
}