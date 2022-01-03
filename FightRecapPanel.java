import javax.swing.*;
import javax.swing.border.BevelBorder;

import java.awt.event.*;
import java.awt.*;

public class FightRecapPanel extends JPanel{

    Font normalFont = new Font("Times New Roman", Font.PLAIN, 25);
    Font middleFont = new Font("Times New Roman", Font.PLAIN, 20);
    Font textFont = new Font("Times New Roman", Font.PLAIN, 15);
    
    JButton btnContinue = new JButton("Continue");
    private ActionListener listener;
    private Monster monster;

    public FightRecapPanel(Monster monster, ActionListener lis) {

        this.listener = lis;
        this.monster = monster;

        GridLayout layout = new GridLayout(5, 1);
        layout.setVgap(5);
        this.setLayout(layout);

        //----> Monster defeated title <----
        JLabel lblTitle = new JLabel("You defeated the " + monster.getName() + "!");
        setupLabel(lblTitle);
        lblTitle.setFont(normalFont);
        lblTitle.setBackground(Color.BLACK);
        lblTitle.setOpaque(true);
        lblTitle.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.WHITE, Color.GRAY));
        this.add(lblTitle);

        //----> You get label <----
        JLabel lblYouGet = new JLabel("You get:");
        setupLabel(lblYouGet);
        lblYouGet.setFont(middleFont);
        this.add(lblYouGet);

        //----> Gold <----
        JLabel lblGold = new JLabel("Gold +" + monster.getGold());
        setupLabel(lblGold);
        lblGold.setForeground(Color.YELLOW);
        this.add(lblGold);

        //----> EXP <----
        JLabel lblEXP = new JLabel("EXP +" + monster.getEXP());
        setupLabel(lblEXP);
        lblEXP.setForeground(Color.GREEN);
        this.add(lblEXP);

        //----> Continue Button <----
        setupButton(btnContinue);
        this.add(btnContinue);
    }

    private void setupLabel(JLabel label) {
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setForeground(Color.WHITE);
        label.setFont(textFont);
    }

    private void setupButton(JButton button) {
        button.setFont(normalFont);
        button.setForeground(Color.WHITE);
        button.setBackground(Color.BLACK);
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.WHITE, Color.GRAY));
        button.addActionListener(this.listener);
        button.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.LIGHT_GRAY);
                button.setForeground(Color.BLACK);
            }
        
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.BLACK);
                button.setForeground(Color.WHITE);
            }
        });
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        super.setBackground(Color.BLACK);
        super.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.WHITE, Color.GRAY));
    }
}
