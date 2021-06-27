package com.example.kaseki;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Vector;

public class PrimarySongDisplayController extends DisplayController{
    // Variables - mainActivity and parent-use find on parent
    private ImageView songImage;
    private ImageView prevButton;
    private ImageView nextButton;
    private ImageView playPauseButton;
    private TextView playlistTitle;
    private TextView songName;
    private static SeekBar seekBar;
    private static final int PLAY_IMAGE=R.drawable.play;
    private static final int PAUSE_IMAGE=R.drawable.pause;
    private SecondarySongDisplayController secondarySongDisplayController;

    PrimarySongDisplayController() {
        this.parent=  this.parent.findViewById(R.id.primarySongDisplayInclude);
        songImage = parent.findViewById(R.id.songImageP);
        prevButton = parent.findViewById(R.id.previousSongButton);
        nextButton = parent.findViewById(R.id.nextSongButton);
        playPauseButton = parent.findViewById(R.id.playPauseButtonP);
        playlistTitle = parent.findViewById(R.id.playlistTitle);
        songName = parent.findViewById(R.id.songNameP);
        seekBar = parent.findViewById(R.id.seekBar);
        seekBar.getProgressDrawable().setColorFilter(parent.getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(Player.getPlayer() != null && fromUser){
                    Player.getPlayer().seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });

        playPauseButton.setOnClickListener(v -> {
            boolean paused = secondarySongDisplayController.isPaused();
            if (paused) {
                Player.resume();
                secondarySongDisplayController.getPlayPauseButton().setImageResource(PAUSE_IMAGE);
                playPauseButton.setImageResource(PAUSE_IMAGE);
            } else {
                Player.pause();
                secondarySongDisplayController.getPlayPauseButton().setImageResource(PLAY_IMAGE);
                playPauseButton.setImageResource(PLAY_IMAGE);
            }
            secondarySongDisplayController.setPaused(!paused);
        });

        prevButton.setOnClickListener(v -> giveIndex(-1));

        nextButton.setOnClickListener(v -> {
            Vector<Playlist> playlist = MainActivity.getPlaylists();
            Song curr = secondarySongDisplayController.getCurPlaying();
            giveIndex(1);
        });
    }
    public void setToSong(Song song,SecondarySongDisplayController secondarySongDisplayController) {
        this.secondarySongDisplayController = secondarySongDisplayController;
        seekBar.setMax(Player.getPlayer().getDuration());
        if (song.isDownloaded()) {
            Picasso.get().load(new File(song.getThumbnailPath())).into(songImage);
        } else {
            Picasso.get().load(song.getThumbnailPath()).into(songImage);
        }
        songName.setText(song.getSongName());
    }
    public void giveIndex(int next_or_prev){
        Vector<Playlist> playlist = MainActivity.getPlaylists();
        Song curr = secondarySongDisplayController.getCurPlaying();
        int arr[] = new int[2];
        arr[0] = -1;
        arr[1] = -1;
        for(int i=0;i<playlist.size();i++){
            for(int j=0;j<playlist.get(i).getSongs().size();j++){
                if(playlist.get(i).getSongs().get(j).getVideoID().equals(curr.getVideoID())){
                    arr[0] = i;
                    arr[1] = j;
                }
            }
        }
        if(arr[0] == -1){
            Toast.makeText(mainActivity, "This song is not in your Playlist", Toast.LENGTH_SHORT).show();
            return;
        }
        int size = playlist.get(arr[0]).getSongs().size();
        int index = ((arr[1] + next_or_prev)%size + size)%size;
        Song next = playlist.get(arr[0]).getSongs().get(index);
        secondarySongDisplayController.setToSong(next);
        setToSong(next, secondarySongDisplayController);
        Uri uri = Uri.parse("file://"+MainActivity.getDownloader().getPath() +"/" +next.getVideoID()+".mp3");
        Player.start(uri,MainActivity.getDownloader().getApplication());
    }

    public void changePlayPauseButton(boolean paused) {
        if (paused) {
            playPauseButton.setImageResource(PAUSE_IMAGE);
        } else {
            playPauseButton.setImageResource(PLAY_IMAGE);
        }
    }
    public static SeekBar getSeekBar(){
        return seekBar;
    }
}
