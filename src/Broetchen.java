/**
 * Broetchen-Objektklasse, Unterklasse von Zutat.
 */
public class Broetchen extends Zutat{
    int backzeit;
    int hoehe;
    
    int test;

    public Broetchen(int nummer, String name, float preis, boolean klassisch, boolean vegan, boolean vegetarisch, int backzeit, int hoehe) {
        super(nummer, name, preis, klassisch, vegan, vegetarisch);
        this.backzeit = backzeit;
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
        
        return hoehe * backzeit;
    }    

    @Override
    public float berechneHoehe(){
        return (float)((this.hoehe * 0.25)*backzeit);
    }
}
