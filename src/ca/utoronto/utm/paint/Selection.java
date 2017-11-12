package ca.utoronto.utm.paint;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.beans.Transient;

/**
 * Actor for PaintPanel handles moving part of the screen
 */
public class Selection extends PaintShape {
	private BufferedImage selection;
	private boolean border;
	private int lastX=0,lastY=0;
	private Rectangle r;
	private Rectangle fillRect;

	public Selection(int x, int y) {
		super(x, y, Color.RED, 5 , false, 0);
		border=true;
		r=new Rectangle(x,y,0,0);
	}

	@Override
	public void print(Graphics2D g2) {
		if(selection!=null){
			g2.setColor(Color.WHITE);
			g2.fill(fillRect);
			g2.drawImage(selection,x,y,null);
			if(border) {
				prepare(g2);
				g2.draw(r);
			}
		}else if(border) {
			prepare(g2);
			g2.draw(r);
		}

	}

	@Override
	public void mouseMoved(int x, int y) {
		if(selection==null) {
			xEnd=x;
			yEnd=y;
			r.width = Math.abs(xEnd-this.x);
			r.height = Math.abs(yEnd-this.y);
			r.x = Math.min(this.x, xEnd);
			r.y = Math.min(this.y, yEnd);
		}else{
			r.x = this.x+=x-lastX;
			r.y = this.y+=y-lastY;
			lastY=y;
			lastX=x;
		}
	}

	public void release(int x, int y,PaintModel model) {
		if(selection==null) {
			r.x = this.x = Math.min(this.x, xEnd);
			r.y = this.y = Math.min(this.y, yEnd);
			BufferedImage image = new BufferedImage(model.getWidth(), model.getHeight(), 1);
			Graphics2D g = image.createGraphics();
			model.paint(g);
			g.dispose();
			fillRect = new Rectangle(r);
			selection = image.getSubimage(r.x,r.y,r.width,r.height);
		}
	}

	public void setReleased(){
		border=false;
	}

	public void setMove(int x, int y) {
		lastX=x;
		lastY=y;
	}

	@Override
	@Transient
	public Rectangle getBounds() {
		return r.getBounds();
	}

	@Override
	public Rectangle2D getBounds2D() {
		return r.getBounds2D();
	}

	public boolean contains(int x, int y) {
		return r.contains(x, y);
	}

	@Override
	public boolean contains(double x, double y) {
		return r.contains(x, y);
	}

	@Override
	public boolean intersects(double x, double y, double w, double h) {
		return r.intersects(x, y, w, h);
	}

	@Override
	public boolean contains(double x, double y, double w, double h) {
		return r.contains(x, y, w, h);
	}

	@Override
	public PathIterator getPathIterator(AffineTransform at) {
		return r.getPathIterator(at);
	}

	@Override
	public PathIterator getPathIterator(AffineTransform at, double flatness) {
		return r.getPathIterator(at, flatness);
	}

	@Override
	public boolean contains(Point2D p) {
		return r.contains(p);
	}

	@Override
	public boolean intersects(Rectangle2D r) {
		return this.r.intersects(r);
	}

	@Override
	public boolean contains(Rectangle2D r) {
		return this.r.contains(r);
	}
}
