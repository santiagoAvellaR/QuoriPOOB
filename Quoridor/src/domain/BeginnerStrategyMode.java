package src.domain;

import java.util.ArrayList;
import java.util.Random;

public class BeginnerStrategyMode extends StrategyMode implements MachineStrategy{

    @Override
    public void makeMove(Board board, Peon peon, Player otherPlayer, int normalBarriers, int temporaryBarriers, int longBarriers, int alliedBarriers) throws QuoridorException {
        if (generateRandomNumber(2) == 0 && (normalBarriers > 0 || temporaryBarriers > 0 || longBarriers > 0 || alliedBarriers > 0)){
            movementType = "addBarrier";
            isHorizontal = generateRandomNumber(2) == 0;
            barrierType = selectBarrierType(normalBarriers, temporaryBarriers, longBarriers, alliedBarriers);
            int barrierLength = barrierType.equals("l") ? 3 : 2;
            if (isHorizontal){
                int row = oddNumberGenerator(board.getBoardSize());
                int column = evenNumberGenerator(board.getBoardSize());
                if (board.barrierCanBePlace(row, column, barrierLength, isHorizontal)){
                    this.row = row;
                    this.column = column;
                    throw new QuoridorException(QuoridorException.MACHINE_ADD_A_BARRIER);
                } else {
                    makeMove(board, peon, otherPlayer, normalBarriers, temporaryBarriers, longBarriers, alliedBarriers);
                }
            }
            else {
                int row = evenNumberGenerator(board.getBoardSize());
                int column = oddNumberGenerator(board.getBoardSize());
                if (board.barrierCanBePlace(row, column, barrierLength, isHorizontal)){
                    this.row = row;
                    this.column = column;
                    throw new QuoridorException(QuoridorException.MACHINE_ADD_A_BARRIER);
                } else {
                    makeMove(board, peon, otherPlayer, normalBarriers, temporaryBarriers, longBarriers, alliedBarriers);
                }
            }
        }
        else {
            movementType = "movePeon";
            ArrayList<String> validMovements = peon.getValidMovements(peon.getRow(), peon.getColumn());
            direction = validMovements.get(generateRandomNumber(validMovements.size()));
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
        return random.nextInt((limit / 2) + 1) * 2 + 1;
    }

}
