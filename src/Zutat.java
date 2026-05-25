import java.util.ArrayList;
import java.awt.Color;

/**
 * Abstakte Oberklasse für alle Zutaten des Burgers.
 */
public abstract class Zutat{
    protected int nummer;
    protected String name;
    protected float preis;

    protected boolean klassisch; 
    protected boolean vegan;
    protected boolean vegetarisch;

    protected static final DyeBucket bucket = new DyeBucket();

    private static final ArrayList<Zutat> zutatenkatalog = new ArrayList<>();

    /**
     * Vollständiger Konstruktor mit allen Parametern:
     *
     * @param nummer
     * @param name
     * @param preis
     * @param klassisch
     * @param vegan
     * @param vegetarisch
     */
    public Zutat(int nummer, String name, float preis, boolean klassisch, boolean vegan, boolean vegetarisch){
        this.nummer = nummer;
        this.name = name;
        this.preis = preis;

        this.klassisch = klassisch;
        this.vegan = vegan; 
        this.vegetarisch = vegetarisch;

        zutatenkatalog.add(this);
    }

    /**
     * Verkürzter Konstruktor ohne Ernaehrungsart
     *
     * @param nummer
     * @param name
     * @param preis
     */
    public Zutat(int nummer, String name, float preis){
        this(nummer,name, preis, false, false, false);
    }

    public static ArrayList<Zutat> getKatalog(){
        return zutatenkatalog;
    }

    public static void setZutat(Zutat neueZutat){
        zutatenkatalog.add(neueZutat);
    }

    public static Zutat getZutat(int nummer){
        Zutat gesuchteZutat = null;

        for(Zutat current : zutatenkatalog){
            if(current.getNummer() == nummer) {
                gesuchteZutat = current;
                break;
            }
        }

        return gesuchteZutat;
    }

    @Override
    public String toString(){
        String isVegan = vegan ? " (vegan) ":"";
        String isVegetarisch = vegetarisch?" (vegetarisch) ":"";
        String isKlassisch = klassisch?" (klassisch) ":"";

        // Faerbt die Varianten farbig ein
        isVegan = bucket.dyeText(isVegan, Color.YELLOW);
        isVegetarisch = bucket.dyeText(isVegetarisch, Color.GREEN);
        isKlassisch = bucket.dyeText(isKlassisch, Color.RED);

        return name + " : " + nummer + ")" + isVegan + isVegetarisch + isKlassisch;
    }

    /**
     * Gibt die Zubereitungsweise der Zutat in der Konsole aus und die Dauer der Prozedur zurueck
     * @return
     */
    public abstract int zubereiten();

    /**
     * Berechnet die Hoehe dynamisch
     * @return
     */
    public abstract float berechneHoehe();

    // Setter
    public void setVegan(boolean newValue){
        this.vegan = newValue;
    }

    public void setVegetarisch(boolean newValue){
        this.vegetarisch = newValue;
    }

    public void setClassic(boolean newValue){
        this.klassisch = newValue;
    }

    // Getter
    public float getPreis(){
        return this.preis;
    }

    public int getNummer(){
        return this.nummer;
    }

    public String getName(){
        return this.name;
    }

    public boolean getVegan(boolean newValue){
        return this.vegan;
    }

    public boolean getVegetarisch(boolean newValue){
        return this.vegetarisch;
    }

    public boolean getKlassisch(boolean newValue){
        return this.klassisch;
    }

    /**
     * Printed die einzelnen Zubereitungsschritte in der Konsole aus
     */
    protected void printZubereitung(){
        print(getZubereitung());
    }

    /**
     * Printed den eingegebenen Input aus
     * @param eingabe
     */
    protected void print(String eingabe){
        String umbruch = "\n";

        System.out.println(eingabe + umbruch);
    }

    /**
     * Gibt die Zubereitungsausgabe zurueck
     * @return
     */
    protected abstract String getZubereitung(); 
}