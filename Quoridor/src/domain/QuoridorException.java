package src.domain;

public class QuoridorException extends Exception{

    public static final String INVALID_MOVEMENT = "Invalid movement";
    public static final String PLAYER_NOT_TURN = "It's not your turn";
    public static final String ERRAASE_TEMPORARY_BARRIER = "View must errase temporary barrier on the board";
    public static final String BARRIER_ALREADY_CREATED = "Barrier already created";
    public static final String INVELID_BARRIER_TYPE = "Invelid barrier type";
    // filtering and cleaning data
    public static final String INVLID_SIZE = "Invelid size, size must be only numbers and not empty";
    public static final String MAXIMUN_SIZE_EXCEEDED = "The size must be between x and y";
    public static final String SIMILAR_PLAYER_COLORS = "The colors of both players are too similar";
    public static final String INVALID_NUMBER_SQUARES = "Invalid number barrier, number must be a only numbers and not empty";
    public static final String MAXIMUN_NUMBER_SQUARES_EXCEEDED = "The sum of barriers must be maximun (board size + 1)";
    public static final String INVALID_NUMBER_BARRIERS = "Invalid number barrier, number must be a only numbers and not empty";
    public static final String MAXIMUN_NUMBER_BARRIERS_EXCEEDED = "The sum of barriers must be maximun (board size + 1)";

    public QuoridorException(String message){
        super(message);
    }
}
