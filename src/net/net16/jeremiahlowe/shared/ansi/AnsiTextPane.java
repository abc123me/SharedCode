package net.net16.jeremiahlowe.shared.ansi;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class AnsiTextPane extends JTextPane{
	/*public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setBounds(0, 0, 1920, 1080);
		JScrollPane sp = new JScrollPane();
		AnsiTextPane pane = new AnsiTextPane();
		f.setContentPane(sp);
		//for(int j = 0; j < 10; j++)
		//	for(int i = 0; i < 8; i++) 
		//		pane.append(ANSI_ESCAPE + "[" + j + ";3" + i + "m[" + i + ", " + j + "]: Hello world!" + ANSI_CLEAR + "\n");
		sp.setViewportView(pane);
		pane.setAutoscrolls(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		int xi = pane.getFontMetrics(pane.getFont()).stringWidth(" ");
		int yi = pane.getFontMetrics(pane.getFont()).getHeight();
		float w = pane.getWidth() - 2 * xi, h = pane.getHeight() - 2 * yi;
		float asp = w / h; asp /= 2;
		float a = 3;
		pane.resetAttributes();
		for(int y = 0; y < h; y += yi) {
			for(int x = 0; x < w; x += xi) {
				int r = 255 / 2 + ((int)Math.round(255 * Math.sqrt(Math.cos((y / h) * 2 * a * asp * Math.PI)))) / 2;
				int g = (int)Math.round(255 * Math.sqrt(Math.sin(((x * y) / (w * h)) * 2 * a * asp * Math.PI)));
				int b = 255 / 2 + ((int)Math.round(255 * Math.sqrt(Math.cos((x / w) * 2 * a * Math.PI)))) / 2;
				String astr = "[48;2;" + r + ';' + g + ';' + b + "m ";
				//System.out.println(astr);
				pane.append(ANSI_ESCAPE + astr); 
			}
			pane.append("\n");
		}
	}*/
	
	public static final String ANSI_ESCAPE = "\u001B[";
	public static final String ANSI_CLEAR = ANSI_ESCAPE + "0m";
	
	private static final int DEFAULT_FOREGROUND = 0;
	private static final int DEFAULT_BACKGROUND = 7;
	
	private static final int FORMAT_ITALIC      = 0x01;
	private static final int FORMAT_UNDERLINE   = 0x02;
	private static final int FORMAT_BRIGHTEN    = 0x04;
	private static final int FORMAT_STRIKE      = 0x08;
	private static final int FORMAT_CONCEAL     = 0x10;
	private static final int FORMAT_DARKEN      = 0x20;
	private static final int FORMAT_SWAP_COLORS = 0x40;
	 
	
	private Color[] defaultColorMap = new Color[] {
			Color.BLACK, Color.RED, Color.GREEN, Color.ORANGE,
			Color.BLUE, Color.MAGENTA, Color.CYAN, Color.WHITE, null
	}; //Last null is for custom color
	private Color[] darkColorMap = new Color[] {
			Color.BLACK, Color.RED, Color.GREEN, Color.ORANGE,
			Color.BLUE, Color.MAGENTA, Color.CYAN, Color.WHITE, null
	}; //Last null is for custom color
	private Color[] brightColorMap = new Color[] {
			Color.GRAY, Color.RED, Color.GREEN, Color.YELLOW,
			Color.BLUE, Color.MAGENTA, Color.CYAN, Color.WHITE, null
	}; //Last null is for custom color
	
	private int slowCaretRate = -1;
	private int fastCaretRate = -1;
	private int currentForeground;
	private int currentBackground;
	private int formattingMask;
	private boolean autoscrolls = false;
	
	public AnsiTextPane(){
		super();
		Container parent = getParent();
		if(parent != null) {
			setGlobalForeground(parent.getForeground());
			setGlobalBackground(parent.getBackground());
		}
		if(slowCaretRate < 1 || fastCaretRate < 1) {
			int rate = 100;
			if(getCaret() != null)
				rate = getCaret().getBlinkRate();
			slowCaretRate = rate;
			fastCaretRate = rate / 2;
		}
		resetAttributes();
	}

	public void resetAttributes() {
		currentForeground = DEFAULT_FOREGROUND;
		currentBackground = DEFAULT_BACKGROUND;
		formattingMask = 0;
	}
	public void setGlobalBackground(Color c) { 
		setGlobalColor(DEFAULT_BACKGROUND, c, true); 
		super.setBackground(c);
		repaint(); validate();
	}
	public void setGlobalForeground(Color c) { 
		setGlobalColor(DEFAULT_FOREGROUND, c, true); 
		super.setForeground(c);
		repaint(); validate();
	}
	@Override public void setAutoscrolls(boolean s) { autoscrolls = s; }
	@Override public boolean getAutoscrolls() { return autoscrolls; }
	public Color getDarkBackground() { return darkColorMap[DEFAULT_BACKGROUND]; }
	public Color getDarkForeground() { return darkColorMap[DEFAULT_FOREGROUND]; }
	public Color getBrightBackground() { return brightColorMap[DEFAULT_BACKGROUND]; }
	public Color getBrightForeground() { return brightColorMap[DEFAULT_FOREGROUND]; }
	public Color getCurrentBackground() { return getCurrentColorMap()[DEFAULT_BACKGROUND]; }
	public Color getCurrentForeground() { return getCurrentColorMap()[DEFAULT_FOREGROUND]; }
	public void setGlobalColor(int pos, Color color, boolean adjustBrightness) {
		if(pos < 0 || pos >= defaultColorMap.length)
			throw new RuntimeException("Color at position " + pos + " does not exist");
		defaultColorMap[pos] = color;
		if(adjustBrightness) {
			brightColorMap[pos] = color.brighter();
			darkColorMap[pos] = color.darker();
		} else {
			brightColorMap[pos] = color;
			darkColorMap[pos] = color;
		}
	}
	
	public void append(String s){
		boolean isAnsi = false;
		String curAnsi = "", text = "";
		for(int i = 0; i < s.length(); i++){
			char c = s.charAt(i);
			if(c == '\033' && s.charAt(i + 1) == '['){
				isAnsi = true;
				atb(text); 
				text = "";
				i++; //Skip the escape sequence and go on
			}
			else if(isAnsi){
				if(c == ';' || (c >= '0' && c <= '9')) curAnsi += c;
				else if(c == 'm'){
					isAnsi = false;
					updateFormatting(curAnsi);
					curAnsi = "";
					continue;
				}
				else throw new RuntimeException("Error parsing ANSI");
			}
			else text += c;
		}
		atb(text);
		text = "";
	}
	@Override public void setText(String txt) {
		clear();
		append(txt); //No way to "set" the text all at once since the style must be dynamically changed based on the text
	}
	public void clear() {
		super.setText("");
	}
	public void setRawText(String txt) {
		super.setText(txt);
	}
	
	private Color[] getCurrentColorMap() {
		if((formattingMask & FORMAT_DARKEN) != 0) return darkColorMap;
		if((formattingMask & FORMAT_BRIGHTEN) != 0) return brightColorMap;
		return defaultColorMap;
	}
	private void atb(String s) {
		SimpleAttributeSet aset = new SimpleAttributeSet();
		Color[] colorMap = getCurrentColorMap();
		int fg = currentForeground, bg = currentBackground;
		if((formattingMask & FORMAT_SWAP_COLORS) != 0) { int c = fg; fg = bg; bg = c; }
		if((formattingMask & FORMAT_CONCEAL) != 0) fg = bg;
		StyleConstants.setForeground(aset, colorMap[fg]);
		StyleConstants.setBackground(aset, colorMap[bg]);
		StyleConstants.setBold(aset, (formattingMask & FORMAT_BRIGHTEN) != 0);
		StyleConstants.setItalic(aset, (formattingMask & FORMAT_ITALIC) != 0);
		StyleConstants.setStrikeThrough(aset, (formattingMask & FORMAT_STRIKE) != 0);
		StyleConstants.setUnderline(aset, (formattingMask & FORMAT_UNDERLINE) != 0);
		int len = getDocument().getLength(); 
		try {getDocument().insertString(len, s, aset);} 
		catch (BadLocationException e) {e.printStackTrace();}
		if(autoscrolls) setCaretPosition(getDocument().getLength());
	}
	private void handleSpecial(int i) {
		float caretSpeed = -1;
		switch(i) {
			//Normal flags
			case 5: caretSpeed = slowCaretRate; break;
			case 6: caretSpeed = fastCaretRate; break;
			// Disable flags
			case 25: caretSpeed = 0; break;
			default: break;
		}
		Caret c = getCaret();
		if(caretSpeed >= 0 && c != null) {
			if(caretSpeed > 0) {
				c.setVisible(true);
				c.setBlinkRate(Math.round(caretSpeed));
			}
			else if(caretSpeed == 0) c.setVisible(false);
		}
	}
	private int customColor(String[] parts, int index, boolean foreground) {
		if(index + 1 >= parts.length) return 0;
		else index++;
		int mode = Integer.parseInt(parts[index]);
		if(mode == 2) { // 24 bit color \e[38;2;r;g;b
			if(index + 3 >= parts.length) return 1;
			int r = Integer.parseInt(parts[index + 1]);
			int g = Integer.parseInt(parts[index + 2]);
			int b = Integer.parseInt(parts[index + 3]);
			Color[] c = getCurrentColorMap();
			int pos = c.length - 1;
			c[pos] = new Color(r, g, b);
			if(foreground) currentForeground = pos;
			else currentBackground = pos;
			return 4;
		}
		return 1;
	}
	private void updateFormatting(String ansi) {
		String[] parts =  ansi.split(";");
		for(int j = 0; j < parts.length; j++) {
			int i = Integer.parseInt(parts[j]);
			if(i >= 30 && i < 38) currentForeground = i - 30;
			else if(i >= 40 && i < 48) currentBackground = i - 40;
			else if(i > 0 && i < 10) handleSpecial(i);
			else if(i > 20 && i < 30) handleSpecial(i);
			else if(i == 0) resetAttributes();
			else if(i == 39) currentForeground = DEFAULT_FOREGROUND;
			else if(i == 49) currentBackground = DEFAULT_BACKGROUND;
			else if(i == 38) j += customColor(parts, j, true); 
			else if(i == 48) j += customColor(parts, j, false); 
		}
	}

	public void appendStyled(String s, int... styles) {
		String prefix = "";
		if(styles.length > 0){
			prefix = "\u001B[";
			for(int style : styles)
				prefix += style + ";";
			prefix = prefix.substring(0, prefix.length() - 1) + "m";
		}
		append("\u001B[0m" + prefix + s + "\u001B[0m");
	}	
}