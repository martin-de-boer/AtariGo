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
    private Color next;


    /**
     * Creates a game with an empty board.
     */
    public Game(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.board = new Board();
        this.next = Color.BLACK;
    }

    /**
     * Returns the board attached to the game.
     * @return the board of the game
     */
    public Board getBoard() {
        return this.board;
    }


    /**
     * Returns the player whose turn it is.
     * @return the player whose turn it is
     */
    public Player getTurn() {
        return this.next == Color.BLACK ? p1 : p2;
    }

    /**
     * Returns whether the move specified is legal or not
     * @return true if the move is legal, false otherwise
     */
    //@ pure;
    public boolean isValidMove(Move move) {
        Board newBoard = board.deepCopy();
        newBoard.setField(move.getField(), move.getColor());
        if (newBoard.isGroupSurrounded(newBoard.getGroup(move.getField()))) {
            return false;
        }
        return move.getColor() == this.next && board.isValidField(move.getField()) && board.isEmpty(move.getField());
    }

    /**
     * Method to get all legal moves in the current position.
     * @return all legal moves in the current position.
     */
    public List<Move> getValidMoves() {
        List<Move> moves = new ArrayList<>();

        for (int i = 0; i < Board.DIM * Board.DIM; i++) {
            Move move = new Move(i, next);
            if (isValidMove(move)) {
                moves.add(move);
            }
        }
        return moves;
    }

    /**
     * Executes the specified move if it is valid, updates the game state,
     * and checks if the game is over after the move.
     *
     * @param move the move to be executed
     * @return true if the game is over after the move, false otherwise
     * @throws IllegalMoveException if the move is invalid
     */
    //@requires isValidMove(move);
    public boolean doMove(Move move) {
        if (isValidMove(move)) {
            board.setField(move.getField(), move.getColor());
            this.next = this.next.other();

        } else {
            if (!isValidMove(move)) {
                throw new IllegalMoveException(board, move);
            }
            board.setField(move.getField(), move.getColor());
        }
        if (isGameOver(move)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if the game is over based on the last move.
     *
     * @param move the move to check for game-over conditions
     * @return true if all groups in the neighborhood of the move are surrounded, false otherwise
     */
    //@requires board.isValidField(move.getField());
    //@pure
    public Boolean isGameOver(Move move) {
        for (int i : board.getNeighbors(move.getField())) {
            if (board.isGroupSurrounded(board.getGroup(i)) && board.getField(i) != Color.EMPTY) {
                return true;
            }
        }
        return false;
    }
}
