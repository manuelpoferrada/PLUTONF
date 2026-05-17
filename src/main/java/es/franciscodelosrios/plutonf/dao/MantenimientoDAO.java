package es.franciscodelosrios.plutonf.dao;

import es.franciscodelosrios.plutonf.dataaccess.ConnectionBD;
import es.franciscodelosrios.plutonf.model.Mantenimiento;
import es.franciscodelosrios.plutonf.model.Prioridad;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MantenimientoDAO {

    private final static String SQL_ALL = "SELECT * FROM mantenimiento";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM mantenimiento WHERE idMantenimiento = ?";
    private final static String SQL_FIND_BY_DESC = "SELECT * FROM mantenimiento WHERE descripcion = ?";
    private static final String SQL_INSERT = "INSERT INTO mantenimiento (descripcion, prioridad, costeRecursos, duracionEntidad, herramientaNecesaria) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE mantenimiento SET descripcion = ?, prioridad = ?, costeRecursos = ?, duracionEntidad = ?, herramientaNecesaria = ? WHERE idMantenimiento = ?";
    private static final String SQL_DELETE = "DELETE FROM mantenimiento WHERE idMantenimiento = ?";

    /**
     * Buscamos todos los mantenimientos de la base de datos
     * @return lista de mantenimientos
     * @throws SQLException
     */
    public static List<Mantenimiento> findAll() throws SQLException {
        Mantenimiento mantenimiento = null;
        List<Mantenimiento> mantenimientos = new ArrayList<>();
        Connection con = ConnectionBD.getInstance().getConnection();

        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(SQL_ALL);

        while (rs.next()) {
            int id = rs.getInt("idMantenimiento");
            String desc = rs.getString("descripcion");
            Prioridad prio = Prioridad.valueOf(rs.getString("prioridad"));
            double coste = rs.getDouble("costeRecursos");
            String duracion = rs.getString("duracionEntidad");
            String herramienta = rs.getString("herramientaNecesaria");

            mantenimiento = new Mantenimiento(id, desc, prio, coste, duracion, herramienta);
            mantenimientos.add(mantenimiento);
        }
        return mantenimientos;
    }

    /**
     * Buscamos un mantenimiento por su id
     * @param idMantenimiento
     * @return el objeto mantenimiento
     * @throws SQLException
     */
    public static Mantenimiento findById(int idMantenimiento) throws SQLException {
        Mantenimiento mantenimiento = null;
        try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, idMantenimiento);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("idMantenimiento");
                String desc = rs.getString("descripcion");
                Prioridad prio = Prioridad.valueOf(rs.getString("prioridad"));
                double coste = rs.getDouble("costeRecursos");
                String duracion = rs.getString("duracionEntidad");
                String herramienta = rs.getString("herramientaNecesaria");

                mantenimiento = new Mantenimiento(id, desc, prio, coste, duracion, herramienta);
            }
        }
        return mantenimiento;
    }

    /**
     * Metodo que se encarga de añadir un mantenimiento
     * @param mantenimiento
     * @return el Mantenimiento añadido
     * @throws SQLException
     */
    public static boolean addMantenimiento(Mantenimiento mantenimiento) throws SQLException {
        boolean anadido = false;
        if ((mantenimiento != null) && findByDescripcion(mantenimiento.getDescripcion()) == null) {
            try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_INSERT)) {
                ps.setString(1, mantenimiento.getDescripcion());
                // Guardamos el enum como String
                ps.setString(2, mantenimiento.getPrioridad().name());
                ps.setDouble(3, mantenimiento.getCosteRecursos());
                ps.setString(4, mantenimiento.getDuracionEntidad());
                ps.setString(5, mantenimiento.getHerramientaNecesaria());

                ps.executeUpdate();
                mantenimiento = findByDescripcion(mantenimiento.getDescripcion());
                anadido = true;
            }
        } else {
            mantenimiento = null;
        }
        return anadido;
    }

    /**
     * Buscamos por descripción para comprobaciones
     * @param descripcion
     * @return objeto mantenimiento
     * @throws SQLException
     */
    public static Mantenimiento findByDescripcion(String descripcion) throws SQLException {
        Mantenimiento mantenimiento = null;
        try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_FIND_BY_DESC)) {
            ps.setString(1, descripcion);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("idMantenimiento");
                String desc = rs.getString("descripcion");
                Prioridad prio = Prioridad.valueOf(rs.getString("prioridad"));
                double coste = rs.getDouble("costeRecursos");
                String duracion = rs.getString("duracionEntidad");
                String herramienta = rs.getString("herramientaNecesaria");

                mantenimiento = new Mantenimiento(id, desc, prio, coste, duracion, herramienta);
            }
        }
        return mantenimiento;
    }

    /**
     * Metodo que actualiza un mantenimiento
     * @param mNuevo datos nuevos
     * @param mActual mantenimiento original
     * @return true si se actualiza
     * @throws SQLException
     */
    public static boolean updateMantenimiento(Mantenimiento mNuevo, Mantenimiento mActual) throws SQLException {
        boolean updated = false;
        if ((mActual != null) && (mNuevo != null) && findById(mActual.getIdMantenimiento()) != null) {
            try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_UPDATE)) {
                ps.setString(1, mNuevo.getDescripcion());
                ps.setString(2, mNuevo.getPrioridad().name());
                ps.setDouble(3, mNuevo.getCosteRecursos());
                ps.setString(4, mNuevo.getDuracionEntidad());
                ps.setString(5, mNuevo.getHerramientaNecesaria());
                ps.setInt(6, mActual.getIdMantenimiento());

                ps.executeUpdate();
                updated = true;
            }
        }
        return updated;
    }

    /**
     * Metodo que elimina un mantenimiento
     * @param mantenimiento mantenimiento que se quiere eliminar
     * @return si se ha eliminado correctamente
     * @throws SQLException
     */
    public static boolean deleteMantenimiento(Mantenimiento mantenimiento) throws SQLException {
        boolean deleted = false;

        if (mantenimiento != null && findById(mantenimiento.getIdMantenimiento()) != null) {
            try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_DELETE)) {
                ps.setInt(1, mantenimiento.getIdMantenimiento());
                ps.executeUpdate();
                deleted = true;
            }
        }
        return deleted;
    }
}