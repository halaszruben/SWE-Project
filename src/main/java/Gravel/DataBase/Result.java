package Gravel.DataBase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * Has the lombok annotation in it, which helps us get the players names.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result extends ArrayList<Result> {

    /**
     * Represents the first player of the game.
     */
    String firstPlayer;

    /**
     * Represents the second player of the game.
     */
    String secondPlayer;

    /**
     * Represents the winner of the game.
     */
    String winnerPlayer;
}
