package Gravel.GUI.Controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.tinylog.Logger;


import java.io.IOException;

/**
 * {@link Class} it contains all the necessary controls to the end of the game.
 */
public class EndGameController {

    /**
     * {@link TextField} it contains the winner of the game.
     */
    @FXML
    public TextField winnerPlayer;

    /**
     * Sets the winnerPlayer to the winner of the game.
     *
     * @param winnerName it contains the winner of the game.
     */
    public void setWinnerName(String winnerName) {
        winnerPlayer.setText(winnerName);
        Logger.info("Setting the game winner name.");
    }

    /**
     * Represents a DataBase of the game, with items stored.
     *
     * @param event when clicked it loads a new scene.
     * @throws IOException when something goes terribly wrong.
     */
    @FXML
    private void highScores(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/ScoresMenu.fxml"));
        Parent root = fxmlLoader.load();
        var controller = (ScoresMenuController) fxmlLoader.getController();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Exits the end menu.
     */
    @FXML
    void exitEndMenuButton() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Thank you GUYS for playing! ;)");
        var result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            Logger.info("Exiting the End Game Menu!");
            Platform.exit();
        }
    }
}
