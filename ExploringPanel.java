
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class ExploringPanel extends JPanel{
    
    Font titleFont = new Font("Helvetica", Font.BOLD, 55);
    Font normalFont = new Font("Helvetica", Font.BOLD, 25);

    JPanel menuPanel = new JPanel();
    JPanel menuBoxPanel = new JPanel();

    JButton btnResume = new JButton("Continue");
    JButton btnExitToTitle = new JButton("Exit to Title Screen");

    ActionListener listener;
    Color bgColor;
    private final Color STARTING_COLOR = Color.GRAY;

    float percentage = 0.5f;

    private int WINDOW_HEIGHT = 720;
    private int WINDOW_WIDTH = 1080;

    private final int BTN_WIDTH = WINDOW_WIDTH / 8;
    private final int BTN_HEIGHT = WINDOW_HEIGHT / 8;

    public ExploringPanel(int width, int height, ActionListener lis){

        WINDOW_HEIGHT = height;
        WINDOW_WIDTH = width;
        listener = lis;
        bgColor = STARTING_COLOR;

        setLayout(null);

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
        bgColor = getBackground().darker();
        // this.setForeground(super.getForeground().darker());
        this.setEnabled(false);
        
        menuBoxPanel.setVisible(true);
        
    }

    //closes the main menu
    public void closeMenu() {
        this.setEnabled(true);
        bgColor = getBackground().brighter();
        // this.setForeground(super.getForeground().brighter());
        menuBoxPanel.setVisible(false);
        
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(bgColor);
    }
}
