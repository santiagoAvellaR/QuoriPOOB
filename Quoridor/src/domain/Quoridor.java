package src.domain;

import java.util.Timer;

public class Quoridor {
    private Board board;
    private Player  player1;
    private Player  player2;
    private String gameMode;
    private String machineMode;
    private int remainingTemporaryBarriers, remainingLargeBarriers, remainingAlliedBarriers, remainingNormalBarriers;


    public Quoridor(int size,
                    int temporaryBarriers, int largeBarriers, int alliedBarriers, int normalBarriers,
                    int rewindSquares, int skipTurnSquares, int teleporterSquares,
                    String playerOneName, String playerOneColor,
                    String playerTwoName, String playerTwoColor,
                    String gameMode, int gameTime,
                    boolean vsMachine, String machineMode) {
        board = new Board(size, playerOneColor, playerTwoColor);
        Peon peon1 = board.getPeon1InitialMoment();
        Peon peon2 = board.getPeon2InitialMoment();

    }
}
