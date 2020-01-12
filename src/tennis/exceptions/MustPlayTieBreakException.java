package tennis.exceptions;

public class MustPlayTieBreakException extends RuntimeException {
    public MustPlayTieBreakException() {
        super("A TieBreakGame must be play, not a NormalGame");
    }
}
