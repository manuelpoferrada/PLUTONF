package es.franciscodelosrios.plutonf.plutonf.controllers;

import es.franciscodelosrios.plutonf.dao.AstronautaDAO;
import es.franciscodelosrios.plutonf.dao.IntervencionMantenimientoDAO;
import es.franciscodelosrios.plutonf.dao.MisionDAO;
import es.franciscodelosrios.plutonf.dao.ModuloDAO;
import es.franciscodelosrios.plutonf.model.Astronauta;
import es.franciscodelosrios.plutonf.model.IntervencionMantenimiento;
import es.franciscodelosrios.plutonf.model.Mision;
import es.franciscodelosrios.plutonf.model.Modulo;
import es.franciscodelosrios.plutonf.plutonf.PlutonfApplication;
import es.franciscodelosrios.plutonf.plutonf.utils.Utils;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RelacionesController {

    @FXML
    private Button btnMisionesNave;

    @FXML
    private Button btnModulosNave;

    @FXML
    private Button btnIntervencionesAstronauta;

    @FXML
    private Button btnAstronautasModulo;

    @FXML
    private Button btnVolverConsultas;

    @FXML
    private TableView<Object> tablaConsultas;

    @FXML
    private TableColumn<Object, String> colConsulta1;

    @FXML
    private TableColumn<Object, String> colConsulta2;

    /**
     * Metodo que muestra las misiones y sus naves
     * @param event
     */
    @FXML
    public void mostrarMisionesNave(ActionEvent event) {

        tablaConsultas.getItems().clear();

        colConsulta1.setText("Nave");
        colConsulta2.setText("Planeta");

        colConsulta1.setCellValueFactory(cellData -> {
            Mision mision = (Mision) cellData.getValue();

            if (mision.getNave() != null) {
                return new SimpleStringProperty(mision.getNave().getNombre());
            }

            return new SimpleStringProperty("Sin nave");
        });

        colConsulta2.setCellValueFactory(cellData -> {
            Mision mision = (Mision) cellData.getValue();
            return new SimpleStringProperty(mision.getNombrePlaneta());
        });

        try {

            List<Mision> misiones = MisionDAO.findAll();

            for (int i = 0; i < misiones.size(); i++) {
                tablaConsultas.getItems().add(misiones.get(i));
            }

        } catch (SQLException e) {
            Utils.mostrarError("Error", "No se han podido cargar las misiones.");
        }
    }

    /**
     * Metodo que muestra los modulos y sus naves
     * @param event
     */
    @FXML
    public void mostrarModulosNave(ActionEvent event) {

        tablaConsultas.getItems().clear();

        colConsulta1.setText("Módulo");
        colConsulta2.setText("Nave");

        colConsulta1.setCellValueFactory(cellData -> {
            Modulo modulo = (Modulo) cellData.getValue();
            return new SimpleStringProperty(modulo.getNombre());
        });

        colConsulta2.setCellValueFactory(cellData -> {
            Modulo modulo = (Modulo) cellData.getValue();

            if (modulo.getNave() != null) {
                return new SimpleStringProperty(modulo.getNave().getNombre());
            }

            return new SimpleStringProperty("Sin nave");
        });

        try {

            List<Modulo> modulos = ModuloDAO.findAll();

            for (int i = 0; i < modulos.size(); i++) {
                tablaConsultas.getItems().add(modulos.get(i));
            }

        } catch (SQLException e) {
            Utils.mostrarError("Error", "No se han podido cargar los módulos.");
        }
    }

    /**
     * Metodo que muestra los astronautas y sus modulos
     * @param event
     */
    @FXML
    public void mostrarAstronautasModulo(ActionEvent event) {

        tablaConsultas.getItems().clear();

        colConsulta1.setText("Astronauta");
        colConsulta2.setText("Módulo");

        colConsulta1.setCellValueFactory(cellData -> {
            Astronauta astronauta = (Astronauta) cellData.getValue();
            return new SimpleStringProperty(astronauta.getNombre());
        });

        colConsulta2.setCellValueFactory(cellData -> {
            Astronauta astronauta = (Astronauta) cellData.getValue();

            if (astronauta.getModulo() != null) {
                return new SimpleStringProperty(astronauta.getModulo().getNombre());
            }

            return new SimpleStringProperty("Sin módulo");
        });

        try {

            List<Astronauta> astronautas = AstronautaDAO.findAll();

            for (int i = 0; i < astronautas.size(); i++) {
                tablaConsultas.getItems().add(astronautas.get(i));
            }

        } catch (SQLException e) {
            Utils.mostrarError("Error", "No se han podido cargar los astronautas.");
        }
    }

    /**
     * Metodo que muestra las intervenciones y sus astronautas
     * @param event
     */
    @FXML
    public void mostrarIntervencionesAstronauta(ActionEvent event) {

        tablaConsultas.getItems().clear();

        colConsulta1.setText("Astronauta");
        colConsulta2.setText("Observación");

        colConsulta1.setCellValueFactory(cellData -> {
            IntervencionMantenimiento intervencion = (IntervencionMantenimiento) cellData.getValue();

            if (intervencion.getAstronauta() != null) {
                return new SimpleStringProperty(intervencion.getAstronauta().getNombre());
            }

            return new SimpleStringProperty("Sin astronauta");
        });

        colConsulta2.setCellValueFactory(cellData -> {
            IntervencionMantenimiento intervencion = (IntervencionMantenimiento) cellData.getValue();
            return new SimpleStringProperty(intervencion.getObservaciones());
        });

        try {

            List<IntervencionMantenimiento> intervenciones = IntervencionMantenimientoDAO.findAll();

            for (int i = 0; i < intervenciones.size(); i++) {
                tablaConsultas.getItems().add(intervenciones.get(i));
            }

        } catch (SQLException e) {
            Utils.mostrarError("Error", "No se han podido cargar las intervenciones.");
        }
    }

    /**
     * Vuelve a la ventana de inicio
     * @param event
     * @throws IOException
     */
    @FXML
    public void volverGestionar(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(PlutonfApplication.class.getResource("/es/franciscodelosrios/plutonf/plutonf/gestionar.fxml"));
        Scene scene = new Scene(loader.load());

        Stage stage = (Stage) btnVolverConsultas.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}