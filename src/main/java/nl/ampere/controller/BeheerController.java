package nl.ampere.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.UncheckedIOException;

/** Scherm Beheer (Melik). De gegevenskoppeling wordt later toegevoegd. */
public class BeheerController {
    @FXML private TableView<?> wijzigingenTabel, apparatenTabel;

    @FXML public void initialize() {
        wijzigingenTabel.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        apparatenTabel.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
    }

    @FXML private void voegApparaatToe() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/nl/ampere/view/apparaatdialoog.fxml"));
            Parent inhoud = loader.load();
            Stage venster = new Stage();
            venster.initOwner(apparatenTabel.getScene().getWindow());
            venster.initModality(Modality.WINDOW_MODAL);
            venster.setTitle("Apparaat toevoegen");
            Scene scene = new Scene(inhoud);
            scene.getStylesheets().add(getClass().getResource("/nl/ampere/css/style.css").toExternalForm());
            venster.setScene(scene);
            venster.setResizable(false);
            venster.showAndWait();
        } catch (IOException e) { throw new UncheckedIOException("Kan apparaatdialoog niet laden", e); }
    }

    static void zichtbaar(Node node, boolean zichtbaar) { node.setVisible(zichtbaar); node.setManaged(zichtbaar); }
}
