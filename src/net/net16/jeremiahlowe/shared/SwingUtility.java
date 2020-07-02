package net.net16.jeremiahlowe.shared;

import java.awt.*;
import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.*;

public class SwingUtility {
	public static final JFrame centerJFrame(JFrame f) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		Rectangle screen = gd.getDefaultConfiguration().getBounds();
		int x = screen.x + screen.width / 2 - f.getWidth() / 2;
		int y = screen.y + screen.height / 2 - f.getHeight() / 2;
		f.setLocation(x, y);
		return f;
	}
	public static final Frame centerFrame(Frame f) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		Rectangle screen = gd.getDefaultConfiguration().getBounds();
		int x = screen.x + screen.width / 2 - f.getWidth() / 2;
		int y = screen.y + screen.height / 2 - f.getHeight() / 2;
		f.setLocation(x, y);
		return f;
	}
	public static final Color color3toColor24(int n) {
		int r = 0, g = 0, b = 0;
		r = n & 0b1;
		g = n & 0b10;
		b = n & 0b100;
		if(r != 0) r = 255;
		if(g != 0) g = 255;
		if(b != 0) b = 255;
		return new Color(r, g, b);
	}
	
	/**
	 * Draws a 7 segment hex digit from 0 to F (15). 
	 * The number can range from 0 to 255, extra bits allow for a decimal point
	 * 5th bit - If set the decimal point will be shown (32)
	 * 6th bit - Show on left (0) / right (64) side
	 * 7th bit - Turn the bit off (0) / on (128)
	 * 
	 * @param g   Graphics object to draw with
	 * @param num Number to draw
	 * @param fc  Foreground color
	 * @param bc  Background color
	 * @parma tc  Text color
	 * @param x   X position
	 * @param y   Y position
	 * @param w   Width
	 * @param h   Height
	 */
	public static final void drawHexDigit(Graphics g, int num, Color fc, Color bc, Color tc, int x, int y, int w, int h) {
		byte segs = 0;
		boolean show_dp = ((num & 32) != 0)  ? true : false;
		boolean dp_dir  = ((num & 64) != 0)  ? true : false;
		boolean dp_stat = ((num & 128) != 0) ? true : false;
		switch(num & 0xF) {
		    /*                 PGFEDCBA      */
			case 0x0: segs = 0b00111111; break;
			case 0x1: segs = 0b00000110; break;
			case 0x2: segs = 0b01011011; break;
			case 0x3: segs = 0b01001111; break;
			case 0x4: segs = 0b00100110; break;
			case 0x5: segs = 0b01101101; break;
			case 0x6: segs = 0b01111101; break;
			case 0x7: segs = 0b00000111; break;
			case 0x8: segs = 0b01111111; break;
			case 0x9: segs = 0b01101111; break;
			case 0xA: segs = 0b01110110; break;
			case 0xB: segs = 0b01111100; break;
			case 0xC: segs = 0b00111001; break;
			case 0xD: segs = 0b01011110; break;
			case 0xE: segs = 0b01111001; break;
			case 0xF: segs = 0b01110011; break;
		}
		g.setColor(bc);
		g.fillRect(x, y, w, h);
		draw7Segment(g, tc, fc, segs, x, y, w, h);
	}
	private static void draw7Segment(Graphics g, Color a, Color b, byte num, int x, int y, int w, int h) {
		int h16 = h / 16, h8 = h / 8;
		int segS = h16, w4 = w / 4;
		int y1 = h8 + y, y1b = y1 + segS;
		int y2 = h8 * 4 + y, y2b = y2 + segS;
		int y3 = h8 * 7 + y;
		int x1 = w4 + x, x2 = (w - w4) + x;
		/*                       PGFEDCBA      */
		drawSegment(g, ((num & 0b00000001) != 0) ? a : b, x1, y1, w / 2, segS); // A
		drawSegment(g, ((num & 0b01000000) != 0) ? a : b, x1, y2, w / 2, segS); // G
		drawSegment(g, ((num & 0b00001000) != 0) ? a : b, x1, y3, w / 2, segS); // D
		drawSegment(g, ((num & 0b00000010) != 0) ? a : b, x2, y1b, segS, y2 - y1b); // B
		drawSegment(g, ((num & 0b00000100) != 0) ? a : b, x2, y2b, segS, y3 - y2b); // C
		drawSegment(g, ((num & 0b00100000) != 0) ? a : b, x1 - segS, y1b, segS, y2 - y1b); // F
		drawSegment(g, ((num & 0b00010000) != 0) ? a : b, x1 - segS, y2b, segS, y3 - y2b); // E
	}
	private static void drawSegment(Graphics g, Color c, int x, int y, int w, int h) {
		g.setColor(c);
		final int np = 4;
		int[] xp = new int[] {0, w, w, 0};
		int[] yp = new int[] {0, 0, h, h};
		for(int i = 0; i < np; i++) {
			xp[i] += x;
			yp[i] += y;
		}
		g.fillPolygon(xp, yp, np);
	}
}
