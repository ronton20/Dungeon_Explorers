import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import java.awt.event.*;
import java.awt.*;

public class ItemPanel extends JPanel{

    public static final String POTION_REFILLABLE = "Refillable Potion";
    public static final String POTION = "Health Potion";
    public static final String LEVEL = "Level";
    public static final String SKILL_POINT = "Skill Point";
    public static final String GOAL_ROOM_MAP = "Goal Room Map";
    public static final String WEAPON = "Weapon";
    public static final String SHIELD = "Shield";
    public static final String HELMET = "Helmet";
    public static final String ARMOUR = "Armour";
    public static final String GLOVES = "Gloves";
    
    Font normalFont = new Font("Times New Roman", Font.PLAIN, 25);
    Font middleFont = new Font("Times New Roman", Font.PLAIN, 20);
    Font textFont = new Font("Times New Roman", Font.PLAIN, 15);

    JPanel descriptionPanel = new JPanel();
    JPanel quantityPanel = new JPanel();

    JLabel lblItem = new JLabel();
    JLabel lblCurrentGold = new JLabel();
    JLabel lblCost = new JLabel();
    JLabel lblCurrentSupply = new JLabel();
    JLabel lblMaxSupply = new JLabel();

    private int currentGold;
    private int cost;
    private int currentSupply;
    private int maxSupply;

    private boolean soldOut;

    private final int POTION_PRICE = 50;
    private final int LEVEL_PRICE = 1000;

    JButton btnBuy = new JButton();

    private Player player;
    private ActionListener listener;

    private String item;
    private int rarity;
    private double statAtt;
    private double statDef;
    private double statCritRate;

    private final double WEAPON_ATT_PER_RARITY = 0.05;
    private final double SHIELD_DEF_PER_RARITY = 0.05;
    private final double HELMET_DEF_PER_RARITY = 0.01;
    private final double HELMET_ATT_PER_RARITY = 0.005;
    private final double ARMOUR_DEF_PER_RARITY = 0.02;
    private final double ARMOUR_ATT_PER_RARITY = 0.005;
    private final double GLOVES_DEF_PER_RARITY = 0.005;
    private final double GLOVES_CRIT_RATE_PER_RARITY = 0.30;

    private final int COST_PER_RARITY = 250;

    public ItemPanel(Player player, ActionListener lis, String item) {

        this.player = player;
        this.listener = lis;
        this.item = item;
        this.rarity = 0;
        this.statAtt = 0;
        this.statDef = 0;
        this.statCritRate = 0;
        this.soldOut = false;

        switch (item) {
            case POTION_REFILLABLE: 
                createRefillablePotion();
                break;

            case POTION: 
                createHealthPotion();
                break;

            case LEVEL: 
                createLevel();
                break;

            case SKILL_POINT: 
                createSkillPoint();
                break;

            case GOAL_ROOM_MAP: 
                createGoalMap();
                break;

            case WEAPON: 
                createWeapon();
                break;

            case SHIELD: 
                createShield();
                break;

            case HELMET: 
                createHelmet();
                break;

            case ARMOUR: 
                createArmour();
                break;

            case GLOVES: 
                createGloves();
                break;

        
            default:
                break;
        }

    }

    public int getPrice() { return cost; }
    public void bought() { currentSupply--; }

    public void restock() {
        if(this.item == POTION_REFILLABLE) {
            currentSupply = maxSupply;
            this.soldOut = true;
        }
    }

