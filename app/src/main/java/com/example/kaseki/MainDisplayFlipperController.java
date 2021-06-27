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

    MainDisplayFlipperController() {
        this.mainActivity = MainActivity.getCurrentInstance();
        this.parent = mainActivity.findViewById(R.id.mainDisplayViewFlipper);
        displayControllersToID.put(HomeDisplayController.class, 0);
        displayControllersToID.put(SearchDisplayController.class, 1);
        displayControllersToID.put(LibraryDisplayController.class, 2);
        displayControllersToID.put(PlaylistDisplayController.class, 3);
        displayControllersToID.put(PrimarySongDisplayController.class, 4);
    }
    public void flipToDisplay(Class<? extends DisplayController> type) {
        int nextID = displayControllersToID.get(type);
        if (nextID > currentSelectedID) {
            if (nextID-currentSelectedID<displayControllersToID.size()-nextID+currentSelectedID) {
                while (currentSelectedID!=nextID) {
                    flipNext();
                    currentSelectedID++;
                }
            } else {
                while (currentSelectedID!=nextID) {
                    flipPrevious();
                    currentSelectedID=(currentSelectedID-1+displayControllersToID.size())%displayControllersToID.size();
                }
            }
        } else {
            if (currentSelectedID-nextID<displayControllersToID.size()-currentSelectedID+nextID) {
                while (currentSelectedID!=nextID) {
                    flipPrevious();
                    currentSelectedID--;
                }
            } else {
                while (currentSelectedID!=nextID) {
                    flipNext();
                    currentSelectedID=(currentSelectedID+1)%displayControllersToID.size();
                }
            }
        }
    }
    public HomeDisplayController initiateHomeDisplay() {
        HomeDisplayController n=new HomeDisplayController();
        idToObject.put(0, n);
        return n;
    }
    public SearchDisplayController initiateSearchDisplay(SongDisplayAdapter songDisplayAdapter) {
        SearchDisplayController n = new SearchDisplayController(songDisplayAdapter);
        idToObject.put(1, n);
        return n;
    }
    public LibraryDisplayController initiateLibraryDisplay(PlaylistDisplayAdapter songDisplayAdapter, Vector<Playlist> playlists) {
        LibraryDisplayController n = new LibraryDisplayController(songDisplayAdapter, playlists);
        idToObject.put(2, n);
        return n;
    }
    public PlaylistDisplayController initiatePlaylistDisplay(Playlist defaultP) {
        PlaylistDisplayController n =  new PlaylistDisplayController(defaultP);
        idToObject.put(3, n);
        return n;
    }
    public PrimarySongDisplayController initiatePrimarySongDisplayController() {
        PrimarySongDisplayController n = new PrimarySongDisplayController();
        idToObject.put(4, n);
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
