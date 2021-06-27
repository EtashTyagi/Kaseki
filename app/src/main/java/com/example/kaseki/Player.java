package com.example.kaseki;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import androidx.annotation.Nullable;

import java.io.IOException;

public class Player extends Service {
    private static MediaPlayer player;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public static void stop(){
        player.stop();
    }
    public static void pause(){
        player.pause();
    }
    public static void start(Uri uri, Application application){
        if(player != null && player.isPlaying()) player.pause();
        player = MediaPlayer.create(application,uri);
        player.start();
    }
    public static void resume() {
        player.start();
    }
    public static MediaPlayer getPlayer(){
        return player;
    }
}
