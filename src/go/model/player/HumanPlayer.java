package go.model.player;

import go.model.game.Game;
import go.model.game.Move;
import go.model.interfaces.Color;
import go.model.interfaces.Player;

import static go.model.game.Board.DIM;
import static go.ui.GoTUI.sc;

/**
 * This class is used to define a human player. It will use the System input to determine moves.
 */
public class HumanPlayer implements Player {
    private String name;
    private final Color color;

    public HumanPlayer(String name, Color color) {
        this.name = name;
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
     *
     * @return the name of the player
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Determines the next move. Uses the system input to determine this next move.
     * @return the next move according to the player.
     */
    @Override
    public Move determineMove(Game game) {

        boolean correctMove = false;
        int x = -1;
        int y = -1;
        while (!correctMove) {
            System.out.print(getName() + ", enter move: ");
            String in = sc.nextLine();
            String[] split = in.split("\\s+");

            try {
                x = Integer.parseInt(split[0]);
                y = Integer.parseInt(split[1]);
            } catch (Exception e) {
                System.out.println("The arguments provided are not valid coordinates or not enough arguments!");
                continue;
            }
            if (!game.isValidMove(new Move(x, y, this.color))) {
                System.out.println("This is not a valid move!");
                continue;
            }

            // Valid move
            correctMove = true;
        }
        return new Move(x, y, this.color);
    }
}
