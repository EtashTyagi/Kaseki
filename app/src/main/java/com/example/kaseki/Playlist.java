package com.example.kaseki;

import java.io.Serializable;
import java.util.Vector;

public class Playlist implements Serializable {
    private Vector<Song> songs;
    private  String name;
    Playlist(String name){
        this.name = name;
        this.songs = new Vector<>();
    }

    public Vector<Song> getSongs() {
        return songs;
    }
    public void insertSong(Song s) {
        songs.add(s);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
