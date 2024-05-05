package src.domain;

import java.awt.*;
import java.util.ArrayList;

public class Peon extends Field{
    private int row;
    private int column;
    private final Board board;

    public Peon(int row, int column, Board board, Color color) {
        super(color);
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

    public void moveVertical(Color playerColor, boolean goesUp) throws QuoridorException {
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
            board.movePeon(playerColor, oldRow, column, row, column);
        }
        else {//validar que donde salta tenga casilla especial
            row += 2*direction;
            board.movePeon(playerColor, oldRow, column, row, column);
        }
    }
    public void moveHorizontal(Color playerColor, boolean goesLeft) throws QuoridorException {
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
            try{
                board.movePeon(playerColor, oldColumn, column, row, column);
            }
            catch(QuoridorException e) {
                if (e.getMessage().equals(QuoridorException.PLAYER_NOT_TURN)) {
                    column += 4 * direction * -1;
                    throw new QuoridorException(QuoridorException.PLAYER_NOT_TURN);
                }
            }
        }
        else {//validar que donde salta tenga casilla especial
            column += 2*direction;
            try{
                board.movePeon(playerColor, oldColumn, column, row, column);
            }
            catch(QuoridorException e) {
                if (e.getMessage().equals(QuoridorException.PLAYER_NOT_TURN)) {
                    column += 2 * direction * -1;
                    throw new QuoridorException(QuoridorException.PLAYER_NOT_TURN);
                }
            }
        }
    }

    public void move(Color playerColor, char direction) throws QuoridorException{
        if (direction == 'u'){
            moveVertical(playerColor, true);
        }
        else if (direction == 'd'){
            moveVertical(playerColor, false);
        }
        else if (direction == 'l'){
            moveHorizontal(playerColor, true);
        }
        else if (direction == 'r'){
            moveHorizontal(playerColor, false);
        }
    }

    public ArrayList<Character> getValidMovements() throws QuoridorException{
        ArrayList<Character> validMovements = new ArrayList<Character>();
        // validate up
        if (validateUp()){validMovements.add('u');}
        return validMovements;
    }

    public boolean validateUp(){
        // verificar barrera
        boolean hasBarrier = false;
        if (board.getField(row-1, column) != null){
            Barrier barrier = (Barrier) board.getField(row-1, column);
            if (!barrier.isAllied(color)){
                hasBarrier = true;
            }
        }
        boolean hasPeon = false;
        return !hasBarrier && !hasPeon;
    }
}
