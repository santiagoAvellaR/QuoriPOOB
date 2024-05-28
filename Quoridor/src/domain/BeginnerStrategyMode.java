package src.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class BeginnerStrategyMode extends StrategyMode implements MachineStrategy, Serializable {

    @Override
    public void makeMove(Board board, Peon peon, Player otherPlayer, int normalBarriers, int temporaryBarriers, int longBarriers, int alliedBarriers) throws QuoridorException {
        if (generateRandomNumber(2) == 0 && (normalBarriers > 0 || temporaryBarriers > 0 || longBarriers > 0 || alliedBarriers > 0)){
            System.out.println("añade barrera");
            movementType = "addBarrier";
            isHorizontal = generateRandomNumber(2) == 0;
            barrierType = selectBarrierType(normalBarriers, temporaryBarriers, longBarriers, alliedBarriers);
            int barrierLength = barrierType.equals("l") ? 3 : 2;
            System.out.println(oddNumberGenerator(barrierLength == 3 ? board.getBoardSize()-4 : board.getBoardSize()-2));
            System.out.println(oddNumberGenerator(barrierLength == 3 ? board.getBoardSize()-4 : board.getBoardSize()-2));
            System.out.println(oddNumberGenerator(barrierLength == 3 ? board.getBoardSize()-4 : board.getBoardSize()-2));
            if (isHorizontal){
                int row = oddNumberGenerator(barrierLength == 3 ? board.getBoardSize()-4 : board.getBoardSize()-2);
                int column = evenNumberGenerator(barrierLength == 3 ? board.getBoardSize()-4 : board.getBoardSize()-2);
                if (board.barrierCanBePlace(row, column, barrierLength, isHorizontal)){
                    this.row = row;
                    this.column = column;
                    throw new QuoridorException(QuoridorException.MACHINE_ADD_A_BARRIER);
                } else {
                    makeMove(board, peon, otherPlayer, normalBarriers, temporaryBarriers, longBarriers, alliedBarriers);
                }
            }
            else {
                int row = evenNumberGenerator(barrierLength == 3 ? board.getBoardSize()-4 : board.getBoardSize()-2);
                int column = oddNumberGenerator(barrierLength == 3 ? board.getBoardSize()-4 : board.getBoardSize()-2);
                if (board.barrierCanBePlace(row, column, barrierLength, isHorizontal)){
                    this.row = row;
                    this.column = column;
                    System.out.println("barrera: " + row + " " + column + " " + barrierLength + " " + isHorizontal + " " + barrierType);
                    throw new QuoridorException(QuoridorException.MACHINE_ADD_A_BARRIER);
                } else {
                    makeMove(board, peon, otherPlayer, normalBarriers, temporaryBarriers, longBarriers, alliedBarriers);
                }
            }
        }
        else {
            movementType = "movePeon";
            ArrayList<String> validMovements = peon.getValidMovements(peon.getRow(), peon.getColumn(), false);
            direction = validMovements.get(generateRandomNumber(validMovements.size()));
            System.out.println("direccion seleccionada por machine: " + direction);
            System.out.println("maquina mueve peon en direction: " + direction);
            throw new QuoridorException(QuoridorException.MACHINE_MOVE_PEON);
        }
    }

    private String selectBarrierType(int normalBarriers, int temporaryBarriers, int longBarriers, int alliedBarriers){
        int type = generateRandomNumber(4);
        String barrierType = switch (type) {
            case 0 -> "n";
            case 1 -> "t";
            case 2 -> "l";
            case 3 -> "a";
            default -> "";
        };
        if (barrierType.equals("n") && normalBarriers > 0){
            return "n";
        } else if (barrierType.equals("t") && temporaryBarriers > 0) {
            return "t";
        } else if (barrierType.equals("l") && longBarriers > 0) {
            return "l";
        }
        else if (barrierType.equals("a") && alliedBarriers > 0) {
            return "a";
        }
        else return selectBarrierType(normalBarriers, temporaryBarriers, longBarriers, alliedBarriers);
    }

    private int generateRandomNumber(int n) {
        Random random = new Random();
        return random.nextInt(n);
    }
    private int evenNumberGenerator(int limit){
        Random random = new Random();
        return random.nextInt((limit / 2) + 1) * 2;
    }
    private static int oddNumberGenerator(int limit) {
        Random random = new Random();
        return random.nextInt((limit / 2)) * 2 + 1;
    }

    @Override
    public void addBarriersThatBlockPeon(int row, int column){}
}
