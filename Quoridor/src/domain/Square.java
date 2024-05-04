package src.domain;

public abstract class Square extends Field{
    protected abstract void applySpecialAction();

    @Override
    protected boolean hasBarrier() {
        return false;
    }
}
