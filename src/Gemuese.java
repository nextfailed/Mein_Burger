public class Gemuese extends Zutat {
    private float scheibenDicke;
    private int scheibenAnzahl;

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
     * Vollstaendiger konstruktor fuer Gemuese. Die Scheibenart ist als default immer auf Scheiben gesetzt
     * @param nummer
     * @param name
     * @param preis
     * @param scheibenAnzahl
     * @param scheibenDicke
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
    public void switchScheibenart(Scheibenart art){
        this.gesetzteScheibenart = art;
    }

    /**
     * Switcht von Scheibe auf Ringe und von Ringe auf Scheibe
     * Jedes Gemuese ist von default aus her eine Scheibe
     */
    public void switchScheibenart(){
        if(gesetzteScheibenart == Scheibenart.SCHEIBEN){
            gesetzteScheibenart = Scheibenart.RINGE;
        }

        else{
            gesetzteScheibenart = Scheibenart.SCHEIBEN;
        }
    }

    // Getter
    public float getScheibenDicke() {
        return scheibenDicke;
    }

    public int getScheibenAnzahl() {
        return scheibenAnzahl;
    }

    /**
     * @return
     */
    @Override
    public int zubereiten() {
        int zubereitungsZeitInSekunden = 1;

        return scheibenAnzahl * zubereitungsZeitInSekunden;
    }

    /**
     * @return
     */
    @Override
    public float berechneHoehe() {
        return scheibenDicke * scheibenAnzahl;
    }

    @Override
    public String getZubereitung() {
        StringBuffer buffer = new StringBuffer();
        String einruecken = " ".repeat(6);
        char umbruch = '\n';

        buffer.append(this.name + " wird gewaschen").append(umbruch);

        for(int i = 0; i < scheibenAnzahl; i++){
            buffer.append(einruecken).append((i + 1) + ". "+ this.gesetzteScheibenart.toString() + " mit " + scheibenDicke + "mm schneiden").append(umbruch);
        }


        return buffer.toString();
    }
}
