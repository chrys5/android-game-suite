package com.example.blackjack;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private Button BlackJack_Button_Temp;
    private Button Sudoku_Button_Temp;
    private Button DinoScroller_Button_Temp;
    private Button TeamInfo_Button_Temp;
    private Button Exit_Button_Temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BlackJack_Button_Temp = (Button) findViewById(R.id.BlackJack_Button);
        BlackJack_Button_Temp.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                openBlackJackActivity();
            }
        });
        Sudoku_Button_Temp = (Button) findViewById(R.id.Sudoku_Button);
        Sudoku_Button_Temp.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                openSudokuActivity();
            }
        });
        DinoScroller_Button_Temp = (Button) findViewById(R.id.DinoScroller_Button);
        DinoScroller_Button_Temp.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                openDinoScrollerActivity();
            }
        });
        TeamInfo_Button_Temp = (Button) findViewById(R.id.TeamInfo_Button);
        TeamInfo_Button_Temp.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                openTeamInfoActivity();
            }
        });
        Exit_Button_Temp = (Button) findViewById(R.id.Exit_Button);
        Exit_Button_Temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
    }
    public void openBlackJackActivity() {
        Intent intent = new Intent(this, BlackJackActivity.class);
        startActivity(intent);
    }
    public void openSudokuActivity() {
        Intent intent = new Intent(this, SudokuActivity.class);
        startActivity(intent);
    }
    public void openDinoScrollerActivity() {
        Intent intent = new Intent(this, DinoScrollerActivity.class);
        startActivity(intent);
    }
    public void openTeamInfoActivity() {
        Intent intent = new Intent(this, TeamInformationActivity.class);
        startActivity(intent);
    }
}