package com.example.blackjack;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Run {
    public boolean isGoingUp = false;
    int dinoFrame = 0;
    int x, y, width, height;
    int runCounter = 0;
    Bitmap run1;

    Run (int screenY, Resources res) {
        run1 = BitmapFactory.decodeResource(res, R.drawable.trex);
        width = run1.getWidth();
        height = run1.getHeight();

        width /= 8;
        height /= 8;

        //put screen ratio stuff here

        //resize
        run1 = Bitmap.createScaledBitmap(run1, width, height, false);

        //margin on x-axis
        x = 64; //64 pixels
        y = 770; //puts image on bottom of screen
    }

    public Bitmap getRun() {
        return run1;
    }
}
