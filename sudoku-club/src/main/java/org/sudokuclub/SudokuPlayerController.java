package org.sudokuclub;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sudokuclub.dao.Sudoku;
import org.sudokuclub.services.SolvedSudokusService;
import org.sudokuclub.services.SudokuService;
import org.sudokuclub.services.UserSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class SudokuPlayerController {
  private static Logger logger = LogManager.getLogger(SudokuPlayerController.class);

  @FXML private Label sudokuName;
  @FXML private Label sudokuAuthor;
  @FXML private GridPane sudokuGrid;
  @FXML private Label sudokuValidationLabel;
  @FXML private Label solvedLabel;

  private int sudokuID;

  public SudokuPlayerController(int sudokuID) {
    this.sudokuID = sudokuID;
  }

  public void initialize() {
    logger.debug("sudokuID in Player: " + this.sudokuID);
    try {
      String user = UserSession.getLogin().getValue();
      if(user != null && user.length() != 0) {
        SolvedSudokusService solvedSudokusService = new SolvedSudokusService();
        int isSudokuSolved = solvedSudokusService.checkSolvedSudokuByUser(user, this.sudokuID);
        if(isSudokuSolved == 1) {
          this.solvedLabel.setText("You've solved this sudoku");
        }
      }
      SudokuService sudokuService = new SudokuService();
      Sudoku sudoku = sudokuService.get(sudokuID);
      this.sudokuName.setText(sudoku.title());
      String author = sudoku.author();
      this.sudokuAuthor.setText("author: " + author);
      int[][] numbers = sudoku.cells();
      this.setupSudoku(numbers);
    } catch (SQLException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void setupSudoku(int[][] numbers) {
    Pattern pattern = Pattern.compile("[1-9]?");
    ObservableList<Node> cells = this.sudokuGrid.getChildren();
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        TextField cell = (TextField) cells.get(i * 9 + j);
        if (numbers[i][j] != 0) {
          cell.setText(String.valueOf(numbers[i][j]));
          cell.setEditable(false);
          cell.getStyleClass().add("sudoku__cell--disabled");
        } else {
          cell.setText("");
        }
        TextFormatter formatter =
            new TextFormatter(
                (UnaryOperator<TextFormatter.Change>)
                    change ->
                        pattern.matcher(change.getControlNewText()).matches() ? change : null);
        cell.setTextFormatter(formatter);
        cell.setOnMouseClicked(e -> cell.selectAll());
      }
    }
  }

  private class Coord {
    public final int row;
    public final int col;

    public Coord(int row, int col) {
      this.row = row;
      this.col = col;
    }
  }

  @FXML
  public void check() throws SQLException, IOException {
    AtomicBoolean isSudokuCorrect = new AtomicBoolean(true);
    int[][] numbers = new int[9][9];
    ObservableList<Node> cells = this.sudokuGrid.getChildren();
    cells.forEach(
        c -> {
          if (c.getStyleClass().contains("sudoku__cell--disabled")) {
            c.getStyleClass().clear();
            c.getStyleClass()
                .addAll("text-input", "text-field", "sudoku__cell", "sudoku__cell--disabled");
          } else {
            c.getStyleClass().clear();
            c.getStyleClass().addAll("text-input", "text-field", "sudoku__cell");
          }
        });
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        TextField cell = (TextField) cells.get((i * 9 + j));
        if (!cell.getText().equals("")) {
          numbers[i][j] = Integer.parseInt(cell.getText());
        } else {
          this.sudokuValidationLabel.setText("Sudoku not done! Empty cells");
          return;
        }
      }
    }

    Map<Integer, List<Coord>> digitsRows = new HashMap<>();
    Map<Integer, List<Coord>> digitsCols = new HashMap<>();
    Map<Integer, List<Coord>> digitsSquares = new HashMap<>();
    for (int d = 1; d <= 9; d++) {
      digitsRows.put(d, new ArrayList<>());
      digitsCols.put(d, new ArrayList<>());
      digitsSquares.put(d, new ArrayList<>());
    }
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        digitsRows.get(numbers[i][j]).add(new Coord(i, j));
        digitsCols.get(numbers[j][i]).add(new Coord(j, i));
      }
      isSudokuCorrect.set(checkDigitsSets(digitsRows, cells) && isSudokuCorrect.get());
      isSudokuCorrect.set(checkDigitsSets(digitsCols, cells) && isSudokuCorrect.get());
    }
    for (int i = 1; i <= 7; i += 3)
      for (int j = 1; j <= 7; j += 3) {
        for (int x : new int[] {-1, 0, 1})
          for (int y : new int[] {-1, 0, 1})
            digitsSquares.get(numbers[i + x][j + y]).add(new Coord(i + x, j + y));

        isSudokuCorrect.set(checkDigitsSets(digitsSquares, cells) && isSudokuCorrect.get());
      }
    if (!isSudokuCorrect.get()) {
      this.sudokuValidationLabel.setText("Sudoku solved incorrectly!");
    } else {
      String user = UserSession.getLogin().getValue();
      if(user != null && user.length() != 0) {
        if(this.solvedLabel.getText().length() == 0) {
          SolvedSudokusService solvedSudokusService = new SolvedSudokusService();
          solvedSudokusService.createSolvedSudoku(user, this.sudokuID);
          this.solvedLabel.setText("You've solved this sudoku");
        }
      }
      this.sudokuValidationLabel.setText("Congratulations! Sudoku solved correctly.");
    }
  }

  private boolean checkDigitsSets(Map<Integer, List<Coord>> digitsMap, ObservableList<Node> cells) {
    digitsMap.values().stream()
        .filter((list) -> list.size() > 1)
        .forEach(
            (list) -> {
              for (Coord cellCoordinate : list) {
                cells
                    .get(cellCoordinate.row * 9 + cellCoordinate.col)
                    .getStyleClass()
                    .add("sudoku__cell--invalid");
              }
            });
    boolean isSudokuCorrect = digitsMap.values().stream().allMatch(list -> list.size() == 1);

    digitsMap.values().forEach(List::clear);
    return isSudokuCorrect;
  }

  public void setSudokuID(int id) {
    this.sudokuID = id;
  }
}
