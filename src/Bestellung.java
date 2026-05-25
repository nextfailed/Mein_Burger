import java.util.ArrayList;

public class Bestellung {

    private ArrayList<Burger> burgerListe = new ArrayList<>();
    private ArrayList<Zutat> zutatenListe = new ArrayList<>();
    private Burger aktuellerBurger;

    // Getter
    public Burger[] getAlleBurger() {
        return (Burger[]) burgerListe.toArray();
    }

    // Klassen-Methoden
    /**
     * Fuegt der Bestellung einen neuen Burger mit dem uebergebenen Namen hinzu.
     *
     * @param burgerName Name des Burgers
     */
    public void neuerBurger(String burgerName) {
        Burger neuerBurger = new Burger(burgerName);
        burgerListe.add(neuerBurger);
        aktuellerBurger = neuerBurger;
    }

    /**
     * Fuegt dem aktuellen Burger die Zutat mit der uebergebenen Nummer hinzu.
     * @param nummer Zutatennummer
     */
    public void zutatHinzufueger(int nummer) {

    }

    public void ok() {
        //TODO
    }

    public void bestellen() {
        //TODO
    }
}
