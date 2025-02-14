package utilidades;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuracion {
    private static final String CONFIG_FILE = "demo\\src\\main\\resources\\config.properties";
    private static Properties properties = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            properties.load(fis); // Cargar el archivo de propiedades
        } catch (IOException e) {
            System.err.println("Error al cargar el archivo de configuración: " + e.getMessage());
        }
    }

    public static String getDBUrl() {
        return properties.getProperty("db.url");
    }

    public static String getDBUsername() {
        return properties.getProperty("db.username");
    }

    public static String getDBPassword() {
        return properties.getProperty("db.password");
    }
}