package src.domain;

import java.awt.Color;

public class SkipTurn extends Square{

    public SkipTurn(Color color, int row, int column) {
        super(color, row, column);
    }


    @Override
    public String getType(){return hasPeon() ? "SkipTurnPeon" : "SkipTurn";}

    @Override
    public void applySpecialAction() throws QuoridorException{
        if (peon != null){
            throw new QuoridorException(QuoridorException.PLAYER_PLAYS_TWICE);
        }
    }

}