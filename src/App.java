import java.util.Scanner;

/**
 * Hauptklasse für die Kontrolle des Programms.
 */
public class App {

    public static Scanner scanner = new Scanner(System.in);

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

    private static void nutzerBefehlseingabe() {
        String eingabe;

        do {
            System.out.println("Bitte deine Eingabe:");
            System.out.print("> ");
            eingabe = scanner.nextLine();
            String[] befehlsTeile = teileBefehl(eingabe);

            switch (befehlsTeile[0].toLowerCase()){
                case "menu", "menü":
                    break;
                case "neuer burger":
                    break;
                case "zutat":
                    if(StringIsNumber(befehlsTeile[1])) {
                        //TODO: Zutat Methode
                    }
                    break;
                case "ok":
                    break;
                case "meine burger":
                    break;
                case "bestellen":
                    break;
                default:
                    System.err.println("Unbekannte Eingaben!");
                    break;
            }
        } while(!eingabe.equals("quit"));
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
