package persistencia;

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
        //extret del material
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            doc = builder.newDocument();
            Element arrel = doc.createElement("destí");
            arrel.setAttribute("codi", Integer.toString(desti.getCodi()));
            arrel.setAttribute("continent",desti.getContinent());
            arrel.setAttribute("nom",desti.getNom());
            doc.appendChild(arrel);

            for (Component comp : desti.getComponents()) {
                Element fill;
                if (comp == null) {
                    throw new GestioExcursionsExcepcio("1");
                } else if (comp instanceof Guia) {
                    Guia guia = (Guia) comp;
                    fill = doc.createElement("Guia");
                    fill.setAttribute("codi", guia.getCodi());
                    fill.setAttribute("nom", guia.getNom());
                    fill.setAttribute("adreca", guia.getAdreca());
                    fill.setAttribute("telefon", guia.getTelefon());
                    arrel.appendChild(fill);
                } else if (comp instanceof VisitaLliure) {
                    VisitaLliure vll = (VisitaLliure) comp;
                    fill = doc.createElement("VisitaLliure");
                    fill.setAttribute("codi", vll.getCodi());
                    fill.setAttribute("nom", vll.getNom());
                    fill.setAttribute("adreca", vll.getAdreca());
                    fill.setAttribute("coordenades", vll.getCoordenades());
                    fill.setAttribute("durada", Integer.toString(vll.getDurada()));
                    arrel.appendChild(fill);
                } else if (comp instanceof VisitaPagament) {
                    VisitaPagament vp = (VisitaPagament) comp;
                    fill = doc.createElement("VisitaPagament");
                    fill.setAttribute("codi", vp.getCodi());
                    fill.setAttribute("nom", vp.getNom());
                    fill.setAttribute("adreca", vp.getAdreca());
                    fill.setAttribute("coordenades", vp.getCoordenades());
                    fill.setAttribute("durada", Integer.toString(vp.getDurada()));
                    fill.setAttribute("preu", Double.toString(vp.getPreu()));
                    arrel.appendChild(fill);
                } //s'han d'afegir els components que hi han dins d'Excursió, en cas que 
                //n'hi hagi;
                else if (comp instanceof Excursio) {
                    Excursio exc = (Excursio) comp;
                    fill = doc.createElement("Excursio");
                    fill.setAttribute("codi", exc.getCodi());
                    fill.setAttribute("nom", exc.getNom());
                    fill.setAttribute("preu", Double.toString(exc.getPreu()));
                    arrel.appendChild(fill);

                    //recórrer el Map d'excursió i afegir com a fills d'Excursió:
                    //guies, visitaPag i VisitaLliure
                    for (Component compExc : exc.getComponents().values()) {
                        Element fillExc;
                        if (comp == null) {
                            throw new GestioExcursionsExcepcio("1");
                        } else if (compExc instanceof Guia) {
                            Guia guia = (Guia) compExc;
                            fillExc = doc.createElement("Guia");
                            fillExc.setAttribute("codi", guia.getCodi());
                            fillExc.setAttribute("nom", guia.getNom());
                            fillExc.setAttribute("adreca", guia.getAdreca());
                            fillExc.setAttribute("telefon", guia.getTelefon());
                            fill.appendChild(fillExc);
                        } else if (compExc instanceof VisitaLliure) {
                            VisitaLliure vll = (VisitaLliure) compExc;
                            fillExc = doc.createElement("VisitaLliure");
                            fillExc.setAttribute("codi", vll.getCodi());
                            fillExc.setAttribute("nom", vll.getNom());
                            fillExc.setAttribute("adreca", vll.getAdreca());
                            fillExc.setAttribute("coordenades", vll.getCoordenades());
                            fillExc.setAttribute("durada", Integer.toString(vll.getDurada()));
                            fill.appendChild(fillExc);
                        } else if (compExc instanceof VisitaPagament) {
                            VisitaPagament vp = (VisitaPagament) compExc;
                            fillExc = doc.createElement("VisitaPagament");
                            fillExc.setAttribute("codi", vp.getCodi());
                            fillExc.setAttribute("nom", vp.getNom());
                            fillExc.setAttribute("adreca", vp.getAdreca());
                            fillExc.setAttribute("coordenades", vp.getCoordenades());
                            fillExc.setAttribute("durada", Integer.toString(vp.getDurada()));
                            fillExc.setAttribute("preu", Double.toString(vp.getPreu()));
                            fill.appendChild(fillExc);
                        } else {
                            throw new GestioExcursionsExcepcio("GestorXML.model");
                        }
                    }
                } else {
                    throw new GestioExcursionsExcepcio("GestorXML.model");
                }
            }
        } catch (ParserConfigurationException | DOMException | GestioExcursionsExcepcio ex) {
            throw new GestioExcursionsExcepcio("GestorXML.model");
        }
    }

    public void desarModel(String nomFitxer) throws GestioExcursionsExcepcio {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            //https://stackoverflow.com/questions/22790146/create-xml-file-with-linebreaks
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            //https://stackoverflow.com/questions/1384802/java-how-to-indent-xml-generated-by-transformer
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
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
        if (nodeList.getLength()==0) {
            throw new GestioExcursionsExcepcio(("GestorXML.carrega"));
        }
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node fill = nodeList.item(i);
            //https://stackoverflow.com/questions/21170909/error-org-apache-xerces-dom-deferredtextimpl-cannot-be-cast-to-org-w3c-dom-elem/21382952
            if (fill.getNodeType() != Node.ELEMENT_NODE) continue;//
            //casting i llençar excepció en cas de null
            if ((Element) fill == null) { //
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
                    //https://stackoverflow.com/questions/21170909/error-org-apache-xerces-dom-deferredtextimpl-cannot-be-cast-to-org-w3c-dom-elem/21382952
                    if (fillExc.getNodeType()!= Node.ELEMENT_NODE) continue;
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
