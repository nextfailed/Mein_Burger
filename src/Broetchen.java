/**
 * Broetchen-Objektklasse, Unterklasse von Zutat.
 */
public class Broetchen extends Zutat{
    private int backzeit;
    private int hoehe;

    final static float WACHSTUMS_RATE = 0.025f; // => 2.5% Wachstum

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
        return super.toString() + " | Backzeit: " + (float)(this.zubereiten()/60f) + " Minuten";
    }

    @Override
    public int zubereiten() {        
        return backzeit;
    }    

    @Override
    public float berechneHoehe(){
        return (float)(this.hoehe * Math.pow((1 + WACHSTUMS_RATE), backzeit));
    }

    @Override
    public String getZubereitung() {
        return (this.name + " " + backzeit/60 + " Minuten roesten und aufschneiden");
    }    
}
