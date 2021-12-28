import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.BevelBorder;

import java.awt.*;

public class healthBar extends JPanel{
    
    private int currentHP;
    private int maxHP;

    private JProgressBar healthBar;

    public healthBar(int currentHP, int maxHP) {
        this.currentHP = currentHP;
        this.maxHP = maxHP;
        this.healthBar = new JProgressBar(0, this.maxHP);
        this.healthBar.setValue(this.currentHP);
        this.healthBar.setBackground(Color.RED);
        this.healthBar.setForeground(Color.GREEN);
        this.healthBar.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.GRAY, Color.DARK_GRAY));
        this.add(healthBar);
    }

    public void updateHealth(int currentHP) {
        this.currentHP = currentHP;
        this.healthBar.setValue(this.currentHP);
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        super.setBackground(Color.BLACK);
        super.setBorder(BorderFactory.createEmptyBorder());
        this.healthBar.setPreferredSize(new Dimension(getWidth() - 10, getHeight()));
    }
}
