public class ElderLich extends Boss{
    
    private final double LIFE_STEAL_PERCENT = 0.5;

    public ElderLich() {
        super(Boss.ELDER_LICH, 1500, 250);
    }

    @Override
    public int attack(Player player) {
        int dmgDealt = player.takeDMG(this.DMG);
        if((int)(Math.random() * 10) == 0)      //chance for another attack
            lifeSteal(dmgDealt);

        return dmgDealt;
    }

    private void lifeSteal(int dmgDealt) {
        this.currentHP += (int)(dmgDealt * LIFE_STEAL_PERCENT);
    }

}
