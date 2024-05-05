package src.domain;

import java.awt.*;
import java.util.ArrayList;

public class Board {
    public int size;
    private Field[][] board;
    private int turns;
    private Color player1Color;
    private Color player2Color;
    private int midColumn;
    ArrayList<Temporary> temporaries;

    public Board(int size, Color player1Color, Color player2Color) {
        this.size = size;
        board = new Field[2 * size - 1][2 * size - 1];
        turns = 0;
        midColumn = getBoardLimit() % 2 == 0 ? getBoardLimit() / 2 : getBoardLimit() / 2 + 1;
        board[getBoardLimit() - 1][midColumn] = new Peon(getBoardLimit() - 1, midColumn, this, player1Color);
        board[0][midColumn] = new Peon(0, midColumn, this, player2Color);
        this.player1Color = player1Color;
        this.player2Color = player2Color;
    }

    public Peon getPeon1InitialMoment(){
        return turns == 0 ? (Peon)board[getBoardLimit() - 1][midColumn] : null;
    }
    public Peon getPeon2InitialMoment(){
        return turns == 0 ? (Peon)board[0][midColumn] : null;
    }
    public int getTurns(){
        return turns;
    }
    public int getBoardLimit() {
        return board.length;
    }

    public Field getField(int row, int column){
        return board[row][column];
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

    public void movePeon(Color playerColor, int oldRow, int oldColumn, int newRow, int newColumn) throws QuoridorException{
        Color playerWhoseTurnIs = turns % 2 == 0 ? player1Color : player2Color;
        if (!playerColor.equals(playerWhoseTurnIs)) {
            throw new QuoridorException(QuoridorException.PLAYER_NOT_TURN);
        }
        board[newRow][newColumn] = board[oldRow][oldColumn];
        board[oldRow][oldColumn] = null;
    }

    public void addBarrier(int row, int column) throws QuoridorException {
        if(board[row][column]!=null){
            throw new QuoridorException(QuoridorException.BARRIER_ALREADY_CREATED);
        }
    }
}
