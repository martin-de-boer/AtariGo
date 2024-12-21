package go.ai.strategy;

import go.ai.interfaces.Strategy;
import go.model.game.Game;
import go.model.game.Move;
import go.model.interfaces.Color;
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
        else return game.getValidMoves().stream().toList().get((int) Math.floor(Math.random() * (game.getValidMoves().size() - 0.01)));
    }

    @Override
    public Map<Move, Double> determineMoveScores(Game game) {
        Map<Move, Double> result = new HashMap<>();
        Game newGame;

        for (Move move : game.getValidMoves()) {
            double score = 1.0;

            // Check if move is winning
            newGame = game.deepCopy();
            newGame.doMove(move);
            if (newGame.isGameOver()) {
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
        List<Move> validMoves = game.getValidMoves().stream().toList();
        for (Move move : validMoves) {
            Game newGame = game.deepCopy();
            newGame.doMove(move);
            if (newGame.isGameOver()) {
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
        List<Move> validMoves = game.getValidMoves().stream().toList();
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
