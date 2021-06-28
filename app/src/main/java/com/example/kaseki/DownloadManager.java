package com.example.kaseki;

import android.app.Application;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;
import com.yausername.youtubedl_android.YoutubeDL;
import com.yausername.youtubedl_android.YoutubeDLException;
import com.yausername.youtubedl_android.YoutubeDLRequest;

import java.io.*;
import java.net.URL;

public class DownloadManager {
    private File path;
    private Application application;
    public void initialize(Application application,String uri){
        //Initializing Youtube -dl
        this.application = application;
        try {
            YoutubeDL.getInstance().init(application);
        } catch (YoutubeDLException e) {
            Log.e("TAG", "failed to initialize youtubedl-android", e);
        }
        //Path --getApplicationInfo().dataDir

        path = new File(uri, "youtubedl-android");

    }

    public boolean download(Song song, Boolean toDownload){

        YoutubeDLRequest request = new YoutubeDLRequest("https://www.youtube.com/watch?v="+song.getVideoID());

        //Setting the options
        Runnable download = new Runnable() {
            @Override
            public void run() {
                song.setDownloaded(toDownload);
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
                if(toDownload) {
                    //Adding song to the Playlist
                    File file = new File(path.getAbsolutePath() + "/" + song.getVideoID() + ".mp3");
                    if (file.exists()) {
                        try {
                            download_thumbnail(song.getThumbnailPath(), song.getVideoID());
                            song.setThumbnailPath(application.getApplicationInfo().dataDir + "/thumbnails/" + song.getVideoID() + ".jpg");
                            System.out.println(song.getThumbnailPath());
                        } catch (IOException e) {
                            System.out.println(e);
                        }
                        Log.d("Download", "Song Downloaded");
                        MainActivity.getPlaylists().get(0).getSongs().add(song);
                        MainActivity.addDownloaded(song);

                        Utils.serializePlaylist(MainActivity.getPlaylists(), MainActivity.getSerializedPath());
                    } else {
                        Log.d("Download", "File Does not downloaded Successfully");
                        Toast.makeText(MainActivity.getCurrentInstance(), "Download failed on your device", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Player.start(Uri.parse("file://"+path.getAbsolutePath() + "/" + song.getVideoID() + ".mp3"),application);
                    MainActivity.getCurrentInstance().getSecondarySongDisplayController().getSongProgressBar().setMax(Player.getPlayer().getDuration());
                    MainActivity.getCurrentInstance().set(song);
                }
            }

        };

        Thread thread = new Thread(download);
        thread.start();

        return true;
    }

    public void download_thumbnail(String address, String video_id) throws IOException {
        URL url = new URL (address);
        InputStream input = url.openStream();
        try {
            OutputStream output = new FileOutputStream(application.getApplicationInfo().dataDir + "/thumbnails/"+video_id+".jpg");
            try {
                byte[] buffer = new byte[20000];
                int bytesRead = 0;
                while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0) {
                    output.write(buffer, 0, bytesRead);
                }
            } finally {
                output.close();
            }
        } finally {
            input.close();
        }
        return;
    }

    public String getPath(){
        return path.getAbsolutePath().toString();
    }

    public Application getApplication(){
        return application;
    }
}

