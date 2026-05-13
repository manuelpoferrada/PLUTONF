package es.franciscodelosrios.plutonf.plutonf.controllers;

import es.franciscodelosrios.plutonf.plutonf.PlutonfApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

public class InicioController {

    @FXML
    private MenuItem itemVersion;

    @FXML
    private MenuItem itemSalir;

    @FXML
    private javafx.scene.control.Button btnCrearMisionInicio;

    @FXML
    private javafx.scene.control.Button btnGestionarInicio;

    @FXML
    private javafx.scene.control.Button btnSalirInicio;

    /**
     * Metodo que muestra la version del programa
     * @param event
     */
    @FXML
    public void mostrarVersion(ActionEvent event) {
        javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);

        alerta.setTitle("Versión");
        alerta.setHeaderText(null);
        alerta.setContentText("Esta es la versión 1.0 de PLUTONF");

        alerta.showAndWait();
    }

    /**
     * Metodo que abre la ventana de crear misión
     * @param event
     * @throws IOException
     */
    @FXML
    public void abrirCrearMision(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(PlutonfApplication.class.getResource("/es/franciscodelosrios/plutonf/crearMision.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) btnCrearMisionInicio.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Metodo que abre la ventana gestionar
     * @param event
     * @throws IOException
     */
    @FXML
    public void abrirGestionar(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(PlutonfApplication.class.getResource("/es/franciscodelosrios/plutonf/gestionar.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) btnGestionarInicio.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Metodo que cierra el programa
     * @param event
     */
    @FXML
    public void cerrarPrograma(ActionEvent event) {
        Stage stage = (Stage) btnSalirInicio.getScene().getWindow();
        stage.close();
    }
}