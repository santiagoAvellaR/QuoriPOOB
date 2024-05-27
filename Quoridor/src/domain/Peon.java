package src.domain;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Peon extends Field implements Serializable {
    private final int playerNumber;
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

    public Peon(int row, int column, Board board, Color color, int numberPlayer) {
        super(color);
        this.row = row;
        this.column = column;
        this.board = board;
        tracker = new ArrayList<String>();
        this.playerNumber = numberPlayer;
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

    public ArrayList<String> getValidMovements(int row, int column){
        ArrayList<String> validMovements = new ArrayList<String>();
        validateVertical(row, column, validMovements, true);
        validateVertical(row, column, validMovements, false);
        validateHorizontal(row, column, validMovements, true);
        validateHorizontal(row, column, validMovements, false);
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
    private void validateVertical(int row, int column, ArrayList<String> validMovementsCalculated, boolean goesUp){
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
        if (!board.hasPeon(row + 2*direction, column)){
            directionString = goesUp ? "n" : "s";
            validMovementsCalculated.add(directionString);
            return;
        }
        if (row <= 2 && goesUp){return;}
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
        if (!board.hasBarrier(row + 2*direction, column - 1) && !validVerticalMovements.contains(directionString)){
            validVerticalMovements.add(directionString);
        }
        directionString = goesUp ? "ne" : "se";
        if (!board.hasBarrier(row + 2*direction, column + 1) && !validVerticalMovements.contains(directionString)){
            validVerticalMovements.add(directionString);
        }
    }
    public void validateHorizontal(int row, int column, ArrayList<String> validMovementsCalculated, boolean goesLeft){
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
        if (!board.hasPeon(row, column + 2*direction)) {
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
        if (!board.hasBarrier(row+1, column + 2*direction) && !validVerticalMovements.contains(directionString)){
            validVerticalMovements.add(directionString);
        }
        directionString = goesLeft ? "nw" : "ne";
        if (!board.hasBarrier(row-1, column + 2*direction) && !validVerticalMovements.contains(directionString)){
            validVerticalMovements.add(directionString);
        }
    }

    private void printMatrix(Object[][] matrix){
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // functions for validate that peon has an exit (way/path to win)
    public boolean hasAnExitPrincipal(int simulateRow, int simulateColumn){
        Boolean[][] visited = new Boolean[getBoardSize()/2+1][getBoardSize()/2+1];
        for (Boolean[] booleans : visited) {
            Arrays.fill(booleans, false);
        }
        visited[simulateRow/2][simulateColumn/2] = true;
        return hasAnExit(simulateRow, simulateColumn, visited);
    }
    public boolean hasAnExit(int simulateRow, int simulateColumn, Boolean[][] positionsVisited){
        if ((simulateRow == 0  && playerNumber == 1) || (simulateRow == board.getBoardSize()-1 && playerNumber == 2)){return true;}
        if (board.getTypeField(simulateRow, simulateColumn).equals("ReWind")){return false;}
        positionsVisited[simulateRow/2][simulateColumn/2] = true;
        ArrayList<String> validDirections = getValidMovementsExits(simulateRow, simulateColumn);
        for (String direction : validDirections){
            int[] newPosition = getTheNewPositionAccordingDirection(simulateRow, simulateColumn, direction);
            if (!positionsVisited[newPosition[0]/2][newPosition[1]/2]) {
                if (hasAnExit(newPosition[0], newPosition[1], positionsVisited)) {
                    return true;
                }
            }
        }
        return false;
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
        if (row == 0 && goesUp) {return;}
        if (row == board.getBoardSize()-1 && !goesUp) {return;}
        int direction = goesUp ? -1 : 1;
        // verificar salto Simple
        if (board.hasBarrier(row + direction, column)) {
            return;
        }
        if (!board.hasPeon(row + 2*direction, column)){
            String directionString = goesUp ? "n" : "s";
            validMovementsCalculated.add(directionString);
            return;
        }
        if (row <= 2 && goesUp){return;}
        if (row >= board.getBoardSize()-3 && !goesUp){return;}
        // verificar salto Doble
        if (board.hasBarrier(row + 3*direction, column)) {
            return;
        }
        if (!board.hasPeon(row + 4*direction, column)) {
            String directionCharacter = goesUp ? "jn" : "js";
            validMovementsCalculated.add(directionCharacter);
        }
    }
    public void validateHorizontalExits(int row, int column, ArrayList<String> validMovementsCalculated, boolean goesLeft){
        if (column == 0 && goesLeft) {return;}
        if (column == board.getBoardSize()-1 && !goesLeft) {return;}
        int direction = goesLeft ? -1 : 1;
        // verificar salto Simple
        if (board.hasBarrier(row, column + direction)) {
            return;
        }
        if (!board.hasPeon(row, column + 2*direction)) {
            String directionString = goesLeft ? "w" : "e";
            validMovementsCalculated.add(directionString);
            return;
        }
        if (column <= 2 && goesLeft){return;}
        if (column >= board.getBoardSize()-2 && !goesLeft){return;}
        // verificar salto Doble
        if (board.hasBarrier(row, column + 3*direction)) {
            return;
        }
        if (board.hasPeon(row, column + 4*direction)) {
            String directionCharacter = goesLeft ? "je" : "jw";
            validMovementsCalculated.add(directionCharacter);
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
            if (getValidMovements(row, column).contains(tracker.get(i))){
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
    public void shortestPath(int simulateRow, int simulateColumn, String[][] path, Integer[][] costs, String lastMovement){
        long startTime = System.currentTimeMillis();
        if ((simulateRow == 0 && playerNumber == 1) || (simulateRow == board.getBoardSize()-1 && playerNumber == 2)){return;}
        if (board.getTypeField(simulateRow, simulateColumn).equals("ReWind")){return;}
        System.out.println("simulateRow: " + simulateRow + " simulateColumn: " + simulateColumn);
        ArrayList<String> validDirections = getValidMovements(simulateRow, simulateColumn);
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
    public ArrayList<String> reconstructShortestPath(){
        Integer[][] costs = new Integer[board.getBoardSize()/2 + 1][board.getBoardSize()/2 + 1];
        for (Integer[] ints : costs) {
            Arrays.fill(ints, Integer.MAX_VALUE);
        }
        costs[getRow()/2][getColumn()/2] = 0;
        shortestPath(this.row, this.column, new String[getBoardSize()/2 + 1][getBoardSize()/2 + 1], costs, "");
        int min, row, column;
        column = 0;
        min = Integer.MAX_VALUE;
        row = playerNumber == 1 ? 0 : costsMatrix.length-1;
        Integer[] significantRow= playerNumber == 1 ? costsMatrix[0] : costsMatrix[costsMatrix.length-1];
        for (int i = 0; i < costsMatrix.length; i++){
            if (significantRow[i] < min){
                min = significantRow[i];
                column = i;
            }
        }
        minimumNumberMovementsToWin = min;
        column = column*2;
        ArrayList<String> path = new ArrayList<>();
        while ((row != this.row) || (column != this.column)){
            path.addFirst(oppositeMovements.get(directionsMatrix[row/2][column/2]));
            printMatrix(directionsMatrix);
            System.out.println(directionsMatrix[row/2][column/2]);
            int[] newPosition = getTheNewPositionAccordingDirection(row, column, directionsMatrix[row/2][column/2]);
            row = newPosition[0];
            column = newPosition[1];
        }
        shortestPath = path;
        return path;
    }

    public String getContraryMovement(String movement){
        return oppositeMovements.get(movement);
    }
}
