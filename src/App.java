import java.util.Scanner;

/**
 * Hauptklasse für die Kontrolle des Programms.
 */
public class App {

    public static Scanner scanner = new Scanner(System.in);
    public static Bestellung bestellung = new Bestellung();

    /**
     * Main-Methode
     * @param args Startbefehl wird nicht verwendet
     */
    public static void main(String[] args) {

//        Burger testBurger = new Burger("testBurger");
//        Zutat broetchen = new Broetchen(1, "Sesam", 0.5f, false, true, false, 5, 4);
//        Zutat salat = new Salat(2, "Rucola", 0.1f);
//        Zutat[] zutaten = new Zutat[] {broetchen, salat};
//        testBurger.zutatenHinzufuegen(zutaten);
//        testBurger.berechneHoehe();
//        var test = testBurger.getGesamtHoehe();

        nutzerBefehlseingabe();
    }


    /**
     * Fragt den Benutzer so lange nach der Eingabe eines Befehls, bis dieser 'bestellen' oder 'quit' eingibt.
     */
    private static void nutzerBefehlseingabe() {
        String eingabe;

        do {
            System.out.println("Bitte deine Eingabe:");
            System.out.print("> ");
            eingabe = scanner.nextLine();
            String[] befehlsTeile = teileBefehl(eingabe);

            switch (befehlsTeile[0].toLowerCase()){
                case "menu", "menü":
                    //TODO: Menue Methode
                    break;
                case "neuerburger": // Vorsicht! Getrennte Worte werden nicht richtig verarbeitet!!
                    bestellung.neuerBurger(befehlsTeile[1]);
                    break;
                case "zutat":
                    if(StringIsNumber(befehlsTeile[1])) {
                        //TODO: Zutat Methode
                    }
                    else {
                        System.err.println("'zutat " + befehlsTeile[1] + "' ist kein gültiger Befehl,\n" +
                                "da '" + befehlsTeile[1] + "' keine Nummer ist!");
                    }
                    break;
                case "ok":
                    //TODO: OK-Befehl
                    break;
                case "meine burger": // Vorsicht! Getrennte Worte werden nicht richtig verarbeitet!!
                    //TODO: Burger auflisten
                    break;
                case "bestellen":
                    //TODO: Bestellung finalisieren
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
        //TODO
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
