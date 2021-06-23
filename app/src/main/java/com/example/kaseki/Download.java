package com.example.kaseki;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

    public class Download extends Activity {
    String url;
    MainActivity mainActivity;
    public String Download_it(String video_id, MainActivity mainActivity){
        if(!Python.isStarted())
            Python.start(new AndroidPlatform(mainActivity));
        Python py = Python.getInstance();
        final PyObject pyobj = py.getModule("script");
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                boolean obj = false;
                obj = pyobj.callAttr("main", url).toBoolean();
                if(obj == true){
                    String filePath = mainActivity.getApplicationInfo().dataDir;
                    Log.e("reached",filePath);
                    MediaPlayer song = MediaPlayer.create(mainActivity, Uri.parse(filePath+"/files/"+url+".mp3"));
                    song.start();
                }
                else{
                    Log.e("reached","It does not work");
                }
            }
        };
        new Thread(runnable).start();
        url = video_id;
        this.mainActivity = mainActivity;
        return "true";
    }
}
