public abstract class Human extends Monster{
    private final int POISON_DMG = 3;
    
    public Human(int level) {
        super(level, 7);
    }

    public int attack(Player player) {
        int dmgDealt = player.takeDMG(this.DMG);

        if((int)(Math.random() * 3) == 0)
            inflictPoison(player);

        return dmgDealt;
    }

    private void inflictPoison(Player player) {
        int poisonDMG = ((int)(Math.random() * (player.getLevel() - this.level)) * POISON_DMG);
        if(poisonDMG <= 0) poisonDMG = POISON_DMG;
        player.setStatusEffect(Player.BLEED, poisonDMG);
    }
}
