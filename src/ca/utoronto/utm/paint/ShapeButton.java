package ca.utoronto.utm.paint;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Graphics;

public class ShapeButton extends JButton {
    private Shape shape;
    private int shapeNum;
    private JPanel canvas;

    public ShapeButton(int shapeNum) {
        this.shapeNum = shapeNum==0?5:shapeNum;
        this.canvas = new JPanel(){
            @Override
            public void paint(Graphics g) {
                shape.setXEnd(canvas.getWidth());
                shape.setYEnd(canvas.getHeight());
                shape.print(g);
                g.dispose();
            }
        };
        shape = new ShapeBuilder(this.shapeNum, 0, 0).setFill(false).setRight(true).build();
        this.add(canvas);
    }

    public int getShapeNum() {
        return shapeNum;
    }
}
