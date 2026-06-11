public class Bratling extends ZutatExtended {
    private final int bratzeit;
    private final int hoehe;

    private final static float SCHRUMPFRATE = 0.035f; // => 3.5% Schrumpfrate


    /**
     * Konstruktor der Bratling-Klasse
     * @param nummer Katalognummer
     * @param name Name des Bratlings
     * @param preis Preis in Euro
     * @param klassisch Ist eine standard Zutat
     * @param diaettyp Vegan, vegetarisch oder keins von beidem
     * @param bratzeit Bratzeit in Minuten
     * @param hoehe Hoehe in Millimeter
     */
    public Bratling(int nummer, String name, float preis, boolean klassisch, int diaettyp, int bratzeit, int hoehe) {
        super(nummer, name, preis, klassisch, diaettyp);
        this.bratzeit = bratzeit; // Bratzeit ist in Sekunden angegeben, 1 Minute → Backzeit = 60;
        this.hoehe = hoehe;
    }

    public Bratling(int nummer, String name, float preis, int bratzeit, int hoehe) {
        this(nummer, name, preis, false, INDIKATOR_NON_VEGAN, bratzeit, hoehe);
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
        printZubereitung();

        return bratzeit;
    }

    @Override
    public float berechneHoehe() {
        return (float)(this.hoehe * Math.pow((1 - SCHRUMPFRATE), bratzeit/60f));
    }

    @Override
    public String getZubereitung() {
        int bratzeitJeSeite = bratzeit / 2;
        return (this.name + " auf jeder Seite " + bratzeitJeSeite/60 + " Minuten und " + bratzeitJeSeite % 60 + " Sekunden grillen");
    }
}
