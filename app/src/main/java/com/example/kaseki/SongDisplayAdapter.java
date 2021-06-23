package com.example.kaseki;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class SongDisplayAdapter extends RecyclerView.Adapter<SongDisplayAdapter.SongDisplayHolder> {
    private ArrayList<Song> myList;
    int mLastPosition = 0;
    public SongDisplayAdapter(ArrayList<Song> myList) {
        this.myList = myList;
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
//        try {
//            File f=new File(myList.get(position).getThumbnailPath());
//            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
//            holder.songImage.setImageBitmap(b);
//        }
//        catch (FileNotFoundException e)
//        {
//            e.printStackTrace();
//        }
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