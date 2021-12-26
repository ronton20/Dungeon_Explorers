import java.awt.*;

public abstract class Room {
    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;

    protected boolean up;
    protected boolean down;
    protected boolean left;
    protected boolean right;

    protected Color roomColor;

    public Room(boolean up, boolean down, boolean left, boolean right) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;

        this.roomColor = Color.WHITE;
    }

    public boolean canGoUp() { return up; }
    public boolean canGoDown() { return down; }
    public boolean canGoLeft() { return left; }
    public boolean canGoRight() { return right; }

    public Color getRoomColor() { return roomColor; }

    public abstract void draw(Graphics g, int width, int height);
}
