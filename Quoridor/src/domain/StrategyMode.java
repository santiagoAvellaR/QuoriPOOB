package src.domain;

import src.presentation.QuoridorObserver;

import java.util.ArrayList;

public abstract class StrategyMode implements MachineStrategy{
    protected String movementType;
    protected String direction;
    protected int row;
    protected int column;
    protected boolean isHorizontal;

    private ArrayList<QuoridorObserver> observers = new ArrayList<>();
}
