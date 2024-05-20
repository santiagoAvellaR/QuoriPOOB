package src.domain;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Human extends Player implements Serializable {
    public Human(Peon peon, String playerName, Color color, int normalBarriers, int temporaryBarriers, int longBarriers, int alliedBarriers) {
        super(peon, playerName, color, normalBarriers, temporaryBarriers, longBarriers, alliedBarriers);
    }

}
