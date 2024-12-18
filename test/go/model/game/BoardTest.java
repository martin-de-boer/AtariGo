package go.model.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import go.model.interfaces.Color;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class BoardTest {
    private Board board;

    @BeforeEach
    public void setUp() {
        board = new Board();
    }

    @Test
    public void testIndex() {
        int index = 0;
        for (int i = 0; i < Board.DIM; i++) {
            for (int j = 0; j < Board.DIM; j++) {
                assertEquals(index, board.indexOf(i, j));
                index += 1;
            }
        }
        assertEquals(0, board.indexOf(0, 0));
        assertEquals(1, board.indexOf(0, 1));
        assertEquals(7, board.indexOf(1, 0));
    }

    @Test
    public void testIsFieldIndex() {
        assertFalse(board.isValidField(-1));
        assertTrue(board.isValidField(0));
        assertTrue(board.isValidField(Board.DIM * Board.DIM - 1));
        assertFalse(board.isValidField(Board.DIM * Board.DIM));
    }

    @Test
    public void testIsFieldRowCol() {
        assertFalse(board.isValidField(-1, 0));
        assertFalse(board.isValidField(0, -1));
        assertTrue(board.isValidField(0, 0));
        assertTrue(board.isValidField(2, 2));
        assertFalse(board.isValidField(7, 3));
        assertFalse(board.isValidField(3, 7));
    }

    @Test
    public void testSetAndGetFieldIndex() {
        board.setField(0, Color.BLACK);
        assertEquals(Color.BLACK, board.getField(0));
        assertEquals(Color.EMPTY, board.getField(1));
    }

    @Test
    public void testSetAndGetFieldRowCol() {
        board.setField(board.indexOf(0, 0), Color.BLACK);
        assertEquals(Color.BLACK, board.getField(board.indexOf(0, 0)));
        assertEquals(Color.EMPTY, board.getField(board.indexOf(0, 1)));
        assertEquals(Color.EMPTY, board.getField(board.indexOf(1, 0)));
        assertEquals(Color.EMPTY, board.getField(board.indexOf(1, 1)));
    }

    @Test
    public void testSetup() {
        for (int i = 0; i < Board.DIM * Board.DIM; i++) {
            assertEquals(Color.EMPTY, board.getField(i));
        }
    }

    @Test
    public void testDeepCopy() {
        board.setField(0, Color.BLACK);
        board.setField(Board.DIM * Board.DIM - 1, Color.WHITE);
        Board deepCopyBoard = board.deepCopy();

        // First test if all the fields are the same
        for (int i = 0; i < Board.DIM * Board.DIM; i++) {
            assertEquals(board.getField(i), deepCopyBoard.getField(i));
        }

        // Check if a field in the deepcopied board the original remains the same
        deepCopyBoard.setField(0, Color.WHITE);

        assertEquals(Color.BLACK, board.getField(0));
        assertEquals(Color.WHITE, deepCopyBoard.getField(0));
    }

    @Test
    public void testisEmptyIndex() {
        board.setField(0, Color.BLACK);
        assertFalse(board.isEmpty(0));
        assertTrue(board.isEmpty(1));
    }

    @Test
    public void testisEmptyRowCol() {
        board.setField(board.indexOf(0, 0), Color.BLACK);
        assertFalse(board.isEmpty(board.indexOf(0, 0)));
        assertTrue(board.isEmpty(board.indexOf(0, 1)));
        assertTrue(board.isEmpty(board.indexOf(1, 0)));
    }

    @Test
    public void testIOf() {
        assertEquals(0, board.iOf(0));
        assertEquals(0, board.iOf(1));
        assertEquals(1, board.iOf(9));
        assertEquals(3, board.iOf(23));
    }

    @Test
    public void testJOf() {
        assertEquals(0, board.jOf(0));
        assertEquals(1, board.jOf(1));
        assertEquals(2, board.jOf(9));
        assertEquals(6, board.jOf(27));
    }

    @Test
    public void testGetNeighbors() {
        assertTrue(board.getNeighbors(0).contains(1));
        assertTrue(board.getNeighbors(0).contains(7));
        assertEquals(2, board.getNeighbors(0).size());

        assertTrue(board.getNeighbors(10).contains(9));
        assertTrue(board.getNeighbors(10).contains(11));
        assertTrue(board.getNeighbors(10).contains(3));
        assertTrue(board.getNeighbors(10).contains(17));
        assertEquals(4, board.getNeighbors(10).size());
    }

    @Test
    public void testHasEmptyNeighbors() {
        assertTrue(board.hasEmptyNeighbors(0));
        assertTrue(board.hasEmptyNeighbors(10));
        board.setField(1, Color.BLACK);
        board.setField(7, Color.WHITE);
        assertFalse(board.hasEmptyNeighbors(0));

        board.setField(9, Color.BLACK);
        board.setField(11, Color.WHITE);
        board.setField(3, Color.BLACK);
        board.setField(17, Color.WHITE);
        assertFalse(board.hasEmptyNeighbors(10));
    }

    @Test
    public void testGetGroup() {
        board.setField(0, Color.BLACK);
        assertTrue(board.getGroup(0).contains(0));
        assertEquals(1, board.getGroup(0).size());

        board.setField(1, Color.BLACK);
        board.setField(7, Color.BLACK);
        board.setField(15, Color.BLACK);
        board.setField(8, Color.WHITE);
        board.setField(3, Color.BLACK);
        board.setField(9, Color.WHITE);

        assertTrue(board.getGroup(0).contains(0));
        assertTrue(board.getGroup(0).contains(1));
        assertTrue(board.getGroup(0).contains(7));

        assertEquals(3, board.getGroup(0).size());

        assertTrue(board.getGroup(8).contains(9));
        assertEquals(2, board.getGroup(8).size());
    }

    @Test
    public void testIsGroupSurrounded() {

        board.setField(0, Color.BLACK);
        board.setField(1, Color.BLACK);
        board.setField(7, Color.BLACK);
        board.setField(15, Color.BLACK);

        board.setField(8, Color.WHITE);

        assertFalse(board.isGroupSurrounded(board.getGroup(0)));

        assertFalse(board.isGroupSurrounded(board.getGroup(8)));

        board.setField(9, Color.BLACK);

        assertFalse(board.isGroupSurrounded(board.getGroup(9)));

        board.setField(2, Color.WHITE);

        board.setField(14, Color.WHITE);

        assertTrue(board.isGroupSurrounded(board.getGroup(0)));
    }
}
