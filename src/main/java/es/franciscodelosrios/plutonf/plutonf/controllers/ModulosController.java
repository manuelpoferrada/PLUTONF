package es.franciscodelosrios.plutonf.plutonf.controllers;

import es.franciscodelosrios.plutonf.dao.ModuloDAO;
import es.franciscodelosrios.plutonf.dao.NaveDAO;
import es.franciscodelosrios.plutonf.model.*;
import es.franciscodelosrios.plutonf.plutonf.PlutonfApplication;
import es.franciscodelosrios.plutonf.plutonf.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ModulosController {

    @FXML
    private TextField txtNombreModulo;

    @FXML
    private TextField txtSectorModulo;

    @FXML
    private TextField txtCapacidadModulo;

    @FXML
    private TextField txtOxigenoModulo;

    @FXML
    private ComboBox<Nave> cbNaveModulo;

    @FXML
    private TextField txtTipoModulo;

    @FXML
    private TextField txtDatoEspecificoModulo;

    @FXML
    private TextField txtBuscarIdModulo;

    @FXML
    private TextField txtBuscarNombreModulo;

    @FXML
    private Button btnVolverModulo;

    @FXML
    private Button btnAbrirDesacoplamiento;

    @FXML
    private TableView<Modulo> tablaModulos;

    @FXML
    private TableColumn<Modulo, Integer> colIdModulo;

    @FXML
    private TableColumn<Modulo, String> colNombreModulo;

    @FXML
    private TableColumn<Modulo, String> colSectorModulo;

    @FXML
    private TableColumn<Modulo, Integer> colCapacidadModulo;

    @FXML
    private TableColumn<Modulo, Double> colOxigenoModulo;

    @FXML
    private TableColumn<Modulo, Double> colTemperaturaModulo;

    @FXML
    private TableColumn<Modulo, Nave> colNaveModulo;

    @FXML
    private TableColumn<Modulo, Integer> colDatoEspecificoModulo;

    /**
     * Metodo que se ejecuta al abrir la ventana
     */
    @FXML
    public void initialize() {
        colIdModulo.setCellValueFactory(new PropertyValueFactory<Modulo, Integer>("idModulo"));
        colNombreModulo.setCellValueFactory(new PropertyValueFactory<Modulo, String>("nombre"));
        colSectorModulo.setCellValueFactory(new PropertyValueFactory<Modulo, String>("sector"));
        colCapacidadModulo.setCellValueFactory(new PropertyValueFactory<Modulo, Integer>("capacidadMaxima"));
        colOxigenoModulo.setCellValueFactory(new PropertyValueFactory<Modulo, Double>("nivelOxigeno"));
        colTemperaturaModulo.setCellValueFactory(new PropertyValueFactory<Modulo, Double>("temperaturaInterior"));
        colNaveModulo.setCellValueFactory(new PropertyValueFactory<Modulo, Nave>("nave"));
        //Configuramos como se rellena la columna
        colDatoEspecificoModulo.setCellValueFactory(cellData -> {
            //Obtengo el modulo de esa tupla
            Modulo modulo = cellData.getValue();

            //CAST transformando un modulo a moduloControl/Labo...
            if (modulo instanceof ModulosControl) {
                return new javafx.beans.property.SimpleIntegerProperty(
                        ((ModulosControl) modulo).getNivelSeguridad()
                ).asObject();

            } else if (modulo instanceof ModulosLaboratorio) {
                return new javafx.beans.property.SimpleIntegerProperty(
                        ((ModulosLaboratorio) modulo).getNumExperimentos()
                ).asObject();

            } else if (modulo instanceof ModulosVivienda) {
                return new javafx.beans.property.SimpleIntegerProperty(
                        ((ModulosVivienda) modulo).getNumCamas()
                ).asObject();
            }

            return null;
        });

        configurarComboBoxNaves();
        cargarNavesComboBox();
        cargarModulos();

        tablaModulos.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        txtNombreModulo.setText(newValue.getNombre());
                        txtSectorModulo.setText(newValue.getSector());
                        txtCapacidadModulo.setText(String.valueOf(newValue.getCapacidadMaxima()));
                        txtOxigenoModulo.setText(String.valueOf(newValue.getNivelOxigeno()));

                        cbNaveModulo.setValue(newValue.getNave());

                        if (newValue instanceof ModulosControl) {
                            txtTipoModulo.setText("CONTROL");
                            txtDatoEspecificoModulo.setText(String.valueOf(((ModulosControl) newValue).getNivelSeguridad()));
                        } else if (newValue instanceof ModulosLaboratorio) {
                            txtTipoModulo.setText("LABORATORIO");
                            txtDatoEspecificoModulo.setText(String.valueOf(((ModulosLaboratorio) newValue).getNumExperimentos()));
                        } else if (newValue instanceof ModulosVivienda) {
                            txtTipoModulo.setText("VIVIENDA");
                            txtDatoEspecificoModulo.setText(String.valueOf(((ModulosVivienda) newValue).getNumCamas()));
                        }
                    }
                });
    }

    /**
     * Configura como se muestran las naves en el ComboBox
     */
    public void configurarComboBoxNaves() {
        cbNaveModulo.setCellFactory(param -> new ListCell<Nave>() {
            @Override
            protected void updateItem(Nave nave, boolean empty) {
                super.updateItem(nave, empty);

                if (empty || nave == null) {
                    setText(null);
                } else {
                    setText(nave.getIdNave() + " - " + nave.getNombre());
                }
            }
        });

        cbNaveModulo.setButtonCell(new ListCell<Nave>() {
            @Override
            protected void updateItem(Nave nave, boolean empty) {
                super.updateItem(nave, empty);

                if (empty || nave == null) {
                    setText(null);
                } else {
                    setText(nave.getIdNave() + " - " + nave.getNombre());
                }
            }
        });
    }

    /**
     * Carga las naves en el ComboBox
     */
    public void cargarNavesComboBox() {
        try {
            cbNaveModulo.getItems().clear();

            List<Nave> naves = NaveDAO.findAll();

            for (int i = 0; i < naves.size(); i++) {
                cbNaveModulo.getItems().add(naves.get(i));
            }

        } catch (SQLException e) {
            Utils.mostrarError("Error", "No se han podido cargar las naves.");
        }
    }

    /**
     * Limpia los campos del formulario
     * @param event
     */
    @FXML
    public void limpiarModulo(ActionEvent event) {
        Utils.limpiarCampos(
                txtNombreModulo,
                txtSectorModulo,
                txtCapacidadModulo,
                txtOxigenoModulo,
                txtTipoModulo,
                txtDatoEspecificoModulo
        );

        cbNaveModulo.setValue(null);
    }

    /**
     * Guardamos un modulo
     * @param event
     */
    @FXML
    public void guardarModulo(ActionEvent event) {

        try {
            if (Utils.campoVacio(txtNombreModulo)
                    || Utils.campoVacio(txtSectorModulo)
                    || Utils.campoVacio(txtCapacidadModulo)
                    || Utils.campoVacio(txtOxigenoModulo)
                    || cbNaveModulo.getValue() == null
                    || Utils.campoVacio(txtTipoModulo)
                    || Utils.campoVacio(txtDatoEspecificoModulo)) {

                Utils.mostrarError("Error", "Debe rellenar todos los campos.");

            } else {

                int capacidad = Utils.convertirEntero(txtCapacidadModulo.getText());
                double oxigeno = Utils.convertirDouble(txtOxigenoModulo.getText());
                int datoEspecifico = Utils.convertirEntero(txtDatoEspecificoModulo.getText());

                if (capacidad == -1 || oxigeno == -1 || datoEspecifico == -1) {

                    Utils.mostrarError("Error", "Capacidad, oxígeno y dato específico deben ser números.");

                } else {

                    Nave nave = cbNaveModulo.getValue();

                    Modulo modulo = null;
                    String tipo = txtTipoModulo.getText();

                    if (tipo.equalsIgnoreCase("CONTROL")) {

                        modulo = new ModulosControl(
                                0,
                                txtNombreModulo.getText(),
                                txtSectorModulo.getText(),
                                capacidad,
                                oxigeno,
                                22,
                                nave,
                                datoEspecifico
                        );

                    } else if (tipo.equalsIgnoreCase("LABORATORIO")) {

                        modulo = new ModulosLaboratorio(
                                0,
                                txtNombreModulo.getText(),
                                txtSectorModulo.getText(),
                                capacidad,
                                oxigeno,
                                22,
                                nave,
                                datoEspecifico
                        );

                    } else if (tipo.equalsIgnoreCase("VIVIENDA")) {

                        modulo = new ModulosVivienda(
                                0,
                                txtNombreModulo.getText(),
                                txtSectorModulo.getText(),
                                capacidad,
                                oxigeno,
                                22,
                                nave,
                                datoEspecifico
                        );

                    } else {

                        Utils.mostrarError("Error", "Tipo de módulo incorrecto.");
                    }

                    if (modulo != null) {

                        if (ModuloDAO.addModulo(modulo)) {

                            Utils.mostrarMensaje("Información", "Módulo guardado correctamente.");
                            limpiarModulo(event);
                            cargarModulos();

                        } else {

                            Utils.mostrarError("Error", "No se ha podido guardar el módulo.");
                        }
                    }
                }
            }

        } catch (SQLException e) {

            Utils.mostrarError("Error", "Error al guardar el módulo.");
        }
    }

    /**
     * Carga todos los modulos en la tabla
     */
    public void cargarModulos() {
        try {
            tablaModulos.getItems().clear();

            List<Modulo> modulos = ModuloDAO.findAll();

            for (int i = 0; i < modulos.size(); i++) {
                tablaModulos.getItems().add(modulos.get(i));
            }

        } catch (SQLException e) {
            Utils.mostrarError("Error", "No se han podido cargar los módulos.");
        }
    }

    /**
     * Elimina el modulo seleccionado
     * @param event
     */
    @FXML
    public void eliminarModulo(ActionEvent event) {
        try {
            Modulo moduloSeleccionado = tablaModulos.getSelectionModel().getSelectedItem();

            if (moduloSeleccionado == null) {
                Utils.mostrarError("Error", "Debe seleccionar un módulo.");
            } else {
                if (ModuloDAO.deleteModulo(moduloSeleccionado)) {
                    Utils.mostrarMensaje("Información", "Módulo eliminado correctamente.");
                    cargarModulos();
                } else {
                    Utils.mostrarError("Error", "No se ha podido eliminar el módulo.");
                }
            }
        } catch (SQLException e) {
            Utils.mostrarError("Error", "Error al eliminar el módulo.");
        }
    }

    /**
     * Actualiza la tabla
     * @param event
     */
    @FXML
    public void actualizarTabla(ActionEvent event) {
        cargarNavesComboBox();
        cargarModulos();
    }

    /**
     * Busca un modulo por ID
     * @param event
     */
    @FXML
    public void buscarPorId(ActionEvent event) {
        try {
            int idModulo = Utils.convertirEntero(txtBuscarIdModulo.getText());

            if (idModulo == -1) {
                Utils.mostrarError("Error", "El ID debe ser un número.");
            } else {
                Modulo modulo = ModuloDAO.findById(idModulo);

                tablaModulos.getItems().clear();

                if (modulo != null) {
                    tablaModulos.getItems().add(modulo);
                } else {
                    Utils.mostrarError("Error", "No existe un módulo con ese ID.");
                }
            }
        } catch (SQLException e) {
            Utils.mostrarError("Error", "Error al buscar el módulo.");
        }
    }

    /**
     * Busca un modulo por nombre
     * @param event
     */
    @FXML
    public void buscarPorNombre(ActionEvent event) {
        try {
            if (Utils.campoVacio(txtBuscarNombreModulo)) {
                Utils.mostrarError("Error", "Debe escribir un nombre.");
            } else {
                Modulo modulo = ModuloDAO.findByName(txtBuscarNombreModulo.getText());

                tablaModulos.getItems().clear();

                if (modulo != null) {
                    tablaModulos.getItems().add(modulo);
                } else {
                    Utils.mostrarError("Error", "No existe un módulo con ese nombre.");
                }
            }
        } catch (SQLException e) {
            Utils.mostrarError("Error", "Error al buscar el módulo.");
        }
    }

    /**
     * Vuelve a la ventana gestionar
     * @param event
     * @throws IOException
     */
    @FXML
    public void volverGestionar(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(PlutonfApplication.class.getResource("/es/franciscodelosrios/plutonf/plutonf/gestionar.fxml"));
        Scene scene = new Scene(loader.load());

        Stage stage = (Stage) btnVolverModulo.getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Abre la ventana de desacoplamiento
     * @param event
     * @throws IOException
     */
    @FXML
    public void abrirDesacoplamiento(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(PlutonfApplication.class.getResource("/es/franciscodelosrios/plutonf/plutonf/desacoplamiento.fxml"));
        Scene scene = new Scene(loader.load());

        Stage stage = (Stage) btnAbrirDesacoplamiento.getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Actualiza el modulo seleccionado en la tabla
     * @param event
     */
    @FXML
    public void actualizarModulo(ActionEvent event) {
        try {
            Modulo moduloSeleccionado = tablaModulos.getSelectionModel().getSelectedItem();

            if (moduloSeleccionado == null) {

                Utils.mostrarError("Error", "Debe seleccionar un módulo.");

            } else if (Utils.campoVacio(txtNombreModulo)
                    || Utils.campoVacio(txtSectorModulo)
                    || Utils.campoVacio(txtCapacidadModulo)
                    || Utils.campoVacio(txtOxigenoModulo)
                    || cbNaveModulo.getValue() == null
                    || Utils.campoVacio(txtTipoModulo)
                    || Utils.campoVacio(txtDatoEspecificoModulo)) {

                Utils.mostrarError("Error", "Debe rellenar todos los campos.");

            } else {

                int capacidad = Utils.convertirEntero(txtCapacidadModulo.getText());
                double oxigeno = Utils.convertirDouble(txtOxigenoModulo.getText());
                int datoEspecifico = Utils.convertirEntero(txtDatoEspecificoModulo.getText());

                if (capacidad == -1 || oxigeno == -1 || datoEspecifico == -1) {

                    Utils.mostrarError("Error", "Capacidad, oxígeno y dato específico deben ser números.");

                } else {

                    Nave nave = cbNaveModulo.getValue();

                    Modulo moduloNuevo = null;
                    String tipo = txtTipoModulo.getText();

                    if (tipo.equalsIgnoreCase("CONTROL")) {

                        moduloNuevo = new ModulosControl(
                                moduloSeleccionado.getIdModulo(),
                                txtNombreModulo.getText(),
                                txtSectorModulo.getText(),
                                capacidad,
                                oxigeno,
                                moduloSeleccionado.getTemperaturaInterior(),
                                nave,
                                datoEspecifico
                        );

                    } else if (tipo.equalsIgnoreCase("LABORATORIO")) {

                        moduloNuevo = new ModulosLaboratorio(
                                moduloSeleccionado.getIdModulo(),
                                txtNombreModulo.getText(),
                                txtSectorModulo.getText(),
                                capacidad,
                                oxigeno,
                                moduloSeleccionado.getTemperaturaInterior(),
                                nave,
                                datoEspecifico
                        );

                    } else if (tipo.equalsIgnoreCase("VIVIENDA")) {

                        moduloNuevo = new ModulosVivienda(
                                moduloSeleccionado.getIdModulo(),
                                txtNombreModulo.getText(),
                                txtSectorModulo.getText(),
                                capacidad,
                                oxigeno,
                                moduloSeleccionado.getTemperaturaInterior(),
                                nave,
                                datoEspecifico
                        );

                    } else {

                        Utils.mostrarError("Error", "Tipo de módulo incorrecto.");
                    }

                    if (moduloNuevo != null) {

                        if (ModuloDAO.updateModulo(moduloNuevo, moduloSeleccionado)) {

                            Utils.mostrarMensaje("Información", "Módulo actualizado correctamente.");
                            limpiarModulo(event);
                            cargarModulos();

                        } else {

                            Utils.mostrarError("Error", "No se ha podido actualizar el módulo.");
                        }
                    }
                }
            }
        } catch (SQLException e) {

            Utils.mostrarError("Error", "Error al actualizar el módulo.");
        }
    }

}