package src.domain;

import java.awt.*;

public class Human extends Player{
    public Human(Peon peon, String playerName, int remainingNumberBridges,  Color color) {
        super(peon, playerName, remainingNumberBridges, color);
    }

    @Override
    public void addBarrier() {

    }
}
