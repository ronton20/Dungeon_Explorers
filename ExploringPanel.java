
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Stack;

public class ExploringPanel extends JPanel implements ActionListener{
    
    Font titleFont = new Font("Helvetica", Font.BOLD, 55);
    Font normalFont = new Font("Helvetica", Font.BOLD, 25);

    JPanel menuPanel = new JPanel();
    JPanel menuBoxPanel = new JPanel();
    FightPanel fightPanel;

    JButton btnResume = new JButton("Continue");
    JButton btnExitToTitle = new JButton("Exit to Title Screen");
    JButton btnUp = new JButton();
    JButton btnDown = new JButton();
    JButton btnLeft = new JButton();
    JButton btnRight = new JButton();

    ActionListener listener;
    Color bgColor;
    Graphics graphic;
    private final Color STARTING_COLOR = Color.GRAY;

    float percentage = 0.5f;

    private int WINDOW_HEIGHT = 720;
    private int WINDOW_WIDTH = 1080;

    private final int BTN_WIDTH = WINDOW_WIDTH / 8;
    private final int BTN_HEIGHT = WINDOW_HEIGHT / 8;

    private boolean menuOpen;

    Cell[][] map;
    int[][] mapIndicator;
    private final int MAP_SIZE = 10; 
    private final int START_ROOM_X = MAP_SIZE / 2;
    private final int START_ROOM_Y = MAP_SIZE - 1;
    private int currentRoomX;
    private int currentRoomY;
    private boolean goalRoomGenerated;
    private boolean fighting;

    private int from;

    private Stack<Cell> stack;

    MiniMap miniMap;
    private final int MINIMAP_SIZE = 200;

    Player player = new Player(WINDOW_WIDTH / 2 - Player.PLAYER_SIZE / 2, WINDOW_HEIGHT / 2 - Player.PLAYER_SIZE / 2, this);




    public ExploringPanel(int width, int height, ActionListener lis){

        WINDOW_HEIGHT = height;
        WINDOW_WIDTH = width;
        listener = lis;
        bgColor = STARTING_COLOR;
        menuOpen = false;
        fighting = false;
        currentRoomX = START_ROOM_X;
        currentRoomY = START_ROOM_Y;

        generateMap();

        map[currentRoomY][currentRoomX].playerEntered();

        for(int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                if(map[i][j] == null) System.out.print("null\t");
                else if(map[i][j].getRoom() == null) System.out.print("0\t");
                else System.out.print(map[i][j].getRoom().getType() + " , ");
            }
            System.out.println();
        }

        miniMap = new MiniMap(map);
        miniMap.setBounds(10, 10, MINIMAP_SIZE, MINIMAP_SIZE);

        setLayout(null);

        //----> Setting the button for going up <----
        btnUp.setOpaque(false);
        btnUp.setContentAreaFilled(false);
        btnUp.setBorderPainted(true);
        btnUp.addActionListener(this);
        btnUp.setBounds(WINDOW_WIDTH / 2 - BTN_WIDTH / 2, 0, BTN_WIDTH, BTN_HEIGHT);
        add(btnUp);

        //----> Setting the button for going down <----
        btnDown.setOpaque(false);
        btnDown.setContentAreaFilled(false);
        btnDown.setBorderPainted(true);
        btnDown.addActionListener(this);
        btnDown.setBounds(WINDOW_WIDTH / 2 - BTN_WIDTH / 2, WINDOW_HEIGHT - BTN_HEIGHT * 2, BTN_WIDTH, BTN_HEIGHT);
        add(btnDown);

        //----> Setting the button for going left <----
        btnLeft.setOpaque(false);
        btnLeft.setContentAreaFilled(false);
        btnLeft.setBorderPainted(true);
        btnLeft.addActionListener(this);
        btnLeft.setBounds(0, WINDOW_HEIGHT / 2 - BTN_WIDTH / 2, BTN_HEIGHT, BTN_WIDTH);
        add(btnLeft);

        //----> Setting the button for going right <----
        btnRight.setOpaque(false);
        btnRight.setContentAreaFilled(false);
        btnRight.setBorderPainted(true);
        btnRight.addActionListener(this);
        btnRight.setBounds(WINDOW_WIDTH - BTN_HEIGHT, WINDOW_HEIGHT / 2 - BTN_WIDTH / 2, BTN_HEIGHT, BTN_WIDTH);
        add(btnRight);

