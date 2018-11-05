package persistencia;

import com.sun.org.apache.xerces.internal.dom.DocumentImpl;
import components.*;
import java.io.File;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import principal.*;

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
        //extret del material, sempre igual
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            doc = new DocumentImpl();
            Element root = doc.createElement("destí");
            root.setAttribute("codi", Integer.toString(desti.getCodi()));
            root.setAttribute("continent", desti.getContinent());
            root.setAttribute("nom", desti.getNom());

            for (Component comp : desti.getComponents()) {
                Element element;
                if (comp == null) {
                    throw new GestioExcursionsExcepcio("1");
                } else if (comp instanceof Guia) {
                    Guia guia = (Guia) comp;
                    element = doc.createElement("Guia");
                    element.setAttribute("codi", guia.getCodi());
                    element.setAttribute("nom", guia.getNom());
                    element.setAttribute("adreca", guia.getAdreca());
                    element.setAttribute("telefon", guia.getTelefon());
                    root.appendChild(element);
                } else if (comp instanceof VisitaLliure) {
                    VisitaLliure vll = (VisitaLliure) comp;
                    element = doc.createElement("VisitaLliure");
                    element.setAttribute("codi", vll.getCodi());
                    element.setAttribute("nom", vll.getNom());
                    element.setAttribute("adreca", vll.getAdreca());
                    element.setAttribute("coordenades", vll.getCoordenades());
                    element.setAttribute("durada", Integer.toString(vll.getDurada()));
                    root.appendChild(element);
                } else if (comp instanceof VisitaPagament) {
                    VisitaPagament vp = (VisitaPagament) comp;
                    element = doc.createElement("VisitaPagament");
                    element.setAttribute("codi", vp.getCodi());
                    element.setAttribute("nom", vp.getNom());
                    element.setAttribute("adreca", vp.getAdreca());
                    element.setAttribute("coordenades", vp.getCoordenades());
                    element.setAttribute("durada", Integer.toString(vp.getDurada()));
                    element.setAttribute("preu", Double.toString(vp.getPreu()));
                    root.appendChild(element);
                } //s'han d'afegir els components que hi han dins d'Excursió, en cas que 
                //n'hi hagi;
                else if (comp instanceof Excursio) {
                    Excursio exc = (Excursio) comp;
                    element = doc.createElement("Excursio");
                    element.setAttribute("codi", exc.getCodi());
                    element.setAttribute("nom", exc.getNom());
                    element.setAttribute("preu", Double.toString(exc.getPreu()));
                    root.appendChild(element);

                    //recórrer el Map d'excursió i afegir com a fills d'Excursió:
                    //guies, visitaPag i VisitaLliure
                    for (Component compExc : exc.getComponents().values()) {
                        if (comp == null) {
                            throw new GestioExcursionsExcepcio("1");
                        } else if (compExc instanceof Guia) {
                            Guia guia = (Guia) comp;
                            element = doc.createElement("Guia");
                            element.setAttribute("codi", guia.getCodi());
                            element.setAttribute("nom", guia.getNom());
                            element.setAttribute("adreca", guia.getAdreca());
                            element.setAttribute("telefon", guia.getTelefon());
                            root.appendChild(element);
                        } else if (compExc instanceof VisitaLliure) {
                            VisitaLliure vll = (VisitaLliure) comp;
                            element = doc.createElement("VisitaLliure");
                            element.setAttribute("codi", vll.getCodi());
                            element.setAttribute("nom", vll.getNom());
                            element.setAttribute("adreca", vll.getAdreca());
                            element.setAttribute("coordenades", vll.getCoordenades());
                            element.setAttribute("durada", Integer.toString(vll.getDurada()));
                            root.appendChild(element);
                        } else if (compExc instanceof VisitaPagament) {
                            VisitaPagament vp = (VisitaPagament) comp;
                            element = doc.createElement("VisitaPagament");
                            element.setAttribute("codi", vp.getCodi());
                            element.setAttribute("nom", vp.getNom());
                            element.setAttribute("adreca", vp.getAdreca());
                            element.setAttribute("coordenades", vp.getCoordenades());
                            element.setAttribute("durada", Integer.toString(vp.getDurada()));
                            element.setAttribute("preu", Double.toString(vp.getPreu()));
                            root.appendChild(element);
                        } else {
                            throw new GestioExcursionsExcepcio("GestorXML.model");
                        }
                    }
                } else {
                    throw new GestioExcursionsExcepcio("GestorXML.model");
                }
            }
        } catch (Exception ex) {
            throw new GestioExcursionsExcepcio("GestorXML.model");
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
        Node rootNode = doc.getFirstChild();

        Element rootElement = (Element) rootNode;

        //si es null o no és destí, llença excepció GestorXML.carrega
        if (rootElement == null) {
            throw new GestioExcursionsExcepcio("GestorXML.carrega");
        }
        if (!rootElement.getNodeName().equals("destí")) {
            throw new GestioExcursionsExcepcio(("GestorXML.carrega"));
        }
        //dades del Destí
        int codi = Integer.parseInt(rootElement.getAttribute("codi"));
        String nom = rootElement.getAttribute("nom");
        String continent = rootElement.getAttribute("continent");

        this.desti = new Desti(nom, continent, codi);//nou constructor Desti

        NodeList nodeList = rootElement.getChildNodes(); //llista de fills per
        //recòrrer

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node fill = nodeList.item(i);
            //casting i llençar excepció en cas de null
            if ((Element) fill == null) {
                throw new GestioExcursionsExcepcio("GestorXML.carrega");
            }
            //variables per a crear els components
            Component component;
            String codiC = ((Element) fill).getAttribute("codi");
            String nomC = ((Element) fill).getAttribute("nom");
            String adreca = ((Element) fill).getAttribute("adreca");
            String telefon = ((Element) fill).getAttribute("telefon");
            String coordenades = ((Element) fill).getAttribute("coordenades");
            int durada;
            double preu;
            //per a cada tipus de component fer un constructor i afegir a components
            switch (fill.getNodeName()) {
                case ("Guia"):
                    component = new Guia(codiC, nomC, adreca, telefon);
                    this.desti.addGuia(component);
                    break;
                case ("VisitaLliure"):
                    durada = Integer.parseInt(((Element) fill).getAttribute("durada"));
                    component = new VisitaLliure(codiC, nomC, adreca, coordenades, durada);
                    this.desti.addVisitaLliure(component);
                    break;
                case ("VisitaPagament"):
                    durada = Integer.parseInt(((Element) fill).getAttribute("durada"));
                    preu = Double.parseDouble(((Element) fill).getAttribute("preu"));
                    component = new VisitaPagament(codiC, nomC, adreca, coordenades, durada, preu);
                    this.desti.addVisitaPagament(component);
                    break;
                case ("Excursio"):
                    preu = Double.parseDouble(((Element) fill).getAttribute("preu"));
                    component = new Excursio(codiC, nomC, (int) preu);
                    this.desti.addExcursio(component);
                    break;
                default:
                    throw new GestioExcursionsExcepcio("GestorXML.carrega");
            }
            /* si és una excursió, haureu d'afegir en l'excursió 
             *   les visites lliures de pagament, les visites de pagament 
                i els guies de l'excursió
             */

            if (component instanceof Excursio) {
                NodeList nodeListExc = fill.getChildNodes();
                //recòrrer els fills d'Excursió i afegir-los a Excursió
                for (int j = 0; j < nodeListExc.getLength(); j++) {
                    Node fillExc = nodeListExc.item(j);

                    //variables
                    Component compExc;
                    String codiE = ((Element) fillExc).getAttribute("codi");
                    String nomE = ((Element) fillExc).getAttribute("nom");
                    String adrecaE = ((Element) fillExc).getAttribute("adreca");
                    String telefonE = ((Element) fillExc).getAttribute("telefon");
                    String coordenadesE = ((Element) fillExc).getAttribute("coordenades");
                    int duradaE;
                    double preuE;
                    //switch per saber quina classe de component és
                    //Guia, VLL o VP.
                    switch (fillExc.getNodeName()) {
                        case ("Guia"):
                            compExc = new Guia(codiE, nomE, adrecaE, telefonE);
                            ((Excursio) component).addComponent(((Guia) compExc).getCodi(), compExc);
                            break;
                        case ("VistaLliure"):
                            duradaE = Integer.parseInt(((Element) fillExc).getAttribute("durada"));
                            compExc = new VisitaLliure(codiE, nomE, adrecaE, coordenadesE, duradaE);
                            ((Excursio) component).addComponent(((VisitaLliure) compExc).getCodi(), compExc);
                            break;
                        case ("VisitaPagament"):
                            duradaE = Integer.parseInt(((Element) fillExc).getAttribute("durada"));
                            preuE = Double.parseDouble(((Element) fillExc).getAttribute("preu"));
                            compExc = new VisitaPagament(codiE, nomE, adrecaE, coordenadesE, duradaE, preuE);
                            ((Excursio) component).addComponent(((VisitaPagament) compExc).getCodi(), compExc);
                            break;
                        default:
                            throw new GestioExcursionsExcepcio("GestorXML.carrega");
                    }
                }
            }
        }
    }
}
