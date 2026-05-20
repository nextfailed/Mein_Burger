public class App {

    public static void main(String[] args) {

        Burger testBurger = new Burger("testBurger");
        Zutat broetchen = new Broetchen(1, "Sesam", 0.5f, false, true, false, 5, 4);
        Zutat salat = new Salat(2, "Rucola", 0.1f);
        Zutat[] zutaten = new Zutat[] {broetchen, salat};
        testBurger.zutatenHinzufuegen(zutaten);
        testBurger.berechneHoehe();
        var test = testBurger.getGesamtHoehe();
    }
}
