import javax.swing.*;
import java.awt.*;
import java.awt.font.GlyphVector;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.AffineTransform;

public class heartshape extends JPanel {
    int currentLetter = 0; 
    double t = 0;           
    String name = "Abinanthan";
    Path2D[] letterPaths;
    int[] letterPointsCount;
    boolean showCracker = false;
    int crackerSize = 0;

    public heartshape() {
        letterPaths = new Path2D[name.length()];
        letterPointsCount = new int[name.length()];
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, getWidth(), getHeight());

        g2.setColor(new Color(144, 238, 144)); // greencolor
        g2.setStroke(new BasicStroke(3));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Font font = new Font("Serif", Font.BOLD, 100);
        g2.setFont(font);

        int xOffset = 0;
        g2.translate(50, 250);

        for (int i = 0; i <= currentLetter && i < name.length(); i++) {
            char c = name.charAt(i);

            if (letterPaths[i] == null) {
                GlyphVector gv = font.createGlyphVector(g2.getFontRenderContext(), new char[]{c});
                Path2D letterPath = (Path2D) gv.getOutline();
                letterPath.transform(AffineTransform.getTranslateInstance(xOffset, 0));
                letterPaths[i] = letterPath;

                // count points
                PathIterator it = letterPath.getPathIterator(null, 0.5);
                int count = 0;
                double[] coords = new double[6];
                while (!it.isDone()) {
                    it.currentSegment(coords);
                    count++;
                    it.next();
                }
                letterPointsCount[i] = count;
            }

            PathIterator it = letterPaths[i].getPathIterator(null, 0.5);
            double[] coords = new double[6];
            double drawn = 0;
            double prevX = 0, prevY = 0;
            boolean first = true;

            double maxT = (i == currentLetter) ? t : letterPointsCount[i];

            while (!it.isDone() && drawn < maxT) {
                int type = it.currentSegment(coords);
                double x = coords[0];
                double y = coords[1];

                if (!first) {
                    g2.drawLine((int) prevX, (int) prevY, (int) x, (int) y);
                } else {
                    first = false;
                }

                prevX = x;
                prevY = y;
                drawn++;
                it.next();
            }

            xOffset += g2.getFontMetrics().charWidth(c) + 20;
        }

        if (showCracker) {
            g2.setColor(Color.ORANGE);
            g2.setStroke(new BasicStroke(2));

            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;
            for (int i = 0; i < 12; i++) {
                double angle = 2 * Math.PI / 12 * i;
                int x = (int) (centerX + crackerSize * Math.cos(angle));
                int y = (int) (centerY + crackerSize * Math.sin(angle));
                g2.drawLine(centerX, centerY, x, y);
            }

            if (crackerSize < 100) {
                crackerSize++;
            }
        }
    }
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Animated Name with Cracker");
        heartshape panel = new heartshape();

        frame.add(panel);
        frame.setSize(700, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        while (true) {
            if (panel.currentLetter < panel.name.length()) {
                panel.t += 2; // speed of drawing

                if (panel.t >= panel.letterPointsCount[panel.currentLetter]) {
                    panel.t = 0;
                    panel.currentLetter++;
                }
            } else {
                panel.showCracker = true; 
            }
            panel.repaint();
            Thread.sleep(100);
        }
    }

}
