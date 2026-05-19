package es.franciscodelosrios.plutonf.plutonf.controllers;

import es.franciscodelosrios.plutonf.dao.MantenimientoDAO;
import es.franciscodelosrios.plutonf.model.Mantenimiento;
import es.franciscodelosrios.plutonf.model.Prioridad;
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

public class MantenimientoController {
    @FXML
    private Button btnIntervenciones;

    @FXML
    private TextField txtDescripcionMantenimiento;

    @FXML
    private TextField txtPrioridadMantenimiento;

    @FXML
    private TextField txtCosteMantenimiento;

    @FXML
    private TextField txtHerramientaMantenimiento;

    @FXML
    private TextField txtBuscarIdMantenimiento;

    @FXML
    private TextField txtBuscarDescripcionMantenimiento;

    @FXML
    private Button btnVolverMantenimiento;

    @FXML
    private TableView<Mantenimiento> tablaMantenimientos;

    @FXML
    private TableColumn<Mantenimiento, Integer> colIdMantenimiento;

    @FXML
    private TableColumn<Mantenimiento, Prioridad> colPrioridadMantenimiento;

    @FXML
    private TableColumn<Mantenimiento, Double> colCosteMantenimiento;

    @FXML
    private TableColumn<Mantenimiento, String> colDescripcionMantenimiento;

    @FXML
    private TableColumn<Mantenimiento, String> colDuracionMantenimiento;

    @FXML
    private TableColumn<Mantenimiento, String> colHerramientaMantenimiento;

    @FXML
    private TextField txtDuracionMantenimiento;

