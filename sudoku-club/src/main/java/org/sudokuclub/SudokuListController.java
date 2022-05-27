package org.sudokuclub;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;

public class SudokuListController {

    class SudokuRow {
        //placeholder
    }

    @FXML
    private TableView<SudokuRow> sudokuTable;
    @FXML
    private TableColumn<SudokuRow, String> nameColumn;
    @FXML
    private TableColumn<SudokuRow, String> authorColumn;
    @FXML
    private TableColumn<SudokuRow, String> doneColumn;

    public void initialize() {
        ObservableList<SudokuRow> initialItems = FXCollections.observableArrayList();

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

