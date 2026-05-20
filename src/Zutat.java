/**
 *
 */
public abstract class Zutat{
    protected int nummer;
    protected String name;
    protected float preis;

    protected boolean klassisch; 
    protected boolean vegan;
    protected boolean vegetarisch;

    public Zutat(int nummer, String name, float preis, boolean klassisch, boolean vegan, boolean vegetarisch){
        this.nummer = nummer;
        this.name = name;
        this.preis = preis;

        this.klassisch = klassisch;
        this.vegan = vegan; 
        this.vegetarisch = vegetarisch;
    }

    public Zutat(int nummer, String name, float preis){
        this(nummer,name, preis, false, false, false);
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
    public boolean getVegan(boolean newValue){
        return this.vegan;
    }

    public boolean getVegetarisch(boolean newValue){
        return this.vegetarisch;
    }

    public boolean getKlassisch(boolean newValue){
        return this.klassisch;
    }
}