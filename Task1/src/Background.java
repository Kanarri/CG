import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Background {
    public static BufferedImage createBackground(int w, int h) {
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();

        //полянка
        g.setColor(new Color(114, 201, 1));
        g.fillRect(0, 300, 800, 500);
        //небо
        g.setColor(new Color(119, 215, 252));
        g.fillRect(0, 0, 800, 300);

        Elka back_elka = new Elka(230, new Color(110, 67, 15), new Color(0, 116, 0));
        Elka front_elka = new Elka(230, new Color(120, 67, 21), new Color(0, 176, 0));
        Grass grass = new Grass(40, new Color(80, 150, 50));

        int x = 100;
        int y = 330;
        for (int i = 0; i < 3; i++){
            back_elka.draw(g, x, y);
            x+=250;
        }

        x = -20;
        y = 355;
        for (int i = 0; i < 4; i++){
            front_elka.draw(g, x, y);
            x+=250;
        }

        Random random = new Random();
        drawGrass(g, grass, 0, 360, 800, 500, random);

        g.dispose();
        return img;
    }
    private static void drawGrass(Graphics2D g, Grass grass, int x, int y, int w, int h, Random random) {
        for (int i = 0; i < 80; i++) {
            int gx = x + random.nextInt(w);
            int gy = y + random.nextInt(h);
            grass.draw(g, gx, gy);
        }
    }
}