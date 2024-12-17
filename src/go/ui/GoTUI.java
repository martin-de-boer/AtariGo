package go.ui;

import go.model.game.Game;
import go.model.game.Move;
import go.model.interfaces.Color;
import go.model.player.HumanPlayer;

/**
 * This class implements the textual user interface of the Go game.
 * To play the game, run the main method of this class.
 */
public class GoTUI {
    /**
     * Starting point of the game.
     * This method initializes the textual user interface for the Go game
     * and starts the game loop.
     */
    public static void main(String[] args) {
        GoTUI tui = new GoTUI();
        tui.setup();
    }

    /**
     * Default constructor for the GoTUI class.
     */
    public GoTUI() {
        // Empty because all settings are specified in the setup function.
    }

    /**
     * Setup function of the TUI.
     * Creates a new game and two players according to the input specified by the user.
     */
    public void setup() {
        Game game = new Game(new HumanPlayer("p1", Color.BLACK), new HumanPlayer("p2", Color.WHITE));
        game.doMove(new Move(2, Color.BLACK));
        game.doMove(new Move(20, Color.WHITE));
        System.out.println(game.getBoard().toString());
    }
}
