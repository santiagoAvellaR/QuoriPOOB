package src.domain;

import java.awt.*;

public class Board {
    public int size;
    private Field[][] board;
    private int turns;

    public Board(int size, Color player1Color, Color player2Color) {
        this.size = size;
        board = new Field[2 * size - 1][2 * size - 1];
        turns = 0;
        board[getBoardLimit() - 1][getBoardLimit() % 2 == 0 ? getBoardLimit() / 2 : getBoardLimit() + 1] = new Peon(getBoardLimit() - 1, getBoardLimit() % 2 == 0 ? getBoardLimit() / 2 : getBoardLimit() + 1, this);
        board[0][getBoardLimit() % 2 == 0 ? getBoardLimit() / 2 : getBoardLimit() + 1] = new Peon(0, getBoardLimit() % 2 == 0 ? getBoardLimit() / 2 : getBoardLimit() + 1, this);
    }

    public Peon getPeon1InitialMoment(){
        return turns == 0 ? (Peon)board[getBoardLimit() - 1][getBoardLimit() % 2 == 0 ? getBoardLimit() / 2 : getBoardLimit() + 1] : null;
    }
    public Peon getPeon2InitialMoment(){
        return turns == 0 ? (Peon)board[0][getBoardLimit() % 2 == 0 ? getBoardLimit() / 2 : getBoardLimit() + 1] : null;
    }
    public int getTurns(){
        return turns;
    }
    public int getBoardLimit() {
        return board.length;
    }

    public boolean hasBarrier(int row, int column) {
        if (board[row][column] != null) {
            return board[row][column].hasBarrier();
        }
        return false;
    }

    public boolean hasSquare(int row, int column) {
        if (board[row][column] != null) {
            return board[row][column].hasSquare();
        }
        return false;
    }

    public boolean hasPeon(int row, int column) {
        if (board[row][column] != null) {
            return board[row][column].hasPeon();
        }
        return false;
    }

    public void movePeon(int oldRow, int oldColumn, int newRow, int newColumn) {
        board[newRow][newColumn] = board[oldRow][oldColumn];
        board[oldRow][oldColumn] = null;
    }

    public void addBarrier(int row, int column) throws QuoridorException {
        if(board[row][column]!=null){
        }
    }
}
