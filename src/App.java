import java.awt.Color;
import java.util.Scanner;

/**
 * Hauptklasse für die Kontrolle des Programms.
 */
public class App {
    // Tools
    public static final Scanner scanner = new Scanner(System.in);
    public static final DyeBucket dyebucket = new DyeBucket();
    private static final Lager lager = new Lager();
    private static Warenkorb warenkorb = new Warenkorb();

    // Wichtig fuer Ausgaben
    protected static final String einleitung = "";
    protected static final String alternativerBefehl = " | ";
    protected static final String erlaeuterung = "=> ";
    public static final char UMBRUCH = '\n';

    // Farben fuer Befehle (Normal und Abbruch)
    protected static final Color keyWordColor = Color.BLUE;
    protected static final Color quitWordColor = Color.RED;


    public static void main(String[] args) {
        StringBuilder ausgabe = new StringBuilder();
        //generiereKatalog();

        // Kopfzeile
        String slogen = "You'll never burger alone - Create your Burger";

        String rand = "#".repeat(slogen.length()) + (UMBRUCH);
        
        ausgabe.append(rand);
        ausgabe.append(dyebucket.dyeText(slogen, Color.YELLOW)).append(UMBRUCH).append(UMBRUCH);

        ausgabe.append("Folgende Befehle stehen dir zur Verfuegung: ").append(UMBRUCH);
        // Help-Ausgabe
        for(Kommando aktuellesKommando : Kommando.values()){
            if(aktuellesKommando == null || !aktuellesKommando.getisEssential()) continue;

            ausgabe.append(UMBRUCH);
            
            String[] kommandoVarianten = aktuellesKommando.getAliases();
            ausgabe.append(alternativerBefehl);
            for(String variante : kommandoVarianten){
                // Faerbt die Variante je nachdem ob sie etwas abbricht oder nicht
                variante = highlightBefehl(variante, aktuellesKommando.getEigenfarbe());

                variante = variante + alternativerBefehl;
                ausgabe.append(variante);

            }

            ausgabe.append(UMBRUCH).append(erlaeuterung).append(aktuellesKommando.getDescription()).append(UMBRUCH);
        }

        ausgabe.append(rand);

        // Befehle:
        System.out.println(ausgabe);

        befehlseingabe();
    }

    /**
     * Fragt den Benutzer nach Befehlen und vergleicht den Input mit den jeweiligen Aliases der Kommandos.
     * Die Methode und das Programm hoeren auf, sobald einer der beenden- oder bestellen-Aliases eingegeben wird.
     * Die Kommandos sind in der Kommando-Enum Klasse dynamisch abspeicherbar und koennen der if-else Abfrage manuell hinzugefuegt werden.
     * Aliases und ihre Beschreibung werden automatisch in der befehlseingabe-methode, sowie beim Hilfs-Aufruf dynamisch hinzugefuert.
     */
    protected static void befehlseingabe() {
        String eingabe;

        do{
            System.out.println(dyebucket.dyeText("Bitte deine Eingabe:", Color.YELLOW));
            System.out.print("> ");
            eingabe = scanner.nextLine();

            eingabe = eingabe.trim();

            if(!eingabe.contains(" ")) {
                eingabe += " ";
            }

            String befehl = befehl(eingabe).toLowerCase();
            String argument = befehlsArgument(eingabe);

            // Sucht hier die Aliases nach ab und ueberprueft, ob der Befehl mit einem Alias uebereinstimmt
            if(kommandoGefunden(befehl, Kommando.HELP)){
                help();
            }

            else if(kommandoGefunden(befehl, Kommando.MENU)){
                    menu();
            }

            else if(kommandoGefunden(befehl, Kommando.NEW)){
                if(!warenkorb.isInBearbeitung()) {
                    if(!warenkorb.add(new Burger(argument))) {
                        continue;
                    }

                    System.out.println("\nDu erstellst Burger " + warenkorb.size() + " : " + Warenkorb.MAX_BURGERANZAHL + " mit dem Namen "+ warenkorb.getAktiverBurger().getName() + ".");
                    System.out.println("Mit " + highlightBefehl("ok") + " kannst du deine Zusammenstellung abschliessen.");
                }

                else {
                    System.err.println("\nFEHLER! Der Burger " + warenkorb.getAktiverBurger().getName() + " befindet sich noch in der Zusammenstellung."
                    + "\nBitte beende erste die Zusammenstellung mit "+ highlightBefehl("ok") +".");
                }

            }

            else if(kommandoGefunden(befehl, Kommando.ZUTAT)){
                if(stringIsNumber(argument)) {
                    zutatHinzufuegen(Integer.parseInt(argument));
                }

                else {
                    System.err.println("\nFEHLER! '" + argument + "' ist keine bekannte Nummer!");
                }

            }
        
            else if(kommandoGefunden(befehl, Kommando.MY)){
                meineBurger();
            }

            else if(kommandoGefunden(befehl, Kommando.OK)){
                if(!warenkorb.isInBearbeitung()){
                    System.err.println("\nFEHLER: Du erstellst momentan noch keinen burger.\nBitte erstelle einen neuen Burger mithilfe von: " + highlightBefehl("Mein burger [Burgername]"));
                    continue;
                }
                if(warenkorb.getAktiverBurger() == null) {
                    System.err.println("\nFEHLER: Dein Burger existiert nicht.");
                    continue;
                }

                if(warenkorb.getAktiverBurger().getAnzahlZutaten() == 0){
                    System.err.println("\nFEHLER: Du hast noch keine Zutaten fuer " + warenkorb.getAktiverBurger().getName() + " hinzugefuegt\nBitte fuege deinem neuen Burger vorerst mindestens "+ highlightQuit("ein Broetchen") +" hinzu.");

                    continue;
                }

                System.out.println("\nDein Burger " + warenkorb.getAktiverBurger().getName() + " wurde deiner Bestellung hinzugefuegt.");
                warenkorb.beendeAktiverBurger();
            }

            else if(kommandoGefunden(befehl, Kommando.NEW)){
                meineBurger();
            }

            else if(kommandoGefunden(befehl, Kommando.ORDER)){
                bestellen();
            }

            else if(kommandoGefunden(befehl, Kommando.QUIT)){ // Beendet das Programm
                System.out.println(dyebucket.dyeText("Auf Wiedersehen!" , Color.blue));
                return;
            }
                
            else{
                unbekannteEingabe();
            }
            

        } while(true); //(!eingabe.equals("quit")); => Redundant, da er auf die gegebenen Schluesselwoerter das Programm beendet
    }

