import java.awt.*;

public class Grass {
    private int size;
    private Color color;

    public Grass(int size, Color color) {
        this.size = size;
        this.color = color;
    }

    public int getSize() { return size; }
    public Color getColor() { return color; }

    public void draw(Graphics gr, int x, int y) {
        Graphics2D g = (Graphics2D) gr;
        g.setColor(color);
        g.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        g.drawLine(x, y, x,y - size);
        g.drawLine(x, y, (int) (x - size / 2.8), (int) (y - size * 3 / 3.8));
        g.drawLine(x, y, (int) (x + size / 2.8), (int) (y - size * 3 / 3.8));
        g.drawLine(x, y,x - size / 2,y - size / 2);
        g.drawLine(x, y,x + size / 2,y - size / 2);
    }
}