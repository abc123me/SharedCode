package net.net16.jeremiahlowe.shared;

import java.awt.Image;

public abstract class GraphicsBackend {
	public abstract void drawImage(Image img, int x, int y, int w, int h);
	public abstract void drawLine(int x1, int y1, int x2, int y2);
	public abstract void drawPolyLine(int[] xp, int[] yp, int np);
	
	public abstract void drawRect(int x, int y, int w, int h);
	public abstract void drawRoundRect(int x, int y, int w, int h, int aw, int ah);
	public abstract void drawEllipse(int x, int y, int w, int h);
	public abstract void drawPolygon(int[] xp, int[] yp, int np);
	public abstract void drawArc(int x, int y, int w, int h, int sa, int aa);
	
	public abstract void fillRect(int x, int y, int w, int h);
	public abstract void fillEllipse(int x, int y, int w, int h);
	public abstract void fillPolygon(int[] xp, int[] yp, int np);
	public abstract void fillRoundRect(int x, int y, int w, int h, int aw, int ah);
	public abstract void fillArc(int x, int y, int w, int h, int sa, int aa);
	
	public abstract void setColor(Color color);
	public abstract void translate(int x, int y);
}
