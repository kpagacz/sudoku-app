package org.sudokuclub;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SudokuListController {

    @FXML
    private Label testLabel;

    public void initialize() {
        this.testLabel.setText("Here will be list of sudokus");
    }
}

