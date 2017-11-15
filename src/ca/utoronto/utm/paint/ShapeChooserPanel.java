package ca.utoronto.utm.paint;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// https://docs.oracle.com/javase/8/docs/api/java/awt/Graphics2D.html
// https://docs.oracle.com/javase/tutorial/2d/

/**
 * ShapeChooserPanel holds the buttons that select the mode of the paint app.
 */
public class ShapeChooserPanel extends JPanel implements ActionListener {
	private static final String[] BUTTON_ASSETS = {"Selection", "Eraser", "TextBox", "Polyline", "Squiggle", "ConcavePolygon", "Triangle", "Rectangle", "Circle", "Polygon"};
	private static final String[] BUTTON_LABELS = {"Selection", "Eraser", "TextBox", "Polyline", "Squiggle", "Edges: 5", "Triangle", "Rectangle", "Circle", "Edges: 5"};
	private static final int[] BUTTON_EDGES = {0, 0, 0, 0, 0, 5, 3, 4, 100, 5};
	public static final int WIDTH = 105;
	private ModeTextField modeTextField;
	private JButton[] shapeButtons;
	private PaintPanel paintPanel;
	private int activeButton = 7;

	public ShapeChooserPanel(PaintPanel paintPanel) {
		this.paintPanel = paintPanel;
		shapeButtons = new JButton[BUTTON_LABELS.length];
		setLayout(new GridLayout(BUTTON_LABELS.length + 1, 1));
		setPreferredSize(new Dimension(WIDTH, 300));

		for(int index = 0; index < BUTTON_LABELS.length; index++) {
			shapeButtons[index] = new ShapeButton(index, BUTTON_ASSETS[index]);
			shapeButtons[index].setFocusable(false);
			add(shapeButtons[index]);
			shapeButtons[index].addActionListener(this);
		}


		modeTextField = new ModeTextField(paintPanel);
		setActiveButton(activeButton);
		add(modeTextField);
	}

	private void setActiveButton(int activeButton) {
		shapeButtons[this.activeButton].setEnabled(true);
		this.activeButton = activeButton;
		shapeButtons[this.activeButton].setEnabled(false);
		paintPanel.setMode(activeButton);
		paintPanel.setEdges(BUTTON_EDGES[activeButton]);
		modeTextField.setEnabled(false);
		modeTextField.setText(BUTTON_LABELS[activeButton]);
		modeTextField.setEnabled(BUTTON_EDGES[activeButton] == 5);
	}

	/**
	 * Controller aspect of this
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		setActiveButton(((ShapeButton) e.getSource()).getShapeNum());
	}
}

