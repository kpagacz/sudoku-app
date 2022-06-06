package org.sudokuclub.services;

import org.sudokuclub.dao.SolvedSudoku;
import org.sudokuclub.dao.SolvedSudokusRepository;
import org.sudokuclub.dao.SudokuDbConnFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class SolvedSudokusService {
  private final Connection connection;

  public SolvedSudokusService() throws SQLException, IOException {
    connection = SudokuDbConnFactory.get();
  }

  public List<Integer> getSolvedSudokusByUser(String user) throws SQLException {
    return SolvedSudokusRepository.get(user, connection).stream()
        .map(SolvedSudoku::sudokuId)
        .collect(Collectors.toList());
  }

  public int checkSolvedSudokuByUser(String user, int sudokuID) throws SQLException {
    int solvedSudoku = SolvedSudokusRepository.get(user, sudokuID, connection);
    return solvedSudoku;
  }

  public void createSolvedSudoku(String user, int sudokuID) throws SQLException {
    SolvedSudokusRepository.create(sudokuID, user, connection);
  }
}
