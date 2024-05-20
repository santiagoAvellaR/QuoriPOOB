package src.domain;

import java.awt.*;
import java.io.Serializable;

public class Temporary extends Barrier implements Serializable {
    private int remainingTurns;
    private int row;
    private int column;

    public Temporary(Color color, boolean horizontal, int row, int column) {
        super(color, horizontal, 2);
        this.row = row;
        this.column = column;
        remainingTurns = 10;
    }

    public int getRow(){return row;}
    public int getColumn(){return column;}

    @Override
    public String getType() {
        return "Temporary";
    }

    @Override
    public void actEachTurn() throws QuoridorException {
        remainingTurns -= 1;
        if(remainingTurns == 0){
            throw new QuoridorException(QuoridorException.ERASE_TEMPORARY_BARRIER);
        }
    }
}
