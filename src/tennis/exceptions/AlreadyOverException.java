package tennis.exceptions;

public class AlreadyOverException extends RuntimeException {
    public AlreadyOverException() {
        super("The game/set is already over");
    }
}
