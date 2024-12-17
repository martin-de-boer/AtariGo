package go.model.interfaces;

/**
 * This interface is used to define methods for the Go game.
 */
public interface Game {
    void reset();
    void getBoard();
    void getMoves();
    void getScores();
    void getWinner();
    void getValidMoves();
}
