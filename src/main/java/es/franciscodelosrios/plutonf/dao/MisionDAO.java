package es.franciscodelosrios.plutonf.dao;

import es.franciscodelosrios.plutonf.dataaccess.ConnectionBD;
import es.franciscodelosrios.plutonf.model.Mision;
import es.franciscodelosrios.plutonf.model.Nave;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MisionDAO {
    private final static String SQL_ALL = "SELECT * FROM misiones";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM misiones WHERE idMision = ?";
    private final static String SQL_FIND_BY_PLANETA = "SELECT * FROM misiones WHERE nombrePlaneta = ?";
    private static final String SQL_INSERT = "INSERT INTO misiones (nombrePlaneta, objetivo, idNave) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE misiones SET nombrePlaneta = ?, objetivo = ?, idNave = ? WHERE idMision = ?";
    private static final String SQL_DELETE = "DELETE FROM misiones WHERE idMision = ?";

    /**
     * Buscamos todas las misiones de la base de datos
     * @return devuelvemos una lista de misiones
     * @throws SQLException
     */
    public static List<Mision> findAll() throws SQLException {
        Mision mision = null;
        List<Mision> misiones = new ArrayList<>();
        Connection con = ConnectionBD.getInstance().getConnection();

        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(SQL_ALL);

        while (rs.next()) {
            int idMision = rs.getInt("idMision");
            String nombrePlaneta = rs.getString("nombrePlaneta");
            String objetivo = rs.getString("objetivo");

            int idNave = rs.getInt("idNave");
            Nave nave = null;
            if (!rs.wasNull()) {
                nave = NaveDAO.findById(idNave);
            }

            mision = new Mision(idMision, nombrePlaneta, objetivo, nave);
            misiones.add(mision);
        }
        return misiones;
    }

    /**
     * Buscamos la mision por su id
     * @param idMision
     * @return el objeto de la mision
     * @throws SQLException
     */
    public static Mision findById(int idMision) throws SQLException {
        Mision mision = null;
        try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, idMision);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("idMision");
                String planeta = rs.getString("nombrePlaneta");
                String objetivo = rs.getString("objetivo");

                int idNave = rs.getInt("idNave");
                Nave nave = null;
                if (!rs.wasNull()) {
                    nave = NaveDAO.findById(idNave);
                }

                mision = new Mision(id, planeta, objetivo, nave);
            }
        }
        return mision;
    }

    /**
     * Metodo que se encarga de añadir una mision
     * @param mision
     * @return la Mision que ha añadido, si no añade ninguna devuelve null
     * @throws SQLException
     */
    public static boolean addMision(Mision mision) throws SQLException {
        boolean anadido = false;
        if ((mision != null) && findByPlaneta(mision.getNombrePlaneta()) == null) {
            try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_INSERT)) {
                ps.setString(1, mision.getNombrePlaneta());
                ps.setString(2, mision.getObjetivo());

                if (mision.getNave() != null) {
                    ps.setInt(3, mision.getNave().getIdNave());
                } else {
                    ps.setNull(3, Types.INTEGER);
                }

                ps.executeUpdate();

                mision = findByPlaneta(mision.getNombrePlaneta());
                anadido = true;
            }
        } else {
            mision = null;
        }
        return anadido;
    }

    /**
     * Buscamos por el nombre del planeta una mision
     * @param nombrePlaneta
     * @return devuelve el objeto de mision
     * @throws SQLException
     */
    public static Mision findByPlaneta(String nombrePlaneta) throws SQLException {
        Mision mision = null;
        try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_FIND_BY_PLANETA)) {
            ps.setString(1, nombrePlaneta);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("idMision");
                String p = rs.getString("nombrePlaneta");
                String obj = rs.getString("objetivo");

                int idNave = rs.getInt("idNave");
                Nave nave = null;
                if (!rs.wasNull()) {
                    nave = NaveDAO.findById(idNave);
                }

                mision = new Mision(id, p, obj, nave);
            }
        }
        return mision;
    }

    /**
     * Metodo que actualiza una mision
     * @param misionNueva  mision que tiene los datos para modificar la actual
     * @param misionActual mision que va a ser modificada
     * @return si se ha actualizado correctamente
     * @throws SQLException
     */
    public static boolean updateMision(Mision misionNueva, Mision misionActual) throws SQLException {
        boolean updated = false;
        if ((misionActual != null) && (misionNueva != null) && findByPlaneta(misionActual.getNombrePlaneta()) != null) {
            try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_UPDATE)) {
                ps.setString(1, misionNueva.getNombrePlaneta());
                ps.setString(2, misionNueva.getObjetivo());

                if (misionNueva.getNave() != null) {
                    ps.setInt(3, misionNueva.getNave().getIdNave());
                } else {
                    ps.setNull(3, Types.INTEGER);
                }

                ps.setInt(4, misionActual.getIdMision());

                ps.executeUpdate();
                updated = true;
            }
        }
        return updated;
    }
    /**
     * Metodo que elimina una mision
     * @param mision mision que se quiere eliminar
     * @return si se ha eliminado correctamente
     * @throws SQLException
     */
    public static boolean deleteMision(Mision mision) throws SQLException {
        boolean deleted = false;

        if (mision != null && findById(mision.getIdMision()) != null) {
            try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_DELETE)) {
                ps.setInt(1, mision.getIdMision());
                ps.executeUpdate();
                deleted = true;
            }
        }
        return deleted;
    }
}