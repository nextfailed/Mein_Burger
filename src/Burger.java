/**
 * Burger dient der Erstellung eines eigenen Burgers mit beliebigen Zutaten und
 * berechnet dessen Hoehe und den Preis.
 */
public class Burger {
    public static final int MAX_ZUTATENANZAHL = 9; // insgesamt 9 Zutaten -> 1 Broetchen an Stelle 0, und 8 Zusatzzutaten
    public static final int BROETCHEN_POSITION = 0;

    public static final int INDIKATOR_VEGAN = 2;
    public static final int INDIKATOR_VEGETARISCH = 1;

    private String name;

    /* 
    private float gesamtHoehe = 0.0f;
    private float gesamtPreis = 0.0f;
    private int zubereitungsDauer;
    */

    private Zutat[] zutaten = new Zutat[MAX_ZUTATENANZAHL];

    public Burger(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        StringBuffer buffer = new StringBuffer();
        int leerzeichenFuerEinruecken = 3;

        String einruecken = " ".repeat(leerzeichenFuerEinruecken);
        char listenZaehler = 'a';

        // Kopfzeile
        buffer.append("Rezept: " + name).append("(").append(this.berechneHoehe() + "mm, ");
        buffer.append(getDiaetdyp()).append(")");
        buffer.append(" - ").append(berechnePreis() + "EUR\n\n");

        // Zutatenliste
        buffer.append(einruecken).append("Zutaten: ");
        for(int i = 0; i < zutaten.length; i++){
            Zutat aktuelleZutat = zutaten[i];

            if(aktuelleZutat == null) continue;
            buffer.append(aktuelleZutat);

            if(i < zutaten.length - 1) buffer.append(", ");
        }

        // Zubereitung
        buffer.append(einruecken).append("Und so gehts: \n");
        for(Zutat aktuelleZutat : zutaten){
            if(aktuelleZutat == null) continue;

            buffer.append(einruecken);
            buffer.append(listenZaehler++ + " - ").append(aktuelleZutat.getZubereitung()).append("\n");
        }

        return buffer.toString();
    }

    // Getter
    public String getName() {
        return name;
    }

    /* 
    public float getGesamtHoehe() {
        return gesamtHoehe;
    }

    public float getGesamtPreis() {
        return gesamtPreis;
    }
    */

    /**
     * Berechnet, ob der Burger vegan oder vegetarisch ist 
     * Wird als Integer behandelt, da alles was Vegan ebenfalls vegetarisch ist
     * @return
     */
    public int getDiaetdyp(){   
        int diaettyp = INDIKATOR_VEGAN;  // Geht von der hoechsten bedingung aus und senkt, falls ein niedrigerer Wert folgt
        
        for(Zutat aktuelleZutat : zutaten){
            diaettyp = Math.min(diaettyp, aktuelleZutat.getDiaettyp()); // Saenkt den Indikatorwert, falls eine Zutat mit einem geringeren diaetentyp innerhalb des Burgers gefunden wurde
        }

        return diaettyp;
    }


    /**
     * Gibt fuer jede Zutat die Zubereitungsweise aus und gibt die gesamte Zubereitungsdauer zurueck
     * @return
     */
    public int zubereiten(){
        int zubereitungsDauer = 0;

        for(Zutat aktuelleZutat : zutaten) {
            if(aktuelleZutat == null) {
                continue;
            }
            
            zubereitungsDauer += aktuelleZutat.zubereiten();
        }

        return zubereitungsDauer;
    }

    /**
     *  Berechnet die Hoehe des Burgers anhand aller verwendeten Zutaten
     * @return
     */
    public float berechneHoehe() {
        float gesamtHoehe = 0f;

        for(Zutat aktuelleZutat : zutaten) {
            if(aktuelleZutat == null) {
                continue;
            }
            
            gesamtHoehe += aktuelleZutat.berechneHoehe();
        }

        return gesamtHoehe;
    }
    
    /**
     * Berechnet dynamisch den Gesamtpreis des Burgers
     * @return
     */
    public float berechnePreis(){
        float gesamtPreis = 0f;
        

        for(Zutat aktuelleZutat : zutaten) {
            if(aktuelleZutat == null) {
                continue;
            }
            
            gesamtPreis += aktuelleZutat.getPreis();
        }

        return gesamtPreis;
    }

    /**
     * Fügt dem Burger eine Liste an beliebigen Zutaten hinzu
     * @param zutat
     */
    public void zutatHinzufuegen(Zutat zutat) {
        // Ueberschreibt falls es sich um ein neues Broetchen handelt an der Position des Broetchens das alte Broetchen und geht zurueck
        if(zutat instanceof Broetchen){
            zutaten[BROETCHEN_POSITION] = zutat;
            return;
        }

        // Sucht nach einer leeren Stelle und fuegt dort die Zutat ein
        for(Zutat aktuelleZutat : zutaten) {
            if(aktuelleZutat == null) {
                aktuelleZutat = zutat;
                return;
            }
        }

        // Gibt eine Fehlermeldung aus, falls keine leere Stelle im Array gefunden wurde und die Zutatenliste somit voll ist 
        System.err.println("Es ist kein Platz mehr fuer " + zutat + " moeglich. Maximale Zutatenanzahl wurde ueberschritten.");
    }
}
