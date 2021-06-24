package com.example.kaseki;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;

public class SongDisplayAdapter extends RecyclerView.Adapter<SongDisplayAdapter.SongDisplayHolder> {
    private ArrayList<Song> myList;
    private HashSet<String> contains;
    private MainActivity mainActivity;
    int mLastPosition = 0;
    public SongDisplayAdapter(MainActivity mainActivity) {
        this.myList = new ArrayList<>();
        this.mainActivity=mainActivity;
    }
    @NonNull
    public SongDisplayHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_display_card, parent, false);
        return new SongDisplayHolder(view);
    }
    @Override
    public void onBindViewHolder(SongDisplayHolder holder, final int position) {
        holder.songNameTextView.setText(myList.get(position).getSongName());
        holder.artistNameTextView.setText(myList.get(position).getArtist());
        //TODO: IMAGE
        Picasso.get().load(myList.get(position).getThumbnailPath()).into(holder.songImage);
        mLastPosition =position;
    }
    @Override
    public int getItemCount() {
        return(null != myList?myList.size():0);
    }
    public void addElement(Song element) {
        if (!contains.contains(element.getVideoID())) {
            myList.add(element);
            contains.add(element.getVideoID());
            notifyDataSetChanged();
        }
    }
    public void clear() {
        myList=new ArrayList<>();
        contains=new HashSet<>();
        mLastPosition=0;
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