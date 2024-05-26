package src.domain;

public interface MachineStrategy {
    void makeMove(Board board, Peon peon, Player otherPlayer, int normalBarriers, int temporaryBarriers, int longBarriers, int alliedBarriers) throws QuoridorException;
}
