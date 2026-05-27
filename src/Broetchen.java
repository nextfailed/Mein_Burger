/**
 * Broetchen-Objektklasse, Unterklasse von Zutat.
 */
public class Broetchen extends Zutat{
    private final int backzeit;
    private final int hoehe;

    final static float WACHSTUMS_RATE = 0.025f; // => 2.5% Wachstum

    /**
     * Konstruktor der Broetchen-Klasse.
     * @param nummer Katalognummer
     * @param name Name des Broetchens
     * @param preis Preis in Euro
     * @param klassisch Ist eine standard Zutat
     * @param diaettyp Vegan, vegetarisch oder keins von beidem
     * @param backzeit Backzeit in Minuten
     * @param hoehe Hoehe in Millimeter
     */
    public Broetchen(int nummer, String name, float preis, boolean klassisch, int diaettyp, int backzeit, int hoehe) {
        super(nummer, name, preis, klassisch, diaettyp);
        this.backzeit = backzeit; // backzeit wird in Sekunden angegeben
        this.hoehe = hoehe;
    }

    public Broetchen(int nummer, String name, float preis, int backzeit, int hoehe){
        this(nummer,name,preis,false, INDIKATOR_NON_VEGAN,backzeit,hoehe);
    }

    @Override
    public String toString(){
        return super.toString() + "| Backzeit: " + this.zubereiten()/60f + " Minuten";
    }

    @Override
    public int zubereiten() {        
        return backzeit;
    }    

    @Override
    public float berechneHoehe(){
        return (float)(this.hoehe * Math.pow((1 + WACHSTUMS_RATE), backzeit/60f));
    }

    @Override
    public String getZubereitung() {
        return (this.name + " " + (float)backzeit/60f + " Minuten roesten und aufschneiden");
    }    
}
