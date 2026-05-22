package es.franciscodelosrios.plutonf.plutonf.controllers;

import es.franciscodelosrios.plutonf.dao.MisionDAO;
import es.franciscodelosrios.plutonf.dao.NaveDAO;
import es.franciscodelosrios.plutonf.model.Mision;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MisionController {

    @FXML
    private TextField txtIdNaveMision;

    @FXML
    private TextField txtPlanetaMision;

    @FXML
    private TextField txtObjetivoMision;

    @FXML
    private TextField txtBuscarIdMision;

    @FXML
    private TextField txtBuscarPlanetaMision;

    @FXML
    private Button btnVolverMision;

    @FXML
    private TableView<Mision> tablaMisiones;

    @FXML
    private TableColumn<Mision, Integer> colIdMision;

    @FXML
    private TableColumn<Mision, String> colPlanetaMision;

    @FXML
    private TableColumn<Mision, String> colObjetivoMision;

    @FXML
    private TableColumn<Mision, Nave> colIdNaveMision;

    /**
     * Metodo que se ejecuta al abrir la ventana
     */
    @FXML
    public void initialize() {
        colIdMision.setCellValueFactory(new PropertyValueFactory<Mision, Integer>("idMision"));
        colPlanetaMision.setCellValueFactory(new PropertyValueFactory<Mision, String>("nombrePlaneta"));
        colObjetivoMision.setCellValueFactory(new PropertyValueFactory<Mision, String>("objetivo"));
        colIdNaveMision.setCellValueFactory(new PropertyValueFactory<Mision, Nave>("nave"));
        cargarMisiones();

        // Accedemos al sistema de selección de la tabla
        tablaMisiones.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        txtPlanetaMision.setText(newValue.getNombrePlaneta());
                        txtObjetivoMision.setText(newValue.getObjetivo());
                        txtIdNaveMision.setText(
                                String.valueOf(newValue.getNave().getIdNave())
                        );
                    }
                });
    }

    /**
     * Limpiamos los campos del formulario
     * @param event
     */
    @FXML
    public void limpiarMision(ActionEvent event) {
        Utils.limpiarCampos(txtIdNaveMision, txtPlanetaMision, txtObjetivoMision);
    }

    /**
     * Guarda una nueva mision en la base de datos
     * @param event
     */
    @FXML
    public void guardarMision(ActionEvent event) {
        try {
            if (Utils.campoVacio(txtIdNaveMision) || Utils.campoVacio(txtPlanetaMision) || Utils.campoVacio(txtObjetivoMision)) {
                Utils.mostrarError("Error", "Debe rellenar todos los campos.");
            } else {
                int idNave = Utils.convertirEntero(txtIdNaveMision.getText());
                if (idNave == -1) {
                    Utils.mostrarError("Error", "El ID de la nave debe ser un número.");
                } else {
                    Nave nave = NaveDAO.findById(idNave);
                    if (nave == null) {
                        Utils.mostrarError("Error", "No existe una nave con ese ID.");
                    } else {
                        Mision mision = new Mision(0, txtPlanetaMision.getText(), txtObjetivoMision.getText(), nave);

                        if (MisionDAO.addMision(mision)) {
                            Utils.mostrarMensaje("Información", "Misión guardada correctamente.");
                            limpiarMision(event);
                            cargarMisiones();
                        } else {
                            Utils.mostrarError("Error", "No se ha podido guardar la misión.");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            Utils.mostrarError("Error", "Error al guardar la misión.");
        }
    }

    /**
     * Carga todas las misiones en la tabla
     */
    public void cargarMisiones() {
        try {
            tablaMisiones.getItems().clear();
            List<Mision> misiones = MisionDAO.findAll();
            for (int i = 0; i < misiones.size(); i++) {
                tablaMisiones.getItems().add(misiones.get(i));
            }
        } catch (SQLException e) {
            Utils.mostrarError("Error", "No se han podido cargar las misiones.");
        }
    }

    /**
     * Elimina la mision seleccionada en la tabla
     * @param event
     */
    @FXML
    public void eliminarMision(ActionEvent event) {
        try {
            Mision misionSeleccionada = tablaMisiones.getSelectionModel().getSelectedItem();
            if (misionSeleccionada == null) {
                Utils.mostrarError("Error", "Debe seleccionar una misión.");
            } else {
                if (MisionDAO.deleteMision(misionSeleccionada)) {
                    Utils.mostrarMensaje("Información", "Misión eliminada correctamente.");
                    cargarMisiones();
                } else {
                    Utils.mostrarError("Error", "No se ha podido eliminar la misión.");
                }
            }
        } catch (SQLException e) {
            Utils.mostrarError("Error", "Error al eliminar la misión.");
        }
    }

    /**
     * Actualizamos la tabla
     * @param event
     */
    @FXML
    public void actualizarTabla(ActionEvent event) {
        cargarMisiones();
    }

    /**
     * Busca una mision por ID
     * @param event
     */
    @FXML
    public void buscarPorId(ActionEvent event) {
        try {
            int idMision = Utils.convertirEntero(txtBuscarIdMision.getText());
            if (idMision == -1) {
                Utils.mostrarError("Error", "El ID debe ser un número.");
            } else {
                Mision mision = MisionDAO.findById(idMision);
                tablaMisiones.getItems().clear();
                if (mision != null) {
                    tablaMisiones.getItems().add(mision);
                } else {
                    Utils.mostrarError("Error", "No existe una misión con ese ID.");
                }
            }
        } catch (SQLException e) {
            Utils.mostrarError("Error", "Error al buscar la misión.");
        }
    }

    /**
     * Busca misiones por planeta
     * @param event
     */
    @FXML
    public void buscarPorPlaneta(ActionEvent event) {

        try {

            if (Utils.campoVacio(txtBuscarPlanetaMision)) {

                Utils.mostrarError("Error", "Debe escribir un planeta.");

            } else {

                List<Mision> misiones = MisionDAO.findAllByPlaneta(txtBuscarPlanetaMision.getText());

                tablaMisiones.getItems().clear();

                if (!misiones.isEmpty()) {

                    for (int i = 0; i < misiones.size(); i++) {
                        tablaMisiones.getItems().add(misiones.get(i));
                    }

                } else {

                    Utils.mostrarError("Error", "No existen misiones para ese planeta.");
                }
            }

        } catch (SQLException e) {

            Utils.mostrarError("Error", "Error al buscar las misiones.");
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
        Stage stage = (Stage) btnVolverMision.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Actualiza la misión seleccionada en la tabla
     * @param event
     */
    @FXML
    public void actualizarMision(ActionEvent event) {
        try {
            Mision misionSeleccionada = tablaMisiones.getSelectionModel().getSelectedItem();

            if (misionSeleccionada == null) {
                Utils.mostrarError("Error", "Debe seleccionar una misión.");
            } else if (Utils.campoVacio(txtIdNaveMision)
                    || Utils.campoVacio(txtPlanetaMision)
                    || Utils.campoVacio(txtObjetivoMision)) {

                Utils.mostrarError("Error", "Debe rellenar todos los campos.");

            } else {
                int idNave = Utils.convertirEntero(txtIdNaveMision.getText());

                if (idNave == -1) {
                    Utils.mostrarError("Error", "El ID de la nave debe ser un número.");
                } else {
                    Nave nave = NaveDAO.findById(idNave);

                    if (nave == null) {
                        Utils.mostrarError("Error", "No existe una nave con ese ID.");
                    } else {
                        Mision misionNueva = new Mision(
                                misionSeleccionada.getIdMision(),
                                txtPlanetaMision.getText(),
                                txtObjetivoMision.getText(),
                                nave
                        );

                        if (MisionDAO.updateMision(misionNueva, misionSeleccionada)) {
                            Utils.mostrarMensaje("Información", "Misión actualizada correctamente.");
                            limpiarMision(event);
                            cargarMisiones();
                        } else {
                            Utils.mostrarError("Error", "No se ha podido actualizar la misión.");
                        }
                    }
                }
            }

        } catch (SQLException e) {
            Utils.mostrarError("Error", "Error al actualizar la misión.");
        }
    }
}