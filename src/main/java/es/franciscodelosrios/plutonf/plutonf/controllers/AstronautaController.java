package es.franciscodelosrios.plutonf.plutonf.controllers;

import es.franciscodelosrios.plutonf.dao.AstronautaDAO;
import es.franciscodelosrios.plutonf.dao.ModuloDAO;
import es.franciscodelosrios.plutonf.model.Astronauta;
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
import java.util.List;

public class AstronautaController {

    @FXML
    private TextField txtDniAstronauta;

    @FXML
    private TextField txtNombreAstronauta;

    @FXML
    private TextField txtHabilidadAstronauta;

    @FXML
    private TextField txtRangoAstronauta;

    @FXML
    private TextField txtIdModuloAstronauta;

    @FXML
    private TextField txtBuscarIdAstronauta;

    @FXML
    private Button btnVolverAstronauta;

    @FXML
    private TableView<Astronauta> tablaAstronautas;

    @FXML
    private TableColumn<Astronauta, Integer> colIdAstronauta;

    @FXML
    private TableColumn<Astronauta, String> colNombreAstronauta;

    @FXML
    private TableColumn<Astronauta, String> colRangoAstronauta;

    @FXML
    private TableColumn<Astronauta, String> colDniAstronauta;

    @FXML
    private TableColumn<Astronauta, String> colHabilidadAstronauta;

