/**
 * Salat-Objektklasse, Unterklasse von Zutat.
 */
public class Salat extends Zutat {
    public Salat(int nummer, String name, float preis) {
        super(nummer, name, preis);
        super.vegan = true;
    }

    private String zubereitungschritt = (this.name + " wird gewaschen und geschleudert");

    /**
     *
     * @return
     */
    @Override
    public int zubereiten() {
        print(zubereitungschritt);

        return 0;
    }

    /**
     * Salat besitzt keine Hoehe und wird immer "0" zurueck geben.
     * @return
     */
    @Override
    public float berechneHoehe() {
        return 0;
    }
}
