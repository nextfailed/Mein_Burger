public class Bratling extends Zutat {
    private int bratzeit;
    private int hoehe;

    private final static float SCHRUMP_FRATE = 0.035f; // => 3.5% Schrumpfrate

    public Bratling(int nummer, String name, float preis, boolean klassisch, int diaettyp, int bratzeit, int hoehe) {
        super(nummer, name, preis, klassisch, diaettyp);
        this.bratzeit = bratzeit; // Bratzeit ist in Sekunden angegeben, 1 Minute -> Backzeit = 60;
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
        return (float)(this.hoehe * Math.pow((1 - SCHRUMP_FRATE), bratzeit/60));
    }

    @Override
    public String getZubereitung() {        
        return (this.name + " auf jeder Seite " + bratzeit/60 + " Minuten und " + bratzeit % 60 + " Sekunden grillen");
    }
}
