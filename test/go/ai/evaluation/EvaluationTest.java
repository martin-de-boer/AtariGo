package go.ai.evaluation;

import go.ai.interfaces.Picker;
import go.ai.movepicker.BestPicker;
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
    private ComputerPlayer p1;
    private ComputerPlayer p2;
    private BestPicker picker1;
    private BestPicker picker2;
    private Evaluation eval;

    @BeforeEach
    public void setUp() {
        picker1 = new BestPicker();
        picker2 = new BestPicker();
        p1 = new ComputerPlayer(new NaiveStrategy(), picker1, Color.BLACK);
        p2 = new ComputerPlayer(new NaiveStrategy(), picker2, Color.WHITE);
        game = new Game(p1,p2);
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

    public static void main(String[] args) throws Exception {
        Picker picker1 = new BestPicker();
        Picker picker2 = new BestPicker();
        Player p1 = new ComputerPlayer(new NaiveStrategy(), picker1, Color.BLACK);
        Player p2 = new ComputerPlayer(new NaiveStrategy(), picker2, Color.WHITE);
        Game game = new Game(p1,p2);

        game.doMove(new Move(18, Color.BLACK));

        System.out.println(game.getBoard().toString());

        ExecutorService executor = Executors.newSingleThreadExecutor();

        Evaluation eval = new Evaluation(game, 1);
        Future future = executor.submit(eval);
        try {
            future.get(10, TimeUnit.SECONDS); // Set the time out of search task
            executor.shutdown();
        } catch (TimeoutException e) {
            executor.shutdown();
        }

        executor.shutdownNow();

        System.out.println(eval.evalScore);
    }
}
