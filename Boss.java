public abstract class Boss extends Monster{
    
    public static final String DRAGON = "Dragon";
    public static final String ELDER_LICH = "Elder Lich";
    public static final String CERBERUS = "Cerberus";

    private String name;

    public Boss(String type, int basicHP, int basicDMG) {
        super(50, basicHP, basicDMG, 1000);
        this.name = type;
    }

    @Override
    public String getName() { return name; }
}
