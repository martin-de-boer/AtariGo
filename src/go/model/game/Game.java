package go.model.game;

import go.model.interfaces.Color;
import go.model.interfaces.Player;
import go.util.exception.IllegalMoveException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class runs a game of Go.
 */
public class Game {
    private Player p1;
    private Player p2;
    private Board board;
    private List<Move> moves;

    /**
     * Creates a game with an empty board.
     */
    public Game(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.board = new Board();
        this.moves = new ArrayList<>();
    }

    /**
     * Returns the board attached to the game.
     * @return the board of the game
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * Returns a list of all the past moves of this game.
     * @return all past moves of this game.
     */
    public List<Move> getMoves() {
        return this.moves;
    }

    /**
     * Returns the player whose turn it is.
     * @return the player whose turn it is
     */
    public Player getTurn() {
        return null;
    }

    /**
     * Returns the winner of this game, or null if it has no winner.
     * @return the winner of the game, null if it has no winner
     */
    public Player getWinner() {
        return null;
    }

    /**
     * Returns whether the move specified is legal or not
     * @return true if the move is legal, false otherwise
     */
    //@ pure;
    public boolean isValidMove(Move move) {
        return board.getSquare(move.getTile()) == Color.EMPTY;
    }

    /**
     * Method to get all legal moves in the current position.
     * @return all legal moves in the current position.
     */
    public List<Move> getValidMoves() {
        return null;
    }

    /**
     * Does the specified move after checking if it's valid.
     * @param move the move to be done
     */
    //@ requires isValidMove(move);
    public void doMove(Move move) {
        if (!isValidMove(move)) {
            throw new IllegalMoveException(board, move);
        }
        board.setSquare(move.getTile(), move.getColor());
    }

    /**
     * This method resets the board, clears the move history and resets the players.
     * @param p1 the 1st player of the new game
     * @param p2 the 2nd player of the new game
     */
    public void reset(Player p1, Player p2) {
        board.clear();
        moves.clear();
        this.p1 = p1;
        this.p2 = p2;
    }
}
