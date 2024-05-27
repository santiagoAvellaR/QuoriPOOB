package src.domain;

import java.io.Serializable;

public class MediumStrategyMode extends StrategyMode implements MachineStrategy, Serializable {
    private int rowHorizontalBarrier;
    private int columnHorizontalBarrier;

    private int rowVerticalBarrier;
    private int columnVerticalBarrier;
    @Override
    public void makeMove(Board board, Peon peon, Player otherPlayer, int normalBarriers, int temporaryBarriers, int longBarriers, int alliedBarriers) throws QuoridorException {
        peon.actualizeStrategyInformation();
        otherPlayer.getPeon().actualizeStrategyInformation();
        if (peon.getMinimumNumberMovementsToWin() <= otherPlayer.getPeon().getMinimumNumberMovementsToWin() || (normalBarriers <= 0 && temporaryBarriers <= 0
                && alliedBarriers <= 0 && longBarriers <= 0)){
            this.movementType = "movePeon";
            this.direction = peon.getBestPeonMovement();
            throw new QuoridorException(QuoridorException.MACHINE_MOVE_PEON);
        }
        else{
            this.movementType = "addBarrier";
            String otherPlayerBestMovement = otherPlayer.getPeon().getBestPeonMovement();
        }
    }

    private void bestHorizontalBarrier(){

    }

    private void bestVerticalBarrier(){

    }
}
