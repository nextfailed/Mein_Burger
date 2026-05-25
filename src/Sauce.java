import java.util.Optional;

public class Sauce extends Zutat {
    public enum Geschmack {
        NORMAL,
        SCHARF,
        SUESS
    }

    private int menge;
    // private String geschmack;
    private Geschmack geschmack;

    private String zubereitungschritt = (this.name + " wird geschuettelt");


    public Sauce(int nummer, String name, float preis, boolean klassisch, boolean vegan, boolean vegetarisch, int menge, Geschmack geschmack) {
        super(nummer, name, preis, klassisch, vegan, vegetarisch);
        this.menge = menge;
        this.geschmack = geschmack;
    }

    public Sauce(int nummer, String name, float preis, int menge, Geschmack geschmack) {
        this(nummer, name, preis, false, false, false, menge, geschmack);
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
     * @return
     */
    @Override
    public int zubereiten() {
        print(zubereitungschritt);

        return 0;
    }

    /**
     * @return
     */
    @Override
    public float berechneHoehe() {
        return 0f;
    }
}
