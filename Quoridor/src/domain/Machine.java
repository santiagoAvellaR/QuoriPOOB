package src.domain;

import java.awt.Color;

public class Machine extends Player{
    String machineMode;
    Board board;

    public Machine(Peon peon, String playerName, Color color,  int normalBarriers, int temporaryBarriers, int longBarriers, int alliedBarriers, String machineMode, Board board){
        super(peon, playerName, color, normalBarriers, temporaryBarriers, longBarriers, alliedBarriers);
        this.machineMode = machineMode;
        this.board = board;
    }

    public void makeMovement(){

    }
}
