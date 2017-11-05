package ca.utoronto.utm.paint;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// https://docs.oracle.com/javase/8/docs/api/java/awt/Graphics2D.html
// https://docs.oracle.com/javase/tutorial/2d/

class ShapeChooserPanel extends JPanel implements ActionListener {
	private View view; // So we can talk to our parent or other components of the view
	private JButton lastPressed;
	private Sides sides;

	public ShapeChooserPanel(View view) {
		this.view=view;
		String[] buttonLabels = { "Selection", "Polyline", "Squiggle", "Polygon", "Rectangle", "Circle"};
		this.setLayout(new GridLayout(buttonLabels.length + 1, 1));

		for (int index = 0; index < ShapeBuilder.getShapeCount(); index++) {
			JButton button = new ShapeButton(ShapeBuilder.getShape(index));
			this.add(button);
			button.addActionListener(this);
		}

		sides = new Sides(view);

		this.add(sides);
	}

	/**
	 * Controller aspect of this
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		((JButton) e.getSource()).setEnabled(false);
		if (lastPressed != null)
			lastPressed.setEnabled(true);
		lastPressed = (JButton) e.getSource();
		sides.setValue(((ShapeButton)e.getSource()).getShapeNum());
	}


}

class Sides extends JTextField implements ActionListener,KeyListener {

	private static final String TEXT_NOT_TO_TOUCH = "Sides: ";
	private View view;
	private int value = 5;
	public Sides(View view){
		super(TEXT_NOT_TO_TOUCH + "5",8);
		this.view = view;
		((AbstractDocument) getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
				System.out.println("inc");
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
		setText(String.valueOf(Math.max(value,0)));
		view.getPaintPanel().setMode(value);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//System.out.println("act");
		try {
			int value = Integer.valueOf(getText().substring(7));
			if (value > 100) {
				value = 100;
			}
			if (value < 1) {
				value = 3;
			}
			if(e!=null) {
				setValue(value);
			}else {
				view.getPaintPanel().setMode(value);
				this.value=value;
			}
		}
		catch(Exception exception) {
			//exception.printStackTrace();
			if(e!=null)
			setValue(5);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		String s = getText();
		if (s.length() >= 10) {
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