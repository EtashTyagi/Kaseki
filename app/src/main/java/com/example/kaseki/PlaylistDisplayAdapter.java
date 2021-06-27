package com.example.kaseki;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class PlaylistDisplayAdapter extends RecyclerView.Adapter<PlaylistDisplayHolder> {
    private ArrayList<Playlist> myList;
    private HashSet<String> contains;
    private HashMap<Playlist, PlaylistButtonController> playlistToControllers;
    private MainActivity mainActivity;
    int mLastPosition = 0;
    public PlaylistDisplayAdapter() {
        this.mainActivity=MainActivity.getCurrentInstance();
        this.myList = new ArrayList<>();
        playlistToControllers =new HashMap<>();
        contains=new HashSet<>();
    }
    @NonNull
    public PlaylistDisplayHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_card, parent, false);
        return new PlaylistDisplayHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull PlaylistDisplayHolder holder, int position) {
        holder.getPlaylistNameButton().setText(myList.get(position).getName());
        //Add Controller
        if (!playlistToControllers.containsKey(myList.get(position))) {
            playlistToControllers.put(myList.get(position),
                    new PlaylistButtonController(holder.getParent(), myList.get(position)));
        }
        mLastPosition =position;
    }
    @Override
    public int getItemCount() {
        return(null != myList?myList.size():0);
    }
    public void addElement(Playlist element) {
        if (!contains.contains(element.getName())) {
            myList.add(element);
            contains.add(element.getName());
            notifyDataSetChanged();
        }
    }
    public void clear() {
        myList=new ArrayList<>();
        contains=new HashSet<>();
        mLastPosition=0;
        notifyDataSetChanged();
        playlistToControllers =new HashMap<>();
    }
}