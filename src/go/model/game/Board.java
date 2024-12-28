package go.model.game;

import go.model.interfaces.Color;
import go.util.ConsoleColors;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents the board of a game of Go with a predetermined dimension.
 */
public class Board {
    public static final int DIM = 7;
    private List<Color> fields;

    //TODO hyper optimise methods for time and disregard memory

    //TODO add JML invariants

    //TODO add asserts for defined conditions

    /**
     * Creates a board with the starting configuration.
     * 4 stones in a checkerboard pattern
     */
    //ensures that the setup happens, rest of the fields are empty (JML)
    public Board() {
        this.fields = new ArrayList<>();
        for (int i = 0; i < DIM * DIM; i++) {
            fields.add(Color.EMPTY);
        }
        setUp();
    }

    /**
     * Sets the middle four squares to the starting position.
     * Only gets called by the constructor
     */
    //ensures that the fields are the color they need to be
    private void setUp() {
        fields.set(indexOf((DIM-1)/2,(DIM-1)/2), Color.BLACK);
        fields.set(indexOf((DIM-1)/2-1,(DIM-1)/2), Color.WHITE);
        fields.set(indexOf((DIM-1)/2-1,(DIM-1)/2-1), Color.BLACK);
        fields.set(indexOf((DIM-1)/2,(DIM-1)/2-1), Color.WHITE);
    }

