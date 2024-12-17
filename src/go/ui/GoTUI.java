package go.ui;

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
     * Creates an empty board and two players according to the input specified by the user.
     */
    public void setup() {
        // TODO document why this method is empty
    }
}
