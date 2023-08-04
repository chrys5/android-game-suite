package com.example.blackjack;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class DinoScrollerBackground {
    int x = 0, y = 0;
    Bitmap background;

    //constructor
    DinoScrollerBackground (int sizeX, int sizeY, Resources res) {
        //inputs are size of screen on both x and y axis and resource file to decode the bitmap
        background = BitmapFactory.decodeResource(res, R.drawable.background);
        //resize background to fit entire screen
        background = Bitmap.createScaledBitmap(background, sizeX, sizeY, false);
    }
}
