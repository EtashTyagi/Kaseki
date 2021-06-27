package com.example.kaseki;

import android.net.Uri;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Objects;
import java.util.Vector;

public class Song implements Serializable {
    private String videoID;
    private String songName;
    private String thumbnailPath;
    private String artist;
    private transient MainActivity mainActivity;
    private boolean isDownloaded;
    private transient boolean isPlaying;

    Song(MainActivity mainActivity) {
        this.mainActivity=mainActivity;
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

    public void setPlaying(boolean playing) {
        if (playing) {
            if(!checkIfDownloaded()) download(false);
            else Player.start(Uri.parse("file://"+MainActivity.getDownloader().getPath() +"/"+getVideoID()+".mp3"),MainActivity.getDownloader().getApplication());
        }
        isPlaying = playing;
    }
    public void download(Boolean to_download){
        if(!checkIfDownloaded()){
            Download_Manager downloader = MainActivity.getDownloader();
            downloader.download(this,to_download);
            this.setDownloaded(true);
        }
        else Toast.makeText(mainActivity, "Song is Already Downloaded", Toast.LENGTH_SHORT).show();
    }
    private boolean checkIfDownloaded(){
        Vector<Playlist> playlists = MainActivity.getPlaylists();
        for(int i=0;i< playlists.size();i++){
            for(int j=0;j<playlists.get(i).getSongs().size();j++){
                if(playlists.get(i).getSongs().get(j).getVideoID().equals(getVideoID())) return true;
            }
        }
        return false;
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