    public void upgrade() {
        if(player.getLevel() < 5) return;

        if(player.getLevel() == 5) {
            switch (this.item) {
                case POTION_REFILLABLE:
                    this.maxSupply++;
                    this.currentSupply = this.maxSupply;
                    updateStats();
                    return;

                case WEAPON:
                    revealStats();
                    return;

                case SHIELD:
                    revealStats();
                    return;

                case HELMET:
                    revealStats();
                    return;

                case ARMOUR:
                    revealStats();
                    return;

                case GLOVES:
                    revealStats();
                    return;
            
                default:
                    return;
            }
        }
        
        if(player.getLevel() == 10) {
            switch (this.item) {
                case POTION_REFILLABLE:
                    this.maxSupply++;
                    this.currentSupply = this.maxSupply;
                    updateStats();
                    return;
            
                default:
                    upgradeItem();
                    return;
            }
        }

        if(player.getLevel() == 15) {
            switch (this.item) {
                case POTION_REFILLABLE:
                    this.maxSupply++;
                    this.currentSupply = this.maxSupply;
                    updateStats();
                    return;
            
                default:
                    upgradeItem();
                    return;
            }
        }
    }

    private void upgradeItem() {
        switch (this.item) {
            case WEAPON:
                this.rarity++;
                this.statAtt = WEAPON_ATT_PER_RARITY * this.rarity;
                break;

            case SHIELD:
                this.rarity++;
                this.statDef = SHIELD_DEF_PER_RARITY * this.rarity;
                break;

            case HELMET:
                this.rarity++;
                this.statDef = HELMET_DEF_PER_RARITY * this.rarity;
                this.statAtt = HELMET_ATT_PER_RARITY * this.rarity;
                break;

            case ARMOUR:
                this.rarity++;
                this.statDef = ARMOUR_DEF_PER_RARITY * this.rarity;
                this.statAtt = ARMOUR_ATT_PER_RARITY * this.rarity;
                break;

            case GLOVES:
                this.rarity++;
                this.statCritRate = GLOVES_CRIT_RATE_PER_RARITY * this.rarity;
                this.statDef = GLOVES_DEF_PER_RARITY * this.rarity;
                break;
        
            default:
                return;
        }

        updateStats();
        if(currentSupply > 0) btnBuy.setText("Upgrade");
    }

    private void revealStats() {
        upgradeItem();
        this.maxSupply = 1;
        this.currentSupply = this.maxSupply;
        descriptionPanel.setVisible(true);
        quantityPanel.setVisible(true);
        btnBuy.setVisible(true);
                    
        updateStats();
        if(currentSupply > 0) btnBuy.setText("Upgrade");
    }

    public void updateStats() {
        updateGold();
        updateSupply();
    }

    private void updateGold() {
        currentGold = player.getGold();
        lblCurrentGold.setText("" + this.currentGold);
        if(this.currentGold < this.cost) lblCurrentGold.setForeground(Color.RED);
        else lblCurrentGold.setForeground(Color.GREEN);
    }

    private void updateSupply() {
        lblCurrentSupply.setText("" + this.currentSupply);
        if(this.currentSupply <= 0) {
            lblCurrentSupply.setForeground(Color.RED);
            btnBuy.setText("Sold Out");
            btnBuy.setForeground(Color.RED);
            this.soldOut = true;
        }
        else {
            lblCurrentSupply.setForeground(Color.WHITE);
            btnBuy.setText("Purchase");
            btnBuy.setForeground(Color.WHITE);
            this.soldOut = false;
        }
    }

