package org.sudokuclub.services;

import org.sudokuclub.dao.Sudoku;

public interface SudokuSolver {
    boolean isSolvable(Sudoku sudoku);
    int[][] solve(Sudoku sudoku);
}
