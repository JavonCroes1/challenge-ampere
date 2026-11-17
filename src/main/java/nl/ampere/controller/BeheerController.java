package nl.ampere.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.UncheckedIOException;

/** Scherm Beheer (Melik). De gegevenskoppeling wordt later toegevoegd. */
public class BeheerController {
    @FXML private TextField limiet, weerAan, maxVoorrang;
    @FXML private Label limietFout, weerAanFout, voorrangFout, instellingenSamenvatting;
    @FXML private VBox instellingenBevestiging;
    @FXML private TableView<?> wijzigingenTabel, apparatenTabel;

    @FXML public void initialize() {
        wijzigingenTabel.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        apparatenTabel.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        for (TextField veld : new TextField[]{limiet, weerAan, maxVoorrang}) {
            veld.textProperty().addListener((obs, oud, nieuw) -> zichtbaar(instellingenBevestiging, false));
        }
    }

    @FXML private void controleerInstellingen() {
        fout(limiet, limietFout, null);
        fout(weerAan, weerAanFout, null);
        fout(maxVoorrang, voorrangFout, null);
        Double l = leesGetal(limiet), w = leesGetal(weerAan);
        Integer m = null;
        try { m = Integer.valueOf(maxVoorrang.getText().trim()); } catch (NumberFormatException ignored) {}
        boolean geldig = true;
        if (l == null || l <= 0) { fout(limiet, limietFout, "Moet groter zijn dan 0"); geldig = false; }
        if (w == null || w < 0) { fout(weerAan, weerAanFout, "Vul een getal van 0 of hoger in"); geldig = false; }
        else if (l != null && w >= l) { fout(weerAan, weerAanFout, "Moet lager zijn dan de limiet"); geldig = false; }
        if (m == null || m < 1) { fout(maxVoorrang, voorrangFout, "Vul minimaal 1 heel uur in"); geldig = false; }
        zichtbaar(instellingenBevestiging, false);
        if (!geldig) return;
        instellingenSamenvatting.setText("Limiet: " + limiet.getText().trim() + " kW\n"
            + "Weer aan onder: " + weerAan.getText().trim() + " kW\n"
            + "Max. voorrang: " + m + " uur");
        zichtbaar(instellingenBevestiging, true);
    }

    @FXML private void annuleerInstellingen() { zichtbaar(instellingenBevestiging, false); }

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

    static Double leesGetal(TextField veld) {
        try {
            double waarde = Double.parseDouble(veld.getText().trim().replace(',', '.'));
            return Double.isFinite(waarde) ? waarde : null;
        } catch (NumberFormatException e) { return null; }
    }
    static void zichtbaar(Node node, boolean zichtbaar) { node.setVisible(zichtbaar); node.setManaged(zichtbaar); }
    private static void fout(TextField veld, Label label, String melding) {
        veld.pseudoClassStateChanged(javafx.css.PseudoClass.getPseudoClass("ongeldig"), melding != null);
        label.setText(melding);
        zichtbaar(label, melding != null);
    }
}
