package com.example.kaseki;

import android.net.Uri;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Objects;

public class Song implements Serializable {
    private String videoID;
    private String songName;
    private String thumbnailPath;
    private String artist;
    private transient MainActivity mainActivity;
    private boolean isDownloaded;
    private transient boolean isPlaying;
    private transient SongDisplayCardController curController=null;

    Song() {
        this.mainActivity=MainActivity.getCurrentInstance();
    }

    public String getVideoID() {
        return videoID;
    }

    public void setVideoID(String videoID) {
        this.videoID = videoID;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public boolean isDownloaded() {
        return isDownloaded;
    }

    public void setDownloaded(boolean downloaded) {
        isDownloaded = downloaded;
    }
    public boolean isPlaying() {
        return isPlaying;
    }

    public void setCurController(SongDisplayCardController curController) {
        this.curController = curController;
    }

    public SongDisplayCardController getCurController() {
        return curController;
    }

    public void setPlaying(boolean playing, SongDisplayCardController controller) {
        if (playing) {
            if(!MainActivity.isDownloaded(this)){
                download(false);
            }
            else{
                controller.notifyDownloaded();
                SongDisplayCardController.set(this);
                Player.start(Uri.parse("file://"+MainActivity.getDownloader().getPath() +"/"+getVideoID()+".mp3"),MainActivity.getDownloader().getApplication());
            }
        }
        isPlaying = playing;
    }
    public void download(Boolean toDownload){
        if(!MainActivity.isDownloaded(this)){
            curController.notifyDownloading();
            DownloadManager downloader = MainActivity.getDownloader();
            MainActivity.getDownloader().download(this,toDownload);
            this.setDownloaded(true);
        }
        else Toast.makeText(mainActivity, "Song is Already Downloaded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return Objects.equals(videoID, song.videoID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(videoID);
    }
}
