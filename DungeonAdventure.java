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
    Font normalFont = new Font("Helvetica", Font.BOLD, 25);

    private final int WINDOW_HEIGHT = 720;
    private final int WINDOW_WIDTH = 1080;

    private boolean menuOpen;

    public DungeonAdventure() {

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("Title_Image.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg = img.getScaledInstance(WINDOW_WIDTH, WINDOW_HEIGHT, Image.SCALE_SMOOTH);

        menuBackgroundImage.setIcon(new ImageIcon(dimg));
        menuBackgroundImage.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        menuOpen = false;
        gamePanel = new ExploringPanel(WINDOW_WIDTH, WINDOW_HEIGHT, this);

        mainPanel.setBackground(Color.DARK_GRAY);
        buttonPanel.setBackground(Color.BLACK);

        mainPanel.setLayout(null);

        //----> Setting the title screen label <----
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setSize(WINDOW_WIDTH / 5 * 2, WINDOW_HEIGHT / 6);
        lblTitle.setAlignmentX(JLabel.CENTER_ALIGNMENT);
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
        buttonPanel.add(btnStart);

        //----> Setting the game screen menu button <----
        btnMenu.setBackground(Color.BLACK);
        btnMenu.setForeground(Color.WHITE);
        btnMenu.setBorder(BorderFactory.createEmptyBorder());
        btnMenu.setFont(normalFont);
        btnMenu.setFocusPainted(false);
        btnMenu.addActionListener(this);

        
        window.add(mainPanel, BorderLayout.CENTER);
        window.add(buttonPanel, BorderLayout.PAGE_END);

        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationByPlatform(true);
        window.setResizable(false);
        window.setVisible(true);

    }

    //this method starts the game
    private void startGame() {
        mainPanel.setVisible(false);
        gamePanel.setBackground(Color.CYAN);
        window.add(gamePanel, BorderLayout.CENTER);
        gamePanel.setVisible(true);
        btnStart.setVisible(false);
        btnMenu.setVisible(true);
        buttonPanel.add(btnMenu);
    }

    //this method starts the game
    private void goToTitle() {
        gamePanel.closeMenu();
        gamePanel.setVisible(false);
        btnMenu.setVisible(false);
        btnStart.setVisible(true);
        buttonPanel.remove(btnMenu);
        mainPanel.setVisible(true);
        buttonPanel.add(btnStart);
    }

    public void actionPerformed(ActionEvent e) {
        //starting the game
        if(e.getSource().equals(btnStart)) {
            startGame();
        }
        //opening/closing the main menu
        if(e.getSource().equals(btnMenu)) {
            if(!menuOpen) {
                gamePanel.openMenu();
                btnMenu.setText("Close");
                menuOpen = true;
            }
            else {
                gamePanel.closeMenu();
                btnMenu.setText("Menu");
                menuOpen = false;
            }
        }
        //returning to the game
        if(e.getSource().equals(gamePanel.btnResume)) {
            gamePanel.closeMenu();
            btnMenu.setText("Menu");
            menuOpen = false;
        }
        //returning to the title screen
        if(e.getSource().equals(gamePanel.btnExitToTitle)) {
            btnMenu.setText("Menu");
            menuOpen = false;
            goToTitle();
        }
    }

}