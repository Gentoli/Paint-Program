package ca.utoronto.utm.paint;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

/**
 * ShapeButton is a JButton that holds an image corresponding to what it draws
 */
public class ShapeButton extends JButton {
    private int shapeNum;
    private Image image;

    /**
     * Creates an instance of ShapeButton, using shapeNum to choose the right image to draw on itself.
     * @param shapeNum number corresponding to it's shape
     */
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
