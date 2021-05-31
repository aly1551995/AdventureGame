import java.awt.*;

public class Coin extends PickableObject{


    public Coin(int id, Point position, int pos_value) {
        super(id, position, pos_value);
    }

    @Override
    public void help() {
        System.out.println(
                "Coins are found in boxes collect them to win\n");
    }
}
