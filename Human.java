public abstract class Human extends Monster{
    private final int POISON_DMG = 3;
    
    public Human(int level) {
        super(level, 7);
    }

    public void attack(Player player) {
        int dmgDealt = (int)(this.DMG * player.getDamageReduction());
        player.takeDMG(dmgDealt);

        if((int)(Math.random() * 3) == 0)
            inflictPoison(player);
    }

    private void inflictPoison(Player player) {
        if(player.getStatusEffect() == Player.NONE)
            player.setStatusEffect(Player.POISON, ((int)(Math.random() * (player.getLevel() - this.level)) * POISON_DMG));
    }
}
