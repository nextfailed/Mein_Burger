/**
 * Salat-Objektklasse, Unterklasse von Zutat.
 */
public class Salat extends Zutat {
    public Salat(int nummer, String name, float preis) {
        super(nummer, name, preis);
        super.diaettyp = INDIKATOR_VEGAN;
    }

    /**
     * Salat muss nicht zubereitet werden.
     * @return immer 0
     */
    @Override
    public int zubereiten() {
        return 0;
    }

    /**
     * Salat besitzt keine Hoehe.
     * @return immer 0
     */
    @Override
    public float berechneHoehe() {
        return 0;
    }

    /**
     * Erweitert den String des Salats, um einen Satz zu bilden.
     * @return "'Salattyp' wird gewaschen und geschleudert"
     */
    @Override
    public String getZubereitung() {
        return (this.name + " wird gewaschen und geschleudert");
    }
}
