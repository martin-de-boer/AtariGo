package go.ai.strategy;

import go.ai.interfaces.Strategy;
import go.model.game.Board;
import go.model.game.Game;
import go.model.game.Move;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SimpleStrategy implements Strategy {
    @Override
    public String toString() {
        return "Simple";
    }

    @Override
    public Move determineMove(Game game) {
        List<Move> validMoves = game.getValidMoves();
        Board board = game.getBoard();

        if (validMoves.isEmpty()) return null;

        Move winningMove = findWinningMove(board);
        if (winningMove != null) return winningMove;

        Move nonLosingMove = findNonLosingMove(board);
        if (nonLosingMove != null) return nonLosingMove;

        Move move = validMoves.stream().toList().get((int) Math.floor(Math.random() * (game.getValidMoves().size())));

        if(isBadMove(board, move)) move = validMoves.stream().toList().get((int) Math.floor(Math.random() * (game.getValidMoves().size())));

        return move;
    }

    public Boolean isBadMove(Board board, Move move) {
        Board newBoard = board.deepCopy();
        newBoard.setField(move.getField(),move.getColor());
        return board.numOfLiberties(move.getField()) >= 2;
    }

    //@ pure;
    public Move findWinningMove(Board board) {
        for (Set<Integer> group : board.getGroups(board.getTurn().other())) {
            Set<Integer> liberties = board.getLiberties(group);
            if(liberties.size() == 1) {
                return new Move(liberties.iterator().next(), board.getTurn()) ;
            }
        }
        return null;
    }

    //@ pure;
    public Move findNonLosingMove(Board board) {
        for (Set<Integer> group : board.getGroups(board.getTurn())) {
            Set<Integer> liberties = board.getLiberties(group);
            if(liberties.size() == 1) {
                return new Move(board.getLiberties(group).iterator().next(), board.getTurn()) ;
            }
        }
        return null;
    }
}
