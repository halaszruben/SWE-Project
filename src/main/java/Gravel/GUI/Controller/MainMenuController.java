package Gravel.GUI.Controller;

import Gravel.StateOfTheGame.PlayerTurn;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * {@link Class} represents all the main menu controllers with their implementations.
 */
public class MainMenuController {

    /**
     * {@link javafx.scene.control.TextField} represents our first player.
     */
    @FXML
    public javafx.scene.control.TextField firstPlayer;

    /**
     * {@link javafx.scene.control.TextField} represents our second player.
     */
    @FXML
    public javafx.scene.control.TextField secondPlayer;

    /**
     * {@link Label} represents the promptWarning when they cannot give their names properly.
     */
    @FXML
    public Label promptWarning;

    /**
     * Initializes the promptWarning configurations.
     */
    public void initialize() {
        Logger.info("Initializing the Main Menu.");
        promptWarning.setVisible(false);
    }

    /**
     * This {@code setPrompt} sets the correct message to prompt on different occasions.
     *
     * @param message of the promptWarning.
     */
    public void setPrompt(String message) {
        Logger.warn("Warning the Players because they forgot to add proper names! \n" + message);
        promptWarning.setText(message);
        promptWarning.setVisible(true);
    }

    /**
     * The {code startMenu} starts the game menu.
     *
     * @param mouseEvent does what It needs to do when clicked on it.
     * @throws IOException on wrong mouseEvent
     */
    @FXML
    public void startMenu(MouseEvent mouseEvent) throws IOException {
        if (firstPlayer.getText().isEmpty()) {
            setPrompt("Please enter a nickname for the First Player!");
        } else if (secondPlayer.getText().isEmpty()) {
            setPrompt("Please enter a nickname for the Second Player!");
        } else if (secondPlayer.getText().equals(firstPlayer.getText())) {
            setPrompt("You cannot play against yourself, \n" +
                    "that's too hard a match up, even for You!");

        } else {
            Logger.info("Starting the Game Menu!");
            startGame(mouseEvent, "/FXML/GameMenu.fxml");
        }
    }

    /**
     * {@link HashMap<PlayerTurn, String>} gets all the players.
     *
     * @return all of the players.
     */
    private HashMap<PlayerTurn, String> getPlayerNames() {
        var players = new HashMap<PlayerTurn, String>();
        players.put(PlayerTurn.FIRST_PLAYER, firstPlayer.getText());
        players.put(PlayerTurn.SECOND_PLAYER, secondPlayer.getText());

        Logger.info("Returning the Player names: " + players);
        return players;
    }

    /**
     * Starts the game.
     *
     * @param mouseEvent when clicked it does what it's supposed to do, when clikced.
     * @param filename   the name of the file you wanna open.
     * @throws IOException when it fails.
     */
    private void startGame(MouseEvent mouseEvent, String filename) throws IOException {
        String filePath = System.getProperty("user.home") + File.separator + ".results";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(filename));
        Parent root = fxmlLoader.load();
        var controller = (Gravel.GUI.Controller.BoardGameController) fxmlLoader.getController();
        controller.setPlayerNames(getPlayerNames());
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Exits the Main menu as well as the game.
     */
    @FXML
    void exitButton() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Do you really wanna exit the game? -_-");
        var result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            Logger.info("Exiting from the Main Menu!");
            Platform.exit();
        }
    }

}