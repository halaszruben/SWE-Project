package Gravel.StateOfTheGame;

/**
 * {@link Enum} is an enum witch contains the two players.
 */
public enum PlayerTurn {

    /**
     * It's the first player.
     */
    FIRST_PLAYER,

    /**
     * It's the second player.
     */
    SECOND_PLAYER;

    /**
     * The {@code getNextPlayerTurn} it gives us the possibility to change between the players.
     *
     * @return SECOND_PLAYER if this is FIRST_PLAYER otherwise return FIRST_PLAYER.
     */
    public PlayerTurn getNextPlayerTurn() {
        return switch (this) {
            case FIRST_PLAYER -> SECOND_PLAYER;
            case SECOND_PLAYER -> FIRST_PLAYER;
        };
    }
}
