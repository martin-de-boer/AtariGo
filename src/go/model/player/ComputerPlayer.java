package go.model.player;

import go.ai.interfaces.Picker;
import go.ai.interfaces.Strategy;
import go.model.game.Game;
import go.model.game.Move;
import go.model.interfaces.Color;
import go.model.interfaces.Player;

/**
 * This class is used to define an AI player with a certain strategy.
 */
public class ComputerPlayer implements Player {
    private final Strategy strategy;
    private final Picker picker;
    private final Color color;

    public ComputerPlayer(Strategy strategy, Picker picker, Color color) {
        this.strategy = strategy;
        this.picker = picker;
        this.color = color;
    }

    /**
     * Retrieves the color associated with the player.
     *
     * @return the color of the player
     */
    //@pure
    public Color getColor() {
        return color;
    }

    /**
     * Returns the name of the player.
     * Format: [strategy]-[color]
     * @return the name of the player
     */
    @Override
    public String getName() {
        return strategy.toString() + "-" + picker.toString() + "-" + color.toString();
    }

    /**
     * Determines the next move. Uses the strategy to determine this.
     * @return the next move according to the strategy.
     */
    @Override
    public Move determineMove(Game game) {
        return picker.pickMove(game, strategy);
    }
}
