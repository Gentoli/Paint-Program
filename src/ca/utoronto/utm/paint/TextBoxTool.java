package ca.utoronto.utm.paint;

import ca.utoronto.utm.pointer.ModifierEvent;
import ca.utoronto.utm.pointer.PointerEvent;
import javafx.scene.control.TextFormatter;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.Style;
import java.awt.event.MouseEvent;

/**
 * The TextBoxTool builds TextBox PaintShapes depending on the parameters in the StylePanel and handles mouse events.
 */
public class TextBoxTool implements ITool, ChangeListener {

    private TextBoxDialog textBoxDialog;
    private PaintShape[] shapes;
    private StylePanel stylePanel;
    private int fontStyle;

    public TextBoxTool(TextBoxDialog textBoxDialog, StylePanel stylePanel, PaintShape[] shapes) {
        this.textBoxDialog = textBoxDialog;
        this.stylePanel = stylePanel;
        this.shapes = shapes;
    }


    @Override
    public void stateChanged(ChangeEvent e) {

    }

    @Override
    public Drawable deselect() {
        textBoxDialog.setVisible(false);
        return null;
    }

    @Override
    public void selected() {
        textBoxDialog.setVisible(true);
    }

    @Override
    public Drawable handlePointerUpdate(PointerEvent e) {
        Drawable rtn = null;
        switch(e.getID()){
            case MouseEvent.MOUSE_PRESSED:
                this.fontStyle = (textBoxDialog.getBoldCheck().isSelected()?1:0) + (textBoxDialog.getItalicCheck().isSelected()?2:0);
                System.out.println(fontStyle);
                shapes[e.getPointerId()]=new TextBox(e.getX(),e.getY(),stylePanel.getColour(),
                        Integer.valueOf(textBoxDialog.getFontSizeChooser().getSelectedItem().toString()),
                        textBoxDialog.getFontChooser().getSelectedItem().toString(),
                        textBoxDialog.getTextField().getText(), fontStyle);
                break;
            case MouseEvent.MOUSE_MOVED:
                if(shapes[e.getPointerId()]!=null) {
                    shapes[e.getPointerId()].mouseMoved(e.getX(), e.getY());
                }
                break;
            case MouseEvent.MOUSE_RELEASED:
                shapes[e.getPointerId()].mouseMoved(e.getX(),e.getY());
                rtn =  shapes[e.getPointerId()];
                shapes[e.getPointerId()]=null;
        }
        return rtn;
    }

    @Override
    public void handleModifierUpdated(ModifierEvent e) {

    }
}
