import java.util.Optional;

public class Sauce extends Zutat {
    private int menge;
    private Geschmack geschmack;


    public Sauce(int nummer, String name, float preis, boolean klassisch, int diaettyp, int menge, Geschmack geschmack) {
        super(nummer, name, preis, klassisch, diaettyp);
        this.menge = menge;
        this.geschmack = geschmack;
    }

    public Sauce(int nummer, String name, float preis, int menge, Geschmack geschmack) {
        this(nummer, name, preis, false, INDIKATOR_NON_VEGAN, menge, geschmack);
    }

    Optional<Geschmack> parse(String string) {
        try {
            return Optional.of(Geschmack.valueOf(string.toUpperCase()));
        }
        catch (IllegalArgumentException exception) {
            return Optional.empty();
        }
    }

    // Getter
    public int getMenge() {
        return menge;
    }

    public Geschmack getGeschmack() {
        return geschmack;
    }

    /**
     * Saucen haben keine Zubereitungsdauer
     * @return
     */
    @Override
    public int zubereiten() {
        return 0;
    }

    /**
     * Saucen haben keinen Einfluss auf die Hoehe
     * @return
     */
    @Override
    public float berechneHoehe() {
        return 0f;
    }

    @Override
    public String getZubereitung(){
        return (this.name + " wird geschuettelt");
    }; 
}
