package go.ai.strategy;

import go.model.game.Game;
import go.model.game.Move;
import go.model.game.Color;
import go.model.game.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * The `NaiveStrategyTest` class is for testing the `determineMove` method in the `NaiveStrategy` class.
 * The `determineMove` method provides with a Move option for a `Game`, the movement option is selected randomly from the list of valid moves.
 */
public class TestNaiveStrategy {

    /**
     * Tests that the `determineMove` method does not return null when there are valid moves in the game.
     */
    @Test
    public void testDetermineMoveNoNullReturn() {
        Game game = mock(Game.class);
        Player player = new Player("Test Player", Color.BLACK);
        List<Move> validMoves = Arrays.asList(new Move(0, Color.BLACK), new Move(1, Color.BLACK),
                                              new Move(2, Color.WHITE));
        when(game.getValidMoves()).thenReturn(validMoves);

        NaiveStrategy strategy = new NaiveStrategy();
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