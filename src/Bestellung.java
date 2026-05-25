public class Bestellung {
    private final int MAX_BURGERANZAHL = 10;

    private Burger[] burgerListe = new Burger[MAX_BURGERANZAHL];
    private boolean isEmpty = true;

    private Burger aktiverBurger;

    // Klassen-Methoden
    /**
     * Fuegt der Bestellung einen neuen Burger mit dem uebergebenen Namen hinzu.
     *
     * @param burgerName Name des Burgers
     */
    public void neuerBurger(String burgerName) {
        Burger neuerBurger = new Burger(burgerName);

        for(int i = 0; i < burgerListe.length; i++) {
            if(burgerListe[i] == null) {
                burgerListe[i] = neuerBurger;
                aktiverBurger = neuerBurger;
                isEmpty = false;
                return;
            }
        }

        System.err.println("Es wurde bereits das Maximum von zehn Burgern in dieser Bestellung erreicht!");
    }

    /**
     * Fuegt dem aktuellen Burger die Zutat mit der uebergebenen Nummer hinzu.
     * @param nummer Zutatennummer
     */
    public void zutatHinzufuegen(int nummer) {
        if(aktiverBurger != null) {
            Zutat zutat = Zutat.getZutat(nummer);
            System.out.println(zutat.toString());
            aktiverBurger.zutatHinzufuegen(zutat);
        }
        else {
            System.err.println("FEHLER! Zurzeit wird kein Burger von dir erstellt. Bitte fuege der\n" +
                    "Bestellung zunaechst einen neuen Burger mit 'neuer Burger' hinzu.");
        }
    }

    /**
     * Beendet die Bearbeitung des Burgers, den der Nutzer zurzeit bearbeitet.
     */
    public void ok() {
        System.out.println("Dein Burger '" + aktiverBurger.getName() + "' wurde aufgenommen.");
        aktiverBurger = null;
    }

    /**
     * Gibt alle Burger dieser Bestellung aus. Sollte noch kein Burger aufgenommen worden sein,
     * wird ein entsprechender Fehler ausgegeben.
     */
    public void meineBurger() {
        if(!isEmpty){
            System.out.println("Folgende Burger wurden deiner Bestellung hinzugefuegt:");
            for (int i = 0; i < burgerListe.length; i++) {
                Burger aktuellerBurger = burgerListe[i];
                System.out.println((i + 1) + ")" + aktuellerBurger.toString());
            }
        }
        else {
            System.err.println("Der Bestellung wurden noch keine Burger hinzugefuegt. Bitte fuege der\n" +
                    "Bestellung zunaechst einen neuen Burger mit 'neuer Burger' hinzu.");
        }
    }

    /**
     * Finalisiert die Bestellung und beginnt die Zubereitung der Burger.
     */
    public void bestellen() {
        //TODO
    }


}
