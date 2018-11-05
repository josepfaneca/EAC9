
/*
 * Classe que defineix una excursió. Una excursió es defineix per un codi, nom i 
 * un preu. A més, contindrà un vector amb guies, visites
 * amb entrada lliure i visites de pagament.
 */
package principal;

import components.VisitaPagament;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author FTA
 */
public class Excursio implements Component, Map {

    private String codi;
    private String nom;
    private double preu;
    //private Component[] components = new Component[120];
    private Map<String,Component> components = new HashMap <>();
    

    /*
     TODO CONSTRUCTOR
     Paràmetres: valors per tots els atributs de la classe menys els vectors.
     Accions:
     - Assignar als atributs corresponents els valors passats com a paràmetres
     */
    public Excursio(String pCodi, String pNom, int pPreu) {
        codi = pCodi;
        nom = pNom;
        preu = pPreu;
    }
    /*
     TODO Mètodes accessors
     */
    public String getCodi() {
        return codi;
    }

    public void setCodi(String codi) {
        this.codi = codi;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPreu() {
        return preu;
    }

    public void setPreu(double preu) {
        this.preu = preu;
    }
    
    public Map<String,Component> getComponents(){
        return components;
    }
    //mètodes interface Map
    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public Object get(Object key) {
        return null;
    }

    @Override
    public Object put(Object key, Object value) {
        return null;
    }

    @Override
    public Object remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map m) {
    }

    @Override
    public void clear() {
    }

    @Override
    public Set keySet() {
        return null;
    }

    @Override
    public Collection values() {
        return null;
    }

    @Override
    public Set entrySet() {
        return null;
    }

    /*
     TODO
     Paràmetres: cap
     Accions:
     - Demanar a l'usuari les dades per consola per crear una nova excursió. Les dades
     a demanar són les que necessita el constructor.
     - També heu de tenir en compte que el nom pot ser una frases, per exemple, 
     Museu de la ciència.
     Retorn: La nova excursió creada-.
     */
    public static Excursio addExcursio() {

        String codi;
        String nom;
        int preu;

        System.out.println("Codi de l'excursió:");
        codi = DADES.next();
        DADES.nextLine(); //Neteja buffer
        System.out.println("Nom de l'excursió:");
        nom = DADES.nextLine();
        System.out.println("Preu de l'excursió:");
        preu = DADES.nextInt();

        return new Excursio(codi, nom, preu);
    }

    @Override
    public void updateComponent() {
        System.out.println("\nCodi de l'excursió: " + codi);
        System.out.println("\nEntra el nou codi:");
                
        codi = DADES.next();
        DADES.nextLine();
        System.out.println("\nNom de l'excursió: " + nom);
        System.out.println("\nEntra el nou nom:");
        nom = DADES.nextLine();
        System.out.println("\nPreu de l'excursió: " + preu);
        System.out.println("\nEntra el nou preu:");
        preu = DADES.nextDouble();
    }

    /*
     Afegir components
     */
    public void addComponent(String codiComponent, Component component) {
        components.put(codiComponent,component);
        if (component instanceof VisitaPagament) {
            preu += ((VisitaPagament) component).getPreu();
        }
    }

    @Override
    public void showComponent() {
        System.out.println("\nLes dades de l'excursió amb codi " + codi + " són:");
        System.out.println("\nNom:" + nom);
        System.out.println("\nPreu:" + preu);
        
        Iterator it = components.keySet().iterator(); 
        while (it.hasNext()) {
            String codiComponent = it.toString();
            System.out.println("Clau: " + codiComponent + " Component: " + components.get(codiComponent));
        }
    }
    
    public static void main(String[] args) {
        Excursio test = addExcursio();
        //test.updateComponent();
        //test.showComponent();
        test.addComponent("1", test);
    }

    

}
