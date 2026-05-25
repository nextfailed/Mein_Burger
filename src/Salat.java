/**
 * Salat-Objektklasse, Unterklasse von Zutat.
 */
public class Salat extends Zutat {
    public Salat(int nummer, String name, float preis) {
        super(nummer, name, preis);
        super.diaettyp = INDIKATOR_VEGAN;
    }


    /**
     *
     * @return
     */
    @Override
    public int zubereiten() {
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

    @Override
    public String getZubereitung() {
        return (this.name + " wird gewaschen und geschleudert");
    }
}
