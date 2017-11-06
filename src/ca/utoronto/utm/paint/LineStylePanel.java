package ca.utoronto.utm.paint;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class LineStylePanel extends JLabel {
    private Shape shape;
    private int styleNum;

    public LineStylePanel(int styleNum) {
        this.styleNum = styleNum;
        this.setPreferredSize(new Dimension(30, 100));
        //shape = new ShapeBuilder(-1, 1, 1).setFill(false).setRight(true).setStrokeStyle(styleNum).build();
        this.repaint();
    }

    @Override
    public void paint(Graphics g) {
//        shape.setXEnd(this.getWidth()-1);
//        shape.setYEnd(this.getHeight()-1);
//        shape.print(g);
//        g.dispose();
        Graphics2D g2 = (Graphics2D)g;
        int x = 1;
        int y = (this.getHeight()/2);
        int xEnd = this.getWidth()-1;
        g2.setStroke((this.styleNum == 0)? new BasicStroke(5): new BasicStroke(5,0,0,10.0f, new float[] {16.0f,20.0f},0.0f));
        g2.drawLine(x,y,xEnd,y);

    }

    public int getstyleNum() {
        return styleNum;
    }
}
