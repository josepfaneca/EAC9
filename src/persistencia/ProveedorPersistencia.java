package persistencia;

import principal.GestioExcursionsExcepcio;
import principal.Desti;

/**
 *
 * @author FTA
 */
public interface ProveedorPersistencia {
    public void desarDesti(String nomFitxer, Desti desti)throws GestioExcursionsExcepcio;
    public void carregarDesti(String nomFitxer)throws GestioExcursionsExcepcio;
}
