package src.domain;

import java.awt.Color;
import java.io.Serializable;

public class Machine extends Player implements Serializable {
    private String machineMode;
    private Board board;
    private MachineStrategy strategy;
    private final Player otherPlayer;

    public Machine(Peon peon, String playerName, Color color,  int normalBarriers, int temporaryBarriers, int longBarriers, int alliedBarriers, String machineMode, Board board, Player otherPlayer){
        super(peon, playerName, color, normalBarriers, temporaryBarriers, longBarriers, alliedBarriers);
        this.board = board;
        setStrategy(machineMode);
        this.otherPlayer = otherPlayer;
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

    public void play(){
        strategy.makeMove(board, peon, otherPlayer);
    }

}
