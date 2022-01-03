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
    JPanel statPanel1 = new JPanel();
    JPanel statPanel2 = new JPanel();

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

    private final int POTION_PRICE = 30;
    private final int LEVEL_PRICE = 500;
    private final int SKILLPOINT_PRICE = 200;
    private final int GOAL_MAP_PRICE = 1000;
    private final int UPGRADE_PRICE = 250;

    JButton btnBuy = new JButton();

    private Player player;
    private ActionListener listener;

    private String item;
    private int rarity;
    private double statAtt;
    private double statDef;
    private double statCritRate;

    public static final double WEAPON_ATT_PER_RARITY = 0.10;
    public static final double SHIELD_DEF_PER_RARITY = 0.10;
    public static final double HELMET_DEF_PER_RARITY = 0.03;
    public static final double HELMET_ATT_PER_RARITY = 0.03;
    public static final double ARMOUR_DEF_PER_RARITY = 0.05;
    public static final double ARMOUR_ATT_PER_RARITY = 0.05;
    public static final double GLOVES_DEF_PER_RARITY = 0.02;
    public static final double GLOVES_CRIT_RATE_PER_RARITY = 0.30;

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
    public void bought() { currentSupply--; updateStats(); }
    public boolean isSoldOut() { return soldOut; }

    public void restock() {
        if(this.item == POTION_REFILLABLE) {
            currentSupply = maxSupply;
            this.soldOut = false;
        }
    }

    public void upgrade() {
        if(player.getLevel() != 5 && player.getLevel() != 10 && player.getLevel() != 15) return;

        if(player.getLevel() == 5) {
            switch (this.item) {
                case POTION_REFILLABLE:
                    this.maxSupply++;
                    this.currentSupply = this.maxSupply;
                    this.lblMaxSupply.setText("/" + this.maxSupply);
                    updateStats();
                    return;

                case WEAPON:
                    this.maxSupply++;
                    this.currentSupply++;
                    revealStats();
                    return;

                case SHIELD:
                    this.maxSupply++;
                    this.currentSupply++;
                    revealStats();
                    return;

                case HELMET:
                    this.maxSupply++;
                    this.currentSupply++;
                    revealStats();
                    return;

                case ARMOUR:
                    this.maxSupply++;
                    this.currentSupply++;
                    revealStats();
                    return;

                case GLOVES:
                    this.maxSupply++;
                    this.currentSupply++;
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
                    this.lblMaxSupply.setText("/" + this.maxSupply);
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
                    this.lblMaxSupply.setText("/" + this.maxSupply);
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
        this.maxSupply++;
        this.currentSupply++;
        updateStats();
    }

    private void revealStats() {
        upgradeItem();
        this.maxSupply = 1;
        this.currentSupply = this.maxSupply;
        statPanel1.setVisible(true);
        statPanel2.setVisible(true);
        btnBuy.setVisible(true);
                    
        updateStats();
        if(currentSupply > 0) btnBuy.setText("Upgrade");
    }

    public void updateStats() {
        updateGold();
        updateSupply();
        if(item == WEAPON) { updateWeapon(); }
        if(item == SHIELD) { updateShield(); }
        if(item == HELMET) { updateHelmet(); }
        if(item == ARMOUR) { updateArmour(); }
        if(item == GLOVES) { updateGloves(); }

    }

    private void updateGold() {
        this.currentGold = player.getGold();
        lblCurrentGold.setText("" + this.currentGold);
        if(item != WEAPON && item != SHIELD && item != HELMET && item != ARMOUR && item != GLOVES) {
            if(this.currentGold < this.cost) lblCurrentGold.setForeground(Color.RED);
            else lblCurrentGold.setForeground(Color.GREEN);
        }
    }

    private void updateSupply() {
        lblCurrentSupply.setText("" + this.currentSupply);
        if(this.currentSupply <= 0) {
            if(item != WEAPON && item != SHIELD && item != HELMET && item != ARMOUR && item != GLOVES)
                lblCurrentSupply.setForeground(Color.RED);
            btnBuy.setText("Sold Out");
            btnBuy.setForeground(Color.RED);
            this.soldOut = true;
        }
        else {
            lblCurrentSupply.setForeground(Color.WHITE);
            btnBuy.setText("Purchase");
            if(item == WEAPON || item == SHIELD || item == HELMET || item == ARMOUR || item == GLOVES)
                btnBuy.setText("Upgrade");
            btnBuy.setForeground(Color.WHITE);
            this.soldOut = false;
        }
    }

    private void updateWeapon() {
        lblCurrentGold.setText("" + player.getDMG());
        int attIncrease = (int)(player.getBaseDMG() * WEAPON_ATT_PER_RARITY);
        lblCost.setText(" +" + attIncrease);
    }

    private void updateShield() {
        lblCurrentGold.setText((int)(player.getDefence() * 100) + "%");
        int defIncrease = (int)(SHIELD_DEF_PER_RARITY * 100);
        lblCost.setText(" +" + defIncrease + "%");
    }

    private void updateHelmet() {
        lblCurrentGold.setText("" + player.getDMG());
        int attIncrease = (int)(player.getBaseDMG() * HELMET_ATT_PER_RARITY);
        lblCost.setText(" +" + attIncrease);

        lblCurrentSupply.setText((int)(player.getDefence() * 100) + "%");
        int defIncrease = (int)(HELMET_DEF_PER_RARITY * 100);
        lblMaxSupply.setText(" +" + defIncrease + "%");
    }

    private void updateArmour() {
        lblCurrentGold.setText("" + player.getDMG());
        int attIncrease = (int)(player.getBaseDMG() * ARMOUR_ATT_PER_RARITY);
        lblCost.setText(" +" + attIncrease);

        lblCurrentSupply.setText((int)(player.getDefence() * 100) + "%");
        int defIncrease = (int)(ARMOUR_DEF_PER_RARITY * 100);
        lblMaxSupply.setText(" +" + defIncrease + "%");
    }

    private void updateGloves() {
        lblCurrentGold.setText((int)(player.getCritRate() * 100) + "%");
        int critIncrease = (int)(GLOVES_CRIT_RATE_PER_RARITY * 100);
        lblCost.setText(" +" + critIncrease + "%");

        lblCurrentSupply.setText((int)(player.getDefence() * 100) + "%");
        int defIncrease = (int)(GLOVES_DEF_PER_RARITY * 100);
        lblMaxSupply.setText(" +" + defIncrease + "%");
    }

    //=============== Creating the Panel Based on Item Chosen ===============
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
        this.currentSupply = this.maxSupply;

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

        this.cost = LEVEL_PRICE;
        this.currentGold = player.getGold();
        this.maxSupply = 10;
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

        this.cost = SKILLPOINT_PRICE;
        this.currentGold = player.getGold();
        this.maxSupply = 30;
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

        JLabel lblLine1 = new JLabel("Gain a");
        setupLabel(lblLine1);
        descriptionPanel.add(lblLine1);

        JLabel lblLine2 = new JLabel("Skillpoint");
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

    private void createGoalMap() {

        this.cost = GOAL_MAP_PRICE;
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

        JLabel lblLine1 = new JLabel("Reveals the location");
        setupLabel(lblLine1);
        descriptionPanel.add(lblLine1);

        JLabel lblLine2 = new JLabel("of the Goal Room");
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

    private void createWeapon() {

        this.cost = UPGRADE_PRICE;
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

        //========== Stat 1 Panel ==========

        GridLayout subLayout = new GridLayout(2, 1);
        statPanel1.setBackground(Color.BLACK);
        statPanel1.setLayout(subLayout);

        JLabel lblATT = new JLabel("ATT");
        setupLabel(lblATT);
        statPanel1.add(lblATT);

        JPanel displayAtt = new JPanel();
        displayAtt.setLayout(new GridLayout(1, 2));
        displayAtt.setBackground(Color.BLACK);

        //----> Current Value Label <----
        lblCurrentGold.setText("" + player.getDMG());
        setupLabel(lblCurrentGold);
        lblCurrentGold.setHorizontalAlignment(JLabel.RIGHT);
        displayAtt.add(lblCurrentGold);

        //----> Added Value Label <----
        int attIncrease = (int)(player.getBaseDMG() * WEAPON_ATT_PER_RARITY);
        lblCost.setText(" +" + attIncrease);
        setupLabel(lblCost);
        lblCost.setForeground(Color.GREEN);
        lblCost.setHorizontalAlignment(JLabel.LEFT);
        displayAtt.add(lblCost);

        statPanel1.add(displayAtt);

        this.add(statPanel1);
        statPanel1.setVisible(false);

        this.add(new JLabel());


        //========== Purchase Button ==========
        btnBuy.setText("Upgrade");
        setupButton(btnBuy);
        this.add(btnBuy);
        btnBuy.setVisible(false);
    }

    private void createShield() {

        this.cost = UPGRADE_PRICE;
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

        //========== Stat 1 Panel ==========

        GridLayout subLayout = new GridLayout(2, 1);
        statPanel1.setBackground(Color.BLACK);
        statPanel1.setLayout(subLayout);

        JLabel lblATT = new JLabel("DEF");
        setupLabel(lblATT);
        statPanel1.add(lblATT);

        JPanel displayAtt = new JPanel();
        displayAtt.setLayout(new GridLayout(1, 2));
        displayAtt.setBackground(Color.BLACK);

        //----> Current Value Label <----
        lblCurrentGold.setText((int)(player.getDefence() * 100) + "%");
        setupLabel(lblCurrentGold);
        lblCurrentGold.setHorizontalAlignment(JLabel.RIGHT);
        displayAtt.add(lblCurrentGold);

        //----> Added Value Label <----
        int defIncrease = (int)(SHIELD_DEF_PER_RARITY * 100);
        lblCost.setText(" +" + defIncrease + "%");
        setupLabel(lblCost);
        lblCost.setForeground(Color.GREEN);
        lblCost.setHorizontalAlignment(JLabel.LEFT);
        displayAtt.add(lblCost);

        statPanel1.add(displayAtt);

        this.add(statPanel1);
        statPanel1.setVisible(false);

        this.add(new JLabel());
        statPanel2.setVisible(false);


        //========== Purchase Button ==========
        btnBuy.setText("Upgrade");
        setupButton(btnBuy);
        this.add(btnBuy);
        btnBuy.setVisible(false);
    }

    private void createHelmet() {
        this.cost = UPGRADE_PRICE;
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

        //========== Stat 1 Panel ==========

        GridLayout subLayout = new GridLayout(2, 1);
        statPanel1.setBackground(Color.BLACK);
        statPanel1.setLayout(subLayout);

        JLabel lblATT = new JLabel("ATT");
        setupLabel(lblATT);
        statPanel1.add(lblATT);

        JPanel displayAtt = new JPanel();
        displayAtt.setLayout(new GridLayout(1, 2));
        displayAtt.setBackground(Color.BLACK);

        //----> Current Value Label <----
        lblCurrentGold.setText("" + player.getDMG());
        setupLabel(lblCurrentGold);
        lblCurrentGold.setForeground(Color.WHITE);
        lblCurrentGold.setHorizontalAlignment(JLabel.RIGHT);
        displayAtt.add(lblCurrentGold);

        //----> Added Value Label <----
        int attIncrease = (int)(player.getDMG() * HELMET_ATT_PER_RARITY);
        lblCost.setText(" +" + attIncrease);
        setupLabel(lblCost);
        lblCost.setForeground(Color.GREEN);
        lblCost.setHorizontalAlignment(JLabel.LEFT);
        displayAtt.add(lblCost);

        statPanel1.add(displayAtt);

        this.add(statPanel1);
        statPanel1.setVisible(false);


        //========== Stat 2 Panel ==========

        statPanel2.setBackground(Color.BLACK);
        statPanel2.setLayout(subLayout);

        JLabel lblDEF = new JLabel("DEF");
        setupLabel(lblDEF);
        statPanel2.add(lblDEF);

        JPanel displayDef = new JPanel();
        displayDef.setLayout(new GridLayout(1, 2));
        displayDef.setBackground(Color.BLACK);

        //----> Current Value Label <----
        lblCurrentSupply.setText((int)(player.getDefence() * 100) + "%");
        setupLabel(lblCurrentSupply);
        lblCurrentSupply.setHorizontalAlignment(JLabel.RIGHT);
        displayDef.add(lblCurrentSupply);

        //----> Added Value Label <----
        int defIncrease = (int)(HELMET_DEF_PER_RARITY * 100);
        lblMaxSupply.setText(" +" + defIncrease + "%");
        setupLabel(lblMaxSupply);
        lblMaxSupply.setForeground(Color.GREEN);
        lblMaxSupply.setHorizontalAlignment(JLabel.LEFT);
        displayDef.add(lblMaxSupply);

        statPanel2.add(displayDef);

        this.add(statPanel2);
        statPanel2.setVisible(false);


        //========== Purchase Button ==========
        btnBuy.setText("Upgrade");
        setupButton(btnBuy);
        this.add(btnBuy);
        btnBuy.setVisible(false);
    }

    private void createArmour() {

        this.cost = UPGRADE_PRICE;
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

        //========== Stat 1 Panel ==========

        GridLayout subLayout = new GridLayout(2, 1);
        statPanel1.setBackground(Color.BLACK);
        statPanel1.setLayout(subLayout);

        JLabel lblATT = new JLabel("ATT");
        setupLabel(lblATT);
        statPanel1.add(lblATT);

        JPanel displayAtt = new JPanel();
        displayAtt.setLayout(new GridLayout(1, 2));
        displayAtt.setBackground(Color.BLACK);

        //----> Current Value Label <----
        lblCurrentGold.setText("" + player.getDMG());
        setupLabel(lblCurrentGold);
        lblCurrentGold.setHorizontalAlignment(JLabel.RIGHT);
        displayAtt.add(lblCurrentGold);

        //----> Added Value Label <----
        int attIncrease = (int)(player.getDMG() * ARMOUR_ATT_PER_RARITY);
        lblCost.setText(" +" + attIncrease);
        setupLabel(lblCost);
        lblCost.setForeground(Color.GREEN);
        lblCost.setHorizontalAlignment(JLabel.LEFT);
        displayAtt.add(lblCost);

        statPanel1.add(displayAtt);

        this.add(statPanel1);
        statPanel1.setVisible(false);


        //========== Stat 2 Panel ==========

        statPanel2.setBackground(Color.BLACK);
        statPanel2.setLayout(subLayout);

        JLabel lblDEF = new JLabel("DEF");
        setupLabel(lblDEF);
        statPanel2.add(lblDEF);

        JPanel displayDef = new JPanel();
        displayDef.setLayout(new GridLayout(1, 2));
        displayDef.setBackground(Color.BLACK);

        //----> Current Value Label <----
        lblCurrentSupply.setText((int)(player.getDefence() * 100) + "%");
        setupLabel(lblCurrentSupply);
        lblCurrentSupply.setHorizontalAlignment(JLabel.RIGHT);
        displayDef.add(lblCurrentSupply);

        //----> Added Value Label <----
        int defIncrease = (int)(ARMOUR_DEF_PER_RARITY * 100);
        lblMaxSupply.setText(" +" + defIncrease + "%");
        setupLabel(lblMaxSupply);
        lblMaxSupply.setForeground(Color.GREEN);
        lblMaxSupply.setHorizontalAlignment(JLabel.LEFT);
        displayDef.add(lblMaxSupply);

        statPanel2.add(displayDef);

        this.add(statPanel2);
        statPanel2.setVisible(false);


        //========== Purchase Button ==========
        btnBuy.setText("Upgrade");
        setupButton(btnBuy);
        this.add(btnBuy);
        btnBuy.setVisible(false);
    }

    private void createGloves() {

        this.cost = UPGRADE_PRICE;
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

        //========== Stat 1 Panel ==========

        GridLayout subLayout = new GridLayout(2, 1);
        statPanel1.setBackground(Color.BLACK);
        statPanel1.setLayout(subLayout);

        JLabel lblATT = new JLabel("Crit Rate");
        setupLabel(lblATT);
        statPanel1.add(lblATT);

        JPanel displayAtt = new JPanel();
        displayAtt.setLayout(new GridLayout(1, 2));
        displayAtt.setBackground(Color.BLACK);

        //----> Current Value Label <----
        lblCurrentGold.setText((int)(player.getCritRate() * 100) + "%");
        setupLabel(lblCurrentGold);
        lblCurrentGold.setHorizontalAlignment(JLabel.RIGHT);
        displayAtt.add(lblCurrentGold);

        //----> Added Value Label <----
        int critIncrease = (int)(GLOVES_CRIT_RATE_PER_RARITY * 100);
        lblCost.setText(" +" + critIncrease + "%");
        setupLabel(lblCost);
        lblCost.setForeground(Color.GREEN);
        lblCost.setHorizontalAlignment(JLabel.LEFT);
        displayAtt.add(lblCost);

        statPanel1.add(displayAtt);

        this.add(statPanel1);
        statPanel1.setVisible(false);


        //========== Stat 2 Panel ==========

        statPanel2.setBackground(Color.BLACK);
        statPanel2.setLayout(subLayout);

        JLabel lblDEF = new JLabel("DEF");
        setupLabel(lblDEF);
        statPanel2.add(lblDEF);

        JPanel displayDef = new JPanel();
        displayDef.setLayout(new GridLayout(1, 2));
        displayDef.setBackground(Color.BLACK);

        //----> Current Value Label <----
        lblCurrentSupply.setText((int)(player.getDefence() * 100) + "%");
        setupLabel(lblCurrentSupply);
        lblCurrentSupply.setHorizontalAlignment(JLabel.RIGHT);
        displayDef.add(lblCurrentSupply);

        //----> Added Value Label <----
        int defIncrease = (int)(GLOVES_DEF_PER_RARITY * 100);
        lblMaxSupply.setText(" +" + defIncrease + "%");
        setupLabel(lblMaxSupply);
        lblMaxSupply.setForeground(Color.GREEN);
        lblMaxSupply.setHorizontalAlignment(JLabel.LEFT);
        displayDef.add(lblMaxSupply);

        statPanel2.add(displayDef);

        this.add(statPanel2);
        statPanel2.setVisible(false);


        //========== Purchase Button ==========
        btnBuy.setText("Upgrade");
        setupButton(btnBuy);
        this.add(btnBuy);
        btnBuy.setVisible(false);
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
        button.setFocusPainted(false);
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
                    if(item == WEAPON || item == SHIELD || item == HELMET || item == ARMOUR || item == GLOVES) { button.setText("Upgrade"); }
                    else { button.setText("Purchase"); }

                    button.setForeground(Color.WHITE);
                }
            }
        });
        
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        super.setBackground(Color.BLACK);
        super.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.WHITE, Color.GRAY));
    }

}
