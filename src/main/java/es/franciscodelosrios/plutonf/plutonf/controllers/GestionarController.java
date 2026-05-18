package es.franciscodelosrios.plutonf.plutonf.controllers;

import es.franciscodelosrios.plutonf.plutonf.PlutonfApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class GestionarController {

    @FXML
    private Button btnGestionMisiones;

    @FXML
    private Button btnGestionNaves;

    @FXML
    private Button btnGestionModulos;

    @FXML
    private Button btnGestionAstronautas;

    @FXML
    private Button btnGestionMantenimiento;

    @FXML
    private Button btnVolverGestionar;

    @FXML
    private Button btnAbrirRelaciones;


    /**
     * Metodo que abre la ventana de misiones
     * @param event
     * @throws IOException
     */
    @FXML
    public void abrirMision(ActionEvent event) throws IOException {
        cambiarVentana("/es/franciscodelosrios/plutonf/plutonf/mision.fxml", btnGestionMisiones);
    }

    /**
     * Metodo que abre la ventana de naves
     * @param event
     * @throws IOException
     */
    @FXML
    public void abrirNaves(ActionEvent event) throws IOException {
        cambiarVentana("/es/franciscodelosrios/plutonf/plutonf/naves.fxml", btnGestionNaves);
    }

    /**
     * Metodo que abre la ventana de modulos
     * @param event
     * @throws IOException
     */
    @FXML
    public void abrirModulos(ActionEvent event) throws IOException {
        cambiarVentana("/es/franciscodelosrios/plutonf/plutonf/modulos.fxml", btnGestionModulos);
    }

    /**
     * Metodo que abre la ventana de astronautas
     * @param event
     * @throws IOException
     */
    @FXML
    public void abrirAstronautas(ActionEvent event) throws IOException {
        cambiarVentana("/es/franciscodelosrios/plutonf/plutonf/astronauta.fxml", btnGestionAstronautas);
    }

    /**
     * Metodo que abre la ventana de mantenimiento
     * @param event
     * @throws IOException
     */
    @FXML
    public void abrirMantenimiento(ActionEvent event) throws IOException {
        cambiarVentana("/es/franciscodelosrios/plutonf/plutonf/mantenimiento.fxml", btnGestionMantenimiento);
    }

    /**
     * Metodo que vuelve a la ventana de inicio
     * @param event
     * @throws IOException
     */
    @FXML
    public void volverInicio(ActionEvent event) throws IOException {
        cambiarVentana("/es/franciscodelosrios/plutonf/plutonf/inicio.fxml", btnVolverGestionar);
    }

    /**
     * Metodo que cambia de ventana
     * @param ruta ruta del archivo fxml
     * @param boton boton desde el que se obtiene la ventana actual
     * @throws IOException
     */
    private void cambiarVentana(String ruta, Button boton) throws IOException {
        FXMLLoader loader = new FXMLLoader(PlutonfApplication.class.getResource(ruta));
        Scene scene = new Scene(loader.load());

        Stage stage = (Stage) boton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Metodo que muestra información de la ventana gestionar
     * @param event
     */
    @FXML
    public void mostrarInformacionVentana(ActionEvent event) {

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);

        alerta.setTitle("Información");
        alerta.setHeaderText("Ventana Gestionar");

        alerta.setContentText(
                "En esta ventana podrá acceder a la gestión completa del " +
                        "\n sistema. "
                        + "Desde aquí podrá administrar misiones, naves,\n módulos, "
                        + "astronautas y mantenimientos."
        );

        alerta.showAndWait();
    }

    /**
     * Abre la ventana de relaciones
     * @param event
     * @throws IOException
     */
    @FXML
    public void abrirRelaciones(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(PlutonfApplication.class.getResource("/es/franciscodelosrios/plutonf/plutonf/relaciones.fxml"));
        Scene scene = new Scene(loader.load());

        Stage stage = (Stage) btnAbrirRelaciones.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}