package src.domain;

import java.awt.Color;

public abstract class Barrier extends Field{
    protected int length;
    protected boolean horizontal;

    public Barrier(Color color, boolean horizontal, int length){
        super(color);
        this.horizontal = horizontal;
        this.length = length;
    }

    public int getLength() {return length;}
    public boolean isHorizontal() {return horizontal;}

    public boolean isAllied(Color color){return false;}

    @Override
    public boolean hasSquare() {return false;}
    @Override
    public boolean hasPeon() {return false;}
    @Override
    public boolean hasBarrier() {return true;}
    @Override
    public void actEachTurn() throws QuoridorException{}
    @Override
    public void applySpecialAction(){}
}
