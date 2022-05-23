package org.sudokuclub;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class SudokuPlayerController {

    @FXML
    private Label sudokuName;
    @FXML
    private Label sudokuAuthor;
    @FXML
    private GridPane sudokuGrid;
    @FXML
    private Label sudokuValidationLabel;

    public void initialize() {
        this.sudokuName.setText("Name of Sudoku");
        String author = "Some User";
        this.sudokuAuthor.setText("author: "+author);

        int[][] numbers = {
                {4, 3, 5,    2, 6, 9,    7, 8, 1},
                {6, 8, 2,    5, 7, 1,    4, 9, 3},
                {1, 9, 7,    8, 3, 4,    5, 6, 2},

                {8, 2, 6,    1, 9, 5,    3, 4, 7},
                {3, 7, 4,    6, 8, 2,    9, 1, 5},
                {0, 5, 1,    7, 4, 3,    6, 2, 8},

                {5, 1, 9,    3, 2, 6,    8, 7, 4},
                {2, 4, 8,    9, 5, 7,    1, 3, 6},
                {7, 6, 3,    4, 0, 8,    2, 5, 0},
        };

        this.setupSudoku(numbers);

    }

    public void setupSudoku(int[][] numbers) {
        Pattern pattern = Pattern.compile("[1-9]?");
        ObservableList<Node> cells = this.sudokuGrid.getChildren();
        for(int i = 0; i<9; i++) {
            for(int j = 0; j<9; j++) {
                TextField cell = (TextField) cells.get((j * 9 + i));
                if(numbers[j][i] != 0) {
                    cell.setText(String.valueOf(numbers[j][i]));
                    cell.setEditable(false);
                    cell.getStyleClass().add("sudoku__cell--disabled");
                } else {
                    cell.setText("");
                }
                TextFormatter formatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
                    return pattern.matcher(change.getControlNewText()).matches() ? change : null;
                });
                cell.setTextFormatter(formatter);
            }
        }
    }

    @FXML
    public void check() {
        AtomicBoolean isSudokuDone = new AtomicBoolean(true);
        int[][] numbers = new int[9][9];
        ObservableList<Node> cells = this.sudokuGrid.getChildren();
        cells.forEach(c -> {
            if(c.getStyleClass().contains("sudoku__cell--disabled")) {
                c.getStyleClass().clear();
                c.getStyleClass().addAll("text-input", "text-field", "sudoku__cell", "sudoku__cell--disabled");
            } else {
                c.getStyleClass().clear();
                c.getStyleClass().addAll("text-input", "text-field", "sudoku__cell");
            }
        });
        for(int i =0; i<9; i++) {
            for(int j=0; j<9; j++) {
                TextField cell = (TextField) cells.get((j * 9 + i));
                if(!cell.getText().equals("")) {
                    numbers[j][i] = Integer.parseInt(cell.getText());
                } else {
                    this.sudokuValidationLabel.setText("Sudoku not done! Empty cells");
                    return;
                }
            }
        }
        class Coord {
            public final int x;
            public final int y;
            public Coord(int x, int y) {
                this.x = x;
                this.y = y;
            }
        }
        HashMap<Integer, ArrayList<Coord>> digitsRows = new HashMap<>();
        HashMap<Integer, ArrayList<Coord>> digitsCols = new HashMap<>();
        HashMap<Integer, ArrayList<Coord>> digitsSquares = new HashMap<>();
        for(int d = 1; d<=9; d++) {
            digitsRows.put(d, new ArrayList<>());
            digitsCols.put(d, new ArrayList<>());
            digitsSquares.put(d, new ArrayList<>());
        }
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j< 9; j++) {
                digitsRows.get(numbers[i][j]).add(new Coord(i, j));
                digitsCols.get(numbers[j][i]).add(new Coord(i, j));
            }
            digitsRows.values().stream()
                    .filter((list) -> list.size() > 1)
                    .forEach((list)-> {
                        isSudokuDone.set(false);
                        for (Coord cellCoord : list) {
                            cells.get(cellCoord.x * 9 + cellCoord.y).getStyleClass().add("sudoku__cell--invalid");
                        }
                    });
            digitsRows.values().forEach(ArrayList::clear);
            digitsCols.values().stream()
                    .filter((list) -> list.size() > 1)
                    .forEach((list)-> {
                        isSudokuDone.set(false);
                        for (Coord cellCoord : list) {
                            cells.get(cellCoord.y * 9 + cellCoord.x).getStyleClass().add("sudoku__cell--invalid");
                        }
                    });
            digitsCols.values().forEach(ArrayList::clear);
        }
        for(int i = 1; i<=7; i+=3) {
            for(int j = 1; j<=7; j+=3) {
                digitsSquares.get(numbers[j][i]).add(new Coord(i, j));
                digitsSquares.get(numbers[j+1][i]).add(new Coord(i, j+1));
                digitsSquares.get(numbers[j-1][i]).add(new Coord(i, j-1));
                digitsSquares.get(numbers[j][i+1]).add(new Coord(i+1, j));
                digitsSquares.get(numbers[j][i-1]).add(new Coord(i-1, j));
                digitsSquares.get(numbers[j+1][i+1]).add(new Coord(i+1, j+1));
                digitsSquares.get(numbers[j-1][i+1]).add(new Coord(i+1, j-1));
                digitsSquares.get(numbers[j+1][i-1]).add(new Coord(i-1, j+1));
                digitsSquares.get(numbers[j-1][i-1]).add(new Coord(i-1, j-1));

                digitsSquares.values().stream()
                        .filter((list) -> list.size() > 1)
                        .forEach((list)-> {
                            isSudokuDone.set(false);
                            for (Coord cellCoord : list) {
                                cells.get(cellCoord.y * 9 + cellCoord.x).getStyleClass().add("sudoku__cell--invalid");
                            }
                        });
                digitsSquares.values().forEach(ArrayList::clear);
            }
        }
        if(!isSudokuDone.get()) {
            this.sudokuValidationLabel.setText("Sudoku not done!");
        } else {
            //Here we should mark this sudoku as done for current user and disable button and/or sudoku cells
            this.sudokuValidationLabel.setText("Sudoku good");
        }
    }
}
