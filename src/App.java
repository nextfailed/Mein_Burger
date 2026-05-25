import java.util.ArrayList;
import java.util.Scanner;

/**
 * Hauptklasse für die Kontrolle des Programms.
 */
public class App {

    public static Scanner scanner = new Scanner(System.in);
    public static Bestellung bestellung;

    /**
     * Main-Methode
     * @param args Startbefehl wird nicht verwendet
     */
    public static void main(String[] args) {
        System.out.println("You'll never burger alone - Create your Burger");
        System.out.println("\nMit 'menu' kannst du dir alle zur Verfügung stehenden Zutaten anzeigen lassen.");
        System.out.println("Mit 'zutat' und der jeweiligen Nummer kannst du eine Zutat auswaehlen.");
        nutzerBefehlseingabe();
    }

    /**
     * Fragt den Benutzer so lange nach der Eingabe eines Befehls, bis dieser 'bestellen' oder 'quit' eingibt.
     */
    private static void nutzerBefehlseingabe() {
        String eingabe;
        bestellung = new Bestellung();

        do {
            System.out.println("Bitte deine Eingabe:");
            System.out.print("> ");
            eingabe = scanner.nextLine();
            String[] befehlsTeile = teileBefehl(eingabe);

            switch (befehlsTeile[0].toLowerCase()){
                case "menu":
                    menu();
                    break;
                case "neuerburger": // Vorsicht! Getrennte Worte werden nicht richtig verarbeitet!!
                    bestellung.neuerBurger(befehlsTeile[1]);
                    break;
                case "zutat":
                    String befehlsZusatz = befehlsTeile[1];
                    if(StringIsNumber(befehlsZusatz)) {
                        bestellung.zutatHinzufuegen(Integer.parseInt(befehlsZusatz));
                    }
                    else {
                        System.err.println("'zutat " + befehlsTeile[1] + "' ist kein gültiger Befehl,\n" +
                                "da '" + befehlsTeile[1] + "' keine Nummer ist!");
                    }
                    break;
                case "ok":
                    bestellung.ok();
                    break;
                case "meineburger": // Vorsicht! Getrennte Worte werden nicht richtig verarbeitet!!
                    bestellung.meineBurger();
                    break;
                case "bestellen":
                    bestellung.bestellen();
                    break;
                case "quit":
                    System.out.println("Auf Wiedersehen!");
                    break;
                default:
                    System.err.println("Unbekannte Eingaben!");
                    break;
            }
        } while(!eingabe.equals("quit") && !eingabe.equals("bestellen"));
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
     * Teilt den Befehl vom Zusatz an der Stelle des Leerzeichens (z.B. "zutat bestellnummer").
     * @param eingabe Nutzereingabe
     * @return {Befehl, Zusatz}
     */
    private static String[] teileBefehl(String eingabe) {
        return eingabe.split(" ");
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
}
