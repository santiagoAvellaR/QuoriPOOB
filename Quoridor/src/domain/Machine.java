package src.domain;

import java.awt.Color;
import java.io.Serializable;
import java.util.HashMap;

public class Machine extends Player implements Serializable {
    private String machineMode;
    private Board board;
    private StrategyMode strategy;
    private final Player otherPlayer;
    private HashMap<String, Integer> lengthBarriersTypes;

    public Machine(Peon peon, String playerName, Color color,  int normalBarriers, int temporaryBarriers, int longBarriers, int alliedBarriers, String machineMode, Board board, Player otherPlayer){
        super(peon, playerName, color, normalBarriers, temporaryBarriers, longBarriers, alliedBarriers);
        this.board = board;
        setStrategy(machineMode);
        this.otherPlayer = otherPlayer;
        this.machineMode = machineMode;
    }

    private void initializeHashMaps(){
        lengthBarriersTypes = new HashMap<>();
        lengthBarriersTypes.put("n", 2);
        lengthBarriersTypes.put("a", 2);
        lengthBarriersTypes.put("t", 2);
        lengthBarriersTypes.put("l", 3);
    }

    public void setStrategy(String machineMode) {
        this.machineMode = machineMode;
        switch (machineMode.toUpperCase()) {
            case "BEGINNER":
                this.strategy = new BeginnerStrategyMode();
                break;
            case "MEDIUM":
                this.strategy = new MediumStrategyMode();
                break;
            case "ADVANCED":
                this.strategy = new AdvancedStrategyMode();
                break;
        }
    }

    public String getMovementType() {
        return strategy.getMovementType();
    }
    public String getDirection() {
        return strategy.getDirection();
    }
    public String getBarrierType() {
        return strategy.getBarrierType();
    }
    public int getRow() {
        return strategy.getRow();
    }
    public int getColumn() {
        return strategy.getColumn();
    }
    public boolean isHorizontal() {
        return strategy.isHorizontal();
    }
    public String getMachineMode(){
        return machineMode;
    }

    public void play() throws QuoridorException {
        try{
            strategy.makeMove(board, peon, otherPlayer, normalBarriers, temporaryBarriers, longBarriers, alliedBarriers);
        }
        catch(QuoridorException e){
            System.out.println("error atrapado en maquina: " + e.getMessage());
            throw e;
        }
    }

}
