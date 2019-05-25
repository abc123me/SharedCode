package net.net16.jeremiahlowe.shared.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ScrollingGraph extends JPanel {
	private float xEnd = 30, yEnd = 5, xIncrement = 0.25f;
	private List<ScrollablePoint> points = new ArrayList<ScrollablePoint>();
	private List<ScrollablePoint> toRemove = new ArrayList<ScrollablePoint>();
	private Color lineColor = Color.RED;
	private float lastX = 0;
	@Override
	public void paintComponent(Graphics ug){
		int w = getWidth(), h = getHeight();
		BufferedImage buffer = new BufferedImage(w, h, ColorModel.OPAQUE);
		Graphics g = buffer.getGraphics();
		g.setColor(getBackground());
		g.fillRect(0, 0, w, h);
		g.setColor(lineColor);
		for(int i = 1; i < points.size(); i++){
			ScrollablePoint spl = points.get(i - 1), sp = points.get(i);
			int x0 = castXPixels(spl.currentX);
			int y0 = castYPixels(spl.currentY);
			int x1 = castXPixels(sp.currentX);
			int y1 = castYPixels(sp.currentY);
			drawLineWithWidth(g, x0, y0, x1, y1, 15);
			spl = sp;
		}
		ug.drawImage(buffer, 0, 0, w, h, null);
	}
	private static void drawLineWithWidth(Graphics g, int x1, int y1, int x2, int y2, int w) {
		int w2 = w / 2;
		for(int i = -w2; i < w2 + 1; i++)
			g.drawLine(x1 + i, y1, x2 + i, y2);
	}
	public int castXPixels(float valX){return Math.round((getWidth() / xEnd) * valX);}
	public int castYPixels(float valY){return getHeight() - Math.round((getHeight() / yEnd) * valY);}
	public void addPointAutoScroll(float y){addPointAndScroll(y, -xIncrement);}
	public void addPointAndScroll(float y, float scrollAmount){
		float x = lastX - scrollAmount;
		points.add(new ScrollablePoint(lastX, y));
		float max = xEnd - scrollAmount;
		if(x > max) scroll(scrollAmount);
		else cleanPointLists();
		lastX = (x > max ? max : x);
	}
	public void addPointNoScroll(float x, float y){
		points.add(new ScrollablePoint(x, y));
		lastX = x;
		cleanPointLists();
	}
	private void scroll(float amount){
		for(ScrollablePoint sp : points) sp.currentX += amount;
		cleanPointLists();
	}
	public void cleanPointLists(){
		for(ScrollablePoint sp : points) if(sp.currentX > xEnd) toRemove.add(sp);
		for(ScrollablePoint sp : toRemove) points.remove(sp);
		toRemove.clear();
		points.removeIf(new Predicate<ScrollablePoint>(){
			@Override
			public boolean test(ScrollablePoint sp) {
				if(sp == null) return true;
				else return false;
			}
		});
		Collections.sort(points);
		repaint();
	}
	public float getGraphWidth(){return xEnd;}
	public float getGraphHeight(){return yEnd;}
	public void autoSetXInc(){
		xIncrement = xEnd / getWidth();
	}
	public void setGraphSize(float w, float h){xEnd = w; yEnd = h; autoSetXInc();}
	public void setXIncrement(float xIncrement){this.xIncrement = xIncrement;}
	public float getXIncrement(){return xIncrement;}
	public Color getLineColor(){return lineColor;}
	public void setLineColor(Color lineColor){this.lineColor = lineColor;}
	
	public static void main(String[] args){
		JFrame j = new JFrame("Testing wrapper");
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		j.setBounds(100, 100, 400, 300);
		ScrollingGraph sg = new ScrollingGraph();
		j.setContentPane(sg);
		j.setVisible(true);
		float x = 0;
		sg.autoSetXInc();
		while(true){
			try {
				Thread.sleep(5);
				sg.addPointAutoScroll((float) (Math.sin(x) + sg.yEnd / 2 + Math.sin(x * x)));
				x += sg.getXIncrement();
			}catch (InterruptedException e) {}
		}
	}
}
class ScrollablePoint implements Comparable<ScrollablePoint>{
	public float currentX, currentY;
	public final float originalX, originalY;
	public ScrollablePoint(float x, float y) {
		originalX = x;
		originalY = y;
		currentX = x;
		currentY = y;
	}
	@Override
	public int compareTo(ScrollablePoint to) {
		if(currentX > to.currentX) return 1;
		else if(currentX == to.currentX) return 0;
		else return -1;
	}
}