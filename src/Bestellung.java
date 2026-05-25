import java.util.ArrayList;

public class Bestellung {

    private ArrayList<Burger> burgerListe = new ArrayList<>();

    public void burgerHinzufuegen(Burger neuerBurger) {
        burgerListe.add(neuerBurger);
    }

    public Burger[] getBurger() {
        return (Burger[]) burgerListe.toArray();
    }
}
