package org.sudokuclub.dao.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sudokuclub.dao.Sudoku;
import org.sudokuclub.services.BacktrackingSudokuSolver;
import org.sudokuclub.services.SudokuSolver;

import static org.junit.jupiter.api.Assertions.*;

class BacktrackingSudokuSolverTest {
  @Test
  @DisplayName("BacktrackingSudokuSolver can be instantiated")
  void testInstantiation() {
    assertDoesNotThrow(BacktrackingSudokuSolverTest::new);
  }

  @Test
  @DisplayName("Empty sudoku cannot be solved")
  void testEmptySudokuIsNotSolvable() {
    int[][] emptySudoku = {
      {0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0},
    };
    Sudoku sudoku = new Sudoku(1, "title", emptySudoku, "author");
    SudokuSolver solver = new BacktrackingSudokuSolver();
    assertFalse(solver.isSolvable(sudoku));
  }

  @Test
  @DisplayName("isSolvable returns true given a solved sudoku")
  void testSolvedSudokuIsSolvable() {
    int[][] grid = {
      {4, 3, 5, 2, 6, 9, 7, 8, 1},
      {6, 8, 2, 5, 7, 1, 4, 9, 3},
      {1, 9, 7, 8, 3, 4, 5, 6, 2},
      {8, 2, 6, 1, 9, 5, 3, 4, 7},
      {3, 7, 4, 6, 8, 2, 9, 1, 5},
      {9, 5, 1, 7, 4, 3, 6, 2, 8},
      {5, 1, 9, 3, 2, 6, 8, 7, 4},
      {2, 4, 8, 9, 5, 7, 1, 3, 6},
      {7, 6, 3, 4, 1, 8, 2, 5, 9}
    };
    Sudoku sudoku = new Sudoku(1, "title", grid, "author");
    SudokuSolver solver = new BacktrackingSudokuSolver();
    assertTrue(solver.isSolvable(sudoku));
  }

  @Test
  @DisplayName("isSolvable returns true given a solvable sudoku")
  void testSolvableSudokuIsSolvable() {
    int[][] grid = {
      {4, 3, 5, 2, 6, 9, 7, 8, 1},
      {6, 8, 2, 5, 7, 1, 4, 9, 3},
      {1, 9, 7, 8, 3, 4, 5, 6, 2},
      {8, 2, 6, 1, 9, 5, 3, 4, 7},
      {3, 7, 4, 6, 0, 2, 9, 1, 5}, // missing 8
      {9, 5, 1, 7, 4, 3, 6, 2, 8},
      {5, 1, 9, 3, 2, 6, 8, 7, 4},
      {2, 4, 8, 9, 5, 7, 1, 3, 6},
      {7, 6, 3, 4, 1, 8, 2, 5, 9}
    };
    Sudoku sudoku = new Sudoku(1, "title", grid, "author");
    SudokuSolver solver = new BacktrackingSudokuSolver();
    assertTrue(solver.isSolvable(sudoku));
  }

  @Test
  @DisplayName("solve throws given an empty unsolvable sudoku")
  void testSolveThrowsGivenEmptySudoku() {
    int[][] emptySudoku = {
      {0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0},
    };
    Sudoku sudoku = new Sudoku(1, "title", emptySudoku, "author");
    SudokuSolver solver = new BacktrackingSudokuSolver();
    assertThrows(RuntimeException.class, () -> solver.solve(sudoku));
  }

  @Test
  @DisplayName("solve returns a solved sudoku given a solvable sudoku")
  void testSolveReturnsSolvedSudoku() {
    int[][] grid = {
      {4, 3, 5, 2, 6, 9, 7, 8, 1},
      {6, 8, 2, 5, 7, 1, 4, 9, 3},
      {1, 9, 7, 8, 3, 4, 5, 6, 2},
      {8, 2, 6, 1, 9, 5, 3, 4, 7},
      {3, 7, 4, 6, 0, 2, 9, 1, 5}, // missing 8
      {9, 5, 1, 7, 4, 3, 6, 2, 8},
      {5, 1, 9, 3, 2, 6, 8, 7, 4},
      {2, 4, 8, 9, 5, 7, 1, 3, 6},
      {7, 6, 3, 4, 1, 8, 2, 5, 9}
    };
    int[][] expected = {
      {4, 3, 5, 2, 6, 9, 7, 8, 1},
      {6, 8, 2, 5, 7, 1, 4, 9, 3},
      {1, 9, 7, 8, 3, 4, 5, 6, 2},
      {8, 2, 6, 1, 9, 5, 3, 4, 7},
      {3, 7, 4, 6, 8, 2, 9, 1, 5},
      {9, 5, 1, 7, 4, 3, 6, 2, 8},
      {5, 1, 9, 3, 2, 6, 8, 7, 4},
      {2, 4, 8, 9, 5, 7, 1, 3, 6},
      {7, 6, 3, 4, 1, 8, 2, 5, 9}
    };
    Sudoku sudoku = new Sudoku(1, "title", grid, "author");
    SudokuSolver solver = new BacktrackingSudokuSolver();
    assertArrayEquals(expected, solver.solve(sudoku));
  }
}
