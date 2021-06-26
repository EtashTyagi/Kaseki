package com.example.kaseki;

import android.util.Log;
import android.view.View;
import android.widget.*;

import java.util.Vector;

public class SongDisplayCardController {
    private View parent;
    private MainActivity mainActivity;
    private TextView songNameTextView;
    private ImageButton playPauseImageButton;
    private ImageButton download_ImageButton;
    private Song song;
    private static final int PLAY_BUTTON=R.drawable.play;
    private static final int PAUSE_BUTTON=R.drawable.pause;
    private boolean paused=true;
    private static SongDisplayCardController curPlaying=null;
    TextView song_text;
    SongDisplayCardController(MainActivity mainActivity, View parent, Song song) {
        this.mainActivity=mainActivity;
        this.parent=parent;
        this.song=song;
        song_text =parent.findViewById(R.id.songNameTextViewSD);
        songNameTextView=parent.findViewById(R.id.songNameTextViewSD);
        playPauseImageButton=parent.findViewById(R.id.playPauseImageButtonSD);
        download_ImageButton = parent.findViewById(R.id.donwload_button);
        songNameTextView.setSelected(true);
        download_ImageButton.setOnClickListener(v -> Download());
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
    public void Download(){
        Download_Manager downloader = MainActivity.getDownloader();
        downloader.download(song);
        song.setDownloaded(true);
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
        Download();
        curPlaying=this;
        playPauseImageButton.setImageResource(PAUSE_BUTTON);
        mainActivity.getSecondarySongDisplayController().play();
    }
    private void pause() {
        playPauseImageButton.setImageResource(PLAY_BUTTON);
        mainActivity.getSecondarySongDisplayController().pause();
    }
}
