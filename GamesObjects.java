import java.awt.*;
import java.util.ArrayList;

public abstract class GamesObjects {
    protected  Point position;
    protected  int pos_value;
    protected static ArrayList<GamesObjects> game_objects = new ArrayList<>();

    public GamesObjects(Point position, int pos_value) {
        this.position = position;
        this.pos_value = pos_value;
        game_objects.add(this);
    }

    public abstract void help();

}
