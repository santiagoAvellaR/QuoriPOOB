package src.domain;

import java.awt.Color;
import java.io.Serializable;
import java.util.Random;

public class Board implements Serializable{
    public final int size;
    private Field[][] board;
    private Temporary deletedTemporary;
    private Peon peon1;
    private Peon peon2;

    public Board(int size, Color player1Color, String shapePeon1, Color player2Color, String shapePeon2, int transporterSquares, int rewindSquares, int skipTurnSquares) {
        this.size = size;
        board = new Field[2 * size - 1][2 * size - 1];
        int midColumn = size % 2 == 0 ? size - 2 : size - 1;
        Peon peon1 = new Peon(getBoardSize() - 1, midColumn, this, player1Color, 1, shapePeon1);
        board[getBoardSize() - 1][midColumn] = peon1;
        this.peon1 = peon1;
        Peon peon2 = new Peon(0, midColumn, this, player2Color, 2, shapePeon2);
        board[0][midColumn] = peon2;
        this.peon2 = peon2;
        fillTheBoard(transporterSquares, rewindSquares, skipTurnSquares, peon1, peon2);
        System.out.println("tablero inicial");
        printBoard();
    }

    private void fillTheBoard(int transporterSquares, int rewindSquares, int skipTurnSquares, Peon peon1, Peon peon2){
        //fillTheBoardTeleporterSquares(transporterSquares);
        fillTheBoardRewindSquares(rewindSquares, peon1, peon2);
        fillTheBoardSkipTurnSquares(skipTurnSquares);
        fillTheBoardTransporterSquares(transporterSquares);
    }
    private void fillTheBoardTeleporterSquares(int transporterSquares){
        while (transporterSquares > 0) {
            int row1 = evenNumberGenerator(getBoardSize()-1);
            int column1 = evenNumberGenerator(getBoardSize()-1);
            int row2 = evenNumberGenerator(getBoardSize()-1);
            int column2 = evenNumberGenerator(getBoardSize()-1);
            if (board[row1][column1] == null && board[row2][column2] == null){
                Color color = generateColor();
                addTeleporterSquare(row1, column1, row2, column2);
                transporterSquares--;
            }
        }
    }
    private void fillTheBoardRewindSquares(int rewindSquares, Peon peon1, Peon peon2){
        while (rewindSquares > 0) {
            int row = evenNumberGenerator(getBoardSize()-1);
            int column = evenNumberGenerator(getBoardSize()-1);
            if (board[row][column] == null) {
                addRewindSquare(row, column);
                rewindSquares--;
                if (!peon1.hasAnExitMainMethod(peon1.getRow(), peon1.getColumn()) || !peon2.hasAnExitMainMethod(peon2.getRow(), peon2.getColumn())) {
                    board[row][column] = null;
                    rewindSquares ++;
                }
            }
        }
    }
    private void fillTheBoardSkipTurnSquares(int skipTurnSquares){
        while (skipTurnSquares > 0) {
            int row = evenNumberGenerator(getBoardSize()-1);
            int column = evenNumberGenerator(getBoardSize()-1);
            if (board[row][column] == null) {
                addSkipTurnSquare(row, column);
                skipTurnSquares--;
            }
        }
    }private void fillTheBoardTransporterSquares(int transporterSquares){
        while (transporterSquares > 0) {
            int row = evenNumberGenerator(getBoardSize()-1);
            int column = evenNumberGenerator(getBoardSize()-1);
            if (board[row][column] == null) {
                addTransporterSquare(row, column);
                transporterSquares--;
            }
        }
    }
    private int evenNumberGenerator(int limit){
        Random random = new Random();
        return random.nextInt((limit / 2) + 1) * 2;
    }
    private static Color generateColor() {
        Random random = new Random();
        int rojo = random.nextInt(256);
        int verde = random.nextInt(256);
        int azul = random.nextInt(256);
        return new Color(rojo, verde, azul);
    }

