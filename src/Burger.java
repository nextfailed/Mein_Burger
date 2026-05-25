/**
 * Burger dient der Erstellung eines eigenen Burgers mit beliebigen Zutaten und
 * berechnet dessen Hoehe und den Preis.
 */
public class Burger {
    public static final int MAX_ZUTATENANZAHL = 9;

    private String name;
    private float gesamtHoehe = 0.0f;
    private float gesamtPreis;
    private Zutat[] zutaten = new Zutat[MAX_ZUTATENANZAHL];

    public Burger(String name) {
        this.name = name;
    }

    // Getter
    public String getName() {
        return name;
    }

    public float getGesamtHoehe() {
        return gesamtHoehe;
    }

    public float getGesamtPreis() {
        return gesamtPreis;
    }


    /**
     * Berechnet die Hoehe des Burgers anhand aller verwendeten Zutaten.
     */
    public void berechneHoehe() {
        for(Zutat aktuelleZutat : zutaten) {
            if(aktuelleZutat instanceof Salat || aktuelleZutat instanceof Sauce) {
                continue;
            }
            else {
                gesamtHoehe += aktuelleZutat.berechneHoehe();
            }
        }
    }

    /**
     * Fügt dem Burger eine Liste an beliebigen Zutaten hinzu.
     * @param zutat
     */
    public void zutatHinzufuegen(Zutat zutat) {
        for(Zutat aktuelleZutat : zutaten) {
            if(aktuelleZutat == null) {
                aktuelleZutat = zutat;
            }
        }
    }
}
