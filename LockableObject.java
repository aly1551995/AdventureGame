import java.awt.Point;
import java.util.ArrayList;
import java.util.Locale;

public abstract class LockableObject extends GamesObjects{
    protected boolean is_locked = true;
    protected  Key key;
    protected int id;
    protected static ArrayList<LockableObject> locked = new ArrayList<>();
    protected static ArrayList<LockableObject> unlocked = new ArrayList<>();

    public LockableObject(Key key, int id, Point position, int pos_value) {
        super(position, pos_value);
        this.key = key;
        this.id = id;
        locked.add(this);
    }

    public static void unLock(Board board, String obj_to_unlock, String index){
        ArrayList<Key> keys = new ArrayList<>();
        LockableObject l = null;
        String class_name;
        for (LockableObject item : locked) {
            if (((item.position.equals(board.getTop_location()) || item.position.equals(board.getCurrent_location()))) && obj_to_unlock.equals(item.getClass().getSimpleName().toLowerCase(Locale.ROOT))){
                l = item;
                break;
            }
        }
        if(l == null){
            for (LockableObject item : unlocked) {
                if (((item.position.equals(board.getTop_location()) || item.position.equals(board.getCurrent_location()))) && obj_to_unlock.equals(item.getClass().getSimpleName().toLowerCase(Locale.ROOT))) {
                    l = item;
                    break;
                }
            }
        }
        if(l != null) {
            if(l.getClass().getSimpleName().equals("Door") && !l.position.equals(board.getTop_location())){
                System.out.println("There is no door to unlock");
            }else {
                class_name = l.getClass().getSimpleName().toLowerCase(Locale.ROOT);
                for (PickableObject item : PickableObject.picked) {
                    if (item instanceof Key) {
                        keys.add((Key) item);
                    }
                }
                if (keys.isEmpty()) {
                    System.out.println("No keys to open the " + class_name);
                } else if (Character.isDigit(index.charAt(0))) {
                    int chosen_key_index = Integer.parseInt(String.valueOf(index.charAt(0))) - 1;
                    if (keys.size() > chosen_key_index) {
                        if (keys.get(chosen_key_index).id == l.key.id && l.is_locked) {
                            l.is_locked = false;
                            unlocked.add(l);
                            locked.remove(l);
                            System.out.println(class_name + " is unlocked");
                            if (l instanceof Box) {
                                ((Box) l).coin.is_picked = true;
                                PickableObject.picked.add(((Box) l).coin);
                                if (((Box) l).coin.id == 0) {
                                    System.out.println("Box is empty !");
                                }
                            }
                        } else if (keys.get(chosen_key_index).id == l.key.id) {
                            System.out.println(class_name + " is already unlocked. Did you mean Lock?");
                        } else {
                            System.out.println("Wrong key.");
                        }
                    } else {
                        System.out.println("You don't have key " + (chosen_key_index + 1) + "");
                    }
                } else {
                    System.out.println("Wrong command");
                }
            }
        }else {
            System.out.println("There is no " + obj_to_unlock + " to unlock");
        }
    }
    public static void lock(Board board, String obj_to_lock, String index) {
        ArrayList<Key> keys = new ArrayList<>();
        LockableObject l = null;
        String class_name;
        boolean status;
        for (LockableObject item : locked) {
            if ((item.position.equals(board.getTop_location()) || item.position.equals(board.getCurrent_location())) && obj_to_lock.equals(item.getClass().getSimpleName().toLowerCase(Locale.ROOT))){
                l = item;
                break;
            }
        }
        if (l == null) {
            for (LockableObject item : unlocked) {
                if ((item.position.equals(board.getTop_location()) || item.position.equals(board.getCurrent_location())) && obj_to_lock.equals(item.getClass().getSimpleName().toLowerCase(Locale.ROOT))){
                    l = item;
                    break;
                }
            }
        }
        if (l != null) {
            if(l.getClass().getSimpleName().equals("Door") && !l.position.equals(board.getTop_location())){
                System.out.println("There is no door to lock");
            }else {
                class_name = l.getClass().getSimpleName().toLowerCase(Locale.ROOT);
                status = l.is_locked;
                for (PickableObject item : PickableObject.picked) {
                    if (item instanceof Key) {
                        keys.add((Key) item);
                    }
                }
                if (keys.isEmpty()) {
                    System.out.println("No keys to lock the " + class_name);
                } else if (Character.isDigit(index.charAt(0))) {
                    int chosen_key_index = Integer.parseInt(String.valueOf(index.charAt(0))) - 1;
                    if (keys.size() > chosen_key_index) {
                        if (keys.get(chosen_key_index).id == l.key.id && !status) {
                            l.is_locked = true;
                            unlocked.remove(l);
                            locked.add(l);
                            System.out.println(class_name + " is locked");
                            if (l instanceof Box) {
                                ((Box) l).coin = new Coin(0, board.get_coordinates(-1), -1);
                            }
                        } else if (keys.get(chosen_key_index).id == l.key.id) {
                            System.out.println(class_name + " is already locked. Did you mean unLock?");
                        } else {
                            System.out.println("Wrong key.");
                        }
                    } else {
                        System.out.println("You don't have key " + (chosen_key_index + 1));
                    }
                } else {
                    System.out.println("Wrong command");
                }
            }
        } else {
            System.out.println("There is no " + obj_to_lock + " to lock");
        }
    }
    public void help() {
        String class_name = this.getClass().getSimpleName().toLowerCase(Locale.ROOT);
        System.out.println("" +
                "Unlock <" + class_name + "> with <key> : Unlock " + class_name + "\n" +
                "Lock <" + class_name + "> with <key> : Lock " + class_name + "\n");
    }
}
