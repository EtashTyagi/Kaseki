package com.example.kaseki;

import android.view.View;
import android.widget.*;

public class SongDisplayCardController {
    private View parent;
    private MainActivity mainActivity;
    private TextView songNameTextView;
    private TextView artistTextView;
    private ImageView songImageContainer;
    private ImageButton download_ImageButton;
    private Song song;
    TextView song_text;
    SongDisplayCardController(View parent, Song song) {
        this.mainActivity=MainActivity.getCurrentInstance();
        this.parent=parent;
        this.song=song;
        song_text =parent.findViewById(R.id.songNameTextViewSD);
        songNameTextView=parent.findViewById(R.id.songNameTextViewSD);
        songImageContainer=parent.findViewById(R.id.songImageSD);
        artistTextView=parent.findViewById(R.id.artistTextViewSD);
        download_ImageButton = parent.findViewById(R.id.donwload_button);
        songNameTextView.setSelected(true);
        download_ImageButton.setOnClickListener(v -> song.download(true));
        songNameTextView.setOnClickListener(this::play);
        artistTextView.setOnClickListener(this::play);
        songImageContainer.setOnClickListener(this::play);
    }

    private void play(View view) {
        song.setPlaying(true);
        mainActivity.getSecondarySongDisplayController().setToSong(song);
        mainActivity.getSecondarySongDisplayController().play(song);
        mainActivity.getSecondarySongDisplayController().getSongProgressBar().setMax(Player.getPlayer().getDuration());
    }
}
