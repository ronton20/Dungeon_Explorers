
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class ExploringPanel extends JPanel implements ActionListener{
    
    Font titleFont = new Font("Helvetica", Font.BOLD, 55);
    Font normalFont = new Font("Helvetica", Font.BOLD, 25);

    JPanel menuPanel = new JPanel();
    JPanel menuBoxPanel = new JPanel();

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
    private boolean canGoUp;
    private boolean canGoDown;
    private boolean canGoLeft;
    private boolean canGoRight;

    int[][] map;
    private final int MAP_SIZE = 5; 
    private final int START_ROOM_X = MAP_SIZE / 2;
    private final int START_ROOM_Y = MAP_SIZE - 1;
    private int currentRoomX;
    private int currentRoomY;

    Player player = new Player(WINDOW_WIDTH / 2 - Player.PLAYER_SIZE / 2, WINDOW_HEIGHT / 2 - Player.PLAYER_SIZE / 2, this);

    public ExploringPanel(int width, int height, ActionListener lis){

        WINDOW_HEIGHT = height;
        WINDOW_WIDTH = width;
        listener = lis;
        bgColor = STARTING_COLOR;
        menuOpen = false;
        currentRoomX = START_ROOM_X;
        currentRoomY = START_ROOM_Y;
        canGoLeft = true;
        canGoRight = true;
        canGoUp = true;
        canGoDown = false;

        generateMap();

        setLayout(null);

        //----> Setting the button for going up <----
        btnUp.setOpaque(false);
        btnUp.setContentAreaFilled(false);
        btnUp.setBorderPainted(false);
        btnUp.addActionListener(this);
        btnUp.setBounds(WINDOW_WIDTH / 2 - BTN_WIDTH / 2, 0, BTN_WIDTH, BTN_HEIGHT);
        add(btnUp);

        //----> Setting the button for going down <----
        btnDown.setOpaque(false);
        btnDown.setContentAreaFilled(false);
        btnDown.setBorderPainted(false);
        btnDown.addActionListener(this);
        btnDown.setBounds(WINDOW_WIDTH / 2 - BTN_WIDTH / 2, WINDOW_HEIGHT - BTN_HEIGHT, BTN_WIDTH, BTN_HEIGHT);
        add(btnDown);

        //----> Setting the button for going left <----
        btnLeft.setOpaque(false);
        btnLeft.setContentAreaFilled(false);
        btnLeft.setBorderPainted(false);
        btnLeft.addActionListener(this);
        btnLeft.setBounds(0, WINDOW_HEIGHT / 2 - BTN_WIDTH / 2, BTN_HEIGHT, BTN_WIDTH);
        add(btnLeft);

        //----> Setting the button for going right <----
        btnRight.setOpaque(false);
        btnRight.setContentAreaFilled(false);
        btnRight.setBorderPainted(false);
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

        //adding all the components to the frame
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
    }

    //opens the main menu
    public void openMenu() {
        menuOpen = true;
        // bgColor = getBackground().darker();
        // this.setForeground(super.getForeground().darker());
        this.setEnabled(false);
        if(player.t.isRunning()) {
            player.t.stop();
        }
        
        menuBoxPanel.setVisible(true);
        
    }

    //closes the main menu
    public void closeMenu() {
        menuOpen = false;
        this.setEnabled(true);
        // bgColor = getBackground().brighter();
        // this.setForeground(super.getForeground().brighter());
        menuBoxPanel.setVisible(false);
        if(player.moving && !player.t.isRunning()) player.t.start();
        
    }

    private void removeButtons() {
        btnUp.setVisible(false);
        btnDown.setVisible(false);
        btnLeft.setVisible(false);
        btnRight.setVisible(false);
    }

    private void addButtons() {
        btnUp.setVisible(canGoUp);
        btnDown.setVisible(canGoDown);
        btnLeft.setVisible(canGoLeft);
        btnRight.setVisible(canGoRight);
    }

    private void generateMap() {
        map = new int[MAP_SIZE][MAP_SIZE];
        for(int i = 0; i < MAP_SIZE; i++) 
            for (int j = 0; j < MAP_SIZE; j++) {
                map[i][j] = 0;
            }
        map[currentRoomY][currentRoomX] = 1;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        graphic = g;
        setBackground(bgColor);
        player.draw(g);
        if(menuOpen) {
            g.setColor(new Color(0, 0, 0, 0.5f));
            g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
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
        
        if(e.getSource().equals(player.t)) {
            if(player.getDirection() == Player.UP) {
                player.move(Player.UP);
                if(!player.movedRoom && player.getY() <= 0) {
                    player.moveRoom(Player.UP);
                    player.movedRoom = true;
                }
                if(player.movedRoom && player.getY() <= WINDOW_HEIGHT / 2 - Player.PLAYER_SIZE / 2) {
                    player.t.stop();
                    player.moving = false;
                    addButtons();
                }
            }
                
            else if(player.getDirection() == Player.DOWN) {
                player.move(Player.DOWN);
                if(!player.movedRoom && player.getY() >= WINDOW_HEIGHT - Player.PLAYER_SIZE) {
                    player.moveRoom(Player.DOWN);
                    player.movedRoom = true;
                }
                if(player.movedRoom && player.getY() >= WINDOW_HEIGHT / 2 - Player.PLAYER_SIZE / 2) {
                    player.t.stop();
                    player.moving = false;
                    addButtons();
                }
            }

            else if(player.getDirection() == Player.LEFT) {
                player.move(Player.LEFT);
                if(!player.movedRoom && player.getX() <= 0) {
                    player.moveRoom(Player.LEFT);
                    player.movedRoom = true;
                }
                if(player.movedRoom && player.getX() <= WINDOW_WIDTH / 2 - Player.PLAYER_SIZE / 2) {
                    player.t.stop();
                    player.moving = false;
                    addButtons();
                }
            }

            else if(player.getDirection() == Player.RIGHT) {
                player.move(Player.RIGHT);
                if(!player.movedRoom && player.getX() >= WINDOW_WIDTH- Player.PLAYER_SIZE) {
                    player.moveRoom(Player.RIGHT);
                    player.movedRoom = true;
                }
                if(player.movedRoom && player.getX() >= WINDOW_WIDTH / 2 - Player.PLAYER_SIZE / 2) {
                    player.t.stop();
                    player.moving = false;
                    addButtons();
                }
            }
            player.draw(graphic);
            repaint();
        }
    }
}
