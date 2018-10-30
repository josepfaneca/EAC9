package principal;

/**
 *
 * @author fta
 */
public class GestioExcursionsExcepcio extends Exception {

    private final String RESET = "\033[0m";// Text Reset
    private final String RED = "\033[0;31m";// RED

    private String codiCausa = "desconegut";
    private String missatge;

    public GestioExcursionsExcepcio(String codiCausa) {
        this.codiCausa = codiCausa;
        switch (codiCausa) {
            case "1":
                this.missatge = "L'opció ha de ser correcta";
                break;
            case "2":
                this.missatge = "Ja no hi caben més components";
                break;
            case "3":
                this.missatge = "El codi està repetit";
                break;
            case "4":
                this.missatge = "El codi no existeix";
                break;
            case "5":
                this.missatge = "No existeix aquesta excursió";
                break;
            case "GestorXML.model":
                this.missatge = "No s'ha pogut crear el model XML per desar el destí";
                break;
            case "GestorXML.desar":
                this.missatge = "No s'ha pogut desar el destí a causa d'error d'entrada/sortida";
                break;
            case "GestorXML.carrega":
                this.missatge = "No s'ha pogut carregar el destí a causa d'error d'entrada/sortida";
                break;
            default:
                this.missatge = "Error desconegut";
                break;
        }
    }
    
    //getters and setters

    public String getCodiCausa() {
        return codiCausa;
    }

    public void setCodiCausa(String codiCausa) {
        this.codiCausa = codiCausa;
    }
    

    
    @Override
    public String getMessage() {
        return "ERROR: [" + RED + codiCausa + RESET +"] : " + RED + missatge + RESET;
    }

}
