package utilidades;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class XMLSAXManager extends DefaultHandler {

    private StringBuffer sb;

    public void startDocument() {
        System.out.println("Inicio documento");
    }

    public void startElement(String Uri, String localName, String qName, Attributes atts) {
        if (qName.equals("nombre")) {
            sb = new StringBuffer();
        }
    }

    public void characters(char[] ch, int start, int length) {
        if (sb != null) {
            sb.append(new String(ch, start, length));
        }
    }

    public void endElement(String uri, String localName, String qName) {
        if (qName.equals("ciudad")) {
            System.out.println(sb);
            sb = null; // Reseteo para el siguiente
        }
    }

    public void endDocument() {
        System.out.println("Final del documento");
    }
}
