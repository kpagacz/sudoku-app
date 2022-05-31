package org.sudokuclub;

import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sudokuclub.services.BacktrackingSudokuSolver;
import org.sudokuclub.services.SudokuService;
import org.sudokuclub.services.SudokuSolver;
import org.sudokuclub.services.UserSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class SudokuDesignerController {
  private final Logger logger = LogManager.getLogger(SudokuDesignerController.class);

  @FXML public Pane NavBar;
  @FXML public GridPane sudokuGrid;
  @FXML public Button publishButton;
  @FXML public Button saveButton;
  @FXML public Button loadButton;
  @FXML public Label submissionResult;
  @FXML public TextField sudokuTitleField;

  public void initialize() {
    publishButton.setOnAction(this::sudokuSubmissionHandler);
    saveButton.setOnAction(this::openSaveDialog);
    loadButton.setOnAction(this::openLoadDialog);
    styleCellsInGrid();
  }

  private void styleCellsInGrid() {
    Pattern pattern = Pattern.compile("[1-9]?");
    ObservableList<Node> cells = this.sudokuGrid.getChildren();
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        TextField cell = (TextField) cells.get(i * 9 + j);
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

  private static class PublishSudokuTask extends Task<String> {

    private final String title;
    private final int[][] grid;
    private final String author;
    private final SudokuService sudokuService;

    private final String publishingFailureMessage = "Publishing failed.";
    private final String publishingSuccessMessage = "Publishing successful.";

    public PublishSudokuTask(String title, int[][] grid, String author)
        throws SQLException, IOException {
      this.title = title;
      this.grid = grid;
      this.author = author;
      this.sudokuService = new SudokuService();
    }

    @Override
    protected String call() {
      try {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        if (!solver.isSolvable(grid)) {
          return "You must publish a proper, solvable sudoku!";
        }
        sudokuService.createSudoku(title, grid, author);
        return publishingSuccessMessage;
      } catch (SQLException | JsonProcessingException e) {
        e.printStackTrace();
        return publishingFailureMessage;
      }
    }
  }

  private int[][] readGrid() {
    int[][] gridDigits = new int[9][9];
    ObservableList<Node> cells = this.sudokuGrid.getChildren();
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        TextField cell = (TextField) cells.get((i * 9 + j));
        int digit = 0;
        if (!cell.getText().equals("")) {
          digit = Integer.parseInt(cell.getText());
          gridDigits[i][j] = digit;
        }
      }
    }
    return gridDigits;
  }

  @FXML
  private void sudokuSubmissionHandler(ActionEvent event) {
    if (UserSession.getLogin().get().equals("")) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Publishing error");
      alert.setHeaderText(null);
      alert.setContentText("You must be signed in to publish a sudoku.");
      alert.showAndWait();
      return;
    }

    if (sudokuTitleField.getText().equals("")) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Publishing error");
      alert.setHeaderText(null);
      alert.setContentText("You must title the sudoku to publish it.");
      alert.showAndWait();
      return;
    }

    try {
      Task<String> publishingTask =
          new PublishSudokuTask(
              sudokuTitleField.getText(), readGrid(), UserSession.getLogin().get());
      publishingTask.setOnSucceeded(this::submissionTaskResultHandler);
      Thread thread = new Thread(publishingTask);
      thread.setDaemon(true);
      thread.start();
    } catch (SQLException | IOException e) {
      e.printStackTrace();
      submissionResult.setText("Publishing failed.");
    }
  }

  private void submissionTaskResultHandler(WorkerStateEvent event) {
    submissionResult.setText((String) event.getSource().getValue());
  }

  @FXML
  void openLoadDialog(ActionEvent event) {
    final Stage dialog = new Stage();
    dialog.initModality(Modality.APPLICATION_MODAL);
    dialog.initOwner(App.mainStage);
    try {
      Parent root =
          FXMLLoader.load(Objects.requireNonNull(App.class.getResource("sudoku-load-dialog.fxml")));
      Scene dialogScene = new Scene(root, 600, 400);
      String css = Objects.requireNonNull(App.class.getResource("style.css")).toExternalForm();
      dialogScene.getStylesheets().add(css);
      dialog.setScene(dialogScene);
    } catch (IOException err) {
      logger.error("sudoku-load-dialog.fxml load problem");
      return;
    }
    dialog.show();
  }

  @FXML
  public void openSaveDialog(ActionEvent event) {
    final Stage dialog = new Stage();
    dialog.initModality(Modality.APPLICATION_MODAL);
    dialog.initOwner(App.mainStage);
    try {
      Parent root =
          FXMLLoader.load(Objects.requireNonNull(App.class.getResource("sudoku-save-dialog.fxml")));
      Scene dialogScene = new Scene(root, 600, 400);
      String css = Objects.requireNonNull(App.class.getResource("style.css")).toExternalForm();
      dialogScene.getStylesheets().add(css);
      dialog.setScene(dialogScene);
    } catch (IOException err) {
      logger.error("sudoku-save-dialog.fxml load problem");
      return;
    }
    dialog.show();
  }
}
