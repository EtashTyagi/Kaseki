package com.example.kaseki;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PlaylistDisplayController extends DisplayController{
    private RecyclerView songScroller;
    private SongDisplayAdapter scrollerAdapter;
    private TextView heading;
    private Playlist playlist;
    PlaylistDisplayController(Playlist playlist) {
        super();
        this.parent=this.parent.findViewById(R.id.playlistDisplayInclude);
        heading=parent.findViewById(R.id.playlist_name);
        heading.setText(playlist.getName());
        songScroller = parent.findViewById(R.id.songScroller);
        scrollerAdapter = new SongDisplayAdapter();
        this.playlist=playlist;
        final LinearLayoutManager layoutManager = new LinearLayoutManager(mainActivity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        songScroller.setLayoutManager(layoutManager);
        songScroller.setAdapter(scrollerAdapter);
        populateSongs();
    }
    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
        scrollerAdapter.clear();
        this.heading.setText(playlist.getName());
        populateSongs();
    }
    private void populateSongs() {
        for (Song s :
                playlist.getSongs()) {
            Log.d("PLAYLIST", s.getSongName());
            scrollerAdapter.addElement(s);
        }
    }
}
