/**
 * Gemuese-Objektklasse, Unterklasse von Zutat.
 */
public class Gemuese extends Zutat {
    private final float scheibenDicke;
    private final int scheibenAnzahl;

    Scheibenart gesetzteScheibenart;

    /**
     * Gibt an, ob ein Gemuese in Scheiben oder Ringe geschnitten wird
     */
    public enum Scheibenart{
        SCHEIBEN("Scheibe"),
        RINGE("Ring");

        public final String tag;

        private Scheibenart(String tag){
            this.tag = tag;
        }

        @Override
        public String toString(){
            return this.tag;
        }
    }

    /**
     * Konstruktor fuer Gemuese. Die Scheibenart ist als default immer auf Scheiben gesetzt
     * @param nummer Katalognummer
     * @param name Gemuesename
     * @param preis Preis in Euro
     * @param scheibenAnzahl Anzahl der Stuecke
     * @param scheibenDicke Scheibendicke in Millimeter
     */
    public Gemuese(int nummer, String name, float preis, int scheibenAnzahl, float scheibenDicke) {
        super(nummer, name, preis);
        super.diaettyp = INDIKATOR_VEGAN;

        this.scheibenAnzahl = scheibenAnzahl;
        this.scheibenDicke = scheibenDicke;
        this.gesetzteScheibenart = Scheibenart.SCHEIBEN;
    }

    /**
     * Setzt die Art des geschnittenen Gemueses auf das Scheiben-Enum, welches mitgeliefert wird
     * @param art Scheibenart
     */
    public Gemuese switchScheibenart(Scheibenart art){
        this.gesetzteScheibenart = art;
        return this;
    }

    /**
     * Switcht von Scheibe auf Ringe und von Ringe auf Scheibe.
     * Jedes Gemuese ist von default aus her eine Scheibe.
     */
    public Gemuese switchScheibenart(){
        if(gesetzteScheibenart == Scheibenart.SCHEIBEN){
            gesetzteScheibenart = Scheibenart.RINGE;
        }

        else{
            gesetzteScheibenart = Scheibenart.SCHEIBEN;
        }
        return this;
    }

    // Getter
    public float getScheibenDicke() {
        return scheibenDicke;
    }

    public int getScheibenAnzahl() {
        return scheibenAnzahl;
    }

    /**
     * @return Zubereitungszeit in Sekunden
     */
    @Override
    public int zubereiten() {
        int zubereitungsZeitInSekunden = 1;

        return scheibenAnzahl * zubereitungsZeitInSekunden;
    }

    /**
     * Berechnet die Hoehe aller Scheiben.
     * @return Hoehe in Millimeter
     */
    @Override
    public float berechneHoehe() {
        return scheibenDicke * scheibenAnzahl;
    }

    /**
     * Erstellt das Ausgabestring fuer die Konsole.
     * @return Ausgabestring
     */
    @Override
    public String getZubereitung() {
        StringBuilder builder = new StringBuilder();
        String einruecken = " ".repeat(6);
        char umbruch = '\n';

        builder.append(this.name).append(" wird gewaschen").append(umbruch);

        for(int i = 0; i < scheibenAnzahl; i++){
            builder.append(einruecken).append((i + 1)).append(". ").append(this.gesetzteScheibenart.toString())
                    .append(" mit ").append(scheibenDicke).append("mm schneiden").append(umbruch);
        }


        return builder.toString();
    }
}
