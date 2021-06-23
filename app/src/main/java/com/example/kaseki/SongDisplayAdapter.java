package com.example.kaseki;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SongDisplayAdapter extends RecyclerView.Adapter<SongDisplayAdapter.SongDisplayHolder> {
    private ArrayList<Song> myList;
    private MainActivity mainActivity;
    int mLastPosition = 0;
    public SongDisplayAdapter(ArrayList<Song> myList, MainActivity mainActivity) {
        this.myList = myList;
        this.mainActivity=mainActivity;
    }
    public SongDisplayHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_display_card, parent, false);
        return new SongDisplayHolder(view);
    }
    @Override
    public void onBindViewHolder(SongDisplayHolder holder, final int position) {
        holder.songNameTextView.setText(myList.get(position).getSongName());
        holder.artistNameTextView.setText(myList.get(position).getVideoID());
        //TODO: IMAGE
//        Utils.setBitmapFromURL(myList.get(position).getThumbnailPath(), holder.songImage, mainActivity);
        mLastPosition =position;
    }
    @Override
    public int getItemCount() {
        return(null != myList?myList.size():0);
    }
    public void notifyData(ArrayList<Song> myList) {
        this.myList = myList;
        notifyDataSetChanged();
    }
    static class SongDisplayHolder extends RecyclerView.ViewHolder {
        private final TextView songNameTextView;
        private final TextView artistNameTextView;
        public ImageView songImage;
        public SongDisplayHolder(final View parent) {
            super(parent);
            songNameTextView = parent.findViewById(R.id.songNameTextViewSD);
            artistNameTextView= parent.findViewById(R.id.artistTextViewSD);
            songImage = parent.findViewById(R.id.songImageSD);
            LinearLayout mainLayout = parent.findViewById(R.id.songDisplayMainLayout);
            mainLayout.setOnClickListener(v ->
                    Toast.makeText(itemView.getContext(), "Position:" + getAdapterPosition(), Toast.LENGTH_SHORT).show());
        }
    }
}