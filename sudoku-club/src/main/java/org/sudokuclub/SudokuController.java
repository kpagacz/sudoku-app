package org.sudokuclub;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SudokuController {

  @FXML private Label testLabel;

  public void initialize() {
    this.testLabel.setText("Here will be Sudoku to create");
  }

  @FXML
  public void openSaveLoadDialog() {
    final Stage dialog = new Stage();
    dialog.initModality(Modality.APPLICATION_MODAL);
    dialog.initOwner(App.mainStage);
    try {
      Parent root =
          FXMLLoader.load(
              Objects.requireNonNull(App.class.getResource("sudoku-save-load-dialog.fxml")));
      Scene dialogScene = new Scene(root, 600, 400);
      String css = Objects.requireNonNull(App.class.getResource("style.css")).toExternalForm();
      dialogScene.getStylesheets().add(css);
      dialog.setScene(dialogScene);
    } catch (IOException err) {
      System.out.println("save-dialog.fxml load problem");
    }
    dialog.show();
  }
}