    /**
     * Metodo que se ejecuta al abrir la ventana
     */
    @FXML
    public void initialize() {
        colIdAstronauta.setCellValueFactory(new PropertyValueFactory<Astronauta, Integer>("idAstronauta"));
        colNombreAstronauta.setCellValueFactory(new PropertyValueFactory<Astronauta, String>("nombre"));
        colRangoAstronauta.setCellValueFactory(new PropertyValueFactory<Astronauta, String>("rango"));
        colDniAstronauta.setCellValueFactory(new PropertyValueFactory<Astronauta, String>("dni"));
        colHabilidadAstronauta.setCellValueFactory(new PropertyValueFactory<Astronauta, String>("habilidadPrincipal"));
        cargarAstronautas();

        // Accedemos al sistema de selección de la tabla
        tablaAstronautas.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        txtDniAstronauta.setText(newValue.getDni());
                        txtNombreAstronauta.setText(newValue.getNombre());
                        txtHabilidadAstronauta.setText(newValue.getHabilidadPrincipal());
                        txtRangoAstronauta.setText(newValue.getRango());
                        txtIdModuloAstronauta.setText(
                                String.valueOf(newValue.getModulo().getIdModulo())
                        );
                    }
                }
        );
    }

    /**
     * Limpia los campos del formulario
     * @param event
     */
    @FXML
    public void limpiarAstronauta(ActionEvent event) {
        Utils.limpiarCampos(txtDniAstronauta, txtNombreAstronauta, txtHabilidadAstronauta, txtRangoAstronauta, txtIdModuloAstronauta);
    }

    /**
     * Guarda un nuevo astronauta
     * @param event
     */
    @FXML
    public void guardarAstronauta(ActionEvent event) {
        try {
            if (Utils.campoVacio(txtDniAstronauta) || Utils.campoVacio(txtNombreAstronauta) || Utils.campoVacio(txtHabilidadAstronauta) || Utils.campoVacio(txtRangoAstronauta) || Utils.campoVacio(txtIdModuloAstronauta)) {
                Utils.mostrarError("Error", "Debe rellenar todos los campos.");
            } else {
                int idModulo = Utils.convertirEntero(txtIdModuloAstronauta.getText());

                if (idModulo == -1) {
                    Utils.mostrarError("Error", "El ID del módulo debe ser un número.");
                } else {
                    Modulo modulo = ModuloDAO.findById(idModulo);

                    if (modulo == null) {
                        Utils.mostrarError("Error", "No existe un módulo con ese ID.");
                    } else {
                        Astronauta astronauta = new Astronauta(
                                0,
                                txtDniAstronauta.getText(),
                                txtNombreAstronauta.getText(),
                                txtHabilidadAstronauta.getText(),
                                txtRangoAstronauta.getText(),
                                0,
                                0,
                                "",
                                modulo
                        );

                        if (AstronautaDAO.addAstronauta(astronauta)) {
                            Utils.mostrarMensaje("Información", "Astronauta guardado correctamente.");
                            limpiarAstronauta(event);
                            cargarAstronautas();
                        } else {
                            Utils.mostrarError("Error", "No se ha podido guardar el astronauta.");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            Utils.mostrarError("Error", "Error al guardar el astronauta.");
        }
    }

    /**
     * Carga todos los astronautas en la tabla
     */
    public void cargarAstronautas() {
        try {
            tablaAstronautas.getItems().clear();

            List<Astronauta> astronautas = AstronautaDAO.findAll();

            for (int i = 0; i < astronautas.size(); i++) {
                tablaAstronautas.getItems().add(astronautas.get(i));
            }

        } catch (SQLException e) {
            Utils.mostrarError("Error", "No se han podido cargar los astronautas.");
        }
    }

    /**
     * Elimina el astronauta seleccionado
     * @param event
     */
    @FXML
    public void eliminarAstronauta(ActionEvent event) {
        try {
            Astronauta astronautaSeleccionado = tablaAstronautas.getSelectionModel().getSelectedItem();

            if (astronautaSeleccionado == null) {
                Utils.mostrarError("Error", "Debe seleccionar un astronauta.");
            } else {
                if (AstronautaDAO.deleteAstronauta(astronautaSeleccionado)) {
                    Utils.mostrarMensaje("Información", "Astronauta eliminado correctamente.");
                    cargarAstronautas();
                } else {
                    Utils.mostrarError("Error", "No se ha podido eliminar el astronauta.");
                }
            }
        } catch (SQLException e) {
            Utils.mostrarError("Error", "Error al eliminar el astronauta.");
        }
    }

    /**
     * Actualiza la tabla
     * @param event
     */
    @FXML
    public void actualizarTabla(ActionEvent event) {
        cargarAstronautas();
    }

    /**
     * Busca un astronauta por ID
     * @param event
     */
    @FXML
    public void buscarPorId(ActionEvent event) {
        try {
            int idAstronauta = Utils.convertirEntero(txtBuscarIdAstronauta.getText());

            if (idAstronauta == -1) {
                Utils.mostrarError("Error", "El ID debe ser un número.");
            } else {
                Astronauta astronauta = AstronautaDAO.findById(idAstronauta);

                tablaAstronautas.getItems().clear();

                if (astronauta != null) {
                    tablaAstronautas.getItems().add(astronauta);
                } else {
                    Utils.mostrarError("Error", "No existe un astronauta con ese ID.");
                }
            }
        } catch (SQLException e) {
            Utils.mostrarError("Error", "Error al buscar el astronauta.");
        }
    }

    /**
     * Vuelve a la ventana de inicio
     * @param event
     * @throws IOException
     */
    @FXML
    public void volverGesionar(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(PlutonfApplication.class.getResource("/es/franciscodelosrios/plutonf/plutonf/gestionar.fxml"));
        Scene scene = new Scene(loader.load());

        Stage stage = (Stage) btnVolverAstronauta.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Actualiza el astronauta seleccionado en la tabla
     * @param event
     */
    @FXML
    public void actualizarAstronauta(ActionEvent event) {
        try {
            Astronauta astronautaSeleccionado = tablaAstronautas.getSelectionModel().getSelectedItem();

            if (astronautaSeleccionado == null) {
                Utils.mostrarError("Error", "Debe seleccionar un astronauta.");
            } else if (Utils.campoVacio(txtDniAstronauta) || Utils.campoVacio(txtNombreAstronauta) || Utils.campoVacio(txtHabilidadAstronauta) || Utils.campoVacio(txtRangoAstronauta) || Utils.campoVacio(txtIdModuloAstronauta)) {
                Utils.mostrarError("Error", "Debe rellenar todos los campos.");
            } else {
                int idModulo = Utils.convertirEntero(txtIdModuloAstronauta.getText());

                if (idModulo == -1) {
                    Utils.mostrarError("Error", "El ID del módulo debe ser un número.");
                } else {
                    Modulo modulo = ModuloDAO.findById(idModulo);

                    if (modulo == null) {
                        Utils.mostrarError("Error", "No existe un módulo con ese ID.");
                    } else {
                        Astronauta astronautaNuevo = new Astronauta(
                                astronautaSeleccionado.getIdAstronauta(),
                                txtDniAstronauta.getText(),
                                txtNombreAstronauta.getText(),
                                txtHabilidadAstronauta.getText(),
                                txtRangoAstronauta.getText(),
                                astronautaSeleccionado.getEdad(),
                                astronautaSeleccionado.getHorasVuelo(),
                                astronautaSeleccionado.getNacionalidad(),
                                modulo
                        );

                        if (AstronautaDAO.updateAstronauta(astronautaNuevo, astronautaSeleccionado)) {
                            Utils.mostrarMensaje("Información", "Astronauta actualizado correctamente.");
                            limpiarAstronauta(event);
                            cargarAstronautas();
                        } else {
                            Utils.mostrarError("Error", "No se ha podido actualizar el astronauta.");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            Utils.mostrarError("Error", "Error al actualizar el astronauta.");
        }
    }
}