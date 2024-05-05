package src.domain;

import java.awt.*;
import java.util.ArrayList;

public abstract class Player {
    protected Peon peon;
    protected int remainingBridges;
    protected String name;
    protected Color color;

    public Player(Peon peon, String name, int remainingBridges, Color color) {
        this.peon = peon;
        this.name = name;
        this.remainingBridges = remainingBridges;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public final void movePeon(Color color, String direction) throws QuoridorException {
        peon.move(direction);
    }

}
