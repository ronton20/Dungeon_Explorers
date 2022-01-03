import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public abstract class Room {
    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;

    public static final int rotationUp = 10;
    public static final int rotationRight = 11;
    public static final int rotationDown = 12;
    public static final int rotationLeft = 13;

    public static final String CORRIDOR = "Corridor";
    public static final String CORNER = "Corner";
    public static final String JUNKTION = "Junktion";
    public static final String CROSS = "Cross";
    public static final String DEAD_END = "Dead End";
    public static final String START_ROOM = "Start Room";
    public static final String GOAL_ROOM = "Goal Room";
    public static final String TREASURE_ROOM = "Treasure Room";

    protected Color mainColor;
    protected Color roomColor;

    private String roomType;
    private int rotation;

    private BufferedImage img;
    private Image roomBackgroundImage;

    private final Color ROOM_COLOR = new Color(204, 102, 0);

    public Room(int rotation, String type) {

        this.roomType = type;
        this.rotation = rotation;

        this.roomColor = ROOM_COLOR;
        this.mainColor = ROOM_COLOR;

        generateBackground();
    }

    public Color getRoomColor() { return roomColor; }
    public String getType() { return roomType; }

    public Image getBackgroundImage(int width, int height) { 
        roomBackgroundImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return roomBackgroundImage;
    }

    public void setStartRoom() {
        roomType = Room.START_ROOM;
        roomColor = Color.GREEN;
        mainColor = Color.GREEN;
    }

    public void setGoal() {
        roomType = Room.GOAL_ROOM;
        this.roomColor = Color.CYAN;
        this.mainColor = Color.CYAN;
    }

    public void setTreasure() {
        roomType = Room.TREASURE_ROOM;
        roomColor = Color.YELLOW;
        mainColor = Color.YELLOW;
    }

    private void generateBackground() {
        this.img = null;
        try {
            switch (this.roomType) {
                case CORRIDOR:
                    if(rotation == rotationUp || rotation == rotationDown)      img = ImageIO.read(new File("Assets/Rooms/Corridor_Up.png"));
                    if(rotation == rotationRight || rotation == rotationLeft)   img = ImageIO.read(new File("Assets/Rooms/Corridor_Right.png"));
                    break;

                case JUNKTION:
                    if(rotation == rotationUp)      img = ImageIO.read(new File("Assets/Rooms/Junktion_Up.png"));
                    if(rotation == rotationRight)   img = ImageIO.read(new File("Assets/Rooms/Junktion_Right.png"));
                    if(rotation == rotationDown)    img = ImageIO.read(new File("Assets/Rooms/Junktion_Down.png"));
                    if(rotation == rotationLeft)    img = ImageIO.read(new File("Assets/Rooms/Junktion_Left.png"));
                break;

                case CORNER:
                    if(rotation == rotationUp)      img = ImageIO.read(new File("Assets/Rooms/Corner_Up.png"));
                    if(rotation == rotationRight)   img = ImageIO.read(new File("Assets/Rooms/Corner_Right.png"));
                    if(rotation == rotationDown)    img = ImageIO.read(new File("Assets/Rooms/Corner_Down.png"));
                    if(rotation == rotationLeft)    img = ImageIO.read(new File("Assets/Rooms/Corner_Left.png"));
                    break;

                case DEAD_END:
                    if(rotation == rotationUp)      img = ImageIO.read(new File("Assets/Rooms/Dead_End_Up.png"));
                    if(rotation == rotationRight)   img = ImageIO.read(new File("Assets/Rooms/Dead_End_Right.png"));
                    if(rotation == rotationDown)    img = ImageIO.read(new File("Assets/Rooms/Dead_End_Down.png"));
                    if(rotation == rotationLeft)    img = ImageIO.read(new File("Assets/Rooms/Dead_End_Left.png"));
                break;

                case CROSS:
                        img = ImageIO.read(new File("Assets/Rooms/Cross.png"));
                    break;
            
                default:
                    break;
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public void draw(Graphics g,int width, int height) {
        g.setColor(mainColor);
        g.fillRect(0, 0, width, height);
    }
}
