package com.example.kaseki;

import android.net.Uri;
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
        download_ImageButton.setOnClickListener(v -> Download(true));
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
    public void Download(Boolean to_download){
        if(!check_for_download(song)){
            Download_Manager downloader = MainActivity.getDownloader();
            downloader.download(song,to_download);
            song.setDownloaded(true);
        }
        else Toast.makeText(mainActivity, "Song is Already Downloaded", Toast.LENGTH_SHORT).show();
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
        if(!check_for_download(song)) Download(false);
        else Player.start(Uri.parse("file://"+MainActivity.getDownloader().getPath() +"/"+song.getVideoID()+".mp3"),mainActivity.getApplication());
        curPlaying=this;
        playPauseImageButton.setImageResource(PAUSE_BUTTON);
        mainActivity.getSecondarySongDisplayController().play();
    }

    private boolean check_for_download(Song song){
        Vector<Playlist> playlists = MainActivity.getPlaylists();
        for(int i=0;i< playlists.size();i++){
            for(int j=0;j<playlists.get(i).getSongs().size();j++){
                if(playlists.get(i).getSongs().get(j).getVideoID().equals(song.getVideoID())) return true;
            }
        }
        return false;
    }

    private void pause() {
        playPauseImageButton.setImageResource(PLAY_BUTTON);
        mainActivity.getSecondarySongDisplayController().pause();
        Player.pause();
    }
}
