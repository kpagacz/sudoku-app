package org.sudokuclub;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;

public class SudokuListController {

    class Sudoku {
        //placeholder
    }

    @FXML
    private TableView<Sudoku> sudokuTable;
    @FXML
    private TableColumn<Sudoku, String> nameColumn;
    @FXML
    private TableColumn<Sudoku, String> authorColumn;
    @FXML
    private TableColumn<Sudoku, String> doneColumn;

    public void initialize() {


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

