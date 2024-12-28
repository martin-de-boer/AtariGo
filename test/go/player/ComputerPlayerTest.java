package go.player;

import go.ai.strategy.SimpleStrategy;
import go.model.game.Game;
import go.model.game.Move;
import go.model.interfaces.Color;
import go.model.player.ComputerPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ComputerPlayerTest {
    Game game;

    @BeforeEach
    public void setUp() {
        ComputerPlayer p1 = new ComputerPlayer(new SimpleStrategy(), Color.BLACK);
        ComputerPlayer p2 = new ComputerPlayer(new SimpleStrategy(), Color.WHITE);

        game = new Game(p1, p2);

    }

    @Test
    public void testComputerPlayer() {
        game.doMove(new Move(0, Color.BLACK));
        game.doMove(new Move(9, Color.WHITE));
        game.doMove(new Move(22, Color.BLACK));

        System.out.println(game.getTurn().determineMove(game));
        game.doMove(game.getTurn().determineMove(game));

        System.out.println(game.isGameOver());
        System.out.println(game.getBoard().toString());
    }
}
