public abstract class Beast extends Monster{
    
    private final int BLEED_DMG = 5;
    
    public Beast(int level) {
        super(level, 4);
    }

    public int attack(Player player) {
        int dmgDealt = this.DMG - (int)(this.DMG * player.getDamageReduction());
        player.takeDMG(dmgDealt);

        if((int)(Math.random() * 4) == 0)
            inflictBleed(player);

        return dmgDealt;
    }

    private void inflictBleed(Player player) {
        int bleedDMG = ((int)(Math.random() * (player.getLevel() - this.level)) * BLEED_DMG);
        if(bleedDMG <= 0) bleedDMG = BLEED_DMG;
        player.setStatusEffect(Player.BLEED, bleedDMG);
    }
}
