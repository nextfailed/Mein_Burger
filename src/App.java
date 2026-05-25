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
}
