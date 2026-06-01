package es.franciscodelosrios.plutonf.plutonf.controllers;

import es.franciscodelosrios.plutonf.dao.NaveDAO;
import es.franciscodelosrios.plutonf.model.Nave;
import es.franciscodelosrios.plutonf.plutonf.PlutonfApplication;
import es.franciscodelosrios.plutonf.plutonf.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
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

public class NavesController {

    @FXML
    private TextField txtNombreNave;

    @FXML
    private TextField txtAlcanceNave;

    @FXML
    private TextField txtCombustibleNave;

    @FXML
    private DatePicker dpFechaNave;

    @FXML
    private TextField txtEstadoNave;

    @FXML
    private TextField txtBuscarIdNave;

    @FXML
    private TextField txtBuscarNombreNave;

    @FXML
    private Button btnVolverNave;

    @FXML
    private TableView<Nave> tablaNaves;

    @FXML
    private TableColumn<Nave, Integer> colIdNave;

    @FXML
    private TableColumn<Nave, String> colNombreNave;

    @FXML
    private TableColumn<Nave, String> colEstadoNave;

    @FXML
    private TableColumn<Nave, Double> colAlcanceNave;

    @FXML
    private TableColumn<Nave, Double> colCombustibleNave;

    @FXML
    private TableColumn<Nave, Date> colFechaNave;

