import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import java.awt.event.*;
import java.awt.*;

public class SkillsPanel extends JPanel implements ActionListener{

    Font normalFont = new Font("Times New Roman", Font.PLAIN, 25);
    Font textFont = new Font("Times New Roman", Font.PLAIN, 15);
    Font countFont = new Font(Font.SANS_SERIF , Font.PLAIN, 15);

    private Player player;
    private ActionListener listener;

    JPanel titlePanel = new JPanel();
    JPanel backPanel = new JPanel();
    JPanel bodyPanel = new JPanel();

    HPskillPanel hpPanel;
    DMGskillPanel dmgPanel;

    JLabel lblSkills = new JLabel();
    JLabel lblSkillPoints = new JLabel();
    JButton btnClose = new JButton();

    Color backgroundColor = new Color(30, 30, 30);

    public SkillsPanel(Player player, ActionListener lis) {
        this.player = player;
        this.listener = lis;

        this.setLayout(null);

        //========== SKILLS Title ==========
        lblSkills.setText("SKILLS");
        lblSkills.setFont(normalFont);
        lblSkills.setForeground(Color.WHITE);
        lblSkills.setHorizontalAlignment(JLabel.CENTER);
        lblSkills.setVerticalAlignment(JLabel.CENTER);

        //========== Skill Point Label ==========
        lblSkillPoints.setText("Skill Points: " + player.getSkillPoints());
        lblSkillPoints.setFont(countFont);
        lblSkillPoints.setForeground(Color.YELLOW);
        lblSkillPoints.setHorizontalAlignment(JLabel.CENTER);
        lblSkillPoints.setVerticalAlignment(JLabel.CENTER);

        titlePanel.setBackground(Color.BLACK);
        titlePanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.WHITE, Color.GRAY));
        titlePanel.setLayout(new GridLayout(1, 3));

        titlePanel.add(lblSkillPoints);
        titlePanel.add(lblSkills);
        titlePanel.add(new JLabel());
        this.add(titlePanel);

        //========== Close Button ==========
        btnClose.setText("Close");
        btnClose.setFont(normalFont);
        btnClose.setForeground(Color.WHITE);
        btnClose.setBackground(Color.BLACK);
        btnClose.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.WHITE, Color.GRAY));
        btnClose.setFocusPainted(false);
        btnClose.addActionListener(this.listener);
        btnClose.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnClose.setBackground(Color.LIGHT_GRAY);
                btnClose.setForeground(Color.BLACK);
            }
        
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnClose.setBackground(Color.BLACK);
                btnClose.setForeground(Color.WHITE);
            }
        });

        this.add(btnClose);

        hpPanel = new HPskillPanel(player, this);
        dmgPanel = new DMGskillPanel(player, this);

        this.add(hpPanel);
        this.add(dmgPanel);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        super.setBackground(backgroundColor);
        super.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.WHITE, Color.GRAY));

        titlePanel.setBounds(0, 0, getWidth(), getHeight() / 10);
        btnClose.setBounds(0, getHeight() - getHeight() / 10, getWidth(), getHeight() / 10);

        int hpPanelWidth = getWidth() / 5;
        int hpPanelHeight = getHeight() / 3;
        hpPanel.setBounds(getWidth() / 3 - hpPanelWidth / 2, getHeight() / 2 - hpPanelHeight / 2, hpPanelWidth, hpPanelHeight);

        int dmgPanelWidth = getWidth() / 5;
        int dmgPanelHeight = getHeight() / 3;
        dmgPanel.setBounds(getWidth() / 3 * 2 - dmgPanelWidth / 2, getHeight() / 2 - dmgPanelHeight / 2, dmgPanelWidth, dmgPanelHeight);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(hpPanel.btnLevelUp)) {
            player.levelHP();
            hpPanel.updateCurrentHP();
            lblSkillPoints.setText("Skill Points: " + player.getSkillPoints());
        }
        
        if(e.getSource().equals(dmgPanel.btnLevelUp)) {
            player.levelDMG();
            dmgPanel.updateCurrentDMG();
            lblSkillPoints.setText("Skill Points: " + player.getSkillPoints());
        }
    }
}
