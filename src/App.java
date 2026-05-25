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
        bestellung = new Bestellung();

        do {
            System.out.println("Bitte deine Eingabe:");
            System.out.print("> ");
            eingabe = scanner.nextLine();
            String[] befehlsTeile = teileBefehl(eingabe);

            switch (befehlsTeile[0].toLowerCase()){
                case "menu", "menü":
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
                    //TODO: OK-Befehl
                    break;
                case "meineburger": // Vorsicht! Getrennte Worte werden nicht richtig verarbeitet!!
                    bestellung.meineBurger();
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
        ArrayList<Zutat> zutatenKatalog = Zutat.getKatalog();

        System.out.println("##### Folgende Zutaten stehen zur Auswahl: #####");

        for(Zutat aktuelleZutat : zutatenKatalog) {
            aktuelleZutat.toString();
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

        // 
    }
}