    /**
     * Metodo que se ejecuta al abrir la ventana
     */
    @FXML
    public void initialize() {
        colIdNave.setCellValueFactory(new PropertyValueFactory<Nave, Integer>("idNave"));
        colNombreNave.setCellValueFactory(new PropertyValueFactory<Nave, String>("nombre"));
        colEstadoNave.setCellValueFactory(new PropertyValueFactory<Nave, String>("estadoNave"));
        colAlcanceNave.setCellValueFactory(new PropertyValueFactory<Nave, Double>("alcance"));
        colCombustibleNave.setCellValueFactory(new PropertyValueFactory<Nave, Double>("combustibleActual"));
        colFechaNave.setCellValueFactory(new PropertyValueFactory<Nave, Date>("fechaLanzamiento"));
        cargarNaves();

        // Accedemos al sistema de selección de la tabla
        tablaNaves.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {

                        txtNombreNave.setText(newValue.getNombre());
                        txtAlcanceNave.setText(String.valueOf(newValue.getAlcance()));
                        txtCombustibleNave.setText(String.valueOf(newValue.getCombustibleActual()));

                        if (newValue.getFechaLanzamiento() != null) {
                            if (newValue.getFechaLanzamiento() instanceof java.sql.Date) {
                                dpFechaNave.setValue(
                                        ((java.sql.Date) newValue.getFechaLanzamiento()).toLocalDate()
                                );
                            } else {
                                dpFechaNave.setValue(
                                        newValue.getFechaLanzamiento()
                                                .toInstant()
                                                .atZone(ZoneId.systemDefault())
                                                .toLocalDate()
                                );
                            }
                        } else {
                            dpFechaNave.setValue(null);
                        }

                        txtEstadoNave.setText(newValue.getEstadoNave());
                    }
                });
    }

    /**
     * Limpia los campos del formulario
     * @param event
     */
    @FXML
    public void limpiarNave(ActionEvent event) {
        Utils.limpiarCampos(txtNombreNave, txtAlcanceNave, txtCombustibleNave, txtEstadoNave);
        dpFechaNave.setValue(null);
    }

    /**
     * Guarda una nueva nave
     * @param event
     */
    @FXML
    public void guardarNave(ActionEvent event) {
        try {
            if (Utils.campoVacio(txtNombreNave) || Utils.campoVacio(txtAlcanceNave) || Utils.campoVacio(txtCombustibleNave) || dpFechaNave.getValue() == null || Utils.campoVacio(txtEstadoNave)) {
                Utils.mostrarError("Error", "Debe rellenar todos los campos.");
            } else {
                double alcance = Utils.convertirDouble(txtAlcanceNave.getText());
                double combustible = Utils.convertirDouble(txtCombustibleNave.getText());

                if (alcance == -1 || combustible == -1) {
                    Utils.mostrarError("Error", "El alcance y el combustible deben ser números.");
                } else {
                    LocalDate localDate = dpFechaNave.getValue();
                    Date fecha = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

                    Nave nave = new Nave(
                            0,
                            txtNombreNave.getText(),
                            alcance,
                            combustible,
                            fecha,
                            txtEstadoNave.getText()
                    );

                    if (NaveDAO.addNave(nave)) {
                        Utils.mostrarMensaje("Información", "Nave guardada correctamente.");
                        limpiarNave(event);
                        cargarNaves();
                    } else {
                        Utils.mostrarError("Error", "No se ha podido guardar la nave.");
                    }
                }
            }
        } catch (SQLException e) {
            Utils.mostrarError("Error", "Error al guardar la nave.");
        }
    }

    /**
     * Carga todas las naves en la tabla
     */
    public void cargarNaves() {
        try {
            tablaNaves.getItems().clear();

            List<Nave> naves = NaveDAO.findAll();

            for (int i = 0; i < naves.size(); i++) {
                tablaNaves.getItems().add(naves.get(i));
            }

        } catch (SQLException e) {
            Utils.mostrarError("Error", "No se han podido cargar las naves.");
        }
    }

    /**
     * Elimina la nave seleccionada
     * @param event
     */
    @FXML
    public void eliminarNave(ActionEvent event) {
        try {
            Nave naveSeleccionada = tablaNaves.getSelectionModel().getSelectedItem();

            if (naveSeleccionada == null) {
                Utils.mostrarError("Error", "Debe seleccionar una nave.");
            } else {
                if (NaveDAO.deleteNave(naveSeleccionada)) {
                    Utils.mostrarMensaje("Información", "Nave eliminada correctamente.");
                    cargarNaves();
                } else {
                    Utils.mostrarError("Error", "No se ha podido eliminar la nave.");
                }
            }
        } catch (SQLException e) {
            Utils.mostrarError("Error", "Error al eliminar la nave.");
        }
    }

    /**
     * Actualiza la tabla
     * @param event
     */
    @FXML
    public void actualizarTabla(ActionEvent event) {
        cargarNaves();
    }

    /**
     * Busca una nave por ID
     * @param event
     */
    @FXML
    public void buscarPorId(ActionEvent event) {
        try {
            int idNave = Utils.convertirEntero(txtBuscarIdNave.getText());

            if (idNave == -1) {
                Utils.mostrarError("Error", "El ID debe ser un número.");
            } else {
                Nave nave = NaveDAO.findById(idNave);

                tablaNaves.getItems().clear();

                if (nave != null) {
                    tablaNaves.getItems().add(nave);
                } else {
                    Utils.mostrarError("Error", "No existe una nave con ese ID.");
                }
            }
        } catch (SQLException e) {
            Utils.mostrarError("Error", "Error al buscar la nave.");
        }
    }

    /**
     * Busca una nave por nombre
     * @param event
     */
    @FXML
    public void buscarPorNombre(ActionEvent event) {
        try {
            if (Utils.campoVacio(txtBuscarNombreNave)) {
                Utils.mostrarError("Error", "Debe escribir un nombre.");
            } else {
                Nave nave = NaveDAO.findByName(txtBuscarNombreNave.getText());

                tablaNaves.getItems().clear();

                if (nave != null) {
                    tablaNaves.getItems().add(nave);
                } else {
                    Utils.mostrarError("Error", "No existe una nave con ese nombre.");
                }
            }
        } catch (SQLException e) {
            Utils.mostrarError("Error", "Error al buscar la nave.");
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

        Stage stage = (Stage) btnVolverNave.getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Actualiza la nave seleccionada en la tabla
     * @param event
     */
    @FXML
    public void actualizarNave(ActionEvent event) {
        try {
            Nave naveSeleccionada = tablaNaves.getSelectionModel().getSelectedItem();

            if (naveSeleccionada == null) {
                Utils.mostrarError("Error", "Debe seleccionar una nave.");
            } else if (Utils.campoVacio(txtNombreNave)
                    || Utils.campoVacio(txtAlcanceNave)
                    || Utils.campoVacio(txtCombustibleNave)
                    || dpFechaNave.getValue() == null
                    || Utils.campoVacio(txtEstadoNave)) {

                Utils.mostrarError("Error", "Debe rellenar todos los campos.");

            } else {
                double alcance = Utils.convertirDouble(txtAlcanceNave.getText());
                double combustible = Utils.convertirDouble(txtCombustibleNave.getText());

                if (alcance == -1 || combustible == -1) {
                    Utils.mostrarError("Error", "El alcance y el combustible deben ser números.");
                } else {
                    LocalDate localDate = dpFechaNave.getValue();
                    Date fecha = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

                    Nave naveNueva = new Nave(
                            naveSeleccionada.getIdNave(),
                            txtNombreNave.getText(),
                            alcance,
                            combustible,
                            fecha,
                            txtEstadoNave.getText()
                    );

                    if (NaveDAO.updateNave(naveNueva, naveSeleccionada)) {
                        Utils.mostrarMensaje("Información", "Nave actualizada correctamente.");
                        limpiarNave(event);
                        cargarNaves();
                    } else {
                        Utils.mostrarError("Error", "No se ha podido actualizar la nave.");
                    }
                }
            }

        } catch (SQLException e) {
            Utils.mostrarError("Error", "Error al actualizar la nave.");
        }
    }

}