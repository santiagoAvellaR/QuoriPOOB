package src.presentation;

public interface QuoridorObserver {
    void timesUp(String message);
    void machineMovePeon(String message);
    void machineAddBarrier(String message, int row, int column, String type, boolean isHorizontal);
}
