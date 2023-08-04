package com.example.blackjack;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.Button;
import android.content.res.Resources;


import java.util.Random;

public class DinoScrollerView extends SurfaceView implements Runnable {

    private Thread thread;
    public boolean isPlaying = true;
    private DinoScrollerBackground b1, b2;
    private Obstacle o1, o2;
    private int sizeX, sizeY;
    private Paint paint;
    private float screenRatioX, screenRatioY;
    private Run run;
    Display display;
    Point point;
    int deviceWidth, deviceHeight;

    int velocity = 0;
    int gravity = 3;
    DinoScrollerActivity dSA;

    public DinoScrollerView(Context context, int sizeX, int sizeY) {
        super(context);

        this.sizeX = sizeX;
        this.sizeY = sizeY;

        screenRatioX = 1920f / sizeX;
        screenRatioY = 1080f / sizeY;

        b1 = new DinoScrollerBackground(sizeX, sizeY, getResources());
        b2 = new DinoScrollerBackground(sizeX, sizeY, getResources());

        o1 = new Obstacle(300, 100, getResources());
        o2 = new Obstacle(300, 100, getResources());

        run = new Run(sizeY, getResources());

        b2.x = sizeX;

        paint = new Paint();
        point = new Point();
        deviceWidth = point.x;
        deviceHeight = point.y;
    }

    @Override
    public void run() {

        while (isPlaying) {
            update();
            draw();
            sleep();
        }
    }

    private void update() {
        Random rand = new Random();
        //changes the positions of the backgrounds on x axis
//        b1.x -= 10 * screenRatioX; //10 pixels to the left
//        b2.x -= 10 * screenRatioX;
        b1.x -= 10;
        b2.x -= 10;
        System.out.println(b1.x +  " is the position");
        //when background moves off of the screen
        if (b1.x + b1.background.getWidth() < 0) {
            //position + width tells you where it is
            b1.x = sizeX;
        }

        if (b2.x + b2.background.getWidth() < 0) {
            //position + width tells you where it is
            b2.x = sizeX;
        }

        o1.x -= 40 * (1 + rand.nextInt(2));
        o2.x -= 20 + rand.nextInt(50);

        if (o1.x + o1.obstacle.getWidth() < 0) {
            o1.x = sizeX + rand.nextInt(2);
        }

        if (o2.x + o2.obstacle.getWidth() < 0) {
            o2.x = sizeX;
        }

        //keeps character from going off of screen
        if (run.y < 0) {
            run.y = 0;
        }

        //another way to keep if from going off of screen
        if ((run.y >= 770)) {
            run.y = 770;
            if(run.y == 770) {
                gravity = 0;
            }
        }

        boolean b = isCollision(run.getRun(), run.x, run.y, o1.getObstacle(), o1.x, o1.y);
        boolean c = isCollision(run.getRun(), run.x, run.y, o2.getObstacle(), o2.x, o2.y);
        //if it collides with either of the two obstacles GAME OVER
        if (b || c) {
            //pause();
            Button exit = (Button) findViewById(R.id.button5);
            isPlaying = false;
            draw();
            pause();
        }

    }

    private void draw() {
        //get canvas from surface view and draw background on the canvas
        if (getHolder().getSurface().isValid()) {
            //if surface view object has been instantiated
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(b1.background, b1.x, b1.y, paint);
            canvas.drawBitmap(b2.background, b2.x, b2.y, paint);

                velocity += gravity;
                run.y += velocity;

            canvas.drawBitmap(o1.obstacle, o1.x, o1.y, paint);
            canvas.drawBitmap(o2.obstacle, o2.x, o2.y, paint);
            //draw the run after drawing the background so it is placed on top of background
            canvas.drawBitmap(run.getRun(), run.x, run.y, paint);
            if (!isPlaying) {
                Bitmap a = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.gameover), sizeX/4, sizeY/4, false);
                canvas.drawBitmap(a, 700,  100, paint);
                Bitmap a1 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.close), sizeX/6, sizeY/5, false);
                canvas.drawBitmap(a1, 900,  400, paint);
                Bitmap a2 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.restart), sizeX/6, sizeY/5, false);
                canvas.drawBitmap(a2, 400,  400, paint);
            }
            //show canvas on screen
            getHolder().unlockCanvasAndPost(canvas);
        }


    }

    private void sleep() {
        //1000 ms/ 17ms = 60fps
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        //initialize thread object
        thread = new Thread(this);
        //gives functionality to the run method
        thread.start();
    }

    public void pause() {
        //calling this terminates the thread
        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println(run.y);
                if (run.y > 769) {
                    velocity = -50;
                    gravity = 3;
                }
                break;
            case MotionEvent.ACTION_UP:
                gravity = 3;
                break;
        }
        if (!isPlaying) {
            if (event.getX() > 1000) {
                System.exit(0);
            } else if (event.getX() > 300 && event.getX() < 900) {
                restart();
                resume();
            }
        }

        return true;
    }

    public static boolean isCollision(Bitmap b1, int x1, int y1,
                                      Bitmap b2, int x2, int y2) {
        Rect bounds1 = new Rect(x1+50, y1+50, x1+b1.getWidth()-50, y1+b1.getHeight()-50);
        //because the dino is a transparent image I shrunk its hit box to make it fair
        Rect bounds2 = new Rect(x2, y2, x2+b2.getWidth(), y2+b2.getHeight());

        if (Rect.intersects(bounds1, bounds2)) {
            return true;
        }
        return false;
    }

    public void restart() {
        Random rand = new Random();
        o1.x = sizeX + rand.nextInt(2);
        o2.x = sizeX + rand.nextInt(2);
        isPlaying = true;
        gravity = 3;
        velocity = 0;
        run.y = 770;
    }


}
