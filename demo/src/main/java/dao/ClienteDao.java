package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modelos.Cliente;
import utilidades.ConexionDB;

public class ClienteDao {
    public static boolean insertarCliente(Cliente cliente) {
        String sql = "INSERT INTO clientes (id, nombre, edad, telefono, ciudad) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionDB.getConnection();
                PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, cliente.getId());
            stmt.setString(2, cliente.getNombre());
            stmt.setString(3, cliente.getEdad());
            stmt.setString(4, cliente.getTelefono());
            stmt.setString(5, cliente.getCiudad());

            int filasInsertadas = stmt.executeUpdate();
            return filasInsertadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar cliente: " + e.getMessage());
            return false;
        }
    }

    public static List<Cliente> obtenerClientes() {
        List<Cliente> listaClientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes";

        try (Connection conexion = ConexionDB.getConnection();
                Statement stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) { // Iteramos sobre los resultados
                String id = rs.getString("id");
                String nombre = rs.getString("nombre");
                String edad = rs.getString("edad");
                String telefono = rs.getString("telefono");
                String ciudad = rs.getString("ciudad");

                // Creamos objeto Cliente
                Cliente cliente = new Cliente(id, nombre, edad, telefono, ciudad);
                listaClientes.add(cliente);
            }

            System.out.println("Clientes obtenidos correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al obtener clientes: " + e.getMessage());
        }
        return listaClientes; // Devolvemos la lista con los clientes
    }

    public static void reiniciarTablaClientes() {
        String sql = "TRUNCATE TABLE clientes";

        try (Connection conexion = ConexionDB.getConnection();
                Statement stmt = conexion.createStatement()) {

            stmt.executeUpdate(sql);
            System.out.println("Tabla 'clientes' reiniciada correctamente.");

        } catch (SQLException e) {
            System.err.println("Error al reiniciar la tabla: " + e.getMessage());
        }
    }

    public static ResultSet obtenerDatosClientes2() {
        String sql = "SELECT * FROM clientes";
        Connection conexion = ConexionDB.getConnection();

        try {
            Statement stmt = conexion.createStatement();
            return stmt.executeQuery(sql); // Se devuelve directamente el ResultSet
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Manejo de error: Devuelve null si falla la consulta
        }
    }
}