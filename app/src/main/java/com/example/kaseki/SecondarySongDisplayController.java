package com.example.kaseki;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class SecondarySongDisplayController {
    private View parent;
    private MainActivity mainActivity;
    private ImageButton playPauseButton;
    private TextView songNameTextView;
    private static final int PLAY_IMAGE=R.drawable.play;
    private static final int PAUSE_IMAGE=R.drawable.pause;
    private boolean paused=true;
    SecondarySongDisplayController(MainActivity mainActivity) {
        this.parent=mainActivity.findViewById(R.id.secondarySongDisplayInclude);
        this.mainActivity=mainActivity;
        playPauseButton=parent.findViewById(R.id.playPauseButton);
        songNameTextView=parent.findViewById(R.id.songNameTextViewSD);
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
}
