package org.sudokuclub;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class SudokuLoadDialogController {
  @FXML private Label testLabel;

  public void initialize() {
    this.testLabel.setText("Here you can save or load sudoku");
  }

  @FXML
  public void closeDialog(ActionEvent event) {
    Node source = (Node) event.getSource();
    Stage stage = (Stage) source.getScene().getWindow();
    stage.close();
  }
}
