package org.sudokuclub.services;

import org.sudokuclub.dao.Sudoku;

import java.util.HashSet;

public class BacktrackingSudokuSolver implements SudokuSolver {

  @Override
  public boolean isSolvable(Sudoku sudoku) {
    return isSolvable(sudoku.cells());
  }

  @Override
  public boolean isSolvable(int[][] gridDigits) {
    try{
      solve(gridDigits);
      return true;
    } catch(RuntimeException e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public int[][] solve(int[][] gridDigits) {
    int[][] copiedDigits = copyGrid(gridDigits);
    Position startingPosition = new Position(0, -1).next(copiedDigits);
    return backtrackingSolve(copiedDigits, startingPosition);
  }

  private int[][] copyGrid(int[][] grid) {
    int[][] copy = new int[9][9];
    for(int i = 0; i < 9; i++) for (int j = 0; j < 9; j++) copy[i][j] = grid[i][j];
    return copy;
  }

  @Override
  public int[][] solve(Sudoku sudoku) {
    return solve(sudoku.cells());
  }

  private int[][] backtrackingSolve(int[][] grid, Position position) {
    if (position == null) return grid;
    Position nextPosition = position.next(grid);
    for(int i = 1; i < 10; i++) {
      grid[position.row()][position.col()] = i;
      if (isDigitValid(grid, position)) {
          int[][] solution = backtrackingSolve(grid, nextPosition);
          if (solution != null) return solution;
        }
      }
    grid[position.row()][position.col()] = 0;
    return null;
  }

  private boolean isDigitValid(int[][] grid, Position position) {
    HashSet<Integer> columnDigits = getColumnDigits(grid, position);
    HashSet<Integer> rowDigits = getRowDigits(grid, position);
    HashSet<Integer> boxDigits = getBoxDigits(grid, position);
    int validatedDigit = grid[position.row()][position.col()];
    return !columnDigits.contains(validatedDigit) && !rowDigits.contains(validatedDigit) && !boxDigits.contains(validatedDigit);
  }

  private record Position(int row, int col) {
    public Position next(int[][] grid) {
      int nextCell = 9 * row + col + 1;
      while(nextCell < 81 && grid[nextCell / 9][nextCell % 9] != 0) nextCell++;
      if (nextCell == 81) return null;
      return new Position(nextCell / 9, nextCell % 9);
    }
  }

  private HashSet<Integer> getColumnDigits(int[][] grid, Position position) {
    HashSet<Integer> answer = new HashSet<>();
    for(int row = 0; row < 9; row++) {
      if (row != position.row()) answer.add(grid[row][position.col()]);
    }
    return answer;
  }

  private HashSet<Integer> getRowDigits(int[][] grid, Position position) {
    HashSet<Integer> answer = new HashSet<>();
    for(int col = 0; col < 9; col++)
      if (col != position.col()) answer.add(grid[position.row()][col]);
    return answer;
  }

  private HashSet<Integer> getBoxDigits(int[][] grid, Position position) {
    HashSet<Integer> answer = new HashSet<>();
    int boxX = position.row() / 3;
    int boxY = position.col() / 3;
    for(int row = boxX * 3; row < (boxX + 1) * 3; row++)
      for(int col = boxY * 3; col < (boxY + 1) * 3; col ++)
        if (row != position.row() || col != position.col())
          answer.add(grid[row][col]);
    return answer;
  }
}
