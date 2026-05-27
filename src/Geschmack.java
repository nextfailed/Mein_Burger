import java.awt.Color;

/**
 * Geschmack fuer Saucen aber auch andere Klassen falls neue ergaenzt werden besitzen einen Geschmack als Enum.
 * Geschmaecker besitzen ihren eigenen Geschmack als String-Ausgabe und wird direkt als solches durch die toString-Methode mit ihrer jeweiligen Farbe direkt ausgegeben
 */
public enum Geschmack {
        NORMAL(""),
        SCHARF("Scharf", Color.RED),
        SUESS("Suess", Color.MAGENTA),
        SALZIG("Salzig", Color.BLUE);

        String beschreibung;
        Color farbrepraesentierung;
        static final DyeBucket bucket = new DyeBucket();

        private Geschmack(String name, Color color){
                this.beschreibung = name;
                this.farbrepraesentierung = color;
        }

        private Geschmack(String name){
                this(name, Color.WHITE);
        }

        @Override
        public String toString(){
                return bucket.dyeText(this.beschreibung, farbrepraesentierung);
        }
}
