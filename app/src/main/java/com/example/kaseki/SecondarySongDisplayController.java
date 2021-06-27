package com.example.kaseki;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

public class SecondarySongDisplayController {
    private View parent;
    private MainActivity mainActivity;
    private ImageButton playPauseButton;
    private TextView songNameTextView;
    private TextView artistTextView;
    private ImageView songImage;
    private ProgressBar songProgressBar;
    private static final int PLAY_IMAGE=R.drawable.play;
    private static final int PAUSE_IMAGE=R.drawable.pause;
    private final int WRAP_HEIGHT;
    private static Song curPlaying;

    private boolean paused=true;

    SecondarySongDisplayController() {
        this.mainActivity=MainActivity.getCurrentInstance();
        this.parent=mainActivity.findViewById(R.id.secondarySongDisplayInclude);
        curPlaying=null;
        WRAP_HEIGHT=parent.getLayoutParams().height;
        collapse();
        songProgressBar=parent.findViewById(R.id.songProgressBar);
        playPauseButton=parent.findViewById(R.id.playPauseButton);
        songNameTextView=parent.findViewById(R.id.songNameTextViewSD);
        artistTextView=parent.findViewById(R.id.artistTextViewSD);
        songImage=parent.findViewById(R.id.songImageSD);
        songNameTextView.setSelected(true);
        playPauseButton.setOnClickListener(this::onClick);
        songNameTextView.setOnClickListener(this::onClick);
        artistTextView.setOnClickListener(this::onClick);
        songImage.setOnClickListener(this::onClick);
    }

    public void onClick(View view) {
        if (view==playPauseButton) {
            if (paused) {
                play(curPlaying);
            } else {
                pause();
            }
            mainActivity.getPrimarySongDisplayController().changePlayPauseButton(!paused);
        } else if (view==songImage || view==artistTextView || view==songNameTextView) {
            mainActivity.getPrimarySongDisplayController().changePlayPauseButton(!paused);
            mainActivity.getPrimarySongDisplayController().setToSong(curPlaying,this);
            collapse();
            mainActivity.changeMainDisplayController(PrimarySongDisplayController.class);
        }
    }

    public void play(Song song) {
        playPauseButton.setImageResource(PAUSE_IMAGE);
        parent.getLayoutParams().height=WRAP_HEIGHT;
        Player.resume();
        curPlaying=song;
        paused=false;
    }

    public void pause() {
        playPauseButton.setImageResource(PLAY_IMAGE);
        parent.getLayoutParams().height=WRAP_HEIGHT;
        Player.pause();
        paused=true;
    }

    public void setToSong(Song song) {
        parent.setVisibility(View.VISIBLE);
        curPlaying=song;
        songNameTextView.setText(song.getSongName());
        artistTextView.setText(song.getArtist());
        if (song.isDownloaded()) {
            Picasso.get().load("file:"+song.getThumbnailPath()).into(songImage);
            Log.d("IMAGE_LOAD", "file:"+song.getThumbnailPath());
        } else {
            Picasso.get().load(song.getThumbnailPath()).into(songImage);
        }
    }
    public View getParent() {
        return parent;
    }

    public ProgressBar getSongProgressBar() {
        return songProgressBar;
    }

    public Song getCurPlaying(){
        return curPlaying;
    }

    public boolean isPaused() {
        return paused;
    }

    public ImageButton getPlayPauseButton() {
        return playPauseButton;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public void collapse() {
        parent.getLayoutParams().height=1;
        parent.setVisibility(View.INVISIBLE);
    }
    public void relapse() {
        parent.getLayoutParams().height=WRAP_HEIGHT;
        parent.setVisibility(View.VISIBLE);
    }
}
