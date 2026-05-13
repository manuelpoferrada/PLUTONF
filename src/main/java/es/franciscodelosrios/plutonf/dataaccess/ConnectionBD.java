package es.franciscodelosrios.plutonf.dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionBD {
    private static final String FILE = "connection.xml";
    private static Connection con;
    private static ConnectionBD _instance;

    /**
     * Constructor privado que lee la configuración y establece la conexión
     */
    private ConnectionBD() {
        ConnectionProperties properties = XMLManager.readXML(new ConnectionProperties(), FILE);
        try {
            con = DriverManager.getConnection(properties.getURL(), properties.getUser(), properties.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
            con = null;
        }
    }

    /**
     * Método para obtener la instancia única de la clase (Singleton)
     * Este es el "getInstance()" que te pedía el error en rojo.
     */
    public static ConnectionBD getInstance() {
        if (_instance == null) {
            _instance = new ConnectionBD();
        }
        return _instance;
    }

    /**
     * Método para obtener la conexión SQL
     */
    public Connection getConnection() {
        try {
            // Verificamos si la conexión se ha cerrado para volver a abrirla si es necesario
            if (con == null || con.isClosed()) {
                ConnectionProperties properties = XMLManager.readXML(new ConnectionProperties(), FILE);
                con = DriverManager.getConnection(properties.getURL(), properties.getUser(), properties.getPassword());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
}


