package com.example.blackjack;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DinoScrollerActivity extends AppCompatActivity {

    private DinoScrollerView dinoScrollerView;
    private Button ButtonExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dino_scroller);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //How to get screen size
//        Point point = new Point();
//        getWindowManager().getDefaultDisplay().getSize(point);
//        Display d = createDisplayContext(android.view.Display);

//        dinoScrollerView = new DinoScrollerView(this, point.x, point.y);
//        dinoScrollerView = new DinoScrollerView(this, getScreenWidth(), getScreenHeight());
        dinoScrollerView = new DinoScrollerView(this, 1920, 1080);
        ButtonExit = (Button) findViewById(R.id.button5);
        ButtonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DinoScrollerActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
//        //show dinoScrollerView on the screen
        setContentView(dinoScrollerView);


    }

    //try to change from depracated use to see if animation works
//    public Context createWindowContext (Display display, int type, Bundle options) {
//
//    }

    @Override
    protected void onPause() {
        super.onPause();
        dinoScrollerView.pause();
    }
    void pause() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        setContentView(R.layout.activity_dino_scroller);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dinoScrollerView.resume();
    }

    //testing code
//    public static int getScreenWidth() {
//        return Resources.getSystem().getDisplayMetrics().widthPixels;
//    }
//
//    public static int getScreenHeight() {
//        return Resources.getSystem().getDisplayMetrics().heightPixels;
//    }
}