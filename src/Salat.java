public class Salat extends Zutat {
    public Salat(int nummer, String name, float preis) {
        super(nummer, name, preis);
        super.vegan = true;
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
