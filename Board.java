import java.awt.Point;

public class Board {

    private final int[][] board;
    private Point current_location;

    public Board(int row, int col){
        current_location = new Point(row - 1, col / 2);
        board = new int[row][col];
        int count = 0;
        for (int i = 0; i < row; i++)
        {
            for (int j = 0; j < col; j++)
            {
                board[i][j] = count;
                count++;
            }
        }
    }

    public void reverseColumns()
    {
        int n = board.length - 1 ;
        for (int i = 0; i <= n; i++) {
            int k = board[0].length - 1;
            for (int j = 0; j <= k; j++) {
                int temp = board[j][i];
                board[j][i] = board[k][i];
                board[k][i] = temp;
                k--;
            }
        }
    }

    public void reverseRows()
    {
        int n = board.length - 1 ;
        for (int i = 0; i <= n; i++) {
            int k = board[0].length - 1;
            for (int j = 0; j <= k; j++) {
                int temp = board[i][j];
                board[i][j] = board[i][k];
                board[i][k] = temp;
                k--;
            }
        }
    }

    public void transpose()
    {
        for (int i = 0; i < board.length; i++)
            for (int j = i; j < board[0].length; j++) {
                int temp = board[j][i];
                board[j][i] = board[i][j];
                board[i][j] = temp;
            }
    }

    public void turn_right() {
        transpose();
        reverseColumns();
        int tmp = current_location.y;
        current_location.y = current_location.x;
        current_location.x = board.length - tmp - 1;
    }


    public void turn_left() {
        transpose();
        reverseRows();
        int tmp = current_location.x;
        current_location.x = current_location.y;
        current_location.y = board.length - tmp - 1;
    }

    public String toString() {
        StringBuilder s = new StringBuilder("board =\n");
        for (int[] rows : board) {
            for (int j = 0; j < board[0].length; j++) {
                if (String.valueOf(rows[j]).length() == 1) {
                    s.append(rows[j]).append("   ");
                } else if (String.valueOf(rows[j]).length() == 2) {
                    s.append(rows[j]).append("  ");
                } else {
                    s.append(rows[j]).append(" ");
                }
            }
            s.append("\n");
        }
        return s.toString();
    }

    public void move() throws InvalidMove{
        boolean found = false;
        for (GamesObjects item: GamesObjects.game_objects) {
            if (item instanceof  Door) {
                if (getTop_location().equals(item.position) && ((Door) item).is_locked) {
                    found = true;
                    break;
                }
            }
        }
        if(!found){
            if (current_location.x <= 0 || current_location.x > board.length-1) {
                throw new InvalidMove("No more room to move !");
            }else{
                current_location.x = current_location.x - 1;
            }
        }else{
            System.out.println("Door is locked\n");
        }
    }

    public Point getCurrent_location() {

        return current_location;
    }

    public Point getTop_location() {
        Point top = new Point(current_location.x, current_location.y);
        top.x -= 1;
        if(top.x < 0)
            top.x = board.length-1;
        return top;
    }

    public Point getBottom_location() {
        Point bottom = new Point(current_location.x, current_location.y);
        bottom.x += 1;
        if(bottom.x > board.length - 1)
            bottom.x = 0;
        return bottom;
    }
    public Point getRight_location() {
        Point right = new Point(current_location.x, current_location.y);
        right.y += 1;
        if(right.y > board.length - 1)
            right.y = 0;
        return right;
    }
    public Point getLeft_location() {
        Point left = new Point(current_location.x, current_location.y);
        left.y -= 1;
        if(left.y < 0)
            left.y = board.length-1;
        return left;
    }

    public int getPointOnBoard(Point p) {
        return board[p.x][p.y];
    }

    public void help(){
        System.out.println(
                "Help\n"+
                "Turn ( right | left ) : Turn in the specified direction\n" +
                "Move                  : Move forward\n" +
                "Take <object>         : Pick up object <object>\n" +
                "Drop <object>         : Drop object <object>\n" +
                "Help <object>         : List commands that you can apply to this <object>\n");
    }

