import java.awt.*;

public class Elka {
    private int size;//высота всей елки
    private Color trunkColor;
    private Color treeColor;

    public Elka(int size, Color trunkColor, Color treeColor) {
        this.size = size;
        this.trunkColor = trunkColor;
        this.treeColor = treeColor;
    }

    public int getSize() { return size; }
    public Color getTrunkColor() { return trunkColor; }
    public Color getTreeColor() { return treeColor; }

    //x y нижний левый угол ствола
    public void draw(Graphics gr, int x, int y) {
        Graphics2D g = (Graphics2D) gr;
        int tw = size / 5;
        int th = size / 3;
        int w = size;
        int h = (int) (size / 1.7);

        //ствол
        g.setColor(trunkColor);
        g.fillRect(x, y - th, tw, th);
        //треугольники
        g.setColor(treeColor);
        int baseY = y - th;
        int cx = x + tw / 2;
        for (int i = 0; i < 3; i++) {
            int[] xs = {cx - w / 2, cx, cx + w / 2};
            int[] ys = {baseY, baseY - h, baseY};
            g.fillPolygon(xs, ys, 3);
            baseY -= h - h / 4;
        }
    }
}