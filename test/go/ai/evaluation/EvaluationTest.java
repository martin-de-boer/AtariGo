package go.ai.evaluation;

import go.ai.strategy.NaiveStrategy;
import go.model.game.Game;
import go.model.game.Move;
import go.model.interfaces.Color;
import go.model.interfaces.Player;
import go.model.player.ComputerPlayer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.concurrent.*;

public class EvaluationTest {
    private Game game;
    private Evaluation eval;

    @BeforeEach
    public void setUp() {
        ComputerPlayer p1 = new ComputerPlayer(new NaiveStrategy(), Color.BLACK);
        ComputerPlayer p2 = new ComputerPlayer(new NaiveStrategy(), Color.WHITE);
        game = new Game(p1, p2);
        eval = new Evaluation(game, 5);
    }

    @Test
    public void testPotentialCaptures() {
        System.out.println("potentialCapture");
        System.out.println(game.getValidMoves());
        game.doMove(new Move(18, Color.BLACK));
        game.doMove(new Move(9, Color.WHITE));
        game.doMove(new Move(8, Color.BLACK));
        game.doMove(new Move(30, Color.WHITE));
        game.doMove(new Move(2, Color.BLACK));

        double score = eval.countPotentialCaptures(game, Color.BLACK);

        System.out.println(score);
        System.out.println(game.getBoard().toString());
    }

    @Test
    public void testEvaluateLiberties() {
        System.out.println("evaluateLiberties");
        game.doMove(new Move(18, Color.BLACK));
        game.doMove(new Move(9, Color.WHITE));
        game.doMove(new Move(8, Color.BLACK));
        game.doMove(new Move(30, Color.WHITE));
        game.doMove(new Move(2, Color.BLACK));

        double score = eval.evaluateLiberties(game, Color.WHITE);

        System.out.println(score);
        System.out.println(game.getBoard().toString());
    }

    @Test
    public void testEvaluatePosition() {
        System.out.println("evaluatePosition");
        game.doMove(new Move(18, Color.BLACK));
        game.doMove(new Move(9, Color.WHITE));
        game.doMove(new Move(8, Color.BLACK));
        game.doMove(new Move(30, Color.WHITE));
        game.doMove(new Move(2, Color.BLACK));

        double score = eval.evaluatePosition(game);

        System.out.println(score);
        System.out.println(game.getBoard().toString());
    }

    @Test
    public void testEvaluate() {
        System.out.println("evaluate");

        game.doMove(new Move(18, Color.BLACK));
        game.doMove(new Move(9, Color.WHITE));
        game.doMove(new Move(8, Color.BLACK));
        game.doMove(new Move(30, Color.WHITE));

        double score = eval.evaluate();
        System.out.println(score);

        System.out.println(game.getBoard().toString());
    }
}