        //----> Setting the menu continue button <----
        btnResume.setBackground(Color.DARK_GRAY);
        btnResume.setForeground(Color.WHITE);
        btnResume.setBounds(WINDOW_WIDTH / 2 - BTN_WIDTH / 2, WINDOW_HEIGHT / 2 - WINDOW_HEIGHT / 8, BTN_WIDTH, BTN_HEIGHT);
        btnResume.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        btnResume.setFont(normalFont);
        btnResume.setFocusPainted(false);
        btnResume.addActionListener(listener);

        //----> Setting the menu exit button <----
        btnExitToTitle.setBackground(Color.DARK_GRAY);
        btnExitToTitle.setForeground(Color.WHITE);
        btnResume.setBounds(WINDOW_WIDTH / 2 - BTN_WIDTH / 2, WINDOW_HEIGHT / 2 + WINDOW_HEIGHT / 8, BTN_WIDTH, BTN_HEIGHT);
        btnExitToTitle.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        btnExitToTitle.setFont(normalFont);
        btnExitToTitle.setFocusPainted(false);
        btnExitToTitle.addActionListener(listener);

        //----> Setting the menu panel <----
        menuBoxPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        menuBoxPanel.setBackground(Color.BLACK);
        menuBoxPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        menuBoxPanel.setPreferredSize(new Dimension(WINDOW_WIDTH / 3, WINDOW_HEIGHT / 8 * 6));
        menuBoxPanel.setLocation(WINDOW_WIDTH / 2 - WINDOW_WIDTH / 6, WINDOW_HEIGHT / 8);
        menuBoxPanel.setBounds(WINDOW_WIDTH / 2 - WINDOW_WIDTH / 6, WINDOW_HEIGHT / 8, WINDOW_WIDTH / 3, WINDOW_HEIGHT / 8 * 6);

        //adding all the components to the panel
        add(miniMap);
        JLabel lblEmpty = new JLabel();
        lblEmpty.setPreferredSize(new Dimension(BTN_WIDTH * 2, BTN_WIDTH * 2));
        add(lblEmpty);
        add(menuBoxPanel);
        menuBoxPanel.setVisible(false);

        GridLayout layout = new GridLayout(2,1);
        layout.setVgap(50);
        menuPanel.setPreferredSize(new Dimension(BTN_WIDTH * 2, BTN_HEIGHT * 2 + 50));
        menuPanel.setBackground(Color.BLACK);
        menuPanel.setLayout(layout);
        menuPanel.add(btnResume);
        menuPanel.add(btnExitToTitle);
        JLabel lblDummy = new JLabel();
        lblDummy.setPreferredSize(new Dimension(BTN_WIDTH * 2, BTN_HEIGHT));

        //----> Game Paused label <----
        JLabel lblPaused = new JLabel("Game Paused");
        lblPaused.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        lblPaused.setAlignmentY(JLabel.CENTER_ALIGNMENT);
        lblPaused.setPreferredSize(new Dimension(BTN_WIDTH * 2, BTN_HEIGHT));
        lblPaused.setFont(normalFont);
        lblPaused.setForeground(Color.WHITE);

        menuBoxPanel.add(lblPaused);
        menuBoxPanel.add(lblDummy);
        menuBoxPanel.add(menuPanel);

