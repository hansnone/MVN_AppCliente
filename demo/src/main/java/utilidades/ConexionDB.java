package utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    public static Connection getConnection() {
        String url = Configuracion.getDBUrl();
        String username = Configuracion.getDBUsername();
        String password = Configuracion.getDBPassword();

        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            return null;
        }
    }
}
