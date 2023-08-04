package com.example.blackjack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SudokuActivity extends AppCompatActivity {
    private Button ButtonExit;
    private SudokuBoardGrid grid;
    private SudokuBoard board;
    private SudokuBoardGrid.SudokuGameEngine engine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku);

        grid = findViewById(R.id.sudokuBoardGrid2);
        board = grid.getBoard();
        engine = grid.getGameEngine();
        ButtonExit = (Button) findViewById(R.id.Exit_to_Main);
        ButtonExit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(SudokuActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void ButtonSolve(View view) {
        board.solve();
        grid.invalidate();
    }
    public void ButtonReset(View view) {
        board.clearBoard();
        grid.invalidate();
    }

    public void ButtonOne(View view) {
        board.addNumber(engine.getSelected_row() - 1, engine.getSelected_col() - 1,1);
        grid.invalidate();
    }
    public void ButtonTwo(View view) {
        board.addNumber(engine.getSelected_row() - 1, engine.getSelected_col() - 1,2);
        grid.invalidate();
    }
    public void ButtonThree(View view) {
        board.addNumber(engine.getSelected_row() - 1, engine.getSelected_col() - 1,3);
        grid.invalidate();
    }
    public void ButtonFour(View view) {
        board.addNumber(engine.getSelected_row() - 1, engine.getSelected_col() - 1,4);
        grid.invalidate();
    }
    public void ButtonFive(View view) {
        board.addNumber(engine.getSelected_row() - 1, engine.getSelected_col() - 1,5);
        grid.invalidate();
    }
    public void ButtonSix(View view) {
        board.addNumber(engine.getSelected_row() - 1, engine.getSelected_col() - 1,6);
        grid.invalidate();
    }
    public void ButtonSeven(View view) {
        board.addNumber(engine.getSelected_row() - 1, engine.getSelected_col() - 1,7);
        grid.invalidate();
    }
    public void ButtonEight(View view) {
        board.addNumber(engine.getSelected_row() - 1, engine.getSelected_col() - 1,8);
        grid.invalidate();
    }
    public void ButtonNine(View view) {
        board.addNumber(engine.getSelected_row() - 1, engine.getSelected_col() - 1,9);
        grid.invalidate();
    }
}