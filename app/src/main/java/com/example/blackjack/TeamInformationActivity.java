package com.example.blackjack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TeamInformationActivity extends AppCompatActivity {
    private Button ButtonExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_information);
        ButtonExit = (Button) findViewById(R.id.Exit_to_Main);
        ButtonExit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(TeamInformationActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}