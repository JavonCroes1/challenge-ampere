package nl.ampere;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Startpunt van Ampère: opent het hoofdvenster met de stijl uit style.css.
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Tot het inloggen af is, start de app direct in het hoofdvenster.
        FXMLLoader loader = new FXMLLoader(App.class.getResource("view/hoofdvenster.fxml"));
        Scene scene = new Scene(loader.load(), 1280, 800);
        scene.getStylesheets().add(App.class.getResource("css/style.css").toExternalForm());

        stage.setTitle("Ampère");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
