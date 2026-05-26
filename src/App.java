import java.awt.Color;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Hauptklasse für die Kontrolle des Programms.
 */
public class App {
    private static final int MAX_BURGERANZAHL = 10;
    public static final char UMBRUCH = '\n';

    public static final Scanner scanner = new Scanner(System.in);
    public static final DyeBucket dyebucket = new DyeBucket();

    private static Burger[] burgerListe = new Burger[MAX_BURGERANZAHL];
    private static boolean isEmpty = true;
    private static Burger aktiverBurger;
    private static boolean wirdZusammengestellt = false;

    public static void main(String[] args) {
        StringBuffer ausgabe = new StringBuffer();
        generiereKatalog();

        // Kopfzeile
        String slogen = "You'll never burger alone - Create your Burger";
        System.out.println("#".repeat(slogen.length()) + (UMBRUCH));
        System.out.println(slogen);

        Color keyWordColor = Color.CYAN;
        ausgabe.append(UMBRUCH).append("- Mit " + dyebucket.dyeText("'help'", keyWordColor) +
                " kannst du dir alle zur Verfuegung stehenden Befehle anzeigen lassen.");

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
    private static void befehlseingabe() {
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
                case "menu":
                    menu();
                    break;
                case "neuer burger":
                    if(!wirdZusammengestellt) {
                        neuerBurger(argument);
                        wirdZusammengestellt = true;

                        System.out.println("Du stellst einen neuen Burger zusammen.");
                        System.out.println("Mit 'ok' kannst du deine Zusammenstellung abschließen.");
                    }
                    else {
                        System.err.println("FEHLER! Der Burger " + aktiverBurger.getName() + " befindet sich noch" +
                                "in der Zusammenstellung. Bitte beende erste die Zusammenstellung mit 'ok'.");
                    }
                    break;
                case "zutat":
                    if(stringIsNumber(argument)) {
                        zutatHinzufuegen(Integer.parseInt(argument));
                    }
                    else {
                        System.err.println("FEHLER! '" + argument + "' ist keine Nummer!");
                    }
                    break;
                case "ok":
                    wirdZusammengestellt = false;
                    break;
                case "meine burger":
                    meineBurger();
                    break;
                case "bestellen":
                    bestellen();
                    break;
                case "help", "hilfe":
                    help();
                    break;
                case "quit":
                    System.out.println(dyebucket.dyeText("Auf Wiedersehen!" , Color.blue));
                    return;
                    // Nur dummy fuer korrekte Ausfuehrung des Switchs
                    //break;
                default:
                    unbekannteEingabe();
                    break;
            }

        } while(true); //(!eingabe.equals("quit"));
    }

    /**
     * Gibt das gesamte Menue ueber die Konsole aus.
     */
    private static void menu() {
        ArrayList<Zutat> zutatenKatalog = Zutat.getKatalog();

        System.out.println("\n##### Folgende Zutaten stehen zur Auswahl: #####\n");

        for(Zutat aktuelleZutat : zutatenKatalog) {
            System.out.println(aktuelleZutat.toString());
        }
    }

    /**
     * Fuegt der Bestellung einen neuen Burger mit dem uebergebenen Namen hinzu.
     * @param burgerName Name des Burgers
     */
    private static void neuerBurger(String burgerName) {
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
    private static void zutatHinzufuegen(int nummer) {
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
    private static void meineBurger() {
        if(!isEmpty){
            System.out.println("Folgende Burger wurden deiner Bestellung hinzugefuegt:");
            for (int i = 0; i < burgerListe.length; i++) {
                Burger aktuellerBurger = burgerListe[i];

                if(aktuellerBurger != null) {
                    System.out.println((i + 1) + ")" + aktuellerBurger);
                }
            }
        }
        else {
            System.err.println("Der Bestellung wurden noch keine Burger hinzugefuegt. Bitte fuege der\n" +
                    "Bestellung zunaechst einen neuen Burger mit 'neuer Burger' hinzu.");
        }
    }

    private static void bestellen() {

        // 1. alle burger zubereiten
        // 2. alle burger printen
        // 3. gesamtpreis ausgeben

        for(int i = 0; i < burgerListe.length; i++) {
            burgerListe[i].zubereiten();
        }
    }

    private static void help() {
        StringBuffer ausgabe = new StringBuffer();

        Color keyWordColor = Color.CYAN;
        ausgabe.append(UMBRUCH).append("- Mit " + dyebucket.dyeText("'help'", keyWordColor) +
                " kannst du dir alle zur Verfuegung stehenden Befehle anzeigen lassen.");
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
        new Bratling(22, "Falafel-Bratling", 1.45f, 210, 21).setVegan().setClassic(true);
        new Bratling(23, "Gemuese Bratling", 1.75f, 240, 25).setVegetarisch().setClassic(true);

        // Gemuese
        new Gemuese(40, "Tomate", 0.25f, 3, 3).setClassic(true);;
        new Gemuese(41, "Salzgurke", 0.15f, 4, 2).setClassic(true);
        new Gemuese(42, "Rote Zwiebelringe", 0.08f, 5, 2);
        new Gemuese(43, "Jalapeno-Ringe", 0.08f, 5, 2);

        // Salat
        new Salat(30, "Eisbergsalat", 0.18f);
        new Salat(31, "Roculasalat", 0.25f);

        // Sauce
        new Sauce(50, "Ketchup", 0.10f, 10, Geschmack.NORMAL).setVegan().setClassic(true);
        new Sauce(51, "Sandwich-Sauce", 0.15f, 10, Geschmack.NORMAL).setVegetarisch().setClassic(true);
        new Sauce(52, "Chili-Sauce", 0.25f, 8, Geschmack.SCHARF).setVegan();
        new Sauce(53, "Honig-Senf-Sauce", 0.18f, 8, Geschmack.SUESS).setVegetarisch();

        // Platz fuer Erweitungen
    }
}
