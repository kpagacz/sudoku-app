package org.sudokuclub.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.Arrays;

public record Sudoku(int id, String title, int[][] cells, String author) implements Serializable {
    public Sudoku(int id, String title, String cells, String author) throws JsonProcessingException {
        this(id, title, new ObjectMapper().readValue(cells, int[][].class), author);
    }

    @Override
    public String toString() {
        return "Sudoku{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", cells=" + Arrays.deepToString(cells) +
                ", author='" + author + '\'' +
                '}';
    }
}
