public class Warenkorb {
    private final int MAX_BURGERANZAHL = 10;

    private Burger[] burgerListe;
    private Burger aktiverBurger;
    protected static boolean inBearbeitung;

    private int size;

    public Warenkorb() {
        burgerListe = new Burger[MAX_BURGERANZAHL];
        aktiverBurger = null;
        inBearbeitung = false;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Burger[] getBurgerListe() {
        return burgerListe;
    }

    public Burger getAktiverBurger() {
        return aktiverBurger;
    }

    public boolean isInBearbeitung() {
        return inBearbeitung;
    }

    public void beendeAktiverBurger() {
        aktiverBurger = null;
    }

    /**
     * Fuegt dem Warenkorb einen neuen Burger hinzu.
     * @param neuerBurger neuer Burger
     */
    public boolean add(Burger neuerBurger) {
        if(size > MAX_BURGERANZAHL) {
            System.err.println("FEHLER: Es koennen maximal 10 Burger in einer Bestellung aufgenommen werden!");
            return false;
        }
        burgerListe[size] = neuerBurger;
        size++;
        aktiverBurger = neuerBurger;
        inBearbeitung = true;
        return true;
    }
}
