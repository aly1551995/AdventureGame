import java.util.Locale;
import java.util.Scanner;

public class GameEngine {

    public void initializeGame() {
        Board board = new Board(5, 5);
        System.out.println(board.toString());
        Key key = new Key(1, board.get_coordinates(22), 22);
        Key key_2 = new Key(2, board.get_coordinates(17), 17);
        Key key_3 = new Key(3, board.get_coordinates(17), 17);
        Key key_4 = new Key(4, board.get_coordinates(18), 18);
        Key key_5 = new Key(5, board.get_coordinates(18), 18);
        Key key_6 = new Key(6, board.get_coordinates(13), 13);

        Door door = new Door(key, 1, board.get_coordinates(17), 17);
        Door door_2 = new Door(key_3, 2, board.get_coordinates(18), 18);
        Door door_3 = new Door(key_5, 3, board.get_coordinates(13), 13);

        Coin coin = new Coin(1, board.get_coordinates(17), 17);
        Coin coin_2 = new Coin(2, board.get_coordinates(18), 18);
        Coin coin_3 = new Coin(3, board.get_coordinates(8), 8);

        Box box = new Box(key_2, coin, 1, board.get_coordinates(17), 17);
        Box box_2 = new Box(key_4, coin_2, 2, board.get_coordinates(18), 18);
        Box box_3 = new Box(key_6, coin_3, 3, board.get_coordinates(8), 8);


        System.out.println("**********************************************************");
        System.out.println("*                                                        *");
        System.out.println("*                                                        *");
        System.out.println("*                                                        *");
        System.out.println("*                                                        *");
        System.out.println("*                  WELCOME TO THE GAME                   *");
        System.out.println("*                                                        *");
        System.out.println("*                                                        *");
        System.out.println("*                                                        *");
        System.out.println("*                                                        *");
        System.out.println("**********************************************************");
        System.out.println("\n");
        boolean exit = false;
        while (board.surroundingsLogic(board) == 0) {
            Scanner sc = new Scanner(System.in);
            String command;
            command = sc.nextLine().toLowerCase(Locale.ROOT).trim();
            String[] cmd = command.split(" ");
            if (command.equals("move")) {
                try {
                    board.move();
                }
                catch (InvalidMove e){
                    System.out.println(e.getMessage());
                }
            }
            if (command.equals("turn right")){
                board.turn_right();
            }
            if (command.equals("turn left")){
                board.turn_left();
            }
            if (cmd[0].equals("take")) {
                if (cmd.length < 2) {
                    System.out.println("incomplete command\n");
                }else if(cmd.length == 2) {
                    try {
                        PickableObject.take(board, cmd[1]);
                    }
                    catch (InvalidTake e){
                        System.out.println(e.getMessage());
                    }
                }else{
                    System.out.println("Invalid Command");
                }
            }
            if (cmd[0].equals("drop")) {
                if (cmd.length < 2) {
                    System.out.println("incomplete command\n");
                }else if(cmd.length == 2) {
                    PickableObject.drop(board, cmd[1]);
                }else{
                    System.out.println("Invalid Command");
                }
            }
            if (cmd[0].equals("unlock")) {
                if (cmd.length == 4 && cmd[3].equals("key")) {
                    LockableObject.unLock(board, cmd[1].toLowerCase(Locale.ROOT), "1");
                } else if (cmd.length == 5) {
                    LockableObject.unLock(board, cmd[1].toLowerCase(Locale.ROOT), cmd[4]);
                } else {
                    System.out.println("Invalid Command\n");
                }
            }
            if (cmd[0].equals("lock")) {
                if (cmd.length == 4 && cmd[3].equals("key")) {
                    LockableObject.lock(board, cmd[1].toLowerCase(Locale.ROOT), "1");
                } else if (cmd.length == 5) {
                    LockableObject.lock(board, cmd[1].toLowerCase(Locale.ROOT), cmd[4]);
                } else {
                    System.out.println("Invalid Command\n");
                }
            }
            if (cmd[0].equals("help"))
                if (cmd.length == 1){
                    board.help();
                }else{
                    switch (cmd[1].toLowerCase(Locale.ROOT)){
                        case "door": door.help();
                        break;
                        case "key": key.help();
                        break;
                        case "coin": coin.help();
                        break;
                        case "box" : box.help();
                        break;
                        default: System.out.println("unknown object\n");
                    }
                }
            if (cmd[0].equals("exit")) {
                exit = true;
                break;
            }
            for (GamesObjects item : GamesObjects.game_objects) {
                item.position = board.get_coordinates(item.pos_value);
            }
        }
        if (!exit)
            System.out.println("Congratulations you won !");
    }
}
