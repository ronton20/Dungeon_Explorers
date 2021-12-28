import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;

public class FightPanel extends JPanel implements ActionListener{

    private Player player;
    private Monster monster;
    private ActionListener listener;

    Font normalFont = new Font("Times New Roman", Font.PLAIN, 25);
    Font textFont = new Font("Times New Roman", Font.PLAIN, 18);

    JButton btnAttack = new JButton();
    JButton btnUsePot = new JButton();
    JButton btnRun = new JButton();

    JLabel lblPlayer = new JLabel();
    JLabel lblmonster = new JLabel();
    JLabel lblPlayerDMG = new JLabel();
    JLabel lblMonsterDMG = new JLabel();
    JLabel lblPlayerAction = new JLabel();
    JLabel lblMonsterAction = new JLabel();

    JPanel playerPanel = new JPanel();
    JPanel monsterPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();

    hpPanel playerHP;
    hpPanel monsterHP;
    
    public FightPanel(Player player, Monster monster, ActionListener lis) {

        GridLayout mainLayout = new GridLayout(1, 3);
        mainLayout.setHgap(10);
        mainLayout.setVgap(10);
        this.setLayout(mainLayout);
        
        this.player = player;
        this.monster = monster;
        this.listener = lis;

        playerHP = new hpPanel(player.getCurrentHP(), player.getMaxHP(), player.getLevel());
        monsterHP = new hpPanel(monster.getCurrentHP(), monster.getMaxHP(), monster.getLevel());

        GridLayout subLayout = new GridLayout(5, 1);
        subLayout.setHgap(10);
        subLayout.setVgap(10);

        playerPanel.setLayout(subLayout);
        monsterPanel.setLayout(subLayout);
        buttonsPanel.setLayout(subLayout);

        playerPanel.setBackground(Color.BLACK);
        buttonsPanel.setBackground(Color.BLACK);
        monsterPanel.setBackground(Color.BLACK);


        //========== Player Panel ==========
        //----> Player Label <----
        lblPlayer.setText("Player");
        setupLabel(lblPlayer);
        lblPlayer.setFont(normalFont);
        playerPanel.add(lblPlayer);

        //----> Player HP <----
        playerPanel.add(playerHP);

        //----> Player Damage Label <----
        lblPlayerDMG.setText("DMG: " + player.getDMG());
        setupLabel(lblPlayerDMG);
        playerPanel.add(lblPlayerDMG);

        //----> Empty Space <----
        playerPanel.add(new JLabel());

        //----> Player Action Label <----
        lblPlayerAction.setText("");
        setupLabel(lblPlayerAction);
        playerPanel.add(lblPlayerAction);


        //========== Monster Panel ==========
        //----> Monster Label <----
        lblmonster.setText(monster.getName());
        setupLabel(lblmonster);
        lblmonster.setFont(normalFont);
        monsterPanel.add(lblmonster);

        //----> Monster HP <----
        monsterPanel.add(monsterHP);

        //----> Monster Damage Label <----
        lblMonsterDMG.setText("DMG: " + monster.getDMG());
        setupLabel(lblMonsterDMG);
        monsterPanel.add(lblMonsterDMG);

        //----> Empty Space <----
        monsterPanel.add(new JLabel());

        //----> Monster Action Label <----
        lblMonsterAction.setText("");
        setupLabel(lblMonsterAction);
        monsterPanel.add(lblMonsterAction);


        //========== Buttons Panel ==========
        buttonsPanel.add(new JLabel());

        //----> Attack Button <----
        btnAttack.setText("Attack!");
        btnAttack.setFont(normalFont);
        btnAttack.setForeground(Color.WHITE);
        btnAttack.setBackground(Color.DARK_GRAY);
        btnAttack.setBorder(BorderFactory.createEtchedBorder());
        btnAttack.setFocusPainted(false);
        btnAttack.addActionListener(this);
        buttonsPanel.add(btnAttack);

        //----> Use Potion Button <----
        btnUsePot.setText("Use Potion (" + player.getPotions() + ")");
        btnUsePot.setFont(normalFont);
        btnUsePot.setForeground(Color.WHITE);
        btnUsePot.setBackground(Color.DARK_GRAY);
        btnUsePot.setBorder(BorderFactory.createEtchedBorder());
        btnUsePot.setFocusPainted(false);
        btnUsePot.addActionListener(this);
        buttonsPanel.add(btnUsePot);

        //----> Run Button <----
        btnRun.setText("Run Away");
        btnRun.setFont(normalFont);
        btnRun.setForeground(Color.WHITE);
        btnRun.setBackground(Color.DARK_GRAY);
        btnRun.setBorder(BorderFactory.createEtchedBorder());
        btnRun.setFocusPainted(false);
        btnRun.addActionListener(this.listener);
        buttonsPanel.add(btnRun);

        buttonsPanel.add(new JLabel());

        this.add(playerPanel);
        this.add(buttonsPanel);
        this.add(monsterPanel);
    }

    private void setupLabel(JLabel label) {
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setForeground(Color.WHITE);
        label.setFont(textFont);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        super.setBackground(Color.BLACK);
        super.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.WHITE, Color.GRAY));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(btnAttack)) {
            int dmgDealt = player.attack(monster);
            monsterHP.updateHealth(monster.getCurrentHP());
            if(monster.isDead())
                listener.actionPerformed(new ActionEvent(btnAttack, ActionEvent.ACTION_PERFORMED, null) {
                    //Nothing need go here, the actionPerformed method (with the
                    //above arguments) will trigger the respective listener
              });
        }
        
    }
}
