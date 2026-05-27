import java.util.ArrayList;
import java.awt.Color;

/**
 * Abstrakte Oberklasse für alle Zutaten des Burgers.
 * Vegan und vegetarisch werden als Integer abgespeichert, da jeder vegane burger ebenfalls vegetarisch ist.
 * Waeren beide als boolean gespeichert, koennte es zu Problemen fuehren.
 */
public abstract class Zutat{
    protected int nummer;
    protected String name;
    protected float preis;

    protected boolean klassisch; 

    // Indikatoren, ob ein Burger vegan, vegetarisch oder weder noch ist
    public static final int INDIKATOR_VEGAN = 2;
    public static final int INDIKATOR_VEGETARISCH = 1;
    public static final int INDIKATOR_NON_VEGAN = 0;

    public int diaettyp; // Diaettyp speicher vegan und vegetarisch als einzelner Integer ab

    protected static final DyeBucket bucket = new DyeBucket();

    private static final ArrayList<Zutat> zutatenkatalog = new ArrayList<>();

    /**
     * Vollständiger Konstruktor mit allen Parametern:
     * @param nummer Katalognummer
     * @param name Zutatenname
     * @param preis Preis in Euro
     * @param klassisch Ist eine standard Zutat
     * @param diaettyp Vegan, vegetarisch oder keins von beidem
     */
    public Zutat(int nummer, String name, float preis, boolean klassisch, int diaettyp){
        this.nummer = nummer;
        this.name = bucket.dyeText(name, Color.BLUE);
        this.preis = preis;

        this.klassisch = klassisch;

        this.diaettyp = diaettyp;

        zutatenkatalog.add(this);
    }

    /**
     * Verkürzter Konstruktor ohne Ernaehrungsart oder ob er klassisch ist.
     * Kann mithilfe von Settern im Nachhinein angepasst werden.
     * @param nummer Katalognummer
     * @param name Zutatenname
     * @param preis Preis in Euro
     */
    public Zutat(int nummer, String name, float preis){
        this(nummer,name, preis, false, 0);
    }

    /**
     * Gibt die Liste an Zutaten zurueck. Jede Zutat wird bei ihrer Instanziierung im statischen Zutatkatalog abgespeichert.
     * @return Zutatenkatalog
     */
    public static ArrayList<Zutat> getKatalog(){
        return zutatenkatalog;
    }

    /**
     * Zutat wird mit ihrer jeweiligen Nummer im Array gesucht und herausgegeben 
     * @param nummer Zutatennummer
     * @return Zutat
     */
    public static Zutat getZutat(int nummer){
        for(Zutat current : zutatenkatalog){
            if(current.getNummer() == nummer) {
                return current;
            }
        }

        System.err.println("Zutat mit der Nummer " + nummer + " konnte nicht gefunden werden.");
        return null;
    }

    @Override
    public String toString(){
        // DyeBucket Faerbt den Text 
        String diaetString = getDiaetstypAsString();


        String isKlassisch = getKlassischToString();


        return  nummer + " : " + bucket.dyeText(name, Color.CYAN) + " - " + bucket.dyeText(this.preis + "EUR ", Color.YELLOW) + diaetString + isKlassisch;
    }

    public String getDiaetstypAsString(){
        return getDiaetstypAsString(this.diaettyp);
    }

    public static String getDiaetstypAsString(int indikator){
        return switch(indikator){
            case INDIKATOR_VEGAN -> bucket.dyeText("(vegan)", Color.GREEN);
            case INDIKATOR_VEGETARISCH -> bucket.dyeText("(vegetarisch)", Color.YELLOW);
            default -> "";
        };
    }

    /**
     * Gibt die Dauer des Herstellens zurueck.
     * @return Dauer in Sekunden TODO Sekunden oder Minuten?
     */
    public abstract int zubereiten();

    /**
     * Berechnet die Hoehe dynamisch.
     * @return Hoehe in Millimeter
     */
    public abstract float berechneHoehe();

    /**
     * Setzt den Diaetstyp auf vegan, gibt sich selbst zurueck, damit man beim Casten ebenfalls Classic auf true setzen kann
     * @return diese Zutat
     */
    public Zutat setVegan(){
        this.diaettyp = INDIKATOR_VEGAN;

        return this;
    }

    /**
     * Setzt den Diaetstyp auf vegetarisch, gibt sich selbst zurueck, damit man beim Casten ebenfalls Classic auf true setzen kann
     * @return diese Zutat
     */
    public Zutat setVegetarisch(){
        this.diaettyp = INDIKATOR_VEGETARISCH;

        return this;
    }

    /**
     * Setzt den Diaetstyp auf nicht vegan oder vegetarisch, gibt sich selbst zurueck, damit man beim Casten ebenfalls Classic auf true setzen kann
     * @return diese Zutat
     */
    public Zutat setNonVegan(){
        this.diaettyp = INDIKATOR_NON_VEGAN;

        return this;
    }

    /**
     * Switcht beim Aufruf klassisch zwischen true und false, gibt sich selbst zurueck, damit man beim Casten ebenfalls den diaetstyp einstellen kann
     * @return diese Zutat
     */
    public Zutat switchClassic(){
        this.klassisch = !klassisch;

        return this;
    }

    /**
     * Setzt klassisch auf den mitgegebenen Wert, gibt sich selbst zurueck, damit man beim Casten ebenfalls den diaetstyp einstellen kann
     * @param neuerWert wahr oder falsch
     * @return diese Zutat
     */
    public Zutat setClassic(boolean neuerWert){
        this.klassisch = neuerWert;

        return this;
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

    public int getDiaettyp(){
        return this.diaettyp;
    }

    /**
     * Teilt getVegan() und getVegetarisch() auf eine Funktion auf, da der return bis auf den Indikator identisch waere.
     * @param indikator Diaettypnummer
     * @return wahr oder falsch
     */
    protected boolean getDiaetdyp(int indikator){
        return this.diaettyp >= indikator; // der typ kann >= der indikator sein, da ein veganer burger ebenfalls vegetarisch ist
    }

    /**
     *
     * @return Gibt zurueck, ob die Zutat vegan ist
     */
    public boolean getVegan(){
        return getDiaetdyp(INDIKATOR_VEGAN);
    }

    /**
     * 
     * @return Gibt zurueck, ob die Zutat vegetarisch oder vegan ist
     */
    public boolean getVegetarisch(){
        return getDiaetdyp(INDIKATOR_VEGETARISCH);
    }

    /**
     * 
     * @return Gibt zurueck, ob die Zutat klassisch ist
     */
    public boolean getKlassisch(){
        return this.klassisch;
    }

    /**
     * 
     * @return Gibt die Variable Klassisch-Variante als String zurueck.
     */
    public static String getKlassischToString(boolean klassisch){
        return klassisch ? bucket.dyeText("(klassisch)", Color.RED) : "";
    }

    /**
     * 
     * @return Gibt die Variable Klassisch-Variante als String zurueck.
     */
    public String getKlassischToString(){
        return getKlassischToString(this.klassisch);
    }

    /**
     * Printed die einzelnen Zubereitungsschritte in der Konsole aus
     */
    protected void printZubereitung(){
        print(getZubereitung());
    }

    /**
     * Printed den eingegebenen Input aus.
     * @param eingabe Eingabestring
     */
    protected void print(String eingabe){
        String umbruch = "\n";

        System.out.println(eingabe + umbruch);
    }

    /**
     * Gibt die Zubereitungsausgabe zurueck.
     * @return Zubereitungsausgabe
     */
    public abstract String getZubereitung(); 
}