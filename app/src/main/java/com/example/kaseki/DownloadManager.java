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
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class DownloadManager {
    private File path;
    private Application application;
    private ThreadPoolExecutor threadPoolExecutor;
    private HashMap<Song, Song> curDownloadingToRealInstance=new HashMap<>();
    public void initialize(Application application,String uri){
        //Initializing Youtube -dl
        threadPoolExecutor=(ThreadPoolExecutor) Executors.newCachedThreadPool();
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
        //Setting the options
        curDownloadingToRealInstance.put(song, song);
        Runnable download = () -> {
            YoutubeDLRequest request = new YoutubeDLRequest("https://www.youtube.com/watch?v="+song.getVideoID());
            Log.d("HELLO", "DOWNLOAD GOT CALLED");

            song.setDownloaded(toDownload);
            request.addOption("-o", path.getAbsolutePath() + "/"+song.getVideoID()+".mp3");
            request.addOption("-f","bestaudio[ext=m4a]");

            try {
                YoutubeDL.getInstance().execute(request, (progress, etaInSeconds) -> {

                    if (song.getCurController()!=null) {
                        Log.d("DOWNLOAD_STATUS", song.getSongName() + " " + progress);
                        MainActivity.getCurrentInstance().runOnUiThread(()->
                                song.getCurController().relapseDownloadProgress(progress));
                    }
                });
            } catch (YoutubeDLException | InterruptedException e) {
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
                    if (song.getCurController()!=null) {
                        MainActivity.getCurrentInstance().runOnUiThread(song.getCurController()::notifyDownloaded);
                    }
                    Utils.serializePlaylist(MainActivity.getPlaylists(), MainActivity.getSerializedPath());
                } else {
                    Log.d("Download", "File Does not downloaded Successfully");
                    MainActivity.getCurrentInstance().runOnUiThread(
                            ()->{ Toast.makeText(MainActivity.getCurrentInstance(),
                                    "Download failed on your device", Toast.LENGTH_LONG).show();
                                song.getCurController().notifyFailedDownload();});
                }
            }
            else{
                File file = new File(path.getAbsolutePath() + "/" + song.getVideoID() + ".mp3");
                if(file.exists()){
                    Player.start(Uri.parse("file://"+path.getAbsolutePath() + "/" + song.getVideoID() + ".mp3"),application);
                    MainActivity.getCurrentInstance().getSecondarySongDisplayController().getSongProgressBar().setMax(Player.getPlayer().getDuration());
                    MainActivity.getCurrentInstance().set(song);
                    MainActivity.getCurrentInstance().runOnUiThread(song.getCurController()::notifyFailedDownload);
                }
                else{
                    Log.d("Download", "File Does not downloaded Successfully");
                    MainActivity.getCurrentInstance().runOnUiThread(
                            ()->{ Toast.makeText(MainActivity.getCurrentInstance(),
                                    "Download failed on your device", Toast.LENGTH_LONG).show();
                                song.getCurController().notifyFailedDownload();});
                }
            }
            curDownloadingToRealInstance.remove(song);
        };
        threadPoolExecutor.execute(download);

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

    public Song getPreviousDownloadingStatus(Song song) {
        if (curDownloadingToRealInstance.containsKey(song)) {
            return curDownloadingToRealInstance.get(song);
        } else {
            return song;
        }
    }
    public boolean isDownloading(Song song) {
        return curDownloadingToRealInstance.containsKey(song);
    }
}

