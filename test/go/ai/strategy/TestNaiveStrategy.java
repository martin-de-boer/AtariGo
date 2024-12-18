package go.ai.strategy;

import go.model.game.Game;
import go.model.game.Move;
import go.model.interfaces.Color;
import go.model.interfaces.Player;

import go.model.player.ComputerPlayer;
import go.model.player.HumanPlayer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;


/**
 * The `NaiveStrategyTest` class is for testing the `determineMove` method in the `NaiveStrategy` class.
 * The `determineMove` method provides with a Move option for a `Game`, the movement option is selected randomly from the list of valid moves.
 */
public class TestNaiveStrategy {
    private Game game;

    @BeforeEach
    public void setup() {
        Player p1 = new ComputerPlayer(new NaiveStrategy(), Color.BLACK);
        Player p2 = new ComputerPlayer(new NaiveStrategy(), Color.WHITE);
        this.game = new Game(p1, p2);
    }

    /**
     * Tests that the `determineMove` method does not return null when there are valid moves in the game.
     */
    @Test
    public void testDetermineValidMove() {
        game.doMove(new Move(2, Color.BLACK));
        game.doMove(new Move(20, Color.WHITE));
        assertNotNull(strategy.determineMove(game));
    }

    /**
     * Tests that the `determineMove` method returns null when there are no valid moves in the game.
     */
    @Test
    public void testDetermineMoveNullReturn() {
        Game game = mock(Game.class);
        when(game.getValidMoves()).thenReturn(new ArrayList<>());

        NaiveStrategy strategy = new NaiveStrategy();
        assertNull(strategy.determineMove(game));
    }
}