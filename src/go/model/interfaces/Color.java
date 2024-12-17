package go.model.interfaces;

/**
 * Enumerated type that defines the colors of a tile in the Go game.
 * BLACK means the tile is occupied by a black stone;
 * WHITE means the tile is occupied by a white stone;
 * EMPTY means the tile is unoccupied.
 */
public enum Color {
    BLACK, WHITE, EMPTY;

    /**
     * Returns the other color of the current color.
     * If the current color is empty, the method returns empty.
     * @return the other color or empty if the current color is empty
     */
    public Color other() {
        if (this == BLACK) {
            return WHITE;
        } else if (this == WHITE) {
            return BLACK;
        } else {
            return EMPTY;
        }
    }

    /**
     * Checks whether the current color is empty.
     * @return true if the color is empty, false otherwise
     */
    public boolean isEmpty() {
        return this == EMPTY;
    }

    /**
     * Method to get the string version of the current color.
     * @return the string version of the current color.
     */
    @Override
    public String toString() {
        if (this == BLACK) {
            return "Black";
        } else if (this == WHITE) {
            return "White";
        } else {
            return "Empty";
        }
    }
}
