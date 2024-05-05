package src.domain;

import java.awt.*;

public class Machine extends Player{
    String machineMode;

    public Machine(Peon peon, String playerName, int remainingNumberBridges,  Color color, String machineMode){
        super(peon, playerName, remainingNumberBridges, color);
        this.machineMode = machineMode;
    }

    public void makeMovement(){

    }
}
