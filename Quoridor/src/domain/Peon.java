package src.domain;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Peon extends Field implements Serializable {
    private int playerNumber = 0;
    private String shape;
    private int row;
    private int column;
    private final Board board;
    private ArrayList<String> tracker;
    private int rewindSquares;
    private int skipTurnSquares;
    private int teleporterSquares;
    private int normalSquares;
    private int transporterSquares;
    private String squareType;
    private HashMap<String, String> oppositeMovements;
    private Integer[][] costsMatrix;
    private String[][] directionsMatrix;
    private ArrayList<String> shortestPath;
    private int minimumNumberMovementsToWin;
    private int sumObjectiveRow;

    public Peon(int row, int column, Board board, Color color, int numberPlayer, String shape) {
        super(color);
        this.row = row;
        this.column = column;
        this.board = board;
        tracker = new ArrayList<String>();
        this.playerNumber = numberPlayer;
        this.shape = shape;
        rewindSquares = 0;
        skipTurnSquares = 0;
        teleporterSquares = 0;
        transporterSquares = 0;
        normalSquares = 1;
        squareType = "Normal";
        initializeHashOppositeMovements();
    }
    private void initializeHashOppositeMovements(){
        oppositeMovements = new HashMap<>();
        // ortogonals
        oppositeMovements.put("n", "s");
        oppositeMovements.put("s", "n");
        oppositeMovements.put("w", "e");
        oppositeMovements.put("e", "w");
        // jumps
        oppositeMovements.put("jn", "js");
        oppositeMovements.put("js", "jn");
        oppositeMovements.put("jw", "je");
        oppositeMovements.put("je", "jw");
        // diagonals
        oppositeMovements.put("ne", "sw");
        oppositeMovements.put("nw", "se");
        oppositeMovements.put("sw", "ne");
        oppositeMovements.put("se", "nw");
    }
    public int getRow() {
        return row;
    }
    public int getColumn() {
        return column;
    }
    public int getPlayerNumber() {
        return playerNumber;
    }

    public String getShape(){
        return shape;
    }

    public void setPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }
    public void setSquareType(String squareType) {
        this.squareType = squareType;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Peon) {
            Peon p = (Peon) obj;
            return (((row == p.row) && (column == p.column)) && color.equals(p.getColor()));
        }
        return false;
    }
    @Override
    public String getType(){return "Peon" + playerNumber;}
    @Override
    public boolean hasBarrier(){return false;}
    @Override
    public boolean hasSquare(){return false;}
    @Override
    public boolean hasPeon(){return true;}
    @Override
    public void actEachTurn() throws QuoridorException {
        if((this.playerNumber == 1 && this.row == 0)){
            throw new QuoridorException(QuoridorException.PLAYER_ONE_WON);
        }
        else if ((this.playerNumber == 2 && this.row == board.getBoardSize()-1)) {
            throw new QuoridorException(QuoridorException.PLAYER_TWO_WON);
        }
    }
    @Override
    public void applySpecialAction(){}

    public int getBoardSize(){return board.getBoardSize();}

    public void moveVertical(boolean goesUp) throws QuoridorException {
        int direction = goesUp ? -1 : 1;
        row += (direction * 2);
        tracker.add(goesUp ? "s" : "n");
        board.movePeon(row + direction*2*-1, column, row, column);


    }
    public void moveHorizontal(boolean goesLeft) throws QuoridorException {
        int direction = goesLeft ? -1 : 1;
        column += (direction * 2);
        tracker.add(goesLeft ? "e" : "w");
        board.movePeon(row, column + direction*2*-1, row, column);
    }
    public void jumpVertical(boolean goesUp) throws QuoridorException {
        int direction = goesUp ? -1 : 1;
        row += (direction*4);
        tracker.add(goesUp ? "js" : "jn");
        board.movePeon(row + direction*4*-1, column, row, column);
    }
    public void jumpHorizontal(boolean goesLeft) throws QuoridorException {
        int direction = goesLeft ? -1 : 1;
        column += (direction*4);
        tracker.add(goesLeft ? "je" : "jw");
        board.movePeon(row, column + direction*4*-1, row, column);
    }
    public void jumpDiagonalRight(boolean goesUp) throws QuoridorException {
        int direction = goesUp ? -1 : 1;
        row += (direction*2);
        column += 2;
        tracker.add(goesUp ? "sw" : "nw");
        board.movePeon(row + 2*direction*-1, column - 2, row, column);
    }
    public void jumpDiagonalLeft(boolean goesUp) throws QuoridorException {
        int direction = goesUp ? -1 : 1;
        row += (direction*2);
        column -= 2;
        tracker.add(goesUp ? "se" : "ne");
        board.movePeon(row + 2*direction*-1, column + 2, row, column);
    }

    public void move(String direction) throws QuoridorException {
        switch (direction) {
            //ortogonales
            case "n" -> moveVertical(true);
            case "s" -> moveVertical(false);
            case "w" -> moveHorizontal(true);
            case "e" -> moveHorizontal(false);
            // saltos
            case "jn" -> jumpVertical(true);
            case "js" -> jumpVertical(false);
            case "jw" -> jumpHorizontal(true);
            case "je" -> jumpHorizontal(false);
            //diagonales
            case "ne" -> jumpDiagonalRight(true);
            case "se" -> jumpDiagonalRight(false);
            case "nw" -> jumpDiagonalLeft(true);
            case "sw" -> jumpDiagonalLeft(false);
        }
    }

    public ArrayList<String> getValidMovements(int row, int column, boolean isAsimulation){
        ArrayList<String> validMovements = new ArrayList<String>();
        validateVertical(row, column, validMovements, true, isAsimulation);
        validateVertical(row, column, validMovements, false, isAsimulation);
        validateHorizontal(row, column, validMovements, true, isAsimulation);
        validateHorizontal(row, column, validMovements, false, isAsimulation);
        if (!squareType.equals("Transporter")){return validMovements;}
        return joinArrayList(calculateAllLateralMovements(row, column, new ArrayList<String>()), validMovements);
    }
    private ArrayList<String> calculateAllLateralMovements(int row, int column, ArrayList<String> movements){
        if (row >= 1 && !board.hasPeon(row - 2, column)) {movements.add("n");}
        if (row <= board.getBoardSize()-1-1 && !board.hasPeon(row + 2, column)) {movements.add("s");}
        if (column <= board.getBoardSize()-1-1 && !board.hasPeon(row, column + 2)) {movements.add("e");}
        if (column >= 1 && !board.hasPeon(row, column - 2)) {movements.add("w");}
        if (row >= 1 && column <= board.getBoardSize()-1-1 && !board.hasPeon(row - 2, column + 2)) {movements.add("ne");}
        if (row >= 1 && column >= 1 && !board.hasPeon(row - 2, column - 2)) {movements.add("nw");}
        if (row <= board.getBoardSize()-1-1 && column <= board.getBoardSize()-1-1 && !board.hasPeon(row + 2, column + 2)) {movements.add("se");}
        if (row <= board.getBoardSize()-1-1 && column >= 1 && !board.hasPeon(row + 2, column - 2)) {movements.add("sw");}
        return movements;
    }
    private ArrayList<String> joinArrayList(ArrayList<String> list1, ArrayList<String> list2){
        for (String word : list2){
            if (!list1.contains(word)){
                list1.add(word);
            }
        }
        return list1;
    }
    private void validateVertical(int row, int column, ArrayList<String> validMovementsCalculated, boolean goesUp, boolean isAsimulation){
        String directionString;
        if (row == 0 && goesUp) {return;}
        if (row == board.getBoardSize()-1 && !goesUp) {return;}
        int direction = goesUp ? -1 : 1;
        // verificar salto Simple
        if (board.hasBarrier(row + direction, column)) {
            Barrier barrier = (Barrier) board.getField(row + direction, column);
            if (!barrier.peonCanPassThrough(color)) {
                return;
            }
        }
        if (!board.hasPeon(row + 2*direction, column) || (board.hasPeon(row + 2*direction, column) && board.getFieldColor(row + 2*direction, column).equals(color))
        || (isAsimulation && board.hasPeon(row + 2*direction, column))){
            directionString = goesUp ? "n" : "s";
            validMovementsCalculated.add(directionString);
            return;
        }
        if (row <= 2 && goesUp ){return;}
        if (row >= board.getBoardSize()-3 && !goesUp){return;}
        // verificar salto Doble
        if (board.hasBarrier(row + 3*direction, column)) {
            Barrier barrier = (Barrier) board.getField(row + 3*direction, column);
            if (!barrier.peonCanPassThrough(color)) {
                validateVerticalDiagonals(row, column, validMovementsCalculated, goesUp);
                return;
            }
        }
        if (!board.hasPeon(row + 4*direction, column) && !board.getFieldColor(row + 2*direction, column).equals(color)) {
            directionString = goesUp ? "jn" : "js";
            validMovementsCalculated.add(directionString);
        }
    }
    private void validateVerticalDiagonals(int row, int column, ArrayList<String> validVerticalMovements, boolean goesUp){
        int direction = goesUp ? -1 : 1;
        String directionString = goesUp ? "nw" : "sw";
        if (!(column <= 0 )) {
            if (!board.hasBarrier(row + 2 * direction, column - 1) && !validVerticalMovements.contains(directionString)) {
                validVerticalMovements.add(directionString);
            }
        }
        if (!(column >= board.getBoardSize()-1)){
            directionString = goesUp ? "ne" : "se";
            if (!board.hasBarrier(row + 2 * direction, column + 1) && !validVerticalMovements.contains(directionString)) {
                validVerticalMovements.add(directionString);
            }
        }
    }
    public void validateHorizontal(int row, int column, ArrayList<String> validMovementsCalculated, boolean goesLeft, boolean isAsimulation){
        String directionString;
        if (column == 0 && goesLeft) {return;}
        if (column == board.getBoardSize()-1 && !goesLeft) {return;}
        int direction = goesLeft ? -1 : 1;
        // verificar salto Simple
        if (board.hasBarrier(row, column + direction)) {
            Barrier barrier = (Barrier) board.getField(row, column + direction);
            if (!barrier.peonCanPassThrough(color)) {
                return;
            }
        }
        if (!board.hasPeon(row, column + 2*direction) || (board.hasPeon(row, column + 2*direction) && board.getFieldColor(row, column + 2*direction).equals(color))
        || (isAsimulation && board.hasPeon(row, column + 2*direction))) {
            directionString = goesLeft ? "w" : "e";
            validMovementsCalculated.add(directionString);
            return;
        }
        if (column <= 2 && goesLeft){return;}
        if (column >= board.getBoardSize()-3 && !goesLeft){return;}
        // verificar salto Doble
        if (board.hasBarrier(row, column + 3*direction)) {
            Barrier barrier = (Barrier) board.getField(row, column + 3*direction);
            if (!barrier.peonCanPassThrough(color)) {
                validateHorizontalDiagonals(row, column, validMovementsCalculated, goesLeft);
                return;
            }
        }
        if (!board.hasPeon(row, column + 4*direction) && !board.getFieldColor(row, column + 2*direction).equals(color)) {
            directionString = goesLeft ? "jw" : "je";
            validMovementsCalculated.add(directionString);
        }
    }
    private void validateHorizontalDiagonals(int row, int column, ArrayList<String> validVerticalMovements, boolean goesLeft){
        int direction = goesLeft ? -1 : 1;
        String directionString = goesLeft ? "sw" : "se";
        if ( !(row >= board.getBoardSize()-1)) {
            if (!board.hasBarrier(row + 1, column + 2 * direction) && !validVerticalMovements.contains(directionString)) {
                validVerticalMovements.add(directionString);
            }
        }
        if (!(row <= 0)){
            directionString = goesLeft ? "nw" : "ne";
            if (!board.hasBarrier(row - 1, column + 2 * direction) && !validVerticalMovements.contains(directionString)) {
                validVerticalMovements.add(directionString);
            }
        }
    }

    private void printMatrix(Object[][] matrix){
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                String espacio = " ";
                if (matrix[i][j] == null) {
                    System.out.print("I" + "  ");
                }
                else if (matrix[i][j] instanceof Integer) {
                    int integer = (int)matrix[i][j];
                    if (integer == Integer.MAX_VALUE){
                        System.out.print("I" + "  ");
                    }
                    else {
                        if (integer < 10){espacio = "  ";}
                        System.out.print(integer + espacio);
                    }
                }
                else if (matrix[i][j] instanceof String) {
                    String string = (String)matrix[i][j];
                    if (string.length() <= 1){espacio = "  ";}
                    System.out.print(string + espacio);
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    // functions for validate that peon has an exit (way/path to win)
    public boolean hasAnExitMainMethod(int simulateRow, int simulateColumn){
        board.deleteTemporarilyPeonFromBoard(playerNumber == 1 ? 2 : 1);
        Boolean[][] visited = new Boolean[getBoardSize()/2+1][getBoardSize()/2+1];
        for (Boolean[] booleans : visited) {
            Arrays.fill(booleans, false);
        }
        visited[simulateRow/2][simulateColumn/2] = true;
        boolean hasAnexit = hasAnExit(simulateRow, simulateColumn, visited, "");
        board.addPeonToBoard(playerNumber == 1 ? 2 : 1);
        return hasAnexit;
    }
    private boolean hasAnExit(int simulateRow, int simulateColumn, Boolean[][] positionsVisited, String lastMovement) {
        if ((simulateRow == 0 && playerNumber == 1) || (simulateRow == board.getBoardSize() - 1 && playerNumber == 2)) {return true;}
        if (board.getTypeField(simulateRow, simulateColumn).equals("ReWind")) {return false;}
        positionsVisited[simulateRow / 2][simulateColumn / 2] = true;
        ArrayList<String> validDirections = getValidMovementsExits(simulateRow, simulateColumn);
        validDirections.remove(oppositeMovements.get(lastMovement));
        for (String direction : validDirections) {
            int[] newPosition = getTheNewPositionAccordingDirection(simulateRow, simulateColumn, direction);
            if (!positionsVisited[newPosition[0] / 2][newPosition[1] / 2]) {
                Boolean[][] newPositionsVisited = copyPositionsVisited(positionsVisited);
                if (hasAnExit(newPosition[0], newPosition[1], newPositionsVisited, direction)) {
                    return true;
                }
            }
        }
        return false;
    }

    private Boolean[][] copyPositionsVisited(Boolean[][] original) {
        Boolean[][] copy = new Boolean[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i].clone();
        }
        return copy;
    }
    private ArrayList<String> getValidMovementsExits(int simulateRow, int simulateColumn){
        ArrayList<String> movements = new ArrayList<String>();
        validateVerticalExits(simulateRow, simulateColumn, movements, true);
        validateVerticalExits(simulateRow, simulateColumn, movements, false);
        validateHorizontalExits(simulateRow, simulateColumn, movements, true);
        validateHorizontalExits(simulateRow, simulateColumn, movements, false);
        return movements;

    }
    private void validateVerticalExits(int row, int column, ArrayList<String> validMovementsCalculated, boolean goesUp){
        String directionString;
        if (row == 0 && goesUp) {return;}
        if (row == board.getBoardSize()-1 && !goesUp) {return;}
        int direction = goesUp ? -1 : 1;
        if (board.hasBarrier(row + direction, column)) {
            return;
        }
        if (board.hasPeon(row + 2*direction, column) || !board.hasPeon(row + 2*direction, column) || (board.hasPeon(row + 2*direction, column) && board.getFieldColor(row + 2*direction, column).equals(color))){
            directionString = goesUp ? "n" : "s";
            validMovementsCalculated.add(directionString);
            return;
        }
        if (row <= 2 && goesUp ){return;}
        if (row >= board.getBoardSize()-3 && !goesUp){return;}
        if (board.hasBarrier(row + 3*direction, column)) {
            Barrier barrier = (Barrier) board.getField(row + 3*direction, column);
            validateVerticalDiagonalsExits(row, column, validMovementsCalculated, goesUp);
            return;
        }
        if (!board.hasPeon(row + 4*direction, column) && !board.getFieldColor(row + 2*direction, column).equals(color)) {
            directionString = goesUp ? "jn" : "js";
            validMovementsCalculated.add(directionString);
        }
    }
    private void validateVerticalDiagonalsExits(int row, int column, ArrayList<String> validVerticalMovements, boolean goesUp){
        int direction = goesUp ? -1 : 1;
        String directionString = goesUp ? "nw" : "sw";
        if (!(column <= 0 )) {
            if (!board.hasBarrier(row + 2 * direction, column - 1) && !validVerticalMovements.contains(directionString)) {
                validVerticalMovements.add(directionString);
            }
        }
        if (!(column >= board.getBoardSize()-1)){
            directionString = goesUp ? "ne" : "se";
            if (!board.hasBarrier(row + 2 * direction, column + 1) && !validVerticalMovements.contains(directionString)) {
                validVerticalMovements.add(directionString);
            }
        }
    }
    public void validateHorizontalExits(int row, int column, ArrayList<String> validMovementsCalculated, boolean goesLeft){
        String directionString;
        if (column == 0 && goesLeft) {return;}
        if (column == board.getBoardSize()-1 && !goesLeft) {return;}
        int direction = goesLeft ? -1 : 1;
        // verificar salto Simple
        if (board.hasBarrier(row, column + direction)) {
            return;
        }
        if (board.hasPeon(row, column + 2*direction) || !board.hasPeon(row, column + 2*direction) || (board.hasPeon(row, column + 2*direction) && board.getFieldColor(row, column + 2*direction).equals(color))) {
            directionString = goesLeft ? "w" : "e";
            validMovementsCalculated.add(directionString);
            return;
        }
        if (column <= 2 && goesLeft){return;}
        if (column >= board.getBoardSize()-3 && !goesLeft){return;}
        // verificar salto Doble
        if (board.hasBarrier(row, column + 3*direction)) {
            validateHorizontalDiagonalsExits(row, column, validMovementsCalculated, goesLeft);
            return;
        }
        if (!board.hasPeon(row, column + 4*direction) && !board.getFieldColor(row, column + 2*direction).equals(color)) {
            directionString = goesLeft ? "jw" : "je";
            validMovementsCalculated.add(directionString);
        }
    }
    private void validateHorizontalDiagonalsExits(int row, int column, ArrayList<String> validVerticalMovements, boolean goesLeft){
        int direction = goesLeft ? -1 : 1;
        String directionString = goesLeft ? "sw" : "se";
        if ( !(row >= board.getBoardSize()-1)) {
            if (!board.hasBarrier(row + 1, column + 2 * direction) && !validVerticalMovements.contains(directionString)) {
                validVerticalMovements.add(directionString);
            }
        }
        if (!(row <= 0)){
            directionString = goesLeft ? "nw" : "ne";
            if (!board.hasBarrier(row - 1, column + 2 * direction) && !validVerticalMovements.contains(directionString)) {
                validVerticalMovements.add(directionString);
            }
        }
    }
    private int[] getTheNewPositionAccordingDirection(int row, int column, String direction){
        return switch (direction) {
            case "n" -> new int[]{row - 2, column};
            case "s" -> new int[]{row + 2, column};
            case "w" -> new int[]{row, column - 2};
            case "e" -> new int[]{row, column + 2};
            case "jn" -> new int[]{row - 4, column};
            case "js" -> new int[]{row + 4, column};
            case "jw" -> new int[]{row, column - 4};
            case "je" -> new int[]{row, column + 4};
            case "ne" -> new int[]{row - 2, column + 2};
            case "nw" -> new int[]{row - 2, column - 2};
            case "se" -> new int[]{row + 2, column + 2};
            case "sw" -> new int[]{row + 2, column - 2};
            default -> new int[]{};
        };
    }
    public void stepBackMovements(int quantityMovements) throws QuoridorException {
        for (int i = tracker.size() - 1; ((i >= tracker.size() - quantityMovements) && (i >= 0)); i--) {
            if (getValidMovements(row, column, false).contains(tracker.get(i))){
                move(tracker.get(i));
                tracker.remove(i);
            }
            else {break;}
            board.printBoard();
        }
    }

    public void passThroughSquare(String type) {
        switch (type) {
            case "N" -> normalSquares += 1;
            case "TL" -> teleporterSquares += 1;
            case "R" -> rewindSquares += 1;
            case "S" -> skipTurnSquares += 1;
            case "TR" -> transporterSquares += 1;
        }
    }
    public int squaresVisited(String type){
        return switch (type) {
            case "N" -> normalSquares;
            case "TL" -> teleporterSquares;
            case "R" -> rewindSquares;
            case "S" -> skipTurnSquares;
            case "TR" -> transporterSquares;
            default -> 0;
        };
    }

    // shortest path
    private void shortestPath(int simulateRow, int simulateColumn, String[][] path, Integer[][] costs, String lastMovement){
        long startTime = System.currentTimeMillis();
        if ((simulateRow == 0 && playerNumber == 1) || (simulateRow == board.getBoardSize()-1 && playerNumber == 2)){return;}
        if (board.getTypeField(simulateRow, simulateColumn).equals("ReWind")){return;}
        ArrayList<String> validDirections = getValidMovements(simulateRow, simulateColumn, true);
        validDirections.remove(lastMovement);
        int cost = board.getTypeField(simulateRow, simulateColumn).equals("SkipTurn") ? 0 : 1;
        for (String direction : validDirections){
            int[] newPosition = getTheNewPositionAccordingDirection(simulateRow, simulateColumn, direction);
            int newRow = newPosition[0];
            int newColumn = newPosition[1];
            if (costs[simulateRow/2][simulateColumn/2] + cost < costs[newRow/2][newColumn/2]){
                costs[newRow/2][newColumn/2] = costs[simulateRow/2][simulateColumn/2] + cost;
                path[newRow/2][newColumn/2] = oppositeMovements.get(direction);
                shortestPath(newPosition[0], newPosition[1], path, costs, oppositeMovements.get(direction));
            }
        }
        directionsMatrix = path;
        costsMatrix = costs;
    }
    private void reconstructShortestPath(){
        int min, row, column;
        column = 0;
        min = Integer.MAX_VALUE;
        int sumAllRow = 0;
        row = playerNumber == 1 ? 0 : costsMatrix.length-1;
        Integer[] significantRow= costsMatrix[row];
        for (int i = 0; i < costsMatrix.length; i++){
            if (significantRow[i] < min){
                min = significantRow[i];
                column = i;
            }
            if (significantRow[i] != Integer.MAX_VALUE){
                sumAllRow += significantRow[i];
            }
        }
        minimumNumberMovementsToWin = min;
        column = column*2;
        row = row*2;
        ArrayList<String> path = new ArrayList<>();
        int cont = 0;
        while (((row != this.row) || (column != this.column)) && directionsMatrix[row/2][column/2] != null){
            path.addFirst(oppositeMovements.get(directionsMatrix[row/2][column/2]));
            int[] newPosition = getTheNewPositionAccordingDirection(row, column, directionsMatrix[row/2][column/2]);
            row = newPosition[0];
            column = newPosition[1];
            cont+=1;
        }
        shortestPath = path;
        sumObjectiveRow = sumAllRow;
        //System.out.println("peon numero " + playerNumber);
        //System.out.println("minimo numero de pasos: " + minimumNumberMovementsToWin + "camino: " + shortestPath.toString());
        //printMatrix(directionsMatrix);
        //printMatrix(costsMatrix);
    }

    public void actualizeStrategyInformation(){
        Integer[][] costs = new Integer[board.getBoardSize()/2 + 1][board.getBoardSize()/2 + 1];
        for (Integer[] ints : costs) {
            Arrays.fill(ints, Integer.MAX_VALUE);
        }
        costs[row/2][column/2] = 0;
        shortestPath(row, column, new String[board.getBoardSize()/2 + 1][board.getBoardSize()/2 + 1], costs, "");
        reconstructShortestPath();
        System.out.println("minimo peon" + playerNumber + ": " + minimumNumberMovementsToWin);
        System.out.println("suma total de: " + sumObjectiveRow);
        //printMatrix(costsMatrix);
        //printMatrix(directionsMatrix);
    }

    public int getMinimumNumberMovementsToWin(){
        return minimumNumberMovementsToWin;
    }

    public String getBestPeonMovement(){
        return shortestPath.getFirst();
    }

    public String getContraryMovement(String movement){
        return oppositeMovements.get(movement);
    }

    public void printCostMatrix(){
        printMatrix(costsMatrix);
    }
    public int getSumObjectiveRow(){
        return sumObjectiveRow;
    }
}
