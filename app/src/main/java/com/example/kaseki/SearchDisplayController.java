package com.example.kaseki;

import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchDisplayController extends DisplayController {
    private SongDisplayAdapter scrollerAdapter;
    private ArrayList<Song> searchList;

    SearchDisplayController(MainActivity mainActivity, SongDisplayAdapter scrollerAdapter, ArrayList<Song> searchList) {
        super(mainActivity);
        this.parent=this.parent.findViewById(R.id.searchDisplayInclude);
        this.searchList=searchList;
        this.scrollerAdapter=scrollerAdapter;
        for (int i = 0; i < 10; i++) {
            Song mLog = new Song();
            mLog.setSongName("SUMTHING");
            mLog.setVideoID("XX");
            searchList.add(mLog);
            scrollerAdapter.notifyData(searchList);
        }

    }
}
