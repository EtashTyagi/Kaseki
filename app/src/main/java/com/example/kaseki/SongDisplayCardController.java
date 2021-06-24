package com.example.kaseki;

import android.view.View;
import android.widget.*;

public class SongDisplayCardController {
    private View parent;
    private MainActivity mainActivity;
    private TextView songNameTextView;
    private ImageButton playPauseImageButton;
    private Song song;
    private static final int PLAY_BUTTON=R.drawable.play;
    private static final int PAUSE_BUTTON=R.drawable.pause;
    private boolean paused=true;
    private static SongDisplayCardController curPlaying=null;
    SongDisplayCardController(MainActivity mainActivity, View parent, Song song) {
        this.mainActivity=mainActivity;
        this.parent=parent;
        this.song=song;
        songNameTextView=parent.findViewById(R.id.songNameTextViewSD);
        playPauseImageButton=parent.findViewById(R.id.playPauseImageButtonSD);
        songNameTextView.setSelected(true);
        playPauseImageButton.setOnClickListener((view)->{
            if (paused) {
                play();
            } else {
                pause();
            }
            mainActivity.getSecondarySongDisplayController().setToSong(song);
            paused=!paused;
        });
    }
    public static void pauseCurPlaying() {
        if (curPlaying!=null) {
            curPlaying.playPauseImageButton.setImageResource(PLAY_BUTTON);
            curPlaying.paused=true;
        }
    }
    public static void playCurPlaying() {
        if (curPlaying!=null) {
            curPlaying.playPauseImageButton.setImageResource(PAUSE_BUTTON);
            curPlaying.paused=false;
        }
    }
    private void play() {
        if (curPlaying!=null) {
            curPlaying.playPauseImageButton.setImageResource(PLAY_BUTTON);
            curPlaying.paused=true;
        }
        curPlaying=this;
        playPauseImageButton.setImageResource(PAUSE_BUTTON);
        mainActivity.getSecondarySongDisplayController().play();
    }
    private void pause() {
        playPauseImageButton.setImageResource(PLAY_BUTTON);
        mainActivity.getSecondarySongDisplayController().pause();
    }
}
