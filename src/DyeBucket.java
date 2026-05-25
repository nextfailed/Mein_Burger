import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class DyeBucket{
    public <C> String dyeText(C content, String color){
        return ("\u001B["+getColor(color)+"m" + content + "\u001B[0m");
    }

    public <C> String dyeText(C content, Color color){
        return ("\u001B["+getColor(color)+"m" + content + "\u001B[0m");
    }

    private static final Map<Color, Integer> COLOR_MAP = new HashMap<>();

    static {
        COLOR_MAP.put(Color.BLACK, 30);
        COLOR_MAP.put(Color.RED, 31);
        COLOR_MAP.put(Color.GREEN, 32);
        COLOR_MAP.put(Color.YELLOW, 33);
        COLOR_MAP.put(Color.BLUE, 34);
        COLOR_MAP.put(Color.MAGENTA, 35);
        COLOR_MAP.put(Color.PINK, 35);
        COLOR_MAP.put(Color.CYAN, 36);
    }

    private int getColor(Color color){
        return COLOR_MAP.getOrDefault(color, 37);
    }

    private int getColor(String color){
        return switch(color.toLowerCase()){
            case "black", "b", "none" -> 30;
            case "red", "r" -> 31;
            case "green", "gr" -> 32;
            case "yellow", "yw" -> 33;
            case "blue", "bl" -> 34;
            case "magenta", "mg" -> 35;
            case "cyan", "cy" -> 36;
            default -> 37; //White;
        };

    }
}