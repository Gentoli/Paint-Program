package ca.utoronto.utm.paint;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ShapeButton extends JButton {
    private Shape shape;
    private int shapeNum;
    private JPanel canvas;
    public ShapeButton(int shapeNum) {
        this.shapeNum = shapeNum==0?5:shapeNum;

        this.canvas = new JPanel(){
            @Override
            public void paint(Graphics g) {
                shape.setXEnd(canvas.getWidth()-1);
                shape.setYEnd(canvas.getHeight()-1);
//                shape.print(g);
                g.dispose();
            }
        };
        shape = new ShapeBuilder(this.shapeNum, 1, 1).setFill(false).setRight(true).build();
        this.add(canvas);
    }

    public int getShapeNum() {
        return shapeNum;
    }
}
