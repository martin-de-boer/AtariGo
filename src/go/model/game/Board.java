package go.model.game;

import go.model.interfaces.Color;
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
}
