package src.domain;

import java.awt.*;
import java.util.ArrayList;

public class Human extends Player{
    public Human(Peon peon, String playerName, Color color, int normalBarriers, int temporaryBarriers, int longBarriers, int alliedBarriers) {
        super(peon, playerName, color, normalBarriers, temporaryBarriers, longBarriers, alliedBarriers);
    }

    public ArrayList<String> getPeonValidMovements() {
        return peon.getValidMovements();
    }
}
