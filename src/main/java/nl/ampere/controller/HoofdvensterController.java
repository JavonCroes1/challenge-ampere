package nl.ampere.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

/**
 * Hoofdvenster: menubalk bovenin, het gekozen scherm in het midden.
 */
public class HoofdvensterController {

    @FXML
    private BorderPane hoofdvenster;

    @FXML
    private Button dashboardKnop;

    @FXML
    private Button beslissingenKnop;

    @FXML
    private Button beheerKnop;

    @FXML
    public void initialize() {
        toonDashboard();
    }

    @FXML
    private void toonDashboard() {
        toonScherm("dashboard.fxml", dashboardKnop);
    }

    @FXML
    private void toonBeslissingen() {
        toonScherm("beslissingen.fxml", beslissingenKnop);
    }

    @FXML
    private void toonBeheer() {
        toonScherm("beheer.fxml", beheerKnop);
    }

    /** Laadt een scherm uit de map view in het midden en markeert de bijbehorende knop. */
    private void toonScherm(String fxmlBestand, Button actieveKnop) {
        try {
            Parent scherm = FXMLLoader.load(getClass().getResource("/nl/ampere/view/" + fxmlBestand));
            hoofdvenster.setCenter(scherm);
        } catch (IOException e) {
            throw new UncheckedIOException("Kan scherm niet laden: " + fxmlBestand, e);
        }

        for (Button knop : List.of(dashboardKnop, beslissingenKnop, beheerKnop)) {
            knop.getStyleClass().remove("actief");
        }
        actieveKnop.getStyleClass().add("actief");
    }
}
