package com.example.kaseki;

import android.view.View;
import android.widget.*;
import androidx.recyclerview.widget.RecyclerView;

class PlaylistDisplayHolder extends RecyclerView.ViewHolder {
    private final Button playlistNameButton;
    private View parent;
    public PlaylistDisplayHolder(final View parent) {
        super(parent);
        this.parent=parent;
        playlistNameButton = parent.findViewById(R.id.playlistNameButton);
        LinearLayout mainLayout = parent.findViewById(R.id.songDisplayMainLayout);
//        mainLayout.setOnClickListener(v ->
//                Toast.makeText(itemView.getContext(), "Position:" + getAdapterPosition(), Toast.LENGTH_SHORT).show());
    }
    public Button getPlaylistNameButton() {
        return playlistNameButton;
    }
    public View getParent() {
        return parent;
    }
}
