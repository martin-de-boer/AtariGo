package go.ai.strategy;

import go.ai.interfaces.Strategy;
import go.model.game.Move;

/**
 * Naive strategy that picks an arbitrary (random move) to play.
 */
public class NaiveStrategy implements Strategy {
    /**
     * Method to determine the next move, which is a random one.
     * @return a random legal move
     */
    @Override
    public Move determineMove() {
        return null;
    }

    /**
     * Method to get the name of the strategy.
     * @return the name of the strategy
     */
    @Override
    public String toString() {
        return "Naive";
    }

}