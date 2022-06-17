package Gravel.StateOfTheGame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {

    Position position;

    @BeforeEach
    void init() {
        position = new Position(1, 3);
    }

    @Test
    void isPositionNeighbourTest() {
        assertTrue(position.isPositionNeighbour(new Position(1, 2)));
    }

    @Test
    void toStringTest() {
        assertEquals(" (1, 3) ", position.toString());
    }

    @Test
    void equalsTest() {
        assertFalse(position.equals(new Position(2, 3)));

        assertTrue(position.equals(new Position(1, 3)));
        assertTrue(position.equals(position));
    }

}
