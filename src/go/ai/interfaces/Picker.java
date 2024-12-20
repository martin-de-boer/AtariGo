package go.ai.interfaces;

import go.model.game.Game;
import go.model.game.Move;

public interface Picker {
    Move pickMove(Game game, Strategy strat);

    @Override
    String toString();
}
