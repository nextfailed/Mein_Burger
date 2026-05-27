import java.awt.Color;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Hauptklasse für die Kontrolle des Programms.
 */
public final class App {
    // Finalisierte Maximalanzahlen
    protected static final int MAX_BURGERANZAHL = 10;

    // Tools
    public static final Scanner scanner = new Scanner(System.in);
    public static final DyeBucket dyebucket = new DyeBucket();

    // Bestellungsspeicher
    protected static Burger[] burgerListe = new Burger[MAX_BURGERANZAHL];
    protected static boolean isEmpty = true;
    protected static Burger aktiverBurger;
    protected static boolean wirdZusammengestellt = false;

    // Wichtig fuer Ausgaben
    protected static final String einleitung = "";
    protected static final String alternativerBefehl = " | ";
    protected static final String erlaeuterung = "=> ";
    public static final char UMBRUCH = '\n';

    // Farben fuer Befehle (Normal und Abbruch)
    protected static final Color keyWordColor = Color.BLUE;
    protected static final Color quitWordColor = Color.RED;


    public static void main(String[] args) {
        StringBuffer ausgabe = new StringBuffer();
        generiereKatalog();

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
        /// Befehle:
        /// 
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

    /*
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

                        System.out.println("\nDu stellst einen neuen Burger Namens "+ aktiverBurger.getName() + " zusammen.");
                        System.out.println("Mit " + highlightBefehl("ok") + " kannst du deine Zusammenstellung abschliessen.");
                    }

                    else {
                        System.err.println("\nFEHLER! Der Burger " + aktiverBurger.getName() + " befindet sich noch in der Zusammenstellung."
                        + "Bitte beende erste die Zusammenstellung mit 'ok'.");
                    }

                    break;
                }

                case "zutat", "ingredient":{
                    if(stringIsNumber(argument)) {
                        zutatHinzufuegen(Integer.parseInt(argument));
                    }

                    else {
                        System.err.println("\nFEHLER! '" + argument + "' ist keine Nummer!");
                    }

                    break;
                }

                case "ok", "done":{
                    if(!wirdZusammengestellt){
                        System.err.println("\nFEHLER: du erstellst momentan noch keinen burger.\nBitte erstelle einen neuen Burger mithilfe von: " + highlightBefehl("Mein burger [Burgername]"));
                        break;
                    }

                    if(aktiverBurger != null && aktiverBurger.getAnzahlZutaten() == 0){
                        System.err.println("\nFEHLER: du hast noch keine Zutaten fuer " + aktiverBurger.getName() + " hinzugefuegt\nBitte fuege deinem neuen Burger vorerst mindestens "+ highlightQuit("ein Broetchen") +" hinzu.");

                        break;
                    }

                    wirdZusammengestellt = false;
                    System.out.println("\nDein Burger " + aktiverBurger.getName() + " wurde deiner Bestellung hinzugefuegt.");

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
                    if(aktiverBurger == null && wirdZusammengestellt) {
                        System.err.println("Sie erstellen momentan noch keinen neuen Burger.");
                        break;
                    }

                    else{
                        aktiverBurger = null;

                        System.out.println(highlightQuit("\nDein neuer Burger wurde komplett zurueckgesetzt"));
                        wirdZusammengestellt = false;
                    }

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
    */

    /**
     * Fragt den Benutzer nach Befehlen und vergleicht den Input mit den jeweiligen Aliases der Kommandos.
     * Die Methode und das Programm hoeren auf, sobald einer der beenden- oder bestellen-Aliases eingegeben wird.
     * 
     * Die Kommandos sind in der Kommando-Enum Klasse dynamisch abspeicherbar und koennen der if-else Abfrage manuell hinzugefuegt werden.
     * Aliases und ihre Beschreibung werden automatisch in der befehlseingabe-methode, sowie beim Hilfs-Aufruf dynamisch hinzugefuert.     * 
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
                if(!wirdZusammengestellt) {
                    neuerBurger(argument);
                    wirdZusammengestellt = true;

                    System.out.println("\nDu stellst einen neuen Burger Namens "+ aktiverBurger.getName() + " zusammen.");
                    System.out.println("Mit " + highlightBefehl("ok") + " kannst du deine Zusammenstellung abschliessen.");
                }

                else {
                    System.err.println("\nFEHLER! Der Burger " + aktiverBurger.getName() + " befindet sich noch in der Zusammenstellung."
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
                continue;

            }

            else if(kommandoGefunden(befehl, Kommando.OK)){
                if(!wirdZusammengestellt){
                    System.err.println("\nFEHLER: du erstellst momentan noch keinen burger.\nBitte erstelle einen neuen Burger mithilfe von: " + highlightBefehl("Mein burger [Burgername]"));
                    continue;
                }

                if(aktiverBurger != null && aktiverBurger.getAnzahlZutaten() == 0){
                    System.err.println("\nFEHLER: du hast noch keine Zutaten fuer " + aktiverBurger.getName() + " hinzugefuegt\nBitte fuege deinem neuen Burger vorerst mindestens "+ highlightQuit("ein Broetchen") +" hinzu.");

                    continue;
                }

                wirdZusammengestellt = false;
                System.out.println("\nDein Burger " + aktiverBurger.getName() + " wurde deiner Bestellung hinzugefuegt.");
            }

            else if(kommandoGefunden(befehl, Kommando.NEW)){

                meineBurger();
                continue;

            }

            else if(kommandoGefunden(befehl, Kommando.ORDER)){
                bestellen();
            }


            else if(kommandoGefunden(befehl, Kommando.CANCEL)){ // Resettet eine neue Zusammenstellung
                if(aktiverBurger == null && wirdZusammengestellt) {
                    System.err.println("Sie erstellen momentan noch keinen neuen Burger.");
                    continue;
                }

                else{
                    aktiverBurger = null;

                    System.out.println(highlightQuit("\nDein neuer Burger wurde komplett zurueckgesetzt"));
                    wirdZusammengestellt = false;
                }

                continue;
            }

            else if(kommandoGefunden(befehl, Kommando.QUIT)){ // Beendet das Programm
                System.out.println(dyebucket.dyeText("Auf Wiedersehen!" , Color.blue));
                return;
            }
                
            else{
                unbekannteEingabe();
                continue;
            }
            

        } while(true); //(!eingabe.equals("quit")); => Redundant, da er auf die gegebenen Schluesselwoerter das Programm beendet
    }

    /**
     * Ueberprueft, ob der Eingabebefehl einem Alias entspricht
     * @return Gibt zurueck, ob einer der Aliases fuer einen bestimmten Befehl mit dem dem Befehl uebereinstimmt
     */
    public static boolean kommandoGefunden(String zuSuchenderAlias, Kommando kommando){
        for(String ueberpruefterAlias : kommando.getAliases()){
            if(zuSuchenderAlias.toLowerCase().equals(ueberpruefterAlias.toLowerCase())) return true;
        }

        return false;
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
            System.err.println("Es wurde bereits das Maximum von " + highlightQuit(MAX_BURGERANZAHL + "") + " Burgern in dieser Bestellung erreicht!");
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
            System.err.println("FEHLER! Zurzeit wird kein Burger von dir erstellt." +
                    "\nBitte fuege der Bestellung zunaechst einen neuen Burger mit "+ highlightBefehl(Kommando.NEW.getMainAlias()) + " hinzu.");
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
                    "Bestellung zunaechst einen neuen Burger mit " + highlightBefehl(Kommando.MY.getMainAlias()) + " hinzu.");
        }
    }

    /**
     *
     */
    protected static void bestellen() {
        // TODO: Unvollstaendig
        // 1. alle burger zubereiten
        // 2. alle burger printen
        // 3. gesamtpreis ausgeben

        float gesamtkosten = 0;
        float gesamtdauer = 0;

        String ueberschrift = "Ihre Burger werden frisch zubereitet: ";
        String rand = "#".repeat(ueberschrift.length());

        rand = dyebucket.dyeText(rand, Color.GREEN);

        System.out.println("");
        System.out.println(rand);
        System.out.println(dyebucket.dyeText(ueberschrift, Color.GREEN));
        System.out.println(rand);
        System.out.println("");

        int count = 1;
        for(Burger aktuellerBurger : burgerListe) {
            if(aktuellerBurger == null) continue;

            gesamtdauer += aktuellerBurger.zubereiten();
            gesamtkosten += aktuellerBurger.berechnePreis();

            System.out.println((count++) + ") " + aktuellerBurger.toString());
        }


        String dauerToString = "# Gesamtdauer: " + Math.round((gesamtdauer/60f)*100f)/100f + " Minuten";
        String preisToString = "# Ihre Kosten: " +Math.round(gesamtkosten*100f)/100f  + " EUR";

        rand = "#".repeat(Math.max(dauerToString.length(), preisToString.length()));

        System.out.println(rand);

        System.out.println(dyebucket.dyeText(dauerToString, Color.CYAN));
        System.out.println(dyebucket.dyeText(preisToString, Color.YELLOW));

        System.out.println(rand);

        burgerListe = new Burger[MAX_BURGERANZAHL];
        isEmpty = true;

        String abgeschlossen = "Ihre Bestellung wurde abgeschlossen. Sie koennen nun eine neue Bestellung aufgegeben.";
        rand = "#".repeat(abgeschlossen.length());

        System.out.println("");
        System.out.println(rand);
        System.out.println(dyebucket.dyeText(abgeschlossen, Color.GREEN));
        System.out.println(rand);
    }

    /**
     * Gibt alle Kommandos, Aliases und ihre Beschreibungen dynamisch in der Konsole aus.
     */
    protected static void help() {
        final StringBuffer ausgabe = new StringBuffer();

        
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

        /// Befehle:
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

        /* Vorherige Vorgehensweise: Brute-Force, ist nicht dynamisch erweiterbar und muss manuell angepasst werden
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
