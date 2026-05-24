package es.franciscodelosrios.plutonf.dao;

import es.franciscodelosrios.plutonf.dataaccess.ConnectionBD;
import es.franciscodelosrios.plutonf.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IntervencionMantenimientoDAO {

    private final static String SQL_ALL = "SELECT * FROM intervenciones_mantenimiento";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM intervenciones_mantenimiento WHERE idIntervencion = ?";
    private static final String SQL_INSERT = "INSERT INTO intervenciones_mantenimiento (idMantenimiento, idAstronauta, idModulo, fechaIntervencion, observaciones) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE intervenciones_mantenimiento SET idMantenimiento = ?, idAstronauta = ?, idModulo = ?, fechaIntervencion = ?, observaciones = ? WHERE idIntervencion = ?";
    private static final String SQL_DELETE = "DELETE FROM intervenciones_mantenimiento WHERE idIntervencion = ?";

    /**
     * Metodo que devuelve una lista de mantentmientos que han habido
     * @return una lista de los mantenimientos
     * @throws SQLException
     */
    public static List<IntervencionMantenimiento> findAll() throws SQLException {
        List<IntervencionMantenimiento> intervenciones = new ArrayList<>();
        Connection con = ConnectionBD.getInstance().getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(SQL_ALL);

        while (rs.next()) {
            IntervencionMantenimiento intervencion = new IntervencionMantenimiento(
                    rs.getInt("idIntervencion"),
                    MantenimientoDAO.findById(rs.getInt("idMantenimiento")),
                    AstronautaDAO.findById(rs.getInt("idAstronauta")),
                    ModuloDAO.findById(rs.getInt("idModulo")),
                    rs.getDate("fechaIntervencion"),
                    rs.getString("observaciones")
            );
            intervenciones.add(intervencion);
        }
        return intervenciones;
    }

    /**
     * Metodos que buscar por ID una intervencion de mantenimiento
     * @param idIntervencion a buscar
     * @return el objeto intervencion si no devuelve null
     * @throws SQLException
     */
    public static IntervencionMantenimiento findById(int idIntervencion) throws SQLException {
        IntervencionMantenimiento intervencion = null;
        try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, idIntervencion);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                intervencion = new IntervencionMantenimiento(
                        rs.getInt("idIntervencion"),
                        MantenimientoDAO.findById(rs.getInt("idMantenimiento")),
                        AstronautaDAO.findById(rs.getInt("idAstronauta")),
                        ModuloDAO.findById(rs.getInt("idModulo")),
                        rs.getDate("fechaIntervencion"),
                        rs.getString("observaciones")
                );
            }
        }
        return intervencion;
    }


    /**
     * Añadimos una intervención
     * @param intervencion a añadir
     * @return si se ha añadido correctamente true, sino false
     * @throws SQLException
     */
    public static boolean addIntervencion(IntervencionMantenimiento intervencion) throws SQLException {
        if (intervencion == null) return false;
        try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_INSERT)) {
            ps.setInt(1, intervencion.getMantenimiento().getIdMantenimiento());
            ps.setInt(2, intervencion.getAstronauta().getIdAstronauta());
            ps.setInt(3, intervencion.getModulo().getIdModulo());
            // Hay que poner esto "new java.sql.Date" porque mysql no lo entiende
            ps.setDate(4, new java.sql.Date(intervencion.getFechaIntervencion().getTime()));
            ps.setString(5, intervencion.getObservaciones());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Actualizamos una Intervencion
     * @param iNueva Nueva intervencion que tiene los datos para actualizar
     * @param iActual Vieja intervencion que vamos a modificar
     * @return si se ha actualizado correctamente true, sino false
     * @throws SQLException
     */
    public static boolean updateIntervencion(IntervencionMantenimiento iNueva, IntervencionMantenimiento iActual) throws SQLException {
        if (iActual == null || iNueva == null) return false;

        try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_UPDATE)) {
            ps.setInt(1, iNueva.getMantenimiento().getIdMantenimiento());
            ps.setInt(2, iNueva.getAstronauta().getIdAstronauta());
            ps.setInt(3, iNueva.getModulo().getIdModulo());
            ps.setDate(4, new java.sql.Date(iNueva.getFechaIntervencion().getTime()));
            ps.setString(5, iNueva.getObservaciones());
            ps.setInt(6, iActual.getIdIntervencion());
            ps.executeUpdate();
            return true;
        }
    }

    /**
     * Metodo que elimina una intervencion
     * @param intervencion intervencion que se quiere eliminar
     * @return si se ha eliminado correctamente
     * @throws SQLException
     */
    public static boolean deleteIntervencion(IntervencionMantenimiento intervencion) throws SQLException {
        boolean deleted = false;

        if (intervencion != null && findById(intervencion.getIdIntervencion()) != null) {
            try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_DELETE)) {
                ps.setInt(1, intervencion.getIdIntervencion());
                ps.executeUpdate();
                deleted = true;
            }
        }

        return deleted;
    }
}
