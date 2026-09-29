import java.awt.*;

public class Food {

    private int x;
    private int y;
    private int size;
    private Color color = Color.ORANGE;

    public Food(final int x, final int y, final int size, final Color color) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public int getSize() { return size; }

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

    public void draw(Graphics gr) {
        Graphics2D g = (Graphics2D) gr;
        //ножка
        g.setColor(Color.WHITE);
        g.fillOval((int)(x - size / 4.0), (int)(y - size / 4.0), size / 2, size);

        g.setColor(Color.BLACK);
        g.drawOval((int)(x - size / 4.0), (int)(y - size / 4.0), size / 2, size);
        //шляпка
        g.setColor(Color.RED);
        g.fillArc((int) (x - size / 2.0), (int) (y - size / 2.0), size, size, 0, 180);

        g.setColor(Color.BLACK);
        g.drawArc((int) (x - size / 2.0), (int) (y - size / 2.0), size, size, 0, 180);
        g.drawLine((int) (x - size / 2.0), y, (int) (x + size / 2.0), y);
        // точки на шляпе
        g.setColor(Color.WHITE);
        int ds = size / 6;
        g.fillOval((int)(x - size / 3.0 - ds / 2.0), (int)(y - size / 6.0 - ds / 2.0), ds, ds);
        g.fillOval((int)(x - ds / 2.0), (int)(y - size / 2.0 + ds / 2.0), ds, ds);
        g.fillOval((int)(x + size / 3.0 - ds / 2.0), (int)(y - size / 6.0 - ds / 2.0), ds, ds);

        g.setColor(Color.BLACK);
        g.drawOval((int)(x - size / 3.0 - ds / 2.0), (int)(y - size / 6.0 - ds / 2.0), ds, ds);
        g.drawOval((int)(x - ds / 2.0), (int)(y - size / 2.0 + ds / 2.0), ds, ds);
        g.drawOval((int)(x + size / 3.0 - ds / 2.0), (int)(y - size / 6.0 - ds / 2.0), ds, ds);
        //хитбокс
//        g.setColor(Color.BLACK);
//        g.drawOval((int)(x - size / 2.0), (int)(y - size / 2.0), size, size);
    }
}