package go.util.exception;

import go.model.game.Board;
import go.model.game.Move;

public class IllegalMoveException extends RuntimeException {
    public IllegalMoveException(Board board, Move move) {
        super("Error: This move is illegal! " + move.toString() + board.toString());
    }
}
