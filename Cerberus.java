public class Cerberus extends Boss{
    
    private int BURN_DMG;

    public Cerberus() {
        super(Boss.CERBERUS, 2000, 400);
        BURN_DMG = this.DMG / 10;
    }

    public int attack(Player player) {
        int dmgDealt = player.takeDMG(this.DMG);

        if((int)(Math.random() * 4) == 0)
            inflictBurn(player);

        return dmgDealt;
    }

    private void inflictBurn(Player player) {
        int burnDMG = ((int)(Math.random() * (player.getLevel() - this.level)) * BURN_DMG);
        if(burnDMG <= 0) burnDMG = BURN_DMG;
        player.setStatusEffect(Player.BURN, burnDMG);
    }
}
