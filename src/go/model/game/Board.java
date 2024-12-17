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
     * Calculates the index of a tile on the board based on the given x and y coordinates.
     *
     * @param x the x-coordinate of the tile
     * @param y the y-coordinate of the tile
     * @return the index of the tile on the board as an integer
     */
    public int indexOf(int x, int y) {
        return x + y * DIM;
    }

    /**
     * Creates and returns a deep copy of the current board.
     *
     * @return a new Board object that is an identical but independent copy of the current board
     */
    public Board deepCopy() {
        Board copy = new Board();
        System.arraycopy(this.squares.toArray(), 0, copy.squares.toArray(), 0, this.squares.size());
        return copy;
    }


    //Tile methods

    /**
     * Sets the tile to a color.
     * @param tile the index of the tile
     * @param color the color that the tile should be
     */
    public void setSquare(int tile, Color color) {
        this.squares.set(tile, color);
    }

    /**
     * Sets the tile to empty.
     * @param tile the index of the tile
     */
    public void clearSquare(int tile) {
        this.squares.set(tile, Color.EMPTY);
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
     * Checks if the specified tile is empty.
     *
     * @param tile the index of the tile to check
     * @return true if the tile is empty, false otherwise
     */
    public Boolean isEmpty(int tile) {
        return this.squares.get(tile) == Color.EMPTY;
    }

    /**
     * Checks if the specified tile index is valid on the board.
     *
     * @param tile the index of the tile to check
     * @return true if the tile index is valid, false otherwise
     */
    public Boolean isValidTile(int tile) {
        return tile >= 0 && tile < DIM * DIM;
    }

    //Neighbor methods

    /**
     * Retrieves the list of neighboring tiles for the specified tile index.
     *
     * @param tile the index of the tile whose neighbors are to be retrieved
     * @return a list of integers representing the indices of neighboring tiles
     */
    public List<Integer> getNeighbors(int tile) {
        return new ArrayList<>();
    }

    /**
     * Checks if the specified tile has no empty neighbors on the board.
     *
     * @param tile the index of the tile to check
     * @return true if none of the neighboring tiles are empty, false otherwise
     */
    public Boolean noEmptyNeighbors(int tile) {
        return false;
    }


    //Move methods

    /**
     * Determines whether the given move captures any opponent's pieces.
     *
     * @param move the move to evaluate for capturing
     * @return true if the move results in a capture, false otherwise
     */
    public Boolean captures(Move move) {
        return false;
    }

    //Group methods

    /**
     * Retrieves a group of connected tiles starting from the given tile.
     * A group consists of tiles of the same color that are directly connected.
     *
     * @param tile the starting tile index
     * @return a list of integers representing the indices of the tiles in the group
     */
    public List<Integer> getGroup(int tile) {
        return new ArrayList<>();
    }

    /**
     * Checks whether the given group of tiles is completely surrounded by tiles of other colors.
     *
     * @param group a list of integers representing the indices of the tiles in the group
     * @return true if the group is completely surrounded, false otherwise
     */
    public Boolean isGroupSurrounded(List<Integer> group) {
        return false;
    }
}
