package go.ai.strategy;

import go.ai.interfaces.Strategy;
import go.model.game.Board;
import go.model.game.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SimpleStrategyTest {
    SimpleStrategy strategy;
    Board board;

    @BeforeEach
    public void setUp() {
        board = new Board();

        board.setField(8, board.getTurn());
        board.setField(15, board.getTurn());
        board.setField(10, board.getTurn());
        board.setField(18, board.getTurn());
        board.setField(2, board.getTurn());

        strategy = new SimpleStrategy();
    }

    @Test
    public void testFindWinningMove () {
        System.out.println("findWinningMove");

        Move move = strategy.findWinningMove(board);

        System.out.println(move);
        System.out.println(board.toString());
    }
}
