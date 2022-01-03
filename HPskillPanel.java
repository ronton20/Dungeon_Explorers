import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import java.awt.event.*;
import java.awt.*;

public class HPskillPanel extends JPanel{

    Font normalFont = new Font("Times New Roman", Font.PLAIN, 25);
    Font middleFont = new Font("Times New Roman", Font.PLAIN, 20);
    Font textFont = new Font("Times New Roman", Font.PLAIN, 15);

    JLabel lblLevelUp = new JLabel();

    JLabel lblMaxHP = new JLabel();
    JLabel lblcurrentHP = new JLabel();
    JLabel lblAddHP = new JLabel();

    JButton btnLevelUp = new JButton();

    private Player player;
    private ActionListener listener;
    
    public HPskillPanel(Player player, ActionListener lis) {
        this.player = player;
        this.listener = lis;

        GridLayout mainLayout = new GridLayout(4, 1);
        mainLayout.setVgap(2);
        this.setLayout(mainLayout);

        //========== Max HP Label ==========
        lblMaxHP.setText("Max HP");
        setupLabel(lblMaxHP);
        lblMaxHP.setFont(normalFont);
        lblMaxHP.setBackground(Color.DARK_GRAY);
        lblMaxHP.setOpaque(true);
        this.add(lblMaxHP);

        //========== Stats Panel 1 ==========

        GridLayout subLayout1 = new GridLayout(2, 1);
        JPanel currentHPpanel = new JPanel();
        currentHPpanel.setBackground(Color.BLACK);
        currentHPpanel.setLayout(subLayout1);

        //----> Current Max HP Label <----
        lblcurrentHP.setText("" + player.getMaxHP());
        setupLabel(lblcurrentHP);
        lblcurrentHP.setFont(middleFont);
        currentHPpanel.add(new JLabel());
        currentHPpanel.add(lblcurrentHP);
        this.add(currentHPpanel);


        //========== Stats Panel 2 ==========

        GridLayout subLayout2 = new GridLayout(2, 1);
        JPanel addHPpanel = new JPanel();
        addHPpanel.setBackground(Color.BLACK);
        addHPpanel.setLayout(subLayout2);

        //----> HP to Add Label <----
        lblAddHP.setText("( +" + Player.HP_PER_POINT + " )");
        setupLabel(lblAddHP);
        lblAddHP.setForeground(Color.GREEN);
        addHPpanel.add(lblAddHP);
        addHPpanel.add(new JLabel());
        this.add(addHPpanel);


        //========== Level Up Button ==========
        btnLevelUp.setText("Level Up");
        setupButton(btnLevelUp);
        this.add(btnLevelUp);
    }

    private void setupLabel(JLabel label) {
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setForeground(Color.WHITE);
        label.setFont(textFont);
    }

    private void setupButton(JButton button) {
        button.setFont(middleFont);
        button.setForeground(Color.WHITE);
        button.setBackground(Color.DARK_GRAY);
        button.setBorder(BorderFactory.createEtchedBorder());
        button.setFocusPainted(false);
        button.addActionListener(this.listener);
        button.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.LIGHT_GRAY);
                button.setForeground(Color.BLACK);
            }
        
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.DARK_GRAY);
                button.setForeground(Color.WHITE);
            }
        });
    }

    public void updateCurrentHP() { lblcurrentHP.setText("" + player.getMaxHP()); }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        super.setBackground(Color.BLACK);
        super.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.WHITE, Color.GRAY));
    }
}
