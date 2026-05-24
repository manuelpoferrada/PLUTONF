package es.franciscodelosrios.plutonf.dao;

import es.franciscodelosrios.plutonf.dataaccess.ConnectionBD;
import es.franciscodelosrios.plutonf.model.Astronauta;
import es.franciscodelosrios.plutonf.model.Modulo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AstronautaDAO {

    private final static String SQL_ALL = "SELECT * FROM astronautas";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM astronautas WHERE idAstronauta = ?";
    private final static String SQL_FIND_BY_DNI = "SELECT * FROM astronautas WHERE dni = ?";
    private static final String SQL_INSERT = "INSERT INTO astronautas (dni, nombre, habilidadPrincipal, rango, edad, horasVuelo, nacionalidad, idModulo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE astronautas SET dni = ?, nombre = ?, habilidadPrincipal = ?, rango = ?, edad = ?, horasVuelo = ?, nacionalidad = ?, idModulo = ? WHERE idAstronauta = ?";
    private static final String SQL_DELETE = "DELETE FROM astronautas WHERE idAstronauta = ?";

    /**
     * Metodo eager que busca todos los astronautas de la base de datos
     * @return lista de astronautas
     * @throws SQLException
     */
    public static List<Astronauta> findAll() throws SQLException {
        Astronauta astronauta = null;
        List<Astronauta> astronautas = new ArrayList<>();
        Connection con = ConnectionBD.getInstance().getConnection();

        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(SQL_ALL);

        while (rs.next()) {
            int id = rs.getInt("idAstronauta");
            String dni = rs.getString("dni");
            String nombre = rs.getString("nombre");
            String habilidad = rs.getString("habilidadPrincipal");
            String rango = rs.getString("rango");
            int edad = rs.getInt("edad");
            int horasVuelo = rs.getInt("horasVuelo");
            String nacionalidad = rs.getString("nacionalidad");

            int idModulo = rs.getInt("idModulo");
            Modulo modulo = null;
            if (!rs.wasNull()) {
                modulo = ModuloDAO.findById(idModulo);
            }

            astronauta = new Astronauta(id, dni, nombre, habilidad, rango, edad, horasVuelo, nacionalidad, modulo);
            astronautas.add(astronauta);
        }
        return astronautas;
    }

    /**
     * Metodo EAGER que busca los astronauta por su id
     * @param idAstronauta
     * @return el objeto astronauta
     * @throws SQLException
     */
    public static Astronauta findById(int idAstronauta) throws SQLException {
        Astronauta astronauta = null;
        try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, idAstronauta);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("idAstronauta");
                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");
                String habilidad = rs.getString("habilidadPrincipal");
                String rango = rs.getString("rango");
                int edad = rs.getInt("edad");
                int horasVuelo = rs.getInt("horasVuelo");
                String nacionalidad = rs.getString("nacionalidad");

                int idModulo = rs.getInt("idModulo");
                Modulo modulo = null;
                if (!rs.wasNull()) {
                    modulo = ModuloDAO.findById(idModulo);
                }

                astronauta = new Astronauta(id, dni, nombre, habilidad, rango, edad, horasVuelo, nacionalidad, modulo);
            }
        }
        return astronauta;
    }

    /**
     * Metodo que se encarga de añadir un astronauta
     * @param astronauta
     * @return el Astronauta que ha añadido, si no añade ninguno devuelve null
     * @throws SQLException
     */
    public static boolean addAstronauta(Astronauta astronauta) throws SQLException {
        boolean anadido = false;
        if ((astronauta != null) && findByDni(astronauta.getDni()) == null) {
            try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_INSERT)) {
                ps.setString(1, astronauta.getDni());
                ps.setString(2, astronauta.getNombre());
                ps.setString(3, astronauta.getHabilidadPrincipal());
                ps.setString(4, astronauta.getRango());
                ps.setInt(5, astronauta.getEdad());
                ps.setInt(6, astronauta.getHorasVuelo());
                ps.setString(7, astronauta.getNacionalidad());

                if (astronauta.getModulo() != null) {
                    ps.setInt(8, astronauta.getModulo().getIdModulo());
                } else {
                    ps.setNull(8, Types.INTEGER);
                }

                ps.executeUpdate();
                astronauta = findByDni(astronauta.getDni());
                anadido = true;
            }
        } else {
            astronauta = null;
        }
        return anadido;
    }

    /**
     * Es un metodo EAGER por el DNI a un astronauta
     * @param dni
     * @return devuelve el objeto astronauta
     * @throws SQLException
     */
    public static Astronauta findByDni(String dni) throws SQLException {
        Astronauta astronauta = null;
        try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_FIND_BY_DNI)) {
            ps.setString(1, dni);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("idAstronauta");
                String d = rs.getString("dni");
                String n = rs.getString("nombre");
                String h = rs.getString("habilidadPrincipal");
                String r = rs.getString("rango");
                int e = rs.getInt("edad");
                int hv = rs.getInt("horasVuelo");
                String nac = rs.getString("nacionalidad");

                int idModulo = rs.getInt("idModulo");
                Modulo modulo = null;
                if (!rs.wasNull()) {
                    modulo = ModuloDAO.findById(idModulo);
                }

                astronauta = new Astronauta(id, d, n, h, r, e, hv, nac, modulo);
            }
        }
        return astronauta;
    }

    /**
     * Metodo que actualiza un astronauta
     * @param astronautaNuevo datos para modificar
     * @param astronautaActual astronauta que va a ser modificado
     * @return si se ha actualizado correctamente
     * @throws SQLException
     */
    public static boolean updateAstronauta(Astronauta astronautaNuevo, Astronauta astronautaActual) throws SQLException {
        boolean updated = false;
        if ((astronautaActual != null) && (astronautaNuevo != null) && findByDni(astronautaActual.getDni()) != null) {
            try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_UPDATE)) {
                ps.setString(1, astronautaNuevo.getDni());
                ps.setString(2, astronautaNuevo.getNombre());
                ps.setString(3, astronautaNuevo.getHabilidadPrincipal());
                ps.setString(4, astronautaNuevo.getRango());
                ps.setInt(5, astronautaNuevo.getEdad());
                ps.setInt(6, astronautaNuevo.getHorasVuelo());
                ps.setString(7, astronautaNuevo.getNacionalidad());

                if (astronautaNuevo.getModulo() != null) {
                    ps.setInt(8, astronautaNuevo.getModulo().getIdModulo());
                } else {
                    ps.setNull(8, Types.INTEGER);
                }

                ps.setInt(9, astronautaActual.getIdAstronauta());

                ps.executeUpdate();
                updated = true;
            }
        }
        return updated;
    }

    /**
     * Metodo que elimina un astronauta
     * @param astronauta astronauta que se quiere eliminar
     * @return si se ha eliminado correctamente
     * @throws SQLException
     */
    public static boolean deleteAstronauta(Astronauta astronauta) throws SQLException {
        boolean deleted = false;

        if (astronauta != null && findById(astronauta.getIdAstronauta()) != null) {
            try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_DELETE)) {
                ps.setInt(1, astronauta.getIdAstronauta());
                ps.executeUpdate();
                deleted = true;
            }
        }
        return deleted;
    }
}
