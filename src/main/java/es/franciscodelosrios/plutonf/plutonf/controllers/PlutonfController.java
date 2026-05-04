package es.franciscodelosrios.plutonf.plutonf.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PlutonfController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
