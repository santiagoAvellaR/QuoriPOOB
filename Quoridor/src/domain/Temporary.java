package src.domain;

import java.awt.*;

public class Temporary extends Barrier{
    private int remainingTime;
    private int row;
    private int column;

    public Temporary(Color color, boolean horizontal, int row, int column) {
        super(color, horizontal, 2);
        this.row = row;
        this.column = column;
        remainingTime = 5;
    }

    public int getRow(){return row;}
    public int getColumn(){return column;}

    @Override
    public String getType() {
        return "Temporary";
    }

    public void reduceTime() throws QuoridorException {
        remainingTime--;
        if(remainingTime == 0){
            throw new QuoridorException(QuoridorException.ERRAASE_TEMPORARY_BARRIER);
        }
    }
}
