public enum Geschmack {
        NORMAL("-"),
        SCHARF("Scharf"),
        SUESS("Suess"),
        SALZIG("Salzig");

        String beschreibung;

        private Geschmack(String name){
                this.beschreibung = name;
        }

        @Override
        public String toString(){
                return this.beschreibung;
        }
}
