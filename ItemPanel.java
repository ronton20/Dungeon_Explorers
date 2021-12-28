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

    JLabel lblItem = new JLabel();
    JLabel lblCurrentGold = new JLabel();
    JLabel lblCost = new JLabel();
    JLabel lblCurrentSupply = new JLabel();
    JLabel lblMaxSupply = new JLabel();

    private int currentGold;
    private int cost;
    private int currentSupply;
    private int maxSupply;

    private final int POTION_PRICE = 50;

    JButton btnBuy = new JButton();

    private Player player;
    private ActionListener listener;

    private String item;

    public ItemPanel(Player player, ActionListener lis, String item) {

        this.player = player;
        this.listener = lis;
        this.item = item;

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
        if(this.currentSupply <= 0) lblCurrentSupply.setForeground(Color.RED);
        else lblCurrentSupply.setForeground(Color.WHITE);
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
        lblItem.setText("Refillable Potion");
        setupLabel(lblItem);
        lblItem.setFont(middleFont);
        lblItem.setBackground(Color.DARK_GRAY);
        lblItem.setOpaque(true);
        this.add(lblItem);

        //========== Cost Panel ==========

        GridLayout subLayout = new GridLayout(2, 1);
        JPanel costPanel = new JPanel();
        costPanel.setBackground(Color.BLACK);
        costPanel.setLayout(subLayout);

        JLabel lblPrice = new JLabel("Price:");
        setupLabel(lblPrice);
        costPanel.add(lblPrice);

        JPanel displayGold = new JPanel();
        displayGold.setLayout(new GridLayout(1, 2));
        displayGold.setBackground(Color.BLACK);

        //----> Current Gold Label <----
        lblCurrentGold.setText("" + currentGold);
        setupLabel(lblCurrentGold);
        if(currentGold < cost) lblCurrentGold.setForeground(Color.RED);
        lblCurrentGold.setHorizontalAlignment(JLabel.RIGHT);
        displayGold.add(lblCurrentGold);

        //----> Price Label <----
        lblCost.setText("/" + cost);
        setupLabel(lblCost);
        lblCost.setHorizontalAlignment(JLabel.LEFT);
        displayGold.add(lblCost);

        costPanel.add(displayGold);
        this.add(costPanel);


        //========== Quantity Panel ==========

        JPanel quantityPanel = new JPanel();
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

    }

    private void createLevel() {

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
        button.setBorder(BorderFactory.createEtchedBorder());
        button.setFocusPainted(false);
        button.addActionListener(this.listener);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        super.setBackground(Color.BLACK);
        super.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.WHITE, Color.GRAY));
    }

}
