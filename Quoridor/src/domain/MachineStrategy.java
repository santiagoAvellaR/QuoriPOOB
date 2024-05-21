package src.domain;

public interface MachineStrategy {
    void makeMove(Board board, Peon peon, Player otherPlayer);
}
