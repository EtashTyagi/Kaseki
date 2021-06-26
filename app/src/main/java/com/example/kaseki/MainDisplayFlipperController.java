package com.example.kaseki;

import android.widget.ViewFlipper;

import java.util.HashMap;
import java.util.Vector;

public class MainDisplayFlipperController {
    private ViewFlipper parent;
    private MainActivity mainActivity;
    private HashMap<Class<? extends DisplayController>, Integer> displayControllersToID
            =new HashMap<>();
    private HashMap<Integer, DisplayController> idToObject=new HashMap<>();
    private int currentSelectedID=0;

    MainDisplayFlipperController(MainActivity mainActivity) {
        this.parent = mainActivity.findViewById(R.id.mainDisplayViewFlipper);
        this.mainActivity = mainActivity;
        displayControllersToID.put(HomeDisplayController.class, 0);
        displayControllersToID.put(SearchDisplayController.class, 1);
        displayControllersToID.put(LibraryDisplayController.class, 2);
        displayControllersToID.put(PlaylistDisplayController.class, 3);
    }
    public void flipToDisplay(Class<? extends DisplayController> type) {
        int nextID = displayControllersToID.get(type);
        if (currentSelectedID==1) {
            if (nextID==0) {
                flipPrevious();
            } else if (nextID==2) {
                flipNext();
            }
        } else if (currentSelectedID==0) {
            if (nextID==2) {
                flipPrevious();
                parent.showPrevious();
            } else if (nextID==1) {
                flipNext();
            }
        } else if (currentSelectedID==2){
            if (nextID==1) {
                flipPrevious();
            } else if (nextID==0) {
                flipNext();
                parent.showNext();
            } else {
                flipNext();
            }
        } else {
            if (nextID==0) {
                flipNext();
            } else if (nextID==1) {
                flipNext();
                parent.showNext();
            } else if (nextID==2) {
                flipPrevious();
            }
        }
        currentSelectedID=nextID;
    }
    public HomeDisplayController initiateHomeDisplay() {
        HomeDisplayController n=new HomeDisplayController(mainActivity);
        idToObject.put(0, n);
        return n;
    }
    public SearchDisplayController initiateSearchDisplay(SongDisplayAdapter songDisplayAdapter) {
        SearchDisplayController n = new SearchDisplayController(mainActivity, songDisplayAdapter);
        idToObject.put(1, n);
        return n;
    }
    public LibraryDisplayController initiateLibraryDisplay(PlaylistDisplayAdapter songDisplayAdapter, Vector<Playlist> playlists) {
        LibraryDisplayController n = new LibraryDisplayController(mainActivity, songDisplayAdapter, playlists);
        idToObject.put(2, n);
        return n;
    }
    public PlaylistDisplayController initiatePlaylistDisplay(Playlist defaultP) {
        PlaylistDisplayController n =  new PlaylistDisplayController(mainActivity, defaultP);
        idToObject.put(3, n);
        return n;
    }
    private void flipNext() {
        parent.setInAnimation(mainActivity.getBaseContext(),
                R.anim.slide_right_next);
        parent.setOutAnimation(mainActivity.getBaseContext(),
                R.anim.slide_left_next);

        parent.showNext();
    }
    private void flipPrevious() {
        parent.setInAnimation(mainActivity.getBaseContext(),
                R.anim.slide_right_prev);
        parent.setOutAnimation(mainActivity.getBaseContext(),
                R.anim.slide_left_prev);
        parent.showPrevious();
    }
}
