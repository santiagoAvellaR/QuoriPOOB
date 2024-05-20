package src.domain;

public class QuoridorException extends Exception{
    // files
    public static final String GENERAL_ERROR = "Error.";
    public static final String FILE_NOT_FOUND = "El archivo no pudo ser encontrado.";
    public static final String ERROR_DURING_PROCESSING = "Error durante el proceso.";
    public static final String CLASS_NOT_FOUND = "Clase no encontrada.";
    // filtering and cleaning data
    public static final String INVALID_SIZE = "Invalid size, size must be only numbers and not empty";
    public static final String MAXIMUM_SIZE_EXCEEDED = "The size must be between x and y";
    public static final String SIMILAR_PLAYER_COLORS = "The colors of both players are too similar";
    public static final String INVALID_NUMBER_SQUARES = "Invalid number barrier, number must be a only numbers and not empty";
    public static final String MAXIMUM_NUMBER_SQUARES_EXCEEDED = "The sum of barriers must be maximum (board size + 1)";
    public static final String INVALID_NUMBER_BARRIERS = "Invalid number barrier, number must be a only numbers and not empty";
    public static final String MAXIMUM_NUMBER_BARRIERS_EXCEEDED = "The sum of barriers must be maximum (board size + 1)";
    public static final String INVALID_BARRIER_TYPE = "Invalid barrier type";
    // game
    public static final String PLAYER_ONE_WON = "Player one won!";
    public static final String PLAYER_TWO_WON = "Player two won!";
    public static final String PLAYER_NOT_TURN = "It's not your turn";
    // time
    public static final String TIMES_UP_PLAYER_ONE = "Player one ran out of time!";
    public static final String TIMES_UP_PLAYER_TWO = "Player two ran out of time!";
    // barriers
    public static final String BARRIER_TRAP_PEON1 = "You can't place the barrier because it would trap the pawn 1.";
    public static final String BARRIER_TRAP_PEON2 = "You can't place the barrier because it would trap the pawn 2.";
    public static final String BARRIER_OVERLAP = "Barriers can not be overlapped";
    public static final String ERASE_TEMPORARY_BARRIER = "View must erase temporary barrier on the board";
    public static final String BARRIER_ALREADY_CREATED = "Barrier already created";
    public static final String DONT_HAVE_BARRIERS_LEFT = "You don't have a barriers to place of this type";
    // squares
    public static final String PEON_HAS_BEEN_TELEPORTED = "The peon has been teleported to another position";
    public static final String PEON_STEPPED_BACK = "The peon has been teleported to another position";
    public static final String PLAYER_PLAYS_TWICE = "Player plays twice";

    public QuoridorException(String message){
        super(message);
    }
}