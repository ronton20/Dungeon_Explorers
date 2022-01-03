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
    ItemPanel skillPoint;
    ItemPanel goalMap;
    ItemPanel weapon;
    ItemPanel shield;
    ItemPanel helmet;
    ItemPanel armour;
    ItemPanel gloves;

    JPanel titlePanel = new JPanel();
    JPanel backPanel = new JPanel();
    JPanel bodyPanel = new JPanel();

    JLabel lblShop = new JLabel();
    JLabel lblYourGold = new JLabel();
    JButton btnClose = new JButton();
    JButton btnBuy = new JButton();

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
        btnClose.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnClose.setBackground(Color.LIGHT_GRAY);
                btnClose.setForeground(Color.BLACK);
            }
        
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnClose.setBackground(Color.BLACK);
                btnClose.setForeground(Color.WHITE);
            }
        });

        this.add(btnClose);

        GridLayout bodyLayout = new GridLayout(2, 5);
        bodyLayout.setVgap(50);
        bodyLayout.setHgap(20);

        bodyPanel.setLayout(bodyLayout);
        bodyPanel.setBackground(backgroundColor);

        refillPot = new ItemPanel(this.player, this, ItemPanel.POTION_REFILLABLE);
        healthPot = new ItemPanel(this.player, this, ItemPanel.POTION);
        level = new ItemPanel(this.player, this, ItemPanel.LEVEL);
        skillPoint = new ItemPanel(this.player, this, ItemPanel.SKILL_POINT);
        goalMap = new ItemPanel(this.player, this, ItemPanel.GOAL_ROOM_MAP);
        weapon = new ItemPanel(this.player, this, ItemPanel.WEAPON);
        shield = new ItemPanel(this.player, this, ItemPanel.SHIELD);
        helmet = new ItemPanel(this.player, this, ItemPanel.HELMET);
        armour = new ItemPanel(this.player, this, ItemPanel.ARMOUR);
        gloves = new ItemPanel(this.player, this, ItemPanel.GLOVES);

        bodyPanel.add(refillPot);
        bodyPanel.add(healthPot);
        bodyPanel.add(level);
        bodyPanel.add(skillPoint);
        bodyPanel.add(goalMap);
        bodyPanel.add(weapon);
        bodyPanel.add(shield);
        bodyPanel.add(helmet);
        bodyPanel.add(armour);
        bodyPanel.add(gloves);

        this.add(bodyPanel);
    }

    public void updateGold() {
        lblYourGold.setText("Your Gold: " + player.getGold());
        refillPot.updateStats();
        healthPot.updateStats();
        level.updateStats();
        skillPoint.updateStats();
        goalMap.updateStats();
        weapon.updateStats();
        shield.updateStats();
        helmet.updateStats();
        armour.updateStats();
        gloves.updateStats();
          
    }

    public void restock() {
        refillPot.restock();
        updateGold();
    }

    public void upgrade() {
        refillPot.upgrade();
        weapon.upgrade();
        shield.upgrade();
        helmet.upgrade();
        armour.upgrade();
        gloves.upgrade();

        updateGold();
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
        
        //Checking if the player bought a refillable potion
        if(e.getSource().equals(refillPot.btnBuy)) {
            if(!refillPot.isSoldOut()) {
                if(player.buyPotion(refillPot.getPrice())) {
                    refillPot.bought();
                    updateGold();
                }
            }
        }

        //Checking if the player bought a health potion
        if(e.getSource().equals(healthPot.btnBuy)) {
            if(!healthPot.isSoldOut()) {
                if(player.buyPotion(healthPot.getPrice())) {
                    healthPot.bought();
                    updateGold();
                }
            }
        }

        //Checking if the player bought a level
        if(e.getSource().equals(level.btnBuy)) {
            if(!level.isSoldOut()) {
                if(player.buyLevel(level.getPrice())) {
                    level.bought();
                    refillPot.restock();
                    upgrade();
                    updateGold();
                    listener.actionPerformed(new ActionEvent(btnBuy, ActionEvent.ACTION_PERFORMED, "Update Skills"));
                }
            }
        }

        //Checking if the player bought a skillpoint
        if(e.getSource().equals(skillPoint.btnBuy)) {
            if(!skillPoint.isSoldOut()) {
                if(player.buySkillpoint(skillPoint.getPrice())) {
                    skillPoint.bought();
                    updateGold();
                    listener.actionPerformed(new ActionEvent(btnBuy, ActionEvent.ACTION_PERFORMED, "Update Skills"));
                }
            }
        }

        //Checking if the player bought the Goal Map
        if(e.getSource().equals(goalMap.btnBuy)) {
            if(!goalMap.isSoldOut()) {
                if(player.buyGoalMap(goalMap.getPrice())) {
                    goalMap.bought();
                    updateGold();
                    listener.actionPerformed(new ActionEvent(btnBuy, ActionEvent.ACTION_PERFORMED, "Goal Map Bought"));
                }
            }
        }

        //Checking if the player upgraded the weapon
        if(e.getSource().equals(weapon.btnBuy)) {
            if(!weapon.isSoldOut()) {
                if(player.buyWeapon(weapon.getPrice(), ItemPanel.WEAPON_ATT_PER_RARITY)) {
                    weapon.bought();
                    updateGold();
                }
            }
        }

        //Checking if the player upgraded the shield
        if(e.getSource().equals(shield.btnBuy)) {
            if(!shield.isSoldOut()) {
                if(player.buyShield(shield.getPrice(), ItemPanel.SHIELD_DEF_PER_RARITY)) {
                    shield.bought();
                    updateGold();
                }
            }
        }

        //Checking if the player upgraded the helmet
        if(e.getSource().equals(helmet.btnBuy)) {
            if(!helmet.isSoldOut()) {
                if(player.buyHelmet(helmet.getPrice(), ItemPanel.HELMET_ATT_PER_RARITY, ItemPanel.HELMET_DEF_PER_RARITY)) {
                    helmet.bought();
                    updateGold();
                }
            }
        }

        //Checking if the player upgraded the armour
        if(e.getSource().equals(armour.btnBuy)) {
            if(!armour.isSoldOut()) {
                if(player.buyArmour(armour.getPrice(), ItemPanel.ARMOUR_ATT_PER_RARITY, ItemPanel.ARMOUR_DEF_PER_RARITY)) {
                    armour.bought();
                    updateGold();
                }
            }
        }

        //Checking if the player upgraded the gloves
        if(e.getSource().equals(gloves.btnBuy)) {
            if(!gloves.isSoldOut()) {
                if(player.buyGloves(gloves.getPrice(), ItemPanel.GLOVES_CRIT_RATE_PER_RARITY, ItemPanel.GLOVES_DEF_PER_RARITY)) {
                    gloves.bought();
                    updateGold();
                }
            }
        }
    }
}
