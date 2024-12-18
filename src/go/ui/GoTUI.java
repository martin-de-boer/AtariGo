package go.ui;

import go.ai.strategy.NaiveStrategy;
import go.ai.strategy.SmartStrategy;
import go.model.game.Game;
import go.model.game.Move;
import go.model.interfaces.Color;
import go.model.interfaces.Player;
import go.model.player.ComputerPlayer;
import go.model.player.HumanPlayer;
import java.util.Scanner;

/**
 * This class implements the textual user interface of the Go game.
 * To play the game, run the main method of this class.
 */
public class GoTUI {
    private Scanner sc = new Scanner(System.in);
    private boolean exitTUI;
    private Game game;

    /**
     * Starting point of the game.
     * This method initializes the textual user interface for the Go game
     * and starts the game loop.
     */
    public static void main(String[] args) {
        GoTUI tui = new GoTUI();
        while (true) {
            tui.runGame();
        }
    }

    /**
     * Default constructor for the GoTUI class.
     */
    public GoTUI() {
        sc = new Scanner(System.in);
    }

    /**
     * Returns if the entire TUI
     */
    public boolean shouldExit() {
        return false;
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

        Player p1;
        Player p2;

        System.out.print("P1 = c/h: ");
        String p1Choice = sc.nextLine();
        if (p1Choice.equalsIgnoreCase("c")) {
            System.out.print("ai = n/s/...: ");
            String p1AIChoice = sc.nextLine();
            if (p1AIChoice.equalsIgnoreCase("n")) {
                p1 = new ComputerPlayer(new NaiveStrategy(), Color.BLACK);
            } else {
                p1 = new ComputerPlayer(new SmartStrategy(), Color.BLACK);
            }
        } else {
            System.out.print("Name of Player 1: ");
            String p1name = sc.nextLine();
            p1 = new HumanPlayer(p1name, Color.BLACK);
        }

        System.out.print("P2 = c/h: ");
        String p2Choice = sc.nextLine();
        if (p2Choice.equalsIgnoreCase("c")) {
            System.out.print("ai = n/s/...: ");
            String p2AIChoice = sc.nextLine();
            if (p2AIChoice.equalsIgnoreCase("n")) {
                p2 = new ComputerPlayer(new NaiveStrategy(), Color.WHITE);
            } else {
                p2 = new ComputerPlayer(new SmartStrategy(), Color.WHITE);
            }
        } else {
            System.out.print("Name of Player 2: ");
            String p1name = sc.nextLine();
            p2 = new HumanPlayer(p1name, Color.WHITE);
        }

        this.game = new Game(p1, p2);
        System.out.println(this.game.getBoard());

    }


    public void runGame() {
        setup();
        while (!game.doMove(game.getTurn().determineMove(game))) {
            this.run();
        }
    }
    /**
     *
     */
    public void run() {

    }
}
