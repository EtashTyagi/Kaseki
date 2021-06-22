package com.example.kaseki;

import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class BottomBarController {
    View parent;
    AppCompatActivity mainActivity;
    ImageButton homeButton;
    ImageButton searchButton;
    ImageButton libraryButton;
    ImageButton selected;
    BottomBarController(View parent, AppCompatActivity mainActivity) {
        this.parent=parent;
        this.mainActivity=mainActivity;
        homeButton=parent.findViewById(R.id.homeButton);
        searchButton=parent.findViewById(R.id.searchButton);
        libraryButton=parent.findViewById(R.id.libraryButton);
        homeButton.setAlpha(0.5F);
        libraryButton.setAlpha(0.5F);
        selected=searchButton;
        homeButton.setOnClickListener(this::onClick);
        searchButton.setOnClickListener(this::onClick);
        libraryButton.setOnClickListener(this::onClick);
    }
    private void onClick(View view) {
        if (view!=selected) {
            selected.setAlpha(0.5F);
            view.setAlpha(1);
            selected=(ImageButton)view;
        }
    }
}