package com.example.kaseki;

import android.view.View;

public class PlaylistButtonController {
    MainActivity mainActivity;
    Playlist playlist;
    View parent;
    PlaylistButtonController(MainActivity mainActivity, View parent, Playlist playlist) {
        this.mainActivity=mainActivity;
        this.parent = parent;
        this.playlist=playlist;
        parent.findViewById(R.id.playlistNameButton).setOnClickListener((view)->{
            mainActivity.getPlaylistDisplayController().setPlaylist(this.playlist);
            mainActivity.changeMainDisplayController(PlaylistDisplayController.class);
        });
    }
}
