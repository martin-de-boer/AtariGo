package go.ai.strategy;

import go.ai.interfaces.Strategy;
import go.model.game.Game;
import go.model.game.Move;
import java.util.List;

/**
 * Naive strategy that picks an arbitrary (random move) to play.
 */
public class SmartStrategy implements Strategy {
    /**
     * Method to determine the next move, which is a random one.
     * @return a random legal move
     */
    @Override
    public Move determineMove(Game game) {
        List<Move> moves = game.getValidMoves();

        double random = Math.random();

        if (moves.isEmpty()) {
            return null;
        }

        double floor = Math.floor(random * (moves.size() - 0.01));
        return moves.get((int) floor);
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