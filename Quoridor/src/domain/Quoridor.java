package src.domain;

import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;

public class Quoridor {
    public static int turns;
    private Board board;
    private Player  player1;
    private Player  player2;
    private String gameMode;
    private boolean vsMachine;

    public Quoridor(int size,
                    int temporaryBarriers, int largeBarriers, int alliedBarriers, int normalBarriers,
                    int rewindSquares, int skipTurnSquares, int teleporterSquares,
                    String playerOneName, Color playerOneColor,
                    String playerTwoName, Color playerTwoColor,
                    String gameMode, int gameTime,
                    boolean vsMachine, String machineMode) {
        turns = 0;
        this.vsMachine = vsMachine;
        board = new Board(size, playerOneColor, playerTwoColor);
        Peon peon1 = board.getPeon1InitialMoment();
        Peon peon2 = board.getPeon2InitialMoment();
        int totalBarriers = temporaryBarriers + largeBarriers + alliedBarriers + normalBarriers;
        player1 = new Human(peon1, playerOneName, totalBarriers, playerOneColor);
        if (vsMachine){player2 = new Machine(peon2,"Machine", totalBarriers, playerTwoColor, machineMode);}
        else{player2 = new Human(peon2, playerTwoName, totalBarriers, playerTwoColor);}
    }

    public int getTurns(){
        return turns;
    }

    public ArrayList<String> getPeonValidMovements(Color playerColor){
        Player selectedPlayer = player1.getColor().equals(playerColor) ? player1 : player2;
        if (!vsMachine || selectedPlayer.equals(player1)){
            Human human = (Human) selectedPlayer;
            return human.getPeonValidMovements();
        }
        return new ArrayList<>();
    }

    public void movePeon(Color playerColor, String direction) throws QuoridorException {
        Player selectedPlayer = player1.getColor().equals(playerColor) ? player1 : player2;
        Player playerWhoIsSupposedToMove = turns%2 == 0 ? player1 : player2;
        if (!selectedPlayer.equals(playerWhoIsSupposedToMove)){throw new QuoridorException(QuoridorException.PLAYER_NOT_TURN);}
        selectedPlayer.movePeon(playerColor, direction);
    }

    public void addBarrier(Color playerColor, int row, int column, boolean horizontal, int length, char type) throws QuoridorException {
        board.addBarrier(playerColor, row, column, horizontal, length, type);
    }

}
