package src.domain;

import java.awt.*;

public abstract class Field {
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
    public abstract void act() throws QuoridorException;

}
