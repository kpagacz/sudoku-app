package org.sudokuclub;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import java.io.IOException;

public class SudokuListController {

    @FXML
    private Label testLabel;

    public void initialize() {
        this.testLabel.setText("Here will be list of sudokus");
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

