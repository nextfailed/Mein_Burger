import java.awt.*;

public class Kaese extends Zutat{
    private final int schmelzzeit;
    private final KaeseTyp kaeseTyp;

    public Kaese(int nummer, String name, float preis, int schmelzzeit, KaeseTyp kaeseTyp) {
        super(nummer, name, preis);
        this.schmelzzeit = schmelzzeit;
        this.kaeseTyp = kaeseTyp;
        this.diaettyp = INDIKATOR_VEGETARISCH;
    }

    @Override
    public int zubereiten() {
        printZubereitung();

        return schmelzzeit;
    }

    @Override
    public float berechneHoehe() {
        return 0;
    }

    @Override
    public String getZubereitung() {
        return (this.name + " auf Bratling legen und " + schmelzzeit /60 + " Minuten und " + schmelzzeit % 60 + " Sekunden schmelzen");
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        String diaetString = getDiaetstypAsString();
        String isKlassisch = getKlassischToString();

        stringBuilder.append(nummer);
        stringBuilder.append(" : ");
        stringBuilder.append(bucket.dyeText(name, Color.CYAN));
        stringBuilder.append(", ");
        stringBuilder.append(kaeseTyp);
        stringBuilder.append(", ");
        stringBuilder.append(diaetString);
        stringBuilder.append(", ");
        stringBuilder.append(isKlassisch);
        stringBuilder.append(" - ");
        stringBuilder.append(bucket.dyeText(this.preis + "EUR ", Color.YELLOW));

        return stringBuilder.toString();
    }
}





















