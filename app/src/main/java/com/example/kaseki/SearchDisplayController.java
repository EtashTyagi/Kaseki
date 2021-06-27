package com.example.kaseki;

import android.util.Log;
import android.widget.*;

public class SearchDisplayController extends DisplayController {
    private SongDisplayAdapter scrollerAdapter;
    private SearchView searchBar;
    private static Search search;

    SearchDisplayController(SongDisplayAdapter scrollerAdapter) {
        super();
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
                search = new Search();
                scrollerAdapter.clear();
                search.call(mainActivity, query, cur);
                return true;
            }
        });

    }

    public void populateSong(Song result) {
        Log.d("TEST", result.toString());
        scrollerAdapter.addElement(result);
    }
    public static Search getSearch(){
        return search;
    }
}
