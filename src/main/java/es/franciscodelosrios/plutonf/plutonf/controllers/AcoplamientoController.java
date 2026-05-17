package es.franciscodelosrios.plutonf.plutonf.controllers;

import es.franciscodelosrios.plutonf.dao.ModuloDAO;
import es.franciscodelosrios.plutonf.dao.NaveDAO;
import es.franciscodelosrios.plutonf.model.Modulo;
import es.franciscodelosrios.plutonf.model.ModulosLaboratorio;
import es.franciscodelosrios.plutonf.model.Nave;
import es.franciscodelosrios.plutonf.plutonf.PlutonfApplication;
import es.franciscodelosrios.plutonf.plutonf.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AcoplamientoController {

    @FXML
    private TableView<Modulo> tablaModulosAcoplamiento;

    @FXML
    private TableColumn<Modulo, Integer> colIdModuloAcoplamiento;

    @FXML
    private TableColumn<Modulo, String> colNombreModuloAcoplamiento;

    @FXML
    private TableColumn<Modulo, String> colSectorModuloAcoplamiento;

    @FXML
    private TableView<Nave> tablaNavesAcoplamiento;

    @FXML
    private TableColumn<Nave, Integer> colIdNaveAcoplamiento;

    @FXML
    private TableColumn<Nave, String> colNombreNaveAcoplamiento;

    @FXML
    private TableColumn<Nave, String> colEstadoNaveAcoplamiento;

    @FXML
    private Button btnVolverAcoplamiento;

    /**
     * Iniciamos las tablas al abrir la pestaña
     */
    @FXML
    public void initialize() {
        colIdModuloAcoplamiento.setCellValueFactory(new PropertyValueFactory<Modulo, Integer>("idModulo"));
        colNombreModuloAcoplamiento.setCellValueFactory(new PropertyValueFactory<Modulo, String>("nombre"));
        colSectorModuloAcoplamiento.setCellValueFactory(new PropertyValueFactory<Modulo, String>("sector"));

        colIdNaveAcoplamiento.setCellValueFactory(new PropertyValueFactory<Nave, Integer>("idNave"));
        colNombreNaveAcoplamiento.setCellValueFactory(new PropertyValueFactory<Nave, String>("nombre"));
        colEstadoNaveAcoplamiento.setCellValueFactory(new PropertyValueFactory<Nave, String>("estadoNave"));

        cargarModulosLaboratorio();
        cargarNaves();
    }

    /**
     * Carga en la tabla solo los modulos de laboratorio ya que son los unicos acoplables
     */
    public void cargarModulosLaboratorio() {
        try {
            tablaModulosAcoplamiento.getItems().clear();
            List<Modulo> modulos = ModuloDAO.findAll();
            for (int i = 0; i < modulos.size(); i++) {
                if (modulos.get(i) instanceof ModulosLaboratorio) {
                    tablaModulosAcoplamiento.getItems().add(modulos.get(i));
                }
            }
        } catch (SQLException e) {
            Utils.mostrarError("Error", "No se han podido cargar los módulos de laboratorio.");
        }
    }

    /**
     * Carga todas las naves en la tabla
     */
    public void cargarNaves() {
        try {
            tablaNavesAcoplamiento.getItems().clear();
            List<Nave> naves = NaveDAO.findAll();
            for (int i = 0; i < naves.size(); i++) {
                tablaNavesAcoplamiento.getItems().add(naves.get(i));
            }
        } catch (SQLException e) {
            Utils.mostrarError("Error", "No se han podido cargar las naves.");
        }
    }

    /**
     * Acopla un modulo de laboratorio a una nave
     * @param event
     */
    @FXML
    public void acoplarModulo(ActionEvent event) {
        try {
            Modulo moduloSeleccionado = tablaModulosAcoplamiento.getSelectionModel().getSelectedItem();
            Nave naveSeleccionada = tablaNavesAcoplamiento.getSelectionModel().getSelectedItem();
            if (moduloSeleccionado == null) {
                Utils.mostrarError("Error", "Debe seleccionar un módulo.");
            } else if (naveSeleccionada == null) {
                Utils.mostrarError("Error", "Debe seleccionar una nave.");
            } else {
                ModulosLaboratorio moduloLaboratorio = (ModulosLaboratorio) moduloSeleccionado;
                Modulo moduloActual = ModuloDAO.findById(moduloLaboratorio.getIdModulo());
                if (moduloLaboratorio.acoplarA(naveSeleccionada)) {
                    if (ModuloDAO.updateModulo(moduloLaboratorio, moduloActual)) {
                        Utils.mostrarMensaje("Información", "Módulo acoplado correctamente.");
                        cargarModulosLaboratorio();
                    } else {
                        Utils.mostrarError("Error", "No se ha podido actualizar el módulo.");
                    }
                } else {
                    Utils.mostrarError("Error", "No se ha podido acoplar el módulo.");
                }
            }
        } catch (SQLException e) {
            Utils.mostrarError("Error", "Error al acoplar el módulo.");
        }
    }

    /**
     * Desacopla un modulo de laboratorio de su nave actual
     * @param event
     */
    @FXML
    public void desacoplarModulo(ActionEvent event) {
        try {
            Modulo moduloSeleccionado = tablaModulosAcoplamiento.getSelectionModel().getSelectedItem();

            if (moduloSeleccionado == null) {
                Utils.mostrarError("Error", "Debe seleccionar un módulo.");
            } else {
                ModulosLaboratorio moduloLaboratorio = (ModulosLaboratorio) moduloSeleccionado;

                if (moduloLaboratorio.getNave() == null) {
                    Utils.mostrarError("Error", "Este módulo no está acoplado a ninguna nave.");
                } else {
                    Modulo moduloActual = ModuloDAO.findById(moduloLaboratorio.getIdModulo());

                    Nave naveActual = moduloLaboratorio.getNave();

                    if (moduloLaboratorio.desacoplar(naveActual)) {
                        if (ModuloDAO.updateModulo(moduloLaboratorio, moduloActual)) {
                            Utils.mostrarMensaje("Información", "Módulo desacoplado correctamente.");
                            cargarModulosLaboratorio();
                        } else {
                            Utils.mostrarError("Error", "No se ha podido actualizar el módulo.");
                        }
                    } else {
                        Utils.mostrarError("Error", "No se ha podido desacoplar el módulo.");
                    }
                }
            }
        } catch (SQLException e) {
            Utils.mostrarError("Error", "Error al desacoplar el módulo.");
        }
    }

    /**
     * Vuelve a la ventana de modulos
     * @param event
     * @throws IOException
     */
    @FXML
    public void volverModulos(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(PlutonfApplication.class.getResource("/es/franciscodelosrios/plutonf/plutonf/modulos.fxml"));
        Scene scene = new Scene(loader.load());

        Stage stage = (Stage) btnVolverAcoplamiento.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}