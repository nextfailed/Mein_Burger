
public class Sauce extends ZutatExtended implements HatGeschmack{
    private final int menge;
    private final Geschmack geschmack;


    public Sauce(int nummer, String name, float preis, boolean klassisch, int diaettyp, int menge, Geschmack geschmack) {
        super(nummer, name, preis, klassisch, diaettyp);
        this.menge = menge;
        this.geschmack = geschmack;
    }

    public Sauce(int nummer, String name, float preis, int menge, Geschmack geschmack) {
        this(nummer, name, preis, false, INDIKATOR_NON_VEGAN, menge, geschmack);
    }

    @Override 
    public String toString(){
        return super.toString() + geschmack;
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
     * @return immer 0
     */
    @Override
    public int zubereiten() {
        return 0;
    }

    /**
     * Saucen haben keinen Einfluss auf die Hoehe
     * @return immer 0
     */
    @Override
    public float berechneHoehe() {
        return 0f;
    }

    @Override
    public String getZubereitung(){
        return (this.name + " wird geschuettelt");
    }
}
