import java.awt.*;

public enum KaeseTyp {
    KUHMILCH("Kuhmilch"),
    ZIEGENMILCH("Ziegenmilch"),
    SCHAFSMILCH("Schafsmilch");

    private final String name;

    KaeseTyp(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return App.dyebucket.dyeText(name, Color.YELLOW);
    }
}
