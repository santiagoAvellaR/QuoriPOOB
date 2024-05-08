package src.domain;

import java.awt.*;
import java.util.ArrayList;

public class Board {
    public static int size;
    private Field[][] board;
    private final int midColumn;
    private Temporary deletedTemporary;

    public Board(int size, Color player1Color, Color player2Color, int teletransporterSquares, int rewindSquares, int skipTurnSquares) {
        this.size = size;
        board = new Field[2 * size - 1][2 * size - 1];
        midColumn = size % 2 == 0 ? size - 2 : size - 1;
        board[getBoardLimit() - 1][midColumn] = new Peon(getBoardLimit() - 1, midColumn, this, player1Color, 1);
        board[0][midColumn] = new Peon(0, midColumn, this, player2Color, 2);
        fillTheBoard(teletransporterSquares, rewindSquares, skipTurnSquares);
        printBoard();
    }

    private void printBoard(){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] != null) {System.out.print(board[i][j].getType() + " ");}
                else {System.out.print("null ");}
            }
            System.out.println();
        }
        System.out.println();
    }

    public String getTypeField(int row, int column){
        if (board[row][column] != null) {return board[row][column].getType();}
        return "Empty";
    }

    private void fillTheBoard(int teletransporterSquares, int rewindSquares, int skipTurnSquares){

    }

    public Peon getPeon1InitialMoment(){return Quoridor.turns.equals(0) ? (Peon)board[getBoardLimit() - 1][midColumn] : null;}
    public Peon getPeon2InitialMoment(){return Quoridor.turns.equals(0) ? (Peon)board[0][midColumn] : null;}

    public int getBoardLimit() {return board.length;}

    public Field getField(int row, int column){return board[row][column];}

    public Field[][] getBoard() {return board;}

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
        printBoard();
    }

    public void addBarrier(Color playerColor, int row, int column, boolean horizontal, char type) throws QuoridorException {
        if(board[row][column]!=null){
            throw new QuoridorException(QuoridorException.BARRIER_ALREADY_CREATED);
        }
        Barrier barrier = createBarrierGivenTheType(playerColor, row, column, horizontal, type);
        addBarrierToTheBoard(row, column, horizontal, barrier);
        printBoard();
    }
    private void addBarrierToTheBoard(int row, int column, boolean horizontal, Barrier barrier){
        if (horizontal){
            for (int j = 0; j < barrier.getLength()*2 - 1; j++) {
                board[row][column + j] = barrier;
            }
        }
        else {
            for (int i = 0; i < barrier.getLength()*2 - 1; i++) {
                board[row + i][column] = barrier;
            }
        }
    }
    private Barrier createBarrierGivenTheType(Color playerColor, int row, int column, boolean horizontal, char type) throws QuoridorException {
        Barrier barrier = null;
        if (type == 'n'){
            barrier = new Normal(playerColor, horizontal);
            return barrier;
        }
        else if (type == 'l') {
            barrier = new Long(playerColor, horizontal);
            return barrier;
        }
        else if (type == 'a') {
            barrier = new Allied(playerColor, horizontal);
            return barrier;
        }
        else if (type == 't') {
            barrier = new Temporary(playerColor, horizontal, row, column);
            return barrier;
        }
        else {
            throw new QuoridorException(QuoridorException.INVELID_BARRIER_TYPE);
        }
    }

    public void fieldAct() throws QuoridorException {
        for (Field[] fieldRow : board){
            for (Field field : fieldRow) {
                try {
                    field.act();
                } catch (QuoridorException e) {
                    if (e.getMessage().equals(QuoridorException.ERRAASE_TEMPORARY_BARRIER)) {
                        Temporary temporary = (Temporary)field;
                        deleteTemporaryFromBoard(temporary);
                        deletedTemporary = temporary;
                        throw new QuoridorException(QuoridorException.ERRAASE_TEMPORARY_BARRIER);
                    }
                }
            }
        }
    }
    private void deleteTemporaryFromBoard(Temporary temporary){
        int column = temporary.getColumn();
        int row = temporary.getRow();
        int length = temporary.getLength();
        if (temporary.isHorizontal()){
            for (int j = 0; j < length*2 - 1; j++) {
                board[row][column + j] = null;
            }
        }
        else {
            for (int i = 0; i < temporary.getLength()*2 - 1; i++) {
                board[row + i][column] = null;
            }
        }
    }
    public int[] getPsotionsDeletedTemporary(){
        return new int[]{deletedTemporary.getRow(), deletedTemporary.getColumn()};
    }

}
