package Gravel.GUI.Controller;

import java.util.List;

import Gravel.DataBase.ConnectionJdbi;
import Gravel.DataBase.Result;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.tinylog.Logger;


/**
 * It contains only the necessary controllers and implementations to the main menu.
 */
public class ScoresMenuController {
    @FXML
    private TableView tableView;

    @FXML
    private TableColumn<Result, String> firstPlayer;

    @FXML
    private TableColumn<Result, String> secondPlayer;

    @FXML
    private TableColumn<Result, String> winnerPlayer;

    @FXML
    private void initialize() {
        Logger.info("Initializing the Scores Menu.");
        firstPlayer.setCellValueFactory(new PropertyValueFactory<>("firstPlayer"));
        secondPlayer.setCellValueFactory(new PropertyValueFactory<>("secondPlayer"));
        winnerPlayer.setCellValueFactory(new PropertyValueFactory<>("winnerPlayer"));
        List<Result> results = ConnectionJdbi.lister();
        ObservableList<Result> observableList = FXCollections.observableArrayList();
        observableList.addAll(results);
        tableView.setItems(observableList);
    }

    /**
     * With this {@link Button} you are able to exit the scores menu.
     */
    @FXML
    void exitScoreButton() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Thank you fro playing! ;)");
        var result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            Logger.info("Exiting from the ScoresMenu");
            Platform.exit();
        }
    }

}

