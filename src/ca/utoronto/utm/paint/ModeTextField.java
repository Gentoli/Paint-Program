package ca.utoronto.utm.paint;

import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * ModeTextField is the JTextField that denotes the number of edges of the shape selected.
 * Restricts the inputs that it can take.
 */
class ModeTextField extends JTextField implements ActionListener,KeyListener {

	private static final String TEXT_NOT_TO_TOUCH = "Edges: ";
	private PaintPanel paintPanel;
	private int value = 6;
	public ModeTextField(PaintPanel paintPanel){
		super(TEXT_NOT_TO_TOUCH + "6",10);
		this.paintPanel = paintPanel;
		((AbstractDocument) getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
				super.insertString(fb, offset, string, attr);
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
				if (isEnabled()) {
					if (offset < TEXT_NOT_TO_TOUCH.length()) {
						length = Math.min(getText().length() - TEXT_NOT_TO_TOUCH.length(), length);
						offset = getText().length() - length;
					}
				}
				System.out.println(text);
				super.replace(fb, offset, length, text, attrs);
				actionPerformed(null);
			}

			@Override
			public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
				System.out.println("remove");
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
		paintPanel.setMode(6);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(!isEnabled())
			return;
		try {
			int value = Integer.valueOf(getText().substring(7));
			if (value > 100) {
				value = 100;
			}
			if (value < 3) {
				value = 3;
			}
			if (e != null) {
				setValue(value);
			} else {
				this.value = value;
			}
		} catch (Exception exception) {
			//exception.printStackTrace();
			if (e != null) {
				setValue(6);
			}
		}
		paintPanel.setEdges(value);

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
