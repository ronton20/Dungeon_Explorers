public abstract class Undead extends Monster{
    
    private final double LIFE_STEAL_PERCENT = 1.2;

    public Undead(int level) {
        super(level, 5);
    }

    public void attack(Player player) {
        int dmgDealt = this.DMG - (int)(this.DMG * player.getDamageReduction());
        player.takeDMG(dmgDealt);
        lifeSteal(dmgDealt);
    }

    private void lifeSteal(int dmgDealt) {
        this.currentHP += (int)(dmgDealt * LIFE_STEAL_PERCENT);
    }
}
