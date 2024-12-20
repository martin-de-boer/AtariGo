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
    @Deprecated
    Move determineMove(Game game);

    /**
     * Method that gives every valid move a certain score according to the strategy.
     * The higher the score, the better it is according to the strategy.
     * Scores can be negative.
     * @return a dictionary where every valid move has a certain score
     */
    Map<Move, Double> determineMoveScores(Game game);

    /**
     * Method to get the name of the strategy.
     * @return the name of the strategy
     */
    @Override
    String toString();
}
