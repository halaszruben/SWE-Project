package Gravel.StateOfTheGame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardStateTest {

    BoardState board;

    @BeforeEach
    void init() {
        board = new BoardState();
    }

    @Test
    void toStringTest() {
        assertTrue(board.toString().contains(" "));
        assertTrue(board.toString().contains("\n"));

        assertEquals(36, board.toString().length());
    }

    @Test
    void addSelectedPositionTest() {
        assertThrows(IllegalArgumentException.class, () -> board.addSelectedPosition(new Position(5, 5)));

        board.addSelectedPosition(new Position(1, 3));
        assertEquals(1, board.getSelectedPosition().size());

        assertThrows(IllegalArgumentException.class, () -> board.addSelectedPosition(new Position(1, 3)));
    }

    @Test
    void removeSelectedPositionTest() {
        board.addSelectedPosition(new Position(1, 3));
        assertEquals(1, board.getSelectedPosition().size());
        board.removeSelectedPosition(new Position(1, 3));
        assertEquals(0, board.getSelectedPosition().size());

        board.resetSelectedPosition();

        board.addSelectedPosition(new Position(1, 3));
        board.addSelectedPosition(new Position(2, 2));
        board.removeSelectedPosition(new Position(1, 3));
        assertEquals(1, board.getSelectedPosition().size());
    }

    @Test
    void getSelectedPositionTest() {
        assertEquals(0, board.getSelectedPosition().size());
    }

    @Test
    void resetSelectedPositionTest() {
        board.addSelectedPosition(new Position(1, 3));
        assertEquals(1, board.getSelectedPosition().size());
        board.resetSelectedPosition();
        assertEquals(0, board.getSelectedPosition().size());
    }

    @Test
    void isSameRowOrColTest() {
        board.addSelectedPosition(new Position(0, 0));
        assertTrue(board.isSameRowOrCol());
        board.resetSelectedPosition();

        board.addSelectedPosition(new Position(2, 2));
        board.addSelectedPosition(new Position(2, 1));
        board.addSelectedPosition(new Position(2, 0));
        assertTrue(board.isSameRowOrCol());
        board.resetSelectedPosition();

        assertFalse(board.isSameRowOrCol());
    }

    @Test
    void isOnTheSameRowTest() {
        board.addSelectedPosition(new Position(2, 2));
        board.addSelectedPosition(new Position(2, 1));
        board.addSelectedPosition(new Position(2, 0));
        assertTrue(board.isSameRowOrCol());
    }

    @Test
    void isOnTheSameColTest() {
        board.addSelectedPosition(new Position(0, 1));
        board.addSelectedPosition(new Position(1, 1));
        assertTrue(board.isSameRowOrCol());
    }
    
}
