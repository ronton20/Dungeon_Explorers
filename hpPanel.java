import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;

public class hpPanel extends JPanel{
    
    private int currentHP;
    private int maxHP;
    private int level;

    private healthBar hpBar;
    private JLabel hp = new JLabel();
    private JLabel lvl = new JLabel();

    Font font = new Font("Times New Roman", Font.PLAIN, 18);

    public hpPanel(int currentHP, int maxHP, int level) {

        this.currentHP = currentHP;
        this.maxHP = maxHP;
        this.level = level;

        hpBar = new healthBar(currentHP, maxHP);

        hp.setText(currentHP + "/" + maxHP);
        hp.setForeground(Color.WHITE);
        hp.setFont(font);
        hp.setHorizontalAlignment(JLabel.CENTER);
        hp.setVerticalAlignment(JLabel.CENTER);

        lvl.setText("Level " + this.level);
        lvl.setForeground(Color.WHITE);
        lvl.setFont(font);
        lvl.setHorizontalAlignment(JLabel.CENTER);
        lvl.setVerticalAlignment(JLabel.CENTER);

        GridLayout layout = new GridLayout(3, 1);
        layout.setVgap(2);
        layout.setHgap(3);
        this.setLayout(layout);

        this.add(lvl);
        this.add(hpBar);
        this.add(hp);

    }

    public void updateHealth(int currentHP) {
        this.currentHP = currentHP;
        hpBar.updateHealth(this.currentHP);
        hp.setText(this.currentHP + "/" + this.maxHP);
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        super.setBackground(Color.BLACK);
    }
}
