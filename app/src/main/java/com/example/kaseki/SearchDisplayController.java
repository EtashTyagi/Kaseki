package com.example.kaseki;

import android.os.CountDownTimer;
import android.util.Log;
import android.widget.*;

import java.util.ArrayList;

public class SearchDisplayController extends DisplayController {
    private SongDisplayAdapter scrollerAdapter;
    private ArrayList<Song> searchList;
    private SearchView searchBar;

    SearchDisplayController(MainActivity mainActivity, SongDisplayAdapter scrollerAdapter, ArrayList<Song> searchList) {
        super(mainActivity);
        this.parent=this.parent.findViewById(R.id.searchDisplayInclude);
        this.searchBar=parent.findViewById(R.id.searchBar);
        this.searchList=searchList;
        this.scrollerAdapter=scrollerAdapter;
        SearchDisplayController cur=this;
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                // your text view here
                return true;
            }
            @Override
            public boolean onQueryTextSubmit(String query) {
                Search search = new Search();
                search.call(mainActivity, query, cur);
                return true;
            }
        });
        for (int i = 0; i < 10; i++) {
            Song mLog = new Song();
            mLog.setSongName("SUMTHING");
            mLog.setVideoID("XX");
            searchList.add(mLog);
            scrollerAdapter.notifyData(searchList);
        }
    }
    public void populateSearchResult(Search result) {
        Log.d("TEST", result.toString());
    }
}
