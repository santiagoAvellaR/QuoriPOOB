package src.domain;

public class QuoridorException extends Exception{

    public static final String INVALID_MOVEMENT = "Invalid movement";
    public static final String PLAYER_NOT_TURN = "It's not your turn";
    public static final String ERRAASE_TEMPORARY_BARRIER = "View must errase temporary barrier on the board";
    public static final String BARRIER_ALREADY_CREATED = "Barrier already created";

    public QuoridorException(String message){
        super(message);
    }
}
