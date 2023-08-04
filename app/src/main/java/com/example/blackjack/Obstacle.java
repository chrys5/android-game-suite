package com.example.blackjack;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Obstacle {
    int x = 2000, y = 770;
    Bitmap obstacle;

    //constructor
    Obstacle (int sizeX, int sizeY, Resources res) {
        obstacle = BitmapFactory.decodeResource(res, R.drawable.cactus);
        obstacle = Bitmap.createScaledBitmap(obstacle, sizeX, sizeY, false);
    }

    public Bitmap getObstacle() {
        return obstacle;
    }
}