    private void createRefillablePotion() {

        this.cost = POTION_PRICE;
        this.currentGold = player.getGold();
        this.maxSupply = 1;
        this.currentSupply = maxSupply;

        GridLayout mainLayout = new GridLayout(4, 1);
        mainLayout.setVgap(2);
        this.setLayout(mainLayout);

        //========== Item Label ==========
        lblItem.setText(this.item);
        setupLabel(lblItem);
        lblItem.setFont(middleFont);
        lblItem.setBackground(Color.DARK_GRAY);
        lblItem.setOpaque(true);
        this.add(lblItem);

        //========== Description Panel ==========

        GridLayout subLayout = new GridLayout(2, 1);
        descriptionPanel.setBackground(Color.BLACK);
        descriptionPanel.setLayout(subLayout);

        JLabel lblLine1 = new JLabel("Restore 50% of");
        setupLabel(lblLine1);
        descriptionPanel.add(lblLine1);

        JLabel lblLine2 = new JLabel("Missing Health");
        setupLabel(lblLine2);
        descriptionPanel.add(lblLine2);

        // JPanel displayGold = new JPanel();
        // displayGold.setLayout(new GridLayout(1, 2));
        // displayGold.setBackground(Color.BLACK);

        // //----> Current Gold Label <----
        // lblCurrentGold.setText("" + currentGold);
        // setupLabel(lblCurrentGold);
        // if(currentGold < cost) lblCurrentGold.setForeground(Color.RED);
        // lblCurrentGold.setHorizontalAlignment(JLabel.RIGHT);
        // displayGold.add(lblCurrentGold);

        // //----> Price Label <----
        // lblCost.setText("/" + cost);
        // setupLabel(lblCost);
        // lblCost.setHorizontalAlignment(JLabel.LEFT);
        // displayGold.add(lblCost);

        // descriptionPanel.add(displayGold);
        this.add(descriptionPanel);


        //========== Quantity Panel ==========

        quantityPanel.setBackground(Color.BLACK);
        quantityPanel.setLayout(subLayout);

        JPanel displayQuantity = new JPanel();
        displayQuantity.setLayout(new GridLayout(1, 2));
        displayQuantity.setBackground(Color.BLACK);

        //----> Current Supply Label <----
        lblCurrentSupply.setText("" + currentSupply);
        setupLabel(lblCurrentSupply);
        if(currentSupply <= 0) lblCurrentSupply.setForeground(Color.RED);
        lblCurrentSupply.setHorizontalAlignment(JLabel.RIGHT);
        displayQuantity.add(lblCurrentSupply);

        //----> Max Supply Label <----
        lblMaxSupply.setText("/" + maxSupply);
        setupLabel(lblMaxSupply);
        lblMaxSupply.setHorizontalAlignment(JLabel.LEFT);
        displayQuantity.add(lblMaxSupply);

        quantityPanel.add(new JLabel());
        quantityPanel.add(displayQuantity);
        this.add(quantityPanel);


        //========== Purchase Button ==========
        btnBuy.setText("Purchase");
        setupButton(btnBuy);
        this.add(btnBuy);
    }

    private void createHealthPotion() {

        this.cost = POTION_PRICE;
        this.currentGold = player.getGold();
        this.maxSupply = 20;
        this.currentSupply = maxSupply;

        GridLayout mainLayout = new GridLayout(4, 1);
        mainLayout.setVgap(2);
        this.setLayout(mainLayout);

        //========== Item Label ==========
        lblItem.setText(this.item);
        setupLabel(lblItem);
        lblItem.setFont(middleFont);
        lblItem.setBackground(Color.DARK_GRAY);
        lblItem.setOpaque(true);
        this.add(lblItem);

        //========== Description Panel ==========

        GridLayout subLayout = new GridLayout(2, 1);
        descriptionPanel.setBackground(Color.BLACK);
        descriptionPanel.setLayout(subLayout);

        JLabel lblLine1 = new JLabel("Restore 50% of");
        setupLabel(lblLine1);
        descriptionPanel.add(lblLine1);

        JLabel lblLine2 = new JLabel("Missing Health");
        setupLabel(lblLine2);
        descriptionPanel.add(lblLine2);

        this.add(descriptionPanel);


        //========== Quantity Panel ==========

        quantityPanel.setBackground(Color.BLACK);
        quantityPanel.setLayout(subLayout);

        JPanel displayQuantity = new JPanel();
        displayQuantity.setLayout(new GridLayout(1, 2));
        displayQuantity.setBackground(Color.BLACK);

        //----> Current Supply Label <----
        lblCurrentSupply.setText("" + currentSupply);
        setupLabel(lblCurrentSupply);
        if(currentSupply <= 0) lblCurrentSupply.setForeground(Color.RED);
        lblCurrentSupply.setHorizontalAlignment(JLabel.RIGHT);
        displayQuantity.add(lblCurrentSupply);

        //----> Max Supply Label <----
        lblMaxSupply.setText("/" + maxSupply);
        setupLabel(lblMaxSupply);
        lblMaxSupply.setHorizontalAlignment(JLabel.LEFT);
        displayQuantity.add(lblMaxSupply);

        quantityPanel.add(new JLabel());
        quantityPanel.add(displayQuantity);
        this.add(quantityPanel);


        //========== Purchase Button ==========
        btnBuy.setText("Purchase");
        setupButton(btnBuy);
        this.add(btnBuy);
    }

