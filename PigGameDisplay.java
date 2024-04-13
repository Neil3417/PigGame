package lab8;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PigGameDisplay extends JFrame implements ActionListener
{
    private static final Color DARK_PINK = new Color(255, 127, 127);
    private static final Color LIGHT_PINK = new Color(255, 195, 195);

    private PigGame game;
    private JLabel nameLabel1;
    private JLabel nameLabel2;
    private JLabel scoreLabel1;
    private JLabel scoreLabel2;
    private JButton rollButton;
    private JButton endTurnButton;
    private PigComponent pigComponent1;
    private PigComponent pigComponent2;
    private JLabel turnTotalLabel;
    private JLabel dieLabel;
    private ImageIcon[] dieIcons;
    private int previousTurnTotal;

    public static void play(PigGame game, String player1, String player2)
    {
        new PigGameDisplay(game, player1, player2);
    }

    public PigGameDisplay(PigGame game, String name1, String name2)
    {
        this.game = game;

        try {
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
        } catch (Exception e) {
            e.printStackTrace();
        }

        Font font = new Font(null, Font.BOLD, 18);
        dieIcons = new ImageIcon[7];
        for (int i = 0; i < dieIcons.length; i++)
            dieIcons[i] = new ImageIcon("die" + i + ".png");
        setTitle("Pig");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 3));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(Color.GREEN);
        getContentPane().add(mainPanel);
        JPanel player1Panel = new JPanel();
        player1Panel.setLayout(new BoxLayout(player1Panel, BoxLayout.PAGE_AXIS));
        player1Panel.setOpaque(false);
        player1Panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(player1Panel);
        nameLabel1 = new JLabel(name1);
        nameLabel1.setFont(font);
        player1Panel.add(nameLabel1);
        pigComponent1 = new PigComponent();
        player1Panel.add(pigComponent1);
        scoreLabel1 = new JLabel();
        scoreLabel1.setFont(font);
        player1Panel.add(scoreLabel1);
        JPanel emptyPanel = new JPanel();
        emptyPanel.setOpaque(false);
        mainPanel.add(emptyPanel);
        JPanel player2Panel = new JPanel();
        player2Panel.setLayout(new BoxLayout(player2Panel, BoxLayout.PAGE_AXIS));
        player2Panel.setOpaque(false);
        player2Panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(player2Panel);
        nameLabel2 = new JLabel(name2);
        nameLabel2.setFont(font);
        player2Panel.add(nameLabel2);
        pigComponent2 = new PigComponent();
        player2Panel.add(pigComponent2);
        scoreLabel2 = new JLabel();
        scoreLabel2.setFont(font);
        player2Panel.add(scoreLabel2);
        emptyPanel = new JPanel();
        emptyPanel.setOpaque(false);
        mainPanel.add(emptyPanel);

        JPanel diePanel = new JPanel();
        diePanel.setOpaque(false);
        diePanel.setLayout(new BoxLayout(diePanel, BoxLayout.PAGE_AXIS));
        diePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(diePanel);

        dieLabel = new JLabel(dieIcons[0]);
        diePanel.add(dieLabel);

        turnTotalLabel = new JLabel();
        turnTotalLabel.setFont(font);
        diePanel.add(turnTotalLabel);

        emptyPanel = new JPanel();
        emptyPanel.setOpaque(false);
        mainPanel.add(emptyPanel);

        rollButton = new JButton("Roll");
        rollButton.setFont(font);
        rollButton.setBackground(Color.YELLOW);
        rollButton.setActionCommand("roll");
        rollButton.addActionListener(this);
        mainPanel.add(rollButton);

        emptyPanel = new JPanel();
        emptyPanel.setOpaque(false);
        mainPanel.add(emptyPanel);

        endTurnButton = new JButton("End Turn");
        endTurnButton.setFont(font);
        endTurnButton.setBackground(new Color(127, 127, 255));
        endTurnButton.setActionCommand("end");
        endTurnButton.addActionListener(this);
        mainPanel.add(endTurnButton);

        update();
        pack();
        setVisible(true);
    }

    private void update()
    {
        scoreLabel1.setText("" + game.getPlayer1Score());
        scoreLabel2.setText("" + game.getPlayer2Score());
        turnTotalLabel.setText("Total:  " + game.getTurnTotal());
        if (game.getWinner() == 1 || (game.getWinner() == 0 && game.getTurn() == 1))
        {
            nameLabel1.setForeground(Color.BLACK);
            pigComponent1.setFaceColor(LIGHT_PINK);
            pigComponent1.setEarAndSnoutColor(DARK_PINK);
            scoreLabel1.setForeground(Color.BLACK);
            nameLabel2.setForeground(Color.GRAY);
            pigComponent2.setFaceColor(Color.LIGHT_GRAY);
            pigComponent2.setEarAndSnoutColor(Color.DARK_GRAY);
            scoreLabel2.setForeground(Color.GRAY);
            if (game.getWinner() == 1)
                pigComponent2.setAlive(false);
        }
        else if (game.getWinner() == 2 || (game.getWinner() == 0 && game.getTurn() == 2))
        {
            nameLabel1.setForeground(Color.GRAY);
            pigComponent1.setFaceColor(Color.LIGHT_GRAY);
            pigComponent1.setEarAndSnoutColor(Color.DARK_GRAY);
            scoreLabel1.setForeground(Color.GRAY);
            nameLabel2.setForeground(Color.BLACK);
            pigComponent2.setFaceColor(LIGHT_PINK);
            pigComponent2.setEarAndSnoutColor(DARK_PINK);
            scoreLabel2.setForeground(Color.BLACK);
            if (game.getWinner() == 2)
                pigComponent1.setAlive(false);
        }
        else
            throw new RuntimeException("Illegal state:  winner = " + game.getWinner() + ", turn = " + game.getTurn());
        if (game.getWinner() != 0)
        {
            rollButton.setEnabled(false);
            endTurnButton.setEnabled(false);
            turnTotalLabel.setText("Game Over");
            return;
        }
        if (game.hasBusted())
        {
            turnTotalLabel.setText("BUSTED!");
            rollButton.setEnabled(false);
        }
        else
        {
            rollButton.setEnabled(true);
        }
    }

    public void actionPerformed(ActionEvent e)
    {
        String command = e.getActionCommand();
        if (command.equals("roll"))
        {
            game.roll();
            if (game.hasBusted())
            {
                previousTurnTotal = 0;
                dieLabel.setIcon(dieIcons[1]);
            }
            else
            {
                int roll = game.getTurnTotal() - previousTurnTotal;
                previousTurnTotal = game.getTurnTotal();
                dieLabel.setIcon(dieIcons[roll]);
            }
        }
        else if (command.equals("end"))
        {
            game.endTurn();
            dieLabel.setIcon(dieIcons[0]);
            previousTurnTotal = 0;
        }
        else
            throw new RuntimeException("Invalid command:  " + command);
        update();
    }

    private static class PigComponent extends JComponent
    {
        private Color earAndSnoutColor;
        private Color faceColor;
        private boolean alive;

        public PigComponent()
        {
            setPreferredSize(new Dimension(100, 100));
            earAndSnoutColor = DARK_PINK;
            faceColor = LIGHT_PINK;
            alive = true;
        }

        public void setAlive(boolean alive)
        {
            this.alive = alive;
            repaint();
            try{Thread.sleep(10);}catch(Exception e){}
        }

        public void setEarAndSnoutColor(Color c)
        {
            earAndSnoutColor = c;
            repaint();
            try{Thread.sleep(10);}catch(Exception e){}
        }

        public void setFaceColor(Color c)
        {
            faceColor = c;
            repaint();
            try{Thread.sleep(10);}catch(Exception e){}
        }

        public void paintComponent(Graphics g)
        {
            //ears
            g.setColor(earAndSnoutColor);
            g.fillRect(0, 0, 100, 50);
            //face
            g.setColor(faceColor);
            g.fillOval(0, 0, 100, 100);
            //snout
            g.setColor(earAndSnoutColor);
            g.fillOval(30, 50, 40, 40);
            //nostrils
            g.setColor(Color.BLACK);
            g.fillOval(38, 65, 10, 10);
            g.fillOval(52, 65, 10, 10);
            //eyes
            if (alive)
            {
                g.fillOval(30, 30, 10, 10);
                g.fillOval(60, 30, 10, 10);
            }
            else
            {
                g.drawLine(30, 30, 40, 40);
                g.drawLine(30, 40, 40, 30);
                g.drawLine(60, 30, 70, 40);
                g.drawLine(60, 40, 70, 30);
            }
        }    
    }
}
