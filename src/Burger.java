import java.util.ArrayList;

public class Burger {
    private String name;
    private float gesamtHoehe = 0.0f;
    private float gesamtPreis;
    private Zutat[] zutaten;

    public Burger(String name) {
        this.name = name;
    }

    // Getter
    public String getName() {
        return name;
    }

    public float getGesamtHoehe() {
        return gesamtHoehe;
    }

    public float getGesamtPreis() {
        return gesamtPreis;
    }

    public void berechneHoehe() {
        for(Zutat aktuelleZutat : zutaten) {
            if(aktuelleZutat instanceof Salat || aktuelleZutat instanceof Sauce) {
                continue;
            }
            else {
                gesamtHoehe += aktuelleZutat.berechneHoehe();
            }
        }
    }

    public void zutatenHinzufuegen(Zutat[] zutaten) {
        int arrayLength = zutaten.length;
        this.zutaten = new Zutat[arrayLength];

        for(int i = 0; i < zutaten.length; i++) {
            this.zutaten[i] = zutaten[i];
        }
    }
}
