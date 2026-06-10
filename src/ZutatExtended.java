/**
 * Gibt jeder Zutat, die nicht fest Vegan, Vegetarisch oder Nicht-Vegan ist die 
 * Setter-Methoden fuer Vegan, Vegetarisch und Nicht-Vegan.
 * 
 * Somit koennen Zutaten wie zuvor auch durch den Aufruf von {@code new ZutatExtended(...).setVegan()} erstellt werden,
 * festgelegte Zutaten wie Salat koennen jedoch hier nicht ueberschrieben werden, da es nur eine normale Zutat ohne die erweiterten Methoden ist.
 */
public abstract class ZutatExtended extends Zutat{
    
    /**
     * Vollständiger Konstruktor mit allen Parametern:
     * @param nummer Katalognummer
     * @param name Zutatenname
     * @param preis Preis in Euro
     * @param klassisch Ist eine standard Zutat
     * @param diaettyp Vegan, vegetarisch oder keins von beidem
     */
    public ZutatExtended(int nummer, String name, float preis, boolean klassisch, int diaettyp){
        super(nummer, name, preis, klassisch, diaettyp);
    }

    /**
     * Verkürzter Konstruktor ohne Ernaehrungsart oder ob er klassisch ist.
     * Kann mithilfe von Settern im Nachhinein angepasst werden.
     * @param nummer Katalognummer
     * @param name Zutatenname
     * @param preis Preis in Euro
     */
    public ZutatExtended(int nummer, String name, float preis){
        this(nummer,name, preis, false, 0);
    }

    /**
     * Setzt den Diaetstyp auf vegan, gibt sich selbst zurueck, damit man beim Casten ebenfalls Classic auf true setzen kann
     * @return diese Zutat
     *
     */
    public Zutat setVegan(){
        diaettyp = INDIKATOR_VEGAN;

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
}
