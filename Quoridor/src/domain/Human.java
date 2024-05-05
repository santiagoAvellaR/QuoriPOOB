package src.domain;

import java.awt.*;
import java.util.ArrayList;

public class Human extends Player{
    public Human(Peon peon, String playerName, int remainingNumberBridges,  Color color) {
        super(peon, playerName, remainingNumberBridges, color);
    }

    public ArrayList<String> getPeonValidMovements() {
        return peon.getValidMovements();
    }
}
