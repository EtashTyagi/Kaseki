package com.example.kaseki;

import android.view.View;
import android.widget.*;
import androidx.recyclerview.widget.RecyclerView;

class SongDisplayHolder extends RecyclerView.ViewHolder {
    private final TextView songNameTextView;
    private final TextView artistNameTextView;
    private ImageView songImage;
    private ImageButton downloadButton;
    private View parent;
    public SongDisplayHolder(final View parent) {
        super(parent);
        this.parent=parent;
        songNameTextView = parent.findViewById(R.id.songNameTextViewSD);
        artistNameTextView= parent.findViewById(R.id.artistTextViewSD);
        songImage = parent.findViewById(R.id.songImageSD);
        downloadButton=parent.findViewById(R.id.donwload_button);
        LinearLayout mainLayout = parent.findViewById(R.id.songDisplayMainLayout);
//        mainLayout.setOnClickListener(v ->
//                Toast.makeText(itemView.getContext(), "Position:" + getAdapterPosition(), Toast.LENGTH_SHORT).show());
    }
    public TextView getSongNameTextView() {
        return songNameTextView;
    }

    public TextView getArtistNameTextView() {
        return artistNameTextView;
    }

    public ImageView getSongImage() {
        return songImage;
    }

    public View getParent() {
        return parent;
    }

    public ImageButton getDownloadButton() {
        return downloadButton;
    }
}
