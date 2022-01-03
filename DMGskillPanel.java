import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import java.awt.event.*;
import java.awt.*;

public class DMGskillPanel extends JPanel{

    Font normalFont = new Font("Times New Roman", Font.PLAIN, 25);
    Font middleFont = new Font("Times New Roman", Font.PLAIN, 20);
    Font textFont = new Font("Times New Roman", Font.PLAIN, 15);

    JLabel lblLevelUp = new JLabel();

    JLabel lblBaseDMG = new JLabel();
    JLabel lblcurrentDMG = new JLabel();
    JLabel lblAddDMG = new JLabel();

    JButton btnLevelUp = new JButton();

    private Player player;
    private ActionListener listener;
    
    public DMGskillPanel(Player player, ActionListener lis) {
        this.player = player;
        this.listener = lis;

        GridLayout mainLayout = new GridLayout(4, 1);
        mainLayout.setVgap(2);
        this.setLayout(mainLayout);

        //========== Base DMG Label ==========
        lblBaseDMG.setText("Base DMG");
        setupLabel(lblBaseDMG);
        lblBaseDMG.setFont(normalFont);
        lblBaseDMG.setBackground(Color.DARK_GRAY);
        lblBaseDMG.setOpaque(true);
        this.add(lblBaseDMG);

        //========== Stats Panel 1 ==========

        GridLayout subLayout1 = new GridLayout(2, 1);
        JPanel currentDMGpanel = new JPanel();
        currentDMGpanel.setBackground(Color.BLACK);
        currentDMGpanel.setLayout(subLayout1);

        //----> Current Base DMG Label <----
        lblcurrentDMG.setText("" + player.getBaseDMG());
        setupLabel(lblcurrentDMG);
        lblcurrentDMG.setFont(middleFont);
        currentDMGpanel.add(new JLabel());
        currentDMGpanel.add(lblcurrentDMG);
        this.add(currentDMGpanel);


        //========== Stats Panel 2 ==========

        GridLayout subLayout2 = new GridLayout(2, 1);
        JPanel addDMGpanel = new JPanel();
        addDMGpanel.setBackground(Color.BLACK);
        addDMGpanel.setLayout(subLayout2);

        //----> DMG to Add Label <----
        lblAddDMG.setText("( +" + Player.DMG_PER_POINT + " )");
        setupLabel(lblAddDMG);
        lblAddDMG.setForeground(Color.GREEN);
        addDMGpanel.add(lblAddDMG);
        addDMGpanel.add(new JLabel());
        this.add(addDMGpanel);


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

    public void updateCurrentDMG() { lblcurrentDMG.setText("" + player.getBaseDMG()); }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        super.setBackground(Color.BLACK);
        super.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.WHITE, Color.GRAY));
    }
}
