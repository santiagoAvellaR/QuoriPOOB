package src.domain;

import java.awt.*;

public abstract class Field {
    protected Color color;

    protected Color getColor(){
        return color;
    }
    protected void setColor(Color color){
        this.color = color;
    }

    protected abstract String getTipy();
    protected abstract boolean hasBarrier();
    protected abstract boolean hasSquare();

}
