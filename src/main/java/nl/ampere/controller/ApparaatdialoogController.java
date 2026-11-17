package nl.ampere.controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static nl.ampere.controller.BeheerController.zichtbaar;

/** Alleen de bediening van de mock-up; gegevens en opslag volgen later. */
public class ApparaatdialoogController {
    @FXML private TextField naam, volgorde;
    @FXML private ComboBox<String> node;
    @FXML private CheckBox magUit;
    @FXML private VBox verwijderBevestiging;

    @FXML public void initialize() {
        magUit.selectedProperty().addListener((obs, oud, nieuw) -> {
            node.setPromptText(nieuw ? "Kies een node" : "Geen");
            if (!nieuw) { node.setValue(null); volgorde.clear(); }
        });
    }

    @FXML private void vraagVerwijderen() {
        zichtbaar(verwijderBevestiging, true);
        ((Stage) naam.getScene().getWindow()).sizeToScene();
    }

    @FXML private void annuleerVerwijderen() {
        zichtbaar(verwijderBevestiging, false);
        ((Stage) naam.getScene().getWindow()).sizeToScene();
    }

    @FXML private void sluiten() { ((Stage) naam.getScene().getWindow()).close(); }
}
