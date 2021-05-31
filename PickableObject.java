import java.awt.Point;
import java.util.ArrayList;
import java.util.Locale;

public abstract class PickableObject extends GamesObjects {
    protected boolean is_picked = false;
    protected int id;
    protected static ArrayList<PickableObject> onFloor = new ArrayList<>();
    protected static ArrayList<PickableObject> picked = new ArrayList<>();

    public PickableObject(int id, Point position, int pos_value) {
        super(position, pos_value);
        this.id = id;
        this.position = position;
        onFloor.add(this);
    }

    public static void take(Board board, String class_name) throws InvalidTake{
        int index = -1;
        for (GamesObjects item: game_objects) {
            if(!(item.getClass().getSuperclass().getSimpleName().equals("PickableObject"))){
                if ((item.pos_value == board.getPointOnBoard(board.getCurrent_location())) && (item.getClass().getSimpleName().toLowerCase(Locale.ROOT).equals(class_name)))
                    throw new InvalidTake("You cannot pick up a " + class_name);
                }
            }
        for (PickableObject item: onFloor) {
                if (item.pos_value == board.getPointOnBoard(board.getCurrent_location()) && (item.getClass().getSimpleName().toLowerCase(Locale.ROOT).equals(class_name)))
                    index = onFloor.indexOf(item);
                }
        if(index == -1){
            System.out.println("There is no " + class_name + " to take");
        }else {
            picked.add(onFloor.get(index));
            onFloor.remove(onFloor.get(index));
            picked.get(picked.size() - 1).is_picked = true;
        }
    }
    public static void drop(Board board, String class_name) {
        int i = picked.size() - 1;
        if(picked.isEmpty()) {
            System.out.println("You don't carry anything to drop");
            return;
        }else if(class_name.equals("coin")){
            System.out.println("You cannot drop a coin");
        }else {
            while (i >= 0) {
                if (picked.get(i).getClass().getSimpleName().toLowerCase(Locale.ROOT).equals(class_name)) {
                    onFloor.add(picked.get(i));
                    picked.remove(picked.get(i));
                    onFloor.get(onFloor.size() - 1).is_picked = false;
                    onFloor.get(onFloor.size() - 1).position = board.getCurrent_location();
                    onFloor.get(onFloor.size() - 1).pos_value = board.getPointOnBoard(board.getCurrent_location());
                    break;
                }else {
                    i--;
                }
            }
        }
        if(i == -1){
            System.out.println("You don't carry " + class_name + " to drop");
        }
    }

    public void help() {
        String class_name = this.getClass().getSimpleName().toLowerCase(Locale.ROOT);
        System.out.println(
                "Take <" + class_name + "> : Takes " + class_name + " from the ground\n" +
                "Drop <" + class_name + "> : Drops " + class_name + " to the ground\n");
    }
}
