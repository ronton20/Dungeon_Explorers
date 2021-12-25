import java.awt.*;

public class Player {

    public static final int PLAYER_SIZE = 50;
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

    public Player(int x, int y) {
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

        this.x = x;
        this.y = y;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getDMG() {
        return totalDMG;
    }

    public int getLevel() {
        return level;
    }

    public int getGold() {
        return gold;
    }

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

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, PLAYER_SIZE, PLAYER_SIZE);
    }
}
