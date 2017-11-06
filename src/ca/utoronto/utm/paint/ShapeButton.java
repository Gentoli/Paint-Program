package ca.utoronto.utm.paint;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

public class ShapeButton extends JButton {
    private Shape shape;
    private int shapeNum;
    private Image image;

    public ShapeButton(int shapeNum) {
        this.shapeNum = shapeNum==0?5:shapeNum;
        try {
            image = ImageIO.read(new File(String.format("assets\\%d.png",shapeNum)));
            setIcon(new ImageIcon(image));
        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    public int getShapeNum() {
        return shapeNum;
    }
}
