package src.domain;

import src.presentation.QuoridorObserver;

import java.util.ArrayList;

public abstract class StrategyMode implements MachineStrategy{
    protected String movementType;
    protected String direction;
    protected String barrierType;
    protected int row;
    protected int column;
    protected boolean isHorizontal;

    public String getMovementType() {
        return movementType;
    }
    public String getDirection() {
        return direction;
    }
    public String getBarrierType() {
        return barrierType;
    }
    public int getRow() {
        return row;
    }
    public int getColumn() {
        return column;
    }
    public boolean isHorizontal() {
        return isHorizontal;
    }

}
