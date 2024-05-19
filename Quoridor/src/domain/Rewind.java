package src.domain;

import java.awt.Color;

public class Rewind extends Square{

    public Rewind(int row, int column, Color color) {
        super(color, row, column);
    }

    @Override
    public String getType(){return hasPeon() ? "ReWindPeon" : "Rewind";}

    @Override
    public void applySpecialAction() throws QuoridorException{
        if(peon != null){
            peon.setPosition(row, column);
            peon.stepBackMovements(2);
            throw new QuoridorException(QuoridorException.PEON_STEPPED_BACK);
        }
    }

}