    /**
     * Ueberprueft, ob der Eingabebefehl einem Alias entspricht
     * @return Gibt zurueck, ob einer der Aliases fuer einen bestimmten Befehl mit dem Befehl uebereinstimmt
     */
    public static boolean kommandoGefunden(String zuSuchenderAlias, Kommando kommando){
        for(String ueberpruefterAlias : kommando.getAliases()){
            if(zuSuchenderAlias.equalsIgnoreCase(ueberpruefterAlias)) return true;
        }

        return false;
    }

    /**
     * Gibt das gesamte Menue ueber die Konsole aus.
     */
    protected static void menu() {
        String seitenrand = "#".repeat(5);

        System.out.println(seitenrand+ "\nFolgende Zutaten stehen zur Auswahl: " + seitenrand + UMBRUCH);
        System.out.println(lager);
    }

    /**
     * Faerbt einen Text mit der dazugegebenen Farbe ein und 
     * @param befehl Eingabe des Befehls
     * @param color Farbe des erlaubten Spektrums fuer den Farbbucket
     * @return Gefaerbten Befehl in ' ' eingebetten
     */
    protected static String highlightBefehl(String befehl, Color color){
        befehl = "'" + befehl + "'";

        return dyebucket.dyeItalic(dyebucket.dyeText(befehl, color));
    }

    /**
     * Faerbt ein Keyword eines Commands mit der festgelegten keyword-farbe ein.
     * @param befehl wird als einfacher String erwartet
     * @return Keyword, welches gefaerbt, kursiv notiert und in Anfuehrungszeichen eingebettet ist
     */
    protected static String highlightBefehl(String befehl){
        return highlightBefehl(befehl, keyWordColor);
    }

    /**
     * Faerbt ein Quit-Keyword eines Commands mit der festgelegten quitkeyword-farbe ein.
     * @param befehl wird als einfacher String erwartet
     * @return Keyword, welches gefaerbt, kursiv notiert und in Anfuehrungszeichen eingebettet ist
     */
    protected static String highlightQuit(String befehl) {
        return highlightBefehl(befehl, quitWordColor);
    }

    /**
     * Fuegt dem aktuellen Burger die Zutat mit der uebergebenen Nummer hinzu.
     * @param nummer Zutatennummer
     */
    protected static void zutatHinzufuegen(int nummer) {
        System.out.println();
        Zutat zutat = lager.getZutat(nummer);
        warenkorb.addZutat(zutat);
    }

