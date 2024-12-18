package go.model.game;

import go.model.interfaces.Color;

/**
 * Represents a move in a game of Go. A move consists of a field on the board and a color.
 */
public class Move {
    private final int field;
    private final Color color;


    /**
     * Constructs a new Move object with the specified field position and color.
     * @param tile the position of the move as an integer
     * @param color the color of the move
     */
    public Move(int tile, Color color) {
        this.field = tile;
        this.color = color;
    }

    /**
     * Constructs a new Move object with the specified field position and color.
     * @param x the x coordinate of the move
     * @param y the y coordinate of the move
     * @param color the color of the move
     */
    public Move(int x, int y, Color color) {
        this.field = x + y * Board.DIM;
        this.color = color;
    }

    /**
     * Retrieves the field of with this move.
     * @return the field as an integer
     */
    //@pure
    public int getField() {
        return this.field;
    }

    /**
     * Retrieves the color of this move.
     * @return the color of the move
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Calculates the x-coordinate of the field on the board based on its field index.
     * @return the x-coordinate of the field as an integer
     */
    public int getX() {
        return this.field % Board.DIM;
    }

    /**
     * Calculates the y-coordinate of the field on the board based on its field index.
     * @return the y-coordinate of the field as an integer
     */
    public int getY() {
        return this.field / Board.DIM;
    }

    /**
     * Returns a string representation of the move, including the field number,
     * its coordinates (x, y), and the associated color.
     * @return a string describing the move
     */
    @Override
    public String toString() {
        return "Move: " + getField() + " (" + getX() + "," + getY() + ")" + " to " +
                this.color.toString();
    }
}
