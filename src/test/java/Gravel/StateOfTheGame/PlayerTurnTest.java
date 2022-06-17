package Gravel.StateOfTheGame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerTurnTest {

    PlayerTurn firstPlayer;

    PlayerTurn secondPlayer;

    @BeforeEach
    void init() {
        firstPlayer = PlayerTurn.FIRST_PLAYER;
        secondPlayer = PlayerTurn.SECOND_PLAYER;
    }

    @Test
    void getNextPlayerTurnTest() {
        this.firstPlayer = this.firstPlayer.getNextPlayerTurn();
        this.secondPlayer = this.secondPlayer.getNextPlayerTurn();
    }

}
