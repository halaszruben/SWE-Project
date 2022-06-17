package Gravel.GUI.View;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * With this class you're able to launch the JavaFX.
 */
public class BoardGameApplication extends Application {

    /**
     * {@code start} you are able to start the "Gravel Game".
     *
     * @param stage this one decides which scene to load first.
     * @throws IOException if the input is wrong;
     */
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/MainMenu.fxml"));
        stage.setTitle("Gravel Game");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}