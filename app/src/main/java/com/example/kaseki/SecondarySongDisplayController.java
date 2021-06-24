package com.example.kaseki;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

public class SecondarySongDisplayController {
    private View parent;
    private MainActivity mainActivity;
    private ImageButton playPauseButton;
    private TextView songNameTextView;
    private TextView artistTextView;
    private ImageView songImage;
    private static final int PLAY_IMAGE=R.drawable.play;
    private static final int PAUSE_IMAGE=R.drawable.pause;
    private boolean paused=true;
    SecondarySongDisplayController(MainActivity mainActivity) {
        this.parent=mainActivity.findViewById(R.id.secondarySongDisplayInclude);
        this.mainActivity=mainActivity;
        parent.setVisibility(View.INVISIBLE);
        playPauseButton=parent.findViewById(R.id.playPauseButton);
        songNameTextView=parent.findViewById(R.id.songNameTextViewSD);
        artistTextView=parent.findViewById(R.id.artistTextViewSD);
        songImage=parent.findViewById(R.id.songImageSD);
        songNameTextView.setSelected(true);
        playPauseButton.setOnClickListener(this::onClick);
    }
    private void onClick(View view) {
        if (view==playPauseButton) {
            if (paused) {
                playPauseButton.setImageResource(PAUSE_IMAGE);
                SongDisplayCardController.playCurPlaying();
            } else {
                playPauseButton.setImageResource(PLAY_IMAGE);
                SongDisplayCardController.pauseCurPlaying();
            }
            paused=!paused;
        }
    }
    public void play() {
        playPauseButton.setImageResource(PAUSE_IMAGE);
        paused=false;
    }
    public void pause() {
        playPauseButton.setImageResource(PLAY_IMAGE);
        paused=true;
    }
    public void setToSong(Song song) {
        parent.setVisibility(View.VISIBLE);
        songNameTextView.setText(song.getSongName());
        artistTextView.setText(song.getArtist());
        Picasso.get().load(song.getThumbnailPath()).into(songImage);
    }
}
