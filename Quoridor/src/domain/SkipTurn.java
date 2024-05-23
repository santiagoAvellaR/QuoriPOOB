package src.domain;

import java.awt.Color;
import java.io.Serializable;

public class SkipTurn extends Square implements Serializable {

    public SkipTurn(int row, int column, Color color) {
        super(color, row, column);
    }


    @Override
    public String getType(){return hasPeon() ? "SkipTurnPeon" : "SkipTurn";}

    @Override
    public void applySpecialAction() throws QuoridorException{
        if (hasPeon()){
            peon.setSquareType("SkipTurn");
            peon.passThroughSquare("S");
            throw new QuoridorException(QuoridorException.PLAYER_PLAYS_TWICE);
        }
    }

}
