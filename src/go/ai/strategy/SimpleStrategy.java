package go.ai.strategy;

import go.ai.interfaces.Strategy;
import go.model.game.Game;
import go.model.game.Move;
import go.model.interfaces.Color;
import java.util.ArrayList;
import java.util.List;

public class SimpleStrategy implements Strategy {
    @Override
    public String toString() {
        return "Simple";
    }

    @Override
    public Move determineMove(Game game) {
        if (findWinningMove(game) != null) return findWinningMove(game);
        if (findNonLosingMove(game) != null) return findNonLosingMove(game);
        else return game.getValidMoves().get((int) Math.floor(Math.random() * (game.getValidMoves().size() - 0.01)));
    }

    public Move findWinningMove(Game game) {
        List<? extends Move> validMoves = game.getValidMoves();
        for (Move move : validMoves) {
            Game newGame = game.deepCopy();
            if (newGame.doMove(move)) {
                return move;
            }
        }
        return null;
    }

    public Move findNonLosingMove(Game game) {
        List<? extends Move> validMoves = game.getValidMoves();
        List<Move> resMoves = new ArrayList<>();
        for (Move move : validMoves) {
            Game newGame = game.deepCopy();
            newGame.doMove(move);
            if (findWinningMove(newGame) == null) {
                resMoves.add(move);
            }
        }
        if (resMoves.isEmpty()) {return null;}
        return resMoves.get((int) Math.floor(Math.random() * (resMoves.size() - 0.01)));
    }


}