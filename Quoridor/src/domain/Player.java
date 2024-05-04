package src.domain;

public abstract class Player {
    private Peon peon;
    private int remainingBridges;

    public abstract void addBarrier();
    public abstract void movePeon(int row, int col);
}
