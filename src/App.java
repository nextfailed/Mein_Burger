import java.util.ArrayList;
import java.util.Scanner;

/**
 * Hauptklasse für die Kontrolle des Programms.
 */
public class App {

    public final static Scanner scanner = new Scanner(System.in);
    public final static DyeBucket dyebucket = new DyeBucket();

    public static Bestellung bestellung;

    public static void main(String[] args) {
        generiereKatalog();

        System.out.println("################################################################################");
        System.out.println("\nYou'll never burger alone - Create your Burger");
        System.out.println("\nMit 'menu' kannst du dir alle zur Verfügung stehenden Zutaten anzeigen lassen.");
        System.out.println("Mit 'zutat' und der jeweiligen Nummer kannst du eine Zutat auswaehlen.");
        befehlseingabe();
    }

    /**
     * Fragt den Benutzer so lange nach der Eingabe eines Befehls, bis dieser 'bestellen' oder 'quit' eingibt.
     */
    private static void befehlseingabe() {
        String eingabe;
        bestellung = new Bestellung();

        do{
            System.out.println("Bitte deine Eingabe:");
            System.out.print("> ");
            eingabe = scanner.nextLine();
            String befehl = befehl(eingabe).toLowerCase();
            String argument = befehlsArgument(eingabe);

            switch(befehl) {
                case "menu":
                    menu();
                    break;
                case "neuer burger":
                    bestellung.neuerBurger(argument);
                    break;
                case "quit":
                    break;
                default:
                    unbekannteEingabe();
                    break;
            }

        } while(eingabe.equals("quit"));
    }

    /**
     * Gibt das gesamte Menue ueber die Konsole aus.
     */
    public static void menu() {
        ArrayList<Zutat> zutatenKatalog = Zutat.getKatalog();

        System.out.println("##### Folgende Zutaten stehen zur Auswahl: #####");

        for(Zutat aktuelleZutat : zutatenKatalog) {
            System.out.println(aktuelleZutat.toString());
        }
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
    public static boolean StringIsNumber(String string) {
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
     * Erzeugt den Standartkatalog, der von Anfang an existiert
     * Zutaten werden autoamtisch in der ArrayListe<Zutat> innerhalb der Zutatenklasse deklariert und muessen daher nur instanziiert werden
     */
    public static void generiereKatalog() {

        // Broetchen
        new Broetchen(10, "Hamburger (Standard)", 0.85f, 27, 90).setVegetarisch().setClassic(true);
        new Broetchen(11, "Hamburger Sesam", 0.95f, 90, 27).setVegetarisch().setClassic(true);
        new Broetchen(12, "Vegan-Broetchen", 0.55f, 240, 34).setVegan();
        new Broetchen(12, "Ciabatta", 0.45f, 330, 41).setVegetarisch();

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

        // Platz fuer erweitungen
    }
}
