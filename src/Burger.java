/**
 * Burger dient der Erstellung eines eigenen Burgers mit beliebigen Zutaten und
 * berechnet dessen Hoehe und den Preis.
 */
public class Burger {
    public static final int MAX_ZUTATENANZAHL = 9; // insgesamt 9 Zutaten -> 1 Broetchen an Stelle BROETCHEN_POSITION, und 8 Zusatzzutaten
    public static final int BROETCHEN_POSITION = 0;

    public static final int INDIKATOR_VEGAN = 2; // Vegan wird als Level 2 angesehen, da vegan ueber Level 1 steht und er somit ebenfalls vegetarisch repraesentiert
    public static final int INDIKATOR_VEGETARISCH = 1; // Vegetarisch wird als Level 1 angesehen, da er nicht vegan ist, aber mehr Restriktionen als non-Vegan hat

    private String name;
    private int anzahlZutaten = 1;

    /* 
    private float gesamtHoehe = 0.0f;
    private float gesamtPreis = 0.0f;
    private int zubereitungsDauer;
    */

    private Zutat[] zutaten;

    public Burger(String name) {
        this.name = name;
        zutaten = new Zutat[MAX_ZUTATENANZAHL];
    }


    @Override
    public String toString(){ //TODO: Geschmack hinzufuegen!
        StringBuffer buffer = new StringBuffer();
        int umNStellenEinruecken = 3;

        String einruecken = " ".repeat(umNStellenEinruecken);
        char listenZaehler = 'a';

        // Kopfzeile
        buffer.append("Rezept fuer: " + name).append("(");
        buffer.append(this.berechneHoehe() + "mm, ");
        buffer.append(getDiaetdyp());

            // fuegt gefundene Geschmaecker an
            Geschmack[] geschmaecker = getGeschmack();
            for(Geschmack currentGeschmack : geschmaecker){
                if(currentGeschmack == null || currentGeschmack == Geschmack.NORMAL) continue; // Fuege dem String den jeweiligen Geschmack hinzu, es seidenn er ist null oder normal 

                buffer.append(", " + currentGeschmack.toString());
            }

        // Fuegt den Preis hinzu
        buffer.append(") - ").append(berechnePreis() + "EUR\n\n");

        // Zutatenliste
        buffer.append(einruecken).append("Zutaten: ");
        for(int i = 0; i < zutaten.length; i++){
            Zutat aktuelleZutat = zutaten[i];

            if(aktuelleZutat == null) continue;
            buffer.append(aktuelleZutat);

            if(i < zutaten.length - 1) buffer.append(", ");
        }

        // Setzt die naechste Zeile um 2 nach unten
        buffer.append("\n".repeat(2));

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

    public int getAnzahlZutaten() {
        return anzahlZutaten;
    }

    /**
     * Gibt den Geschmack des Burgers zurueck
     * Der Burger kann mehrere Geschmacksrichtungen haben, Geschmack ist ohne Probleme erweiterbar
     * @return
     */
    public Geschmack[] getGeschmack(){
        int laengeOhneNormal = Geschmack.values().length - 1;
        Geschmack[] geschmaecker = new Geschmack[laengeOhneNormal]; // Jeder Geschmack kann vertreten werden, NORMAL entspricht hier einem "null"-Objekt

        // Setze jedes Feld auf "NORMAL", normal agiert hier als leeres "null"-Objekt
        for(int i = 0; i < laengeOhneNormal; i++){
            geschmaecker[i] = Geschmack.NORMAL;
        }

        // Laeuft ueber jede Zutat und sucht jeden Geschmack der existiert und notiert diesen bei fund einmalig
        for(Zutat currentZutat : zutaten){
            if(currentZutat != null && currentZutat instanceof hatGeschmack){ // Geschmack von Zutat wird nur angeschaut, falls auch die Zutat existiert und einen Geschmack hat
                Geschmack currentGeschmack = ((hatGeschmack)currentZutat).getGeschmack();

                // Suche im Geschmacks-Verzeichnis, ob der momentan gefundene Geschmack bereits vorkommt, wenn nicht fuege ihn an einer leeren Stelle an
                for(int i = 0; i < geschmaecker.length; i++){
                    Geschmack zuTestenderGeschmack = geschmaecker[i];

                    // Falls der Geschmack bereits verzeichnet ist, brech ab
                    if(currentGeschmack.equals(zuTestenderGeschmack)) break; 

                    //Falls Geschmack nicht bereits vorkommt und ein "leerer" Geschmack gefunden wurde, ersetze ihn und brich ab
                    if(zuTestenderGeschmack.equals(Geschmack.NORMAL)) { 
                        geschmaecker[i] = currentGeschmack; 
                    }
                }

            }
        }

        return geschmaecker;
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
        // Ueberschreibt, falls es sich um ein neues Broetchen handelt an der Position des Broetchens das alte Broetchen und geht zurueck
        if(zutat instanceof Broetchen){
            zutaten[BROETCHEN_POSITION] = zutat;
            return;
        }

        // Sucht nach einer leeren Stelle und fuegt dort die Zutat ein
        for (int i = BROETCHEN_POSITION + 1; i < zutaten.length; i++) {
            if (zutaten[i] == null) {
                zutaten[i] = zutat;
                anzahlZutaten++;
                return;
            }
        }

        // Gibt eine Fehlermeldung aus, falls keine leere Stelle im Array gefunden wurde und die Zutatenliste somit voll ist 
        System.err.println("Es ist kein Platz mehr fuer " + zutat + " moeglich. Maximale Zutatenanzahl wurde ueberschritten.");
    }
}
