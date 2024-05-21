package src.domain;

import java.util.ArrayList;
import java.util.Random;

public class BeginnerStrategyMode extends StrategyMode implements MachineStrategy{

    @Override
    public void makeMove(Board board, Peon peon, Player otherPlayer){
        if (generateRandomNumber(2) == 0){
            movementType = "addBarrier";
        }
        else {
            movementType = "movePeon";
            ArrayList<String> validMovements = peon.getValidMovements();
            direction = validMovements.get(generateRandomNumber(validMovements.size()));

        }
    }

    private int generateRandomNumber(int n) {
        Random random = new Random();
        return random.nextInt(n);
    }
}
