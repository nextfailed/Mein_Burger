import java.util.ArrayList;
import java.util.Arrays;

public class Lager {

    private final ArrayList<Zutat> zutatenkatalog;
    
    public Lager() {
        zutatenkatalog = generiereKatalog();
    }

    public ArrayList<Zutat> getZutatenkatalog() {
        return zutatenkatalog;
    }

    /**
     * Zutat wird mit ihrer jeweiligen Nummer im Array gesucht und herausgegeben
     * @param nummer Zutatennummer
     * @return Zutat
     */
    public Zutat getZutat(int nummer){
        for(Zutat current : zutatenkatalog){
            if(current.getNummer() == nummer) {
                return current;
            }
        }

        System.err.println("Zutat mit der Nummer " + nummer + " konnte nicht gefunden werden.");
        return null;
    }

    /**
     * Erzeugt den Standartkatalog, der von Anfang an existiert.
     * Beim Erstellen des Lagers wird der Katalog automatisch in
     * die ArrayListe {@code zutatenkatalog} gespeichert.
     * Die Liste kann ausserhalb der Laufzeit erweitert werde.
     */
    private ArrayList<Zutat> generiereKatalog() {
        
        return new ArrayList<>(Arrays.asList(
        // Broetchen
        new Broetchen(10, "Hamburger (Standard)", 0.85f, 27, 90).setVegetarisch().setClassic(true),
        new Broetchen(11, "Hamburger Sesam", 0.95f, 90, 27).setVegetarisch().setClassic(true),
        new Broetchen(12, "Vegan-Broetchen", 0.55f, 240, 34).setVegan(),
        new Broetchen(13, "Ciabatta", 0.45f, 330, 41).setVegetarisch(),

        // Bratlinge
        new Bratling(20, "Rindfleisch (Original)", 1.85f, 270, 25).setNonVegan().setClassic(true),
        new Bratling(21, "Haenchenfleisch gegrillt", 1.15f, 180, 11).setNonVegan().setClassic(true),
        new Bratling(22, "Falafel-Bratling", 1.45f, 210, 21).setVegan(),
        new Bratling(23, "Gemuese Bratling", 1.75f, 240, 25).setVegetarisch(),

        // Salat
        new Salat(30, "Eisbergsalat", 0.18f),
        new Salat(31, "Roculasalat", 0.25f),

        // Gemuese
        new Gemuese(40, "Tomate", 0.25f, 3, 3).setClassic(true),
        new Gemuese(41, "Salzgurke", 0.15f, 4, 2).setClassic(true),
        new Gemuese(42, "Rote Zwiebelringe", 0.08f, 5, 2).switchScheibenart(),
        new Gemuese(43, "Jalapeno-Ringe", 0.08f, 5, 2).switchScheibenart(),

        // Sauce
        new Sauce(50, "Ketchup", 0.10f, 10, Geschmack.NORMAL).setVegan().setClassic(true),
        new Sauce(51, "Sandwich-Sauce", 0.15f, 10, Geschmack.NORMAL).setVegetarisch().setClassic(true),
        new Sauce(52, "Chili-Sauce", 0.25f, 8, Geschmack.SCHARF).setVegan(),
        new Sauce(53, "Honig-Senf-Sauce", 0.18f, 8, Geschmack.SUESS).setVegetarisch()

        // Platz fuer Erweitungen
        ));
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for(Zutat aktuelleZutat : zutatenkatalog) {
            stringBuilder.append(aktuelleZutat.toString()).append("\n");
        }

        return stringBuilder.toString();
    }
}
