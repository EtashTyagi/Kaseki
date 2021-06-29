package com.example.kaseki;

import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.File;

public class SongDisplayCardController extends RecyclerView.ViewHolder {
    private View parent;
    private MainActivity mainActivity;
    private TextView songNameTextView;
    private TextView artistTextView;
    private ImageView songImageContainer;
    private ImageButton downloadDeleteButton;
    private TextView downloadProgress;
    private Song song;
    private static float TEXT_SIZE;
    SongDisplayCardController(View parent) {
        super(parent);
        this.mainActivity=MainActivity.getCurrentInstance();
        this.parent=parent;
        songNameTextView=parent.findViewById(R.id.songNameTextViewSD);
        songImageContainer=parent.findViewById(R.id.songImageSD);
        artistTextView=parent.findViewById(R.id.artistTextViewSD);
        downloadDeleteButton = parent.findViewById(R.id.donwload_delete_button);
        downloadProgress=parent.findViewById(R.id.percentDownload);
        TEXT_SIZE=downloadProgress.getTextSize()/2.5f;
        collapseDownloadProgress();
        songNameTextView.setSelected(true);
        downloadDeleteButton.setOnClickListener(v -> song.download(true));
        songNameTextView.setOnClickListener(this::play);
        artistTextView.setOnClickListener(this::play);
        songImageContainer.setOnClickListener(this::play);
    }
    public void notifyFailedDownload() {
        collapseDownloadProgress();
        downloadDeleteButton.setImageResource(R.drawable.outline_download_white_48);
        downloadDeleteButton.setOnClickListener(v -> song.download(true));
    }
    public void notifyDownloading() {
        Glide.with(mainActivity).load(R.drawable.loading).into(downloadDeleteButton);
        downloadDeleteButton.setOnClickListener((v)->{});
    }
    private void play(View view) {
        song.setPlaying(true, this);
    }
    public void collapseDownloadProgress() {
        downloadProgress.setText("0%");
        downloadProgress.setTextSize(0);
    }
    public void relapseDownloadProgress(float percentDownload) {
        downloadProgress.setTextSize(TEXT_SIZE);
        downloadProgress.setText(String.format("%s%%", percentDownload));
    }
    public void notifyDownloaded() {
        collapseDownloadProgress();
        downloadDeleteButton.setImageResource(R.drawable.outline_delete_white_48);
        downloadDeleteButton.setOnClickListener(this::deleteSong);
    }
    public void deleteSong(View v) {
        File toDelete = new File(MainActivity.getDownloader().getPath() + "/"+song.getVideoID()+".mp3");
        MainActivity.removeSong(song);
        song.setDownloaded(false);
        if(!toDelete.delete()){
            MainActivity.getCurrentInstance().runOnUiThread(
                    ()->Toast.makeText(MainActivity.getCurrentInstance(),
                            "Delete Failed", Toast.LENGTH_SHORT).show());
        }
        downloadDeleteButton.setImageResource(R.drawable.outline_download_white_48);
        downloadDeleteButton.setOnClickListener((view)->song.download(true));
    }
    public void bindSong(Song song) {
        this.song=song;
        song.setCurController(this);
        songNameTextView.setText(song.getSongName());
        artistTextView.setText(song.getArtist());
        if (MainActivity.isDownloaded(song)) {
            notifyDownloaded();
            Picasso.get().load("file:"+song.getThumbnailPath()).into(songImageContainer);
        } else if (MainActivity.getDownloader().isDownloading(song)){
            notifyDownloading();
            Picasso.get().load(song.getThumbnailPath()).into(songImageContainer);
        } else {
            notifyFailedDownload();
            Picasso.get().load(song.getThumbnailPath()).into(songImageContainer);
        }
    }
    public static void set(Song song){
        MainActivity.getCurrentInstance().getSecondarySongDisplayController().setToSong(song);
        MainActivity.getCurrentInstance().getSecondarySongDisplayController().play(song);
    }

}