    private void createLevel() {
        this.cost = LEVEL_PRICE;;
        this.currentGold = player.getGold();
        this.maxSupply = 20;
        this.currentSupply = maxSupply;

        GridLayout mainLayout = new GridLayout(4, 1);
        mainLayout.setVgap(2);
        this.setLayout(mainLayout);

        //========== Item Label ==========
        lblItem.setText(this.item);
        setupLabel(lblItem);
        lblItem.setFont(middleFont);
        lblItem.setBackground(Color.DARK_GRAY);
        lblItem.setOpaque(true);
        this.add(lblItem);

        //========== Description Panel ==========

        GridLayout subLayout = new GridLayout(2, 1);
        descriptionPanel.setBackground(Color.BLACK);
        descriptionPanel.setLayout(subLayout);

        JLabel lblLine1 = new JLabel("Gain a Level");
        setupLabel(lblLine1);
        descriptionPanel.add(lblLine1);

        JLabel lblLine2 = new JLabel("");
        setupLabel(lblLine2);
        descriptionPanel.add(lblLine2);

        this.add(descriptionPanel);


        //========== Quantity Panel ==========

        quantityPanel.setBackground(Color.BLACK);
        quantityPanel.setLayout(subLayout);

        JPanel displayQuantity = new JPanel();
        displayQuantity.setLayout(new GridLayout(1, 2));
        displayQuantity.setBackground(Color.BLACK);

        //----> Current Supply Label <----
        lblCurrentSupply.setText("" + currentSupply);
        setupLabel(lblCurrentSupply);
        if(currentSupply <= 0) lblCurrentSupply.setForeground(Color.RED);
        lblCurrentSupply.setHorizontalAlignment(JLabel.RIGHT);
        displayQuantity.add(lblCurrentSupply);

        //----> Max Supply Label <----
        lblMaxSupply.setText("/" + maxSupply);
        setupLabel(lblMaxSupply);
        lblMaxSupply.setHorizontalAlignment(JLabel.LEFT);
        displayQuantity.add(lblMaxSupply);

        quantityPanel.add(new JLabel());
        quantityPanel.add(displayQuantity);
        this.add(quantityPanel);


        //========== Purchase Button ==========
        btnBuy.setText("Purchase");
        setupButton(btnBuy);
        this.add(btnBuy);
    }

    private void createSkillPoint() {

    }

    private void createGoalMap() {

    }

    private void createWeapon() {

    }

    private void createShield() {

    }

    private void createHelmet() {

    }

    private void createArmour() {

    }

    private void createGloves() {

    }

    private void setupLabel(JLabel label) {
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setForeground(Color.WHITE);
        label.setFont(textFont);
    }

    private void setupButton(JButton button) {
        button.setFont(textFont);
        button.setForeground(Color.WHITE);
        button.setBackground(Color.DARK_GRAY);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEtchedBorder());
        button.addActionListener(this.listener);
        button.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if(!soldOut) {
                    button.setBackground(Color.GRAY);
                    button.setText(cost + " Gold");

                    if(currentGold < cost) button.setForeground(Color.RED);
                    else button.setForeground(Color.GREEN);
                }
            }
        
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.DARK_GRAY);
                if(!soldOut) {
                    if(item == WEAPON || item == SHIELD || item == HELMET || item == ARMOUR || item == GLOVES) button.setText("Upgrade");
                    else button.setText("Purchase");

                    button.setForeground(Color.WHITE);
                }
            }
        });
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        super.setBackground(Color.BLACK);
        super.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.WHITE, Color.GRAY));
    }

}
