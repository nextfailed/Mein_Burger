public class Gemuese extends Zutat {
    private int scheibenDicke;
    private int scheibenAnzahl;

    public Gemuese(int nummer, String name, float preis) {
        super(nummer, name, preis);
        super.vegan = true;
    }

    // Getter
    public int getScheibenDicke() {
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
        return 0;
    }

    /**
     * @return
     */
    @Override
    public float berechneHoehe() {
        return 0;
    }
}