        addButtons();
    }

    public boolean isFighting() { return fighting; }

    //opens the main menu
    public void openMenu() {
        if(fighting) return;
        if(player.t.isRunning()) {
            player.t.stop();
        }

        disableBackground();

        menuBoxPanel.setVisible(true);
    }

    //closes the main menu
    public void closeMenu() {
        if(fighting) return;
        menuBoxPanel.setVisible(false);
        if(player.moving && !player.t.isRunning()) player.t.start();
        enableBackground();
        if(player.moving) removeButtons();
    }

    public void disableBackground() {   menuOpen = true; this.setEnabled(false); removeButtons(); miniMap.darken();  }
    public void enableBackground()  {   menuOpen = false; this.setEnabled(true); addButtons(); miniMap.brighten();   }

    //removes directional buttons from the screen
    private void removeButtons() {
        btnUp.setVisible(false);
        btnDown.setVisible(false);
        btnLeft.setVisible(false);
        btnRight.setVisible(false);
    }

    //adds directional buttons to the screen based on current room
    private void addButtons() {
        btnUp.setVisible(map[currentRoomY][currentRoomX].canGoUp());
        btnDown.setVisible(map[currentRoomY][currentRoomX].canGoDown());
        btnLeft.setVisible(map[currentRoomY][currentRoomX].canGoLeft());
        btnRight.setVisible(map[currentRoomY][currentRoomX].canGoRight());
    }

    private void generateMap() {
        map = new Cell[MAP_SIZE][MAP_SIZE];
        mapIndicator = new int[MAP_SIZE][MAP_SIZE];
        stack = new Stack<>();

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                map[i][j] = new Cell(j, i, MINIMAP_SIZE / MAP_SIZE);
            }
        }

        map[START_ROOM_Y][START_ROOM_X].visited();
        map[START_ROOM_Y][START_ROOM_X].setColor(Color.GREEN);

        mapIndicator[START_ROOM_Y][START_ROOM_X] = 1;
        goalRoomGenerated = false;
        
        createMap(map[START_ROOM_Y][START_ROOM_X]);

        ArrayList<Room> deadEndList = new ArrayList<>();

        //generating rooms
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if(map[i][j] != null) {
                    map[i][j].createRoom();
                    if(map[i][j].getRoom().getType() == Room.DEAD_END)
                        deadEndList.add(map[i][j].getRoom());
                }
            }
        }
        //Setting the starting room
        map[START_ROOM_Y][START_ROOM_X].getRoom().setStartRoom();

        //generating the Goal room
        while(!goalRoomGenerated) {
            int rand = (int)(Math.random() * deadEndList.size());
            if(deadEndList.get(rand).getType() != Room.START_ROOM) {
                deadEndList.get(rand).setGoal();
                goalRoomGenerated = true;
            }
        }
    }

    private void createMap(Cell current) {

        Cell next = getNeighbor(current);
        if(next != null) {
            next.visited();
            next.setColor(Color.CYAN);
            stack.add(current);
            removeWalls(current, next);
            createMap(next);
        } else if(!stack.isEmpty()) {
            createMap(stack.pop());
        }
    }

    private Cell getNeighbor(Cell cell) {

        int row = cell.getY();
        int col = cell.getX();

        ArrayList<Cell> neighbors = new ArrayList<>();

        if(row > 0) {
            Cell top = map[row - 1][col];
            if(!top.isVisited()) neighbors.add(top);
        }
        if(col < MAP_SIZE - 1) {
            Cell right = map[row][col + 1];
            if(!right.isVisited()) neighbors.add(right);
        }
        if(row < MAP_SIZE - 1) {
            Cell bottom = map[row + 1][col];
            if(!bottom.isVisited()) neighbors.add(bottom);
        }
        if(col > 0) {
            Cell left = map[row][col - 1];
            if(!left.isVisited()) neighbors.add(left);
        }

        if(neighbors.size() > 0) {
            return neighbors.get((int)(Math.random() * neighbors.size()));
        }

        return null;
    }

    private void removeWalls(Cell a, Cell b) {
        int x = a.getX() - b.getX();

        if(x == 1) {
            a.removeWall(Cell.LEFT);
            b.removeWall(Cell.RIGHT);
        } else if(x == -1) {
            a.removeWall(Cell.RIGHT);
            b.removeWall(Cell.LEFT);
        } else {
            int y = a.getY() - b.getY();

            if(y == 1) {
                a.removeWall(Cell.TOP);
                b.removeWall(Cell.BOTTOM);
            } else if(y == -1) {
                a.removeWall(Cell.BOTTOM);
                b.removeWall(Cell.TOP);
            } else return;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        graphic = g;
        setBackground(map[currentRoomY][currentRoomX].getRoom().getRoomColor());
        player.draw(g);
        if(menuOpen) {
            g.setColor(new Color(0, 0, 0, 0.5f));
            g.fillRect(0, 0, super.getWidth(), super.getHeight());
        }
    }

    private void playerMoved() {
        player.t.stop();
        player.moving = false;
        fighting = true;
        if(map[currentRoomY][currentRoomX].getMonster() == null)
             map[currentRoomY][currentRoomX].generateMonster(player.getLevel());
        if(map[currentRoomY][currentRoomX].needToFight()) {
            fightPanel = new FightPanel(player, map[currentRoomY][currentRoomX].getMonster(), this);
            fightPanel.setBounds(getWidth() / 2 - getWidth() / 4 * 3 / 2, getHeight() / 2 - getHeight() / 4 * 3 / 2, getWidth() / 4 * 3, getHeight() / 4 * 3);
            fightPanel.setVisible(true);
            disableBackground();
            this.add(fightPanel);
        }
        // addButtons();
        if(!map[currentRoomY][currentRoomX].needToFight()) {
            fighting = false;
            enableBackground();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(btnUp)) {
            removeButtons();
            player.go(Player.UP, WINDOW_WIDTH ,WINDOW_HEIGHT, graphic);
        }

        if(e.getSource().equals(btnDown)) {
            removeButtons();
            player.go(Player.DOWN, WINDOW_WIDTH ,WINDOW_HEIGHT, graphic);
        }

        if(e.getSource().equals(btnLeft)) {
            removeButtons();
            player.go(Player.LEFT, WINDOW_WIDTH ,WINDOW_HEIGHT, graphic);
        }

        if(e.getSource().equals(btnRight)) {
            removeButtons();
            player.go(Player.RIGHT, WINDOW_WIDTH ,WINDOW_HEIGHT, graphic);
        }
        
        if(fighting) {
            if(e.getSource().equals(fightPanel.btnRun)) {
                fighting = false;
                this.remove(fightPanel);
                player.go(this.from, WINDOW_WIDTH ,WINDOW_HEIGHT, graphic);
            }

            if(e.getSource().equals(fightPanel.btnAttack)) {
                fighting = false;
                this.remove(fightPanel);
                enableBackground();
            }
        }

        if(e.getSource().equals(player.t)) {
            if(player.getDirection() == Player.UP) {
                player.move(Player.UP);
                if(!player.movedRoom && player.getY() <= 0) {
                    player.moveRoom(Player.UP);
                    player.movedRoom = true;
                    map[currentRoomY][currentRoomX].playerLeft();
                    currentRoomY--;
                    map[currentRoomY][currentRoomX].playerEntered();
                }
                if(player.movedRoom && player.getY() <= WINDOW_HEIGHT / 2 - Player.PLAYER_SIZE / 2) {
                    this.from = Player.DOWN;
                    playerMoved();
                }
            }
                
            else if(player.getDirection() == Player.DOWN) {
                player.move(Player.DOWN);
                if(!player.movedRoom && player.getY() >= WINDOW_HEIGHT - Player.PLAYER_SIZE) {
                    player.moveRoom(Player.DOWN);
                    player.movedRoom = true;
                    map[currentRoomY][currentRoomX].playerLeft();
                    currentRoomY++;
                    map[currentRoomY][currentRoomX].playerEntered();
                }
                if(player.movedRoom && player.getY() >= WINDOW_HEIGHT / 2 - Player.PLAYER_SIZE / 2) {
                    this.from = Player.UP;
                    playerMoved();
                }
            }

            else if(player.getDirection() == Player.LEFT) {
                player.move(Player.LEFT);
                if(!player.movedRoom && player.getX() <= 0) {
                    player.moveRoom(Player.LEFT);
                    player.movedRoom = true;
                    map[currentRoomY][currentRoomX].playerLeft();
                    currentRoomX--;
                    map[currentRoomY][currentRoomX].playerEntered();
                }
                if(player.movedRoom && player.getX() <= WINDOW_WIDTH / 2 - Player.PLAYER_SIZE / 2) {
                    this.from = Player.RIGHT;
                    playerMoved();
                }
            }

            else if(player.getDirection() == Player.RIGHT) {
                player.move(Player.RIGHT);
                if(!player.movedRoom && player.getX() >= WINDOW_WIDTH- Player.PLAYER_SIZE) {
                    player.moveRoom(Player.RIGHT);
                    player.movedRoom = true;
                    map[currentRoomY][currentRoomX].playerLeft();
                    currentRoomX++;
                    map[currentRoomY][currentRoomX].playerEntered();
                }
                if(player.movedRoom && player.getX() >= WINDOW_WIDTH / 2 - Player.PLAYER_SIZE / 2) {
                    this.from = Player.LEFT;
                    playerMoved();
                }
            }
            player.draw(graphic);
            repaint();
        }
    }
}
