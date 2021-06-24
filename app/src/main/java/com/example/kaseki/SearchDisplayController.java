package com.example.kaseki;

import android.os.CountDownTimer;
import android.util.Log;
import android.widget.*;

import java.util.ArrayList;

public class SearchDisplayController extends DisplayController {
    private SongDisplayAdapter scrollerAdapter;
    private SearchView searchBar;

    SearchDisplayController(MainActivity mainActivity, SongDisplayAdapter scrollerAdapter) {
        super(mainActivity);
        this.parent=this.parent.findViewById(R.id.searchDisplayInclude);
        this.searchBar=parent.findViewById(R.id.searchBar);
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
                scrollerAdapter.clear();
                search.call(mainActivity, query, cur);
                Song dummy = new Song();
                dummy.setArtist("Tommy Boy");
                dummy.setVideoID("fPO76Jlnz6c");
                dummy.setSongName("Coolio - Gangsta's Paradise (feat. L.V.) [Official Music Video]");
                dummy.setThumbnailPath("https://i.ytimg.com/vi/fPO76Jlnz6c/maxresdefault.jpg");
                populateSong(dummy);
                return true;
            }
        });

    }

    public void populateSong(Song result) {
        Log.d("TEST", result.toString());
        scrollerAdapter.addElement(result);
    }
}
