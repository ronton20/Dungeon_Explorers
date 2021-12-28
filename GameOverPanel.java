import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import java.awt.event.*;
import java.awt.*;

public class GameOverPanel extends JPanel{

    Font titleFont = new Font("Times New Roman", Font.BOLD, 85);
    Font normalFont = new Font("Times New Roman", Font.PLAIN, 25);
    
    private ActionListener listener;

    JButton btnQuit = new JButton();
    JLabel lblDefeat = new JLabel();

    public GameOverPanel(ActionListener lis) {
        this.listener = lis;

        this.setLayout(null);

        //setting up the label
        lblDefeat.setText("D E F E A T");
        lblDefeat.setHorizontalAlignment(JLabel.CENTER);
        lblDefeat.setVerticalAlignment(JLabel.CENTER);
        lblDefeat.setFont(titleFont);
        lblDefeat.setForeground(Color.RED);
        this.add(lblDefeat);

        //setting up the button
        btnQuit.setText("Exit to Title");
        setupButton(btnQuit);
        this.add(btnQuit);
    }

    private void setupButton(JButton btn) {
        btn.setFont(normalFont);
        btn.setForeground(Color.WHITE);
        btn.setBackground(Color.DARK_GRAY);
        btn.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.WHITE, Color.GRAY));
        btn.setFocusPainted(false);
        btn.addActionListener(this.listener);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        super.setBackground(Color.BLACK);
        super.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.WHITE, Color.GRAY));

        int btnWidth = getWidth() / 3;
        int btnHeight = btnWidth / 3;
        btnQuit.setBounds(getWidth() / 2 - btnWidth / 2, getHeight() - btnHeight - 10, btnWidth, btnHeight);
        lblDefeat.setBounds(0, getHeight() / 2 - getHeight() / 8, getWidth(), getHeight() / 4);
    }
}
