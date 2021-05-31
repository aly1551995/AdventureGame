import java.awt.Point;

public class Box extends LockableObject{

    protected Coin coin;

    public Box(Key key, Coin coin, int id, Point position, int pos_value) {
        super(key, id, position, pos_value);
        this.coin  = coin;
    }

}
