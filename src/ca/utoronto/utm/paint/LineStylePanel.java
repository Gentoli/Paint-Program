package ca.utoronto.utm.paint;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Graphics;

public class LineStylePanel extends JPanel {
    private Shape shape;
    private int styleNum;
    private JPanel canvas;

    public LineStylePanel(int styleNum) {
        this.styleNum = styleNum;
        this.canvas = new JPanel(){
            @Override
            public void paint(Graphics g) {
                shape.setXEnd(canvas.getWidth()-1);
                shape.setYEnd(canvas.getHeight()-1);
                shape.print(g);
                g.dispose();
            }
        };
        shape = new ShapeBuilder(-1, 1, 1).setFill(false).setRight(true).build();
        this.add(canvas);
    }

    public int getstyleNum() {
        return styleNum;
    }
}
