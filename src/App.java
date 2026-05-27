import java.awt.Color;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Hauptklasse für die Kontrolle des Programms.
 */
public final class App {
    protected static final int MAX_BURGERANZAHL = 10;
    public static final char UMBRUCH = '\n';

    public static final Scanner scanner = new Scanner(System.in);
    public static final DyeBucket dyebucket = new DyeBucket();

    protected static Burger[] burgerListe = new Burger[MAX_BURGERANZAHL];
    protected static boolean isEmpty = true;
    protected static Burger aktiverBurger;
    protected static boolean wirdZusammengestellt = false;

    protected static final Color keyWordColor = Color.BLUE;
    protected static final Color quitWordColor = Color.RED;

    public static void main(String[] args) {
        StringBuffer ausgabe = new StringBuffer();
        generiereKatalog();

        // Kopfzeile
        String slogen = "You'll never burger alone - Create your Burger";
        String einleitung = "- Mit ";
        
        ausgabe.append("#".repeat(slogen.length()) + (UMBRUCH));
        ausgabe.append(slogen + (UMBRUCH));

        // Help-Ausgabe
        ausgabe.append(UMBRUCH).append(einleitung);
            
            // Befehle
            ausgabe.append(highlightBefehl("help")).append(", ");
            ausgabe.append(highlightBefehl("h")).append(" oder ");
            ausgabe.append(highlightBefehl("?")); 

        ausgabe.append(" kannst du dir alle zur Verfuegung stehenden Befehle anzeigen lassen.").append(UMBRUCH);

        // Quit-Ausgabe
        ausgabe.append(einleitung);
            // Befehle
            ausgabe.append(highlightBefehl("quit")).append(", ");       
            ausgabe.append(highlightBefehl("q")).append(" oder ");   
            ausgabe.append(highlightBefehl("!")); 

        ausgabe.append(" kannst du das Programm beenden.").append(UMBRUCH);

        ausgabe.append("#".repeat(slogen.length()));

        System.out.println(ausgabe.toString());

        //        Burger testBurger = new Burger("testBurger");
        //        testBurger.zutatHinzufuegen(Zutat.getZutat(10));
        //        testBurger.zutatHinzufuegen(Zutat.getZutat(20));
        //        testBurger.zutatHinzufuegen(Zutat.getZutat(30));
        //        System.out.println(testBurger);

        befehlseingabe();
    }

    /**
     * Fragt den Benutzer so lange nach der Eingabe eines Befehls, bis dieser 'bestellen' oder 'quit' eingibt.
     */
    protected static void befehlseingabe() {
        String eingabe;

        do{
            System.out.println(dyebucket.dyeText("Bitte deine Eingabe:", Color.YELLOW));
            System.out.print("> ");
            eingabe = scanner.nextLine();

            if(!eingabe.contains(" ")) {
                eingabe += " ";
            }

            String befehl = befehl(eingabe).toLowerCase();
            String argument = befehlsArgument(eingabe);

            switch(befehl) {
                case "menu", "m":
                    menu();
                    break;

                case "neuer burger", "new":{
                    if(!wirdZusammengestellt) {
                        neuerBurger(argument);
                        wirdZusammengestellt = true;

                        System.out.println("\nDu stellst einen neuen Burger zusammen.");
                        System.out.println("Mit 'ok' kannst du deine Zusammenstellung abschliessen.");
                    }

                    else {
                        System.err.println("FEHLER! Der Burger " + aktiverBurger.getName() + " befindet sich noch in der Zusammenstellung."
                        + "Bitte beende erste die Zusammenstellung mit 'ok'.");
                    }

                    break;
                }

                case "zutat", "ingredient":{
                    if(stringIsNumber(argument)) {
                        zutatHinzufuegen(Integer.parseInt(argument));
                    }

                    else {
                        System.err.println("FEHLER! '" + argument + "' ist keine Nummer!");
                    }

                    break;
                }

                case "ok", "done":{
                    if(!wirdZusammengestellt){
                        System.err.println("FEHLER: du erstellst momentan noch keinen burger.\nBitte erstelle einen neuen Burger mithilfe von: 'Mein burger [" +  highlightBefehl("Burgername") + "]'");
                        break;
                    }

                    if(aktiverBurger != null && aktiverBurger.getAnzahlZutaten() == 0){
                        System.err.println("FEHLER: du hast noch keine Zutaten fuer '" + aktiverBurger.getName() + "' hinzugefuegt\nBitte fuege deinem neuen Burger vorerst mindestens ein Broetchen hinzu.");

                        break;
                    }

                    wirdZusammengestellt = false;
                    System.out.println("Dein Burger '" + aktiverBurger.getName() + " wurde in Bestellung gegeben.");

                    break;
                }

                case "meine burger", "my":{
                    meineBurger();
                    break;

                }

                case "bestellen", "order":{
                    bestellen();
                    break;
                }

                case "hilfe", "help", "h", "?":{
                    help();
                    break;
                }

                case "abbruch", "cancel":{ // Resettet eine neue Zusammenstellung
                    if(aktiverBurger == null || wirdZusammengestellt) {
                        System.err.println("Sie erstellen momentan noch keinen neuen Burger.");
                        break;
                    }

                    aktiverBurger = null;
                    break;
                }

                case "beende", "quit", "q", "!":{ // Beendet das Programm
                    System.out.println(dyebucket.dyeText("Auf Wiedersehen!" , Color.blue));
                    return;
                }
                    
                    //break;
                default:{
                    unbekannteEingabe();
                    break;
                }
            }

        } while(true); //(!eingabe.equals("quit")); => Redundant, da er auf die gegebenen Schluesselwoerter das Programm beendet
    }

