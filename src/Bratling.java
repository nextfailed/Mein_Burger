public class Bratling extends Zutat {
    private int bratzeit;
    private int hoehe;

    private String zubereitungschritt = (this.name + " wird geschuettelt");

    public Bratling(int nummer, String name, float preis, boolean klassisch, boolean vegan, boolean vegetarisch, int bratzeit, int hoehe) {
        super(nummer, name, preis, klassisch, vegan, vegetarisch);
        this.bratzeit = bratzeit;
        this.hoehe = hoehe;
    }

    public Bratling(int nummer, String name, float preis, int bratzeit, int hoehe) {
        this(nummer, name, preis, false, false, false, bratzeit, hoehe);
    }

    // Getter
    public int getBratzeit() {
        return bratzeit;
    }

    public int getHoehe() {
        return hoehe;
    }

    @Override
    public int zubereiten() {
        print(zubereitungschritt);

        return 0;
    }

    @Override
    public float berechneHoehe() {
        return 0;
    }
}
