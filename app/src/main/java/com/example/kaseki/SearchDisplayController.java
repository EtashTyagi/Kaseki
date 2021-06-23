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

    }
    public void populateSearchResult(Search result) {
        Log.d("TEST", result.toString());
        ArrayList<String> titles = result.getTitle();
        ArrayList<String> channel=result.getChannel_Title();
        ArrayList<String> smallThumbnail=result.getSmall_thumbnail();
        for (int i = 0; i < titles.size(); i++) {
            Song mLog = new Song();
            mLog.setSongName(titles.get(i));
            mLog.setVideoID(channel.get(i));
            if (i < smallThumbnail.size()) {
                mLog.setThumbnailPath(smallThumbnail.get(i));
            }

            searchList.add(mLog);
            scrollerAdapter.notifyData(searchList);
        }

    }
}
