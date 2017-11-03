package ca.utoronto.utm.paint;

import java.awt.*;

public interface PointerListenser {
	public Component getComponent();
	public void pointerDown();
	public void pointerUp();
	public void pointerMoved();
}
