import java.awt.BorderLayout;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class DungeonAdventure implements ActionListener {

    public static void main(String[] args) {
        new DungeonAdventure();    
    }

    private final String TITLE = "Dungeon Explorers";
    JFrame window = new JFrame(TITLE);
    JPanel mainPanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    ExploringPanel gamePanel;
    JLabel lblTitle = new JLabel(TITLE);
    JButton btnStart = new JButton("Begin Your Adventure!");
    JButton btnMenu = new JButton("Menu");

    JPanel menuTitlePanel = new JPanel();
    JLabel menuBackgroundImage = new JLabel();

    Font titleFont = new Font("Times New Roman", Font.PLAIN, 55);
    Font normalFont = new Font("Times New Roman", Font.BOLD, 25);

    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Dimension screen = toolkit.getScreenSize();

    private final int WINDOW_HEIGHT = (int)screen.getHeight() / 5 * 4;
    private final int WINDOW_WIDTH = (int)screen.getWidth() / 4 * 3;

    private boolean menuOpen;

    TimerPanel timerPanel = new TimerPanel();

    JLabel lblDummy = new JLabel();




    public DungeonAdventure() {

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("Assets/Title_Image.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg = img.getScaledInstance(WINDOW_WIDTH, WINDOW_HEIGHT, Image.SCALE_SMOOTH);

        menuBackgroundImage.setIcon(new ImageIcon(dimg));
        menuBackgroundImage.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        menuOpen = false;
        gamePanel = null;

        mainPanel.setBackground(Color.BLACK);
        mainPanel.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT / 10 * 9);
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setLayout(new GridLayout(1, 3));
        buttonPanel.setBounds(0, WINDOW_HEIGHT - WINDOW_HEIGHT / 10 - 5, WINDOW_WIDTH, WINDOW_HEIGHT / 15);

        mainPanel.setLayout(null);

        //----> Setting the title screen label <----
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setSize(WINDOW_WIDTH / 5 * 2, WINDOW_HEIGHT / 6);
        lblTitle.setHorizontalAlignment(JLabel.CENTER);
        lblTitle.setFont(titleFont);
        menuTitlePanel.setOpaque(false);
        menuTitlePanel.setBounds(WINDOW_WIDTH / 2 - lblTitle.getWidth() / 2, 50, lblTitle.getWidth(), lblTitle.getHeight());
        menuTitlePanel.setLayout(new BorderLayout());
        menuTitlePanel.add(lblTitle);
        mainPanel.add(menuBackgroundImage, BorderLayout.CENTER);
        mainPanel.add(menuTitlePanel);
        mainPanel.setComponentZOrder(menuTitlePanel, 0);

        //----> Setting the title screen button <----

        btnStart.setBackground(Color.BLACK);
        btnStart.setForeground(Color.WHITE);
        btnStart.setBorder(BorderFactory.createEmptyBorder());
        btnStart.setFont(normalFont);
        btnStart.addActionListener(this);
        btnStart.setFocusPainted(false);
        buttonPanel.add(timerPanel);
        buttonPanel.add(btnStart);
        buttonPanel.add(lblDummy);

        //----> Setting the game screen menu button <----
        btnMenu.setBackground(Color.BLACK);
        btnMenu.setForeground(Color.WHITE);
        btnMenu.setBorder(BorderFactory.createEmptyBorder());
        btnMenu.setFont(normalFont);
        btnMenu.setFocusPainted(false);
        btnMenu.addActionListener(this);

        

        window.setLayout(null);
        window.setBackground(Color.BLACK);
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        window.setVisible(true);

        window.add(mainPanel, BorderLayout.CENTER);
        window.add(buttonPanel, BorderLayout.PAGE_END);
    }

    //this method starts the game
    private void startGame() {
        mainPanel.setVisible(false);
        if(gamePanel != null) { window.remove(gamePanel); }
        gamePanel = new ExploringPanel(WINDOW_WIDTH, WINDOW_HEIGHT / 10 * 9, this);
        gamePanel.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT / 10 * 9);
        gamePanel.setBackground(Color.BLACK);
        window.add(gamePanel);
        gamePanel.setVisible(true);
        btnStart.setVisible(false);
        buttonPanel.remove(btnStart);
        buttonPanel.remove(lblDummy);
        btnMenu.setVisible(true);
        buttonPanel.add(btnMenu);
        buttonPanel.add(lblDummy);

        // timerPanel.showTimer();
        // timerPanel.startTimer();
    }

    //this method starts the game
    private void goToTitle() {
        gamePanel.closeMenu();
        gamePanel.setVisible(false);
        window.remove(gamePanel);
        btnMenu.setVisible(false);
        btnStart.setVisible(true);
        buttonPanel.remove(btnMenu);
        buttonPanel.remove(lblDummy);
        mainPanel.setVisible(true);
        buttonPanel.add(btnStart);
        buttonPanel.add(lblDummy);

        // timerPanel.stopTimer();
        // timerPanel.hideTimer();
        // timerPanel.resetTimer();
    }

    public void actionPerformed(ActionEvent e) {
        //starting the game
        if(e.getSource().equals(btnStart)) {
            startGame();
        }
        //opening/closing the main menu
        if(e.getSource().equals(btnMenu)) {
            if(gamePanel.isFighting()) return;
            if(gamePanel.inSkillsTab()) return;
            if(gamePanel.inShopTab()) return;
            if(gamePanel.isGameOver()) return;
            if(!menuOpen) {
                // timerPanel.stopTimer();
                gamePanel.openMenu();
                btnMenu.setText("Close");
                menuOpen = true;
            }
            else {
                // timerPanel.startTimer();
                gamePanel.closeMenu();
                btnMenu.setText("Menu");
                menuOpen = false;
            }
        }
        //returning to the game
        if(e.getSource().equals(gamePanel.menuBoxPanel.btnResume)) {
            // timerPanel.startTimer();
            gamePanel.closeMenu();
            btnMenu.setText("Menu");
            menuOpen = false;
        }
        //returning to the title screen
        if(e.getSource().equals(gamePanel.menuBoxPanel.btnExitToTitle)) {
            btnMenu.setText("Menu");
            menuOpen = false;
            goToTitle();
        }

        if(gamePanel.isGameOver()) {
            if(e.getSource().equals(gamePanel.gameOverPanel.btnQuit)) {
                goToTitle();
            }
            if(e.getSource().equals(gamePanel.victoryPanel.btnQuit)) {
                goToTitle();
            }
        }
    }

}