import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class GUI extends Canvas {

    JFrame frame;
    JPanel panel;

    static int cellAmount = 20;
    static int cellWidth = 20;
    static int width = cellAmount * cellWidth;
    static int height = cellAmount * cellWidth;
    int amountRevealed = 0;
    int maxAmountRevealed = 10;
    boolean gameWon = false;
    boolean gameLost = false;
    boolean revealAll = false;


    Cell[][] Grid = new Cell[cellAmount][cellAmount];

    public GUI(String title) {
        frame = new JFrame();
        frame.setTitle(title);

        panel = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                FontMetrics fm = g2d.getFontMetrics();
                for(int x = 0; x < cellAmount; x++) {
                    for(int y = 0; y < cellAmount; y++) {
                        if(!gameWon && !gameLost) {
                            if(Grid[x][y].revealed || revealAll) {
                                g.setColor(Color.lightGray);
                                g.fillRect(x * cellWidth, y * cellWidth, cellWidth, cellWidth);
                                g.setColor(Color.BLACK);

                                if(!Grid[x][y].bomb) {
                                    g2d.setFont(new Font("Arial", Font.BOLD, 15));
                                    String toPrint = Grid[x][y].nearby() + "";

                                    int fx = (Grid[x][y].x * cellWidth + cellWidth + Grid[x][y].x * cellWidth)/2 - fm.stringWidth(toPrint)/2;
                                    int fy = (Grid[x][y].y * cellWidth + cellWidth + Grid[x][y].y * cellWidth) / 2 + fm.getHeight()/3;
                                    g2d.drawString(toPrint, fx, fy);
                                }
                            }
                            g.drawRect(x * cellWidth, y * cellWidth, cellWidth, cellWidth);
                            if (Grid[x][y].bomb && Grid[x][y].revealed || revealAll && Grid[x][y].bomb) {
                                g.fillOval(x * cellWidth + cellWidth / 8, y * cellWidth + cellWidth / 8, cellWidth - cellWidth / 4, cellWidth - cellWidth / 4);
                            }
                        }
                        if(gameLost || gameWon) {
                            g2d.setColor(Color.WHITE);
                            g2d.drawRect(0, 0, width, height);
                            g2d.setColor(Color.BLACK);
                            g2d.setFont(new Font("Arial", Font.BOLD, 25));
                            int fy = (height - fm.getHeight())/2;
                            if(gameWon) {
                                String toPrint  = "Game won!";
                                int fx = (width/2 - fm.stringWidth(toPrint));
                                g2d.drawString(toPrint, fx, fy);
                            } else if(gameLost) {
                                String toPrint = "Game lost!";
                                int fx = (width/2 - fm.stringWidth(toPrint));
                                g2d.drawString(toPrint, fx, fy);
                            }
                        }
                    }
                }
            }
        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if(!Grid[e.getX()/cellWidth][e.getY()/cellWidth].revealed) {
                    Grid[e.getX()/cellWidth][e.getY()/cellWidth].reveal();
                }
                gameWon = gameWon();
                if(Grid[e.getX()/cellWidth][e.getY()/cellWidth].bomb)
                    gameLost = true;
                amountRevealed = 0;
                panel.repaint();
            }
        });

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_C) {
                    revealAll = true;
                    panel.repaint();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_C) {
                    revealAll = false;
                    panel.repaint();
                }
            }
        });

        panel.setPreferredSize(new Dimension(width + 1, height + 1));

        frame.add(this);
        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        init();
    }

    public void init() {
        for(int i = 0; i < Grid.length; i++) {
            for(int j = 0; j < Grid.length; j++) {
                Grid[i][j] = new Cell(i, j);
            }
        }
    }

    public boolean gameWon() {
        for(int x = 0; x < Grid.length; x++) {
            for(int y = 0; y < Grid.length; y++) {
                if(!Grid[x][y].revealed && !Grid[x][y].bomb) {
                    return false;
                }
            }
        }
        return true;
    }

}
