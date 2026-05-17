package es.franciscodelosrios.plutonf.plutonf.controllers;

import es.franciscodelosrios.plutonf.dao.AstronautaDAO;
import es.franciscodelosrios.plutonf.dao.IntervencionMantenimientoDAO;
import es.franciscodelosrios.plutonf.dao.MantenimientoDAO;
import es.franciscodelosrios.plutonf.dao.ModuloDAO;
import es.franciscodelosrios.plutonf.model.Astronauta;
import es.franciscodelosrios.plutonf.model.IntervencionMantenimiento;
import es.franciscodelosrios.plutonf.model.Mantenimiento;
import es.franciscodelosrios.plutonf.model.Modulo;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class IntervencionMantenimientoController {

    @FXML
    private TextField txtIdMantenimientoIntervencion;

    @FXML
    private TextField txtIdAstronautaIntervencion;

    @FXML
    private TextField txtIdModuloIntervencion;

    @FXML
    private TextField txtFechaIntervencion;

    @FXML
    private TextField txtObservacionesIntervencion;

    @FXML
    private TextField txtBuscarIdIntervencion;

    @FXML
    private Button btnVolverIntervencion;

    @FXML
    private TableView<IntervencionMantenimiento> tablaIntervenciones;

    @FXML
    private TableColumn<IntervencionMantenimiento, Integer> colIdIntervencion;

    @FXML
    private TableColumn<IntervencionMantenimiento, Date> colFechaIntervencion;

    @FXML
    private TableColumn<IntervencionMantenimiento, String> colObservacionesIntervencion;

    @FXML
    private TableColumn<IntervencionMantenimiento, Mantenimiento> colMantenimientoIntervencion;

    @FXML
    private TableColumn<IntervencionMantenimiento, Astronauta> colAstronautaIntervencion;

    @FXML
    private TableColumn<IntervencionMantenimiento, Modulo> colModuloIntervencion;

    /**
     * Metodo que se ejecuta al abrir la ventana
     */
    @FXML
    public void initialize() {
        colIdIntervencion.setCellValueFactory(new PropertyValueFactory<IntervencionMantenimiento, Integer>("idIntervencion"));
        colFechaIntervencion.setCellValueFactory(new PropertyValueFactory<IntervencionMantenimiento, Date>("fechaIntervencion"));
        colObservacionesIntervencion.setCellValueFactory(new PropertyValueFactory<IntervencionMantenimiento, String>("observaciones"));
        colMantenimientoIntervencion.setCellValueFactory(new PropertyValueFactory<IntervencionMantenimiento, Mantenimiento>("mantenimiento"));
        colAstronautaIntervencion.setCellValueFactory(new PropertyValueFactory<IntervencionMantenimiento, Astronauta>("astronauta"));
        colModuloIntervencion.setCellValueFactory(new PropertyValueFactory<IntervencionMantenimiento, Modulo>("modulo"));
        cargarIntervenciones();
    }

    /**
     * Limpiamos los campos del formulario
     * @param event
     */
    @FXML
    public void limpiarIntervencion(ActionEvent event) {
        Utils.limpiarCampos(
                txtIdMantenimientoIntervencion,
                txtIdAstronautaIntervencion,
                txtIdModuloIntervencion,
                txtFechaIntervencion,
                txtObservacionesIntervencion
        );
    }

    /**
     * Guardamos una nueva intervencion de mantenimiento
     * @param event
     */
    @FXML
    public void guardarIntervencion(ActionEvent event) {
        try {
            if (Utils.campoVacio(txtIdMantenimientoIntervencion)
                    || Utils.campoVacio(txtIdAstronautaIntervencion)
                    || Utils.campoVacio(txtIdModuloIntervencion)
                    || Utils.campoVacio(txtFechaIntervencion)
                    || Utils.campoVacio(txtObservacionesIntervencion)) {

                Utils.mostrarError("Error", "Debe rellenar todos los campos.");

            } else {
                int idMantenimiento = Utils.convertirEntero(txtIdMantenimientoIntervencion.getText());
                int idAstronauta = Utils.convertirEntero(txtIdAstronautaIntervencion.getText());
                int idModulo = Utils.convertirEntero(txtIdModuloIntervencion.getText());

                if (idMantenimiento == -1 || idAstronauta == -1 || idModulo == -1) {
                    Utils.mostrarError("Error", "Los ID deben ser números.");
                } else {
                    Mantenimiento mantenimiento = MantenimientoDAO.findById(idMantenimiento);
                    Astronauta astronauta = AstronautaDAO.findById(idAstronauta);
                    Modulo modulo = ModuloDAO.findById(idModulo);

                    if (mantenimiento == null) {
                        Utils.mostrarError("Error", "No existe un mantenimiento con ese ID.");
                    } else if (astronauta == null) {
                        Utils.mostrarError("Error", "No existe un astronauta con ese ID.");
                    } else if (modulo == null) {
                        Utils.mostrarError("Error", "No existe un módulo con ese ID.");
                    } else {
                        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                        Date fecha = formato.parse(txtFechaIntervencion.getText());

                        IntervencionMantenimiento intervencion = new IntervencionMantenimiento(
                                0,
                                mantenimiento,
                                astronauta,
                                modulo,
                                fecha,
                                txtObservacionesIntervencion.getText()
                        );

                        if (IntervencionMantenimientoDAO.addIntervencion(intervencion)) {
                            Utils.mostrarMensaje("Información", "Intervención guardada correctamente.");
                            limpiarIntervencion(event);
                            cargarIntervenciones();
                        } else {
                            Utils.mostrarError("Error", "No se ha podido guardar la intervención.");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            Utils.mostrarError("Error", "Error al guardar la intervención.");
        } catch (ParseException e) {
            Utils.mostrarError("Error", "La fecha debe tener el formato yyyy-MM-dd.");
        }
    }

    /**
     * Cargamos todas las intervenciones en la tabla
     */
    public void cargarIntervenciones() {
        try {
            tablaIntervenciones.getItems().clear();

            List<IntervencionMantenimiento> intervenciones = IntervencionMantenimientoDAO.findAll();

            for (int i = 0; i < intervenciones.size(); i++) {
                tablaIntervenciones.getItems().add(intervenciones.get(i));
            }

        } catch (SQLException e) {
            Utils.mostrarError("Error", "No se han podido cargar las intervenciones.");
        }
    }

    /**
     * Eliminamos la intervencion seleccionada
     * @param event
     */
    @FXML
    public void eliminarIntervencion(ActionEvent event) {
        try {
            IntervencionMantenimiento intervencionSeleccionada = tablaIntervenciones.getSelectionModel().getSelectedItem();

            if (intervencionSeleccionada == null) {
                Utils.mostrarError("Error", "Debe seleccionar una intervención.");
            } else {
                if (IntervencionMantenimientoDAO.deleteIntervencion(intervencionSeleccionada)) {
                    Utils.mostrarMensaje("Información", "Intervención eliminada correctamente.");
                    cargarIntervenciones();
                } else {
                    Utils.mostrarError("Error", "No se ha podido eliminar la intervención.");
                }
            }
        } catch (SQLException e) {
            Utils.mostrarError("Error", "Error al eliminar la intervención.");
        }
    }

    /**
     * Actualizamos la tabla
     * @param event
     */
    @FXML
    public void actualizarTabla(ActionEvent event) {
        cargarIntervenciones();
    }

    /**
     * Buscamos una intervencion por ID
     * @param event
     */
    @FXML
    public void buscarPorId(ActionEvent event) {
        try {
            int idIntervencion = Utils.convertirEntero(txtBuscarIdIntervencion.getText());

            if (idIntervencion == -1) {
                Utils.mostrarError("Error", "El ID debe ser un número.");
            } else {
                IntervencionMantenimiento intervencion = IntervencionMantenimientoDAO.findById(idIntervencion);

                tablaIntervenciones.getItems().clear();

                if (intervencion != null) {
                    tablaIntervenciones.getItems().add(intervencion);
                } else {
                    Utils.mostrarError("Error", "No existe una intervención con ese ID.");
                }
            }
        } catch (SQLException e) {
            Utils.mostrarError("Error", "Error al buscar la intervención.");
        }
    }

    /**
     * Volvemos a la ventana de inicio
     * @param event
     * @throws IOException
     */
    @FXML
    public void volverMantenimiento(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(PlutonfApplication.class.getResource("/es/franciscodelosrios/plutonf/plutonf/mantenimiento.fxml"));
        Scene scene = new Scene(loader.load());

        Stage stage = (Stage) btnVolverIntervencion.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}