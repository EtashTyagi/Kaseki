package com.example.kaseki;

import android.view.View;
import android.view.ViewStub;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Vector;

public class LibraryDisplayController extends DisplayController{
    PlaylistDisplayAdapter playlistDisplayAdapter;
    Vector<Playlist> playlists;
    RecyclerView songDisplay;
    LibraryDisplayController(PlaylistDisplayAdapter playlistDisplayAdapter, Vector<Playlist> playlists) {
        super();
        this.parent=this.parent.findViewById(R.id.libraryDisplayInclude);
        this.playlistDisplayAdapter = playlistDisplayAdapter;
        this.playlists=playlists;
        this.songDisplay=parent.findViewById(R.id.songScroller);
        for (Playlist p : playlists) { playlistDisplayAdapter.addElement(p); }
    }

}
