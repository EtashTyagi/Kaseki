package com.example.kaseki;

import android.view.View;
import android.widget.ImageButton;

public class BottomBarController {
    View parent;
    MainActivity mainActivity;
    ImageButton homeButton;
    ImageButton searchButton;
    ImageButton libraryButton;
    ImageButton selected;
    BottomBarController() {
        this.mainActivity=MainActivity.getCurrentInstance();
        this.parent=mainActivity.findViewById(R.id.bottomBarInclude);
        homeButton=parent.findViewById(R.id.homeButton);
        searchButton=parent.findViewById(R.id.searchButton);
        libraryButton=parent.findViewById(R.id.libraryButton);
        searchButton.setAlpha(0.5F);
        libraryButton.setAlpha(0.5F);
        selected=homeButton;
        homeButton.setOnClickListener(this::onClick);
        searchButton.setOnClickListener(this::onClick);
        libraryButton.setOnClickListener(this::onClick);
    }
    private void onClick(View view) {
        if (mainActivity.getSecondarySongDisplayController().getCurPlaying()!=null
                && mainActivity.getSecondarySongDisplayController().getParent().getVisibility()==View.INVISIBLE) {
            mainActivity.getSecondarySongDisplayController().relapse();
        }
        if (selected!=null) {
            selected.setAlpha(0.5F);
        }
        view.setAlpha(1);
        selected=(ImageButton)view;
        if (view==homeButton) {
            mainActivity.changeMainDisplayController(HomeDisplayController.class);
        } else if (view==searchButton) {
            mainActivity.changeMainDisplayController(SearchDisplayController.class);
        } else if (view==libraryButton) {
            mainActivity.changeMainDisplayController(LibraryDisplayController.class);
        }
    }
}