package com.example.kaseki;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class SongDisplayAdapter extends RecyclerView.Adapter<SongDisplayCardController> {
    private ArrayList<Song> myList;
    private HashSet<String> contains;
    private MainActivity mainActivity;
    int mLastPosition = 0;
    public SongDisplayAdapter() {
        this.mainActivity=MainActivity.getCurrentInstance();
        this.myList = new ArrayList<>();
        this.contains = new HashSet<>();
    }
    @NonNull
    public SongDisplayCardController onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_display_card, parent, false);
        return new SongDisplayCardController(view);
    }
    @Override
    public void onBindViewHolder(SongDisplayCardController holder, final int position) {
        holder.bindSong(myList.get(position));
        mLastPosition =position;
    }
    @Override
    public int getItemCount() {
        return(null != myList?myList.size():0);
    }
    public void addElement(Song element) {
        if (!contains.contains(element.getVideoID())) {
            myList.add(MainActivity.getDownloader().getPreviousDownloadingStatus(element));
            contains.add(element.getVideoID());
            notifyDataSetChanged();
        }
    }
    public void clear() {
        for (Song s : myList) {
            s.setCurController(null);
        }
        myList=new ArrayList<>();
        contains=new HashSet<>();
        mLastPosition=0;
        notifyDataSetChanged();
    }
}