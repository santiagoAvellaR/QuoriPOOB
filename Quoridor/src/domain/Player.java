package src.domain;

import java.awt.*;

public abstract class Player {
    private Peon peon;
    private int remainingBridges;
    private String name;
    private Color color;

    public Player(Peon peon, String name, int remainingBridges, Color color) {
        this.peon = peon;
        this.name = name;
        this.remainingBridges = remainingBridges;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public abstract void addBarrier();
    public abstract void movePeon(int row, int col);
}
