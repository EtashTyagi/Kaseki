package com.example.kaseki;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;

public class Utils {

    // The public static function which can be called from other classes
    public static void changeStatusBarColor(Activity activity, int color) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            activity.getWindow().setStatusBarColor(
                    darkenColor(
                            ContextCompat.getColor(activity, color)));
        }
    }
    public static void changeNavBarColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setNavigationBarColor(darkenColor(
                    ContextCompat.getColor(activity, color)));
        }
    }


    // Code to darken the color supplied (mostly color of toolbar)
    private static int darkenColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.8f;
        return Color.HSVToColor(hsv);
    }

    public static void setBitmapFromURL(String src, ImageView setImage, MainActivity mainActivity) {
        Thread thread = new Thread(() -> {
            try {
                URL url = new URL(src);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                mainActivity.runOnUiThread(()->setImage.setImageBitmap(BitmapFactory.decodeStream(input)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }
    public static boolean serializePlaylist(Vector<Playlist> object, String path) {
        try {
            FileOutputStream file = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(object);
            out.close();
            file.close();
            return true;
        } catch (Exception e)  {
            Log.d("SERIALIZATION", e.toString());
            return false;
        }
    }
    public static Vector<Playlist> deserializePlaylist(String filename) {
        Vector<Playlist> object;
        try {
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            object = (Vector<Playlist>) in.readObject();

            in.close();
            file.close();

        } catch(Exception e) {
            Log.d("Deserialized",e.toString());
            Playlist defaultPlay = new Playlist("Default");
            Vector<Playlist> playlists = new Vector<>();
            playlists.add(defaultPlay);
            return playlists;
        }
        return object;
    }
}

