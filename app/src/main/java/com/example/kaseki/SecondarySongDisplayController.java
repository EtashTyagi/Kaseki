package com.example.kaseki;

import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class SecondarySongDisplayController {
    View parent;
    MainActivity mainActivity;
    ImageButton playPauseButton;
    boolean paused=true;
    SecondarySongDisplayController(MainActivity mainActivity) {
        this.parent=mainActivity.findViewById(R.id.secondarySongDisplayInclude);
        this.mainActivity=mainActivity;
        playPauseButton=parent.findViewById(R.id.playPauseButton);
        playPauseButton.setOnClickListener(this::onClick);
    }
    private void onClick(View view) {
        if (view==playPauseButton) {
            if (paused) {
                playPauseButton.setImageResource(R.drawable.outline_pause_white_48);
            } else {
                playPauseButton.setImageResource(R.drawable.outline_play_arrow_white_48);
            }
            paused=!paused;
        }
    }
}
