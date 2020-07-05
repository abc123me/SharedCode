package net.net16.jeremiahlowe.shared;

import java.awt.*;

public class AWTGraphics extends GraphicsBackend {
	public final Graphics g;
	
	public AWTGraphics(Graphics g) {
		if(g == null)
			throw new NullPointerException("Graphics cannot be null!");
		this.g = g;
	}
	
	@Override public void drawImage(Image img, int x, int y, int w, int h) {
		g.drawImage(img, x, y, w, h, null);
	}
	@Override public void drawLine(int x1, int y1, int x2, int y2) {
		g.drawLine(x1, y1, x2, y2);
	}
	@Override public void drawPolyLine(int[] xp, int[] yp, int np) {
		g.drawPolyline(xp, yp, np);
	}
	@Override public void drawRect(int x, int y, int w, int h) {
		g.drawRect(x, y, w, h);
	}
	@Override public void drawRoundRect(int x, int y, int w, int h, int aw, int ah) {
		g.drawRoundRect(x, y, w, h, aw, ah);
	}
	@Override public void drawEllipse(int x, int y, int w, int h) {
		g.drawOval(x, y, w, h);
	}
	@Override public void drawPolygon(int[] xp, int[] yp, int np) {
		g.drawPolygon(xp, yp, np);
	}
	@Override public void drawArc(int x, int y, int w, int h, int sa, int aa) {
		g.drawArc(x, y, w, h, sa, aa);
	}
	@Override public void fillRect(int x, int y, int w, int h) {
		g.fillRect(x, y, w, h);
	}
	@Override public void fillEllipse(int x, int y, int w, int h) {
		g.fillOval(x, y, w, h);
	}
	@Override public void fillPolygon(int[] xp, int[] yp, int np) {
		g.fillPolygon(xp, yp, np);
	}
	@Override public void fillRoundRect(int x, int y, int w, int h, int aw, int ah) {
		g.fillRoundRect(x, y, w, h, aw, ah);
	}
	@Override public void fillArc(int x, int y, int w, int h, int sa, int aa) {
		g.fillArc(x, y, w, h, sa, aa);
	}
	@Override public void setColor(Color c) {
		g.setColor(c.toAwtColor());
	}
	@Override public void translate(int x, int y) {
		g.translate(x, y);
	}
	
	public void dispose() {
		g.dispose();
	}
}
