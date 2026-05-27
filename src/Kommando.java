import java.awt.Color;

public enum Kommando {
    /// Liste der Kommandos
    HELP(
        new String[]{
        "Hilfe", "Help", "h", "?"
        }, 

        "Stellt dir alle Befehle zur Verfuegung.",
        Kommandotyp.AUSGABE
    ),

    QUIT(
        new String[]{
        "Quit", "Beende", "q", "!"
        }, 

        "Beendet das Programm.",
        Kommandotyp.ABBRUCH
    ),

    MENU(
        new String[]{
        "Menu", "M"
        },

        "Gibt alle existierenden Zutaten mit ihrer ID und Eigenschaften aus.",

        Kommandotyp.AUSGABE
    ),
    
    NEW(
        new String[]{
        "Neuer Burger [Burgername : String ohne Leerzeichen]", "New [Burgername]"
        },

        "Erstelle einen neuen Burger. Mithilfe von " + App.highlightBefehl("Zutat [Zutatsnummer]") + 
        " koennen diesem Zutaten hinzugefuegt werden. Burger muessen mit "+ App.highlightBefehl("ok") +" abgeschlossen werden.\n  Achtung: Du kannst maximal " + App.dyebucket.dyeItalic(App.MAX_BURGERANZAHL) + " Burger erstellen",
        
        Kommandotyp.HINZUFUEGEN
    ),

    ZUTAT(
        new String[]{
        "Zutat [ID : Integer]", "Ingredient[ID]", "Add[ID]"
        },

        "Fuege deinem Burger nach " + App.highlightBefehl("Mein Burger [Burgername]") + " eine Zutat mithilfe ihrer ID hinzu.",
        Kommandotyp.HINZUFUEGEN
    ),

    CANCEL(
        new String[]{
        "Cancel", "Abbruch"
        },

        "Bricht die Zusammenstellung deines aktuellen Burgers ab und erstellt einen neuen.",

        Kommandotyp.ABBRUCH
    ),

    OK(
        new String[]{
        "ok", "done"
        },

        "Beende das Zusammenstellen deines Burgers. Dein Burger kann bis zu " + App.dyebucket.dyeItalic(Burger.MAX_ZUTATENANZAHL) + 
        " Zutaten haben.\n  Achtung: Es muss mindestens ein Broetchen hinzugefuegt werden! Du musst ebenfalls einen neuen Burger erstellen, bevor du einen abgeben kannst.",
        
        Kommandotyp.BESTAETIGEN
    ),

    MY(
        new String[]{
        "Mein Burger", "My"
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
   
    private final String[] kommandos;
    private final String beschreibung;

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
        
    private Kommando(String[] commands, String description, Kommandotyp type){
        this.kommandos = commands;
        this.beschreibung = description;

        this.kommandotyp = type;
    }

    private Kommando(String[] commands, String description){
        this(commands, description, Kommandotyp.NORMAL);
    }

    public String toString(){
        return this.kommandos[0];
    }

    public String[] getKommandos(){
        return this.kommandos;
    }

    public String getDescription(){
        return App.dyebucket.dyeItalic(beschreibung);
    }

    public Kommandotyp getKommandotyp(){
        return this.kommandotyp;
    }

    public Color getEigenfarbe(){
        return this.kommandotyp.eigenfarbe;
    } 
}
