package src.domain;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class MediumStrategyMode extends StrategyMode implements MachineStrategy, Serializable {
    private int rowHorizontalBarrier = 0;
    private int columnHorizontalBarrier = 0;
    private String horizontalBarrierType = "";
    private int oponentStepsHorizontalBarrier = 0;
    private int machineStepsHorizontalBarrier = 0;

    private int rowVerticalBarrier = 0;
    private int columnVerticalBarrier = 0;
    private String verticalBarrierType = "";
    private int oponentStepsVerticalBarrier = 0;
    private int machineStepsVerticalBarrier = 0;

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
            ArrayList<String> availableBarriers = getAvailableBarriers(normalBarriers, temporaryBarriers, longBarriers, alliedBarriers);
            bestHorizontalBarrier(board, peon.getMinimumNumberMovementsToWin(), otherPlayer.getPeon().getMinimumNumberMovementsToWin(), availableBarriers,
                    peon, otherPlayer);
            bestVerticalBarrier(board, peon.getMinimumNumberMovementsToWin(), otherPlayer.getPeon().getMinimumNumberMovementsToWin(), availableBarriers,
                    peon, otherPlayer);
            if (oponentStepsHorizontalBarrier < oponentStepsVerticalBarrier){
                this.row = rowHorizontalBarrier;
                this.column = columnHorizontalBarrier;
                this.barrierType = horizontalBarrierType;
                this.isHorizontal = true;
            }
            else {
                this.row = rowVerticalBarrier;
                this.column = columnVerticalBarrier;
                this.barrierType = verticalBarrierType;
                this.isHorizontal = false;
            }
            throw new QuoridorException(QuoridorException.MACHINE_ADD_A_BARRIER);
        }
    }

    private ArrayList<String> getAvailableBarriers(int normalBarriers, int temporaryBarriers, int longBarriers, int alliedBarriers){
        ArrayList<String> availableBarriers = new ArrayList<>();
        if (alliedBarriers > 0){
            availableBarriers.add("a");
        }
        if (temporaryBarriers > 0){
            availableBarriers.add("t");
        }
        if (normalBarriers > 0){
            availableBarriers.add("n");
        }
        if (longBarriers > 0){
            availableBarriers.add("l");
        }
        if (availableBarriers.contains("t") && availableBarriers.contains("n")){
            availableBarriers.remove("t");
        }
        return availableBarriers;
    }

    private void bestHorizontalBarrier(Board board, int minimumNumberMovementsMe, int minimumNumberMovementsOponent, ArrayList<String> availableBarriers,
                                       Peon peon, Player otherPlayer) throws QuoridorException {
        for (String barrier : availableBarriers){
            int lenght = barrier.equals("l") ? 3 : 2;
            for (int i = 1; i < board.getBoardSize(); i+=2){
                for (int j = 0; j < board.getBoardSize(); j+=2){
                    if (board.getTypeField(i, j).equals("Empty") && board.barrierCanBePlace(i, j, lenght, true)){
                        try{
                            board.addBarrier(Color.WHITE, i, j, lenght, true, barrier);
                            peon.actualizeStrategyInformation();
                            otherPlayer.getPeon().actualizeStrategyInformation();
                            board.deleteBarrier(i, j, lenght, true, null);
                            if (peon.getMinimumNumberMovementsToWin() <= minimumNumberMovementsMe && otherPlayer.getPeon().getMinimumNumberMovementsToWin() > minimumNumberMovementsOponent){
                                rowHorizontalBarrier = i;
                                columnHorizontalBarrier = j;
                                horizontalBarrierType = barrier;
                            }
                        }
                        catch (QuoridorException e){
                            if (e.getMessage().equals(QuoridorException.BARRIER_ALREADY_CREATED) || e.getMessage().equals(QuoridorException.BARRIER_OVERLAP)) {
                            }
                            else {
                                throw new QuoridorException(e.getMessage());
                            }
                        }
                    }
                }
            }
        }
        oponentStepsHorizontalBarrier = minimumNumberMovementsMe;
        machineStepsHorizontalBarrier = minimumNumberMovementsOponent;
    }

    private void bestVerticalBarrier(Board board, int minimumNumberMovementsMe, int minimumNumberMovementsOponent, ArrayList<String> availableBarriers,
                                       Peon peon, Player otherPlayer) throws QuoridorException {
        for (String barrier : availableBarriers){
            int lenght = barrier.equals("l") ? 3 : 2;
            for (int i = 0; i < board.getBoardSize(); i+=2){
                for (int j = 1; j < board.getBoardSize(); j+=2){
                    if (board.getTypeField(i, j).equals("Empty") && board.barrierCanBePlace(i, j, lenght, true)){
                        try{
                            board.addBarrier(Color.WHITE, i, j, lenght, true, barrier);
                            peon.actualizeStrategyInformation();
                            otherPlayer.getPeon().actualizeStrategyInformation();
                            board.deleteBarrier(i, j, lenght, true, null);
                            if (peon.getMinimumNumberMovementsToWin() <= minimumNumberMovementsMe && otherPlayer.getPeon().getMinimumNumberMovementsToWin() > minimumNumberMovementsOponent){
                                rowVerticalBarrier = i;
                                columnVerticalBarrier = j;
                                verticalBarrierType = barrier;
                            }
                        }
                        catch (QuoridorException e){
                            if (e.getMessage().equals(QuoridorException.BARRIER_ALREADY_CREATED) || e.getMessage().equals(QuoridorException.BARRIER_OVERLAP)) {
                            }
                            else {
                                throw new QuoridorException(e.getMessage());
                            }
                        }
                    }
                }
            }
        }
        oponentStepsVerticalBarrier = minimumNumberMovementsMe;
        machineStepsVerticalBarrier = minimumNumberMovementsOponent;
    }
}