    public Point get_coordinates(int value){
        Point p = new Point(0, 0);
        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board[0].length; j++)
            {
                if(board[i][j] == value){
                    p.x = i;
                    p.y = j;
                    return p;
                }
            }
        }
        return p;
    }

    public int surroundingsLogic(Board board) {
        int boxes = 0;
        int coins = 0;
        int keys = 0;
        StringBuilder out = new StringBuilder();
        for (GamesObjects go : GamesObjects.game_objects) {
            if (go instanceof Door) {
                if (go.position.equals(board.getTop_location()))
                    if (out.length() > 0) {
                        out.append(", ").append("and one door in front of you");
                    } else {
                        out.append("There is one door in front of you");
                    }
                if (go.position.equals(board.getBottom_location()))
                    if (out.length() > 0) {
                        out.append(", ").append("and one door behind you");
                    } else {
                        out.append("There is one door behind you");
                    }
                if (go.position.equals(board.getRight_location()))
                    if (out.length() > 0) {
                        out.append(", ").append("and one door to your right");
                    } else {
                        out.append("There is one door to your right");
                    }
                if (go.position.equals(board.getLeft_location()))
                    if (out.length() > 0) {
                        out.append(", ").append("and one door to your left");
                    } else {
                        out.append("There is one door to your left");
                    }
            } else if (go instanceof Box) {
                if (go.position.equals(board.getCurrent_location())) {
                    boxes++;
                }
            } else if (go instanceof Key) {
                if (go.position.equals(board.getCurrent_location()) && !((PickableObject) go).is_picked) {
                    keys++;
                }
            }
        }
        if(out.length() != 0)
            out.append("\n");
        if (boxes == 0 && keys == 0) {
            out.append("There is nothing on the ground");
        } else if (keys == 0) {
            if (boxes == 1)
                out.append("There is one box on the ground");
            if (boxes > 1) {
                out.append("There are ").append(boxes).append(" boxes on the ground");
            }
        } else if (boxes == 0) {
            if (keys == 1)
                out.append("There is a key on the ground");
            if (keys > 1) {
                out.append("There are ").append(keys).append(" keys on the ground");
            }
        } else {
            if (keys == 1) {
                out.append("There is a key on the ground ");
            } else {
                out.append("There are ").append(keys).append(" keys on the ground");
            }
            if (boxes == 1) {
                out.append(", and there is a box on the ground ");
            } else {
                out.append(", and there are ").append(boxes).append(" boxes on the ground");
            }
        }
        out.append("\n");
        keys = 0;
        for (PickableObject p : PickableObject.picked) {
            if (p instanceof Key) {
                keys++;
            } else {
                if (p.id != 0)
                    coins++;
            }
        }
        if (coins == 3) {
            return 1;
        } else {
            if (keys == 0 && coins == 0) {
                out.append("You do not carry anything.");
            } else if (keys == 1 && coins == 0) {
                out.append("You carry a key");
            } else if (keys == 1 && coins == 1) {
                out.append("You carry a key and a coin");
            } else if (keys > 1 && coins == 1) {
                out.append("You carry ").append(keys).append(" keys");
                out.append(", and a coin");
            } else if (keys > 1 && coins > 1) {
                out.append("You carry ").append(keys).append(" keys");
                out.append(", and ").append(coins).append(" coins");
            } else if (keys > 1 && coins == 0) {
                out.append("You carry ").append(keys).append(" keys");
            } else if (keys == 1 && coins > 1) {
                out.append("You carry a key");
                out.append(", and ").append(coins).append(" coins");
            } else if (keys == 0 && coins == 1) {
                out.append("You carry a coin");
            } else if (keys == 0 && coins > 1) {
                out.append("You carry ").append(coins).append(" coins");
            }
            out.append("\n");
            System.out.println(out);
            return 0;
        }
    }
}


