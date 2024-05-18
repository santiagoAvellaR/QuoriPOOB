package src.domain;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class Quoridor {
    public Integer turns;
    private Board board;
    private Player  player1;
    private Player  player2;
    private String gameMode;
    private boolean vsMachine;
    private HashMap<String, Integer> lengthBarriersTypes;
    private Timer timer;
    private Timer timerGlobal;
    private int tiempoTotalPartida;
    private int tiempoRestanteGlobal;


    public Quoridor(String size,
                    String normalBarriers, String temporaryBarriers, String largeBarriers, String alliedBarriers,
                    String teleporterSquares, String rewindSquares, String skipTurnSquares,
                    boolean vsMachine,
                    String playerOneName, Color playerOneColor,
                    String playerTwoName, Color playerTwoColor,
                    String gameMode, String gameTime,
                    String machineMode) throws QuoridorException {
        // initialize variables
        this.vsMachine = vsMachine;
        this.gameMode = gameMode;
        turns = 0;
        // size
        int sizeInt = validateStringSize(size);
        // board
        if (areSimilarColors(playerOneColor, playerTwoColor)) {throw new QuoridorException(QuoridorException.SIMILAR_PLAYER_COLORS);}
        validateBoardData(sizeInt, playerOneColor, playerTwoColor, teleporterSquares, rewindSquares, skipTurnSquares);
        // players
        validateStringNumberBarriers(normalBarriers, temporaryBarriers, largeBarriers, alliedBarriers);
        validatePlayerData(sizeInt, playerOneColor, playerOneName, playerTwoColor, playerTwoName, normalBarriers, temporaryBarriers, largeBarriers, alliedBarriers, machineMode);
        // time

        //initialize containers
        initializeHashMaps();
    }
    private int validateStringSize(String size) throws QuoridorException {
        if(!(size.matches("[0-9]+") && !size.isEmpty())) {
            throw new QuoridorException(QuoridorException.INVLID_SIZE);
        }
        int sizeInt = Integer.parseInt(size);
        if (sizeInt < 2 || sizeInt >1000) {
            throw new QuoridorException(QuoridorException.MAXIMUN_SIZE_EXCEEDED);
        }
        return sizeInt;
    }
    private void validateBoardData(int size, Color playerOneColor, Color playerTwoColor, String teleporterSquares, String rewindSquares, String skipTurnSquares) throws QuoridorException {
        if(!(teleporterSquares.matches("[0-9]+") && !teleporterSquares.isEmpty()) ||
                !(rewindSquares.matches("[0-9]+") && !rewindSquares.isEmpty()) ||
                !(skipTurnSquares.matches("[0-9]+") && !skipTurnSquares.isEmpty())){
            throw new QuoridorException(QuoridorException.INVALID_NUMBER_SQUARES);
        }
        int teleporterSquaresInt = Integer.parseInt(teleporterSquares);
        int rewindSquaresInt = Integer.parseInt(rewindSquares);
        int skipTurnSquaresInt = Integer.parseInt(skipTurnSquares);
        int totalSpecialSquares = teleporterSquaresInt + rewindSquaresInt + skipTurnSquaresInt;
        if (totalSpecialSquares > Math.pow(size, 2)-2){
            throw new QuoridorException(QuoridorException.MAXIMUN_NUMBER_SQUARES_EXCEEDED);
        }
        board = new Board(size, playerOneColor, playerTwoColor, teleporterSquaresInt, rewindSquaresInt, skipTurnSquaresInt);
    }
    private void validateStringNumberBarriers(String normalBarriers, String temporaryBarriers, String largeBarriers, String alliedBarriers) throws QuoridorException {
        if(!(temporaryBarriers.matches("[0-9]+") && !temporaryBarriers.isEmpty()) ||
                !(largeBarriers.matches("[0-9]+") && !largeBarriers.isEmpty()) ||
                !(alliedBarriers.matches("[0-9]+") && !alliedBarriers.isEmpty()) ||
                !(normalBarriers.matches("[0-9]+") && !normalBarriers.isEmpty())){
            throw new QuoridorException(QuoridorException.INVALID_NUMBER_BARRIERS);
        }
    }
    private void validatePlayerData(int size, Color playerOneColor, String playerOneName, Color playerTwoColor, String playerTwoName, String normalBarriers, String temporaryBarriers, String largeBarriers, String alliedBarriers, String machineMode) throws QuoridorException {
        int normalBarriersInt = Integer.parseInt(normalBarriers);
        int temporaryBarriersInt = Integer.parseInt(temporaryBarriers);
        int largeBarriersInt = Integer.parseInt(largeBarriers);
        int alliedBarriersInt = Integer.parseInt(alliedBarriers);
        int totalBarriers = temporaryBarriersInt + largeBarriersInt + alliedBarriersInt + normalBarriersInt;
        if (totalBarriers > size + 1){throw new QuoridorException(QuoridorException.MAXIMUN_NUMBER_BARRIERS_EXCEEDED);}
        normalBarriersInt = totalBarriers == 0 ? size + 1 : normalBarriersInt;
        Peon peon1 = board.getPeon1InitialMoment();
        Peon peon2 = board.getPeon2InitialMoment();
        player1 = new Human(peon1, playerOneName, playerOneColor, normalBarriersInt, temporaryBarriersInt, largeBarriersInt, alliedBarriersInt);
        if (vsMachine){player2 = new Machine(peon2,"Machine", playerTwoColor, normalBarriersInt, temporaryBarriersInt, largeBarriersInt, alliedBarriersInt, machineMode, board);}
        else{player2 = new Human(peon2, playerTwoName, playerTwoColor, normalBarriersInt, temporaryBarriersInt, largeBarriersInt, alliedBarriersInt);}
    }
    private void iniciarCronometroGlobal(int tiempoTotalPartida) {
        this.tiempoTotalPartida = tiempoTotalPartida;
        this.tiempoRestanteGlobal = tiempoTotalPartida;
        this.timerGlobal = new Timer();
        timerGlobal.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (tiempoRestanteGlobal > 0) {
                    System.out.println("Tiempo restante para la partida: " + tiempoRestanteGlobal + " segundos");
                    tiempoRestanteGlobal--;
                }
                else {
                    System.out.println("¡Tiempo agotado! El juego ha terminado.");
                    timerGlobal.cancel();
                }
            }
        }, 0, 1000);
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

    public int getNumberBarrier(Color playerColor, String type){
        Player selectedPlayer = player1.getColor().equals(playerColor) ? player1 : player2;
        return selectedPlayer.numberBarrier(playerColor, type);
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

    public int[][] getPeonsPositions(){
        return new int[][]{{player1.getPeonRow(), player1.getPeonColumn()},{player2.getPeonRow(), player2.getPeonColumn()}};
    }

    public Color getFieldColor(int row, int column){
        return board.getFieldColor(row, column);
    }

    // TEST FUNCTIONS
    public String getTypeOfField(int row, int column){return board.getTypeField(row, column);}
    public void printBoard(){board.printBoard();}
    public void stepBackPeon1(int numberSteps) throws QuoridorException {
        player1.getPeon().stepBackMovements(numberSteps);
    }
    public void stepBackPeon2(int numberSteps) throws QuoridorException {
        player2.getPeon().stepBackMovements(numberSteps);
    }
    public void addTeleporterSquare(int row1, int column1, int row2, int column2) throws QuoridorException {
        board.addTeleporterSquare(row1, column1, row2, column2);
    }
    public void addSkipTurnSquare(int row, int column){
        board.addSkipTurnSquare(row, column);
    }
    public boolean peonsHasAnExit(){return player1.peonHasAnExit() && player2.peonHasAnExit();}
}