public class Dragon extends Boss{

    public Dragon() {
        super(Boss.DRAGON, 2500, 500);
    }

    @Override
    public int attack(Player player) {
        int dmgDealt = player.takeDMG(this.DMG);
        if((int)(Math.random() * 10) == 0)      //chance for another attack
            dmgDealt += attack(player);

        return dmgDealt;
    }
    
}