    /**
     * Metodo que se inicia la abrir la ventana
     */
    @FXML
    public void initialize() {
        colIdMantenimiento.setCellValueFactory(new PropertyValueFactory<Mantenimiento, Integer>("idMantenimiento"));
        colPrioridadMantenimiento.setCellValueFactory(new PropertyValueFactory<Mantenimiento, Prioridad>("prioridad"));
        colCosteMantenimiento.setCellValueFactory(new PropertyValueFactory<Mantenimiento, Double>("costeRecursos"));
        colDescripcionMantenimiento.setCellValueFactory(new PropertyValueFactory<Mantenimiento, String>("descripcion"));
        colDuracionMantenimiento.setCellValueFactory(new PropertyValueFactory<Mantenimiento, String>("duracionEntidad"));
        colHerramientaMantenimiento.setCellValueFactory(new PropertyValueFactory<Mantenimiento, String>("herramientaNecesaria"));
        cargarMantenimientos();

        // Accedemos al sistema de selección de la tabla
        tablaMantenimientos.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        txtDescripcionMantenimiento.setText(newValue.getDescripcion());
                        txtPrioridadMantenimiento.setText(String.valueOf(newValue.getPrioridad()));
                        txtCosteMantenimiento.setText(String.valueOf(newValue.getCosteRecursos()));
                        txtDuracionMantenimiento.setText(newValue.getDuracionEntidad());
                        txtHerramientaMantenimiento.setText(newValue.getHerramientaNecesaria());
                    }
                });
    }

    /**
     * Limpiamos los campos a rellenar de mantenimiento
     * @param event
     */
    @FXML
    public void limpiarMantenimiento(ActionEvent event) {
        Utils.limpiarCampos(txtDescripcionMantenimiento, txtPrioridadMantenimiento, txtCosteMantenimiento, txtDuracionMantenimiento, txtHerramientaMantenimiento);
    }

    /**
     * Guardamos un objeto mantenimiento en la base de datos
     * @param event
     */
    @FXML
    public void guardarMantenimiento(ActionEvent event) {
        try {
            if (Utils.campoVacio(txtDescripcionMantenimiento) || Utils.campoVacio(txtPrioridadMantenimiento) || Utils.campoVacio(txtCosteMantenimiento) || Utils.campoVacio(txtDuracionMantenimiento) || Utils.campoVacio(txtHerramientaMantenimiento)) {
                Utils.mostrarError("Error", "Debe rellenar todos los campos.");
            } else {
                double coste = Utils.convertirDouble(txtCosteMantenimiento.getText());
                if (coste == -1) {
                    Utils.mostrarError("Error", "El coste debe ser un número.");
                } else {
                    Prioridad prioridad = Prioridad.valueOf(
                            txtPrioridadMantenimiento.getText().toUpperCase()
                    );
                    Mantenimiento mantenimiento = new Mantenimiento(
                            0,
                            txtDescripcionMantenimiento.getText(),
                            prioridad,
                            coste,
                            txtDuracionMantenimiento.getText(),
                            txtHerramientaMantenimiento.getText()
                    );
                    if (MantenimientoDAO.addMantenimiento(mantenimiento)) {
                        Utils.mostrarMensaje("Información", "Mantenimiento guardado correctamente.");
                        limpiarMantenimiento(event);
                        cargarMantenimientos();
                    } else {
                        Utils.mostrarError("Error", "No se ha podido guardar el mantenimiento.");
                    }
                }
            }
        } catch (SQLException e) {
            Utils.mostrarError("Error", "Error al guardar el mantenimiento.");
        } catch (IllegalArgumentException e) {
            Utils.mostrarError("Error", "La prioridad debe ser BAJA, ALTA o CRITICA.");
        }
    }

    /**
     * Mostramos los mantenimientos en la tabla que representa la base de datos
     */
    public void cargarMantenimientos() {
        try {
            tablaMantenimientos.getItems().clear();

            List<Mantenimiento> mantenimientos = MantenimientoDAO.findAll();

            for (int i = 0; i < mantenimientos.size(); i++) {
                tablaMantenimientos.getItems().add(mantenimientos.get(i));
            }

        } catch (SQLException e) {
            Utils.mostrarError("Error", "No se han podido cargar los mantenimientos.");
        }
    }

    /**
     * Eliminamos un objeto mantenimiento de la base de datos
     * @param event
     */
    @FXML
    public void eliminarMantenimiento(ActionEvent event) {
        try {
            Mantenimiento mantenimientoSeleccionado = tablaMantenimientos.getSelectionModel().getSelectedItem();

            if (mantenimientoSeleccionado == null) {
                Utils.mostrarError("Error", "Debe seleccionar un mantenimiento.");
            } else {
                if (MantenimientoDAO.deleteMantenimiento(mantenimientoSeleccionado)) {
                    Utils.mostrarMensaje("Información", "Mantenimiento eliminado correctamente.");
                    cargarMantenimientos();
                } else {
                    Utils.mostrarError("Error", "No se ha podido eliminar el mantenimiento.");
                }
            }
        } catch (SQLException e) {
            Utils.mostrarError("Error", "Error al eliminar el mantenimiento.");
        }
    }

    /**
     * Actualizamos la tabla
     * @param event
     */
    @FXML
    public void actualizarTabla(ActionEvent event) {
        cargarMantenimientos();
    }

    /**
     * Bsucamos un objeto mantenimiento por ID
     * @param event
     */
    @FXML
    public void buscarPorId(ActionEvent event) {
        try {
            int idMantenimiento = Utils.convertirEntero(txtBuscarIdMantenimiento.getText());

            if (idMantenimiento == -1) {
                Utils.mostrarError("Error", "El ID debe ser un número.");
            } else {
                Mantenimiento mantenimiento = MantenimientoDAO.findById(idMantenimiento);

                tablaMantenimientos.getItems().clear();

                if (mantenimiento != null) {
                    tablaMantenimientos.getItems().add(mantenimiento);
                } else {
                    Utils.mostrarError("Error", "No existe un mantenimiento con ese ID.");
                }
            }
        } catch (SQLException e) {
            Utils.mostrarError("Error", "Error al buscar el mantenimiento.");
        }
    }

    /**
     * Buscamos un objeto por su descripcion
     * @param event
     */
    @FXML
    public void buscarPorDescripcion(ActionEvent event) {
        try {
            if (Utils.campoVacio(txtBuscarDescripcionMantenimiento)) {
                Utils.mostrarError("Error", "Debe escribir una descripción.");
            } else {
                Mantenimiento mantenimiento = MantenimientoDAO.findByDescripcion(txtBuscarDescripcionMantenimiento.getText());

                tablaMantenimientos.getItems().clear();

                if (mantenimiento != null) {
                    tablaMantenimientos.getItems().add(mantenimiento);
                } else {
                    Utils.mostrarError("Error", "No existe un mantenimiento con esa descripción.");
                }
            }
        } catch (SQLException e) {
            Utils.mostrarError("Error", "Error al buscar el mantenimiento.");
        }
    }

    /**
     * Boton que usamos para volver al inicio
     * @param event
     * @throws IOException
     */
    @FXML
    public void volverGestionar(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(PlutonfApplication.class.getResource("/es/franciscodelosrios/plutonf/plutonf/gestionar.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) btnVolverMantenimiento.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Abre la ventana de intervenciones
     * @param event
     * @throws IOException
     */
    @FXML
    public void abrirIntervenciones(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                PlutonfApplication.class.getResource(
                        "/es/franciscodelosrios/plutonf/plutonf/intervencionMantenimiento.fxml"
                )
        );
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) btnIntervenciones.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Actualiza el mantenimiento seleccionado en la tabla
     * @param event
     */
    @FXML
    public void actualizarMantenimiento(ActionEvent event) {
        try {
            Mantenimiento mantenimientoSeleccionado = tablaMantenimientos.getSelectionModel().getSelectedItem();

            if (mantenimientoSeleccionado == null) {
                Utils.mostrarError("Error", "Debe seleccionar un mantenimiento.");
            } else if (Utils.campoVacio(txtDescripcionMantenimiento)
                    || Utils.campoVacio(txtPrioridadMantenimiento)
                    || Utils.campoVacio(txtCosteMantenimiento)
                    || Utils.campoVacio(txtDuracionMantenimiento)
                    || Utils.campoVacio(txtHerramientaMantenimiento)) {

                Utils.mostrarError("Error", "Debe rellenar todos los campos.");

            } else {
                double coste = Utils.convertirDouble(txtCosteMantenimiento.getText());

                if (coste == -1) {
                    Utils.mostrarError("Error", "El coste debe ser un número.");
                } else {
                    Prioridad prioridad = Prioridad.valueOf(txtPrioridadMantenimiento.getText().toUpperCase());

                    Mantenimiento mantenimientoNuevo = new Mantenimiento(
                            mantenimientoSeleccionado.getIdMantenimiento(),
                            txtDescripcionMantenimiento.getText(),
                            prioridad,
                            coste,
                            txtDuracionMantenimiento.getText(),
                            txtHerramientaMantenimiento.getText()
                    );

                    if (MantenimientoDAO.updateMantenimiento(mantenimientoNuevo, mantenimientoSeleccionado)) {
                        Utils.mostrarMensaje("Información", "Mantenimiento actualizado correctamente.");
                        limpiarMantenimiento(event);
                        cargarMantenimientos();
                    } else {
                        Utils.mostrarError("Error", "No se ha podido actualizar el mantenimiento.");
                    }
                }
            }

        } catch (SQLException e) {
            Utils.mostrarError("Error", "Error al actualizar el mantenimiento.");
        } catch (IllegalArgumentException e) {
            Utils.mostrarError("Error", "La prioridad debe ser BAJA, ALTA o CRITICA.");
        }
    }
}