package ca.utoronto.utm.paint;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// https://docs.oracle.com/javase/8/docs/api/java/awt/Graphics2D.html
// https://docs.oracle.com/javase/tutorial/2d/

class ShapeChooserPanel extends JPanel implements ActionListener {
	private View view; // So we can talk to our parent or other components of the view
	private JButton lastPressed;
	private static final String TEXT_NOT_TO_TOUCH = "Sides: ";
	public ShapeChooserPanel(View view) {
		this.view=view;
		String[] buttonLabels = { "Circle", "Rectangle", "Square", "Squiggle", "Polyline", "Polygon"};
		this.setLayout(new GridLayout(buttonLabels.length + 1, 1));

		for (String label : buttonLabels) {
			JButton button = new JButton(label);
			this.add(button);
			button.addActionListener(this);
		}

		JTextField sides = new JTextField(TEXT_NOT_TO_TOUCH + "5",8);
		((AbstractDocument) sides.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
				if (offset < TEXT_NOT_TO_TOUCH.length()) {
					return;
				}
				super.insertString(fb, offset, string, attr);
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
				if (offset < TEXT_NOT_TO_TOUCH.length()) {
					length = Math.max(0, length - TEXT_NOT_TO_TOUCH.length());
					offset = TEXT_NOT_TO_TOUCH.length();
				}
				super.replace(fb, offset, length, text, attrs);
			}

			@Override
			public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {
				if (offset < TEXT_NOT_TO_TOUCH.length()) {
					length = Math.max(0, length + offset - TEXT_NOT_TO_TOUCH.length());
					offset = TEXT_NOT_TO_TOUCH.length();
				}
				if (length > 0) {
					super.remove(fb, offset, length);
				}
			}
		});
		sides.addActionListener(e -> {
			try {
				int value = Integer.valueOf(((JTextField)e.getSource()).getText());
				if (value > 100) {
					value = 100;
				}
				if (value < 1) {
					value = 3;
				}
				view.getPaintPanel().setEdges(Integer.valueOf(((JTextField)e.getSource()).getText()));
			}
			catch(Exception exception) {
				((JTextField)e.getSource()).setText("5");
				view.getPaintPanel().setEdges(5);
			}
		});
		sides.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				String s = sides.getText();
				if (s.length() >= 10) {
					e.consume();
				}
			}
		});
		this.add(sides);
	}



	/**
	 * Controller aspect of this
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		this.view.getPaintPanel().setMode(e.getActionCommand());
		((JButton) e.getSource()).setEnabled(false);
		if (lastPressed != null)
			lastPressed.setEnabled(true);
		lastPressed = (JButton) e.getSource();
		System.out.println(e.getActionCommand());
	}

	
}

