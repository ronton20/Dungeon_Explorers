import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player {

    public static final String NONE = "None";
    public static final String BLEED = "Bleed";
    public static final String POISON = "Poison";

    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;

    public static final int PLAYER_SIZE = 50;
    public static final int STEP = 10;
    private int x;
    private int y;

    public final int DEFAULT_MAX_EXP = 150;
    public final int DEFAULT_CURRENT_EXP = 0;
    public final int DEFAULT_MAX_HP = 120;
    public final int DEFAULT_CURRENT_HP = DEFAULT_MAX_HP;
    public final int DEFAULT_DMG = 15;
    public final int DEFAULT_LEVEL = 1;
    public final int DEFAULT_GOLD = 0;
    public final double DEFAULT_DMG_REDUCTION = 0;

    public static final int HP_PER_POINT = 5;
    public static final int DMG_PER_POINT = 2;
    public static final int HP_PER_LEVEL = 5;
    public static final int DMG_PER_LEVEL = 2;
    public static final int SKILL_POINTS_PER_LEVEL = 3;
    private final double EXP_PERCENT_PER_LEVEL = 0.2;

    private int maxEXP;
    private int currentEXP;
    private int maxHP;
    private int currentHP;
    private int baseDMG;
    private int totalDMG;
    private int level;
    private int gold;
    private double dmgReduction;
    private int skillPointsHP;
    private int skillPointsDMG;
    private int skillPoints;
    private String statusEffect;
    private int statusEffectDmg;
    private int healthPots;

    private boolean dead;

    private int panelWidth;
    private int panelHeight;

    public boolean movedRoom;
    public boolean moving;
    private boolean reachedGoal;
    private int direction;

    JLabel character = new JLabel();

    Timer t;
    Graphics graphic;
    ActionListener listener;

    public Player(int x, int y, ActionListener lis) {
        this.maxEXP = DEFAULT_MAX_EXP;
        this.currentEXP = DEFAULT_CURRENT_EXP;
        this.maxHP = DEFAULT_MAX_HP;
        this.currentHP = DEFAULT_CURRENT_HP;
        this.baseDMG = DEFAULT_DMG;
        this.totalDMG = DEFAULT_DMG;
        this.level = DEFAULT_LEVEL;
        this.gold = DEFAULT_GOLD;
        this.dmgReduction = DEFAULT_DMG_REDUCTION;
        this.skillPointsHP = 0;
        this.skillPointsDMG = 0;
        this.skillPoints = 0;
        this.healthPots = 5;
        this.listener = lis;
        this.moving = false;
        this.statusEffect = Player.NONE;
        this.statusEffectDmg = 0;
        this.reachedGoal = false;

        dead = false;

        this.x = x;
        this.y = y;

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("big-steve-face.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg = img.getScaledInstance(PLAYER_SIZE, PLAYER_SIZE, Image.SCALE_SMOOTH);

        character.setSize(PLAYER_SIZE, PLAYER_SIZE);
        character.setIcon(new ImageIcon(dimg));

        t = new Timer(10, listener);
    }

    //----> Getters and Simple Methods <----
    public int getCurrentHP()           { return currentHP; }
    public int getMaxHP()               { return maxHP; }
    public int getCurrentEXP()          { return currentEXP; }
    public int getMaxEXP()              { return maxEXP; }
    public int getDMG()                 { return totalDMG; }
    public int getBaseDMG()             { return baseDMG; }
    public int getLevel()               { return level; }
    public int getGold()                { return gold; }
    public int getDirection()           { return direction; }
    public int getX()                   { return x; }
    public int getY()                   { return y; }
    public int getSkillPoints()         { return skillPoints; }
    public int getPotions()             { return healthPots; }
    public double getDamageReduction()  { return dmgReduction; }
    public String getStatusEffect()     { return statusEffect; }
    public int getStatusEffectDMG()     { return statusEffectDmg; }
    public void clearStatusEffect()     { statusEffect = Player.NONE; }
    public boolean isDead()             { return dead; }
    public void goalReached()           { reachedGoal = true; }

    public int attack(Monster monster) {
        int dmgDealt = totalDMG;
        monster.takeDMG(dmgDealt);
        return dmgDealt;
    }

    public void takeDMG(int DMG) {
        this.currentHP -= DMG;
        if(statusEffect == Player.BLEED) this.currentHP -= statusEffectDmg;
        if(statusEffect == Player.POISON) this.currentHP -= statusEffectDmg;
        if(this.currentHP <= 0)
            die();
    }

    public boolean buyPotion(int price) {
        if(gold < price) return false;
        gold -= price;
        healthPots++;
        return true;
    }

    public boolean usePotion() {
        if(healthPots == 0) return false;
        if(statusEffect != Player.NONE) {
            statusEffect = Player.NONE;
            statusEffectDmg = 0;
            healthPots--;
            return true;
        }
        if(currentHP == maxHP) return false;
        healthPots--;
        double healPercent = 0.5;
        int missingHealth = maxHP - currentHP;
        if(missingHealth <= 20)
            heal(20);
        else
            heal((int)(missingHealth * healPercent));
        return true;
    }

    public void heal(int health) {
        currentHP += health;
        if(currentHP > maxHP)
            currentHP = maxHP;
    }

    public void gainSpoils(Monster monster) {
        this.gold += monster.getGold();
        earnEXP(monster.getEXP());
        System.out.println(currentEXP + "/" + maxEXP + ", Level: " + level);
    }

    public void earnEXP(int exp) {
        currentEXP += exp;
        if(currentEXP >= maxEXP)
            levelUp();
    }

    public void levelUp() {
        if(statusEffect != Player.NONE) { statusEffect = Player.NONE; statusEffectDmg = 0; }
        skillPoints += SKILL_POINTS_PER_LEVEL;
        level++;
        updateStats();
        healthPots++;
        if(!usePotion()) healthPots--;
        int tempExp = currentEXP - maxEXP;
        maxEXP = maxEXP + (int)(maxEXP * EXP_PERCENT_PER_LEVEL);
        currentEXP = tempExp;
        if(currentEXP < 0)
            currentEXP = 0;
    }

    public void updateStats() {
        double hpPercent = (maxHP - currentHP) / maxHP;
        maxHP = DEFAULT_MAX_HP + (HP_PER_POINT * skillPointsHP) + (HP_PER_LEVEL * level);
        currentHP = currentHP + (int)(maxHP * hpPercent);

        baseDMG = DEFAULT_DMG + (DMG_PER_POINT * skillPointsDMG) + (DMG_PER_LEVEL * level);
        totalDMG = baseDMG;     //***** needs to adjust when items are added *****
    }

    public void levelHP() {
        if(skillPoints == 0) return;
        skillPointsHP++;
        skillPoints--;
        updateStats();
    }

    public void levelDMG() {
        if(skillPoints == 0) return;
        skillPointsDMG++;
        skillPoints--;
        updateStats();
    }

    public void setStatusEffect(String type, int dmg) {
        statusEffect = type;
        statusEffectDmg = dmg;
    }

    public void die() {
        currentHP = 0;
        dead = true;
    }

    public void move(int direction) {
        if(direction == Player.UP) this.y -= Player.STEP;
        else if(direction == Player.DOWN) this.y += Player.STEP;
        else if(direction == Player.LEFT) this.x -= Player.STEP;
        else if(direction == Player.RIGHT) this.x += Player.STEP;
    }

    public void moveRoom(int direction) {
        switch(direction) {
            case UP:
                y = panelHeight - PLAYER_SIZE;
                return;
            
            case DOWN:
                y = 0;
                return;

            case LEFT:
                x = panelWidth;
                return;

            case RIGHT:
                x = 0;
                return;

            default:
                return;
        }
    }

    public void go(int direction, int panelWidth, int panelHeight, Graphics g) {
        if(!isValidDirection(direction)) return;
        this.direction = direction;
        moving = true;
        graphic = g;
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        this.reachedGoal = false;
        movedRoom = false;
        t.start();
        
    }

    private boolean isValidDirection(int direction) {
        switch(direction) {
            case UP:
                return true;
            
            case DOWN:
                return true;

            case LEFT:
                return true;

            case RIGHT:
                return true;

            default:
                return false;
        }
    }

    public void draw() {
        character.setLocation(x, y);
    }
}
