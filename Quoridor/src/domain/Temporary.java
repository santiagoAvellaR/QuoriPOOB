package src.domain;

import java.awt.*;

public class Temporary extends Barrier{
    private int remainingTime;

    public Temporary(Color color, boolean horizontal) {
        super(color, horizontal, 2);
        remainingTime = 4;
    }

    @Override
    public String getType() {
        return "Temporary";
    }

    public void reduceTime() throws QuoridorException {
        remainingTime--;
        if(remainingTime == 0){
            throw new QuoridorException(QuoridorException.ERRAASE_TEMPORARY_BARRIER);
        }
    }
}
