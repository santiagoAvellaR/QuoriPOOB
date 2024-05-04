package src.domain;

public class QuoridorException extends Exception{

    public static final String INVALID_MOVEMENT = "Invalid movement";

    public QuoridorException(String message){
        super(message);
    }
}
