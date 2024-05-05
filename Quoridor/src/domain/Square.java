package src.domain;

import java.awt.*;

public abstract class Square extends Field{

    public Square(Color color){
        super(color);
    }

    @Override
    public boolean hasBarrier() {return false;}

    @Override
    public boolean hasPeon() {return false;}

    protected abstract void applySpecialAction();
}
