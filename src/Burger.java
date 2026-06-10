import java.awt.Color;

/**
 * Burger dient der Erstellung eines eigenen Burgers mit beliebigen Zutaten und
 * berechnet dessen Hoehe und den Preis.
 */
public class Burger {
    public static final int MAX_ZUTATENANZAHL = 9; // insgesamt 9 Zutaten -> 1 Broetchen an Stelle BROETCHEN_POSITION, und 8 Zusatzzutaten
    public static final int BROETCHEN_POSITION = 0;

    public static final int INDIKATOR_VEGAN = Zutat.INDIKATOR_VEGAN; // Vegan wird als Level 2 angesehen, da vegan ueber Level 1 steht und er somit ebenfalls vegetarisch repraesentiert
    public static final int INDIKATOR_VEGETARISCH = Zutat.INDIKATOR_VEGETARISCH; // Vegetarisch wird als Level 1 angesehen, da er nicht vegan ist, aber mehr Restriktionen als non-Vegan hat

    private final String name;

    private final Zutat[] zutaten;

    /**
     * Der Name wird dem Burger (inklusive Farbgebung) hinzugefuegt. Zutaten wird auf die maximale Anzahl gesetzt.
     * @param name Name des Burgers
     */
    public Burger(String name) {
        this.name = App.dyebucket.dyeText("'" + name + "'", Color.CYAN);
        zutaten = new Zutat[MAX_ZUTATENANZAHL];
    }


    /**
     * Ausgabe der Burgerdetails und dessen Rezepts.
     * @return Burgerdetails und Rezept
     */
    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        int umNStellenEinruecken = 3;

        String einruecken = " ".repeat(umNStellenEinruecken);

        // Kopfzeile
        builder.append("Rezept fuer ").append(this.name).append(": (");
        builder.append(Math.round(this.berechneHoehe()*100f)/100f).append("mm");

        String diaetstyp = getDiaettypAsString();

        if(!diaetstyp.isBlank()) builder.append(", ").append(diaetstyp);

        String isKlassischString = Zutat.getKlassischToString(this.isKlassisch());

        if(!isKlassischString.isBlank()) { builder.append(", ").append(isKlassischString); }

            // fuegt gefundene Geschmaecker an
            Geschmack[] geschmaecker = getGeschmack();
            for(Geschmack currentGeschmack : geschmaecker){
                // Fuege dem String den jeweiligen Geschmack hinzu, es seidenn er ist null oder normal 
                if(currentGeschmack == null || currentGeschmack == Geschmack.NORMAL) continue; 

                builder.append(", ").append(currentGeschmack);
            }

        // Fuegt den Preis hinzu
        builder.append(") - ").append(Math.round(berechnePreis()*100f)/100f).append("EUR\n\n");

        // Zutatenliste
        builder.append(einruecken).append("Zutaten: ");
        for(int i = 0; i < zutaten.length; i++){
            Zutat aktuelleZutat = zutaten[i];

            if(aktuelleZutat == null) continue;
            if(!(i == 0)) builder.append(", ");

            builder.append(aktuelleZutat.getName());
        }

        // Setzt die naechste Zeile um 2 nach unten
        builder.append("\n".repeat(2));

        // Zubereitung
        builder.append(einruecken).append("Und so gehts: \n");
        builder.append(kuechenAnweisung());

