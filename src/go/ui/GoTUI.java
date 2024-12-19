package go.ui;

import go.ai.interfaces.Strategy;
import go.ai.strategy.NaiveStrategy;
import go.ai.strategy.SimpleStrategy;
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
    public static Scanner sc = new Scanner(System.in);
    private static Game game;
    private Move lastMove;
    private static int p1Score = 0;
    private static int p2Score = 0;


    /**
     * Starting point of the game.
     * This method initializes the textual user interface for the Go game
     * and starts the game loop.
     */
    public static void main(String[] args) {
        sc = new Scanner(System.in);
        GoTUI tui = new GoTUI();
        boolean exit = false;
        boolean newPlayers = true;

        while (!exit) {
            setup(newPlayers);

            if(game.getP1() instanceof ComputerPlayer && game.getP2() instanceof ComputerPlayer) {
                simulate(tui);
            } else {
                tui.runGame();
            }


            exit = tui.isExit();
            if(!exit) {
                newPlayers = tui.getNewPlayers();
            }
        }
    }

    private static void simulate(GoTUI tui) {
        Boolean exit = false;
        String str = null;

        while (!exit) {
            System.out.print("How many games? ");
            str = sc.nextLine();

            if(str.matches("\\d+")) {
                exit = true;
            } else {
                System.out.print("try again\n");
            }
        }
        for (int i = 0; i < Integer.parseInt(str); i++) {
            tui.simulateGame();
            setup(false);
        }
        System.out.println("P1: " + p1Score + " P2: " + p2Score);
    }

    private Boolean isExit() {
        Boolean exit = false;
        while (!exit) {
            System.out.print("Another game? (y/n) ");
            String level = sc.nextLine();

            switch (level) {
                case "y":
                    return false;
                case "n":
                    return true;
                default:
                    System.out.print("try again\n");
            }
        }
        return null;
    }

    private Boolean getNewPlayers() {
        Boolean exit = false;
        while (!exit) {
            System.out.print("Same players? (y/n) ");
            String level = sc.nextLine();

            switch (level) {
                case "y":
                    return false;
                case "n":
                    return true;
                default:
                    System.out.print("try again\n");
            }
        }
        return null;
    }

    private static Strategy getStrategyLevel() {
        while (true) {
            System.out.print("naive, simple or smart? ");
            String level = sc.nextLine().toLowerCase();

            switch (level) {
                case "naive":
                    return new NaiveStrategy();
                case "simple":
                    return new SimpleStrategy();
                case "smart":
                    return new SmartStrategy();
                default:
                    System.out.print("try again\n");
            }
        }
    }

    /**
     * Setup function of the TUI.
     * Creates a new game and two players according to the input specified by the user.
     */
    public static void setup(boolean newPlayers) {
        Player p1 = null;
        Player p2 = null;
        if (newPlayers) {
            p1 = null;
            p2 = null;

            p1Score = 0;
            p2Score = 0;

            Boolean exit = false;
            String line;

            while (!exit) {
                System.out.print("P1: computer or human? ");
                line = sc.nextLine();
                switch (line) {
                    case "computer" :
                        exit = true;
                        p1 = new ComputerPlayer(getStrategyLevel(), Color.BLACK);
                        break;

                    case "human" :
                        exit = true;
                        System.out.print("Name of Player 1: ");
                        String name = sc.nextLine();
                        p1 = new HumanPlayer(name, Color.BLACK);
                        break;

                    default :
                        System.out.print("try again\n");
                }
            }

            exit = false;

            while (!exit) {
                System.out.print("P2: computer or human? ");
                line = sc.nextLine();
                switch (line) {
                    case "computer" :
                        exit = true;
                        p2 = new ComputerPlayer(getStrategyLevel(), Color.WHITE);
                        break;

                    case "human" :
                        exit = true;
                        System.out.print("Name of Player 2: ");
                        String name = sc.nextLine();
                        p2 = new HumanPlayer(name, Color.WHITE);
                        break;

                    default :
                        System.out.print("try again\n");
                }
            }
        }
        else {
            p1 = game.getP1();
            p2 = game.getP2();
        }

        game = new Game(p1, p2);
    }

    public void simulateGame() {
        while (winCondition() == false) { }

        Player winner = lastMove.getColor() == Color.BLACK ? game.getP1() : game.getP2();
        if(winner == game.getP1()) {
            p1Score++;
        } else {
            p2Score++;
        }
    }

    public void runGame() {
        System.out.println(game.getBoard());
        while (winCondition() == false) {
            System.out.println(game.getBoard().toString());
            System.out.println("Last move: " + lastMove);
        }

        System.out.println(game.getBoard().toString());
        System.out.println("Last move: " + lastMove);
        System.out.println("Game over!");
        Player winner = lastMove.getColor() == Color.BLACK ? game.getP1() : game.getP2();
        System.out.println("Winner: " +  winner.getName());
        if(winner == game.getP1()) {
            p1Score++;
        } else {
            p2Score++;
        }
        System.out.println("P1: " + p1Score + " P2: " + p2Score);
    }

    private boolean winCondition() {
        if(game.getValidMoves().isEmpty()) {
            return true;
        }
        lastMove = game.getTurn().determineMove(game);
        return game.doMove(lastMove);
    }
}
