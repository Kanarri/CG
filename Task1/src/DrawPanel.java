import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

public class DrawPanel extends JPanel implements ActionListener {

    private final int PANEL_WIDTH;
    private final int PANEL_HEIGHT;

    private Timer timer;
    private Timer happyTimer;

    private Kolobok kolobok;
    private Food food;
    private BufferedImage background;

    private boolean autopilot = false;
    private double autovx = 0;
    private double autovy = 0;

    private static final double SPEED = 8.0;
    private static final int FOOD_SIZE = 60;
    private static final int HAPPY_TIME = 1000;
    private static final int GRASS_TOP = 350;

    private boolean left, right, up, down;

    private Random random = new Random();

    public DrawPanel(final int width, final int height, final int timerDelay) {
        this.PANEL_WIDTH = width;
        this.PANEL_HEIGHT = height;
        this.background = Background.createBackground(width, height);

        setPreferredSize(new Dimension(width, height));
        setBackground(Color.DARK_GRAY);
        setFocusable(true);

        this.kolobok = new Kolobok(width / 2.0,height / 2.0,110, new Color(255, 210, 14));

        spawnFood();

        timer = new Timer(timerDelay, this);
        timer.start();

        happyTimer = new Timer(HAPPY_TIME, e -> {
            kolobok.setHappy(false);
            repaint();
        });
        happyTimer.setRepeats(false);

        addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT: autopilot = false; left = true; break;
                    case KeyEvent.VK_RIGHT: autopilot = false; right = true; break;
                    case KeyEvent.VK_UP: autopilot = false; up = true; break;
                    case KeyEvent.VK_DOWN: autopilot = false; down = true; break;

                    case KeyEvent.VK_SPACE:
                        double vx = 0, vy = 0;
                        if (left) vx -= 1;
                        if (right) vx += 1;
                        if (up) vy -= 1;
                        if (down)  vy += 1;

                        if (vx != 0 || vy != 0) {
                            autovx = vx;
                            autovy = vy;
                            autopilot = !autopilot;
                        }
                        break;
                }
            }
            @Override public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT: left = false; break;
                    case KeyEvent.VK_RIGHT: right = false; break;
                    case KeyEvent.VK_UP: up = false; break;
                    case KeyEvent.VK_DOWN: down = false; break;
                }
            }
        });
    }

    @Override
    public void paint(final Graphics gr) {
        super.paint(gr);
        Graphics2D g = (Graphics2D) gr;

        g.drawImage(background, 0, 0, null);

        if (food != null) food.draw(gr);
        kolobok.draw(gr);
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        if (e.getSource() == timer) {
            update();
            repaint();
        }
    }

    private void update() {
        double vx, vy;
        if (autopilot) {
            vx = autovx;
            vy = autovy;
        } else {
            vx = 0;
            vy = 0;
            if (left) vx -= 1;
            if (right) vx += 1;
            if (up) vy -= 1;
            if (down) vy += 1;

            if (vx != 0 && vy != 0) {
                vx *= 0.7;
                vy *= 0.7;
            }
        }

        kolobok.move(vx * SPEED,vy * SPEED);

        int r = (int) (kolobok.getSize() / 2);

        if (kolobok.getX() < r) {
            kolobok.setX(r);
            if (autopilot) autovx = -autovx;
        }
        if (kolobok.getX() > PANEL_WIDTH - r) {
            kolobok.setX(PANEL_WIDTH - r);
            if (autopilot) autovx = -autovx;
        }
        if (kolobok.getY() < GRASS_TOP) {
            kolobok.setY(GRASS_TOP);
            if (autopilot) autovy = -autovy;
        }
        if (kolobok.getY() > PANEL_HEIGHT - r) {
            kolobok.setY(PANEL_HEIGHT - r);
            if (autopilot) autovy = -autovy;
        }

        if (food != null && collides()) {
            spawnFood();
            kolobok.setHappy(true);
            kolobok.setSize((int)(kolobok.getSize() * 1.1));
            happyTimer.restart();
        }

        if (food != null && collides()) {
            spawnFood();
            kolobok.setHappy(true);
            kolobok.setSize((int) (kolobok.getSize() * 1.1));
            happyTimer.restart();
        }
    }

    private boolean collides() {
        double dx = kolobok.getX() - food.getX();
        double dy = kolobok.getY() - food.getY();
        double minDist = kolobok.getSize() / 2.0 + food.getSize() / 2.0;
        return dx * dx + dy * dy <= minDist * minDist;
    }

    private void spawnFood() {
        int r = FOOD_SIZE / 2;

        int x, y;
        do {
            x = r + random.nextInt(PANEL_WIDTH  - FOOD_SIZE + 1);
            y = GRASS_TOP + random.nextInt(PANEL_HEIGHT - GRASS_TOP - FOOD_SIZE + 1);
        } while (isUnderPlayer(x, y, r));

        food = new Food(x, y, FOOD_SIZE, Color.RED);
    }

    private boolean isUnderPlayer(double fx, double fy, double foodR) {
        double dx = kolobok.getX() - fx;
        double dy = kolobok.getY() - fy;
        double minDist = kolobok.getSize() / 2.0 + foodR;
        return dx * dx + dy * dy < minDist * minDist;
    }
}