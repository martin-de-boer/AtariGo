package go.model.game;

import go.ai.strategy.NaiveStrategy;
import go.model.interfaces.Color;
import go.model.player.ComputerPlayer;
import go.util.exception.IllegalMoveException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    private Game game;
    private ComputerPlayer p1;
    private ComputerPlayer p2;

    @BeforeEach
    public void setUp() {
        p1 = new ComputerPlayer(new NaiveStrategy(), Color.BLACK);
        p2 = new ComputerPlayer(new NaiveStrategy(), Color.WHITE);
        game = new Game(p1,p2);
    }

    @Test
    public void testSetup() {
        assertEquals(game.getTurn(), p1);
    }

    @Test
    public void testIsValidMove() {
        Move validMove = new Move(5, Color.BLACK);
        assertTrue(game.isValidMove(validMove));

        Move wrongIndexMove1 = new Move(-1, Color.WHITE);
        assertFalse(game.isValidMove(wrongIndexMove1));

        Move wrongIndexMove2 = new Move(100, Color.WHITE);
        assertFalse(game.isValidMove(wrongIndexMove2));

        Move wrongColorMove = new Move(5, Color.WHITE);
        assertFalse(game.isValidMove(wrongColorMove));

        Move invalidFieldMove = new Move(-1, Color.BLACK);
        assertFalse(game.isValidMove(invalidFieldMove));

        game.getBoard().setField(5, Color.BLACK);
        Move occupiedFieldMove = new Move(5, Color.BLACK);
        assertFalse(game.isValidMove(occupiedFieldMove));
    }

    @Test
    public void testGetValidMoves() {
        int initialValidMoves = game.getValidMoves().size();
        assertEquals(Board.DIM * Board.DIM, initialValidMoves);

        Move move1 = new Move(5, Color.BLACK);
        game.doMove(move1);

        assertFalse(game.getValidMoves().contains(move1));
        assertEquals(initialValidMoves - 1, game.getValidMoves().size());

        Move move2 = new Move(10, Color.WHITE);
        game.doMove(move2);

        assertFalse(game.getValidMoves().contains(move2));
        assertEquals(initialValidMoves - 2, game.getValidMoves().size());
    }

    @Test
    public void testIsGameOver() {
        Move move1 = new Move(3, Color.BLACK);
        game.doMove(move1);
        assertFalse(game.isGameOver(move1));

        Move move2 = new Move(10, Color.WHITE);
        game.doMove(move2);
        assertFalse(game.isGameOver(move2));

        game.getBoard().setField(9, Color.BLACK);
        game.getBoard().setField(11, Color.BLACK);

        Move finishingMove = new Move(17, Color.BLACK);
        game.doMove(finishingMove);


        assertTrue(game.isGameOver(finishingMove));
    }

    @Test
    public void testDoMove() {
        Move move1 = new Move(5, Color.BLACK);
        assertDoesNotThrow(() -> game.doMove(move1));
        assertEquals(Color.BLACK, game.getBoard().getField(5));

        assertEquals(Color.WHITE, game.getTurn().getColor());

        Move invalidMove1 = new Move(5, Color.WHITE);
        assertThrows(IllegalMoveException.class, () -> game.doMove(invalidMove1));

        Move move2 = new Move(10, Color.WHITE);
        assertDoesNotThrow(() -> game.doMove(move2));
        assertEquals(Color.WHITE, game.getBoard().getField(10));

        game.doMove(new Move(11, Color.BLACK));

        game.getBoard().setField(13, Color.BLACK);
        game.getBoard().setField(19, Color.BLACK);

        Move invalidMove2 = new Move(12, Color.WHITE);

        assertThrows(IllegalMoveException.class, () -> game.doMove(invalidMove2));

        Move invalidMove3 = new Move(6, Color.WHITE);

        assertThrows(IllegalMoveException.class, () -> game.doMove(invalidMove3));
    }
}
