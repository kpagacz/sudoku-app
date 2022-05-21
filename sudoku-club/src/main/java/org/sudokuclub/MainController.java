package org.sudokuclub;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import java.io.IOException;

public class MainController {

    @FXML
    private Label testLabel;

    public void initialize() {
        this.testLabel.setText("Main View");
    }

    @FXML
    public void goToSudoku() {
        try {
            Parent newWindow = FXMLLoader.load(App.class.getResource("sudoku-view.fxml"));
            App.setNewScene(newWindow);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void goToSudokuList() {
        try {
            Parent newWindow = FXMLLoader.load(App.class.getResource("sudoku-list-view.fxml"));
            App.setNewScene(newWindow);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
