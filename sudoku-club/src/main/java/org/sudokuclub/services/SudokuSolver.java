package org.sudokuclub.services;

import org.sudokuclub.dao.Sudoku;

public interface SudokuSolver {
  boolean isSolvable(Sudoku sudoku);

  boolean isSolvable(int[][] grid);

  int[][] solve(Sudoku sudoku);

  int[][] solve(int[][] grid);
}
