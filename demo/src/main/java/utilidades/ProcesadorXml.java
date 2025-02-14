package utilidades;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import modelos.Cliente;

public class ProcesadorXml {

    public static void getDBFactory(File archivo) {
        DocumentBuilderFactory dBFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder dBuilder = dBFactory.newDocumentBuilder();
            Document document = dBuilder.parse(archivo);

            Node nodoRaiz = document.getDocumentElement();
            System.out.println("Nodo raiz: " + nodoRaiz);

            NodeList listado = document.getElementsByTagName("cliente");

            for (int i = 0; i < listado.getLength(); i++) {
                Node nodo = listado.item(i);
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element elementoCliente = (Element) nodo;
                    String id = elementoCliente.getAttribute("id");
                    String nombre = elementoCliente.getElementsByTagName("nombre").item(0).getTextContent();
                    System.out.println("Cliente " + id + " - " + nombre);
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Cliente> getDBFactory2(File archivo) {
        ArrayList<Cliente> listadoClientes = new ArrayList<>();
        DocumentBuilderFactory dBFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder dBuilder = dBFactory.newDocumentBuilder();
            Document document = dBuilder.parse(archivo);

            NodeList listado = document.getElementsByTagName("cliente");

            for (int i = 0; i < listado.getLength(); i++) {
                Node nodo = listado.item(i);
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element elementoCliente = (Element) nodo;
                    String id = elementoCliente.getAttribute("id");
                    String nombre = elementoCliente.getElementsByTagName("nombre").item(0).getTextContent();
                    String edad = elementoCliente.getElementsByTagName("edad").item(0).getTextContent();
                    String telefono = elementoCliente.getElementsByTagName("telefono").item(0).getTextContent();
                    String ciudad = elementoCliente.getElementsByTagName("ciudad").item(0).getTextContent();

                    // Construimos Cliente
                    Cliente cliente = new Cliente(id, nombre, edad, ciudad, telefono);
                    listadoClientes.add(cliente);
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return listadoClientes;
    }

    public static void imprimirListado(ArrayList<Cliente> listado) {
        System.out.println("\n############# LISTADO DE CLIENTES ##################");
        for (Cliente cliente : listado) {
            String id = cliente.getId();
            String nombre = cliente.getNombre();
            System.out.println(id + " - " + nombre);
        }
    }

    public static void descargarAXml(ResultSet datos) {
        String ruta = "demo\\src\\main\\resources\\ClientesDesdeBD.xml";
        File archivo = new File(ruta);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;

        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.newDocument();
            Element nodoRaiz = document.createElement("Clientes");
            document.appendChild(nodoRaiz);

            while (datos.next()) {
                Element cliente = document.createElement("Cliente"); // Creamos la etiqueta cliente
                // Escribimos el valor del atributo cogiendo el id de la base de datos
                cliente.setAttribute("id", String.valueOf(datos.getInt("id")));
                Element nombre = document.createElement("nombre");
                // Escribimos el nombre cogiendo el valor de la columna "nombre de la bbdd"
                nombre.appendChild(document.createTextNode(datos.getString("nombre")));
                cliente.appendChild(nombre); // Unimos etiqueta nombre a cliente
                Element edad = document.createElement("edad");
                edad.appendChild(document.createTextNode(datos.getString("edad")));
                cliente.appendChild(edad); // Unimos etiqueta edad a cliente
                Element telefono = document.createElement("telefono");
                telefono.appendChild(document.createTextNode(datos.getString("telefono")));
                cliente.appendChild(telefono); // Unimos etiqueta telefono a cliente

                nodoRaiz.appendChild(cliente); // Unimos el cliente entero a clientes

                // Para almacenar los datos en un fichero xml, con transformer hacemosque no
                // aparezca en 1 sola linea
                TransformerFactory tf = TransformerFactory.newInstance();
                Transformer t = tf.newTransformer();
                // Con indent estamos dando formato al archivo y que aparezca la indentación
                t.setOutputProperty(OutputKeys.INDENT, "yes");
                DOMSource ds = new DOMSource(document); // Crea un objeto DOM en memoria a partir del document
                StreamResult sr = new StreamResult(archivo);
                t.transform(ds, sr);
            }
            System.out.println("Archivo xml creado.");

        } catch (ParserConfigurationException | DOMException | SQLException | TransformerException e) {
            e.printStackTrace();
        }

    }

}
