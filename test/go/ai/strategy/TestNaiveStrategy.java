package go.ai.strategy;

import go.model.game.Board;
import go.model.game.Game;
import go.model.game.Move;
import go.model.interfaces.Color;
import go.model.interfaces.Player;

import go.model.player.ComputerPlayer;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;


/**
 * The `NaiveStrategyTest` class is for testing the `determineMove` method in the `NaiveStrategy` class.
 * The `determineMove` method provides with a Move option for a `Game`, the movement option is selected randomly from the list of valid moves.
 */
class TestNaiveStrategy {
    private Game game;
    private NaiveStrategy strategy;

    @BeforeEach
    void setup() {
        Player p1 = new ComputerPlayer(new NaiveStrategy(), Color.BLACK);
        Player p2 = new ComputerPlayer(new NaiveStrategy(), Color.WHITE);
        this.game = new Game(p1, p2);
        this.strategy = new NaiveStrategy();
    }

    /**
     * Tests that the `determineMove` method does not return null when there are valid moves in the game.
     */
    @Test
    void testDetermineValidMove() {
        game.doMove(new Move(2, Color.BLACK));
        game.doMove(new Move(20, Color.WHITE));
        Move move = strategy.determineMove(game);
        assertNotNull(move);

        // Check if the move is in the valid moves
        boolean inValidMoves = false;
        for (Move m : game.getValidMoves()) {
            if (m.isEqual(move)) {
                inValidMoves = true;
            }
        }
        assertTrue(inValidMoves);
    }


    /**
     * Tests that the `determineMove` method returns null when there are no valid moves in the game.
     */
    @Test
    void testDetermineMoveNullReturn() {
        for (int i = 0; i < Board.DIM * Board.DIM; i++) {
            game.getBoard().setField(i, Color.WHITE);
        }

        assertNull(strategy.determineMove(game));
    }
}