package src.domain;

public abstract class Barrier extends Field{

    protected boolean orientarion;

    @Override
    protected boolean hasSquare() {return false;}
    @Override
    protected boolean hasPeon() {return false;}
}
