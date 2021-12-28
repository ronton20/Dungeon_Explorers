import java.awt.*;

public class Cell {

    public static final String TOP = "Top";
    public static final String RIGHT = "Right";
    public static final String BOTTOM = "Bottom";
    public static final String LEFT = "Left";
    
    private int x;
    private int y;
    private int cellSize;

    private boolean topWall;
    private boolean rightWall;
    private boolean bottomWall;
    private boolean leftWall;

    private Room room;
    private Color cellColor;
    private Color hiddenColor;

    private boolean visited;    //determines if the cell was visited during generation

    private boolean hidden;
    private boolean playerIsHere;

    private Monster monster;

    public Cell(int x, int y ,int size) {

        this.x = x;
        this.y = y;
        this.cellSize = size;
        topWall = true;
        rightWall = true;
        bottomWall = true;
        leftWall = true;
        room = null;
        hidden = true;
        visited = false;
        monster = null;

        cellColor = Color.WHITE;
        hiddenColor = Color.GRAY;
    }

    public void playerEntered()     { playerIsHere = true; hidden = false; }
    public void playerLeft()        { playerIsHere = false; }
    public Room getRoom()           { return room; }
    public void setRoom(Room room)  { this.room = room; }
    public void setColor(Color c)   { cellColor = c; }
    public void visited()           { visited = true; }
    public boolean isVisited()      { return visited; }
    public int getX()               { return x; }
    public int getY()               { return y; }
    public Color getColor()         { return cellColor; }
    public boolean canGoUp()        { return !topWall; }
    public boolean canGoRight()     { return !rightWall; }
    public boolean canGoDown()      { return !bottomWall; }
    public boolean canGoLeft()      { return !leftWall; }
    public Monster getMonster()     { return monster; }
    
    public void removeWall(String wall) {
        switch (wall) {
            case TOP:
                topWall = false;
                break;
            case RIGHT:
                rightWall = false;
                break;
            case BOTTOM:
                bottomWall = false;
                break;
            case LEFT:
                leftWall = false;
                break;
            default:
                return;
        }
    }
    
    public void createRoom() {
        //making a dead end
        if(!topWall && rightWall && bottomWall && leftWall) { room = new DeadEnd(Room.rotationUp); return;}
        if(topWall && !rightWall && bottomWall && leftWall) { room = new DeadEnd(Room.rotationRight); return;}
        if(topWall && rightWall && !bottomWall && leftWall) { room = new DeadEnd(Room.rotationDown); return;}
        if(topWall && rightWall && bottomWall && !leftWall) { room = new DeadEnd(Room.rotationLeft); return;}

        //making a corridor
        if(!topWall && rightWall && !bottomWall && leftWall) { room = new Corridor(Room.rotationUp); return;}
        if(topWall && !rightWall && bottomWall && !leftWall) { room = new Corridor(Room.rotationRight); return;}

        //making a corner
        if(!topWall && rightWall && bottomWall && !leftWall) { room = new Corner(Room.rotationUp); return;}
        if(!topWall && !rightWall && bottomWall && leftWall) { room = new Corner(Room.rotationRight); return;}
        if(topWall && !rightWall && !bottomWall && leftWall) { room = new Corner(Room.rotationDown); return;}
        if(topWall && rightWall && !bottomWall && !leftWall) { room = new Corner(Room.rotationLeft); return;}

        //making a junktion
        if(!topWall && !rightWall && bottomWall && !leftWall) { room = new Junktion(Room.rotationUp); return;}
        if(!topWall && !rightWall && !bottomWall && leftWall) { room = new Junktion(Room.rotationRight); return;}
        if(topWall && !rightWall && !bottomWall && !leftWall) { room = new Junktion(Room.rotationDown); return;}
        if(!topWall && rightWall && !bottomWall && !leftWall) { room = new Junktion(Room.rotationLeft); return;}

        //making a cross
        if(!topWall && !rightWall && !bottomWall && !leftWall) { room = new Cross(Room.rotationUp); return;}
    }

    public boolean needToFight() {
        if(room.getType() == Room.START_ROOM || room.getType() == Room.GOAL_ROOM || room.getType() == Room.TREASURE_ROOM) return false;
        if(monster.isDead()) return false;
        return true;
    }

    public void generateMonster(int level) {
        String[] monsters = {"Wolf", "Bear", "Wraith", "Ghost", "Zombie", "Skeleton", "Thief"};
        int rand = (int)(Math.random() * monsters.length);
        int monsterLevel = (int)(Math.random() * 4) - 2 + level;
        if(monsterLevel < 1) monsterLevel = 1;
        if(level <= 5) monsterLevel = level;

        switch (monsters[rand]) {
            case "Wolf":
                monster = new Wolf(monsterLevel);
                break;
            case "Bear":
                monster = new Wolf(monsterLevel);
                break;
            case "Wraith":
                monster = new Wraith(monsterLevel);
                break;
            case "Ghost":
                monster = new Ghost(monsterLevel);
                break;
            case "Zombie":
                monster = new Zombie(monsterLevel);
                break;
            case "Skeleton":
                monster = new Skeleton(monsterLevel);
                break;
            case "Thief":
                monster = new Thief(monsterLevel);
                break;
        
            default:
                break;
        }
    }

    public void draw(Graphics g) {
        int x = this.x * cellSize;
        int y = this.y * cellSize;
        if(hidden) {
            g.setColor(hiddenColor);
            g.fillRect(x, y, cellSize, cellSize);
            g.setColor(Color.BLACK);
            g.drawLine(x, y, x + cellSize, y);                          //top left to top right
            g.drawLine(x + cellSize, y, x + cellSize, y + cellSize);    //top right to bottom right
            g.drawLine(x + cellSize, y + cellSize, x, y + cellSize);    //bottom right to bottom left
            g.drawLine(x, y + cellSize, x, y);                          //bottom left to top left
        } else {
            if(playerIsHere)
                g.setColor(Color.BLUE);
            else
                g.setColor(room.getRoomColor());
            
            g.fillRect(x, y, cellSize, cellSize);
            g.setColor(Color.BLACK);
            if(topWall)
                g.drawLine(x, y, x + cellSize, y);                          //top left to top right
            if(rightWall)
                g.drawLine(x + cellSize, y, x + cellSize, y + cellSize);    //top right to bottom right
            if(bottomWall)
                g.drawLine(x + cellSize, y + cellSize, x, y + cellSize);    //bottom right to bottom left
            if(leftWall)
                g.drawLine(x, y + cellSize, x, y);                          //bottom left to top left
        }
    }
}
