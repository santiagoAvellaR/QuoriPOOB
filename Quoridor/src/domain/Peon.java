package src.domain;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Peon extends Field{
    private int playerNumber;
    private int row;
    private int column;
    private final Board board;
    private ArrayList<String> tracker;
    private boolean hasFoundAndExit;

    public Peon(int row, int column, Board board, Color color, int numberPlayer) {
        super(color);
        this.row = row;
        this.column = column;
        this.board = board;
        tracker = new ArrayList<String>();
        this.playerNumber = numberPlayer;
        hasFoundAndExit = false;
    }
    public int getRow() {
        return row;
    }
    public int getColumn() {
        return column;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    public void setHasFoundAndExit(boolean hasFoundAndExit) {this.hasFoundAndExit = hasFoundAndExit;}
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Peon) {
            Peon p = (Peon) obj;
            return (((row == p.row) && (column == p.column)) && color.equals(p.getColor()));
        }
        return false;
    }
    @Override
    public String getType(){
        return "Peon";
    }
    @Override
    public boolean hasBarrier(){return false;}
    @Override
    public boolean hasSquare(){return false;}
    @Override
    public boolean hasPeon(){return true;}
    @Override
    public void act() throws QuoridorException {
        if((playerNumber == 1 && row == 0)){
            throw new QuoridorException(QuoridorException.PLAYER_ONE_WON);
        }
        else if ((playerNumber == 2 && row == board.getBoardSize()-1)) {
            throw new QuoridorException(QuoridorException.PLAYER_TWO_WON);
        }
    }

    public void moveVertical(boolean goesUp) throws QuoridorException {
        int direction = goesUp ? -1 : 1;
        board.movePeon(row, column, row + direction*2, column);
        row += direction*2;
        tracker.add(goesUp ? "s" : "n");
    }
    public void moveHorizontal(boolean goesLeft) throws QuoridorException {
        int direction = goesLeft ? -1 : 1;
        board.movePeon(row, column, row, column + direction*2);
        column += direction*2;
        tracker.add(goesLeft ? "w" : "e");
    }
    public void jumpVertical(boolean goesUp) throws QuoridorException {
        int direction = goesUp ? -1 : 1;
        board.movePeon(row, column, row + direction*4, column);
        row += direction*4;
        tracker.add(goesUp ? "js" : "jn");
    }
    public void jumpHorizontal(boolean goesLeft) throws QuoridorException {
        int direction = goesLeft ? -1 : 1;
        board.movePeon(row, column, row, column + direction*4);
        column += direction*4;
        tracker.add(goesLeft ? "jw" : "je");
    }

    public void move(String direction) throws QuoridorException{
        if (direction.equals("n")){moveVertical(true);}
        else if (direction.equals("s")){moveVertical(false);}
        else if (direction.equals("w")){moveHorizontal(true);}
        else if (direction.equals("e")){moveHorizontal(false);}
        else if (direction.equals("jn")){jumpVertical(true);}
        else if (direction.equals("js")){jumpVertical(false);}
        else if (direction.equals("jw")){jumpHorizontal(true);}
        else if (direction.equals("je")){jumpHorizontal(false);}
    }

    public ArrayList<String> getValidMovements(){
        ArrayList<String> validMovements = new ArrayList<String>();
        validMovements = validateVertical(validMovements, true);
        validMovements = validateVertical(validMovements, false);
        validMovements = validateHorizontal(validMovements, true);
        validMovements = validateHorizontal(validMovements, false);
        System.out.println((Arrays.toString(validMovements.toArray(new String[0]))));
        return validMovements;
    }

    public ArrayList<String> validateVertical(ArrayList<String> validMovementsCalculated, boolean goesUp){
        ArrayList<String> validVericalMovements = validMovementsCalculated;
        if (row == 0 && goesUp) {return validVericalMovements;}
        if (row == board.getBoardSize()-1 && !goesUp) {return validVericalMovements;}
        int direction = goesUp ? -1 : 1;
        // verificar salto Simple
        if (board.hasBarrier(row + direction, column)) {
            Barrier barrier = (Barrier) board.getField(row + direction, column);
            if (!barrier.isAllied(color)) {
                return validVericalMovements;
            }
        }
        if (!board.hasPeon(row + 2*direction, column)){
            String directionString = goesUp ? "n" : "s";
            validVericalMovements.add(directionString);
            return validVericalMovements;
        }
        if (row <= 2 && goesUp){return validVericalMovements;}
        if (row >= board.getBoardSize()-3 && !goesUp){return validVericalMovements;}
        // verificar salto Doble
        if (board.hasBarrier(row + 3*direction, column)) {
            Barrier barrier = (Barrier) board.getField(row + 3*direction, column);
            if (!barrier.isAllied(color)) {
                return validVericalMovements;
            }
        }
        if (!board.hasPeon(row + 4*direction, column)) {
            String directionString = goesUp ? "jn" : "js";
            validVericalMovements.add(directionString);
            return validVericalMovements;
        }
        return validateVerticalDiagonals(row, column, validVericalMovements, goesUp);
    }
    private ArrayList<String> validateVerticalDiagonals(int row, int column, ArrayList<String> validVerticalMovements, boolean goesUp){
        int direction = goesUp ? -1 : 1;
        String directionString = goesUp ? "nw" : "sw";
        if (!board.hasBarrier(row+2*direction, column - 1) && !validVerticalMovements.contains(directionString)){
            validVerticalMovements.add(directionString);
        }
        directionString = goesUp ? "ne" : "se";
        if (!board.hasBarrier(row+2*direction, column + 1) && !validVerticalMovements.contains(directionString)){
            validVerticalMovements.add(directionString);
        }
        return validVerticalMovements;
    }

    public ArrayList<String> validateHorizontal(ArrayList<String> validMovementsCalculated, boolean goesLeft){
        ArrayList<String> validHorizontalMovements = validMovementsCalculated;
        if (column == 0 && goesLeft) {return validHorizontalMovements;}
        if (column == board.getBoardSize()-1 && !goesLeft) {return validHorizontalMovements;}
        int direction = goesLeft ? -1 : 1;
        // verificar salto Simple
        if (board.hasBarrier(row, column + direction)) {
            Barrier barrier = (Barrier) board.getField(row, column + direction);
            if (!barrier.isAllied(color)) {
                return validHorizontalMovements;
            }
        }
        if (!board.hasPeon(row, column + 2*direction)) {
            String directionString = goesLeft ? "w" : "e";
            validHorizontalMovements.add(directionString);
            return validHorizontalMovements;
        }
        if (column <= 2 && goesLeft){return validHorizontalMovements;}
        if (column >= board.getBoardSize()-3 && !goesLeft){return validHorizontalMovements;}
        // verificar salto Doble
        if (board.hasBarrier(row, column + 3*direction)) {
            Barrier barrier = (Barrier) board.getField(row, column + 3*direction);
            if (!barrier.isAllied(color)) {
                return validHorizontalMovements;
            }
        }
        if (!board.hasPeon(row, column + 4*direction)) {
            String directionCharacter = goesLeft ? "je" : "jw";
            validHorizontalMovements.add(directionCharacter);
            return validHorizontalMovements;
        }
        return validateHorizontalDiagonals(row, column, validHorizontalMovements, goesLeft);
    }
    private ArrayList<String> validateHorizontalDiagonals(int row, int column, ArrayList<String> validVerticalMovements, boolean goesLeft){
        int direction = goesLeft ? -1 : 1;
        String directionString = goesLeft ? "nw" : "ne";
        if (!board.hasBarrier(row+1, column + 2*direction) && !validVerticalMovements.contains(directionString)){
            validVerticalMovements.add(directionString);
        }
        directionString = goesLeft ? "sw" : "se";
        if (!board.hasBarrier(row-1, column + 2*direction) && !validVerticalMovements.contains(directionString)){
            validVerticalMovements.add(directionString);
        }
        return validVerticalMovements;
    }

    public boolean hasAnExit(int simulateRow, int simulateColumn, Boolean[][] positionsVisited){
        if (simulateRow == 0  && playerNumber == 1){return true;}
        if (simulateRow == board.getBoardSize()-1 && playerNumber == 2){return true;}
        positionsVisited[simulateRow][simulateColumn] = true;
        ArrayList<String> validDirections = getValidMovementsExits(simulateRow, simulateColumn);
        for (String direction : validDirections){
            int[] newPosition = getTheNewPositionAccordingDirection(simulateRow, simulateColumn, direction);
            if (!positionsVisited[newPosition[0]][newPosition[1]] && !hasFoundAndExit) {
                if (hasAnExit(newPosition[0], newPosition[1], positionsVisited)) {
                    hasFoundAndExit = true;
                    return true;
                }
            }
        }
        return false;
    }
    private ArrayList<String> getValidMovementsExits(int simulateRow, int simulateColumn){
        ArrayList<String> movements = validateVerticalExits(simulateRow, simulateColumn, new ArrayList<String>(), true);
        movements = validateVerticalExits(simulateRow, simulateColumn, movements, false);
        movements = validateHorizontalExits(simulateRow, simulateColumn, movements, true);
        movements = validateHorizontalExits(simulateRow, simulateColumn, movements, false);
        return movements;
    }
    private ArrayList<String> validateVerticalExits(int row, int column, ArrayList<String> validMovementsCalculated, boolean goesUp){
        ArrayList<String> validVericalMovements = validMovementsCalculated;
        if (row == 0 && goesUp) {return validVericalMovements;}
        if (row == board.getBoardSize()-1 && !goesUp) {return validVericalMovements;}
        int direction = goesUp ? -1 : 1;
        // verificar salto Simple
        if (board.hasBarrier(row + direction, column)) {
            return validVericalMovements;
        }
        if (!board.hasPeon(row + 2*direction, column)){
            String directionString = goesUp ? "n" : "s";
            validVericalMovements.add(directionString);
            return validVericalMovements;
        }
        if (row <= 2 && goesUp){return validVericalMovements;}
        if (row >= board.getBoardSize()-3 && !goesUp){return validVericalMovements;}
        // verificar salto Doble
        if (board.getField(row + 3*direction, column) != null) {
            return validVericalMovements;
        }
        if (!board.hasPeon(row + 4*direction, column)) {
            String directionCharacter = goesUp ? "jn" : "js";
            validVericalMovements.add(directionCharacter);
            return validVericalMovements;
        }
        return validVericalMovements;
    }
    public ArrayList<String> validateHorizontalExits(int row, int column, ArrayList<String> validMovementsCalculated, boolean goesLeft){
        ArrayList<String> validHorizontalMovements = validMovementsCalculated;
        if (column == 0 && goesLeft) {return validHorizontalMovements;}
        if (column == board.getBoardSize()-1 && !goesLeft) {return validHorizontalMovements;}
        int direction = goesLeft ? -1 : 1;
        // verificar salto Simple
        if (board.getField(row, column + direction) != null) {
            return validHorizontalMovements;
        }
        if (!board.hasPeon(row, column + 2*direction)) {
            String directionString = goesLeft ? "w" : "e";
            validHorizontalMovements.add(directionString);
            return validHorizontalMovements;
        }
        if (column <= 2 && goesLeft){return validHorizontalMovements;}
        if (column >= board.getBoardSize()-2 && !goesLeft){return validHorizontalMovements;}
        // verificar salto Doble
        if (board.getField(row, column + 3*direction) != null) {
            return validHorizontalMovements;
        }
        if (board.hasPeon(row, column + 4*direction)) {
            String directionCharacter = goesLeft ? "je" : "jw";
            validHorizontalMovements.add(directionCharacter);
            return validHorizontalMovements;
        }
        return validHorizontalMovements;
    }
    private int[] getTheNewPositionAccordingDirection(int row, int column, String direction){
        if (direction.equals("n")){return new int[]{row - 2,column};}
        else if (direction.equals("s")){return new int[]{row + 2,column};}
        else if (direction.equals("w")){return new int[]{row,column - 2};}
        else if (direction.equals("e")){return new int[]{row,column + 2};}
        else if (direction.equals("jn")){return new int[]{row - 4,column};}
        else if (direction.equals("js")){return new int[]{row + 4,column};}
        else if (direction.equals("jw")){return new int[]{row,column - 4};}
        else if (direction.equals("je")){return new int[]{row,column + 4};}
        return new int[]{};
    }

}
