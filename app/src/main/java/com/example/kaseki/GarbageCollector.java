package com.example.kaseki;

import android.util.Log;

import javax.crypto.spec.DESedeKeySpec;
import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

public class GarbageCollector {
    public static void collectGarbage(String path){
        Vector<String> songs = generateSongs();
        Vector<String> thumbnails = generateThumbnails();
        clearThumbnails(path,thumbnails);
        clearSongs(songs);
    }

    private static void clearThumbnails(String path, Vector<String> thumbnails){
        File directory = new File(path + "/thumbnails");
        File[] files = directory.listFiles();
        ArrayList<File> delete = new ArrayList<>();
        for (int i = 0; i < files.length; i++)
        {
            if(!thumbnails.contains(files[i].getName()))
                delete.add(files[i]);
        }
        for(int i=0;i<delete.size();i++)
            delete.get(i).delete();
    }

    private static void clearSongs(Vector<String> songs){
        File directory = new File(MainActivity.getDownloader().getPath());
        File[] files = directory.listFiles();
        ArrayList<File> delete = new ArrayList<>();
        if(files == null) return;
        for (int i = 0; i < files.length; i++)
        {
            if(!songs.contains(files[i].getName()))
                delete.add(files[i]);
        }
        for(int i=0;i<delete.size();i++)
            delete.get(i).delete();
        directory = new File(MainActivity.getDownloader().getPath());
        File[] files1 = directory.listFiles();
        for (int i = 0; i < files1.length; i++)
        {
            Log.d("Songs",files1[i].getName());
        }

    }
    private static Vector<String> generateSongs(){
        Vector<String> songs = new Vector<>();
        Vector<Playlist> playlist = MainActivity.getPlaylists();
        for(int i=0;i<playlist.size();i++){
            for (int j=0;j<playlist.get(i).getSongs().size();j++){
                songs.add(playlist.get(i).getSongs().get(j).getVideoID()+".mp3");
            }
        }
        return songs;
    }

    private static Vector<String> generateThumbnails(){
        Vector<String> songs = new Vector<>();
        Vector<Playlist> playlist = MainActivity.getPlaylists();
        for(int i=0;i<playlist.size();i++){
            for (int j=0;j<playlist.get(i).getSongs().size();j++){
                songs.add(playlist.get(i).getSongs().get(j).getVideoID()+".jpg");
            }
        }
        return songs;
    }

}
