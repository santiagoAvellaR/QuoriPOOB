package src.domain;

import src.presentation.QuoridorObserver;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.Timer;

public class Quoridor implements Serializable{
    public int turns;
    public int delta;
    private Board board;
    private Player  player1;
    private Player  player2;
    private String gameMode;
    private final boolean vsMachine;
    private HashMap<String, Integer> lengthBarriersTypes;
    private Timer timerPlayer1;
    private Timer timerPlayer2;
    private int timePlayer1;
    private int timePlayer2;
    private int totalTime;
    private ArrayList<QuoridorObserver> observers = new ArrayList<>();

    public Quoridor(String size,
                    String normalBarriers, String temporaryBarriers, String largeBarriers, String alliedBarriers,
                    String teleporterSquares, String rewindSquares, String skipTurnSquares,
                    boolean vsMachine,
                    String playerOneName, Color playerOneColor, String shapePeon1,
                    String playerTwoName, Color playerTwoColor, String shapePeon2,
                    String gameMode, int gameTime,
                    String machineMode) throws QuoridorException {
        // initialize variables
        this.vsMachine = vsMachine;
        this.gameMode = gameMode;
        turns = 0;
        delta = 1;
        // size
        int sizeInt = validateStringSize(size);
        // board
        if (areSimilarColors(playerOneColor, playerTwoColor)) {throw new QuoridorException(QuoridorException.SIMILAR_PLAYER_COLORS);}
        validateBoardData(sizeInt, playerOneColor, shapePeon1, playerTwoColor, shapePeon2, teleporterSquares, rewindSquares, skipTurnSquares);
        // players
        validateStringNumberBarriers(normalBarriers, temporaryBarriers, largeBarriers, alliedBarriers);
        validatePlayerData(sizeInt, playerOneColor, playerOneName, playerTwoColor, playerTwoName, normalBarriers, temporaryBarriers, largeBarriers, alliedBarriers, machineMode);
        // time
        if(!gameMode.equals("NORMAL")){
            timePlayer1 = gameTime;
            timePlayer2 = gameTime;
        }
        //initialize containers
        initializeHashMaps();
    }
    private int validateStringSize(String size) throws QuoridorException {
        if(!(size.matches("[0-9]+") && !size.isEmpty())) {
            throw new QuoridorException(QuoridorException.INVALID_SIZE);
        }
        int sizeInt = Integer.parseInt(size);
        if (sizeInt < 2 || sizeInt >1000) {
            throw new QuoridorException(QuoridorException.MAXIMUM_SIZE_EXCEEDED);
        }
        return sizeInt;
    }
    private void validateBoardData(int size, Color playerOneColor, String shapePeon1, Color playerTwoColor, String shapePeon2, String teleporterSquares, String rewindSquares, String skipTurnSquares) throws QuoridorException {
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
            throw new QuoridorException(QuoridorException.MAXIMUM_NUMBER_SQUARES_EXCEEDED);
        }
        if (rewindSquaresInt > (int)(((float)(Math.pow(size, 2)))*0.75)){
            throw new QuoridorException("amount of rewind squares: " + rewindSquaresInt + QuoridorException.MAXIMUM_NUMBER_REWIND_EXCEEDED);
        }
        board = new Board(size, playerOneColor, shapePeon1, playerTwoColor, shapePeon2, teleporterSquaresInt, rewindSquaresInt, skipTurnSquaresInt);
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
        if (totalBarriers > size + 1){throw new QuoridorException(QuoridorException.MAXIMUM_NUMBER_BARRIERS_EXCEEDED);}
        normalBarriersInt = totalBarriers == 0 ? size + 1 : normalBarriersInt;
        Peon peon1 = board.getPeon1InitialMoment();
        Peon peon2 = board.getPeon2InitialMoment();
        player1 = new Human(peon1, playerOneName, playerOneColor, normalBarriersInt, temporaryBarriersInt, largeBarriersInt, alliedBarriersInt);
        if (vsMachine){player2 = new Machine(peon2,"Machine", playerTwoColor, normalBarriersInt, temporaryBarriersInt, largeBarriersInt, alliedBarriersInt, machineMode, board, player1);}
        else{player2 = new Human(peon2, playerTwoName, playerTwoColor, normalBarriersInt, temporaryBarriersInt, largeBarriersInt, alliedBarriersInt);}
    }
    private void initializeHashMaps(){
        lengthBarriersTypes = new HashMap<>();
        lengthBarriersTypes.put("n", 2);
        lengthBarriersTypes.put("a", 2);
        lengthBarriersTypes.put("t", 2);
        lengthBarriersTypes.put("l", 3);
    }
    public void setTimePlayer(Color playerColor, int newTime){
        if(playerColor.equals(player1.getColor())){
            timePlayer1 = newTime;
        }else{
            timePlayer2 = newTime;
        }

    }
    public void sumTurn(){
        turns+=1;
    }
    public void setDelta(int newDelta){
        delta =newDelta;
    }


    public int  getTimePlayer(Color playerColor){
        return playerColor.equals(player1.getColor()) ? timePlayer1 : timePlayer2;
    }
    public Integer getTurns(){
        return turns;
    }
    public String getGameMode(){return gameMode;}
    public boolean getVsMachine(){
        return vsMachine;
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
            selectedPlayer.movePeon(direction);
            actualizeEachTurn(playerColor);
        } catch (QuoridorException e) {
            if (e.getMessage().equals(QuoridorException.PLAYER_PLAYS_TWICE)){
                actualizeEachTurn(playerColor);
                turns += delta;
                throw new QuoridorException(QuoridorException.PLAYER_PLAYS_TWICE);
            }
            else if (e.getMessage().equals(QuoridorException.PEON_HAS_BEEN_TELEPORTED)) {
                actualizeEachTurn(playerColor);
                throw new QuoridorException(QuoridorException.PEON_HAS_BEEN_TELEPORTED);
            }
            else if (e.getMessage().equals(QuoridorException.PEON_STEPPED_BACK)) {
                actualizeEachTurn(playerColor);
                throw new QuoridorException(QuoridorException.PEON_STEPPED_BACK);
            } else {
                if (!(e.getMessage().equals(QuoridorException.PLAYER_ONE_WON) || e.getMessage().equals(QuoridorException.PLAYER_TWO_WON)
                || e.getMessage().equals(QuoridorException.ERASE_TEMPORARY_BARRIER))) {
                    Log.record(e);
                }
                throw new QuoridorException(e.getMessage());
            }
        }
    }

    public void addBarrier(Color playerColor, int row, int column, boolean horizontal, String type) throws QuoridorException {
        Player selectedPlayer = player1.getColor().equals(playerColor) ? player1 : player2;
        Player playerWhoIsSupposedToMove = turns%2 == 0 ? player1 : player2;
        if (!selectedPlayer.equals(playerWhoIsSupposedToMove)){throw new QuoridorException(QuoridorException.PLAYER_NOT_TURN);}
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
        actualizeEachTurn(playerColor);
    }

    public int getNumberBarrier(Color playerColor, String type){
        Player selectedPlayer = player1.getColor().equals(playerColor) ? player1 : player2;
        return selectedPlayer.numberBarrier(playerColor, type);
    }

    public void machineTurn() throws QuoridorException {
        System.out.println('\n' + "Juega maquina. Tunos: " + turns);
        if (vsMachine){
            Machine machine = (Machine) player2;
            try {
                System.out.println("llama al metodo de juega contra la maquina");
                machine.play();
            }
            catch (QuoridorException e) {
                System.out.println("Excepcion arrojada: " + e.getMessage());
                System.out.println("Juega y lanza la excepcion de: " + e.getMessage());
                if (e.getMessage().equals(QuoridorException.MACHINE_ADD_A_BARRIER)){
                    System.out.println("entra en el catch de maquina quiere agregar una barrera");
                    try {
                        System.out.println("añadiendo barrera al tablero en el turno: " + turns);
                        System.out.println(machine.getColor() + ", " + machine.getRow() + ", " + machine.getColumn()+ ", " + machine.isHorizontal()+ ", " + machine.getBarrierType());
                        addBarrier(machine.getColor(), machine.getRow(), machine.getColumn(), machine.isHorizontal(), machine.getBarrierType());
                        System.out.println("ya añadio la barrera con el metodo de Quoridor, va a notificar");
                        notifyMachineAddBarrierObservers(QuoridorException.MACHINE_ADD_A_BARRIER, machine.getRow(), machine.getColumn(), machine.getBarrierType(), machine.isHorizontal());
                    } catch (QuoridorException e1) {
                        if (e1.getMessage().equals(QuoridorException.BARRIER_TRAP_PEON1) || e1.getMessage().equals(QuoridorException.BARRIER_TRAP_PEON2) ||
                                e1.getMessage().equals(QuoridorException.BARRIER_ALREADY_CREATED) || e1.getMessage().equals(QuoridorException.BARRIER_OVERLAP)){
                            System.out.println("atrapa al peon");
                            machine.strategyChooseAnotherBarrier(machine.getRow(), machine.getColumn());
                            machineTurn();
                        }
                        else {
                            Log.record(e1);
                            System.out.println("mientras añadía la barrera arrojo: " + e1.getMessage());
                        }
                    }
                } else if (e.getMessage().equals(QuoridorException.MACHINE_MOVE_PEON)) {
                    System.out.println("moviendo peon en el tablero");
                    movePeon(machine.getColor(), machine.getDirection());
                    notifyMachineMovePeonObservers(QuoridorException.MACHINE_MOVE_PEON, machine.getPeon().getContraryMovement(machine.getDirection()));
                } else if (e.getMessage().equals(QuoridorException.ERASE_TEMPORARY_BARRIER) || e.getMessage().equals(QuoridorException.PLAYER_ONE_WON) ||
                e.getMessage().equals(QuoridorException.PLAYER_PLAYS_TWICE)) {
                    throw e;
                } else if (e.getMessage().equals(QuoridorException.PLAYER_NOT_TURN)) {
                    System.out.println("no es el turno de la maquina");
                } else {
                    Log.record(e);
                    throw new QuoridorException(e.getMessage());
                }
            }
        }
        else {
            System.out.println("no es vs Maquina");
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

    public void actualizeEachTurn(Color playerColor) throws QuoridorException {
        Color playerColorTurn = turns%2 == 0 ? player1.getColor() : player2.getColor();
        System.out.println("va a actualizar en cada turno");
        if (playerColorTurn.equals(playerColor)) {
            turns += delta;
            System.out.println();
            System.out.println("turno: " + turns);
            System.out.println("movimientos peon1: " + player1.getPeonValidMovements());
            System.out.println("movimientos peon2: " + player2.getPeonValidMovements());
            printBoard();
            peonActualizeStrategyInformation();
            board.fieldActEachTurn();
        }
    }

    public int[][] getPeonsPositions(){
        return new int[][]{{player1.getPeonRow(), player1.getPeonColumn()},{player2.getPeonRow(), player2.getPeonColumn()}};
    }

    public Color getFieldColor(int row, int column){
        return board.getFieldColor(row, column);
    }

    public String getPlayerShape(int numberPeon){
        if (numberPeon == 1){
            return player1.getPeon().getShape();
        }
        else if (numberPeon == 2){
            return player2.getPeon().getShape();
        }
        return null;
    }

    public Color getColorPlayer(int number){
        Color colorPlayer = (number == 0) ? player1.getColor():player2.getColor();
        return colorPlayer;
    }
    public int getBoardSize(){return board.getBoardSize();}
    public String getPlayerName(int player){
        return (player == 1) ? player1.getName() : player2.getName();
    }

    public int squaresVisited(Color playerColor, String type){
        Player selectedPlayer = player1.getColor().equals(playerColor) ? player1 : player2;
        return selectedPlayer.squaresVisited(type);
    }


    // OBSERVER FUNCTIONS
    public void addObserver(QuoridorObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(QuoridorObserver observer) {
        observers.remove(observer);
    }

    private void notifyTimesUpObservers(String message, String gameMode) {
        for (QuoridorObserver observer : observers) {
            observer.timesUp(message, gameMode);
        }

    }
    private void notifyMachineMovePeonObservers(String message, String direction) {
        for (QuoridorObserver observer : observers) {
            observer.machineMovePeon(message, direction);
        }
    }
    private void notifyMachineAddBarrierObservers(String message, int row, int column, String type, boolean isHorizontal) {
        for (QuoridorObserver observer : observers) {
            observer.machineAddBarrier(message, row, column, type, isHorizontal);
        }
    }

    // FILE FUNCTIONS
    public static Quoridor open(File file) throws QuoridorException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            String s = (String) in.readObject();
            return (Quoridor) in.readObject();
        } catch (FileNotFoundException e) {
            throw new QuoridorException(QuoridorException.FILE_NOT_FOUND);
        } catch (IOException e) {
            throw new QuoridorException(QuoridorException.ERROR_DURING_PROCESSING);
        } catch (ClassNotFoundException e) {
            throw new QuoridorException(QuoridorException.CLASS_NOT_FOUND);
        } catch (Exception e) {
            Log.record(e);
            throw new QuoridorException(QuoridorException.GENERAL_ERROR);
        }
    }

    public void save(File file) throws QuoridorException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file, true))) {
            out.writeObject("Quoridor storage\n");
            out.writeObject(this);
        } catch (FileNotFoundException e) {
            throw new QuoridorException(QuoridorException.FILE_NOT_FOUND);
        } catch (IOException e) {
            throw new QuoridorException(QuoridorException.ERROR_DURING_PROCESSING);
        } catch (Exception e) {
            Log.record(e);
            throw new QuoridorException(QuoridorException.GENERAL_ERROR);
        }
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
    public void addTeleporterSquare(int row1, int column1, int row2, int column2) {
        board.addTeleporterSquare(row1, column1, row2, column2);
    }
    public void addSkipTurnSquare(int row, int column){
        board.addSkipTurnSquare(row, column);
    }
    public void addRewindSquare(int row, int column) {
        board.addRewindSquare(row, column);
    }
    public boolean peonsHasAnExit(){return player1.peonHasAnExit() && player2.peonHasAnExit();}

    public void peonActualizeStrategyInformation(){
        player1.getPeon().actualizeStrategyInformation();
        player2.getPeon().actualizeStrategyInformation();
    }
}