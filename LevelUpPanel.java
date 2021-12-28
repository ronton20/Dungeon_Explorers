import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import java.awt.event.*;
import java.awt.*;

public class LevelUpPanel extends JPanel {

    Font normalFont = new Font("Times New Roman", Font.PLAIN, 25);
    Font textFont = new Font("Times New Roman", Font.PLAIN, 15);

    JLabel lblLevelUp = new JLabel();

    JLabel lblHP = new JLabel();
    JLabel lblDMG = new JLabel();
    JLabel lblSkillPoints = new JLabel();

    JButton btnSkills = new JButton();
    JButton btnContinue = new JButton();

    ActionListener listener;
    Player player;
    
    public LevelUpPanel(Player player, ActionListener lis) {
        this.player = player;
        this.listener = lis;

        GridLayout mainLayout = new GridLayout(3, 1);
        mainLayout.setVgap(5);
        this.setLayout(mainLayout);

        //========== Level Up Label ==========
        lblLevelUp.setText("LEVEL UP!");
        setupLabel(lblLevelUp);
        lblLevelUp.setFont(normalFont);
        this.add(lblLevelUp);

        //========== Stats Panel ==========
        GridLayout lblLayout = new GridLayout(3, 1);
        lblLayout.setVgap(3);

        JPanel labelPanel = new JPanel();
        labelPanel.setBackground(Color.BLACK);
        labelPanel.setLayout(lblLayout);

        //----> HP Label <----
        lblHP.setText("Max HP +" + Player.HP_PER_LEVEL);
        setupLabel(lblHP);
        labelPanel.add(lblHP);

        //----> DMG Label <----
        lblDMG.setText("Base DMG +" + Player.DMG_PER_LEVEL);
        setupLabel(lblDMG);
        labelPanel.add(lblDMG);

        //----> Skill Points Label <----
        lblSkillPoints.setText("Skill Points +" + Player.SKILL_POINTS_PER_LEVEL);
        setupLabel(lblSkillPoints);
        labelPanel.add(lblSkillPoints);

        this.add(labelPanel);


        //========== Buttons Panel ==========
        GridLayout btnLayout = new GridLayout(2, 1);
        btnLayout.setVgap(5);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setLayout(btnLayout);

        //----> Skills Button <----
        btnSkills.setText("Go to Skills");
        setupButton(btnSkills);
        buttonPanel.add(btnSkills);

        //----> Continue Button <----
        btnContinue.setText("Continue");
        setupButton(btnContinue);
        buttonPanel.add(btnContinue);

        this.add(buttonPanel);
    }

    private void setupLabel(JLabel label) {
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setForeground(Color.WHITE);
        label.setFont(textFont);
    }

    private void setupButton(JButton button) {
        button.setFont(textFont);
        button.setForeground(Color.WHITE);
        button.setBackground(Color.DARK_GRAY);
        button.setBorder(BorderFactory.createEtchedBorder());
        button.setFocusPainted(false);
        button.addActionListener(this.listener);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        super.setBackground(Color.BLACK);
        super.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.WHITE, Color.GRAY));
    }
}
