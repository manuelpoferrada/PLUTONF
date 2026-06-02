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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    @FXML
    private TableColumn<Object, String> colConsulta3;

    /**
     * Clase interna sencilla para poder mostrar datos relacionados en la tabla.
     */
    public static class ConsultaRelacion {

        private String dato1;
        private String dato2;
        private String dato3;

        public ConsultaRelacion(String dato1, String dato2, String dato3) {
            this.dato1 = dato1;
            this.dato2 = dato2;
            this.dato3 = dato3;
        }

        public String getDato1() {
            return dato1;
        }

        public String getDato2() {
            return dato2;
        }

        public String getDato3() {
            return dato3;
        }
    }

    /**
     * Prepara la tabla para mostrar datos de tipo ConsultaRelacion.
     */
    public void prepararTabla() {
        tablaConsultas.getItems().clear();

        colConsulta1.setCellValueFactory(cellData -> {
            ConsultaRelacion consulta = (ConsultaRelacion) cellData.getValue();
            return new SimpleStringProperty(consulta.getDato1());
        });

        colConsulta2.setCellValueFactory(cellData -> {
            ConsultaRelacion consulta = (ConsultaRelacion) cellData.getValue();
            return new SimpleStringProperty(consulta.getDato2());
        });

        colConsulta3.setCellValueFactory(cellData -> {
            ConsultaRelacion consulta = (ConsultaRelacion) cellData.getValue();
            return new SimpleStringProperty(consulta.getDato3());
        });
    }

    /**
     * Metodo que muestra las misiones y sus naves
     * @param event
     */
    @FXML
    public void mostrarMisionesNave(ActionEvent event) {

        prepararTabla();

        colConsulta1.setText("Nave");
        colConsulta2.setText("Planeta");
        colConsulta3.setText("Objetivo");
        colConsulta3.setVisible(true);

        try {

            List<Mision> misiones = MisionDAO.findAll();

            for (int i = 0; i < misiones.size(); i++) {

                Mision mision = misiones.get(i);

                String nombreNave = "Sin nave";

                if (mision.getNave() != null) {
                    nombreNave = mision.getNave().getNombre();
                }

                ConsultaRelacion consulta = new ConsultaRelacion(
                        nombreNave,
                        mision.getNombrePlaneta(),
                        mision.getObjetivo()
                );

                tablaConsultas.getItems().add(consulta);

            }

        } catch (SQLException e) {
            Utils.mostrarError("Error", "No se han podido cargar las misiones.");
        }
    }

    /**
     * Metodo que muestra las naves y sus modulos
     * @param event
     */
    @FXML
    public void mostrarModulosNave(ActionEvent event) {

        prepararTabla();

        colConsulta1.setText("Nave");
        colConsulta2.setText("Módulos");
        colConsulta3.setVisible(false);

        try {

            List<Modulo> modulos = ModuloDAO.findAll();
            Map<String, String> relaciones = new LinkedHashMap<>();

            for (int i = 0; i < modulos.size(); i++) {

                Modulo modulo = modulos.get(i);

                String nombreNave = "Sin nave";

                if (modulo.getNave() != null) {
                    nombreNave = modulo.getNave().getNombre();
                }

                String nombreModulo = modulo.getNombre();

                if (!relaciones.containsKey(nombreNave)) {
                    relaciones.put(nombreNave, nombreModulo);
                } else {
                    relaciones.put(nombreNave, relaciones.get(nombreNave) + ", " + nombreModulo);
                }
            }

            for (String nombreNave : relaciones.keySet()) {
                ConsultaRelacion consulta = new ConsultaRelacion(nombreNave, relaciones.get(nombreNave), "");
                tablaConsultas.getItems().add(consulta);
            }

        } catch (SQLException e) {
            Utils.mostrarError("Error", "No se han podido cargar los módulos.");
        }
    }

    /**
     * Metodo que muestra los modulos y sus astronautas
     * @param event
     */
    @FXML
    public void mostrarAstronautasModulo(ActionEvent event) {

        prepararTabla();

        colConsulta1.setText("Módulo");
        colConsulta2.setText("Astronautas");
        colConsulta3.setVisible(false);

        try {

            List<Astronauta> astronautas = AstronautaDAO.findAll();
            Map<String, String> relaciones = new LinkedHashMap<>();

            for (int i = 0; i < astronautas.size(); i++) {

                Astronauta astronauta = astronautas.get(i);

                String nombreModulo = "Sin módulo";

                if (astronauta.getModulo() != null) {
                    nombreModulo = astronauta.getModulo().getNombre();
                }

                String nombreAstronauta = astronauta.getNombre();

                if (!relaciones.containsKey(nombreModulo)) {
                    relaciones.put(nombreModulo, nombreAstronauta);
                } else {
                    relaciones.put(nombreModulo, relaciones.get(nombreModulo) + ", " + nombreAstronauta);
                }
            }

            for (String nombreModulo : relaciones.keySet()) {
                ConsultaRelacion consulta = new ConsultaRelacion(nombreModulo, relaciones.get(nombreModulo), "");
                tablaConsultas.getItems().add(consulta);
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

        prepararTabla();

        colConsulta1.setText("Astronauta");
        colConsulta2.setText("Intervenciones");
        colConsulta3.setVisible(false);

        try {

            List<IntervencionMantenimiento> intervenciones = IntervencionMantenimientoDAO.findAll();
            Map<String, String> relaciones = new LinkedHashMap<>();

            for (int i = 0; i < intervenciones.size(); i++) {

                IntervencionMantenimiento intervencion = intervenciones.get(i);

                String nombreAstronauta = "Sin astronauta";

                if (intervencion.getAstronauta() != null) {
                    nombreAstronauta = intervencion.getAstronauta().getNombre();
                }

                String textoIntervencion = intervencion.getObservaciones();

                if (intervencion.getMantenimiento() != null) {
                    textoIntervencion = intervencion.getMantenimiento().getDescripcion() + " - " + intervencion.getObservaciones();
                }

                if (!relaciones.containsKey(nombreAstronauta)) {
                    relaciones.put(nombreAstronauta, textoIntervencion);
                } else {
                    relaciones.put(nombreAstronauta, relaciones.get(nombreAstronauta) + ", " + textoIntervencion);
                }
            }

            for (String nombreAstronauta : relaciones.keySet()) {
                ConsultaRelacion consulta = new ConsultaRelacion(nombreAstronauta, relaciones.get(nombreAstronauta), "");
                tablaConsultas.getItems().add(consulta);
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
        stage.setResizable(false);
        stage.show();
    }
}
