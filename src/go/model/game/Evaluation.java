package go.model.game;

import go.model.interfaces.Color;
import java.util.concurrent.*;

public class Evaluation implements Runnable {
    public double evalScore = 0;
    public int evalDepth = 0;
    public Game evalGame = null;

    public Evaluation(Game game, int depth) {
        evalGame = game;
        evalDepth = depth;
    }

    public double evaluate(Game game, int depth) {
        return evaluate(game, depth, true, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    public double evaluate(Game game, int depth, boolean isMaximizingPlayer, double alpha, double beta) {
        if (depth == 0 || game.isGameOver()) {
            return evaluatePosition(game);
        }

        if (isMaximizingPlayer) {
            double maxEval = Double.NEGATIVE_INFINITY;
            for (Move move : game.getValidMovesSmart()) {
                Game newGame = game.deepCopy();
                newGame.doMove(move);

                double eval = evaluate(newGame, depth - 1, false, alpha, beta);
                maxEval = Math.max(maxEval, eval);

                if (beta < eval) {
                    break;
                }
                alpha = Math.max(alpha, eval);
            }
            return maxEval;

        } else {
            double minEval = Double.POSITIVE_INFINITY;
            for (Move move : game.getValidMovesSmart()) {
                Game newGame = game.deepCopy();
                newGame.doMove(move);

                double eval = evaluate(newGame, depth - 1, true, alpha, beta);
                minEval = Math.min(minEval, eval);

                if (beta < eval) {
                    break;
                }
                beta = Math.min(beta, eval);
            }
            return minEval;
        }
    }

    public double evaluatePosition(Game game) {
        double score = 0;

        // Check if the game is over
        if (game.isGameOver()) {
            return game.getWinner() == Color.BLACK ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
        }

        // Evaluate based on liberties
        score += evaluateLiberties(game, Color.BLACK);
        score -= evaluateLiberties(game, Color.WHITE);

        // Evaluate potential captures
        score += 100 * countPotentialCaptures(game, Color.BLACK);
        score -= 100 * countPotentialCaptures(game, Color.WHITE);


        return score;
    }

    public double evaluateLiberties(Game game, Color color) {
        double libertyScore = 0;
        for (int i : game.getBoard().getFields(color)) {
            int liberties = game.getBoard().numOfLiberties(i);
            if (liberties == 1) {
                libertyScore -= 50; // Heavily penalize stones in atari
            } else {
                libertyScore += liberties;
            }
        }
        return libertyScore;
    }

    public int countPotentialCaptures(Game game, Color color) {
        int potentialCaptures = 0;
        for (int opponentStone : game.getBoard().getFields(color.other())) {
            if (game.getBoard().numOfLiberties(opponentStone) == 1) {
                potentialCaptures++;
            }
        }
        //TODO change so capturing two stones with one move counts as one
        return potentialCaptures;
    }

    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        for (int i = 0; i < evalDepth && !Thread.interrupted(); i++) {
            double score = evaluate(evalGame, i);
            evalScore = score;
        }
    }
}
