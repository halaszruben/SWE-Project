package Gravel.GUI.Controller;

import Gravel.DataBase.ConnectionJdbi;
import Gravel.DataBase.Result;
import Gravel.StateOfTheGame.BoardState;
import Gravel.StateOfTheGame.PlayerTurn;
import Gravel.StateOfTheGame.Position;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;
import java.util.HashMap;


import static javafx.scene.paint.Color.*;

/**
 * {@link Class} contains all the implementations and all the controllers that are necessary to run board/game menu.
 */
public class BoardGameController {

    private BoardState boardState;

    @FXML
    private GridPane gridBoard;

    /**
     * {@link TextField} represents one of the players name.
     */
    @FXML
    public TextField playerName;

    /**
     * {@link HashMap<PlayerTurn, String>} contains all the names.
     */
    HashMap<PlayerTurn, String> playerNames;

    @FXML
    private void initialize() {
        Logger.info("Initializing the Board Game.");
        for (int col = 0; col < gridBoard.getColumnCount(); col++) {
            for (int row = 0; row < gridBoard.getColumnCount(); row++) {
                var squareGravel = squareGravel();
                gridBoard.add(squareGravel, col, row);
            }
        }
        boardState = new BoardState();

    }

    /**
     * Sets all the player names into a {@link HashMap<PlayerTurn, String>} to store the values.
     *
     * @param playerNames represents the player names.
     */
    public void setPlayerNames(HashMap<PlayerTurn, String> playerNames) {
        this.playerNames = playerNames;
        Logger.info("Setting the Player names.");
        playerName.setText(playerNames.get(boardState.getPlayerTurn()));
    }

    private StackPane squareGravel() {
        Logger.info("Putting the Gravels on the Board for the Game grid pane.");
        var squareGravel = new StackPane();
        squareGravel.getStyleClass().add("squareGravel");
        var piece = new Circle(50);
        piece.setFill(DARKGRAY);
        squareGravel.getChildren().add(piece);
        squareGravel.setOnMouseClicked(this::handleMouseClick);
        return squareGravel;
    }

    /**
     * {@code handleMouseClick} it changes the square transparencies, thus making the game work.
     *
     * @param event when clicked on it, it runs the given objective.
     */
    @FXML
    private void handleMouseClick(MouseEvent event) {
        var squareGravel = (StackPane) event.getSource();
        var circle = (Circle) squareGravel.getChildren().get(0);

        var row = GridPane.getRowIndex(squareGravel);
        var col = GridPane.getColumnIndex(squareGravel);
        Position pos = new Position(row, col);

        if (circle.getFill().equals(GREEN)) {
            boardState.removeSelectedPosition(pos);
            circle.setFill(DARKGRAY);
        } else if (circle.getFill().equals(DARKGRAY)) {
            boardState.addSelectedPosition(pos);
            circle.setFill(GREEN);
        } else if (circle.getFill().equals(TRANSPARENT)) {
            circle.setFill(TRANSPARENT);
        }

        Logger.info("Clicking on the Gravel " + boardState.getSelectedPosition() + " and changing their color.");

    }

    /**
     * {@link Node} it makes us able to get a Node from the {@link GridPane}.
     *
     * @param row represents the position of the row.
     * @param col represents the position of the column.
     * @return the acquired node.
     */
    private Node getNodeFromGridPane(int row, int col) {
        Logger.info("Getting the node from row " + row + " and column " + col);
        for (Node node : gridBoard.getChildren()) {
            if (node instanceof StackPane) {
                if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                    return node;
                }
            }
        }

        throw new IllegalArgumentException();
    }

    /**
     * Checks every selection in every order possible if it is a valid selection.
     * With this method help we are able to remove Gravels.
     *
     * @return true if the selection is valid, otherwise return false.
     */
    private boolean isValidSelection() {
        Logger.info("Checking if the selection is valid!");
        if (boardState.getSelectedPosition().size() == 1) {
            return true;
        }

        if (!boardState.isSameRowOrCol()) {
            return false;
        }

        if (boardState.isOnTheSameRow()) {
            int valRow = boardState.getSelectedPosition().get(0).row();

            Integer max = boardState.getSelectedPosition()
                    .stream()
                    .mapToInt(Position::col)
                    .max().getAsInt();

            Integer min = boardState.getSelectedPosition()
                    .stream()
                    .mapToInt(Position::col)
                    .min().getAsInt();

            for (int val = min; val < max; ++val) {
                var squareGravel = (StackPane) getNodeFromGridPane(valRow, val);
                var circle = (Circle) squareGravel.getChildren().get(0);

                if (circle.getFill().equals(TRANSPARENT)) {
                    return false;
                }
            }
        } else if (boardState.isOnTheSameCol()) {
            int valCol = boardState.getSelectedPosition().get(0).col();

            Integer max = boardState.getSelectedPosition()
                    .stream()
                    .mapToInt(Position::row)
                    .max().getAsInt();

            Integer min = boardState.getSelectedPosition()
                    .stream()
                    .mapToInt(Position::row)
                    .min().getAsInt();

            for (int val = min; val < max; ++val) {
                var squareGravel = (StackPane) getNodeFromGridPane(val, valCol);
                var circle = (Circle) squareGravel.getChildren().get(0);

                if (circle.getFill().equals(TRANSPARENT)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Signals the end of the turn, and also checks if we have a winner, and if we do the endgame function is called.
     *
     * @param actionEvent when something is done with it ends the current turn.
     */
    public void endTurnClick(ActionEvent actionEvent) {
        if (!isValidSelection()) {
            Logger.warn("That is an invalid move, try not to cheat, You won't be able to!");
            return;
        }
        Logger.info("Finished move.");
        boardState.nextPlayer();

        for (var position : boardState.getSelectedPosition()) {
            var squareGravel = (StackPane) getNodeFromGridPane(position.row(), position.col());
            var circle = (Circle) squareGravel.getChildren().get(0);
            circle.setFill(TRANSPARENT);
        }

        boardState.resetSelectedPosition();
        playerName.setText(playerNames.get(boardState.getPlayerTurn()));

        if (playerWon()) {
            Logger.info("The game has ended, we have a winner.");
            endGame(actionEvent);
        }

    }

    /**
     * {@return true if all the gravels have been taken down from the board, otherwise it returns false.}
     */
    public boolean playerWon() {

        for (int row = 0; row < 4; ++row) {
            for (int col = 0; col < 4; ++col) {
                var squareGravel = (StackPane) getNodeFromGridPane(row, col);
                var circle = (Circle) squareGravel.getChildren().get(0);
                if (!circle.getFill().equals(TRANSPARENT)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Calls the new scene into action and also inserts the necessary values into the DataBase.
     *
     * @param actionEvent helps us to load the new state of the game into the scene.
     */
    private void endGame(ActionEvent actionEvent) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/EndGameMenu.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
            var controller = (EndGameController) fxmlLoader.getController();
            controller.setWinnerName(playerNames.get(boardState.getPlayerTurn()));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            ConnectionJdbi.insert(new Result(playerNames.get(PlayerTurn.FIRST_PLAYER), playerNames.get(PlayerTurn.SECOND_PLAYER), playerNames.get(boardState.getPlayerTurn())));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Exits the board/game menu, as well as the whole game, but not without sending an {@link Alert} first.
     */
    @FXML
    void exitGameButton() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("-_- Are you absolutely sure about this?");
        var result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            Logger.info("Exiting from the Board Game Menu!");
            Platform.exit();
        }
    }
}