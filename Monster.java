
public abstract class Monster {
    
    private int maxHP;
    private int currentHP;
    private int DMG;
    private int level;

    public final int DEFAULT_MAX_HP = 100;
    public final int DEFAULT_CURRENT_HP = DEFAULT_MAX_HP;
    public final int DEFAULT_DMG = 10;
    public final int DEFAULT_LEVEL = 1;

    private final int HP_PER_LEVEL = 10;
    private final int DMG_PER_LEVEL = 2;

    public Monster() {
        maxHP = DEFAULT_MAX_HP;
        currentHP = DEFAULT_CURRENT_HP;
        DMG = DEFAULT_DMG;
        level = DEFAULT_LEVEL;
    }

    public Monster(int maxHP, int DMG) {
        this.maxHP = maxHP;
        this.currentHP = maxHP;
        this.DMG = DMG;
        this.level = DEFAULT_LEVEL;
    }

    public Monster(int level) {
        this.level = level;
        this.maxHP = DEFAULT_MAX_HP + (HP_PER_LEVEL * level);
        this.currentHP = this.maxHP;
        this.DMG = DEFAULT_DMG + (DMG_PER_LEVEL * level);
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getDMG() {
        return DMG;
    }

    public int getLevel() {
        return level;
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

    public void die() {

    }
}
