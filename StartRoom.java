import java.awt.Color;
import java.awt.Graphics;

public class StartRoom extends Room{
    public StartRoom() {
        super(true, false, true, true);
        roomColor = Color.GREEN;
    }

    public void openExit() {
        this.down = true;
    }
    
    @Override
    public void draw(Graphics g, int width, int height) {
        // TODO Auto-generated method stub
        
    }
    
}
