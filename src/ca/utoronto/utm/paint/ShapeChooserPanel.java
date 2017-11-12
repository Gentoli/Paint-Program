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
class ShapeChooserPanel extends JPanel implements ActionListener {
	private ModeTextField modeTextField;
	private JButton[] shapeButtons;
	private static final String[] buttonLabels = { "Selection", "TextBox", "Polyline", "Squiggle", "Triangle", "Rectangle", "Circle", "Edges: 5"};
	private static final int[] buttonEdges = { 0,0,0,0,3,4,100,5};
	private PaintPanel paintPanel;

	public ShapeChooserPanel(PaintPanel paintPanel) {
		this.paintPanel=paintPanel;
		shapeButtons = new JButton[8];
		this.setLayout(new GridLayout(9, 1));
		this.setPreferredSize(new Dimension(105, 300));

		for (int index = 0; index < buttonLabels.length; index++) {
			shapeButtons[index] = new ShapeButton(index);
			shapeButtons[index].setFocusable(false);
			this.add(shapeButtons[index]);
			shapeButtons[index].addActionListener(this);
		}


		modeTextField = new ModeTextField(paintPanel);
		setActiveButton(activeButton);
		this.add(modeTextField);
	}

	private int activeButton = 7;

	public void setActiveButton(int activeButton) {
		shapeButtons[this.activeButton].setEnabled(true);
		this.activeButton = activeButton;
		shapeButtons[this.activeButton].setEnabled(false);
		paintPanel.setMode(activeButton);
		paintPanel.setEdges(buttonEdges[activeButton]);
		modeTextField.setEnabled(false);
		modeTextField.setText(buttonLabels[activeButton]);
		modeTextField.setEnabled(activeButton==7);
	}

	/**
	 * Controller aspect of this
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		setActiveButton(((ShapeButton) e.getSource()).getShapeNum());
		//modeTextField.setValue(num);
//		if (num <= 5) {
//			modeTextField.setEnabled(false);
//		}
//		else{
//			modeTextField.setEnabled(true);
//		}

		//		if(e.getSource() instanceof ShapeButton)
//
//			modeTextField.setValue(((ShapeButton)e.getSource()).getShapeNum());
//		else
//			modeTextField.setValue(3);
	}
}

