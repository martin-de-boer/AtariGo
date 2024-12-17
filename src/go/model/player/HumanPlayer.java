package go.model.player;

import go.model.game.Game;
import go.model.game.Move;
import go.model.interfaces.Color;
import go.model.interfaces.Player;
import java.util.Scanner;

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
        Boolean valid = false;
        Move move = null;
        System.out.println( name + " Make a move: " );
        while (!valid) {
            Scanner input = new Scanner(System.in);

            int i = input.nextInt();
            move = new Move(i, color);

            if (game.isValidMove(move)) {
                valid = true;
            }
            else {
                System.out.print("Try again! ");
            }
        }
        return move;
    }
}
