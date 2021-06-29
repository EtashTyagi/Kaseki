package com.example.kaseki;

import android.util.Log;
import android.view.View;
import android.widget.*;
import com.bumptech.glide.Glide;

public class SearchDisplayController extends DisplayController {
    private SongDisplayAdapter scrollerAdapter;
    private SearchView searchBar;
    private ImageView loadingIndicator;
    private static Search search;

    SearchDisplayController(SongDisplayAdapter scrollerAdapter) {
        super();
        this.parent=this.parent.findViewById(R.id.searchDisplayInclude);
        this.searchBar=parent.findViewById(R.id.searchBar);
        this.scrollerAdapter=scrollerAdapter;
        this.loadingIndicator=parent.findViewById(R.id.loadingIndicator);
        Glide.with(mainActivity).load(R.drawable.loading).into(loadingIndicator);
        loadingIndicator.setVisibility(View.INVISIBLE);
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
                loadingIndicator.setVisibility(View.VISIBLE);
                search.call(mainActivity, query, cur);
                return true;
            }
        });

    }
    public void notifySearchComplete() {
        loadingIndicator.setVisibility(View.INVISIBLE);
        MainActivity.getCurrentInstance().runOnUiThread(
                ()->{ Toast.makeText(MainActivity.getCurrentInstance(),
                        "Search completed", Toast.LENGTH_SHORT).show();});
    }
    public void populateSong(Song result) {
        Log.d("TEST", result.toString());
        scrollerAdapter.addElement(result);
    }
    public static Search getSearch(){
        return search;
    }
}
