package com.example.blackjack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class SudokuBoard {

    static class Box {
        boolean mutable;
        boolean invalid;
        Integer value;

        Box(boolean mutable, Integer value) {
            this.mutable = mutable;
            this.value = value;
            this.invalid = false;
        }

        Box(boolean mutable) {
            this(mutable, null);
        }

        boolean equals(int n) {
            return value != null && value == n;
        }
    }

    private final Box[][] board;

    public Box get(int r, int c){
        return board[r][c];
    }

    public SudokuBoard() { //generate random board
        board = new Box[9][9];
        ArrayList<Integer> set = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (i / 3 == j / 3) {
                    board[i][j] = new Box(false, set.remove((int) (Math.random() * set.size())));
                } else {
                    board[i][j] = new Box(false);
                }
            }
            if ((i+1) % 3 == 0) {
                set = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
            }
        }
        solve();
        set.add(0);
        for (int i = 10; i <= 80; i++) {
            set.add(i);
        }

        Collections.shuffle(set);
        int numsToRemove = (int) (Math.random() * 14) + 36;
        for (int i = 0; i < numsToRemove; i++) {
            int r = set.get(i) / 9;
            int c = set.get(i) % 9;
            board[r][c].value = null;
            board[r][c].mutable = true;
        }
    }

    public void addNumber(int r, int c, int n) { //return whether number is valid or not
        if (board[r][c].mutable) {
            if (board[r][c].equals(n)) {
                board[r][c].value = null;
            } else {
                board[r][c].value = n;
            }
            checkSquare(r, c);
        }
    }

    public void clearBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j].mutable) {
                    board[i][j].value = null;
                }
                board[i][j].invalid = false;
            }
        }
    }

    public boolean solve() {
        return solve(-1, 0);
    }

    private boolean solve(int r, int c) {
        //find next open square
        do {
            r++;
            if (r == 9) {
                r = 0;
                c++;
                if (c == 9) {
                    return true;
                }
            }
        } while (board[r][c].value != null);
        //brute-force check all possible numbers into square
        for (int n = 1; n <= 9; n++) {
            if (isValidSquare(r, c, n)) {
                board[r][c].value = n;
                if (solve(r, c)) {
                    return true;
                }
            }
        }
        //backtrack
        board[r][c].value = null;
        return false;
    }

    public void checkSquare(int r, int c) {
        for (int i = 0; i < 9; i++) {
            board[r][i].invalid = !isValidSquare(r, i);
            board[i][c].invalid = !isValidSquare(i, c);
        }
        for (int i = r - r%3; i < r - r%3 + 3; i++) {
            for (int j = c - c%3; j < c - c%3 + 3; j++) {
                board[i][j].invalid = !isValidSquare(i, j);
            }
        }
    }

    public boolean isValidSquare(int r, int c) {
        if (board[r][c].value == null) {
            return true;
        }
        return isValidSquare(r, c, board[r][c].value);
    }

    private boolean isValidSquare(int r, int c, int n) {
        for (int i = 0; i < 9; i++) {
            if (i != c && board[r][i].equals(n)) {
                return false;
            }
            if (i != r && board[i][c].equals(n)) {
                return false;
            }
        }
        for (int i = r - r%3; i < r - r%3 + 3; i++) {
            for (int j = c - c%3; j < c - c%3 + 3; j++) {
                if (i != r && j != c && board[i][j].equals(n)) {
                    return false;
                }
            }
        }
        return true;
    }

    private char[][] toArray() { //for testing
        char[][] arr = new char[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j].value == null) {
                    arr[i][j] = '-';
                } else {
                    arr[i][j] = (char) (board[i][j].value + '0');
                }
            }
        }
        return arr;
    }
}