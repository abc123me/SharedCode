package net.net16.jeremiahlowe.shared.ansi;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class AnsiTextPane extends JTextPane {
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
	private AnsiStyle style = new AnsiStyle();
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
		style.reset();
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
	@Override public void setAutoscrolls(boolean s) { 
		autoscrolls = s;
		Caret caret = getCaret();
		if(caret instanceof DefaultCaret)
			((DefaultCaret) caret).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
	}
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
	public void append(String s) {
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
				if(c == ';' || (c >= '0' && c <= '9'))
					curAnsi += c;
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
	
	private void autoscroll() {
		setCaretPosition(getDocument().getEndPosition().getOffset() - 1);
	}
	private Color[] getCurrentColorMap() {
		if(style.darken()) return darkColorMap;
		if(style.brighten()) return brightColorMap;
		return defaultColorMap;
	}
	private void atb(String s) {
		SimpleAttributeSet aset = new SimpleAttributeSet();
		Color[] colorMap = getCurrentColorMap();
		int fg = currentForeground, bg = currentBackground;
		if(style.swap()) { int c = fg; fg = bg; bg = c; }
		if(style.conceal()) fg = bg;
		StyleConstants.setForeground(aset, colorMap[fg]);
		StyleConstants.setBackground(aset, colorMap[bg]);
		StyleConstants.setBold(aset, style.brighten());
		StyleConstants.setItalic(aset, style.darken());
		StyleConstants.setStrikeThrough(aset, style.strike());
		StyleConstants.setUnderline(aset, style.underline());
		int len = getDocument().getLength(); 
		try { getDocument().insertString(len, s, aset); } 
		catch (BadLocationException e) {e.printStackTrace();}
		if(autoscrolls) autoscroll();
	}
	private void handleSpecial(int i) {
		float caretSpeed = -1;
		if(!style.interpretAnsiCode(i)) {
			switch(i) {
				//Normal flags
				case 5: caretSpeed = slowCaretRate; break;
				case 6: caretSpeed = fastCaretRate; break;
				// Disable flags
				case 25: caretSpeed = 0; break;
				default: break;
			}
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