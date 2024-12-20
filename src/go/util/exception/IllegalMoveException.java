package go.util.exception;

public class IllegalMoveException extends RuntimeException {
    public IllegalMoveException(String board, String move) {
        super("Error: This move is illegal! " + board + "\n" + move);
    }
}
