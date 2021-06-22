package com.example.kaseki;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public abstract class DisplayController {
    protected View parent;
    protected MainActivity mainActivity;
    DisplayController(MainActivity mainActivity) {
        this.parent = mainActivity.findViewById(R.id.mainDisplayViewFlipper);
        this.mainActivity = mainActivity;
    }
}
