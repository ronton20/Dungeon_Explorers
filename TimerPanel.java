import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;

public class TimerPanel extends JPanel {
    
    private long milliseconds;
    Timer clock;
    private JLabel clockLabel = new JLabel();

    public TimerPanel() {

        this.milliseconds = 0;

        clockLabel.setForeground(Color.WHITE);
        clockLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        clockLabel.setHorizontalAlignment(JLabel.LEFT);
        clockLabel.setVerticalAlignment(JLabel.BOTTOM);
        clockLabel.setVisible(false);

        this.add(clockLabel);

        this.clock = new Timer(1, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                milliseconds++;
                long seconds = milliseconds / 100;
                int day = (int) TimeUnit.SECONDS.toDays(seconds);
                long hours = TimeUnit.SECONDS.toHours(seconds) - (day * 24);
                long minute = TimeUnit.SECONDS.toMinutes(seconds)
                        - (TimeUnit.SECONDS.toHours(seconds) * 60);
                long second = TimeUnit.SECONDS.toSeconds(seconds)
                        - (TimeUnit.SECONDS.toMinutes(seconds) * 60);
                long millisecond = (TimeUnit.MILLISECONDS.toMillis(milliseconds) 
                        - (TimeUnit.MILLISECONDS.toSeconds(milliseconds) * 1000)) % 100;
                clockLabel.setText(String.format("%02d:%02d:%02d:%02d", hours, minute, second, millisecond));
            }
        });
    }

    public void startTimer()    { clock.start(); }
    public void stopTimer()     { clock.stop(); }
    public void showTimer()     { clockLabel.setVisible(true); }
    public void hideTimer()     { clockLabel.setVisible(false); }
    public void resetTimer()    { milliseconds = 0; }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        super.setBackground(Color.BLACK);
    }
}
