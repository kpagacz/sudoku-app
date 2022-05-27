package org.sudokuclub;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.sudokuclub.dao.SolvedSudoku;
import org.sudokuclub.dao.Sudoku;
import org.sudokuclub.services.SolvedSudokusService;
import org.sudokuclub.services.SudokuService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SudokuListController {

  record SudokuRow(int id, String name, String author, boolean done) {}

  private List<SudokuRow> rowItems = new ArrayList<>();

  @FXML private TableView<SudokuRow> sudokuTable;
  @FXML private TableColumn<SudokuRow, String> nameColumn;
  @FXML private TableColumn<SudokuRow, String> authorColumn;
  @FXML private TableColumn<SudokuRow, String> doneColumn;

  public void initialize() {
    updateRowItems(1, 10, null);
    ObservableList<SudokuRow> initialItems = FXCollections.observableArrayList(rowItems);
  }

  private void updateRowItems(int page, int itemsPerPage, String user) {
    List<Sudoku> sudokus;
    Set<Integer> solvedSudokus = new HashSet<>();
    try {
      SudokuService sudokuService = new SudokuService();
      sudokus = sudokuService.get(page, itemsPerPage);
      SolvedSudokusService solvedSudokusService = new SolvedSudokusService();
      solvedSudokus = new HashSet<>();
      if (user != null) {
        solvedSudokus = new HashSet<>(solvedSudokusService.getSolvedSudokusByUser(user));
      }

    } catch (SQLException | IOException e) {
      throw new RuntimeException(e);
    }

    Set<Integer> finalSolvedSudokus = solvedSudokus;
    rowItems =
        sudokus.stream()
            .map(
                sudoku ->
                    new SudokuRow(
                        sudoku.id(),
                        sudoku.title(),
                        sudoku.author(),
                        finalSolvedSudokus.contains(sudoku.id())))
            .toList();
  }

  @FXML
  public void goToSudokuPlayer() {
    try {
      Parent newWindow = FXMLLoader.load(App.class.getResource("sudoku-player-view.fxml"));
      App.setNewScene(newWindow);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
