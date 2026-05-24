package es.franciscodelosrios.plutonf.dao;

import es.franciscodelosrios.plutonf.dataaccess.ConnectionBD;
import es.franciscodelosrios.plutonf.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModuloDAO {
    private final static String SQL_ALL = "SELECT * FROM modulos";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM modulos WHERE idModulo = ?";
    private final static String SQL_FIND_BY_NAME = "SELECT * FROM modulos WHERE nombre = ?";
    private static final String SQL_INSERT = "INSERT INTO modulos (nombre, sector, capacidadMaxima, nivelOxigeno, temperaturaInterior, idNave, nivelSeguridad, numExperimentos, numCamas) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE modulos SET nombre = ?, sector = ?, capacidadMaxima = ?, nivelOxigeno = ?, temperaturaInterior = ?, idNave = ?, nivelSeguridad = ?, numExperimentos = ?, numCamas = ? WHERE idModulo = ?";
    private static final String SQL_DELETE = "DELETE FROM modulos WHERE idModulo = ?";

    /**
     * Metodo que busca todos los modulos
     * @return devuelve una lista de modulos
     * @throws SQLException
     */
    public static List<Modulo> findAll() throws SQLException {
        Modulo modulo = null;
        List<Modulo> modulos = new ArrayList<>();
        Connection con = ConnectionBD.getInstance().getConnection();

        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(SQL_ALL);

        while (rs.next()) {
            int idModulo = rs.getInt("idModulo");
            String nombre = rs.getString("nombre");
            String sector = rs.getString("sector");
            int capacidad = rs.getInt("capacidadMaxima");
            double oxigeno = rs.getDouble("nivelOxigeno");
            double temperatura = rs.getDouble("temperaturaInterior");

            int idNave = rs.getInt("idNave");
            Nave nave = null;
            if (!rs.wasNull()) {
                nave = NaveDAO.findById(idNave);
            }

            // Identificamos el tipo de modulo por las columnas de la BD
            int nivelSeguridad = rs.getInt("nivelSeguridad");
            int numExperimentos = rs.getInt("numExperimentos");
            int numCamas = rs.getInt("numCamas");

            if (!rs.wasNull() && numCamas > 0) {
                modulo = new ModulosVivienda(idModulo, nombre, sector, capacidad, oxigeno, temperatura, nave, numCamas);
            } else if (numExperimentos > 0) {
                modulo = new ModulosLaboratorio(idModulo, nombre, sector, capacidad, oxigeno, temperatura, nave, numExperimentos);
            } else {
                modulo = new ModulosControl(idModulo, nombre, sector, capacidad, oxigeno, temperatura, nave, nivelSeguridad);
            }

            modulos.add(modulo);
        }
        return modulos;
    }

    /**
     * Buscamos un modulo por su ID
     * @param idModulo del modulo a buscar
     * @return el objeto modulo que se ha encontrado, sino deuvleve null
     * @throws SQLException
     */
    public static Modulo findById(int idModulo) throws SQLException {
        Modulo modulo = null;
        try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, idModulo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("idModulo");
                String nom = rs.getString("nombre");
                String sec = rs.getString("sector");
                int cap = rs.getInt("capacidadMaxima");
                double oxi = rs.getDouble("nivelOxigeno");
                double temp = rs.getDouble("temperaturaInterior");

                int idNav = rs.getInt("idNave");
                Nave nav = null;
                if (!rs.wasNull()) {
                    nav = NaveDAO.findById(idNav);
                }

                int seg = rs.getInt("nivelSeguridad");
                int exp = rs.getInt("numExperimentos");
                int cam = rs.getInt("numCamas");

                if (cam > 0) {
                    modulo = new ModulosVivienda(id, nom, sec, cap, oxi, temp, nav, cam);
                } else if (exp > 0) {
                    modulo = new ModulosLaboratorio(id, nom, sec, cap, oxi, temp, nav, exp);
                } else {
                    modulo = new ModulosControl(id, nom, sec, cap, oxi, temp, nav, seg);
                }
            }
        }
        return modulo;
    }

    /**
     * Buscamos por nombre un modulo
     * @param nombre del modulo que se haya encontrado
     * @return el objeto modulo encontrado, sino devuelve null
     * @throws SQLException
     */
    public static Modulo findByName(String nombre) throws SQLException {
        Modulo modulo = null;
        try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_FIND_BY_NAME)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("idModulo");
                String nom = rs.getString("nombre");
                String sec = rs.getString("sector");
                int cap = rs.getInt("capacidadMaxima");
                double oxi = rs.getDouble("nivelOxigeno");
                double temp = rs.getDouble("temperaturaInterior");

                int idNav = rs.getInt("idNave");
                Nave nav = null;
                if (!rs.wasNull()) {
                    nav = NaveDAO.findById(idNav);
                }

                int seg = rs.getInt("nivelSeguridad");
                int exp = rs.getInt("numExperimentos");
                int cam = rs.getInt("numCamas");

                if (cam > 0) {
                    modulo = new ModulosVivienda(id, nom, sec, cap, oxi, temp, nav, cam);
                } else if (exp > 0) {
                    modulo = new ModulosLaboratorio(id, nom, sec, cap, oxi, temp, nav, exp);
                } else {
                    modulo = new ModulosControl(id, nom, sec, cap, oxi, temp, nav, seg);
                }
            }
        }
        return modulo;
    }

    /**
     * Añadimos un modulo a a lista de modulos
     * @param modulo
     * @return si se ha añadido true si no false
     * @throws SQLException
     */
    public static boolean addModulo(Modulo modulo) throws SQLException {
        boolean insertado = false;

        if ((modulo != null) && findByName(modulo.getNombre()) == null) {
            try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_INSERT)) {
                ps.setString(1, modulo.getNombre());
                ps.setString(2, modulo.getSector());
                ps.setInt(3, modulo.getCapacidadMaxima());
                ps.setDouble(4, modulo.getNivelOxigeno());
                ps.setDouble(5, modulo.getTemperaturaInterior());

                if (modulo.getNave() != null) {
                    ps.setInt(6, modulo.getNave().getIdNave());
                } else {
                    ps.setNull(6, Types.INTEGER);
                }

                ps.setNull(7, Types.INTEGER);
                ps.setNull(8, Types.INTEGER);
                ps.setNull(9, Types.INTEGER);

                if (modulo instanceof ModulosControl) {
                    ps.setInt(7, ((ModulosControl) modulo).getNivelSeguridad());
                } else if (modulo instanceof ModulosLaboratorio) {
                    ps.setInt(8, ((ModulosLaboratorio) modulo).getNumExperimentos());
                } else if (modulo instanceof ModulosVivienda) {
                    ps.setInt(9, ((ModulosVivienda) modulo).getNumCamas());
                }

                int filasAfectadas = ps.executeUpdate();
                if (filasAfectadas > 0) {
                    insertado = true;
                }
            }
        }

        return insertado;
    }

    /**
     * Metodo que actualiza un Modulo
     * @param moduloNuevo el modulo que se va a usar para actualizar el actual
     * @param moduloActual el modulo a actualizar
     * @return si se ha actualizado o no
     * @throws SQLException
     */
    public static boolean updateModulo(Modulo moduloNuevo, Modulo moduloActual) throws SQLException {
        boolean updated = false;
        if ((moduloActual != null) && (moduloNuevo != null) && findByName(moduloActual.getNombre()) != null) {
            try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_UPDATE)) {
                ps.setString(1, moduloNuevo.getNombre());
                ps.setString(2, moduloNuevo.getSector());
                ps.setInt(3, moduloNuevo.getCapacidadMaxima());
                ps.setDouble(4, moduloNuevo.getNivelOxigeno());
                ps.setDouble(5, moduloNuevo.getTemperaturaInterior());

                if (moduloNuevo.getNave() != null) {
                    ps.setInt(6, moduloNuevo.getNave().getIdNave());
                } else {
                    ps.setNull(6, Types.INTEGER);
                }

                ps.setNull(7, Types.INTEGER);
                ps.setNull(8, Types.INTEGER);
                ps.setNull(9, Types.INTEGER);

                if (moduloNuevo instanceof ModulosControl) {
                    ps.setInt(7, ((ModulosControl) moduloNuevo).getNivelSeguridad());
                } else if (moduloNuevo instanceof ModulosLaboratorio) {
                    ps.setInt(8, ((ModulosLaboratorio) moduloNuevo).getNumExperimentos());
                } else if (moduloNuevo instanceof ModulosVivienda) {
                    ps.setInt(9, ((ModulosVivienda) moduloNuevo).getNumCamas());
                }

                ps.setInt(10, moduloActual.getIdModulo());

                ps.executeUpdate();
                updated = true;
            }
        }
        return updated;
    }

    /**
     * Metodo que elimina un modulo
     * @param modulo modulo que se quiere eliminar
     * @return si se ha eliminado correctamente
     * @throws SQLException
     */
    public static boolean deleteModulo(Modulo modulo) throws SQLException {
        boolean deleted = false;

        if (modulo != null && findById(modulo.getIdModulo()) != null) {
            try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_DELETE)) {
                ps.setInt(1, modulo.getIdModulo());
                ps.executeUpdate();
                deleted = true;
            }
        }
        return deleted;
    }
}