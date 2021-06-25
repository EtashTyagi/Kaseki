package com.example.kaseki;

import android.app.Application;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import com.google.android.material.shape.MarkerEdgeTreatment;
import com.yausername.youtubedl_android.YoutubeDL;
import com.yausername.youtubedl_android.YoutubeDLException;
import com.yausername.youtubedl_android.YoutubeDLRequest;

import java.io.File;

public class Download_Manager {
    private File path;
    public void initialize(Application application,String uri){
        //Initializing Youtube -dl
        try {
            YoutubeDL.getInstance().init(application);
        } catch (YoutubeDLException e) {
            Log.e("TAG", "failed to initialize youtubedl-android", e);
        }
        //Path --getApplicationInfo().dataDir

        path = new File(uri, "youtubedl-android");

    }

    public boolean download(Song song){
        YoutubeDLRequest request = new YoutubeDLRequest("https://www.youtube.com/watch?v="+song.getVideoID());

        //Setting the options
        Runnable download = new Runnable() {
            @Override
            public void run() {
                request.addOption("-o", path.getAbsolutePath() + "/"+song.getVideoID()+".mp3");
                request.addOption("-f","bestaudio[ext=m4a]");

                try {
                    YoutubeDL.getInstance().execute(request, (progress, etaInSeconds) -> {
                        System.out.println(String.valueOf(progress) + "% (ETA " + String.valueOf(etaInSeconds) + " seconds)");
                    });
                } catch (YoutubeDLException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //Adding song to the Playlist
                File file = new File(path.getAbsolutePath() + "/"+song.getVideoID()+".mp3");
                if(file.exists()){
                    Log.d("Download","Song Downloaded");
                    MainActivity.getPlaylists().get(0).getSongs().add(song);
                }
                else{
                    Log.d("Download","File Does not downloaded Successfully");
                }
            }

        };

        Thread thread = new Thread(download);
        thread.start();

        return true;
    }

    public String getPath(){
        return path.toString();
    }
}
