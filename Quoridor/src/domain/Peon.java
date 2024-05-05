package src.domain;

import java.awt.*;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;

public class Peon extends Field{
    private int row;
    private int column;
    private Color color;
    private final Board board;

    public Peon(int row, int column, Board board) {
        this.row = row;
        this.column = column;
        this.board = board;
    }
    public int getRow() {
        return row;
    }
    public int getColumn() {
        return column;
    }
    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Peon) {
            Peon p = (Peon) obj;
            return row == p.row && column == p.column && color.equals(p.color);
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
        if (row == 0 && goesUp){throw new QuoridorException(QuoridorException.INVALID_MOVEMENT);}
        if (row == board.getBoardLimit()-1 && !goesUp){throw new QuoridorException(QuoridorException.INVALID_MOVEMENT);}
        int oldRow = row;
        if (board.hasPeon(row+(2*direction), column)){
            if (row <= 2 && goesUp){throw new QuoridorException(QuoridorException.INVALID_MOVEMENT);}
            if (row >= board.getBoardLimit()-2 && !goesUp){throw new QuoridorException(QuoridorException.INVALID_MOVEMENT);}
            if (board.hasBarrier(row+(3*direction), column)){throw new QuoridorException(QuoridorException.INVALID_MOVEMENT);}
            // validar que donde salta tenga casilla especial
            row += 4*direction;
            board.movePeon(oldRow, column, row, column);
        }
        else {//validar que donde salta tenga casilla especial
            row += 2*direction;
            board.movePeon(oldRow, column, row, column);
        }
    }
    public void moveHorizontal(boolean goesLeft) throws QuoridorException {
        int direction = goesLeft ? -1 : 1;
        if (column == 0 && goesLeft){throw new QuoridorException(QuoridorException.INVALID_MOVEMENT);}
        if (column == board.getBoardLimit()-1 && !goesLeft){throw new QuoridorException(QuoridorException.INVALID_MOVEMENT);}
        int oldColumn = column;
        if (board.hasPeon(row, column+(2*direction))){
            if (column <= 2 && goesLeft){throw new QuoridorException(QuoridorException.INVALID_MOVEMENT);}
            if (column >= board.getBoardLimit()-2 && !goesLeft){throw new QuoridorException(QuoridorException.INVALID_MOVEMENT);}
            if (board.hasBarrier(row, column+(3*direction))){throw new QuoridorException(QuoridorException.INVALID_MOVEMENT);}
            // validar que donde salta tenga casilla especial
            column += 4*direction;
            board.movePeon(oldColumn, column, row, column);
        }
        else {//validar que donde salta tenga casilla especial
            column += 2*direction;
            board.movePeon(oldColumn, column, row, column);
        }
    }
    public void move(char direction) throws QuoridorException{
        if (direction == 'u'){
            moveVertical(true);
        }
        else if (direction == 'd'){
            moveVertical(false);
        }
        else if (direction == 'l'){
            moveHorizontal(true);
        }
        else if (direction == 'r'){
            moveHorizontal(false);
        }
    }

    public ArrayList<Character> getValidMovements(Board board) throws QuoridorException{
        ArrayList<Character> validMovements = new ArrayList<Character>();
        return validMovements;
    }
}
