
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ExploringPanel extends JPanel implements ActionListener{
    
    Font titleFont = new Font("Helvetica", Font.BOLD, 55);
    Font normalFont = new Font("Helvetica", Font.BOLD, 25);

    MenuPanel menuBoxPanel;
    FightPanel fightPanel;
    LevelUpPanel lvlUpPanel;
    SkillsPanel skillsTab;
    ShopPanel shopPanel;
    GameOverPanel gameOverPanel;

    JButton btnUp = new JButton();
    JButton btnDown = new JButton();
    JButton btnLeft = new JButton();
    JButton btnRight = new JButton();

    JProgressBar expBar;
    JButton openSkillTab = new JButton();
    JButton openShopTab = new JButton();

    Image imgSkills;
    Image imgSkillsAvailable;
    Image imgShop;

    private final int ICON_SIZE = 50;

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
    private boolean leveledUp;
    private boolean inSkillTab;
    private boolean inShopTab;
    private boolean gameOver;

    private int from;

    // private Stack<Cell> stack;       //if we want a DFS generated map
    private PriorityQueue<Cell> queue;  //if we want a BFS generated map

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
        leveledUp = false;
        inSkillTab = false;
        inShopTab = false;
        this.gameOver = false;
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

        shopPanel = new ShopPanel(player, this);
        this.add(shopPanel);

        this.add(player.character);

        //----> Setting up the EXP Bar <----
        expBar = new JProgressBar(0, player.getMaxEXP());
        expBar.setBackground(Color.DARK_GRAY);
        expBar.setForeground(Color.YELLOW);
        expBar.setValue(player.getCurrentEXP());
        int expBarWidth = WINDOW_WIDTH / 3;
        expBar.setBounds(WINDOW_WIDTH / 2 - expBarWidth / 2, 5, expBarWidth, 20);
        expBar.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.GRAY, Color.DARK_GRAY));
        this.add(expBar);


        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("Skills_Normal.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        imgSkills = img.getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH);

        try {
            img = ImageIO.read(new File("Skills_Available.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        imgSkillsAvailable = img.getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH);

        try {
            img = ImageIO.read(new File("Shop.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        imgShop = img.getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH);

        openSkillTab.setIcon(new ImageIcon(imgSkills));
        openSkillTab.setBounds(WINDOW_WIDTH - ICON_SIZE - 30, 10, ICON_SIZE, ICON_SIZE);
        openSkillTab.addActionListener(this);

        openShopTab.setIcon(new ImageIcon(imgShop));
        openShopTab.setBounds(WINDOW_WIDTH - ICON_SIZE - 30, ICON_SIZE + 20, ICON_SIZE, ICON_SIZE);
        openShopTab.addActionListener(this);

        this.add(openSkillTab);
        this.add(openShopTab);

        //----> Setting the button for going up <----
        setupButtons(btnUp);
        btnUp.setBounds(WINDOW_WIDTH / 2 - BTN_WIDTH / 2, 0, BTN_WIDTH, BTN_HEIGHT);
        add(btnUp);

        //----> Setting the button for going down <----
        setupButtons(btnDown);
        btnDown.setBounds(WINDOW_WIDTH / 2 - BTN_WIDTH / 2, WINDOW_HEIGHT - BTN_HEIGHT * 2, BTN_WIDTH, BTN_HEIGHT);
        add(btnDown);

        //----> Setting the button for going left <----
        setupButtons(btnLeft);
        btnLeft.setBounds(0, WINDOW_HEIGHT / 2 - BTN_WIDTH / 2, BTN_HEIGHT, BTN_WIDTH);
        add(btnLeft);

        //----> Setting the button for going right <----
        setupButtons(btnRight);
        btnRight.setBounds(WINDOW_WIDTH - BTN_HEIGHT, WINDOW_HEIGHT / 2 - BTN_WIDTH / 2, BTN_HEIGHT, BTN_WIDTH);
        add(btnRight);


        //========== Menu Panel ==========
        menuBoxPanel = new MenuPanel(listener);
        menuBoxPanel.setBounds(WINDOW_WIDTH / 2 - WINDOW_WIDTH / 6, WINDOW_HEIGHT / 8, WINDOW_WIDTH / 3, WINDOW_HEIGHT / 8 * 6);

        
        //adding all the components to the panel
        add(miniMap);
        JLabel lblEmpty = new JLabel();
        lblEmpty.setPreferredSize(new Dimension(BTN_WIDTH * 2, BTN_WIDTH * 2));
        add(lblEmpty);
        add(menuBoxPanel);
        menuBoxPanel.setVisible(false);


        addButtons();
    }

    public boolean isFighting() { return fighting; }
    public boolean inSkillsTab() { return inSkillTab; }
    public boolean inShopTab() { return inShopTab; }
    public boolean isGameOver() { return gameOver; }

    //setting up the buttons for moving around
    private void setupButtons(JButton btn) {
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(true);
        btn.addActionListener(this);
    }

    //opens the main menu
    public void openMenu() {
        if(fighting) return;
        if(inSkillTab) return;
        if(player.t.isRunning()) {
            player.t.stop();
        }

        disableBackground();

        menuBoxPanel.setVisible(true);
    }

    //closes the main menu
    public void closeMenu() {
        if(fighting) return;
        if(inSkillTab) return;
        menuBoxPanel.setVisible(false);
        if(player.moving && !player.t.isRunning()) player.t.start();
        enableBackground();
        if(player.moving) removeButtons();
    }

    public void disableBackground() {   menuOpen = true;
                                        this.setEnabled(false);
                                        removeButtons();
                                        miniMap.darken();
                                        expBar.setVisible(false);
                                        openSkillTab.setVisible(false);
                                        openShopTab.setVisible(false);  }
    
    public void enableBackground()  {   menuOpen = false;
                                        this.setEnabled(true);
                                        addButtons();
                                        miniMap.brighten();
                                        expBar.setVisible(true);
                                        openSkillTab.setVisible(true);
                                        openShopTab.setVisible(true);   }

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
        // stack = new Stack<>();       //if we wnat the maze to be generated in a DFS fashion
        queue = new PriorityQueue<>();  //if we wnat the maze to be generated in a BFS fashion

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
            queue.add(current);
            removeWalls(current, next);
            createMap(next);
        } else if(!queue.isEmpty()) {
            createMap(queue.poll());
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

    private void endBattle() {
        int prevLevel = player.getLevel();
        player.gainSpoils(map[currentRoomY][currentRoomX].getMonster());
        shopPanel.updateGold();
        if(player.getLevel() > prevLevel) leveledUp = true;
        else leveledUp = false;

        if(leveledUp) {
            lvlUpPanel = new LevelUpPanel(player, this);
            int width = getWidth() / 5;
            int height = getHeight() / 4;
            lvlUpPanel.setBounds(getWidth() / 2 - width / 2, getHeight() / 2 - height / 2, width, height);
            this.add(lvlUpPanel);
            shopPanel.restock();
            shopPanel.upgrade();
        }
        else enableBackground();

        updateEXPbar();
        updateSkillsIcon();
    }

    private void updateEXPbar() {
        expBar.setMaximum(player.getMaxEXP());
        expBar.setValue(player.getCurrentEXP());
    }

    private void updateSkillsIcon() {
        if(player.getSkillPoints() > 0) openSkillTab.setIcon(new ImageIcon(imgSkillsAvailable));
        else openSkillTab.setIcon(new ImageIcon(imgSkills));
    }

    private void gotoSkills() {
        inSkillTab = true;
        disableBackground();
        int skillTabWidth = getWidth() / 3 * 2;
        int skillTabheight = getHeight() / 3 * 2;
        skillsTab = new SkillsPanel(player, this);
        skillsTab.setBounds(getWidth() / 2 - skillTabWidth / 2, getHeight() / 2 - skillTabheight / 2, skillTabWidth, skillTabheight);
        this.add(skillsTab);
    }

    private void closeSkills() {
        inSkillTab = false;
        skillsTab.setVisible(false);
        this.remove(skillsTab);
        updateSkillsIcon();
        enableBackground();
    }

    private void gotoShop() {
        inShopTab = true;
        disableBackground();
        int shopTabWidth = getWidth() / 5 * 4;
        int shopTabHeight = getHeight() / 5 * 4;
        shopPanel.setBounds(getWidth() / 2 - shopTabWidth / 2, getHeight() / 2 - shopTabHeight / 2, shopTabWidth, shopTabHeight);
        shopPanel.setVisible(true);
    }

    private void closeShop() {
        inShopTab = false;
        shopPanel.setVisible(false);
        updateSkillsIcon();
        enableBackground();
    }

    private void gameOver() {
        gameOver = true;
        disableBackground();
        gameOverPanel = new GameOverPanel(listener);
        int gameOverWidth = getWidth() / 3 * 2;
        int gameOverHeight = getHeight() / 3 * 2;
        gameOverPanel.setBounds(getWidth() / 2 - gameOverWidth / 2, getHeight() / 2 - gameOverHeight / 2, gameOverWidth, gameOverHeight);
        this.add(gameOverPanel);
    }

    private void revealGoalRoom() {
        for(int i = 0; i < MAP_SIZE; i++) {
            for(int j = 0; j < MAP_SIZE; j++) {
                if(map[i][j].getRoom().getType() == Room.GOAL_ROOM) {
                    map[i][j].reveal();
                    miniMap.repaint();
                }
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        graphic = g;
        setBackground(map[currentRoomY][currentRoomX].getRoom().getRoomColor());
        player.draw();
        if(menuOpen) {
            g.setColor(new Color(0, 0, 0, 0.5f));
            g.fillRect(0, 0, super.getWidth(), super.getHeight());
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
        
        if(e.getSource().equals(openSkillTab)) {
            gotoSkills();
        }

        if(e.getSource().equals(openShopTab)) {
            gotoShop();
        }

        if(inShopTab) {
            if(e.getSource().equals(shopPanel.btnClose))
                closeShop();
            if(e.getSource().equals(shopPanel.btnBuy)) {

                if(e.getActionCommand() == "Update Skills")
                    updateSkillsIcon();

                if(e.getActionCommand() == "Goal Map Bought")
                    revealGoalRoom();
            }
        }

        if(fighting) {
            if(e.getSource().equals(fightPanel.btnRun)) {
                fighting = false;
                this.remove(fightPanel);
                player.go(this.from, WINDOW_WIDTH ,WINDOW_HEIGHT, graphic);
            }

            if(e.getSource().equals(fightPanel.btnAttack)) {
                if(e.getActionCommand() == "Monster Defeated") {
                    fighting = false;
                    fightPanel.setVisible(false);
                    this.remove(fightPanel);
                    endBattle();
                }

                if(e.getActionCommand() == "Player Defeated") {
                    fighting = false;
                    fightPanel.setVisible(false);
                    this.remove(fightPanel);
                    gameOver();
                }
            }
        }

        if(leveledUp) {
            if(e.getSource().equals(lvlUpPanel.btnContinue)) {
                leveledUp = false;
                this.remove(lvlUpPanel);
                updateSkillsIcon();
                enableBackground();
            }

            if(e.getSource().equals(lvlUpPanel.btnSkills)) {
                leveledUp = false;
                lvlUpPanel.setVisible(false);
                this.remove(lvlUpPanel);
                updateSkillsIcon();
                gotoSkills();
            }
        }

        if(inSkillTab) {
            if(e.getSource().equals(skillsTab.btnClose)) {
                closeSkills();
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
            player.draw();
            repaint();
        }
    }
}
