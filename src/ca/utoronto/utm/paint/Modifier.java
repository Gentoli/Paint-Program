package ca.utoronto.utm.paint;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.image.BufferedImage;

/**
 * Actor for PaintPanel handles moving part of the screen
 */
public class Modifier extends PaintShape {
	private BufferedImage selection;
	private int width,height;
	private PaintModel model;
	private boolean border,move;
	private int lastX=0,lastY=0;
	private Rectangle r;

	public Modifier(int x, int y,PaintModel model) {
		super(x, y, Color.RED, 5 , false, 0);
		this.model = model;
		move=true;
		border=true;
	}

	@Override
	public void setColour(Color colour) {
	}

	@Override
	public void setStroke(Stroke stroke) {
	}

	@Override
	public void setCenter(boolean center) {
	}

	@Override
	public void setRight(boolean right) {
	}

	@Override
	public void print(Graphics2D g2) {

		if(selection!=null){
			g2.setColor(Color.WHITE);
			g2.fill(r);
			g2.drawImage(selection,x,y,null);
			if(border) {
				prepare(g2);
				g2.drawRect(x, y, width, height);
			}
		}else if(border) {
			prepare(g2);
			g2.drawRect(Math.min(x, xEnd), Math.min(y, yEnd), width,height);
		}

	}

	@Override
	public void setEnd(int x, int y) {
		if(!move)
			return;
		if(selection==null) {
			super.setEnd(x,y);
			width = Math.abs(getWidth());
			height = Math.abs(getHeight());
		}else{
			this.x+=x-lastX;
			this.y+=y-lastY;
			lastY=y;
			lastX=x;
		}
	}

	public void release(int x, int y) {
		if(contains(x,y)){
			setEnd(x,y);
		}
		if(selection==null) {
			this.x = Math.min(this.x, xEnd);
			this.y = Math.min(this.y, yEnd);
			BufferedImage image = new BufferedImage(model.getWidth(), model.getHeight(), 1);
			Graphics2D g = image.createGraphics();
			model.paint(g);
			g.dispose();
			r = new Rectangle(this.x,this.y,width,height);
			selection = image.getSubimage(this.x, this.y, width, height);
		}
		move=false;
	}

	public void setReleased(){
		border=false;
	}

	public void move(int x, int y) {
		move=true;
		lastY=y;
		lastX=x;
	}
}
