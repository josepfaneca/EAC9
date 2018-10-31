/*
 * Classe que defineix un desti. Un destí es defineix per un codi, un nom i continent 
 * al qual pertany el destí. A més, contindrà un vector amb excursions, guies, visites 
 * amb entrada lliure  i visites de pagament.
 */
package principal;

import components.Guia;
import components.VisitaLliure;
import components.VisitaPagament;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author FTA
 */
public class Desti implements Component, List {

    private int codi;
    private static int properCodi = 1; //El proper codi a assignar
    private String nom;
    private String continent;
    private  List<Component> components = new ArrayList<>();
    private int pCodi; //atribut de paràmetre pCodi
    //private Component[] components = new Component[220];
    //private int posicioComponents = 0; //Priemra posició buida del vector de components

    //private GestioExcursionsExcepcio existCom = new GestioExcursionsExcepcio("3");
    private String missatge;

    /*
     TODO
     CONSTRUCTOR
     Paràmetres: valors per tots els atributs de la classe menys els vectors i el
     codi.
     Accions:
     - Assignar als atributs corresponents els valors passats com a paràmetres
     - Assignar a l'atribut codi el valor de l'atribut properCodi i actualitzar
     properCodi amb el següent codi a assignar.
     */
    public Desti(String pNom, String pContinent) {
        codi = properCodi;
        properCodi++;
        nom = pNom;
        continent = pContinent;
    }
    //constructor tractament XML
    public Desti(int codi, String nom, String continent, int pCodi, String missatge) {
        this.codi = codi;
        this.nom = nom;
        this.continent = continent;
        this.pCodi = pCodi;
        this.missatge = missatge;
    }
    
    /*
     TODO Mètodes accessors    
     */
    public int getCodi() {
        return codi;
    }

    public void setCodi(int codi) {
        this.codi = codi;
    }

    public static int getProperCodi() {
        return properCodi;
    }

