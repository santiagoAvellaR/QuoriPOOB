package src.domain;

import java.awt.*;
import java.util.ArrayList;

public class Peon extends Field{
    private int row;
    private int column;
    private final Board board;
    private ArrayList<String> tracker;

    public Peon(int row, int column, Board board, Color color) {
        super(color);
        this.row = row;
        this.column = column;
        this.board = board;
        tracker = new ArrayList<String>();
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
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Peon) {
            Peon p = (Peon) obj;
            return (((row == p.row) && (column == p.column)) && color.equals(p.getColor()));
        }
        return false;
    }
    public String getType(){
        return "Peon";
    }
    @Override
    public boolean hasBarrier(){return false;}
    @Override
    public boolean hasSquare(){return false;}
    @Override
    public boolean hasPeon(){return true;}

    public void moveVertical(boolean goesUp) throws QuoridorException {
        int direction = goesUp ? -1 : 1;
        board.movePeon(row, column, row + direction*2, column);
        row += direction*2;
        tracker.add(goesUp ? "d" : "u");
    }
    public void moveHorizontal(boolean goesLeft) throws QuoridorException {
        int direction = goesLeft ? -1 : 1;
        board.movePeon(row, column, row, column + direction*2);
        column += direction*2;
        tracker.add(goesLeft ? "l" : "r");
    }

    public void move(String direction) throws QuoridorException{
        if (direction.equals("u")){
            moveVertical(true);
        }
        else if (direction.equals("d")){
            moveVertical(false);
        }
        else if (direction.equals("l")){
            moveHorizontal(true);
        }
        else if (direction.equals("r")){
            moveHorizontal(false);
        }
    }

    public ArrayList<String> getValidMovements(){
        ArrayList<String> validMovements = new ArrayList<String>();
        validMovements = validateVertical(validMovements, true);
        validMovements = validateVertical(validMovements, false);
        validMovements = validateHorizontal(validMovements, true);
        validMovements = validateHorizontal(validMovements, false);
        return validMovements;
    }

    public ArrayList<String> validateVertical(ArrayList<String> validMovementsCalculated, boolean goesUp){
        ArrayList<String> validVericalMovements = validMovementsCalculated;
        if (row == 0 && goesUp) {return validVericalMovements;}
        if (row == board.getBoardLimit()-1 && !goesUp) {return validVericalMovements;}
        int direction = goesUp ? -1 : 1;
        // verificar salto Simple
        if (board.getField(row + direction, column) != null) {
            Barrier barrier = (Barrier) board.getField(row + direction, column);
            if (!barrier.isAllied(color)) {
                return validVericalMovements;
            }
        }
        if (!board.hasPeon(row + 2*direction, column)){
            String directionString = goesUp ? "u" : "d";
            validVericalMovements.add(directionString);
            return validVericalMovements;
        }
        if (row <= 2 && goesUp){return validVericalMovements;}
        if (row >= board.getBoardLimit()-2 && !goesUp){return validVericalMovements;}
        // verificar salto Doble
        if (board.getField(row + 3*direction, column) != null) {
            Barrier barrier = (Barrier) board.getField(row + 3*direction, column);
            if (!barrier.isAllied(color)) {
                return validVericalMovements;
            }
        }
        if (board.hasPeon(row + 4*direction, column)) {
            String directionCharacter = goesUp ? "ju" : "jd";
            validVericalMovements.add(directionCharacter);
            return validVericalMovements;
        }
        return validVericalMovements;
    }

    public ArrayList<String> validateHorizontal(ArrayList<String> validMovementsCalculated, boolean goesLeft){
        ArrayList<String> validHorizontalMovements = validMovementsCalculated;
        if (column == 0 && goesLeft) {return validHorizontalMovements;}
        if (column == board.getBoardLimit()-1 && !goesLeft) {return validHorizontalMovements;}
        int direction = goesLeft ? -1 : 1;
        // verificar salto Simple
        if (board.getField(row, column + direction) != null) {
            Barrier barrier = (Barrier) board.getField(row, column + direction);
            if (!barrier.isAllied(color)) {
                return validHorizontalMovements;
            }
        }
        if (board.hasPeon(row, column + 2*direction)) {
            String directionString = goesLeft ? "u" : "d";
            validHorizontalMovements.add(directionString);
            return validHorizontalMovements;
        }
        if (column <= 2 && goesLeft){return validHorizontalMovements;}
        if (column >= board.getBoardLimit()-2 && !goesLeft){return validHorizontalMovements;}
        // verificar salto Doble
        if (board.getField(row, column + 3*direction) != null) {
            Barrier barrier = (Barrier) board.getField(row, column + 3*direction);
            if (!barrier.isAllied(color)) {
                return validHorizontalMovements;
            }
        }
        if (board.hasPeon(row, column + 4*direction)) {
            String directionCharacter = goesLeft ? "ju" : "jd";
            validHorizontalMovements.add(directionCharacter);
            return validHorizontalMovements;
        }
        return validHorizontalMovements;
    }
}
