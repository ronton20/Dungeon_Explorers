public abstract class Undead extends Monster{
    
    private final double LIFE_STEAL_PERCENT = 0.2;

    public Undead(int level) {
        super(level, 5);
    }

    public int attack(Player player) {
        int dmgDealt = this.DMG - (int)(this.DMG * player.getDamageReduction());
        player.takeDMG(dmgDealt);
        if((int)(Math.random() * 5) == 0)
            lifeSteal(dmgDealt);

        return dmgDealt;
    }

    private void lifeSteal(int dmgDealt) {
        this.currentHP += (int)(dmgDealt * LIFE_STEAL_PERCENT);
    }
}
