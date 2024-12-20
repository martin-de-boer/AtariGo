package go.ai.strategy;

import go.ai.interfaces.Strategy;
import go.model.game.Game;
import go.model.game.Move;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleStrategy implements Strategy {
    @Override
    public String toString() {
        return "Simple";
    }

    @Override
    public Move determineMove(Game game) {
        if (game.getValidMoves().isEmpty()) return null;
        if (findWinningMove(game) != null) return findWinningMove(game);
        if (findNonLosingMove(game) != null) return findNonLosingMove(game);
        else return game.getValidMoves().get((int) Math.floor(Math.random() * (game.getValidMoves().size() - 0.01)));
    }

    @Override
    public Map<Move, Double> determineMoveScores(Game game) {
        Map<Move, Double> result = new HashMap<>();
        Game newGame;

        for (Move move : game.getValidMoves()) {
            double score = 1.0;

            // Check if move is winning
            newGame = game.deepCopy();
            if (newGame.doMove(move)) {
                score += 100.0;
            }

            // Check if move is nonlosing
            newGame = game.deepCopy();
            newGame.doMove(move);
            if (findWinningMove(newGame) == null) {
                score += 50.0;
            }

            result.put(move, score);
        }
        return result;
    }

    //@ pure;
    public Move findWinningMove(Game game) {
        List<Move> validMoves = game.getValidMoves();
        for (Move move : validMoves) {
            Game newGame = game.deepCopy();
            if (newGame.doMove(move)) {
                return move;
            }
        }
        return null;
    }

    //@ pure;
    public Move findNonLosingMove(Game game) {
        List<Move> resMoves = findNonLosingMoves(game);
        if (resMoves.isEmpty()) {return null;}
        return resMoves.get((int) Math.floor(Math.random() * (resMoves.size() - 0.01)));
    }

    //@ pure;
    public List<Move> findNonLosingMoves(Game game) {
        List<Move> validMoves = game.getValidMoves();
        List<Move> resMoves = new ArrayList<>();
        for (Move move : validMoves) {
            Game newGame = game.deepCopy();
            newGame.doMove(move);
            if (findWinningMove(newGame) == null) {
                resMoves.add(move);
            }
        }
        return resMoves;
    }


}
