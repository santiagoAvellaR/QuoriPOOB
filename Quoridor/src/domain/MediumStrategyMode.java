package src.domain;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class MediumStrategyMode extends StrategyMode implements MachineStrategy, Serializable {
    private HashMap<int[], String> barriersTharBlockPeon = new HashMap<>();

    @Override
    public void addBarriersThatBlockPeon(int row, int column){
        barriersTharBlockPeon.put(new int[]{row, column}, "block");
    }

    @Override
    public void makeMove(Board board, Peon peon, Player otherPlayer, int normalBarriers, int temporaryBarriers, int longBarriers, int alliedBarriers) throws QuoridorException {
        peon.actualizeStrategyInformation();
        otherPlayer.getPeon().actualizeStrategyInformation();
        System.out.println(peon.getMinimumNumberMovementsToWin() + " <= " + otherPlayer.getPeon().getMinimumNumberMovementsToWin());
        System.out.println("filas que bloquean: " + barriersTharBlockPeon.keySet());
        if (peon.getMinimumNumberMovementsToWin() <= otherPlayer.getPeon().getMinimumNumberMovementsToWin() || (normalBarriers <= 0 && temporaryBarriers <= 0
                && alliedBarriers <= 0 && longBarriers <= 0)){
            System.out.println("maquina decide mover peon");
            this.movementType = "movePeon";
            this.direction = peon.getBestPeonMovement();
            throw new QuoridorException(QuoridorException.MACHINE_MOVE_PEON);
        }
        else{
            row = Integer.MAX_VALUE;
            column = Integer.MAX_VALUE;
            barrierType = "";
            this.movementType = "addBarrier";
            System.out.println("maquina decide añadir barrera");
            String otherPlayerBestMovement = otherPlayer.getPeon().getBestPeonMovement();
            ArrayList<String> availableBarriers = getAvailableBarriers(normalBarriers, temporaryBarriers, longBarriers, alliedBarriers);
            System.out.println("validando posibilidades");
            bestHorizontalBarrier(board, peon.getSumObjectiveRow(), otherPlayer.getPeon().getSumObjectiveRow(), peon.getMinimumNumberMovementsToWin(),
                    otherPlayer.getPeon().getMinimumNumberMovementsToWin(), availableBarriers, peon, otherPlayer);
            System.out.println("mandando respuesta, notificando...");
            int[] posiciones = new int[]{row, column};
            if ((row != Integer.MAX_VALUE && column != Integer.MAX_VALUE && !barrierType.isEmpty()) || (barriersTharBlockPeon.containsKey(posiciones))){
                System.out.println("encontro una barrera para poner, manda notificacion... ");
                throw new QuoridorException(QuoridorException.MACHINE_ADD_A_BARRIER);
            }
            else {
                System.out.println("maquina no encontró respuesta, decide mover peon");
                this.movementType = "movePeon";
                this.direction = peon.getBestPeonMovement();
                throw new QuoridorException(QuoridorException.MACHINE_MOVE_PEON);
            }
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
        if (availableBarriers.contains("a") && (availableBarriers.contains("n") || availableBarriers.contains("t"))){
            availableBarriers.remove("n");
            availableBarriers.remove("t");
        }
        return availableBarriers;
    }

    private void bestHorizontalBarrier(Board board, int sumMovementsME, int sumMovementOP, int meMNMTW, int otherPMNMTW, ArrayList<String> availableBarriers,
                                       Peon peon, Player otherPlayer) throws QuoridorException {
        peon.printCostMatrix();
        otherPlayer.getPeon().printCostMatrix();
        for (String barrier : availableBarriers) {
            int length = barrier.equals("l") ? 3 : 2;
            // int i = 0; i < board.getBoardSize() && i < otherPlayer.getPeon().getRow() + 2; i++
            for (int i = board.getBoardSize()-1; i >= 0; i--) {
                // int j = 0; j < board.getBoardSize(); j++
                for (int j = board.getBoardSize()-1; j >= 0; j--) {
                    System.out.println("ciclo   i: " + i + " j: " + j);
                    if ((i % 2 == 0 && j % 2 != 0) || (i % 2 != 0 && j % 2 == 0) && !barriersTharBlockPeon.containsKey(new int[]{i, j})) {
                        boolean isHorizontalCal = (i % 2 != 0 && j % 2 == 0);
                        boolean isEmpty = board.getTypeField(i, j).equals("Empty");
                        boolean canBePlaced = board.barrierCanBePlace(i, j, length, isHorizontalCal, peon, otherPlayer.getPeon());
                        if (isEmpty && canBePlaced) {
                            try {
                                //System.out.println("validando caso: " + i + ", " + j + " orientacion: " + isHorizontalCal);
                                board.addBarrier(Color.WHITE, i, j, length, isHorizontalCal, barrier);
                                peon.actualizeStrategyInformation();
                                otherPlayer.getPeon().actualizeStrategyInformation();
                                //System.out.println("numero de pasos para ganar(maquina): " + peon.getMinimumNumberMovementsToWin());
                                //System.out.println("numero de pasos para ganar(jugador): " + otherPlayer.getPeon().getMinimumNumberMovementsToWin());
                                // ensayo (peon.getSumObjectiveRow() < sumMovementsME && otherPlayer.getPeon().getSumObjectiveRow() > sumMovementOP)
                                if ((peon.getMinimumNumberMovementsToWin() < meMNMTW || (peon.getMinimumNumberMovementsToWin() <= meMNMTW && otherPlayer.getPeon().getMinimumNumberMovementsToWin() > otherPMNMTW))) {
                                    row = i;
                                    column = j;
                                    barrierType = barrier;
                                    isHorizontal = isHorizontalCal;
                                    System.out.println("                        posible: " + row + ", " + column + " orientacion " + isHorizontalCal);
                                    sumMovementsME = peon.getSumObjectiveRow();
                                    sumMovementOP = otherPlayer.getPeon().getSumObjectiveRow();
                                    meMNMTW = peon.getMinimumNumberMovementsToWin();
                                    otherPMNMTW = otherPlayer.getPeon().getMinimumNumberMovementsToWin();
                                }
                                board.deleteBarrier(i, j, length, isHorizontalCal, null);
                            } catch (QuoridorException e) {
                                if (e.getMessage().equals(QuoridorException.BARRIER_ALREADY_CREATED) || e.getMessage().equals(QuoridorException.BARRIER_OVERLAP)) {
                                    addBarriersThatBlockPeon(i, j);
                                    System.out.println("primer if: " + e.getMessage());
                                    continue;
                                } else {
                                    // Para otras excepciones, vuelve a lanzar la excepción
                                    System.out.println("arrojo excepcion en el else del ciclo: " + e.getMessage());
                                    throw new QuoridorException(e.getMessage());
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println("despues de hacer todo el ciclo, se decidio por:   " + row + ", " + column + " tipo: " + barrierType);
    }

}
