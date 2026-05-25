import java.util.ArrayList;

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

    public void setZutat(Zutat neueZutat){
        zutatenkatalog.add(neueZutat);
    }

    public Zutat getZutat(int nummer){
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
        String isVegan = vegan?"(vegan) ":"";
        String isVegetarisch = vegetarisch?"(vegetarisch) ":"";
        String isKlassisch = klassisch?"(klassisch) ":"";

        return name + " : " + nummer + ")" + isVegan + isVegetarisch + isKlassisch;
    }

    public abstract int zubereiten();

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
     * @param zubereitung
     */
    protected void print(String zubereitungsschritt){
        String abstand = "\n";

        System.out.println(zubereitungsschritt + abstand);
    }
}