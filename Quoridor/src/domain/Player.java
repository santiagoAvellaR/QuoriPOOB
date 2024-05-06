package src.domain;

import java.awt.Color;

public abstract class Player {
    protected String name;
    protected Color color;
    protected Peon peon;
    protected int normalBarriers;
    protected int temporaryBarriers;
    protected int longBarriers;
    protected int alliedBarriers;

    public Player(Peon peon, String name, Color color, int normalBarriers, int temporaryBarriers, int longBarriers, int alliedBarriers) {
        this.name = name;
        this.color = color;
        this.peon = peon;
        this.normalBarriers = normalBarriers;
        this.temporaryBarriers = temporaryBarriers;
        this.longBarriers = longBarriers;
        this.alliedBarriers = alliedBarriers;
    }

    public Color getColor() {return color;}
    public Peon getPeon() {return peon;}

    public final void movePeon(String direction) throws QuoridorException {
        peon.move(direction);
    }

}
