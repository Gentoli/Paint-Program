package ca.utoronto.utm.paint;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// https://docs.oracle.com/javase/8/docs/api/java/awt/Graphics2D.html
// https://docs.oracle.com/javase/tutorial/2d/

class ShapeChooserPanel extends JPanel implements ActionListener {
	private View view; // So we can talk to our parent or other components of the view
	private JButton lastPressed;
	private Sides sides;
	private JButton[] shapeButtons;

	public ShapeChooserPanel(View view, PaintModel model) {
		this.view=view;
		//String[] buttonLabels = { "Selection", "Polyline", "Squiggle", "Polygon", "Triangle", "Rectangle", "Circle"};
		shapeButtons = new JButton[7];
		this.setLayout(new GridLayout(8, 1));
		this.setPreferredSize(new Dimension(90, 300));

		shapeButtons[0] = new JButton("Selection");
		this.add(shapeButtons[0]);
		shapeButtons[0].addActionListener(this);
		for (int index = 1; index < ShapeBuilder.getShapeCount(); index++) {
			shapeButtons[index] = new ShapeButton(ShapeBuilder.getShape(index));
			this.add(shapeButtons[index]);
			shapeButtons[index].addActionListener(this);
		}
		shapeButtons[5].setEnabled(false);
		lastPressed = shapeButtons[5];

		sides = new Sides(view);
		sides.setFocusable(true);
		sides.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.isControlDown()) {
					switch(e.getKeyCode()) {
						case 90:
							model.undo();
							break;
						case 89:
							model.redo();
							break;
					}
				}
			}
		});
		this.add(sides);
	}

	public JButton[] getShapeButtons() {
		return this.shapeButtons;
	}

	public JButton getLastPressed() {
		return this.lastPressed;
	}

	public void setLastPressed(JButton lastPressed) {
		this.lastPressed = lastPressed;
	}

	/**
	 * Controller aspect of this
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		((JButton) e.getSource()).setEnabled(false);
		lastPressed.setEnabled(true);
		lastPressed = (JButton) e.getSource();
		if(e.getSource() instanceof ShapeButton)
			sides.setValue(((ShapeButton)e.getSource()).getShapeNum());
		else
			sides.setValue(ShapeBuilder.MODIFY);
		view.requestFocus();
	}
}


class Sides extends JTextField implements ActionListener,KeyListener {

	private static final String TEXT_NOT_TO_TOUCH = "Edges: ";
	private View view;
	private int value = 4;
	public Sides(View view){
		super(TEXT_NOT_TO_TOUCH + "4",8);
		this.view = view;
		((AbstractDocument) getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
				if (offset < TEXT_NOT_TO_TOUCH.length()) {
					return;
				}
				super.insertString(fb, offset, string, attr);
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
				//System.out.println(text.length());
				//System.out.println(length);
				if (offset < TEXT_NOT_TO_TOUCH.length()) {
//					length = Math.max(0, length - TEXT_NOT_TO_TOUCH.length());
//					offset = TEXT_NOT_TO_TOUCH.length();
					length = Math.min(getText().length()-TEXT_NOT_TO_TOUCH.length(),length);
					offset = getText().length()-length;
				}
				super.replace(fb, offset, length, text, attrs);
				actionPerformed(null);
			}

			@Override
			public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {
				if (offset < TEXT_NOT_TO_TOUCH.length()) {
//					length = Math.max(0, length + offset - TEXT_NOT_TO_TOUCH.length());
//					offset = TEXT_NOT_TO_TOUCH.length();
					length = Math.min(getText().length()-TEXT_NOT_TO_TOUCH.length(),length);
					offset = getText().length()-length;
				}
				if (length > 0) {
					super.remove(fb, offset, length);
				}
				actionPerformed(null);
			}
		});
		addActionListener(this);
		addKeyListener(this);
	}
	public void setValue(int value){
		this.value = value;
		//setText(String.valueOf(Math.max(value,0)));
		setText(String.valueOf(value));
		view.getPaintPanel().setMode(value);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			int value = Integer.valueOf(getText().substring(7));
			if (value > 100) {
				value = 100;
			}
			if(value<0) {}
			else if (value < 3) {
				value = 3;
			}
			if (e != null) {
				setValue(value);
			} else {
				view.getPaintPanel().setMode(value);
				this.value = value;
			}
		} catch (Exception exception) {
			//exception.printStackTrace();
			if (e != null) {
				setValue(5);
				value = 5;
			}
		}
		int index = 0;
		if (value == -1)
			index = 2;
		else if (value == -2)
			index = 1;
		else if (value == -3)
			index = 0;
		else if (value == 3)
			index = 4;
		else if (value == 4)
			index = 5;
		else if (value >= 50)
			index = 6;
		else
			index = 3;
		System.out.println(index);
		view.getShapeChooserPanel().getLastPressed().setEnabled(true);
		view.getShapeChooserPanel().getShapeButtons()[index].setEnabled(false);
		view.getShapeChooserPanel().setLastPressed(view.getShapeChooserPanel().getShapeButtons()[index]);

	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (!Character.isDigit((e.getKeyChar()))) {
			e.consume();
		}
		String s = getText();
		if (s.length() >= 9) {
			e.consume();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
}