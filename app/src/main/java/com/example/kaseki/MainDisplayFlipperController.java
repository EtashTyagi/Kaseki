package com.example.kaseki;

import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.HashMap;

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
            } else if (nextID==1) {
                flipNext();
            }
        } else {
            if (nextID==1) {
                flipPrevious();
            } else if (nextID==0) {
                flipNext();
            }
        }
        currentSelectedID=nextID;
    }
    public void initiateHomeDisplay() {
        idToObject.put(0, new HomeDisplayController(mainActivity));
    }
    public void initiateSearchDisplay(ArrayList<Song> searchList, SongDisplayAdapter songDisplayAdapter) {
        idToObject.put(1, new SearchDisplayController(mainActivity, songDisplayAdapter, searchList));
    }
    public void initiateLibraryDisplay() {
        idToObject.put(2, new LibraryDisplayController(mainActivity));
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
