package com.example.kaseki;

import android.view.View;
import android.view.ViewStub;
import androidx.appcompat.app.AppCompatActivity;

public class SearchDisplayController extends DisplayController{
    SearchDisplayController(MainActivity mainActivity) {
        super(mainActivity);
        this.parent=this.parent.findViewById(R.id.searchDisplayInclude);
    }
}
