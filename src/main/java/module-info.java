module nl.ampere {
    requires javafx.controls;
    requires javafx.fxml;

    // FXML mag de controllers aanmaken en hun @FXML-velden invullen.
    opens nl.ampere.controller to javafx.fxml;

    // JavaFX moet de klasse App kunnen starten.
    exports nl.ampere;
}
