package go.model.game;

import go.model.interfaces.Color;
import go.model.interfaces.Player;
import go.util.exception.IllegalMoveException;
import java.util.*;

/**
 * This class runs a game of Go.
 */
public class Game {
    private final Player p1;
    private final Player p2;
    private Board board;


    /**
     * Creates a game with an empty board.
     */
    public Game(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.board = new Board();
    }

    public Game(Player p1, Player p2, Board board) {
        this(p1,p2);
        this.board = board.deepCopy();
    }

    public Player getP2() {
        return p2;
    }

    public Player getP1() {
        return p1;
    }

    /**
     * Returns the board attached to the game.
     * @return the board of the game
     */
    public Board getBoard() {
        return this.board;
    }

    public Boolean isGameOver() {
        return board.isGameOver();
    }

    public Color getWinner() {
        for (int i = 0; i < Board.DIM * Board.DIM; i++) {
            if (board.getColor(i) != Color.EMPTY && board.numOfLiberties(board.getGroup(i)) == 0) {
                return board.getColor(i).other();
            }
        }
        return null;
    }

    public boolean captures(Move move) {
        for (int i : board.getNeighbors(move.getField())) {
            if (!board.isEmpty(i) && 1 == board.numOfLiberties(board.getGroup(i))) {
                return true;
            }
        }
        return false;
    }



    /**
     * Returns the player whose turn it is.
     * @return the player whose turn it is
     */
    public Player getTurn() {
        return board.getTurn() == Color.BLACK ? p1 : p2;
    }

    /**
     * Returns whether the move specified is legal or not
     * @return true if the move is legal, false otherwise
     */
    //@ pure;
    public boolean isValidMove(Move move) {

        if(!board.isValidField(move.getField())) {
            return false;
        }
        return move.getColor() == board.getTurn() && board.isEmpty(move.getField());
    }

    /**
     * Method to get all legal moves in the current position.
     * @return all legal moves in the current position.
     */
    public List<Move> getValidMoves() {
        List<Move> moves = new ArrayList<>();

        for (int i = 0; i < Board.DIM * Board.DIM; i++) {
            Move move = new Move(i, board.getTurn());
            if (isValidMove(move)) {
                moves.add(move);
            }
        }
        return moves;
    }

    public List<Move> getValidMovesSmart() {
        List<Pair> scores = new ArrayList<>();
        double score;

        for (Move move : getValidMoves()) {
            score = 0.0;

            if (captures(move)) {
                score = Double.POSITIVE_INFINITY;
            } else {
                for (int i : board.getNeighbors(move.getField())) {
                    if(board.getColor(i) == move.getColor()) {
                        score += 1.0;
                    } else if (board.getColor(i) != move.getColor()) {
                        score += 2.0;
                    }
                }
            }

            scores.add(new Pair(score, move));
        }

        Collections.sort(scores);

        List<Move> moves = new ArrayList<>();

        for (Pair p : scores) {
            moves.add(p.moveValue);
        }

        return moves;
    }

    /**
     * Executes the specified move if it is valid, updates the game state,
     * and checks if the game is over after the move.
     *
     * @param move the move to be executed
     * @throws IllegalMoveException if the move is invalid
     */
    //@requires isValidMove(move);
    public void doMove(Move move) {
        if (isValidMove(move)) {
            board.setField(move.getField(), move.getColor());

        } else {
            throw new IllegalMoveException(board.toString(), move.toString());
        }
    }

    public Game deepCopy() {
        Game newGame = new Game(p1, p2);
        newGame.board = board.deepCopy();
        return newGame;
    }

    private static class Pair implements Comparable<Pair> {
        double doubleValue;
        Move moveValue;

        Pair(double d, Move m) {
            this.doubleValue = d;
            this.moveValue = m;
        }

        @Override
        public int compareTo(Pair other) {
            return Double.compare(this.doubleValue, other.doubleValue);
        }
    }
}
