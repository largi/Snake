/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Snake;

/**
 *
 * @author marco
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    //Fieldsize
    private final int B_WIDTH = 500;
    private final int B_HEIGHT = 500;
    //dot/Bodypartsize
    private final int DOT_SIZE = 25;
    //number of dot ((B_WIDTH * B_HEIGHT) / (DOT_SIZE * DOT_SIZE)
    private final int ALL_DOTS = 400;
    //RND multiplicator B_WIDTH
    private final int RAND_POS = 19;

    //Position of bodyparts
    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];

    //number of bodyparts
    private int dots;

    //Apple position
    private int apple_x;
    private int apple_y;

    //Boolean for direction change
    private boolean rightDirection = true;

    private boolean leftDirection = false;
    private boolean upDirection = false;
    private boolean downDirection = false;

    //check game still going on
    private boolean inGame = true;

    //Timer and Delay (-= faster || +=slower)
    private int DELAY = 200;
    private Timer timer;
    private boolean setpause = false;

    //Container for snakebody and apple
    private ImageIcon body;
    private ImageIcon body2;
    private ImageIcon apple;

    //Container for snakehead with 4 directions
    private ImageIcon headu;
    private ImageIcon headd;
    private ImageIcon headl;
    private ImageIcon headr;

    public Board() {

        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        initGame();
    }

    /**
     * Start Game with 3 Snake dots Set Position of the dots start timer
     */
    private void initGame() {

        dots = 3;

        for (int z = 0; z < dots; z++) {
            x[z] = 200 - z * DOT_SIZE;
            y[z] = 200;
        }

        locateApple();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    /**
     * Draw Snake in position up down left or right Draw apple
     *
     * @param g
     */
    private void doDrawing(Graphics g) {

        if (inGame) {

            apple = new ImageIcon("apple.png");
            apple.paintIcon(this, g, apple_x, apple_y);

            headr = new ImageIcon("headr.png");
            headr.paintIcon(this, g, x[0], y[0]);

            //Print head in position up, down, left or right.
            //z = Snakedot with position 0 (Head)
            for (int z = 0; z < dots; z++) {

                if (z == 0 && rightDirection) {
                    headr = new ImageIcon("headr.png");
                    headr.paintIcon(this, g, x[z], y[z]);
                }
                if (z == 0 && leftDirection) {
                    headl = new ImageIcon("headl.png");
                    headl.paintIcon(this, g, x[z], y[z]);
                }
                if (z == 0 && upDirection) {
                    headu = new ImageIcon("headu.png");
                    headu.paintIcon(this, g, x[z], y[z]);
                }
                if (z == 0 && downDirection) {
                    headd = new ImageIcon("headd.png");
                    headd.paintIcon(this, g, x[z], y[z]);
                } else if (z != 0 && z % 2 == 0) {
                    body = new ImageIcon("body.png");
                    body.paintIcon(this, g, x[z], y[z]);
                } else if (z != 0 && z % 2 != 0) {
                    body2 = new ImageIcon("body2.png");
                    body2.paintIcon(this, g, x[z], y[z]);
                }
            }
        } else {

            gameOver(g);

        }

    }

    /**
     * Draw GameOver
     *
     * @param g
     */
    private void gameOver(Graphics g) {

        String endgame = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(endgame, (B_WIDTH - metr.stringWidth(endgame)) / 2, B_HEIGHT / 2);

        

    }

    /**
     * if Head position == apple position, snake get +1 bodypart
     */
    private void checkApple() {

        if ((x[0] == apple_x) && (y[0] == apple_y)) {

            dots++;
            locateApple();
        }
    }

    /**
     * Move the snake.
     */
    private void move() {

        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }

        if (leftDirection) {
            x[0] -= DOT_SIZE;
        }

        if (rightDirection) {
            x[0] += DOT_SIZE;
        }

        if (upDirection) {
            y[0] -= DOT_SIZE;
        }

        if (downDirection) {
            y[0] += DOT_SIZE;
        }
    }

    /**
     * Check for collision with snake or wall.
     */
    private void checkCollision() {

        //collision with Bodypart
        for (int z = dots; z > 0; z--) {

            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;

            }
        }

        //collision with wall
        if (y[0] >= B_HEIGHT) {
            inGame = false;
        }

        if (y[0] < 0) {
            inGame = false;
        }

        if (x[0] >= B_WIDTH) {
            inGame = false;
        }

        if (x[0] < 0) {
            inGame = false;
        }

        if (!inGame) {
            inGame = false;
        }
    }

    /**
     * Snake speed up (5 ms), if dots % 4 == 0
     */
    private void speedup() {

        if ((x[0] == apple_x) && (y[0] == apple_y) && dots % 4 == 0) {

            timer.setDelay((DELAY = DELAY - 10));

        }
    }

    /**
     * Random position for apple
     */
    private void locateApple() {

        for (int z = dots; z > 0; z--) {

            int r = (int) (Math.random() * RAND_POS);
            apple_x = ((r * DOT_SIZE));

            r = (int) (Math.random() * RAND_POS);
            apple_y = ((r * DOT_SIZE));

        }
    }

    /**
     * Start game, move Snake, check for Apple and Collision
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (inGame) {
            timer.start();
            checkApple();
            checkCollision();
            move();
            speedup();

        }

        repaint();

    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            //Restart the game.
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {

                timer.stop();
                if (inGame = true) {
                    DELAY = 200;
                    initGame();

                    rightDirection = true;
                    upDirection = false;
                    downDirection = false;
                    leftDirection = false;
                }
            }

            //pauses the game.
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                if (!setpause) {

                    timer.stop();
                    setpause = true;
                } else if (setpause) {
                    timer.start();
                    setpause = false;
                }
            }

            //Keyevent Snakedirection
            if ((key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) && (!rightDirection)) {
                leftDirection = true;

                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) && (!leftDirection)) {
                rightDirection = true;

                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_UP || key == KeyEvent.VK_W) && (!downDirection)) {
                upDirection = true;

                rightDirection = false;
                leftDirection = false;
            }

            if ((key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) && (!upDirection)) {
                downDirection = true;

                rightDirection = false;
                leftDirection = false;
            }
        }
    }
}
