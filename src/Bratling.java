
public class Bratling extends Zutat {
    private int bratzeit;
    private int hoehe;

    public Bratling(int nummer, String name, float preis, int hoehe) {
        super(nummer, name, preis);
        this.hoehe = hoehe;
    }

    @Override
    public int zubereiten() {
        return 0;
    }

    @Override
    public float berechneHoehe() {
        return 0;
    }
}
