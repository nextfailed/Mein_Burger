/**
 * Broetchen-Objektklasse, Unterklasse von Zutat.
 */
public class Broetchen extends Zutat{
    private int backzeit;
    private int hoehe;

    final static float WACHSTUMS_RATE = 0.025f; // => 2.5% Wachstum

    public Broetchen(int nummer, String name, float preis, boolean klassisch, boolean vegan, boolean vegetarisch, int backzeit, int hoehe) {
        super(nummer, name, preis, klassisch, vegan, vegetarisch);
        this.backzeit = backzeit; // backzeit wird in Sekunden angegeben
        this.hoehe = hoehe;
    }

    public Broetchen(int nummer, String name, float preis, int backzeit, int hoehe){
        this(nummer,name,preis,false,false,false,backzeit,hoehe);
    }

    @Override
    public String toString(){
        return super.toString() + " | Backzeit" + this.zubereiten();
    }

    @Override
    public int zubereiten() {
        printZubereitung();
        
        return backzeit;
    }    

    @Override
    public float berechneHoehe(){
        return (float)(this.hoehe * Math.pow((1 + WACHSTUMS_RATE), backzeit));
    }

    @Override
    protected String getZubereitung() {
        return (this.name + " " + backzeit + " Minuten roesten und aufschneiden");
    }    
}
