public class Zombie extends Undead{
    public Zombie(int level) {
        super(level);
        this.DMG = 9;
    }

    public String getName() { return "Zombie"; }
}
