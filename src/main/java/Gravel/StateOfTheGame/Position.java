package Gravel.StateOfTheGame;

import org.tinylog.Logger;

import static Gravel.StateOfTheGame.BoardState.*;

/**
 * This {@code Position} is a {@link Record} which helps us to get the positions of the Gravels from the board.
 *
 * @param row it's the row index from the board
 * @param col it's the column index from the board
 */
public record Position(int row, int col) {

    /**
     * Initializes the row and the column if they are the correct inputs.
     *
     * @param row represents the given row.
     * @param col represents the given column.
     * @throws IllegalArgumentException if you give wrong inputs.
     */
    public Position(int row, int col) {
        Logger.info("Checking row " + row + " and column " + col + " values!");
        if (row < 0 || row >= BOARD_SIZE) {
            throw new IllegalArgumentException("Wrong ROW coordinates! Out of range!");
        } else if (col < 0 || col >= BOARD_SIZE) {
            throw new IllegalArgumentException("Wrong COLUMN coordinates! Out of range!");
        } else {
            this.row = row;
            this.col = col;
        }
    }

    /**
     * {code isPositionNeighbour} checks if the current positions are neighbours or not.
     *
     * @param currentPosition the position of the current row and column.
     * @return true if the currentPosition is a neighbour, otherwise it returns false.
     */
    public boolean isPositionNeighbour(Position currentPosition) {
        if (Math.abs(this.row - currentPosition.row) <= 1
                && Math.abs(this.col - currentPosition.col) <= 1
                && !(this.row == currentPosition.row() && this.col == currentPosition.col()))
            return true;
        else
            return false;
    }

    /**
     * {code toString} formats the row and the column to a String pair.
     *
     * @return the formatted String.
     */
    @Override
    public String toString() {
        return String.format(" (%d, %d) ", row, col);
    }

    /**
     * Checks if the object is equal with {code this}
     *
     * @param obj the reference object with which to compare.
     * @return if the above said is True, it returns True or obj is of Position
     * and the given position is the same with the row and col it returns true otherwise false.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        return (obj instanceof Position position) && position.row == row && position.col == col;
    }

}
