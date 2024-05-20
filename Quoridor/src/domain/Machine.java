package src.domain;

import java.awt.Color;
import java.io.Serializable;

public class Machine extends Player implements Serializable {
    String machineMode;
    Board board;

    public Machine(Peon peon, String playerName, Color color,  int normalBarriers, int temporaryBarriers, int longBarriers, int alliedBarriers, String machineMode, Board board){
        super(peon, playerName, color, normalBarriers, temporaryBarriers, longBarriers, alliedBarriers);
        this.machineMode = machineMode;
        this.board = board;
    }

    public void play(){

    }
}