        return builder.toString();
    }

    // Getter
    public String getName() {
        return name;
    }

    /**
     * Zaehlt, wie viele Zutaten sich momentan im Burger befinden 
     * @return Anzahl der Zutaten im Burger (das Broetchen zaehlt ebenfalls dzau)
     */
    public int getAnzahlZutaten() {
        int anzahlZutaten = 0;

        for(Zutat aktuelleZutat : zutaten){
            if(aktuelleZutat != null){
                anzahlZutaten++;
            }
        }

        return anzahlZutaten;
    }

    /**
     * Gibt den Geschmack des Burgers zurueck.
     * Der Burger kann mehrere Geschmacksrichtungen haben, das Enum 'Geschmack' ist ohne Probleme auf neuere Geschmacksrichtungen erweiterbar.
     * @return Geschmacksrichtung
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
            // Geschmack von Zutat wird nur angeschaut, falls auch die Zutat existiert und einen Geschmack hat
            if(currentZutat != null && currentZutat instanceof HatGeschmack){
                Geschmack currentGeschmack = ((HatGeschmack)currentZutat).getGeschmack();

                // Suche im Geschmacks-Verzeichnis, ob der momentan gefundene Geschmack bereits vorkommt, wenn nicht fuege ihn an einer leeren Stelle an
                for(int i = 0; i < geschmaecker.length; i++){
                    Geschmack zuTestenderGeschmack = geschmaecker[i];

                    // Falls der Geschmack bereits verzeichnet ist, breche ab
                    if(currentGeschmack.equals(zuTestenderGeschmack)) break; 

                    //Falls Geschmack nicht bereits vorkommt und ein "leerer" Geschmack gefunden wurde, ersetze ihn und brich ab
                    if(zuTestenderGeschmack.equals(Geschmack.NORMAL)) { 
                        geschmaecker[i] = currentGeschmack; 
                        break;
                    }
                }

            }
        }

        return geschmaecker;
    }

    /**
     * Berechnet, ob der Burger vegan oder vegetarisch ist.
     * Wird als Integer behandelt, da alles was Vegan ebenfalls vegetarisch ist.
     * @return Diettyp als Integer. (0 = none vegan, 1 = vegetarisch, 2 = vegan)
     */
    public int getDiaettyp(){
        int diaettyp = INDIKATOR_VEGAN;  // Geht von der hoechsten bedingung aus und senkt, falls ein niedrigerer Wert folgt
        
        for(Zutat aktuelleZutat : zutaten) {
            if (aktuelleZutat != null) {
                diaettyp = Math.min(diaettyp, aktuelleZutat.getDiaettyp()); // Saenkt den Indikatorwert, falls eine Zutat mit einem geringeren diaetentyp innerhalb des Burgers gefunden wurde
            }
        }
        return diaettyp;
    }

    /**
     * 
     * @return Gibt zurueck, ob jede Zutat innerhalb des Burgers klassisch ist
     */
    protected boolean isKlassisch(){
        boolean isKlassisch = true;

        for(Zutat aktuelleZutat : zutaten){
            if(aktuelleZutat == null) continue;

            isKlassisch = isKlassisch && aktuelleZutat.getKlassisch();
        }

        return isKlassisch;
    }

    /**
     * Konvertiert Diaetstyp von Integer als sein String und gibt diesen zurueck.
     * Die Methode ist in Zutat definitert, Burger ruft diese statisch auf.
     * 
     * @param diaetstyp sollte den Diaetstyp des ganzen Burgers erhalten.
     * @return Gibt ueber den Indikator den Diaetstyp als String zurueck.
     */
    protected String getDiaettypAsString(int diaetstyp){
        return Zutat.getDiaetstypAsString(diaetstyp);
    }

    /**
     * Fuehrt getDiaettypAsString() direkt mit dem errechneten Diaetstypen des gesamten Burgers aus. 
     * @return Diaettyp
     */
    protected String getDiaettypAsString(){
        return Zutat.getDiaetstypAsString(getDiaettyp());
    }

    /**
     * Liest fuer jede Zutat die Zubereitungsdauer aus und gibt die gesamte Zubereitungsdauer des Burgers zurueck.
     * @return Zeit in Sekunden
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
     * Geht ueber jede Zutat und gibt ihre jeweilige Zubereitungsanweisung zurueck
     * @return Anweisung der Zutatenzubereitung als String
     */
    public String kuechenAnweisung() {
        StringBuilder builder = new StringBuilder();
        int umNStellenEinruecken = 3;

        String einruecken = " ".repeat(umNStellenEinruecken);
        char listenZaehler = 'a';

        for(Zutat aktuelleZutat : zutaten){
            if(aktuelleZutat == null) continue;

            builder.append(einruecken);
            builder.append(listenZaehler++).append(" - ").append(aktuelleZutat.getZubereitung()).append("\n");
        }



        return builder.toString();
    }

    /**
     *  Berechnet die Hoehe des Burgers anhand aller verwendeten Zutaten
     * @return Hoehe in cm
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
     * @return Preis in Euro
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
     * @param zutat neue Zutat
     */
    public void zutatHinzufuegen(Zutat zutat) {
        // Ueberschreibt, falls es sich um ein neues Broetchen handelt an der Position des Broetchens das alte Broetchen und geht zurueck.
        if(zutat instanceof Broetchen){
            zutaten[BROETCHEN_POSITION] = zutat;

            System.out.println(zutat + " hinzugefuegt.");
            return;
        }

        if(zutaten[BROETCHEN_POSITION] == null) {
            System.err.println("FEHLER! Bitte fuege zuerst ein Broetchen hinzu.");
            return;
        }

        // Sucht nach einer leeren Stelle und fuegt dort die Zutat ein
        for (int i = BROETCHEN_POSITION + 1; i < zutaten.length; i++) {
            if (zutaten[i] == null) {
                zutaten[i] = zutat;

                System.out.println(zutat.toString() + " hinzugefuegt.");
                return;
            }
        }

        // Gibt eine Fehlermeldung aus, falls keine leere Stelle im Array gefunden wurde und die Zutatenliste somit voll ist 
        System.err.println("Es ist kein Platz mehr fuer " + App.highlightBefehl(zutat.getName()) + ".\nMaximale Zutatenanzahl wurde ueberschritten.");
    }
}
