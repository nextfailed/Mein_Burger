public class Gemuese extends Zutat {
    private float scheibenDicke;
    private int scheibenAnzahl;


    public Gemuese(int nummer, String name, float preis, int scheibenAnzahl, float scheibenDicke) {
        super(nummer, name, preis);
        super.diaettyp = INDIKATOR_VEGAN;

        this.scheibenAnzahl = scheibenAnzahl;
        this.scheibenDicke = scheibenDicke;
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

        buffer.append(this.name + " wird gewaschen");
        for(int i = 0; i < scheibenAnzahl; i++){
            buffer.append((i+1) + getZubereitung());
        }

        buffer.append(") Scheibe mit" + scheibenDicke + "mm schneiden");

        return buffer.toString();
    }
}
