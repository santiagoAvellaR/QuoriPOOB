package src.domain;

import java.awt.*;
import java.util.Timer;

public class Quoridor {
    private Board board;
    private Player  player1;
    private Player  player2;
    private String gameMode;

    public Quoridor(int size,
                    int temporaryBarriers, int largeBarriers, int alliedBarriers, int normalBarriers,
                    int rewindSquares, int skipTurnSquares, int teleporterSquares,
                    String playerOneName, Color playerOneColor,
                    String playerTwoName, Color playerTwoColor,
                    String gameMode, int gameTime,
                    boolean vsMachine, String machineMode) {
        board = new Board(size, playerOneColor, playerTwoColor);
        Peon peon1 = board.getPeon1InitialMoment();
        Peon peon2 = board.getPeon2InitialMoment();
        int totalBarriers = temporaryBarriers + largeBarriers + alliedBarriers + normalBarriers;
        player1 = new Human(peon1, playerOneName, totalBarriers, playerOneColor);
        if (vsMachine){player2 = new Machine(peon2,"Machine", totalBarriers, playerTwoColor, machineMode);}
        else{player2 = new Human(peon2, playerTwoName, totalBarriers, playerTwoColor);}
    }

    public int getTurns(){
        return board.getTurns();
    }
}