    /**
     * Gibt alle Burger dieser Bestellung aus. Sollte noch kein Burger aufgenommen worden sein,
     * wird ein entsprechender Fehler ausgegeben.
     */
    protected static void meineBurger() {
        System.out.println(warenkorb);
    }

    /**
     * Loest die Bestellung im Warenkorb aus und gibt deren Rueckgabe
     * ueber die Konsole aus.
     */
    protected static void bestellen() {
        // Abschliessung und Zuruecksetzung
        System.out.println(warenkorb.abschliessen());
        neuerWarenkorb();

        String abgeschlossen = "Ihre Bestellung wurde abgeschlossen. Sie koennen nun eine neue Bestellung aufgegeben.";
        String rand = "#".repeat(abgeschlossen.length());

        System.out.println();
        System.out.println(rand);
        System.out.println(App.dyebucket.dyeText(abgeschlossen, Color.GREEN));
        System.out.println(rand);
    }

    /**
     * Erstelle einen neuen (leeren) Warenkorb.
     */
    private static void neuerWarenkorb(){
        warenkorb = new Warenkorb();
    }

    /**
     * Gibt alle Kommandos, Aliases und ihre Beschreibungen dynamisch in der Konsole aus.
     */
    protected static void help() {
        final StringBuilder ausgabe = new StringBuilder();

        
        String anfang = dyebucket.dyeText("Hier sind alle noetigen Kommandos mit ihren Zusatzargumenten und ihrer Beschreibung aufgelistet.", Color.YELLOW);
        String rand = "#".repeat(anfang.length());

        // Anfang
        ausgabe.append(rand).append(UMBRUCH);

        ausgabe.append(anfang);

        ausgabe.append(UMBRUCH).append(UMBRUCH).append("Syntax: ").append(highlightBefehl("Kommando [Argument]"));
        ausgabe.append(UMBRUCH).append("Weder Argumente noch Kommandos sind Case-Sensitiv.");

        
        ausgabe.append(UMBRUCH).append(rand).append(UMBRUCH);

        // Erlaeuterung der Farbgebung
        ausgabe.append(UMBRUCH).append(dyebucket.dyeItalic("Farb-Bedeutungen: ")).append(alternativerBefehl);
        for(Kommando.Kommandotyp typ : Kommando.Kommandotyp.values()){
            ausgabe.append(dyebucket.dyeText(typ.toString(), typ.getEigenfarbe())).append(alternativerBefehl);
        }

        ausgabe.append(UMBRUCH).append(UMBRUCH).append(rand).append(UMBRUCH);

        // Befehle:
        for(Kommando aktuellesKommando : Kommando.values()){
            if(aktuellesKommando == null) continue;

            ausgabe.append(UMBRUCH);
            ausgabe.append(einleitung);
        
            String[] kommandoVarianten = aktuellesKommando.getAliases();

            ausgabe.append(alternativerBefehl);

            for(String variante : kommandoVarianten){
                // Faerbt die Variante je nachdem ob sie etwas abbricht oder nicht
                variante = highlightBefehl(variante, aktuellesKommando.getEigenfarbe());

                variante = variante + alternativerBefehl;
                ausgabe.append(variante);
            }

            ausgabe.append(UMBRUCH).append(erlaeuterung).append(aktuellesKommando.getDescription()).append(UMBRUCH);
        }

        ausgabe.append(UMBRUCH).append(rand);

        System.out.println(ausgabe);
    }

    /**
     * Entfernt den Zusatz vom Befehl nach dem letzten Leerzeichen.
     * @param eingabe Nutzereingabe: "zutat 1"
     * @return Befehl: "zutat"
     */
    public static String befehl(String eingabe) {
        return eingabe.substring(0, eingabe.lastIndexOf(' '));
    }

    /**
     * Trennt das Argument an der Stelle des Leerzeichens vom Befehl ab.
     * @param eingabe Nutzereingabe: "zutat 1"
     * @return Argument: "1"
     */
    public static String befehlsArgument(String eingabe) {
        return eingabe.substring(eingabe.lastIndexOf(' ') + 1);
    }

    /**
     * Ueberprueft, ob der uebergebene String eine Nummer ist.
     * @param string Zu ueberpruefender String
     * @return 'true' oder 'false'
     */
    public static boolean stringIsNumber(String string) {
        try {
            Integer.parseInt(string);
            return true;
        }
        catch(NumberFormatException e) {
            return false;
        }
    }

    public static void unbekannteEingabe() {
        System.err.println("Unbekannte Eingaben!");
    }
}
