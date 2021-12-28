public abstract class Beast extends Monster{
    
    private final int BLEED_DMG = 5;
    
    public Beast(int level) {
        super(level, 4);
    }

    public void attack(Player player) {
        int dmgDealt = (int)(this.DMG * player.getDamageReduction());
        player.takeDMG(dmgDealt);

        if((int)(Math.random() * 4) == 0)
            inflictBleed(player);
    }

    private void inflictBleed(Player player) {
        if(player.getStatusEffect() == Player.NONE)
            player.setStatusEffect(Player.BLEED, ((int)(Math.random() * (player.getLevel() - this.level)) * BLEED_DMG));
    }
}
