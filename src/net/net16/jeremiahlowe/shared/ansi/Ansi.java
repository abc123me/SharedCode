package net.net16.jeremiahlowe.shared.ansi;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

@Deprecated
public class Ansi {
	public static final String ESC = "\033[";
    public static final String RESET = ESC + "0m";
    
    public static final String format(String text, AnsiColor foreground, AnsiColor background, AnsiStyle... styles){return format(text, true, foreground, background, styles);}
    public static final String format(String text, AnsiColor foreground, AnsiStyle... styles){return format(text, true, foreground, null, styles);}
    public static final String format(String text, AnsiColor foreground){return format(text, true, foreground, null, AnsiStyle.Reset);}
    public static final String format(String text, boolean reset, AnsiColor foreground, AnsiColor background, AnsiStyle... styles){return Ansi.getSequence(reset, foreground, background, styles) + text;}
    public static final String getSequence(AnsiColor foreground, AnsiColor background, AnsiStyle... styles){return getSequence(true, foreground, background, styles);}
    public static final String getSequence(AnsiColor foreground, AnsiStyle... styles){return getSequence(true, foreground, null, styles);}
    public static final String getSequence(AnsiColor foreground){return getSequence(true, foreground, null, AnsiStyle.Reset);}
    public static final String getSequence(boolean reset, AnsiColor foreground, AnsiColor background, AnsiStyle... styles){
    	String out = reset ? "\033[0m\033[" : "\033[";
    	if(styles != null)
    		for(AnsiStyle s : styles)
        		if(s != null)
        			out += s.getAnsi() + ";";
    	if(foreground != null)
    	    out += foreground.getAnsi(false) + ";";
    	if(background != null)
    	    out += background.getAnsi(true) + ";";
    	return out.substring(0, out.length() - 1) + 'm'; //Replace trailing semicolon with m
    }
    public static final MutableAttributeSet createAttributeSet(AnsiColor foreground, AnsiColor background, AnsiStyle... styles){
    	MutableAttributeSet aset = new SimpleAttributeSet();
    	if(foreground != null) StyleConstants.setForeground(aset, foreground.toColor());
    	if(background != null) StyleConstants.setBackground(aset, background.toColor());
		if(styles != null){
			for(AnsiStyle s : styles){
				if(s != null){
					switch(s){
						case Bright: StyleConstants.setBold(aset, true); break;
						case Italic: StyleConstants.setItalic(aset, true); break;
						case Strikethrough: StyleConstants.setStrikeThrough(aset, true); break;
						case Underlined: StyleConstants.setUnderline(aset, true); break;
						default: break;
					}
				}
			}
		}
		return aset;
    }
    public static final boolean isValidColor(int id){
    	boolean reg = id >= 30 && id <= 37;
    	boolean regB = id >= 40 && id <= 47;
    	boolean brg = id >= 90 && id <= 97;
    	boolean brgB = id >= 100 && id <= 107;
    	return reg || brg || brgB || regB;
    }
}

