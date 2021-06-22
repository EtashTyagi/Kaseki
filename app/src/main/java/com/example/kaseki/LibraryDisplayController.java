package com.example.kaseki;

import android.view.View;
import android.view.ViewStub;
import androidx.appcompat.app.AppCompatActivity;

public class LibraryDisplayController extends DisplayController{
    LibraryDisplayController(MainActivity mainActivity) {
        super(mainActivity);
        this.parent=this.parent.findViewById(R.id.libraryDisplayInclude);
    }
}
