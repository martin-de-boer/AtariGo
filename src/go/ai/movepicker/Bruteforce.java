package go.ai.movepicker;

import go.ai.interfaces.Strategy;
import go.ai.strategy.SimpleStrategy;
import go.model.game.Game;
import go.model.game.Move;
import java.util.List;

public class Bruteforce implements Strategy {
    private final int depth;
    private final SimpleStrategy strat;

    /**
     * Constructs a Bruteforce strategy object with the specified strategy and search depth.
     *
     * @param strat the strategy to be used as the base for determining moves
     * @param depth the depth to which the brute-force algorithm should explore
     */
    public Bruteforce( int depth) {
        this.depth = depth;
        this.strat = new SimpleStrategy();
    }

    /**
     * Method to determine the next move according to the strategy.
     *
     * @param game
     * @return the next move that the strategy suggests
     */
    @Override
    public Move determineMove(Game game) {
        Move path = calculatePath(game, depth);
        if (path != null) return path;
        return strat.determineMove(game);
    }

    /**
     * Method to recursively calculate path and return a move if it is instantly winning.
     *
     * @return a winning move or a non-losing/arbitrary else
     */
    //@ requires depth >= 0;
    public Move calculatePath(Game game, int depth) {
        if (depth == 0) {
            return strat.determineMove(game);
        }

        Move winning = strat.findWinningMove(game);
        List<Move> nonLosing = strat.findNonLosingMoves(game);

        // Instantly return the winning move if exists
        if (winning != null) {
            return winning;
        }
        // Else: calculate path for non-losing moves firstly, then for arbitrary moves
        else if (!nonLosing.isEmpty()) {
            for (Move move : nonLosing) {
                Game newGame = game.deepCopy();
                newGame.doMove(move);
                if (calculatePath(newGame, depth - 1) != null) return move;
            }
        } else {
            for (Move move : game.getValidMoves()) {
                Game newGame = game.deepCopy();
                newGame.doMove(move);
                if (calculatePath(newGame, depth - 1) != null) return move;
            }
        }
        return null;
    }

    public int getDepth() {
        return depth;
    }

    @Override
    public String toString() {
        return "Bruteforce, depth = " + depth;
    }
}
