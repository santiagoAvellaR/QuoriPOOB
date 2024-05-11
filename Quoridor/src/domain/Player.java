package src.domain;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

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

    public ArrayList<String> getPeonValidMovements() {
        return peon.getValidMovements();
    }

    public void reduceNumberBarriers(Color playerColor, String barrierType){
        if (color.equals(playerColor)){
            if (barrierType.equals("n")){normalBarriers -= 1;}
            else if (barrierType.equals("t")){temporaryBarriers -= 1;}
            else if (barrierType.equals("l")){longBarriers -= 1;}
            else if (barrierType.equals("a")){alliedBarriers -= 1;}
        }
    }

    public boolean stillHasBarrierType(Color playerColor, String barrierType){
        if (color.equals(playerColor)){
            if (barrierType.equals("n")){return normalBarriers > 0;}
            else if (barrierType.equals("t")){return temporaryBarriers > 0;}
            else if (barrierType.equals("l")){return longBarriers > 0;}
            else if (barrierType.equals("a")){return alliedBarriers > 0;}
            return false;
        }
        return false;
    }

    public boolean peonHasAnExit() {
        Boolean[][] visited = new Boolean[Board.size*2 - 1][Board.size*2 - 1];
        for (int i = 0; i < visited.length; i++) {
            Arrays.fill(visited[i], false);
        }
        peon.setHasFoundAndExit(false);
        return peon.hasAnExit(peon.getRow(), peon.getColumn(), visited);
    }
    public Color getColor() {return color;}
    public Peon getPeon() {return peon;}
    public int getPeonRow() {return peon.getRow();}
    public int getPeonColumn() {return peon.getColumn();}

}
