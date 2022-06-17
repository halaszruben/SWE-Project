package Gravel.StateOfTheGame;

import javafx.beans.property.ReadOnlyObjectWrapper;
import org.tinylog.Logger;

import java.util.ArrayList;

/**
 * This {@code BoardState} represents the state of the whole game and is also a {@link Class}.
 */
public class BoardState {

    /**
     * It represents the size of the board.
     */
    public static final int BOARD_SIZE = 4;

    /**
     * It represents the number of Turns in a game.
     */
    public int numberOfTurns;

    private PlayerTurn playerTurn;

    private ArrayList<Position> selectedPosition;

    private ReadOnlyObjectWrapper<Gravels>[][] board = new ReadOnlyObjectWrapper[BOARD_SIZE][BOARD_SIZE];

    /**
     * The {code BoardState} is the initialization of the board.
     * It gives the selected Positions a new empty {@link ArrayList}.
     */
    public BoardState() {
        Logger.info("Initializing the Board");
        numberOfTurns = 0;
        playerTurn = PlayerTurn.FIRST_PLAYER;
        for (var row = 0; row < BOARD_SIZE; row++) {
            for (var col = 0; col < BOARD_SIZE; col++) {
                board[row][col] = new ReadOnlyObjectWrapper<Gravels>(Gravels.GRAVELS);
            }
        }
        selectedPosition = new ArrayList<Position>();
    }

    /**
     * {@link String} type method, which formats the board to it's String implementation.
     *
     * @return the {@link String} board.
     */
    public String toString() {
        StringBuilder sbBoard = new StringBuilder();
        for (var row = 0; row < BOARD_SIZE; row++) {
            for (var col = 0; col < BOARD_SIZE; col++) {
                sbBoard.append(board[row][col].get().ordinal()).append(' ');
            }
            sbBoard.append('\n');
        }
        return sbBoard.toString();
    }

    /**
     * The {code addSelectedPosition} adds all the positions to the selectedPosition {@link ArrayList<Position>} type.
     *
     * @param position contains the row and column which will be added to the selectedPosition if
     *                 it does not @throws IllegalArgumentException .
     */
    public void addSelectedPosition(Position position) {
        Logger.trace("Adding the selected " + position);
        for (var allSelectedPositions : selectedPosition) {
            if (allSelectedPositions.equals(position))
                throw new IllegalArgumentException("This Position was already selected!");
        }

        selectedPosition.add(position);
    }

    /**
     * Removes the selected positions if they were already selected.
     *
     * @param position represent the row and the column of a position
     */
    public void removeSelectedPosition(Position position) {
        Logger.info("Removing the selected " + position);
        for (var allSelectedPositions : selectedPosition) {
            if (allSelectedPositions.equals(position)) {
                selectedPosition.remove(position);
                return;
            }
        }
    }

    /**
     * {@link ArrayList<Position>} get's the selected positions.
     *
     * @return a copy of the selected position.
     */
    public ArrayList<Position> getSelectedPosition() {
        return (ArrayList<Position>) selectedPosition.clone();
    }

    /**
     * Clears the selected position, making it selectable again.
     */
    public void resetSelectedPosition() {
        selectedPosition.clear();
    }

    /**
     * Get's the next player in the upcoming turn. Also it numbers the turns that have been played so far.
     */
    public void nextPlayer() {
        this.playerTurn = this.playerTurn.getNextPlayerTurn();
        numberOfTurns++;
    }

    /**
     * {{@return true if it's on the same row or column as the selected position, otherwise it returns false.}
     */
    public boolean isSameRowOrCol() {
        if (selectedPosition.size() == 1) {
            return true;
        } else if (selectedPosition.size() < 1 || selectedPosition.size() > 4) {
            return false;
        } else if (isOnTheSameRow()) {
            return true;
        } else if (isOnTheSameCol()) {
            return true;
        }
        return false;
    }

    /**
     * This {code isOnTheSameRow}} checks if the selected position is on the same row as the first one.
     *
     * @return all the rows which are on the same row as the selected positions.
     */
    public boolean isOnTheSameRow() {
        var allRow = new ArrayList<Integer>();

        Logger.info("Checking If the rows match.");
        for (var rowPos : selectedPosition) {
            allRow.add(rowPos.row());
        }

        return allRow.stream()
                .allMatch(allRow.get(0)::equals);
    }

    /**
     * This {code isOnTheSameRow}} checks if the selected position is on the same column as the first one.
     *
     * @return all the columns which are on the same column as the selected positions.
     */
    public boolean isOnTheSameCol() {
        var allCol = new ArrayList<Integer>();

        Logger.info("Checking If the Columns match.");
        for (var colPos : selectedPosition) {
            allCol.add(colPos.col());
        }

        return allCol.stream()
                .allMatch(allCol.get(0)::equals);
    }

    /**
     * {@return the player turn}
     */
    public PlayerTurn getPlayerTurn() {
        return playerTurn;
    }

}