    public void printBoard(){
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

    public Color getFieldColor(int row, int column){
        if (board[row][column] != null) {return board[row][column].getColor();}
        return null;
    }

    public Peon getPeon1InitialMoment(){
        int midColumn = size % 2 == 0 ? size - 2 : size - 1;
        return (Peon)board[getBoardSize() - 1][midColumn];
    }
    public Peon getPeon2InitialMoment(){
        int midColumn = size % 2 == 0 ? size - 2 : size - 1;
        return (Peon)board[0][midColumn];
    }

    public int getBoardSize() {return board.length;}

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

    public void movePeon(int oldRow, int oldColumn, int newRow, int newColumn) throws QuoridorException {
        Peon peon;
        if (hasSquare(oldRow, oldColumn) && hasPeon(oldRow, oldColumn)) {
            Square square = (Square)board[oldRow][oldColumn];
            peon = square.getPeon();
            square.setPeon(null);
        }
        else{
            peon = (Peon)board[oldRow][oldColumn];
            board[oldRow][oldColumn] = null;
        }
        if (hasSquare(newRow, newColumn)) {
            Square square = (Square)board[newRow][newColumn];
            square.setPeon(peon);
            square.applySpecialAction();
        }
        else {
            peon.setSquareType("Normal");
            peon.passThroughSquare("N");
            board[newRow][newColumn] = peon;
        }
    }

    public void deleteBarrier(int row, int column, int length, boolean horizontal, Integer lastRowColumn){
        int limit = lastRowColumn == null ? length*2 - 1 : lastRowColumn;
        if (horizontal){
            for (int j = 0; j < limit; j++) {
                board[row][column + j] = null;
            }
        }
        else {
            for (int i = 0; i < limit; i++) {
                board[row + i][column] = null;
            }
        }
    }

    public void addBarrier(Color playerColor, int row, int column, int length, boolean horizontal, String type) throws QuoridorException {
        if(board[row][column]!=null){
            throw new QuoridorException(QuoridorException.BARRIER_ALREADY_CREATED);
        }
        Barrier barrier = createBarrierGivenTheType(playerColor, row, column, horizontal, type);
        addBarrierToTheBoard(row, column, horizontal, barrier);
    }
    private void addBarrierToTheBoard(int row, int column, boolean horizontal, Barrier barrier) throws QuoridorException {
        if(row == getBoardSize() - 1 || column == getBoardSize() - 1){
            if (horizontal) {
                for (int j = 0; j < (barrier.getLength() * 2 - 1); j++) {
                    if (board[row][column - j] != null) {
                        deleteBarrier(row, column, barrier.getLength(), horizontal, j);
                        throw new QuoridorException(QuoridorException.BARRIER_OVERLAP);
                    }
                    board[row][column - j] = barrier;
                }
            }
            else {
                for (int i = 0; i < (barrier.getLength() * 2 - 1); i++) {
                    if (board[row - i][column] != null) {
                        deleteBarrier(row, column, barrier.getLength(), horizontal, i);
                        throw new QuoridorException(QuoridorException.BARRIER_OVERLAP);
                    }
                    board[row - i][column] = barrier;
                }
            }
        }
        else {
            if (horizontal) {
                for (int j = 0; j < barrier.getLength() * 2 - 1; j++) {
                    if (board[row][column + j] != null) {
                        deleteBarrier(row, column, barrier.getLength(), horizontal, j);
                        throw new QuoridorException(QuoridorException.BARRIER_OVERLAP);
                    }
                    board[row][column + j] = barrier;
                }
            } else {
                for (int i = 0; i < barrier.getLength() * 2 - 1; i++) {
                    if (board[row + i][column] != null) {
                        deleteBarrier(row, column, barrier.getLength(), horizontal, i);
                        throw new QuoridorException(QuoridorException.BARRIER_OVERLAP);
                    }
                    board[row + i][column] = barrier;
                }
            }
        }
    }
    private Barrier createBarrierGivenTheType(Color playerColor, int row, int column, boolean horizontal, String type) throws QuoridorException {
        return switch (type) {
            case "n" -> new Normal(playerColor, horizontal);
            case "l" -> new Long(playerColor, horizontal);
            case "a" -> new Allied(playerColor, horizontal);
            case "t" -> new Temporary(playerColor, horizontal, row, column);
            default -> throw new QuoridorException(QuoridorException.INVALID_BARRIER_TYPE);
        };
    }

    public void fieldActEachTurn() throws QuoridorException {
        for (Field[] fieldRow : board){
            for (Field field : fieldRow) {
                if (field != null) {
                    try {
                        field.actEachTurn();
                    } catch (QuoridorException e) {
                        if (e.getMessage().equals(QuoridorException.ERASE_TEMPORARY_BARRIER)) {
                            Temporary temporary = (Temporary) field;
                            deleteTemporaryFromBoard(temporary);
                            deletedTemporary = temporary;
                            throw new QuoridorException(QuoridorException.ERASE_TEMPORARY_BARRIER);
                        }
                        else{
                            throw new QuoridorException(e.getMessage());
                        }
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
    public int[] getPositionDeletedTemporary(){
        return new int[]{deletedTemporary.getRow(), deletedTemporary.getColumn()};
    }
    public boolean getOrientationDeletedTemporary(){return deletedTemporary.isHorizontal();}

    // helpers fill the board and test functions
    public void addRewindSquare(int row, int column) {
        Color color = generateColor();
        board[row][column] = new Rewind(row, column, color);
    }
    public void addTeleporterSquare(int row1, int column1, int row2, int column2) {
        Color color = generateColor();
        Teleporter teleporter1 = new Teleporter(row1, column1, color);
        board[row1][column1] = teleporter1;
        Teleporter teleporter2 = new Teleporter(row2, column2, color);
        board[row2][column2] = teleporter2;
        teleporter1.setOtherTeleporter(teleporter2);
        teleporter2.setOtherTeleporter(teleporter1);
    }
    public void addSkipTurnSquare(int row, int column) {
        Color color = generateColor();
        board[row][column] = new SkipTurn(row, column, color);
    }
    public void addTransporterSquare(int row, int column){
        Color color = generateColor();
        board[row][column] = new Transporter(row, column, color);
    }

    public boolean barrierCanBePlace(int row, int column, int length, boolean isHorizontal) {
        if (row + (length*2)-1 >= getBoardSize() && !isHorizontal) {return false;}
        if (column + (length*2)-1 >= getBoardSize() && isHorizontal) {return false;}
        if (!isHorizontal){
            for (int i = row; i < row + length*2 - 1; i++) {
                if (board[i][column] != null) {
                    return false;
                }
            }
        }
        else {
            for (int j = column; j < column + length*2 - 1; j++) {
                if (board[row][j] != null) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean barrierCanBePlace(int row, int column, int length, boolean isHorizontal, Peon peon1, Peon peon2) {
        if (row + ((length*2)-2) >= getBoardSize() && !isHorizontal) {return false;}
        if (column + ((length*2)-2) >= getBoardSize() && isHorizontal) {return false;}
        if (!isHorizontal){
            for (int i = row; i < row + length*2 - 1; i++) {
                if (board[i][column] != null) {
                    return false;
                }
            }
        }
        else {
            for (int j = column; column + j < length*2 - 1; j++) {
                if (board[row][j] != null) {
                    return false;
                }
            }
        }
        return peon1.hasAnExitMainMethod(peon1.getRow(), peon1.getColumn()) && peon2.hasAnExitMainMethod(peon2.getRow(), peon2.getColumn());
    }

    public void deleteTemporarilyPeonFromBoard(int numberPeon){
        if (numberPeon == 1){
            board[peon1.getRow()][peon1.getColumn()] = null;
        }
        else if (numberPeon == 2){
            board[peon2.getRow()][peon2.getColumn()] = null;
        }
    }

    public void addPeonToBoard(int numberPeon){
        if (numberPeon == 1){
            board[peon1.getRow()][peon1.getColumn()] = peon1;
        }
        else if (numberPeon == 2){
            board[peon2.getRow()][peon2.getColumn()] = peon2;
        }
    }

}