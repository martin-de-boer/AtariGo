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
    private List<Color> fields;

    /**
     * Creates a board with all empty squares.
     */
    public Board() {
        this.fields = new ArrayList<>();
        for (int i = 0; i < DIM * DIM; i++) {
            fields.add(Color.EMPTY);
        }
    }

    /**
     * Calculates the index of a tile on the board based on the given x and y coordinates.
     *
     * @param x the x-coordinate of the tile
     * @param y the y-coordinate of the tile
     * @return the index of the tile on the board as an integer
     */
    //@pure
    public int indexOf(int x, int y) {
        return x + y * DIM;
    }

    //@pure
    public int xOf(int field) {
        return field % DIM;
    }

    //@pure
    public int yOf(int field) {
        return field / DIM;
    }

    /**
     * Creates and returns a deep copy of the current board.
     *
     * @return a new Board object that is an identical but independent copy of the current board
     */
    //@pure
    public Board deepCopy() {
        Board copy = new Board();
        System.arraycopy(this.fields.toArray(), 0, copy.fields.toArray(), 0, this.fields.size());
        return copy;
    }


    //Tile methods

    /**
     * Sets the tile to a color.
     * @param field the index of the tile
     * @param color the color that the tile should be
     */
    //@requires isValidField(field);
    //@ensures fields.get(field) == color;
    public void setField(int field, Color color) {
        this.fields.set(field, color);
    }

    /**
     * Returns the color of a field
     * @param field the index of the field
     * @return the color of the specified field
     */
    //@requires isValidField(field);
    //@pure
    public Color getField(int field) {
        return this.fields.get(field);
    }

    /**
     * Checks if the specified field is empty.
     *
     * @param field the index of the field to check
     * @return true if the field is empty, false otherwise
     */
    //@requires isValidField(field);
    //@ensures (this.fields.get(field) == Color.EMPTY) <==> \result == true;
    //@pure
    public Boolean isEmpty(int field) {
        return this.fields.get(field) == Color.EMPTY;
    }

    /**
     * Checks whether the given field index is valid within the bounds of the board.
     *
     * @param field the index of the field to check
     * @return true if the field index is valid, false otherwise
     */
    //@ensures (field >= 0 && field < DIM * DIM) <==> \result == true;
    //@pure
    public Boolean isValidField(int field) {
        return field >= 0 && field < DIM * DIM;
    }


    /**
     * Checks whether the given field coordinates are valid within the bounds of the board.
     *
     * @param x the x coordinate of the field to check
     * @param y the y coordinate of the field to check
     * @return true if the field coordinates are valid, false otherwise
     */
    //@ensures (x >= 0 && x < DIM && y >= 0 && y < DIM) <==> \result == true;
    //@pure
    public Boolean isValidField(int x, int y) {
        return x >= 0 && x < DIM && y >= 0 && y < DIM;
    }

    //Neighbor methods

    /**
     * Retrieves the neighboring fields of the specified field on the board.
     *
     * @param field the index of the field for which neighbors are to be retrieved
     * @return a list of integers representing the indices of the neighboring fields
     */
    //@requires isValidField(field);
    //@ensures (\forall int i; \result.contains(i); isValidField(i));
    //@pure
    public List<Integer> getNeighbors(int field) {
        int x = xOf(field);
        int y = yOf(field);
        List<Integer> neighbors = new ArrayList<>();
        if( x > 0 ) {
            neighbors.add(indexOf(x - 1, y));
        }
        if( x < DIM - 1 ) {
            neighbors.add(indexOf(x + 1, y));
        }
        if( y > 0 ) {
            neighbors.add(indexOf(x, y - 1));
        }
        if( y < DIM - 1 ) {
            neighbors.add(indexOf(x, y + 1));
        }
        return neighbors;
    }

    /**
     * Checks if the specified field has no empty neighboring fields.
     *
     * @param field the index of the field to check
     * @return true if none of the neighboring fields are empty, false otherwise
     */
    //@requires isValidField(field);
    //@ensures \result == true <==> (\forall int i; getNeighbors(field).contains(i); getField(i) != Color.EMPTY);
    //@pure
    public Boolean hasEmptyNeighbors(int field) {
        for( int i : getNeighbors(field)) {
            if( getField(i) == Color.EMPTY ) {
                return false;
            }
        }
        return true;
    }


    //Group methods

    /**
     * Returns a List of connected fields starting from the specified field.
     *
     * @param field the starting field index
     * @return a list of integers representing the connected group
     */
    //@requires isValidField(field);
    //@requires getField(field) != Color.EMPTY;
    //@ensures (\forall int f; \result.contains(f); isValidField(f));
    //@pure
    public List<Integer> getGroup(int field) {
        List<Integer> group = new ArrayList<>();
        group.add(field);

        int i = 0;
        while (i < group.size()) {
            for (int j : getNeighbors(group.get(i))) {
                if (group.contains(j)) {
                    continue;
                }
                if (!isValidField(j)) {
                    continue;
                }
                if (getField(j) != getField(field)) {
                    continue;
                }
                group.add(j);
            }
            i++;
        }
        return group;
    }

    /**
     * Checks whether the given group of fields is completely surrounded by fields of other colors.
     *
     * @param group a list of integers representing the indices of the fields in the group
     * @return true if the group is completely surrounded, false otherwise
     */
    //@requires (\forall int field; group.contains(field); isValidField(field));
    //@ensures (\forall int field; group.contains(field); hasEmptyNeighbors(field)) <==> \result == true;
    //@pure
    public Boolean isGroupSurrounded(List<Integer> group) {
        for( int i : group ) {
            if( hasEmptyNeighbors(i) ) {
                return false;
            }
        }
        return true;
    }

    /**
     * Generates a string representation of the current board.
     * Also works with different DIM values.
     * @return a string representing the board
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // First line:
        sb.append((ConsoleColors.YELLOW_BOLD_BRIGHT + "+" + ConsoleColors.RED_BOLD_BRIGHT +
                "---").repeat(DIM));
        sb.append(ConsoleColors.YELLOW_BOLD_BRIGHT + "+\n");

        // Now recursively print the rest:
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                sb.append(ConsoleColors.RED_BOLD_BRIGHT + "|" + ConsoleColors.RESET);
                switch (this.fields.get(i * DIM + j)) {
                    case BLACK -> sb.append(ConsoleColors.PURPLE_BOLD_BRIGHT + " O ");
                    case WHITE -> sb.append(ConsoleColors.WHITE_BOLD_BRIGHT + " O ");
                    case EMPTY -> {
                        // Print numbers in empty cells on the edges:
                        if (i == 0) sb.append(ConsoleColors.WHITE + " " + (j % 10) + " ");
                        else if (j == 0) sb.append(ConsoleColors.WHITE + " " + (i % 10) + " ");
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
