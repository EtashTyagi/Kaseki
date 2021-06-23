package com.example.kaseki;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private BottomBarController bottomBarController;
    private SecondarySongDisplayController secondarySongDisplayController;
    private MainDisplayFlipperController mainDisplayFlipperController;
    private Button button;
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
        button = findViewById(R.id.button2);
        MainActivity mainActivity = this;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Download obj = new Download();
                Log.d("Ouput",obj.Download_it("VkJlv0m6els",mainActivity));
            }
        });

    }
    public void changeMainDisplayController(Class<? extends DisplayController> type) {
        mainDisplayFlipperController.flipToDisplay(type);
    }
}