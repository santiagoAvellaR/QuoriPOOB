package src.domain;

import java.awt.Color;
import java.util.ArrayList;

public class Quoridor {
    public static Integer turns;
    private Board board;
    private Player  player1;
    private Player  player2;
    private String gameMode;
    private boolean vsMachine;

    public Quoridor(String size,
                    String normalBarriers, String temporaryBarriers, String largeBarriers, String alliedBarriers,
                    String teleporterSquares, String rewindSquares, String skipTurnSquares,
                    String playerOneName, Color playerOneColor,
                    String playerTwoName, Color playerTwoColor,
                    String gameMode, String gameTime,
                    boolean vsMachine, String machineMode) throws QuoridorException {
        turns = 0;
        this.vsMachine = vsMachine;
        this.gameMode = gameMode;
        if (areSimilarColors(playerOneColor, playerTwoColor)) {
            throw new QuoridorException(QuoridorException.SIMILAR_PLAYER_COLORS);
        }
        if(!(size.matches("[0-9]+") && !size.isEmpty())) {
            throw new QuoridorException(QuoridorException.INVLID_SIZE);
        }
        int sizeInt = Integer.parseInt(size);
        if (sizeInt < 2 || sizeInt >1000) {
            throw new QuoridorException(QuoridorException.MAXIMUN_SIZE_EXCEEDED);
        }
        if(!(teleporterSquares.matches("[0-9]+") && !teleporterSquares.isEmpty()) ||
                !(rewindSquares.matches("[0-9]+") && !rewindSquares.isEmpty()) ||
                !(skipTurnSquares.matches("[0-9]+") && !skipTurnSquares.isEmpty())){
            throw new QuoridorException(QuoridorException.INVALID_NUMBER_SQUARES);
        }
        int teleporterSquaresInt = Integer.parseInt(teleporterSquares);
        int rewindSquaresInt = Integer.parseInt(rewindSquares);
        int skipTurnSquaresInt = Integer.parseInt(skipTurnSquares);
        int totalSpecialSquares = teleporterSquaresInt + rewindSquaresInt + skipTurnSquaresInt;
        if (totalSpecialSquares > Math.pow(sizeInt, 2)-2){
            throw new QuoridorException(QuoridorException.MAXIMUN_NUMBER_SQUARES_EXCEEDED);
        }
        board = new Board(sizeInt, playerOneColor, playerTwoColor, teleporterSquaresInt, rewindSquaresInt, skipTurnSquaresInt);
        if(!(temporaryBarriers.matches("[0-9]+") && !temporaryBarriers.isEmpty()) ||
                !(largeBarriers.matches("[0-9]+") && !largeBarriers.isEmpty()) ||
                !(alliedBarriers.matches("[0-9]+") && !alliedBarriers.isEmpty()) ||
                !(normalBarriers.matches("[0-9]+") && !normalBarriers.isEmpty())){
            throw new QuoridorException(QuoridorException.INVALID_NUMBER_BARRIERS);
        }
        int normalBarriersInt = Integer.parseInt(normalBarriers);
        int temporaryBarriersInt = Integer.parseInt(temporaryBarriers);
        int largeBarriersInt = Integer.parseInt(largeBarriers);
        int alliedBarriersInt = Integer.parseInt(alliedBarriers);
        int totalBarriers = temporaryBarriersInt + largeBarriersInt + alliedBarriersInt + normalBarriersInt;
        if (totalBarriers > sizeInt + 1){
            throw new QuoridorException(QuoridorException.MAXIMUN_NUMBER_BARRIERS_EXCEEDED);
        }
        normalBarriersInt = totalBarriers == 0 ? sizeInt +1 : normalBarriersInt;
        Peon peon1 = board.getPeon1InitialMoment();
        Peon peon2 = board.getPeon2InitialMoment();
        player1 = new Human(peon1, playerOneName, playerOneColor, normalBarriersInt, temporaryBarriersInt, largeBarriersInt, alliedBarriersInt);
        if (vsMachine){player2 = new Machine(peon2,"Machine", playerTwoColor, normalBarriersInt, temporaryBarriersInt, largeBarriersInt, alliedBarriersInt, machineMode, board);}
        else{player2 = new Human(peon2, playerTwoName, playerTwoColor, normalBarriersInt, temporaryBarriersInt, largeBarriersInt, alliedBarriersInt);}
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
        return null;
    }

    public void movePeon(Color playerColor, String direction) throws QuoridorException {
        Player selectedPlayer = player1.getColor().equals(playerColor) ? player1 : player2;
        Player playerWhoIsSupposedToMove = turns%2 == 0 ? player1 : player2;
        if (!selectedPlayer.equals(playerWhoIsSupposedToMove)){throw new QuoridorException(QuoridorException.PLAYER_NOT_TURN);}
        if (!vsMachine || selectedPlayer.equals(player1)) {
            Human human = (Human) selectedPlayer;
            human.movePeon(direction);
            turns += 1;
        }
    }

    public void addBarrier(Color playerColor, int row, int column, boolean horizontal, char type) throws QuoridorException {
        Player selectedPlayer = player1.getColor().equals(playerColor) ? player1 : player2;
        Player playerWhoIsSupposedToMove = turns%2 == 0 ? player1 : player2;
        if (!selectedPlayer.equals(playerWhoIsSupposedToMove)){throw new QuoridorException(QuoridorException.PLAYER_NOT_TURN);}
        if (!vsMachine || selectedPlayer.equals(player1)) {
            board.addBarrier(playerColor, row, column, horizontal, type);
            turns += 1;
        }
    }

    public void machineTurn(){
        if (vsMachine){
            Machine machine = (Machine) player2;
            machine.play();
        }
    }

    public int[] getDeletedTemporary(){
        return board.getPsotionsDeletedTemporary();
    }

    public boolean areSimilarColors(Color color1, Color color2) {
        int redDiff = color1.getRed() - color2.getRed();
        int greenDiff = color1.getGreen() - color2.getGreen();
        int blueDiff = color1.getBlue() - color2.getBlue();
        // Calcula la distancia euclidiana en el espacio RGB
        double distance = Math.sqrt(redDiff * redDiff + greenDiff * greenDiff + blueDiff * blueDiff);
        return distance <= 30;
    }

    public void actualizeEachTurn() throws QuoridorException {
        board.fieldAct();
    }

    public boolean peonsHasAnExit(){return player1.peonHasAnExit() && player2.peonHasAnExit();}

    // TEST FUNCTIONS
    public Peon getPeon1(){return player1.getPeon();}
    public Peon getPeon2(){return player2.getPeon();}
    public Field[][] getBoard(){return board.getBoard();}
    public String getTypeOfField(int row, int column){return board.getTypeField(row, column);}

}
