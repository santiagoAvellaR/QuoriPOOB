package src.domain;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

public class Quoridor {
    public Integer turns;
    private Board board;
    private Player  player1;
    private Player  player2;
    private String gameMode;
    private boolean vsMachine;
    private HashMap<String, Integer> lengthBarriersTypes;
    public Quoridor(String size,
                    String normalBarriers, String temporaryBarriers, String largeBarriers, String alliedBarriers,
                    String teleporterSquares, String rewindSquares, String skipTurnSquares,
                    boolean vsMachine,
                    String playerOneName, Color playerOneColor,
                    String playerTwoName, Color playerTwoColor,
                    String gameMode, String gameTime,
                    String machineMode) throws QuoridorException {
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
        if (totalBarriers > sizeInt + 1){throw new QuoridorException(QuoridorException.MAXIMUN_NUMBER_BARRIERS_EXCEEDED);}
        normalBarriersInt = totalBarriers == 0 ? sizeInt + 1 : normalBarriersInt;
        Peon peon1 = board.getPeon1InitialMoment();
        Peon peon2 = board.getPeon2InitialMoment();
        player1 = new Human(peon1, playerOneName, playerOneColor, normalBarriersInt, temporaryBarriersInt, largeBarriersInt, alliedBarriersInt);
        if (vsMachine){player2 = new Machine(peon2,"Machine", playerTwoColor, normalBarriersInt, temporaryBarriersInt, largeBarriersInt, alliedBarriersInt, machineMode, board);}
        else{player2 = new Human(peon2, playerTwoName, playerTwoColor, normalBarriersInt, temporaryBarriersInt, largeBarriersInt, alliedBarriersInt);}
        initializeHashMaps();
    }

    private void initializeHashMaps(){
        lengthBarriersTypes = new HashMap<>();
        lengthBarriersTypes.put("n", 2);
        lengthBarriersTypes.put("a", 2);
        lengthBarriersTypes.put("t", 2);
        lengthBarriersTypes.put("l", 3);
    }

    public Integer getTurns(){
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
        try {
            if (!vsMachine || selectedPlayer.equals(player1)) {
                selectedPlayer.movePeon(direction);
                actualizeEachTurn();
            }
        } catch (QuoridorException e) {
            if (e.getMessage().equals(QuoridorException.PLAYER_PLAYS_TWICE)){
                actualizeEachTurn();
                turns += 1;
                throw new QuoridorException(QuoridorException.PLAYER_PLAYS_TWICE);
            }
            else {throw new QuoridorException(e.getMessage());}
        }
    }

    public void addBarrier(Color playerColor, int row, int column, boolean horizontal, String type) throws QuoridorException {
        Player selectedPlayer = player1.getColor().equals(playerColor) ? player1 : player2;
        Player playerWhoIsSupposedToMove = turns%2 == 0 ? player1 : player2;
        if (!selectedPlayer.equals(playerWhoIsSupposedToMove)){throw new QuoridorException(QuoridorException.PLAYER_NOT_TURN);}
        if (!vsMachine || selectedPlayer.equals(player1)) {
            if (!selectedPlayer.stillHasBarrierType(playerColor, type)) {
                throw new QuoridorException(QuoridorException.DONT_HAVE_BARRIERS_LEFT);
            }
            board.addBarrier(playerColor, row, column, lengthBarriersTypes.get(type), horizontal, type);
            if (!player1.peonHasAnExit()){
                board.deleteBarrier(row, column, lengthBarriersTypes.get(type), horizontal, null);
                throw new QuoridorException(QuoridorException.BARRIER_TRAP_PEON1);
            }
            if (!player2.peonHasAnExit()){
                board.deleteBarrier(row, column, lengthBarriersTypes.get(type), horizontal, null);
                throw new QuoridorException(QuoridorException.BARRIER_TRAP_PEON2);
            }
            selectedPlayer.reduceNumberBarriers(playerColor, type);
            actualizeEachTurn();
        }
    }
    public int getNumberBarrier(Color player, String type){
        Player selectedPlayer = player1.getColor().equals(player) ? player1 : player2;
        return selectedPlayer.numberBarrier(player, type);
    }

    public void machineTurn() throws QuoridorException {
        if (vsMachine){
            Machine machine = (Machine) player2;
            machine.play();
            actualizeEachTurn();
        }
    }

    public int[] getPositionDeletedTemporary(){
        return board.getPositionDeletedTemporary();
    }
    public boolean getOrientationDeletedTemporary(){
        return board.getOrientationDeletedTemporary();
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
        turns += 1;
        System.out.println("tuno: " + turns);
        System.out.println("movimientos peon1: " + player1.getPeonValidMovements());
        System.out.println("movimientos peon2: " + player2.getPeonValidMovements());
        printBoard();
        board.fieldActEachTurn();
    }

    public boolean peonsHasAnExit(){return player1.peonHasAnExit() && player2.peonHasAnExit();}

    public int[][] getPeonsPositions(){
        return new int[][]{{player1.getPeonRow(), player1.getPeonColumn()},{player2.getPeonRow(), player2.getPeonColumn()}};
    }

    // TEST FUNCTIONS
    public Peon getPeon1(){return player1.getPeon();}
    public Peon getPeon2(){return player2.getPeon();}
    public Field[][] getBoard(){return board.getBoard();}
    public String getTypeOfField(int row, int column){return board.getTypeField(row, column);}
    public void printBoard(){board.printBoard();}
    public void setpBackPeon1(int numberSteps) throws QuoridorException {
        player1.getPeon().stepBackMovements(numberSteps);
    }
    public void setpBackPeon2(int numberSteps) throws QuoridorException {
        player2.getPeon().stepBackMovements(numberSteps);
    }
}