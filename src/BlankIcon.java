
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.Icon;

/**
 *Draws a empty icon in case an image is null
 */
public class BlankIcon implements Icon {

    final int width = 30;
    final int height = 30;

    final BasicStroke stroke = new BasicStroke(4);

    /**
     * Draws the icon at the specified location
     */
    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setColor(Color.WHITE);
        g2d.fillRect(x + 1, y + 1, width - 2, height - 2);

        g2d.setColor(Color.BLACK);
        g2d.drawRect(x + 1, y + 1, width - 2, height - 2);

        g2d.setColor(Color.RED);

        g2d.setStroke(stroke);
        g2d.drawLine(x + 10, y + 10, x + width - 10, y + height - 10);
        g2d.drawLine(x + 10, y + height - 10, x + width - 10, y + 10);

        g2d.dispose();
    }

    /**
     * @return the width of the icon
     */
    @Override
    public int getIconWidth() {
        return width;
    }

    /**
     * @return the height of the icon
     */
    @Override
    public int getIconHeight() {
        return height;
    }

}
