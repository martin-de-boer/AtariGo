package go.model.interfaces;

import go.model.game.Game;
import go.model.game.Move;

/**
 * This interface is used to define methods for various types of players of the Go game.
 */
public interface Player {
    /**
     * Determines the next move. Implementation varies with different types of players.
     * @return a valid Move
     */
    //@requires !game.isGameOver();
    //@ensures game.isValidMove(\result);
    Move determineMove(Game game);

    /**
     * returns the color of the player
     * @return the color of the player
     */
    Color getColor();
}
