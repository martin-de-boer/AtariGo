package go.model.game;

import go.model.interfaces.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the board of a game of Go with a predetermined dimension.
 */
public class Board {
    public static final int DIM = 7;
    private List<Color> fields;

    /**
     * Creates a board with all empty fields.
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
     * Sets a field to empty.
     * @param field the index of the field
     */
    //@requires isValidField(field);
    //@ensures fields.get(field) == Color.EMPTY;
    public void clearField(int field) {
        this.fields.set(field, Color.EMPTY);
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


    //Neighbor methods

    /**
     * Retrieves the neighboring fields of the specified field on the board.
     *
     * @param field the index of the field for which neighbors are to be retrieved
     * @return a list of integers representing the indices of the neighboring fields
     */
    //@requires isValidField(field);
    //@pure
    public List<Integer> getNeighbors(int field) {
        return new ArrayList<>();
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
    public Boolean noEmptyNeighbors(int field) {
        return false;
    }


    //Move methods

    /**
     * Determines whether the given move captures any opponent's pieces.
     *
     * @param move the move to evaluate for capturing
     * @return true if the move results in a capture, false otherwise
     */
    //@requires isValidField(move.getField());
    //@pure
    public Boolean captures(Move move) {
        return false;
    }


    //Group methods

    /**
     * Returns a List of connected fields starting from the specified field.
     *
     * @param field the starting field index
     * @return a list of integers representing the connected group
     */
    //@requires isValidField(field);
    //@ensures (\forall int f; \result.contains(f); isValidField(f));
    //@pure
    public List<Integer> getGroup(int field) {
        return new ArrayList<>();
    }

    /**
     * Checks whether the given group of fields is completely surrounded by fields of other colors.
     *
     * @param group a list of integers representing the indices of the fields in the group
     * @return true if the group is completely surrounded, false otherwise
     */
    //@requires (\forall int field; group.contains(field); isValidField(field));
    //@ensures (\forall int field; group.contains(field); noEmptyNeighbors(field)) <==> \result == true;
    //@pure
    public Boolean isGroupSurrounded(List<Integer> group) {
        return false;
    }
}
