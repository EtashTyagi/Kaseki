package com.example.kaseki;

import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HomeDisplayController extends DisplayController{
    HomeDisplayController() {
        super();
        this.parent=this.parent.findViewById(R.id.homeDisplayInclude);
    }
}
