package go.ai.interfaces;

import go.model.game.Game;
import go.model.game.Move;
import java.util.Dictionary;
import java.util.Map;

/**
 * This interface defines the basic methods a strategy needs.
 * The computer player will use a strategy in order to determine its next move
 */
public interface Strategy {
    /**
     * Method to determine the next move according to the strategy.
     * @return the next move that the strategy suggests
     */
    Move determineMove(Game game);

    /**
     * Method to get the name of the strategy.
     * @return the name of the strategy
     */
    @Override
    String toString();
}
