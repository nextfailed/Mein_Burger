/**
 * Burger dient der Erstellung eines eigenen Burgers mit beliebigen Zutaten und
 * berechnet dessen Hoehe und den Preis.
 */
public class Burger {
    public static final int MAX_ZUTATENANZAHL = 9; // insgesamt 9 Zutaten -> 1 Broetchen an Stelle 0, und 8 Zusatzzutaten
    public static final int BROETCHEN_POSITION = 0;

    private String name;
    private float gesamtHoehe = 0.0f;
    private float gesamtPreis = 0.0f;
    private int zubereitungsDauer;

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
     * Berechnet, ob der Burger vegan ist
     * @return
     */
    public boolean isVegan(){    
        for(Zutat aktuelleZutat : zutaten){
            if(!aktuelleZutat.getVegan()) return false;
        }

        return true;
    }

    /**
     * Gibt zurueck, ob der Burger vegetarisch ist. (gibt ebenfalls true zurueck, wenn der Burger vegan ist)
     * @return
     */
    public boolean isVegetarisch(){
        for(Zutat aktuelleZutat : zutaten){
            if(!(aktuelleZutat.getVegetarisch() || aktuelleZutat.getVegan())) return false;
        }

        return true;
    }

    /**
     * Gibt fuer jede Zutat die Zubereitungsweise aus und gibt die gesamte Zubereitungsdauer zurueck
     * @return
     */
    public void zubereiten(){
        zubereitungsDauer = 0;

        for(Zutat aktuelleZutat : zutaten) {
            if(aktuelleZutat == null) {
                continue;
            }
            
            zubereitungsDauer += aktuelleZutat.zubereiten();
        }

    }


    /**
     * Berechnet die Hoehe des Burgers anhand aller verwendeten Zutaten.
     */
    public void berechneHoehe() {
        gesamtHoehe = 0f;

        for(Zutat aktuelleZutat : zutaten) {
            if(aktuelleZutat == null) {
                continue;
            }
            
            gesamtHoehe += aktuelleZutat.berechneHoehe();
        }
    }
    
    public void berechnePreis(){
        gesamtPreis = 0f;
        

        for(Zutat aktuelleZutat : zutaten) {
            if(aktuelleZutat == null) {
                continue;
            }
            
            gesamtPreis += aktuelleZutat.getPreis();
        }
    }

    /**
     * Fügt dem Burger eine Liste an beliebigen Zutaten hinzu.
     * @param zutat
     */
    public void zutatHinzufuegen(Zutat zutat) {
        if(zutat instanceof Broetchen){
            zutaten[BROETCHEN_POSITION] = zutat;
            return;
        }

        for(Zutat aktuelleZutat : zutaten) {
            if(aktuelleZutat == null) {
                aktuelleZutat = zutat;
                return;
            }
        }

        System.err.println("Es ist kein Platz mehr fuer " + zutat + " moeglich. Maximale Zutatenanzahl wurde ueberschritten.");
    }
}
