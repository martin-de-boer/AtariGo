package go.model.game;

import go.model.interfaces.Color;

/**
 * Represents a move in a game of Go. A move consists of a tile on the board and a color.
 */
public class Move {
    private final int tile;
    private final Color color;


    /**
     * Constructs a new Move object with the specified tile position and color.
     * @param tile the position of the move as an integer
     * @param color the color of the move
     */
    public Move(int tile, Color color) {
        this.tile = tile;
        this.color = color;
    }

    /**
     * Retrieves the tile of with this move.
     * @return the tile as an integer
     */
    public int getTile() {
        return this.tile;
    }

    /**
     * Retrieves the color of this move.
     * @return the color of the move
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Calculates the x-coordinate of the tile on the board based on its tile index.
     * @return the x-coordinate of the tile as an integer
     */
    public int getX() {
        return this.tile % Board.DIM;
    }

    /**
     * Calculates the y-coordinate of the tile on the board based on its tile index.
     * @return the y-coordinate of the tile as an integer
     */
    public int getY() {
        return this.tile / Board.DIM;
    }

    /**
     * Returns a string representation of the move, including the tile number,
     * its coordinates (x, y), and the associated color.
     * @return a string describing the move
     */
    @Override
    public String toString() {
        return "Move: " + getTile() + " (" + getX() + "," + getY() + ")" + " to " +
                this.color.toString();
    }
}
