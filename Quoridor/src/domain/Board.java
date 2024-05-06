package src.domain;

import java.awt.*;
import java.util.ArrayList;

public class Board {
    public int size;
    private Field[][] board;
    private int midColumn;
    ArrayList<Temporary> temporaries;

    public Board(int size, Color player1Color, Color player2Color, int teletransporterSquares, int rewindSquares, int skipTurnSquares) {
        this.size = size;
        board = new Field[2 * size - 1][2 * size - 1];
        midColumn = getBoardLimit() % 2 == 0 ? getBoardLimit() / 2 : getBoardLimit() / 2 + 1;
        board[getBoardLimit() - 1][midColumn] = new Peon(getBoardLimit() - 1, midColumn, this, player1Color);
        board[0][midColumn] = new Peon(0, midColumn, this, player2Color);
        temporaries = new ArrayList<>();
        fieldTheBoard(teletransporterSquares, rewindSquares, skipTurnSquares);
    }

    private void fieldTheBoard(int teletransporterSquares, int rewindSquares, int skipTurnSquares){

    }

    public Peon getPeon1InitialMoment(){
        return Quoridor.turns == 0 ? (Peon)board[getBoardLimit() - 1][midColumn] : null;
    }
    public Peon getPeon2InitialMoment(){
        return Quoridor.turns == 0 ? (Peon)board[0][midColumn] : null;
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

    public void movePeon(int oldRow, int oldColumn, int newRow, int newColumn) throws QuoridorException{
        board[newRow][newColumn] = board[oldRow][oldColumn];
        board[oldRow][oldColumn] = null;
    }

    public void addBarrier(Color playerColor, int row, int column, boolean horizontal, int length, char type) throws QuoridorException {
        if(board[row][column]!=null){
            throw new QuoridorException(QuoridorException.BARRIER_ALREADY_CREATED);
        }
        Barrier barrier = createBarrierGivenTheType(playerColor, horizontal, type);
        if (horizontal) {
            for (int j = 0; j < length; j++) {
                board[row][column + j] = barrier;
            }
        }
    }

    private Barrier createBarrierGivenTheType(Color playerColor, boolean horizontal, char type) throws QuoridorException {
        Barrier barrier = null;
        if (type == 'n'){
            barrier = new Normal(playerColor, horizontal);
        }
        else if (type == 'l') {
            barrier = new Long(playerColor, horizontal);
        }
        else if (type == 'a') {
            barrier = new Allied(playerColor, horizontal);
        }
        else if (type == 't') {
            barrier = new Temporary(playerColor, horizontal);
        }
        else {
            throw new QuoridorException(QuoridorException.INVELID_BARRIER_TYPE);
        }
        if (barrier != null) {
            return barrier;
        }
        return null;
    }
}
