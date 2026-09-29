import java.awt.*;

public class Kolobok {

    private double x;
    private double y;
    private double size;
    private Color color;
    private boolean happy = false;

    public Kolobok(final double x, final double y, final int size, final Color color) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public double getSize() { return size; }

    public void move(double dx, double dy) {
        x += dx;
        y += dy;
    }

    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    public void setHappy(boolean happy) {
        this.happy = happy;
    }

    public void draw(Graphics gr) {
        Graphics2D g = (Graphics2D) gr;
        //тело
        g.setColor(color);
        g.fillOval((int)(x - size / 2.0), (int)(y - size / 2.0), (int) size, (int) size);

        g.setColor(Color.BLACK);
        g.drawOval((int)(x - size / 2.0), (int)(y - size / 2.0), (int) size, (int) size);
        //глаза белки
        g.setColor(Color.WHITE);
        g.fillOval((int)(x - size / 4.0), (int)(y - size / 4.0), (int) (size / 5.0), (int) (size / 5.0));
        g.fillOval((int)(x + size / 8.0), (int)(y - size / 4.0), (int) (size / 5.0), (int) (size / 5.0));

        g.setColor(Color.BLACK);
        g.drawOval((int)(x - size / 4.0), (int)(y - size / 4.0), (int) (size / 5), (int) (size / 5));
        g.drawOval((int)(x + size / 8.0), (int)(y - size / 4.0), (int) (size / 5), (int) (size / 5));
        //зрачки
        g.fillOval((int)(x - size / 5.0), (int)(y - size / 5.0), (int) (size / 12), (int) (size / 12));
        g.fillOval((int)(x + size / 6.0), (int)(y - size / 5.0), (int) (size / 12), (int) (size / 12));
        //рот
        if (happy) {
            g.drawArc((int)(x - size / 8.0), (int)(y + size / 8.0), (int) (size / 4), (int) (size / 6), 180, 180);
        } else {
            g.drawArc((int)(x - size / 8.0), (int)(y + size / 8.0), (int) (size / 4), (int) (size / 6), 0, 180);
        }
    }
}