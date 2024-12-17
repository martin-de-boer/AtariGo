package go.model.game;

import go.model.interfaces.Color;
import go.util.ConsoleColors;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the board of a game of Go with a predetermined dimension.
 */
public class Board {
    public static final int DIM = 7;
    private List<Color> squares;

    /**
     * Creates a board with all empty squares.
     */
    public Board() {
        this.squares = new ArrayList<>();
        for (int i = 0; i < DIM * DIM; i++) {
            squares.add(Color.EMPTY);
        }
    }

    /**
     * Sets the tile to a color.
     * @param tile the index of the tile
     * @param color the color that the tile should be
     */
    public void setSquare(int tile, Color color) {
        this.squares.set(tile, color);
    }

    /**
     * Sets the tile at (x,y) to a color.
     * @param x the x index of the tile
     * @param y the y index of the tile
     * @param color the color that the tile should be
     */
    public void setSquare(int x, int y, Color color) {
        this.squares.set(x + y * DIM, color);
    }

    /**
     * Sets the tile to empty.
     * @param tile the index of the tile
     */
    public void clearSquare(int tile) {
        this.squares.set(tile, Color.EMPTY);
    }

    /**
     * Sets the tile at (x,y) to empty.
     * @param x the x index of the tile
     * @param y the y index of the tile
     */
    public void clearSquare(int x, int y) {
        this.squares.set(x + y * DIM, Color.EMPTY);
    }

    /**
     * Returns the color of a tile
     * @param tile the index of the tile
     * @return the color of the specified tile
     */
    public Color getSquare(int tile) {
        return this.squares.get(tile);
    }

    /**
     * Returns the color of the tile at (x,y)
     * @param x the x index of the tile
     * @param y the y index of the tile
     * @return the color of the specified tile
     */
    public Color getSquare(int x, int y) {
        return this.squares.get(x + y * DIM);
    }

    /**
     * Sets all the tiles to empty.
     */
    public void clear() {
        for (int i = 0; i < DIM * DIM; i++) {
            squares.set(i, Color.EMPTY);
        }
    }

    /**
     * Creates a deep copy of the current board.
     * @return a new board instance with identical square values as the original board
     */
    public Board deepCopy() {
        Board copy = new Board();
        for (int i = 0; i < DIM * DIM; i++) {
            copy.squares.set(i, this.squares.get(i));
        }
        return copy;
    }

    /**
     * Generates a string representation of the current board.
     * Hardcoded to DIM = 7.
     * @return a string representing the board
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Board:\n");

        sb.append((ConsoleColors.YELLOW_BOLD_BRIGHT + "+" + ConsoleColors.RED_BOLD_BRIGHT +
                "---").repeat(DIM));
        sb.append(ConsoleColors.YELLOW_BOLD_BRIGHT + "+\n");

        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                sb.append(ConsoleColors.RED_BOLD_BRIGHT + "|" + ConsoleColors.RESET);
                switch (this.squares.get(i * DIM + j)) {
                    case BLACK -> sb.append(ConsoleColors.PURPLE_BOLD_BRIGHT + " O ");
                    case WHITE -> sb.append(ConsoleColors.WHITE_BOLD_BRIGHT + " O ");
                    case EMPTY -> {
                        if (i == 0) sb.append(ConsoleColors.WHITE + " " + j + " ");
                        else if (j == 0) sb.append(ConsoleColors.WHITE + " " + i + " ");
                        else sb.append("   ");
                    }
                }
            }
            sb.append(ConsoleColors.RED_BOLD_BRIGHT + "|\n");

            sb.append((ConsoleColors.YELLOW_BOLD_BRIGHT + "+" + ConsoleColors.RED_BOLD_BRIGHT +
                    "---").repeat(DIM));
            sb.append(ConsoleColors.YELLOW_BOLD_BRIGHT + "+\n");
        }
        return sb.toString();
    }
}
