package src.domain;

import java.io.Serializable;

public interface MachineStrategy {
    void makeMove(Board board, Peon peon, Player otherPlayer, int normalBarriers, int temporaryBarriers, int longBarriers, int alliedBarriers) throws QuoridorException;
}
