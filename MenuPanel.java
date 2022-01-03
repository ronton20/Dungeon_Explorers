import javax.swing.border.BevelBorder;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class MenuPanel extends JPanel{

    Font titleFont = new Font("Times New Roman", Font.PLAIN, 45);
    Font normalFont = new Font("Times New Roman", Font.PLAIN, 25);
    
    private ActionListener listener;

    JPanel buttonPanel = new JPanel();

    JLabel lblPaused = new JLabel("Game Paused");
    JButton btnResume = new JButton("Continue");
    JButton btnExitToTitle = new JButton("Exit to Title Screen");

    public MenuPanel(ActionListener lis) {
        this.listener = lis;

        GridLayout layout = new GridLayout(4, 1);
        layout.setVgap(20);
        this.setLayout(layout);

        //----> Setting the menu continue button <----
        setupButton(btnResume);

        //----> Setting the menu exit button <----
        setupButton(btnExitToTitle);

        //----> Game Paused label <----
        lblPaused.setHorizontalAlignment(JLabel.CENTER);
        lblPaused.setVerticalAlignment(JLabel.CENTER);
        lblPaused.setFont(titleFont);
        lblPaused.setForeground(Color.WHITE);

        this.add(lblPaused);
        // this.add(new JLabel());
        this.add(btnResume);
        this.add(btnExitToTitle);
    }

    private void setupButton(JButton btn) {
        btn.setBackground(Color.DARK_GRAY);
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.WHITE, Color.GRAY));
        btn.setFont(normalFont);
        btn.setFocusPainted(false);
        btn.addActionListener(listener);
        btn.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(Color.LIGHT_GRAY);
                btn.setForeground(Color.BLACK);
            }
        
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(Color.DARK_GRAY);
                btn.setForeground(Color.WHITE);
            }
        });
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLACK);
        setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.WHITE, Color.GRAY));

        int btnWidth = getWidth() / 3 * 2;
        int btnHeight = btnWidth / 3;

        btnResume.setBounds(getWidth() / 2 - btnWidth / 2, getHeight() / 2 - btnHeight - 20, btnWidth, btnHeight);
        btnExitToTitle.setBounds(getWidth() / 2 - btnWidth / 2, getHeight() / 2 + 20, btnWidth, btnHeight);
        
    }
}
