import java.awt.*;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.*;

public class Player {

    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;

    public static final int PLAYER_SIZE = 50;
    public static final int STEP = 10;
    private int x;
    private int y;

    public final int DEFAULT_MAX_EXP = 500;
    public final int DEFAULT_CURRENT_EXP = 0;
    public final int DEFAULT_MAX_HP = 120;
    public final int DEFAULT_CURRENT_HP = DEFAULT_MAX_HP;
    public final int DEFAULT_DMG = 15;
    public final int DEFAULT_LEVEL = 1;
    public final int DEFAULT_GOLD = 0;

    private final int HP_PER_POINT = 15;
    private final int DMG_PER_POINT = 3;
    private final int SKILL_POINTS_PER_LEVEL = 3;
    private final double EXP_PERCENT_PER_LEVEL = 1.2;

    private int maxEXP;
    private int currentEXP;
    private int maxHP;
    private int currentHP;
    private int baseDMG;
    private int totalDMG;
    private int level;
    private int gold;
    private int skillPointsHP;
    private int skillPointsDMG;
    private int skillPoints;

    private int panelWidth;
    private int panelHeight;

    public boolean movedRoom;
    public boolean moving;
    private boolean reachedDestination;
    private int direction;
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
        this.skillPointsHP = 0;
        this.skillPointsDMG = 0;
        this.skillPoints = 0;
        this.listener = lis;
        this.moving = false;

        this.x = x;
        this.y = y;

        t = new Timer(25, listener);
    }

    //----> getters <----
    public int getCurrentHP() { return currentHP; }
    public int getMaxHP() { return maxHP; }
    public int getDMG() { return totalDMG; }
    public int getLevel() { return level; }
    public int getGold() { return gold; }
    public int getDirection() { return direction; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getSkillPoints() { return skillPoints; }

    public void takeDMG(int DMG) {
        this.currentHP -= DMG;
        if(this.currentHP <= 0)
            die();
    }

    public void heal(int health) {
        currentHP += health;
        if(currentHP > maxHP)
            currentHP = maxHP;
    }

    public void earnEXP(int exp) {
        currentEXP += exp;
        if(currentEXP >= maxEXP)
            levelUp();
    }

    public void levelUp() {
        skillPoints += SKILL_POINTS_PER_LEVEL;
        level++;
        int tempExp = currentEXP - maxEXP;
        maxEXP = (int)(maxEXP * EXP_PERCENT_PER_LEVEL);
        currentEXP = tempExp;
        if(currentEXP < 0)
            currentEXP = 0;
    }

    public void updateStats() {
        int hpPercent = currentHP / maxHP;
        maxHP = DEFAULT_MAX_HP + (HP_PER_POINT * skillPointsHP);
        currentHP = maxHP * hpPercent;

        baseDMG = DEFAULT_DMG + (DMG_PER_POINT * skillPointsDMG);
        totalDMG = baseDMG;     //***** needs to adjust when items are added *****
    }

    public void die() {

    }

    public void move(int direction) {
        System.out.println("move");
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
        System.out.println("go");
        reachedDestination = false;
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

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, PLAYER_SIZE, PLAYER_SIZE);
    }
}
