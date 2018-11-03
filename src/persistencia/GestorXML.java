package persistencia;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import principal.GestioExcursionsExcepcio;
import principal.Desti;

/**
 *
 * @author FTA
 */
public class GestorXML implements ProveedorPersistencia {

    private Document doc;
    private Desti desti;

    public Desti getDesti() {
        return desti;
    }

    public void setDesti(Desti desti) {
        this.desti = desti;
    }

    @Override
    public void desarDesti(String nomFitxer, Desti desti) throws GestioExcursionsExcepcio {
        construeixModel(desti);
        desarModel(nomFitxer);
    }

    @Override
    public void carregarDesti(String nomFitxer) throws GestioExcursionsExcepcio {
        carregarFitxer(nomFitxer);
        fitxerDesti();
    }

    /*Paràmetres: Destí a partir de la qual volem construir el model
     *
     *Acció: 
     * - Llegir els atributs de l'objecte Desti passat per paràmetre 
     *   per construir un model (document XML) sobre el Document doc (atribut de GestorXML).
     *
     * - L'arrel del document XML és "desti". Aquesta arrel, l'heu d'afegir 
     *   a doc. Un cop fet això, heu de recórrer l'ArrayList components de 
     *   Desti i per a cada component, afegir un fill a doc. Cada fill 
     *   tindrà com atributs els atributs de l'objecte (codi, nom, …).
     *
     * - Si es tracta d'una excursió, a més, heu d'afegir fills addicionals amb 
     *   les visites de lliure pagament, les visites de pagament i els guies assignats a excursió .
     *
     *Retorn: cap
     */
    public void construeixModel(Desti desti) throws GestioExcursionsExcepcio {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            File f = new File("FITXER");
            doc = builder.parse(f);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            System.out.println("Error en la lectura del document: "+ e);
        }
        
        
       
    }

    public void desarModel(String nomFitxer) throws GestioExcursionsExcepcio {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(doc);
            File f = new File(nomFitxer + ".xml");
            StreamResult result = new StreamResult(f);
            transformer.transform(source, result);
        } catch (Exception ex) {
            throw new GestioExcursionsExcepcio("GestorXML.desar");
        }
    }

    public void carregarFitxer(String nomFitxer) throws GestioExcursionsExcepcio {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            File f = new File(nomFitxer + ".xml");
            doc = builder.parse(f);
        } catch (Exception ex) {
            throw new GestioExcursionsExcepcio("GestorXML.carrega");
        }
    }

    /*Paràmetres: cap
     *
     *Acció: 
     * - Llegir el fitxer del vostre sistema i carregar-lo sobre l'atribut doc, 
     *   per assignar valors als atributs de Desti i la resta d'objectes que formen 
     *   els components de Desti.
     *    
     * - Primer heu de crear l'objecte Desti a partir de l'arrel del document XML
     *   per després recórrer el doc i per cada fill, afegir un objecte a l'atribut 
     *   components de Desti mitjançant el mètode escaient de la classe Desti.
    
     * - Si l'element del document XML que s'ha llegit és una excursió, recordeu 
     *   que a més d'afegir-los a components, també haureu d'afegir en l'excursió 
     *   les visites lliures de pagament, les visites de pagament i els guies de
     *   l'excursió.
     *
     *Retorn: cap
     */
    private void fitxerDesti() throws GestioExcursionsExcepcio {

    }
}
