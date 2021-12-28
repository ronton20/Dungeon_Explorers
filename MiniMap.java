import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;

public class MiniMap extends JPanel{

    Cell[][] map;
    int blockSize;
    boolean dark;
    
    public MiniMap(Cell[][] map) {
        this.map = map;
        dark = false;
    }

    public void darken() { dark = true; }
    public void brighten() { dark = false; }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        super.setBackground(Color.DARK_GRAY);

        blockSize = getWidth() / map.length;

        for(int row = 0; row < map.length; row++) {
            for (int col = 0; col < map.length; col++) {
                if(map[row][col] != null) map[row][col].draw(g);
            }
        }

        g.setColor(Color.black);
        g.drawLine(0, 0, getWidth(), 0);
        g.drawLine(0, 0, 0, getHeight());
        g.drawLine(getWidth() - 1, 0, getWidth() - 1, getHeight());
        g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);

        if(dark) {
            g.setColor(new Color(0, 0, 0, 0.5f));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        // g.setColor(Color.BLACK);
        // for(int i = 0; i < map.length; i++) {
        //     g.drawLine(blockSize * i, 0, blockSize * i, getHeight());
        //     g.drawLine(0, blockSize * i, getWidth(), blockSize * i);
        // }
    }
}
