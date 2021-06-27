package com.example.kaseki;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private Song curPlaying;
    private int WRAP_HEIGHT;
    private boolean paused=true;
    SecondarySongDisplayController(MainActivity mainActivity) {
        this.parent=mainActivity.findViewById(R.id.secondarySongDisplayInclude);
        this.mainActivity=mainActivity;
        curPlaying=new Song(mainActivity);
        WRAP_HEIGHT=parent.getLayoutParams().height;
        parent.getLayoutParams().height=1;
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
            } else {
                playPauseButton.setImageResource(PLAY_IMAGE);
            }
            paused=!paused;
        }
    }
    public void play(Song song) {
        playPauseButton.setImageResource(PAUSE_IMAGE);
        parent.getLayoutParams().height=WRAP_HEIGHT;
        curPlaying=song;
        Player.resume();
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
        songNameTextView.setText(song.getSongName());
        artistTextView.setText(song.getArtist());
        Picasso.get().load(song.getThumbnailPath()).into(songImage);
    }
}
