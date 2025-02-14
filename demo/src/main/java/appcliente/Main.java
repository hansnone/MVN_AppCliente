package appcliente;

import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dao.ClienteDao;
import modelos.Cliente;
import utilidades.ProcesadorXml;
import utilidades.ProcesadorXml_SAX;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String rutaArchivo = "demo\\src\\main\\resources";
        String nombreArchivo = "Clientes.xml";

        File archivo = new File(rutaArchivo, nombreArchivo);
        // Extraemos los clientes desde el fichero xml y los guardamos en un Array
        ArrayList<Cliente> listadoClientes = ProcesadorXml.getDBFactory2(archivo);

        String menu = "\nIntroduce opcion:\n" +
                " 1- Mostrar clientes de archivo xml con DOM\n" +
                " 2- Mostrar clientes de archivo xml con SAX\n" +
                " 3- Insertar clientes desde xml a bbdd\n" +
                " 4- Borrar tabla clientes de bbdd\n" +
                " 5- Mostrar clientes de la base datos\n" +
                " 6- Descargar desde BBDD\n" +
                " 7- Salir";
        String opcion;

        do {
            System.out.println(menu);
            switch (opcion = sc.next()) {
                case "1":
                    System.out.println("Mostrando listado clientes del xml con DOM");
                    ProcesadorXml.imprimirListado(listadoClientes);
                    break;
                case "2":
                    System.out.println("Mostrando listado clientes del xml con SAX");
                    extraerDatosXmlSax(archivo);
                    break;
                case "3":
                    System.out.println("Insertando listado en la base de datos...");
                    insertarListado(listadoClientes);
                    break;
                case "4":
                    System.out.println("Borrando tabla de la base de datos ...");
                    ClienteDao.reiniciarTablaClientes();
                    break;
                case "5":
                    System.out.println("Mostrando base de datos");
                    mostrarBaseDatos();
                    break;
                case "6":
                    System.out.println("Descargando desde base de datos");
                    descargarDesdeBBDD();
                    break;
                case "7":
                    System.out.println("FIN");
                    break;
                default:
                    System.out.println("Opcion incorrecta.");
                    break;
            }
        } while (!opcion.equals("7"));
        sc.close();
    }

    private static void extraerDatosXmlSax(File archivo) {
        // SAX: Verificar si el archivo existe antes de procesarlo
        if (archivo.exists() && archivo.isFile()) {
            System.out.println("Procesando archivo XML...");
            ProcesadorXml_SAX.parsearSax(archivo);
        } else {
            System.out.println("El archivo XML no existe o la ruta es incorrecta.");
        }
    }

    // Metodo que inserta los clientes en la bbdd pasando un listado
    private static void insertarListado(ArrayList<Cliente> listado) {
        for (Cliente cliente : listado) {
            ClienteDao.insertarCliente(cliente);
        }
    }

    private static void mostrarBaseDatos() {
        List<Cliente> listado = ClienteDao.obtenerClientes();
        for (Cliente cliente : listado) {
            System.out.println(cliente);
        }
    }

    private static void descargarDesdeBBDD() {
        ResultSet rs = ClienteDao.obtenerDatosClientes2();
        ProcesadorXml.descargarAXml(rs);
    }

}