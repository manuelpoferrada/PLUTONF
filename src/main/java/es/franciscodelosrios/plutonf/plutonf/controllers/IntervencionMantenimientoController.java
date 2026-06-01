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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class IntervencionMantenimientoController {

    @FXML
    private ComboBox<Mantenimiento> cbMantenimientoIntervencion;

    @FXML
    private ComboBox<Astronauta> cbAstronautaIntervencion;

    @FXML
    private ComboBox<Modulo> cbModuloIntervencion;

    @FXML
    private DatePicker dpFechaIntervencion;

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

        configurarComboBoxMantenimientos();
        configurarComboBoxAstronautas();
        configurarComboBoxModulos();

        cargarMantenimientosComboBox();
        cargarAstronautasComboBox();
        cargarModulosComboBox();

        cargarIntervenciones();

        // Accedemos al sistema de selección de la tabla
        tablaIntervenciones.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {

                        cbMantenimientoIntervencion.setValue(newValue.getMantenimiento());
                        cbAstronautaIntervencion.setValue(newValue.getAstronauta());
                        cbModuloIntervencion.setValue(newValue.getModulo());

                        if (newValue.getFechaIntervencion() != null) {
                            if (newValue.getFechaIntervencion() instanceof java.sql.Date) {
                                dpFechaIntervencion.setValue(
                                        ((java.sql.Date) newValue.getFechaIntervencion()).toLocalDate()
                                );
                            } else {
                                dpFechaIntervencion.setValue(
                                        newValue.getFechaIntervencion()
                                                .toInstant()
                                                .atZone(ZoneId.systemDefault())
                                                .toLocalDate()
                                );
                            }
                        } else {
                            dpFechaIntervencion.setValue(null);
                        }

                        txtObservacionesIntervencion.setText(newValue.getObservaciones());
                    }
                });
    }

    /**
     * Configura como se muestran los mantenimientos en el ComboBox
     */
    public void configurarComboBoxMantenimientos() {
        cbMantenimientoIntervencion.setCellFactory(param -> new ListCell<Mantenimiento>() {
            @Override
            protected void updateItem(Mantenimiento mantenimiento, boolean empty) {
                super.updateItem(mantenimiento, empty);

                if (empty || mantenimiento == null) {
                    setText(null);
                } else {
                    setText(mantenimiento.getIdMantenimiento() + " - " + mantenimiento.getDescripcion());
                }
            }
        });

        cbMantenimientoIntervencion.setButtonCell(new ListCell<Mantenimiento>() {
            @Override
            protected void updateItem(Mantenimiento mantenimiento, boolean empty) {
                super.updateItem(mantenimiento, empty);

                if (empty || mantenimiento == null) {
                    setText(null);
                } else {
                    setText(mantenimiento.getIdMantenimiento() + " - " + mantenimiento.getDescripcion());
                }
            }
        });
    }

    /**
     * Configura como se muestran los astronautas en el ComboBox
     */
    public void configurarComboBoxAstronautas() {
        cbAstronautaIntervencion.setCellFactory(param -> new ListCell<Astronauta>() {
            @Override
            protected void updateItem(Astronauta astronauta, boolean empty) {
                super.updateItem(astronauta, empty);

                if (empty || astronauta == null) {
                    setText(null);
                } else {
                    setText(astronauta.getIdAstronauta() + " - " + astronauta.getNombre());
                }
            }
        });

        cbAstronautaIntervencion.setButtonCell(new ListCell<Astronauta>() {
            @Override
            protected void updateItem(Astronauta astronauta, boolean empty) {
                super.updateItem(astronauta, empty);

                if (empty || astronauta == null) {
                    setText(null);
                } else {
                    setText(astronauta.getIdAstronauta() + " - " + astronauta.getNombre());
                }
            }
        });
    }

    /**
     * Configura como se muestran los modulos en el ComboBox
     */
    public void configurarComboBoxModulos() {
        cbModuloIntervencion.setCellFactory(param -> new ListCell<Modulo>() {
            @Override
            protected void updateItem(Modulo modulo, boolean empty) {
                super.updateItem(modulo, empty);

                if (empty || modulo == null) {
                    setText(null);
                } else {
                    setText(modulo.getIdModulo() + " - " + modulo.getNombre());
                }
            }
        });

        cbModuloIntervencion.setButtonCell(new ListCell<Modulo>() {
            @Override
            protected void updateItem(Modulo modulo, boolean empty) {
                super.updateItem(modulo, empty);

                if (empty || modulo == null) {
                    setText(null);
                } else {
                    setText(modulo.getIdModulo() + " - " + modulo.getNombre());
                }
            }
        });
    }

    /**
     * Carga los mantenimientos en el ComboBox
     */
    public void cargarMantenimientosComboBox() {
        try {
            cbMantenimientoIntervencion.getItems().clear();

            List<Mantenimiento> mantenimientos = MantenimientoDAO.findAll();

            for (int i = 0; i < mantenimientos.size(); i++) {
                cbMantenimientoIntervencion.getItems().add(mantenimientos.get(i));
            }

        } catch (SQLException e) {
            Utils.mostrarError("Error", "No se han podido cargar los mantenimientos.");
        }
    }

    /**
     * Carga los astronautas en el ComboBox
     */
    public void cargarAstronautasComboBox() {
        try {
            cbAstronautaIntervencion.getItems().clear();

            List<Astronauta> astronautas = AstronautaDAO.findAll();

            for (int i = 0; i < astronautas.size(); i++) {
                cbAstronautaIntervencion.getItems().add(astronautas.get(i));
            }

        } catch (SQLException e) {
            Utils.mostrarError("Error", "No se han podido cargar los astronautas.");
        }
    }

    /**
     * Carga los modulos en el ComboBox
     */
    public void cargarModulosComboBox() {
        try {
            cbModuloIntervencion.getItems().clear();

            List<Modulo> modulos = ModuloDAO.findAll();

            for (int i = 0; i < modulos.size(); i++) {
                cbModuloIntervencion.getItems().add(modulos.get(i));
            }

        } catch (SQLException e) {
            Utils.mostrarError("Error", "No se han podido cargar los módulos.");
        }
    }

    /**
     * Limpiamos los campos del formulario
     * @param event
     */
    @FXML
    public void limpiarIntervencion(ActionEvent event) {
        Utils.limpiarCampos(
                txtObservacionesIntervencion
        );

        cbMantenimientoIntervencion.setValue(null);
        cbAstronautaIntervencion.setValue(null);
        cbModuloIntervencion.setValue(null);
        dpFechaIntervencion.setValue(null);
    }

    /**
     * Guardamos una nueva intervencion de mantenimiento
     * @param event
     */
    @FXML
    public void guardarIntervencion(ActionEvent event) {
        try {
            if (cbMantenimientoIntervencion.getValue() == null
                    || cbAstronautaIntervencion.getValue() == null
                    || cbModuloIntervencion.getValue() == null
                    || dpFechaIntervencion.getValue() == null
                    || Utils.campoVacio(txtObservacionesIntervencion)) {

                Utils.mostrarError("Error", "Debe rellenar todos los campos.");

            } else {
                Mantenimiento mantenimiento = cbMantenimientoIntervencion.getValue();
                Astronauta astronauta = cbAstronautaIntervencion.getValue();
                Modulo modulo = cbModuloIntervencion.getValue();

                LocalDate localDate = dpFechaIntervencion.getValue();
                Date fecha = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

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
        } catch (SQLException e) {
            Utils.mostrarError("Error", "Error al guardar la intervención.");
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
        cargarMantenimientosComboBox();
        cargarAstronautasComboBox();
        cargarModulosComboBox();
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
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Actualiza la intervencion seleccionada en la tabla
     * @param event
     */
    @FXML
    public void actualizarIntervencion(ActionEvent event) {
        try {
            IntervencionMantenimiento intervencionSeleccionada = tablaIntervenciones.getSelectionModel().getSelectedItem();

            if (intervencionSeleccionada == null) {
                Utils.mostrarError("Error", "Debe seleccionar una intervención.");
            } else if (cbMantenimientoIntervencion.getValue() == null
                    || cbAstronautaIntervencion.getValue() == null
                    || cbModuloIntervencion.getValue() == null
                    || dpFechaIntervencion.getValue() == null
                    || Utils.campoVacio(txtObservacionesIntervencion)) {

                Utils.mostrarError("Error", "Debe rellenar todos los campos.");

            } else {
                Mantenimiento mantenimiento = cbMantenimientoIntervencion.getValue();
                Astronauta astronauta = cbAstronautaIntervencion.getValue();
                Modulo modulo = cbModuloIntervencion.getValue();

                LocalDate localDate = dpFechaIntervencion.getValue();
                Date fecha = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

                IntervencionMantenimiento intervencionNueva = new IntervencionMantenimiento(
                        intervencionSeleccionada.getIdIntervencion(),
                        mantenimiento,
                        astronauta,
                        modulo,
                        fecha,
                        txtObservacionesIntervencion.getText()
                );

                if (IntervencionMantenimientoDAO.updateIntervencion(intervencionNueva, intervencionSeleccionada)) {
                    Utils.mostrarMensaje("Información", "Intervención actualizada correctamente.");
                    limpiarIntervencion(event);
                    cargarIntervenciones();
                } else {
                    Utils.mostrarError("Error", "No se ha podido actualizar la intervención.");
                }
            }

        } catch (SQLException e) {
            Utils.mostrarError("Error", "Error al actualizar la intervención.");
        }
    }
}
