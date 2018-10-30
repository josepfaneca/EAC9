package persistencia;

import principal.GestioExcursionsExcepcio;
import principal.Desti;


/**
 *
 * @author FTA
 */
public class GestorPersistencia {
    private GestorXML gestor;

    public GestorXML getGestor() {
        return gestor;
    }

    public void setGestor(GestorXML pGestor) {
        gestor = pGestor;
    }

    public void desarDesti(String tipusPersistencia, String nomFitxer, Desti desti) throws GestioExcursionsExcepcio{
        if (tipusPersistencia.equals("XML")) {
            gestor = new GestorXML();
            gestor.desarDesti(nomFitxer, desti);
        }
    }

    public void carregarDesti(String tipusPersistencia, String nomFitxer) throws GestioExcursionsExcepcio{
       
        if (tipusPersistencia.equals("XML")) {
            gestor = new GestorXML();
            gestor.carregarDesti(nomFitxer);
        }
    }
 
}