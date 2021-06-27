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

public class SongDisplayAdapter extends RecyclerView.Adapter<SongDisplayHolder> {
    private ArrayList<Song> myList;
    private HashSet<String> contains;
    private HashMap<Song, SongDisplayCardController> songToControllers;
    private MainActivity mainActivity;
    int mLastPosition = 0;
    public SongDisplayAdapter() {
        this.mainActivity=MainActivity.getCurrentInstance();
        this.myList = new ArrayList<>();
        songToControllers=new HashMap<>();
        this.contains = new HashSet<>();
    }
    @NonNull
    public SongDisplayHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_display_card, parent, false);
        return new SongDisplayHolder(view);
    }
    @Override
    public void onBindViewHolder(SongDisplayHolder holder, final int position) {
        holder.getSongNameTextView().setText(myList.get(position).getSongName());
        holder.getArtistNameTextView().setText(myList.get(position).getArtist());
        if (myList.get(position).isDownloaded()) {
            Picasso.get().load("file:"+myList.get(position).getThumbnailPath()).into(holder.getSongImage());
            Log.d("IMAGE_LOAD", "file:"+myList.get(position).getThumbnailPath());
        } else {
            Picasso.get().load(myList.get(position).getThumbnailPath()).into(holder.getSongImage());
        }
        if (!songToControllers.containsKey(myList.get(position))) {
            songToControllers.put(myList.get(position),
                    new SongDisplayCardController(holder.getParent(), myList.get(position)));
        }
        if (MainActivity.isDownloaded(myList.get(position))) {
            holder.getDownloadButton().setLayoutParams(new LinearLayout.LayoutParams(0, 0));
        }
        mLastPosition =position;
    }
    @Override
    public int getItemCount() {
        return(null != myList?myList.size():0);
    }
    public void addElement(Song element) {
        if (!contains.contains(element.getVideoID())) {
            myList.add(element);
            contains.add(element.getVideoID());
            notifyDataSetChanged();
        }
    }
    public void clear() {
        myList=new ArrayList<>();
        contains=new HashSet<>();
        mLastPosition=0;
        notifyDataSetChanged();
        songToControllers=new HashMap<>();
    }
}