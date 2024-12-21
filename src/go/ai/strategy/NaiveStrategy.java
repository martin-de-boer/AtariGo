package go.ai.strategy;

import go.ai.interfaces.Strategy;
import go.model.game.Game;
import go.model.game.Move;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Naive strategy that picks an arbitrary (random move) to play.
 */
public class NaiveStrategy implements Strategy {
    /**
     * Method to determine the next move, which is a random one.
     * @return a random legal move
     */
    @Deprecated
    public Move determineMove(Game game) {
        List<Move> moves = game.getValidMoves().stream().toList();

        double random = Math.random();

        if (moves.isEmpty()) {
            return null;
        }

        double floor = Math.floor(random * (moves.size() - 0.01));
        return moves.get((int) floor);
    }

    /**
     * Method that gives every valid move a random score
     *
     * @return a dictionary where every valid move has a certain score
     */
    @Override
    public Map<Move, Double> determineMoveScores(Game game) {
        Map<Move, Double> scores = new HashMap<Move, Double>();
        List<Move> moves = game.getValidMoves().stream().toList();
        for (Move move : moves) {
            scores.put(move, Math.random());
        }
        return scores;
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