package src.domain;

public abstract class Square extends Field{

    @Override
    protected boolean hasBarrier() {return false;}

    @Override
    protected boolean hasPeon() {return false;}

    protected abstract void applySpecialAction();
}
