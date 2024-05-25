package src.domain;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class Player implements Serializable{
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Player) {
            Player p = (Player) obj;
            return p.getName().equals(this.name) && p.getColor().equals(this.color) && p.getPeon().equals(this.peon);
        }
        return false;
    }

    public ArrayList<String> getPeonValidMovements() {
        return peon.getValidMovements(peon.getRow(), peon.getColumn());
    }
    public int numberBarrier(Color playerColor, String type){
        if (color.equals(playerColor)){
            switch (type) {
                case "n" -> {return normalBarriers;}
                case "t" -> {return temporaryBarriers;}
                case "l" -> {return longBarriers;}
                case "a" -> {return alliedBarriers;}
            }
        }
        return 0;
    }
    public void reduceNumberBarriers(Color playerColor, String barrierType){
        if (color.equals(playerColor)){
            switch (barrierType) {
                case "n" -> normalBarriers -= 1;
                case "t" -> temporaryBarriers -= 1;
                case "l" -> longBarriers -= 1;
                case "a" -> alliedBarriers -= 1;
            }
        }
    }

    public boolean stillHasBarrierType(Color playerColor, String barrierType){
        if (color.equals(playerColor)){
            return switch (barrierType) {
                case "n" -> normalBarriers > 0;
                case "t" -> temporaryBarriers > 0;
                case "l" -> longBarriers > 0;
                case "a" -> alliedBarriers > 0;
                default -> false;
            };
        }
        return false;
    }

    public void movePeon(String direction) throws QuoridorException {
        peon.move(direction);
    }

    public boolean peonHasAnExit() {
        Boolean[][] visited = new Boolean[peon.getBoardSize()/2+1][peon.getBoardSize()/2+1];
        for (Boolean[] booleans : visited) {
            Arrays.fill(booleans, false);
        }
        visited[peon.getRow()/2][peon.getColumn()/2] = true;
        return peon.hasAnExit(peon.getRow(), peon.getColumn(), visited);
    }

    public void peonShortestPath(){
        Integer[][] costs = new Integer[peon.getBoardSize()/2 + 1][peon.getBoardSize()/2 + 1];
        for (Integer[] ints : costs) {
            Arrays.fill(ints, Integer.MAX_VALUE);
        }
        costs[peon.getRow()/2][peon.getColumn()/2] = 0;
        peon.shortestPath(peon.getRow(), peon.getColumn(), new String[peon.getBoardSize()/2 + 1][peon.getBoardSize()/2 + 1], costs, "");
    }

    public String getName(){return name;}
    public Color getColor() {return color;}
    public Peon getPeon() {return peon;}
    public int getPeonRow() {return peon.getRow();}
    public int getPeonColumn() {return peon.getColumn();}
    public int squaresVisited(String type){return peon.squaresVisited(type);}

}