package go.ai.strategy;

import go.ai.evaluation.bestMoveEvaluation;
import go.ai.interfaces.Strategy;
import go.model.game.Game;
import go.model.game.Move;
import go.model.interfaces.Color;
import go.model.interfaces.Player;
import go.model.player.ComputerPlayer;
import java.util.concurrent.*;

public class evaluationStrategy implements Strategy {

    @Override
    public Move determineMove(Game game) {
        Player p1 = new ComputerPlayer(new NaiveStrategy(), Color.BLACK);
        Player p2 = new ComputerPlayer(new NaiveStrategy(), Color.WHITE);
        Game newGame = new Game(p1, p2);


        ExecutorService executor = Executors.newSingleThreadExecutor();

        bestMoveEvaluation eval = new bestMoveEvaluation(newGame, 1);
        Future future = executor.submit(eval);
        try {
            future.get(10, TimeUnit.SECONDS); // Set the time out of search task
            executor.shutdown();
        } catch (Exception e) {
            executor.shutdown();
        }

        executor.shutdownNow();

        return eval.evalMove;
    }

    @Override
    public String toString() {
        return "Evaluation";
    }
}
