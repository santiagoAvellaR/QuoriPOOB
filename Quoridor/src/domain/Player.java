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
    public boolean peonHasAnExit() {
        Boolean[][] visited = new Boolean[Board.size * 2][Board.size * 2];
        for (int i = 0; i < visited.length; i++) {
            for (int j = 0; j < visited[i].length; j++) {
                visited[i][j] = false;
            }
        }
        return peon.hasAnExit(peon.getRow(), peon.getColumn(), visited);
    }
    public Color getColor() {return color;}
    public Peon getPeon() {return peon;}

}
