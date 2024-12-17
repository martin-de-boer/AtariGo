package go.model.interfaces;

import go.model.game.Game;
import go.model.game.Move;

/**
 * This interface is used to define methods for various types of players of the Go game.
 */
public interface Player {
    /**
     * Returns the name of the player.
     * @return the name of the player
     */
    String getName();

    /**
     * Determines the next move. Implementation varies with different types of players.
     */
    Move determineMove(Game game);

}
