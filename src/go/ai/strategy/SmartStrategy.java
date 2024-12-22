package go.ai.strategy;

import go.ai.evaluation.Evaluation;
import go.model.game.Game;
import go.model.game.Move;
import go.model.interfaces.Color;

public class SmartStrategy extends Evaluation {
    public Move evalMove = null;

    public SmartStrategy(Game game, int depth) {
        super(game, depth);
    }

    public Move bestMove() {
        int depth = super.evalDepth;
        Game game = super.evalGame;

        if (depth == 0 || game.isGameOver()) {
            return null;
        }

        Move bestMove = null;

        boolean isMaximizingPlayer = super.evalGame.getTurn().getColor() == Color.BLACK;
        double alpha = Double.NEGATIVE_INFINITY;
        double beta = Double.POSITIVE_INFINITY;

        if (isMaximizingPlayer) {
            double maxEval = Double.NEGATIVE_INFINITY;
            for (Move move : game.getValidMovesSmart()) {
                Game newGame = game.deepCopy();
                newGame.doMove(move);

                double eval = super.evaluate(newGame, depth - 1, false, alpha, beta);

                if (maxEval < eval) {
                    maxEval = eval;
                    bestMove = move;
                }

                alpha = Math.max(alpha, eval);
            }
            return bestMove;

        } else {
            double minEval = Double.POSITIVE_INFINITY;
            for (Move move : game.getValidMovesSmart()) {
                Game newGame = game.deepCopy();
                newGame.doMove(move);

                double eval = super.evaluate(newGame, depth - 1, true, alpha, beta);

                if (minEval > eval) {
                    minEval = eval;
                    bestMove = move;
                }
                beta = Math.min(beta, eval);
            }
            return bestMove;
        }
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
        for (int i = 1; i <= super.evalDepth && !Thread.interrupted(); i++) {
            Move move = bestMove();
            evalMove = move;
        }
    }
}
