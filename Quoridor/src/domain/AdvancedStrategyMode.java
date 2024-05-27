package src.domain;

import java.io.Serializable;

public class AdvancedStrategyMode extends StrategyMode implements MachineStrategy, Serializable {

    @Override
    public void makeMove(Board board, Peon peon, Player otherPlayer, int normalBarriers, int temporaryBarriers, int longBarriers, int alliedBarriers){

    }
}
