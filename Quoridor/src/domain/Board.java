package src.domain;

import java.awt.*;

public class Board {

    public static int size;
    private Field[][] board;
    private Color color;

    public Board(int size, Color color, String player1Name, String player2Name) {
        this.size = size;
        this.color = color;
        board = new Field[size][size];
    }

    public boolean hasBarrier(int row, int column){
        if(board[row][column] != null){
            return board[row][column].hasBarrier();
        }
        return false;
    }
    public boolean hasSquare(int row, int column){
        if(board[row][column] != null){
            return board[row][column].hasSquare();
        }
        return false;
    }
}
