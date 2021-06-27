package com.example.kaseki;

import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.io.File;

public class PrimarySongDisplayController extends DisplayController{
    // Variables - mainActivity and parent-use find on parent
    private ImageView songImage;
    private ImageView prevButton;
    private ImageView nextButton;
    private ImageView playPauseButton;
    private TextView playlistTitle;
    private TextView songName;
    PrimarySongDisplayController() {
        this.parent=this.parent.findViewById(R.id.primarySongDisplayInclude);
        songImage=parent.findViewById(R.id.songImageP);
        prevButton=parent.findViewById(R.id.previousSongButton);
        nextButton=parent.findViewById(R.id.nextSongButton);
        playPauseButton=parent.findViewById(R.id.playPauseButtonP);
        playlistTitle=parent.findViewById(R.id.playlistTitle);
        songName=parent.findViewById(R.id.songNameP);
    }
    public void setToSong(Song song) {
        if (song.isDownloaded()) {
            Picasso.get().load(new File(song.getThumbnailPath())).into(songImage);
        } else {
            Picasso.get().load(song.getThumbnailPath()).into(songImage);
        }
        songName.setText(song.getSongName());
    }
}
