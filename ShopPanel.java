import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import java.awt.event.*;
import java.awt.*;

public class ShopPanel extends JPanel implements ActionListener{

    Font normalFont = new Font("Times New Roman", Font.PLAIN, 25);
    Font textFont = new Font("Times New Roman", Font.PLAIN, 15);
    Font countFont = new Font(Font.SANS_SERIF , Font.PLAIN, 15);

    private Player player;
    private ActionListener listener;

    ItemPanel refillPot;
    ItemPanel healthPot;
    ItemPanel level;

    JPanel titlePanel = new JPanel();
    JPanel backPanel = new JPanel();
    JPanel bodyPanel = new JPanel();

    HPskillPanel hpPanel;
    DMGskillPanel dmgPanel;

    JLabel lblShop = new JLabel();
    JLabel lblYourGold = new JLabel();
    JButton btnClose = new JButton();

    Color backgroundColor = new Color(30, 30, 30);

    public ShopPanel(Player player, ActionListener lis) {
        this.player = player;
        this.listener = lis;

        this.setLayout(null);

        //========== SHOP Title ==========
        lblShop.setText("SHOP");
        lblShop.setFont(normalFont);
        lblShop.setForeground(Color.WHITE);
        lblShop.setHorizontalAlignment(JLabel.CENTER);
        lblShop.setVerticalAlignment(JLabel.CENTER);

        //========== Your Gold Label ==========
        lblYourGold.setText("Your Gold: " + player.getGold());
        lblYourGold.setFont(countFont);
        lblYourGold.setForeground(Color.YELLOW);
        lblYourGold.setHorizontalAlignment(JLabel.CENTER);
        lblYourGold.setVerticalAlignment(JLabel.CENTER);

        titlePanel.setBackground(Color.BLACK);
        titlePanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.WHITE, Color.GRAY));
        titlePanel.setLayout(new GridLayout(1, 3));

        titlePanel.add(lblYourGold);
        titlePanel.add(lblShop);
        titlePanel.add(new JLabel());
        this.add(titlePanel);

        //========== Close Button ==========
        btnClose.setText("Close");
        btnClose.setFont(normalFont);
        btnClose.setForeground(Color.WHITE);
        btnClose.setBackground(Color.BLACK);
        btnClose.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.WHITE, Color.GRAY));
        btnClose.setFocusPainted(false);
        btnClose.addActionListener(this.listener);

        this.add(btnClose);

        hpPanel = new HPskillPanel(player, this);
        dmgPanel = new DMGskillPanel(player, this);

        GridLayout bodyLayout = new GridLayout(2, 5);
        bodyLayout.setVgap(50);
        bodyLayout.setHgap(20);

        bodyPanel.setLayout(bodyLayout);
        bodyPanel.setBackground(backgroundColor);

        refillPot = new ItemPanel(this.player, this, ItemPanel.POTION_REFILLABLE);
        healthPot = new ItemPanel(this.player, this, ItemPanel.POTION);
        level = new ItemPanel(this.player, this, ItemPanel.LEVEL);

        bodyPanel.add(refillPot);
        bodyPanel.add(healthPot);
        bodyPanel.add(level);
        bodyPanel.add(new JPanel());
        bodyPanel.add(new JPanel());
        bodyPanel.add(new JPanel());
        bodyPanel.add(new JPanel());
        bodyPanel.add(new JPanel());
        bodyPanel.add(new JPanel());
        bodyPanel.add(new JPanel());

        this.add(bodyPanel);
    }

    public void updateGold() {
        lblYourGold.setText("Your Gold: " + player.getGold());
        refillPot.updateStats();
        
        //need all the items in the shop to be here        
    }

    public void updateGold(ItemPanel item) {
        lblYourGold.setText("Your Gold: " + player.getGold());
        item.updateStats();         
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        super.setBackground(backgroundColor);
        super.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.WHITE, Color.GRAY));

        titlePanel.setBounds(0, 0, getWidth(), getHeight() / 10);
        btnClose.setBounds(0, getHeight() - getHeight() / 10, getWidth(), getHeight() / 10);
        bodyPanel.setBounds(10, getHeight() / 10 + 10, getWidth() - 20, getHeight() / 10 * 8 - 20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(hpPanel.btnLevelUp)) {
            player.levelHP();
            hpPanel.updateCurrentHP();
            lblYourGold.setText("Your Gold: " + player.getGold());
        }
        
        if(e.getSource().equals(dmgPanel.btnLevelUp)) {
            player.levelDMG();
            dmgPanel.updateCurrentDMG();
            lblYourGold.setText("Your Gold: " + player.getGold());
        }

        if(e.getSource().equals(refillPot.btnBuy)) {
            if(player.buyPotion(refillPot.getPrice())) {
                refillPot.bought();
                updateGold(refillPot);
            }
            
        }
    }
}
