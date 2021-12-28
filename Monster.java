
public abstract class Monster {
    
    protected int maxHP;
    protected int currentHP;
    protected int DMG;
    protected int level;
    protected int gold;
    protected int EXP;

    public final int DEFAULT_MAX_HP = 60;
    public final int DEFAULT_CURRENT_HP = DEFAULT_MAX_HP;
    public final int DEFAULT_DMG = 5;
    public final int DEFAULT_LEVEL = 1;
    public final int DEFAULT_GOLD = 5;
    public final int DEFAULT_EXP = 40;

    private final int HP_PER_LEVEL = 10;
    private final int DMG_PER_LEVEL = 2;
    private final int GOLD_PER_LEVEL = 5;
    private final double EXP_PER_LEVEL = 0.2;

    private boolean dead;

    public Monster() {
        maxHP = DEFAULT_MAX_HP;
        currentHP = DEFAULT_CURRENT_HP;
        DMG = DEFAULT_DMG;
        level = DEFAULT_LEVEL;
        gold = DEFAULT_GOLD;
        EXP = DEFAULT_EXP;
        dead = false;
    }

    public Monster(int level) {
        this.level = level;
        this.maxHP = DEFAULT_MAX_HP + (((int)(Math.random() * HP_PER_LEVEL) + 5) * level);
        this.currentHP = this.maxHP;
        this.DMG = DEFAULT_DMG + (((int)(Math.random() * DMG_PER_LEVEL) + 1) * level);
        this.gold = DEFAULT_GOLD + (((int)(Math.random() * GOLD_PER_LEVEL) + 1) * this.level);
        this.EXP = DEFAULT_EXP + (int)(DEFAULT_EXP * EXP_PER_LEVEL) * this.level;
    }

    public Monster(int level, int basicDmg) {
        this.level = level;
        this.maxHP = DEFAULT_MAX_HP + (((int)(Math.random() * HP_PER_LEVEL) + 5) * level);
        this.currentHP = this.maxHP;
        this.DMG = basicDmg + (((int)(Math.random() * DMG_PER_LEVEL) + 1) * level);
        this.gold = DEFAULT_GOLD + (((int)(Math.random() * GOLD_PER_LEVEL) + 1) * this.level);
        this.EXP = DEFAULT_EXP + (int)(DEFAULT_EXP * EXP_PER_LEVEL) * this.level;
    }

    public int getCurrentHP() { return currentHP; }
    public int getMaxHP() { return maxHP; }
    public int getDMG() { return DMG; }
    public int getLevel() { return level; }
    public int getGold() { return gold; }
    public int getEXP() { return EXP; }
    public boolean isDead() { return dead; }
    public abstract String getName();

    public void takeDMG(int DMG) {
        this.currentHP -= DMG;
        if(this.currentHP <= 0) {
            this.currentHP = 0;
            dead = true;
        }
    }

    public void heal(int health) {
        currentHP += health;
        if(currentHP > maxHP)
            currentHP = maxHP;
    }

    public abstract int attack(Player player);
}
