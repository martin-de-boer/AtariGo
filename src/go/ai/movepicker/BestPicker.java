package go.ai.movepicker;

import go.ai.interfaces.Picker;
import go.ai.interfaces.Strategy;
import go.model.game.Game;
import go.model.game.Move;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Map;

public class BestPicker implements Picker {

    @Override
    public Move pickMove(Game game, Strategy strat) {
        Map.Entry<Move, Double> maxEntry = null;
        Map<Move, Double> scores = strat.determineMoveScores(game);
        for (Map.Entry<Move, Double> entry : scores.entrySet()) {
            if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
                maxEntry = entry;
            }
        }
        return (maxEntry == null) ? null : maxEntry.getKey();
    }

    @Override
    public String toString() {
        return "BestPicker";
    }
}
