package es.franciscodelosrios.plutonf.dao;

import es.franciscodelosrios.plutonf.dataaccess.ConnectionBD;
import es.franciscodelosrios.plutonf.model.Nave;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NaveDAO {
    private final static String SQL_ALL = "SELECT * FROM nave";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM nave WHERE idNave = ?";
    private final static String SQL_FIND_BY_NAME = "SELECT * FROM nave WHERE nombre = ?";
    private static final String SQL_INSERT = "INSERT INTO nave (nombre, alcance, combustibleActual, fechaLanzamiento, estadoNave) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE nave SET nombre = ?, alcance = ?, combustibleActual = ?, fechaLanzamiento = ?, estadoNave = ? WHERE idNave = ?";
    private static final String SQL_DELETE = "DELETE FROM nave WHERE idNave = ?";

    /**
     * Metodo que busca todas las naves
     *
     * @return devuelve una lista de naves
     * @throws SQLException
     */
    public static List<Nave> findAll() throws SQLException {
        Nave nave = null;
        List<Nave> naves = new ArrayList<>();
        Connection con = ConnectionBD.getInstance().getConnection();

        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(SQL_ALL);

        while (rs.next()) {
            int idNave = rs.getInt("idNave");
            String nombre = rs.getString("nombre");
            double alcance = rs.getDouble("alcance");
            double combustibleActual = rs.getDouble("combustibleActual");
            java.util.Date fechaLanzamiento = rs.getDate("fechaLanzamiento");
            String estadoNave = rs.getString("estadoNave");

            nave = new Nave(idNave, nombre, alcance, combustibleActual, fechaLanzamiento, estadoNave);
            naves.add(nave);
        }
        return naves;
    }

    /**
     * Metodo que busca una nave por su id
     *
     * @param idNave
     * @return el objeto de la nave
     * @throws SQLException
     */
    public static Nave findById(int idNave) throws SQLException {
        Nave nave = null;
        try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, idNave);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("idNave");
                String nombre = rs.getString("nombre");
                double alcance = rs.getDouble("alcance");
                double combustible = rs.getDouble("combustibleActual");
                java.util.Date fecha = rs.getDate("fechaLanzamiento");
                String estado = rs.getString("estadoNave");

                nave = new Nave(id, nombre, alcance, combustible, fecha, estado);
            }
        }
        return nave;
    }

    /**
     * Metodo que se encarga de añadir una nave
     *
     * @param nave
     * @return la Nave obtejo que ha añadido
     * @throws SQLException
     */
    public static boolean addNave(Nave nave) throws SQLException {
        boolean anadido = false;
        if ((nave != null) && findByName(nave.getNombre()) == null) {
            try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_INSERT)) {
                ps.setString(1, nave.getNombre());
                ps.setDouble(2, nave.getAlcance());
                ps.setDouble(3, nave.getCombustibleActual());
                ps.setDate(4, new java.sql.Date(nave.getFechaLanzamiento().getTime()));
                ps.setString(5, nave.getEstadoNave());
                ps.executeUpdate();
                nave = findByName(nave.getNombre());
                anadido = true;
            }
        } else {
            nave = null;
        }
        return anadido;
    }

    /**
     * Metodo que buscar por nombre una nave
     *
     * @param nombre
     * @return devuelve el ojbeto de nave
     * @throws SQLException
     */
    private static Nave findByName(String nombre) throws SQLException {
        Nave nave = null;
        try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_FIND_BY_NAME)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("idNave");
                String n = rs.getString("nombre");
                double alc = rs.getDouble("alcance");
                double comb = rs.getDouble("combustibleActual");
                java.util.Date fec = rs.getDate("fechaLanzamiento");
                String est = rs.getString("estadoNave");

                nave = new Nave(id, n, alc, comb, fec, est);
            }
        }
        return nave;
    }

    /**
     * Metodo que actualiza una nave
     *
     * @param naveNueva
     * @param naveActual
     * @return si se ha actualizado correctamente
     * @throws SQLException
     */
    public static boolean updateNave(Nave naveNueva, Nave naveActual) throws SQLException {
        boolean updated = false;
        if ((naveActual != null) && (naveNueva != null) && findByName(naveActual.getNombre()) != null) {
            try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_UPDATE)) {
                ps.setString(1, naveNueva.getNombre());
                ps.setDouble(2, naveNueva.getAlcance());
                ps.setDouble(3, naveNueva.getCombustibleActual());
                ps.setDate(4, new java.sql.Date(naveNueva.getFechaLanzamiento().getTime()));
                ps.setString(5, naveNueva.getEstadoNave());
                ps.setInt(6, naveActual.getIdNave());

                ps.executeUpdate();
                updated = true;
            }
        }
        return updated;
    }

    /**
     * Metodo que elimina una nave
     *
     * @param nave nave que se quiere eliminar
     * @return si se ha eliminado correctamente
     * @throws SQLException
     */
    public static boolean deleteNave(Nave nave) throws SQLException {
        boolean deleted = false;

        if (nave != null && findById(nave.getIdNave()) != null) {
            try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_DELETE)) {
                ps.setInt(1, nave.getIdNave());
                ps.executeUpdate();
                deleted = true;
            }
        }

        return deleted;
    }
}