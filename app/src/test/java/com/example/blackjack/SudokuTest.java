package com.example.blackjack;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SudokuTest {
    SudokuBoard board;

    @Before
    public void init() {
        board = new SudokuBoard();
    }

    @Test
    public void testBoardCreation() {
        board = new SudokuBoard();
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                assertTrue(board.isValidSquare(r, c));
            }
        }
    }

    @Test
    public void testSolve() {
        board.solve();
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                assertTrue(board.isValidSquare(r, c) && board.get(r, c).value != null);
            }
        }
    }

    @Test
    public void testReset() {
        board.clearBoard();
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                assertTrue(!board.get(r, c).mutable || board.get(r, c).value == null);
            }
        }
    }

    @Test
    public void testAdd() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                Integer num = (c * r) % 9;
                Integer oldVal = board.get(r, c).value;
                board.addNumber(r, c, num);
                if (board.get(r, c).mutable) {
                    assertEquals(num, board.get(r, c).value);
                } else {
                    assertEquals(oldVal, board.get(r, c).value);
                }
            }
        }
    }
}
