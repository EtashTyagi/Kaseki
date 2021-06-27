package com.example.kaseki;

import android.view.View;

public abstract class DisplayController {
    protected View parent;
    protected MainActivity mainActivity;
    DisplayController() {
        this.mainActivity = MainActivity.getCurrentInstance();
        this.parent = mainActivity.findViewById(R.id.mainDisplayViewFlipper);
    }
}
