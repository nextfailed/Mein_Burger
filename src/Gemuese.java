public class Gemuese extends Zutat {
    private float scheibenDicke;
    private int scheibenAnzahl;


    public Gemuese(int nummer, String name, float preis, int scheibenAnzahl, float scheibenDicke) {
        super(nummer, name, preis);
        super.vegan = true;

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
        print(this.name + " wird gewaschen");
        for(int i = 0; i < scheibenAnzahl; i++){
            print((i+1) + getZubereitung());
        }

        return 0;
    }

    /**
     * @return
     */
    @Override
    public float berechneHoehe() {
        return scheibenDicke * scheibenAnzahl;
    }

    @Override
    protected String getZubereitung() {
        return (") Scheibe mit" + scheibenDicke + "mm schneiden");
    }
}