    /**
     * Gibt das gesamte Menue ueber die Konsole aus.
     */
    protected static void menu() {
        ArrayList<Zutat> zutatenKatalog = Zutat.getKatalog();
        String seitenrand = "#".repeat(5);

        System.out.println(seitenrand+ "\nFolgende Zutaten stehen zur Auswahl: " + seitenrand + UMBRUCH);

        for(Zutat aktuelleZutat : zutatenKatalog) {
            System.out.println(aktuelleZutat.toString());
        }
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
     * Faerbt ein Keyword eines Commands mit der festgelegten keyword-farbe ein
     * @param befehl wird als einfacher String erwartet
     * @return Keyword, welches gefaerbt, kursiv notiert und in ' ' eingebettet ist 
     */
    protected static String highlightBefehl(String befehl){
        return highlightBefehl(befehl, keyWordColor);
    }

    /**
     * Faerbt ein Quit-Keyword eines Commands mit der festgelegten quitkeyword-farbe ein
     * @param befehl wird als einfacher String erwartet
     * @return Keyword, welches gefaerbt, kursiv notiert und in ' ' eingebettet ist 
     */
    protected static String highlightQuit(String befehl){
        return highlightBefehl(befehl, quitWordColor);
    }



    /**
     * Fuegt der Bestellung einen neuen Burger mit dem uebergebenen Namen hinzu.
     * @param burgerName Name des Burgers
     */
    protected static void neuerBurger(String burgerName) {
        Burger neuerBurger = new Burger(burgerName);

        try {
            for(int i = 0; i < burgerListe.length; i++) {
                if(burgerListe[i] == null) {
                    burgerListe[i] = neuerBurger;
                    aktiverBurger = neuerBurger;
                    isEmpty = false;
                    return;
                }
            }
        }
        catch (Exception exception) {
            System.err.println("Es wurde bereits das Maximum von zehn Burgern in dieser Bestellung erreicht!");
        }
    }

    /**
     * Fuegt dem aktuellen Burger die Zutat mit der uebergebenen Nummer hinzu.
     * @param nummer Zutatennummer
     */
    protected static void zutatHinzufuegen(int nummer) {
        System.out.println("");

        if(aktiverBurger != null) {
            Zutat zutat = Zutat.getZutat(nummer);
            aktiverBurger.zutatHinzufuegen(zutat);
        }
        else {
            System.err.println("FEHLER! Zurzeit wird kein Burger von dir erstellt. Bitte fuege der\n" +
                    "Bestellung zunaechst einen neuen Burger mit 'neuer Burger " +
                    dyebucket.dyeItalic(dyebucket.dyeText("Burgername", Color.red)) + "' hinzu.");
        }
    }

    /**
     * Gibt alle Burger dieser Bestellung aus. Sollte noch kein Burger aufgenommen worden sein,
     * wird ein entsprechender Fehler ausgegeben.
     */
    protected static void meineBurger() {
        if(!isEmpty){
            String seiten = "#".repeat(4);
            System.out.println(seiten + " Folgende Burger wurden deiner Bestellung hinzugefuegt: " + seiten);

            for (int i = 0; i < burgerListe.length; i++) {
                Burger aktuellerBurger = burgerListe[i];

                if(aktuellerBurger != null) {
                    System.out.println(+ (i + 1) + ") " + aktuellerBurger);
                }
            }
        }

        else {
            System.err.println("Der Bestellung wurden noch keine Burger hinzugefuegt. Bitte fuege der\n" +
                    "Bestellung zunaechst einen neuen Burger mit 'neuer Burger' hinzu.");
        }
    }

    protected static void bestellen() {

        // 1. alle burger zubereiten
        // 2. alle burger printen
        // 3. gesamtpreis ausgeben

        for(int i = 0; i < burgerListe.length; i++) {
            burgerListe[i].zubereiten();
        }
    }

    protected static void help() {
        final StringBuffer ausgabe = new StringBuffer();

        final String einleitung = "";
        final String alternativerBefehl = " | ";
        final String erlaeuterung = "=> ";
        
        String anfang = dyebucket.dyeText("Hier sind alle noetigen Kommandos mit ihren Zusatzargumenten und ihrer Beschreibung aufgelistet.", Color.YELLOW);
        String rand = "#".repeat(anfang.length());

        // Anfang
        ausgabe.append(rand).append(UMBRUCH);

        ausgabe.append(anfang);

        ausgabe.append(UMBRUCH).append(UMBRUCH).append(erlaeuterung).append("'Kommando [Argument]'");
        ausgabe.append(UMBRUCH).append("Weder Argumente noch Kommands sind Case-Sensitiv.");

        ausgabe.append(UMBRUCH).append(UMBRUCH).append(dyebucket.dyeText("Wichtig! Es duerfen keine Leerzeichen am Ende hinzugefuegt werden, wenn Argumente vorhanden sind!", quitWordColor));
        
        ausgabe.append(UMBRUCH).append(rand).append(UMBRUCH);

        // Erlaeuterung der Farbgebung
        ausgabe.append(UMBRUCH).append(dyebucket.dyeItalic("Farb-Bedeutungen: ")).append(alternativerBefehl);
        for(Kommando.Kommandotyp typ : Kommando.Kommandotyp.values()){
            ausgabe.append(dyebucket.dyeText(typ.toString(), typ.getEigenfarbe())).append(alternativerBefehl);
        }

        ausgabe.append(UMBRUCH).append(UMBRUCH).append(rand).append(UMBRUCH);

        /// Befehle:
        for(Kommando aktuellesKommando : Kommando.values()){
            if(aktuellesKommando == null) continue;

            ausgabe.append(UMBRUCH);
            ausgabe.append(einleitung);
        
            String[] kommandoVarianten = aktuellesKommando.getKommandos();

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

        /*
        // Help-Ausgabe
        ausgabe.append(UMBRUCH).append(einleitung);
            
            // Befehle (Syntax)
            ausgabe.append(highlightBefehl("help")).append(alternativerBefehl);
            ausgabe.append(highlightBefehl("h")).append(alternativerBefehl);
            ausgabe.append(highlightBefehl("?")).append(UMBRUCH); 

        // Erlaeuterung
        ausgabe.append(erlaeuterung).append(dyebucket.dyeItalic("Stellt alle Befehle zur Verfuegung")).append(UMBRUCH);

        ausgabe.append(UMBRUCH);

        // Quit-Ausgabe
        ausgabe.append(einleitung);
            // Befehle (Syntax)
            ausgabe.append(highlightQuit("beende")).append(alternativerBefehl); 
            ausgabe.append(highlightQuit("quit")).append(alternativerBefehl);       
            ausgabe.append(highlightQuit("q")).append(alternativerBefehl);   
            ausgabe.append(highlightQuit("!")).append(UMBRUCH);
            
        // Erlaeuterung
        ausgabe.append(erlaeuterung).append(dyebucket.dyeItalic("beendet das Programm")).append(UMBRUCH);

        ausgabe.append(UMBRUCH);

        // Menu
        ausgabe.append(einleitung);
            // Befehle (Syntax)
            ausgabe.append(highlightBefehl("Menu")).append(alternativerBefehl);       
            ausgabe.append(highlightBefehl("M")).append(UMBRUCH);  

        // Erlaeuterung
        ausgabe.append(erlaeuterung).append(dyebucket.dyeItalic("Gibt alle moeglichen Zutaten")).append(UMBRUCH);

        // Neuer Burger
        ausgabe.append(einleitung);
            // Befehle (Syntax)
            ausgabe.append(highlightBefehl("neuer Burger [Burgername:String]")).append(alternativerBefehl); 
            ausgabe.append(highlightBefehl("new [Burgername:String]")).append(UMBRUCH); 

        // Erlaeuterung
        ausgabe.append(erlaeuterung).append(dyebucket.dyeItalic("Erstelle einen neuen Burger. Mithilfe von " + highlightBefehl("Zutat [Zutatsnummer]") + " koennen diesem Zutaten hinzugefuegt werden. Burger muessen mit "+highlightBefehl("ok")+" abgeschlossen werden.\nAchtung: Du kannst maximal " + MAX_BURGERANZAHL + " Burger erstellen")).append(UMBRUCH);

        // Zutat
        ausgabe.append(einleitung);
            // Befehle (Syntax)
            ausgabe.append(highlightBefehl("Zutat [Burgername:String]")).append(alternativerBefehl); 
            ausgabe.append(highlightBefehl("Ingredient [zutatnummer:int]")).append(UMBRUCH); 

        // Erlaeuterung
        ausgabe.append(erlaeuterung).append(dyebucket.dyeItalic("Fuege deinem Burger nach " + highlightBefehl("Mein Burger [Burgername]") + " eine Zutat mithilfe ihrer ID hinzu")).append(UMBRUCH);

        // Abbrechen
        ausgabe.append(einleitung);
            // Befehle (Syntax)
            ausgabe.append(highlightQuit("Abbruch")).append(alternativerBefehl);  
            ausgabe.append(highlightQuit("Cancel")).append(UMBRUCH); 

        // Erlaeuterung
        ausgabe.append(erlaeuterung).append(dyebucket.dyeItalic("Bricht die Zusammenstellung deines aktuellen Burgers ab und erstellt einen neuen")).append(UMBRUCH);

        // ok
        ausgabe.append(einleitung);
            // Befehle (Syntax)
            ausgabe.append(highlightBefehl("ok")).append(UMBRUCH); 

        // Erlaeuterung
        ausgabe.append(erlaeuterung).append(dyebucket.dyeItalic("Beende das Zusammenstellen deines Burgers. Dein Burger kann bis zu " + Burger.MAX_ZUTATENANZAHL + " Zutaten haben.\nAchtung: Es muss mindestens ein Broetchen hinzugefuegt werden! Du musst ebenfalls einen neuen Burger erstellen, bevor du einen abgeben kannst.")).append(UMBRUCH);

        
        // Meine Burger
        ausgabe.append(einleitung);
            // Befehle (Syntax)
            ausgabe.append(highlightBefehl("Meine Burger")).append(alternativerBefehl);       
            ausgabe.append(highlightBefehl("My")).append(UMBRUCH);  

        // Erlaeuterung
        ausgabe.append(erlaeuterung).append(dyebucket.dyeItalic("Listet alle von dir erstellten Burger aus.")).append(UMBRUCH);

        // Bestellen
        ausgabe.append(einleitung);
            // Befehle (Syntax)
            ausgabe.append(highlightBefehl("Order")).append(alternativerBefehl);       
            ausgabe.append(highlightBefehl("Bestellen")).append(UMBRUCH);  

        // Erlaeuterung
        ausgabe.append(erlaeuterung).append(dyebucket.dyeItalic("Gibt die erstellten Burger, ihr Rezept, den Gesamtpreis und Gesamtzubereitungszeit an und beendet die Bestellung.")).append(UMBRUCH);

        */

        System.out.println(ausgabe.toString());
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

    /**
     * Erzeugt den Standartkatalog, der von Anfang an existiert.
     * Zutaten werden automatisch in der ArrayListe<Zutat> innerhalb der
     * Zutatenklasse durch den Konstruktoraufruf deklariert und
     * muessen daher nur instanziiert werden.
     */
    public static void generiereKatalog() {

        // Broetchen
        new Broetchen(10, "Hamburger (Standard)", 0.85f, 27, 90).setVegetarisch().setClassic(true);
        new Broetchen(11, "Hamburger Sesam", 0.95f, 90, 27).setVegetarisch().setClassic(true);
        new Broetchen(12, "Vegan-Broetchen", 0.55f, 240, 34).setVegan();
        new Broetchen(13, "Ciabatta", 0.45f, 330, 41).setVegetarisch();

        // Bratlinge
        new Bratling(20, "Rindfleisch (Original)", 1.85f, 270, 25).setNonVegan().setClassic(true);
        new Bratling(21, "Haenchenfleisch gegrillt", 1.15f,  180, 11).setNonVegan().setClassic(true);
        new Bratling(22, "Falafel-Bratling", 1.45f, 210, 21).setVegan();
        new Bratling(23, "Gemuese Bratling", 1.75f, 240, 25).setVegetarisch();

        // Salat
        new Salat(30, "Eisbergsalat", 0.18f);
        new Salat(31, "Roculasalat", 0.25f);

        // Gemuese
        new Gemuese(40, "Tomate", 0.25f, 3, 3).setClassic(true);;
        new Gemuese(41, "Salzgurke", 0.15f, 4, 2).setClassic(true);
        new Gemuese(42, "Rote Zwiebelringe", 0.08f, 5, 2).switchScheibenart();
        new Gemuese(43, "Jalapeno-Ringe", 0.08f, 5, 2).switchScheibenart();

        // Sauce
        new Sauce(50, "Ketchup", 0.10f, 10, Geschmack.NORMAL).setVegan().setClassic(true);
        new Sauce(51, "Sandwich-Sauce", 0.15f, 10, Geschmack.NORMAL).setVegetarisch().setClassic(true);
        new Sauce(52, "Chili-Sauce", 0.25f, 8, Geschmack.SCHARF).setVegan();
        new Sauce(53, "Honig-Senf-Sauce", 0.18f, 8, Geschmack.SUESS).setVegetarisch();

        // Platz fuer Erweitungen
    }
}
