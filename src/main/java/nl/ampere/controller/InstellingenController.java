package nl.ampere.controller;

import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.Locale;

import static nl.ampere.controller.BeheerController.zichtbaar;

/** Controle en bevestiging van ingevoerde instellingen, zonder gegevensbron of opslag. */
public class InstellingenController {
    @FXML private TextField limiet, weerAan, maxVoorrang;
    @FXML private Label limietFout, weerAanFout, voorrangFout;
    @FXML private Label oudeLimiet, nieuweLimiet, oudeWeerAan, nieuweWeerAan;
    @FXML private Label oudeVoorrang, nieuweVoorrang, instellingenStatus;
    @FXML private VBox instellingenBevestiging;

    // Alleen formulierstatus, geen NepData of permanente opslag.
    private record Waarden(double limiet, double weerAan, int maxVoorrang) {}
    private Waarden bevestigd;
    private Waarden voorstel;

    @FXML public void initialize() {
        luister(limiet, limietFout);
        luister(weerAan, weerAanFout);
        luister(maxVoorrang, voorrangFout);
    }

    private void luister(TextField veld, Label fout) {
        veld.textProperty().addListener((obs, oud, nieuw) -> {
            voorstel = null;
            zichtbaar(instellingenBevestiging, false);
            zichtbaar(instellingenStatus, false);
            fout(veld, fout, null);
        });
    }

    @FXML private void controleerInstellingen() {
        voorstel = null;
        zichtbaar(instellingenBevestiging, false);
        zichtbaar(instellingenStatus, false);
        fout(limiet, limietFout, null);
        fout(weerAan, weerAanFout, null);
        fout(maxVoorrang, voorrangFout, null);

        Double grens = leesGetal(limiet);
        Double aan = leesGetal(weerAan);
        Integer uren = null;
        try { uren = Integer.valueOf(maxVoorrang.getText().trim()); }
        catch (NumberFormatException ignored) { }

        boolean geldig = true;
        if (grens == null || grens <= 0) {
            fout(limiet, limietFout, "Vul een limiet groter dan 0 in");
            geldig = false;
        }
        if (aan == null || aan < 0) {
            fout(weerAan, weerAanFout, "Vul een getal van 0 of hoger in");
            geldig = false;
        } else if (grens != null && aan >= grens) {
            fout(weerAan, weerAanFout, "Moet lager zijn dan de limiet");
            geldig = false;
        }
        if (uren == null || uren < 1) {
            fout(maxVoorrang, voorrangFout, "Vul minimaal 1 heel uur in");
            geldig = false;
        }
        if (!geldig) return;

        voorstel = new Waarden(grens, aan, uren);
        oudeLimiet.setText(bevestigd == null ? "—" : getal(bevestigd.limiet()));
        oudeWeerAan.setText(bevestigd == null ? "—" : getal(bevestigd.weerAan()));
        oudeVoorrang.setText(bevestigd == null ? "—" : Integer.toString(bevestigd.maxVoorrang()));
        nieuweLimiet.setText(getal(grens));
        nieuweWeerAan.setText(getal(aan));
        nieuweVoorrang.setText(Integer.toString(uren));
        zichtbaar(instellingenBevestiging, true);
    }

    @FXML private void bevestigInstellingen() {
        if (voorstel == null) return;
        bevestigd = voorstel;
        voorstel = null;
        zichtbaar(instellingenBevestiging, false);
        zichtbaar(instellingenStatus, true);
    }

    @FXML private void annuleerInstellingen() {
        voorstel = null;
        limiet.setText(bevestigd == null ? "" : getal(bevestigd.limiet()));
        weerAan.setText(bevestigd == null ? "" : getal(bevestigd.weerAan()));
        maxVoorrang.setText(bevestigd == null ? "" : Integer.toString(bevestigd.maxVoorrang()));
        zichtbaar(instellingenBevestiging, false);
        zichtbaar(instellingenStatus, false);
    }

    private static Double leesGetal(TextField veld) {
        try {
            double getal = Double.parseDouble(veld.getText().trim().replace(',', '.'));
            return Double.isFinite(getal) ? getal : null;
        } catch (NumberFormatException e) { return null; }
    }

    private static String getal(double waarde) {
        return String.format(Locale.forLanguageTag("nl-NL"), "%s", waarde).replace('.', ',');
    }

    private static void fout(TextField veld, Label label, String melding) {
        veld.pseudoClassStateChanged(PseudoClass.getPseudoClass("ongeldig"), melding != null);
        if (melding != null) label.setText(melding);
        zichtbaar(label, melding != null);
    }
}
