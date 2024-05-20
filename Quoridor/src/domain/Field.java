package src.domain;

import java.awt.Color;
import java.io.Serializable;

public abstract class Field implements Serializable {
    protected Color color;

    public Field(Color color) {
        this.color = color;
    }

    protected Color getColor(){
        return color;
    }
    protected void setColor(Color color){
        this.color = color;
    }

    public abstract String getType();
    public abstract boolean hasBarrier();
    public abstract boolean hasSquare();
    public abstract boolean hasPeon();

    public abstract void applySpecialAction() throws QuoridorException;
    public abstract void actEachTurn() throws QuoridorException;

}
