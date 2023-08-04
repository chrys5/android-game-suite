package com.example.blackjack;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.HashSet;
import java.util.Set;

public class SudokuBoardGrid extends View {
    private final int sudoku_board_Color;
    private final SudokuGameEngine gameEngine = new SudokuGameEngine();
    private SudokuBoard board = new SudokuBoard();

    private int cells;

    private final int selectedcellFill;
    private final int rowcolHighlight;
    private final int lettersColor;
    private final int solvedColor;

    private final Paint boardPaint = new Paint();
    private final Paint selectedcellFillPaint = new Paint();
    private final Paint rowcolHighlightPaint = new Paint();
    private final Paint lettersPaint = new Paint();
    private final Paint solvedPaint = new Paint();


    public SudokuBoardGrid(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.SudokuBoardGrid, 0, 0);
        try{
            sudoku_board_Color = array.getInteger(R.styleable.SudokuBoardGrid_sudoku_board_Color,
                    0);
            selectedcellFill = array.getInteger(R.styleable.SudokuBoardGrid_selectedcellFill,
                    0);
            rowcolHighlight = array.getInteger(R.styleable.SudokuBoardGrid_rowcolHighlight,
                    0);
            lettersColor = array.getInteger(R.styleable.SudokuBoardGrid_lettersColor, 0);

            solvedColor = array.getInteger(R.styleable.SudokuBoardGrid_solvedColor, 0);

        } finally {
            array.recycle();
        }
    }

    @Override
    protected void onMeasure(int width, int height){
        super.onMeasure(width, height);
        int size = Math.min(this.getMeasuredWidth(), this.getMeasuredHeight());
        cells = size / 9;
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        boardPaint.setStyle(Paint.Style.STROKE);
        boardPaint.setStrokeWidth(24);
        boardPaint.setColor(Color.BLACK);
        boardPaint.setAntiAlias(true);

        selectedcellFillPaint.setStyle(Paint.Style.FILL);
        boardPaint.setAntiAlias(true);
        selectedcellFillPaint.setColor(selectedcellFill);

        rowcolHighlightPaint.setStyle(Paint.Style.FILL);
        boardPaint.setAntiAlias(true);
        rowcolHighlightPaint.setColor(rowcolHighlight);

        selectedCellColor(canvas, gameEngine.getSelected_row(), gameEngine.getSelected_col());
        canvas.drawRect(0, 0, getWidth(), getHeight(), boardPaint);
        canvasDraw(canvas);
        displayNumbers(canvas);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event){
        boolean valid;
        float x = event.getX();
        float y = event.getY();
        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN){
            gameEngine.setSelected_row((int)Math.ceil(y/cells));
            gameEngine.setSelected_col((int)Math.ceil(x/cells));
            valid = true;
        }
        else{
            valid = false;
        }
        return valid;
    }

    public SudokuBoard getBoard() {
        return board;
    }

    public SudokuGameEngine getGameEngine() {
        return gameEngine;
    }

    private void selectedCellColor(Canvas canvas, int row, int col){
        if(gameEngine.getSelected_row() >= 0 && gameEngine.getSelected_col() >= 0){
            canvas.drawRect((col-1) *cells, 0 , col *cells, cells*9,
                    rowcolHighlightPaint);
            canvas.drawRect(0, (row-1) *cells , cells*9, row *cells,
                    rowcolHighlightPaint);
            canvas.drawRect((col-1) *cells, (row-1) *cells , col *cells, row *cells,
                    selectedcellFillPaint);

        }
        invalidate();
    }
    private final Rect bounds = new Rect();

    private void displayNumbers(Canvas canvas) {
        lettersPaint.setTextSize(cells);
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                if (board.get(i, j).value != null) {
                    if (board.get(i, j).invalid) {
                        lettersPaint.setColor(Color.rgb(195, 0, 0));
                    } else if (board.get(i, j). mutable) {
                        lettersPaint.setColor(Color.rgb(128, 70, 173));
                    } else {
                        lettersPaint.setColor(Color.BLACK);
                    }
                    String text = Integer.toString(board.get(i,j).value);
                    float text_width, text_height;

                    lettersPaint.getTextBounds(text, 0, text.length(), bounds);
                    text_width = lettersPaint.measureText(text);
                    text_height = bounds.height();

                    canvas.drawText(text, (j*cells) + ((cells - text_width)/2), (i*cells +cells) -
                            ((cells - text_height)/2), lettersPaint);
                }
            }
        }
    }

    protected void drawThickLine(){
        boardPaint.setStyle(Paint.Style.STROKE);
        boardPaint.setStrokeWidth(12);
        boardPaint.setColor(Color.BLACK);
    }
    protected void drawThinLine(){
        boardPaint.setStyle(Paint.Style.STROKE);
        boardPaint.setStrokeWidth(4);
        boardPaint.setColor(Color.BLACK);
    }
    protected void canvasDraw(Canvas canvas) {
        for (int row = 0; row < 10; row++) {
            if (row % 3 == 0) {
                drawThickLine();
            }
            else{
                drawThinLine();
            }
            canvas.drawLine(0, cells * row, getWidth(),
                cells * row, boardPaint);
        }
        for (int col = 0; col < 10; col++) {
            if (col % 3 == 0) {
                drawThickLine();
            }
            else{
                drawThinLine();
            }
            canvas.drawLine(cells * col, 0, cells * col,
                    getHeight(), boardPaint);
        }

    }

    public static class SudokuGameEngine {
        private static int selected_row;
        private static int selected_col;

        SudokuGameEngine() {
            selected_row = 5;
            selected_col = 5;
        }

        public int getSelected_row() {
            return selected_row;
        }

        public int getSelected_col() {
            return selected_col;
        }

        public void setSelected_row(int row) {
            selected_row = row;
        }
        public void setSelected_col(int col) {
            selected_col = col;
        }
    }

}
