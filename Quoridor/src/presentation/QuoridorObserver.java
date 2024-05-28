package src.presentation;

public interface QuoridorObserver {
    void timesUp(String message, String gameMode);
    void machineMovePeon(String message, String direction);
    void machineAddBarrier(String message, int row, int column, String type, boolean isHorizontal);
}
