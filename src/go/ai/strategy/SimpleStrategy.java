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
        //if (findNonLosingMove(game) != null) return findNonLosingMove(game);
        else return game.getValidMoves().stream().toList().get((int) Math.floor(Math.random() * (game.getValidMoves().size() - 0.01)));
    }

    //@ pure;
    public Move findWinningMove(Game game) {
        List<Move> validMoves = game.getValidMoves().stream().toList();
        for (Move move : validMoves) {
            Game newGame = game.deepCopy();
            newGame.doMove(move);
            if (newGame.isGameOver() && newGame.getWinner() == game.getTurn().getColor()) {
                return move;
            }
        }
        return null;
    }

    //@ pure;
    public Move findNonLosingMove(Game game) {
        List<Move> resMoves = findNonLosingMoves(game);
        if (resMoves.isEmpty()) {return null;}
        return resMoves.get((int) Math.floor(Math.random() * (resMoves.size())));
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
