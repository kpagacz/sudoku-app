package org.sudokuclub.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.sudokuclub.dao.Sudoku;
import org.sudokuclub.dao.SudokuDbConnFactory;
import org.sudokuclub.dao.SudokusRepository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class SudokuService {
  public final Connection connection;

  public SudokuService() throws SQLException, IOException {
    connection = SudokuDbConnFactory.get();
  }

  public List<Sudoku> get(int page, int itemsPerPage) throws SQLException, JsonProcessingException {
    return SudokusRepository.get(connection, page, itemsPerPage);
  }

  public int sudokusCount() throws SQLException {
    return SudokusRepository.getSudokusCount(connection);
  }
}
