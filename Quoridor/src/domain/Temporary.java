package src.domain;

import java.awt.*;

public class Temporary extends Barrier{
    private int remainingTurns;
    private int row;
    private int column;

    public Temporary(Color color, boolean horizontal, int row, int column) {
        super(color, horizontal, 2);
        this.row = row;
        this.column = column;
        remainingTurns = 5;
    }

    public int getRow(){return row;}
    public int getColumn(){return column;}

    @Override
    public String getType() {
        return "Temporary";
    }

    public void reduceRemainingTime() throws QuoridorException {
        remainingTurns--;
        if(remainingTurns == 0){
            throw new QuoridorException(QuoridorException.ERRAASE_TEMPORARY_BARRIER);
        }
    }
}
