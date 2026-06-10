import java.awt.*;

/**
 * Der Warenkorb verwaltet die Bestellung und den Burger der sich z.Z.
 * in Bearbeitung befindet. Maximale Anzahl der Burger pro Bestellung = 10.
 */
public class Warenkorb {
    public static final int MAX_BURGERANZAHL = 10;

    private final Burger[] burgerListe;
    private Burger aktiverBurger;

    private int size;

    public Warenkorb() {
        burgerListe = new Burger[MAX_BURGERANZAHL];
        aktiverBurger = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Burger getAktiverBurger() {
        return aktiverBurger;
    }

    public int size() {
        return size;
    }

    /**
     * Gibt zurueck, ob ein Burger z.Z. in Bearbeitung ist.
     * @return Wahr oder falsch
     */
    public boolean isInBearbeitung() {
        return aktiverBurger != null;
    }

    /**
     * Setzt den aktiven Burger inaktiv.
     */
    public void beendeAktiverBurger() {
        aktiverBurger = null;
    }

    /**
     * Fuegt dem Warenkorb einen neuen Burger hinzu.
     * @param neuerBurger neuer Burger
     */
    public boolean add(Burger neuerBurger) {
        if(size >= MAX_BURGERANZAHL) {
            System.err.println("FEHLER: Es koennen maximal 10 Burger in einer Bestellung aufgenommen werden!");
            return false;
        }
        burgerListe[size] = neuerBurger;
        size++;
        aktiverBurger = neuerBurger;
        return true;
    }

    /**
     * Fuegt dem aktiven Burger die uebergebene Zutat hinzu.
     * @param neueZutat neue Zutat
     */
    public void addZutat(Zutat neueZutat) {
        if(aktiverBurger != null) {
            if(neueZutat != null) {
                aktiverBurger.zutatHinzufuegen(neueZutat);

                System.out.println(aktiverBurger.getAnzahlZutaten() + " : " + Burger.MAX_ZUTATENANZAHL + " Zutat(en) hinzugefuegt.");
            }
        }
        else {
            System.err.println("FEHLER! Zurzeit wird kein Burger von dir erstellt." +
                    "\nBitte fuege der Bestellung zunaechst einen neuen Burger mit "+ App.highlightBefehl(Kommando.NEW.getMainAlias()) + " hinzu.");
        }
    }

    /**
     * Schliesst die Bestellung ab und beginnt die Zubereitung der einzelnen
     * Burger im Warenkorb.
     * @return String für die Konsolenausgabe
     */
    public String abschliessen() {
        StringBuilder stringBuilder = new StringBuilder();

        if(isEmpty()) {
            stringBuilder.append("Sie haben bis jetzt noch keine Bestellung aufgegeben.");
            stringBuilder.append("Bitte geben Sie mithilfe von ");
            stringBuilder.append(App.highlightBefehl(Kommando.NEW.getMainAlias()));
            stringBuilder.append(" eine Bestellung auf.");
            return stringBuilder.toString();
        }

        float gesamtkosten = 0;
        float gesamtdauer = 0;

        String ueberschrift = " Ihre Burger werden frisch zubereitet: ";
        String rand = "#".repeat(ueberschrift.length());

        rand = App.dyebucket.dyeText(rand, Color.GREEN);

        stringBuilder.append("\n");
        stringBuilder.append(rand);
        stringBuilder.append(App.dyebucket.dyeText(ueberschrift, Color.GREEN));
        stringBuilder.append(rand);
        stringBuilder.append("\n");

        int count = 1;
        for(Burger aktuellerBurger : burgerListe) {
            if(aktuellerBurger == null) continue;

            stringBuilder.repeat("\n", 2);

            gesamtdauer += aktuellerBurger.zubereiten();
            gesamtkosten += aktuellerBurger.berechnePreis();

            stringBuilder.append(count++);
            stringBuilder.append(") ");
            stringBuilder.append(aktuellerBurger);
        }

        stringBuilder.repeat("\n", 2);

        String dauerToString = "# Gesamtdauer: " + Math.round((gesamtdauer/60f)*100f)/100f + " Minuten";
        String preisToString = "# Ihre Kosten: " +Math.round(gesamtkosten*100f)/100f  + " EUR";

        rand = "#".repeat(Math.max(dauerToString.length(), preisToString.length()));

        stringBuilder.append(rand);

        stringBuilder.repeat("\n", 2);
        stringBuilder.append(App.dyebucket.dyeText(dauerToString, Color.CYAN));
        stringBuilder.append("\n");
        stringBuilder.append(App.dyebucket.dyeText(preisToString, Color.YELLOW));
        stringBuilder.repeat("\n", 2);

        stringBuilder.append(rand);

        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if(!isEmpty()){
            String seiten = "#".repeat(4);
            stringBuilder.append(seiten);
            stringBuilder.append(" Folgende Burger wurden deiner Bestellung hinzugefuegt: ");
            stringBuilder.append(seiten);
            stringBuilder.repeat("\n", 2);

            for (int i = 0; i < burgerListe.length; i++) {
                Burger aktuellerBurger = burgerListe[i];

                if(aktuellerBurger != null) {
                    stringBuilder.append(i + 1).append(". ").append(aktuellerBurger);
                }
            }
        }
        else {
            stringBuilder.append("Der Bestellung wurden noch keine Burger hinzugefuegt. Bitte fuege der\n");
            stringBuilder.append("Bestellung zunaechst einen neuen Burger mit ");
            stringBuilder.append(App.highlightBefehl(Kommando.MY.getMainAlias()));
            stringBuilder.append(" hinzu.");
        }
        return stringBuilder.toString();
    }
}
