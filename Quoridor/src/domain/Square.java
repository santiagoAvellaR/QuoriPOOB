package src.domain;

import java.awt.Color;
import java.io.Serializable;

public abstract class Square extends Field implements Serializable {
    protected int row;
    protected int column;
    protected Peon peon;

    public Square(Color color, int row, int column){
        super(color);
        this.row = row;
        this.column = column;
        peon = null;
    }

    @Override
    public boolean hasBarrier() {return false;}
    @Override
    public boolean hasPeon() {return (peon != null);}
    @Override
    public void actEachTurn(){}
    @Override
    public boolean hasSquare() {return true;}

    public void setPeon(Peon newPeon){
        peon = newPeon;
    }

    public Peon getPeon(){return peon;}
    public int getRow(){return this.row;}
    public int getColumn(){return this.column;}

    @Override
    public void applySpecialAction() throws QuoridorException{}
}
