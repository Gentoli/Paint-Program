package ca.utoronto.utm.paint;

import java.awt.Color;
import java.awt.Graphics;

public class ClearMask implements Drawable {

	private int width, height;

	public ClearMask(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void print(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0,0,width, height);
	}
}
