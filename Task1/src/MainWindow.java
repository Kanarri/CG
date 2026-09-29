import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private final DrawPanel panel;

    public MainWindow() throws HeadlessException {
        panel = new DrawPanel(800, 800, 16);
        this.add(panel);
        this.setResizable(false);
    }
}
