package com.example.kaseki;

import android.app.Application;
import android.util.Log;
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

    public boolean download(String video_id){
        YoutubeDLRequest request = new YoutubeDLRequest("https://www.youtube.com/watch?v="+video_id);

        //Setting the options
        request.addOption("-o", path.getAbsolutePath() + "/"+video_id+".mp3");
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

        return true;
    }

    public String getPath(){
        return path.toString();
    }
}
