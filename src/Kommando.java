import java.awt.Color;

public enum Kommando {
    /// Liste der Kommandos
    HELP(
        new String[]{
        "Hilfe", "Help", "h", "?"
        }, 

        "Stellt dir alle Befehle zur Verfuegung.",
        Kommandotyp.AUSGABE,
        true
    ),

    QUIT(
        new String[]{
        "Beende", "Quit", "q", "!"
        }, 

        "Beendet das Programm.",
        Kommandotyp.ABBRUCH,
        true
    ),

    MENU(
        new String[]{
        "Menu", "M"
        },

        "Gibt alle existierenden Zutaten mit ihrer ID und ihren Eigenschaften aus.",

        Kommandotyp.AUSGABE
    ),
    
    NEW(
        new String[]{
        "Neuer Burger", "New"
        },

        "Erstelle einen neuen Burger. Dem Neuen Burger muss im Kommando als Argument ein Name hinzugegeben werden.\n  Mithilfe von " + App.highlightBefehl("Zutat [Zutatsnummer]") + 
        " koennen diesem Zutaten hinzugefuegt werden. Burger muessen mit "+ App.highlightBefehl("ok") +" abgeschlossen werden.\n  Achtung: Du kannst maximal " 
        + App.dyebucket.dyeText(App.MAX_BURGERANZAHL, Color.YELLOW) + " Burger erstellen",
        
        Kommandotyp.HINZUFUEGEN
    ),

    ZUTAT(
        new String[]{
        "Zutat", "Ingredient", "Z"
        },

        "Fuege deinem Burger nach " + App.highlightBefehl("Mein Burger [Burgername]") + " eine Zutat mithilfe ihrer ID hinzu. ID wird als Argument ans Ende dazugegeben.",
        Kommandotyp.HINZUFUEGEN
    ),

    CANCEL(
        new String[]{
        "Abbruch", "Cancel" 
        },

        "Bricht die Zusammenstellung deines aktuellen Burgers ab und erstellt einen neuen.",

        Kommandotyp.ABBRUCH
    ),

    OK(
        new String[]{
        "okay", "ok", "done"
        },

        "Beende das Zusammenstellen deines Burgers. Dein Burger kann bis zu " + App.dyebucket.dyeText(Burger.MAX_ZUTATENANZAHL, Color.YELLOW) + 
        " Zutaten haben.\n  Achtung: Es muss mindestens ein Broetchen hinzugefuegt werden! Du musst ebenfalls einen neuen Burger erstellen, bevor du einen abgeben kannst.",
        
        Kommandotyp.BESTAETIGEN
    ),

    MY(
        new String[]{
        "Burgerliste", "Meine_Burger", "My"
        }, 

        "Listet alle von dir erstellten Burger aus.",

        Kommandotyp.AUSGABE
    ),
    
    ORDER(
        new String[]{
        "Bestellen", "Order"
        }, 

        "Gibt die erstellten Burger, ihr Rezept, den Gesamtpreis und Gesamtzubereitungszeit an und beendet die Bestellung.",

        Kommandotyp.BESTAETIGEN
    );


    /// Initialisierungen    
   
    private final String[] aliases;
    private final String beschreibung;
    private final boolean isEssential;

    private final Kommandotyp kommandotyp;
        
    // Dient jediglich zur Farbspezifikation
    public enum Kommandotyp{
        ABBRUCH("Abbruch", Color.RED), 
        HINZUFUEGEN("Hinzufuegen", Color.GREEN),
        BESTAETIGEN("Bestaetigen", Color.YELLOW),
        AUSGABE("Ausgabe", Color.BLUE),
        NORMAL("Sonstiges");

        Color eigenfarbe;
        String description;

        private Kommandotyp(String description, Color eigenfarbe){
            this.description = description;
            this.eigenfarbe = eigenfarbe;
        }

        private Kommandotyp(String description){
            this(description, Color.WHITE);
        }

        @Override
        public String toString(){
            return this.description;
        }


        public Color getEigenfarbe(){
            return this.eigenfarbe;
        }
    }
        
    /**
     * 
     * @param commands Beinhaltet Aliases fuer den selben Befehlsaufruf 
     * @param description Beinhaltet die Beschreibung zu dem jeweiligen Befehl
     * @param type Gibt den Typen des Befehls sowie die dazugehoerige Farbe mit an
     * @param essential Gibt an, ob das Kommando bereits am Anfang ausgegeben wird oder erst mit dem ersten Help-Aufruf
     */
    private Kommando(String[] commands, String description, Kommandotyp type, boolean essential){
        this.aliases = commands;
        this.beschreibung = description;
        this.isEssential = essential;

        this.kommandotyp = type;
    }

    /**
     * 
     * @param commands
     * @param description
     * @param type
     */
    private Kommando(String[] commands, String description, Kommandotyp type){
        this(commands, description, type, false);
    }

    /**
     * 
     * @param commands
     * @param description
     */
    private Kommando(String[] commands, String description){
        this(commands, description, Kommandotyp.NORMAL);
    }

    public String toString(){
        return this.getMainAlias();
    }

    public String getMainAlias(){
        return this.aliases[0];
    }

    public String[] getAliases(){
        return this.aliases;
    }

    public String getDescription(){
        return App.dyebucket.dyeItalic(beschreibung);
    }

    public Kommandotyp getKommandotyp(){
        return this.kommandotyp;
    }

    /**
     * Gibt die Farbe des Kommandotypens zurueck, dient zur Einfaerbung des Kommandos
     * @return
     */
    public Color getEigenfarbe(){
        return this.kommandotyp.eigenfarbe;
    } 

    /**
     * Gibt aus, ob das Kommando bereits am Anfang ausgegeben wird oder erst nach dem ersten Help-Aufruf.
     * @return
     */
    public boolean getisEssential(){
        return this.isEssential;
    }
}
