package net.net16.jeremiahlowe.shared;

import java.awt.*;
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
}