    public static void setProperCodi() {
        properCodi++;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }
    //puc accedir a components i utilitzar els mètodes de List
    public List<Component> getComponents() {
        return components;
    }

//    public void setComponents(List<Component> components) {
//        this.components = components;
//    }
    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return null;
    }

    @Override
    public Object[] toArray(Object[] a) {
        return null;
    }

    @Override
    public boolean add(Object e) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection c) {
        return false;
    }

    @Override
    public boolean addAll(Collection c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public Object get(int index) {
        return null;
    }

    @Override
    public Object set(int index, Object element) {
        return null;
    }

    @Override
    public void add(int index, Object element) {

    }

    @Override
    public Object remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator listIterator() {
        return null;
    }

    @Override
    public ListIterator listIterator(int index) {
        return null;
    }

    @Override
    public List subList(int fromIndex, int toIndex) {
        return null;
    }

    /*
    TODO
     Paràmetres: cap
     Accions:
     - Demanar a l'usuari les dades per consola per crear un nou destí. Les dades
     a demanar són les que necessita el constructor.
     - Heu de tenir en compte que el nom pot ser una frase, per exemple, New York.
     Retorn: El nou destí creat.
     */
    public static Desti addDesti() {

        String nom;
        String continent;

        System.out.println("Nom del destí:");
        nom = DADES.nextLine();
        System.out.println("Continent del destí:");
        continent = DADES.nextLine();
        return new Desti(nom, continent);
    }

    @Override
    public void updateComponent() {

        System.out.println("\nNom del destí: " + nom);
        System.out.println("\nEntra el nou nom:");
        nom = DADES.next();
        System.out.println("\nContinent del destí: " + continent);
        System.out.println("\nEntra el nou continent:");
        continent = DADES.next();
    }

    @Override
    public void showComponent() {
        System.out.println("\nLes dades del destí amb codi " + codi + " són:");
        System.out.println("\nNom:" + nom);
        System.out.println("\nContinent:" + continent);
    }

    public void addGuia(Guia nouGuia) throws GestioExcursionsExcepcio {

        //nouGuia = Guia.addGuia(); de moment ho deixem així

        if (selectComponent(1, nouGuia.getCodi()) != -1) {
            throw new GestioExcursionsExcepcio("3");
        } else {
            boolean insertat;
            insertat = components.add(this);
            if (insertat) {
                System.out.println("S'ha insertat un nou guia amb el codi:[" + this.getCodi() + "]");
                System.out.println(components.size());
            } else {
                System.out.println("NO S'HA INSERT EL GUIA:[" + this.getCodi() + "]");

            }

        }
    }
//    public void addGuia() throws GestioExcursionsExcepcio {
//
//        Guia nouGuia = Guia.addGuia();
//
//        if (selectComponent(1, nouGuia.getCodi()) != -1) {
//            throw new GestioExcursionsExcepcio("3");
//        } else {
//            boolean insertat;
//            insertat = components.add(nouGuia);
//            if (insertat) {
//                System.out.println("S'ha insertat un nou guia amb el codi:[" + nouGuia.getCodi() + "]");
//            } else {
//                System.out.println("NO S'HA INSERT EL GUIA:[" + nouGuia.getCodi() + "]");
//
//            }
//
//        }
//    }

    public void addVisitaLliure(VisitaLliure novaVisita) throws GestioExcursionsExcepcio {

        //novaVisita = VisitaLliure.addVisitaLliure(); modificat de moment

        if (selectComponent(2, novaVisita.getCodi()) != -1) {
            throw new GestioExcursionsExcepcio("3");
        } else {
            components.add(novaVisita);
        }

    }
//    public void addVisitaLliure() throws GestioExcursionsExcepcio {
//
//        VisitaLliure novaVisita = VisitaLliure.addVisitaLliure();
//
//        if (selectComponent(2, novaVisita.getCodi()) != -1) {
//            throw new GestioExcursionsExcepcio("3");
//        } else {
//            components.add(novaVisita);
//        }
//
//    }

    public void addVisitaPagament(VisitaPagament novaVisita) throws GestioExcursionsExcepcio {

        //novaVisita = VisitaPagament.addVisitaPagament(); modificat de moment

        if (selectComponent(3, novaVisita.getCodi()) != -1) {
            throw new GestioExcursionsExcepcio("3");
        } else {
            components.add(novaVisita);
        }
    }
//    public void addVisitaPagament() throws GestioExcursionsExcepcio {
//
//        VisitaPagament novaVisita = VisitaPagament.addVisitaPagament();
//
//        if (selectComponent(3, novaVisita.getCodi()) != -1) {
//            throw new GestioExcursionsExcepcio("3");
//        } else {
//            components.add(novaVisita);
//        }
//    }

    public void addExcursio(Excursio novaExcursio) throws GestioExcursionsExcepcio {

        //novaExcursio = Excursio.addExcursio(); modificat de moment

        if (selectComponent(4, novaExcursio.getCodi()) != -1) {
            throw new GestioExcursionsExcepcio("3");
        } else {
            components.add(novaExcursio);
        }
    }
//    public void addExcursio() throws GestioExcursionsExcepcio {
//
//        Excursio novaExcursio = Excursio.addExcursio();
//
//        if (selectComponent(4, novaExcursio.getCodi()) != -1) {
//            throw new GestioExcursionsExcepcio("3");
//        } else {
//            components.add(novaExcursio);
//        }
//    }

    public void addComponentExcursio(int tipusComponent) throws GestioExcursionsExcepcio {
        Excursio excursioSel = null;
        int pos = selectComponent(4, null);

        if (pos < 0) {
            throw new GestioExcursionsExcepcio("5");
        } else {

            excursioSel = (Excursio) components.get(pos);

            pos = selectComponent(tipusComponent, null);

            if (pos < 0) {
                throw new GestioExcursionsExcepcio("4");
            } else {
                String codiPoString = Integer.toString(pos);
                excursioSel.addComponent(codiPoString, components.get(pos));
            }
        }
    }

    public int selectComponent(int tipusComponent, String codi) {

        if (codi == null) {
            //Demanem quin tipus de component vol seleccionar i el seu codi
            switch (tipusComponent) {
                case 1:
                    System.out.println("Codi del guia?:");
                    break;
                case 2:
                    System.out.println("Codi de la visita lliure?:");
                    break;
                case 3:
                    System.out.println("Codi de la visita de pagament?:");
                    break;
                case 4:
                    System.out.println("Codi de l'excursió?:");
                    break;
            }

            codi = DADES.next();
        }

        int posElement = -1; //Posició que ocupa el component seleccionat dins el vector de components del destí
        boolean trobat = false;

        //Seleccionem la posició que ocupa el component dins el vector de components
        // del destí 
        //recórrer tota la llista 
        for (int i = 0; i < components.size() && !trobat; i++) {
            if (components.get(i) instanceof Guia && tipusComponent == 1) {
                if (((Guia) components.get(i)).getCodi().equals(codi)) {
                    posElement = i;
                    trobat = true;
                }
            } else if (components.get(i) instanceof VisitaLliure && tipusComponent == 2) {
                if (((VisitaLliure) components.get(i)).getCodi().equals(codi)) {
                    posElement = i;
                    trobat = true;
                }
            } else if (components.get(i) instanceof VisitaPagament && tipusComponent == 3) {
                if (((VisitaPagament) components.get(i)).getCodi().equals(codi)) {
                    posElement = i;
                    trobat = true;
                }
            } else if (components.get(i) instanceof Excursio && tipusComponent == 4) {
                if (((Excursio) components.get(i)).getCodi().equals(codi)) {
                    posElement = i;
                    trobat = true;
                }
            }
        }
        return posElement;
    }

    public static void main(String[] args) {
        Desti test = addDesti();
//
        try {
            test.addGuia(Guia.addGuia());
//            //test.addVisitaLliure();
//            //test.addVisitaPagament();
//            //test.addExcursio();
//            //test.addComponentExcursio(2);*/
//            /*Iterator<Component> it = components.iterator();
//            while (it.hasNext()) {
//                Component next = it.next();
//                System.out.println(next);
//            }
//            test.showComponent();*/
//
        } catch (GestioExcursionsExcepcio e) {
            System.out.println(e.getMessage());

        }
    }
    
}

