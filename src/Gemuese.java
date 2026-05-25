public class Gemuese extends Zutat {
    private float scheibenDicke;
    private int scheibenAnzahl;

    private String zubereitungschritt = (") Scheibe mit" + scheibenDicke + "mm schneiden");

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
        String einruecken = "\n  ";

        print(this.name + " wird gewaschen");
        for(int i = 0; i < scheibenAnzahl; i++){
            print(einruecken + i + zubereitungschritt);
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
}