    /**
     * Checks if the game is over.
     * @return true if there is at least one group enclosed.
     */
    //TODO ensures (result) == condition
    //@pure
    public Boolean isGameOver() {
        for (int i = 0; i < Board.DIM * Board.DIM; i++) {
            if (!isEmpty(i) && numOfLiberties(getGroup(i)) == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the color of the next player.
     * @return color of the next player
     */
    //@requires !isGameOver();
    //@ensures \result == Color.BLACK <==> (getFields(Color.BLACK).size() == getFields(Color.WHITE).size());
    //@pure
    public Color getTurn() {
        //TODO if isGameOver then return null
        //TODO modify this method to also check for White status and return null if neither
        return getFields(Color.BLACK).size() == getFields(Color.WHITE).size() ? Color.BLACK : Color.WHITE;
    }

    /**
     * Calculates the index of a field on the board based on the given x and y coordinates.
     *
     * @param x the x-coordinate of the field
     * @param y the y-coordinate of the field
     * @return the index of the field on the board as an integer
     */
    //@requires isValidField(x, y);
    //@ensures isValidField(\result);
    //@pure
    public int indexOf(int x, int y) {
        return x * DIM + y;
    }

    /**
     * Calculates the row index of a field based on its index.
     *
     * @param field the index of the field
     * @return the row index of the specified field
     */
    //@requires isValidField(field);
    //@ensures isValidField(\result, 0);
    //TODO modify for all columns
    //@pure
    public int getRow(int field) {
        return field / DIM;
    }

    /**
     * Calculates the column index of a field based on its index.
     *
     * @param field the index of the field
     * @return the column index of the specified field
     */
    //@requires isValidField(field);
    //@ensures isValidField(0, \result);
    //TODO modify for all rows
    //@pure
    public int getCol(int field) {
       return field % DIM;
    }

    /**
     * Private constructor for the deepcopy method.
     * Initializes a Board with the given fields.
     * @param fields copies the fields array into the new Board
     */
    //@ensures this.fields == fields;
    private Board(List<Color> fields) {
        this.fields = new ArrayList<>(fields);
    }

    /**
     * Deepcopy method.
     * @return a copy of the board.
     */
    //@ensures this.fields == \result.fields;
    public Board deepCopy() {
        return new Board(new ArrayList<>(this.fields));
    }

    /**
     * Sets the field to a color.
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
    public Color getColor(int field) {
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

    /**
     * Retrieves the neighboring fields of the specified field on the board.
     *
     * @param field the index of the field for which neighbors are to be retrieved
     * @return a Set of integers representing the indices of the neighboring fields
     */
    //@requires isValidField(field);
    //@ensures (\forall int i; \result.contains(i); isValidField(i));
    //@pure
    public Set<Integer> getNeighbors(int field) {
        int x = getRow(field);
        int y = getCol(field);
        Set<Integer> neighbors = new HashSet<>();

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
     * Retrieves the neighboring fields of a group of fields on the board.
     *
     * @param group a set of integers representing the indices of the fields in the group
     * @return a set of integers representing the indices of neighboring fields
     *         of the group
     */
    //@pure
    public Set<Integer> getNeighbors(Set <Integer> group) {

        Set<Integer> neighbors = new HashSet<>();

        for( int i : group ) {
            neighbors.addAll(getNeighbors(i));
        }

        neighbors.removeAll(group);

        return neighbors;
    }

    /**
     * Checks if the specified field has no empty neighboring fields.
     *
     * @param field the index of the field to check
     * @return true if none of the neighboring fields are empty, false otherwise
     */
    //@requires isValidField(field);
    //@ensures \result == (\num_of int i; getNeighbors(field).contains(i); getColor(i) != Color.EMPTY);
    //@pure
    public int numOfLiberties(int field) {
        int count = 0;
        for( int i : getNeighbors(field)) {
            if( isEmpty(i) ) {
                count++;
            }
        }
        return count;
    }

    /**
     * Calculates the number of liberties (empty neighboring fields)
     * for a given group of fields on the board.
     *
     * @param group a set of integers representing the indices of the group of fields
     * @return the number of empty neighboring fields (liberties) for the group
     */
    //@pure
    public int numOfLiberties(Set<Integer> group) {
        return getLiberties(group).size();
    }

    /**
     * Returns the index of the liberties of a field (empty neighbours).
     * @param field field to check
     * @return the index of the empty neighbours of the field
     */
    //@requires isValidField(field) && !isEmpty(field);
    //@ensures (\forall int i; \result.contains(i); isValidField(i) && isEmpty(i) && getNeighbors(field).contains(i));
    //@pure
    public Set<Integer> getLiberties(int field) {
        Set<Integer> liberties = new HashSet<>();
        for( int i : getNeighbors(field) ) {
            if( isEmpty(i) ) {
                liberties.add(i);
            }
        }
        return liberties;
    }

    /**
     * Returns the index of the liberties of a group.
     * @param group group to check
     * @return the empty neighbours of the group
     */
    //@requires (\forall int i; group.contains(i); isValidField(i) && !isEmpty(i));
    //@ensures (\forall int i; \result.contains(i); isValidField(i) && isEmpty(i));
    //@pure
    public Set<Integer> getLiberties(Set <Integer> group) {
        Set<Integer> liberties = new HashSet<>();
        for( int i : group ) {
            liberties.addAll(getLiberties(i));
        }
        return liberties;
    }

    /**
     * Determines whether a specified field is an "eye" in the game.
     * An "eye" is an empty point surrounded by stones of the same group.
     *
     * @param field the index of the field to check
     * @return true if the field is an eye, false otherwise
     */
    //@pure
    public boolean isEye(int field) {

        if( getColor(field) != Color.EMPTY ) {
            return false;
        }

        if( numOfLiberties(field) != 0 ) {
            return false;
        }

        Set<Integer> neighbors = getNeighbors(field);

        Set<Integer> group = getGroup(neighbors.iterator().next());

        return new HashSet<>(group).containsAll(neighbors);
    }

    /**
     * Determines if a group of fields is considered "alive" based on the presence
     * of at least two "eyes" in its neighboring fields.
     *
     * @param group a set of integers representing the indices of the fields in the group
     * @return true if the group has more than one "eye" in its neighboring fields, false otherwise
     */
    //@pure
    public boolean isAlive(Set <Integer> group) {

        int numOfEyes = 0;

        for( int i : getNeighbors(group) ) {
            if( isEye(i) ) {
                numOfEyes++;
            }
        }

        return numOfEyes > 1;
    }

    /**
     * Returns a Set of connected fields starting from the specified field.
     *
     * @param field the starting field index
     * @return a set of integers representing the connected group
     */
    //@requires isValidField(field);
    //@requires getColor(field) != Color.EMPTY;
    //@ensures (\forall int f; \result.contains(f); isValidField(f));
    //@ensures ((\forall int f; \result.contains(f); getColor(f) == getColor(field)));
    //@pure
    public Set<Integer> getGroup(int field) {
        Set<Integer> group = new HashSet<>();
        Set<Integer> visited = new HashSet<>();
        List<Integer> stack = new ArrayList<>();
        stack.add(field);

        while (!stack.isEmpty()) {
            int current = stack.remove(stack.size() - 1);
            if (visited.contains(current)) {
                continue;
            }
            visited.add(current);
            group.add(current);

            for (int neighbor : getNeighbors(current)) {
                if (!visited.contains(neighbor) && getColor(neighbor) == getColor(field)) {
                    stack.add(neighbor);
                }
            }
        }
        return group;
    }

    /**
     * Retrieves all groups of connected fields on the board that are of the specified color.
     *
     * @param color the color of the fields to group
     * @return a set of sets where each inner set contains the indices of a group of connected fields of the specified color
     */
    //@pure
    //TODO optimise
    public Set<Set<Integer>> getGroups (Color color) {
        Set <Set <Integer>> groups = new HashSet<>();

        for( int i = 0; i < Board.DIM * Board.DIM; i++ ) {
            if( getColor(i) == color ) {
                Set <Integer> group = getGroup(i);
                groups.add(group);
            }
        }

        return groups;
    }

    /**
     * Retrieves all groups of connected fields on the board for both black and white colors.
     *
     * @return a set of sets where each inner set contains the indices of a group of connected fields for both black and white colors
     */
    //@pure
    public Set<Set<Integer>> getGroups() {
        Set <Set <Integer>> groups = new HashSet<>();
        groups.addAll(getGroups(Color.BLACK));
        groups.addAll(getGroups(Color.WHITE));
        return groups;
    }

    /**
     * Retrieves the set of field indices on the board that match the specified color.
     *
     * @param color the desired color to filter fields
     * @return a set of integers representing the indices of fields with the specified color
     */
    //@ensures (\forall int i; fields.contains(i); getColor(i) == color <==> \result.contains(i));
    //@pure
    public Set<Integer> getFields(Color color) {
        Set<Integer> fields = new HashSet<>();
        for (int i = 0; i < Board.DIM * Board.DIM; i++) {
            if (getColor(i) == color) {
                fields.add(i);
            }
        }
        return fields;
    }

    /**
     * Retrieves the set of all field indices on the board for both black and white colors.
     *
     * @return a set of integers representing the indices of all fields occupied by black or white stones
     */
    //@ensures (\forall int i; fields.contains(i); !isEmpty(i) <==> \result.contains(i));
    //@pure
    public Set<Integer> getFields() {
        Set<Integer> f = new HashSet<>();
        f.addAll(getFields(Color.BLACK));
        f.addAll(getFields(Color.WHITE));
        return f;
    }


    /**
     * Generates a string representation of the current board.
     * Also works with different DIM values.
     *
     * @return a string representing the board
     */
    //@pure
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
