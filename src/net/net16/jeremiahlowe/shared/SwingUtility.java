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
}
