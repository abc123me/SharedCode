package net.net16.jeremiahlowe.shared.ansi;

import java.awt.Color;

public enum AnsiColor {
	Black(30), Red(31), Green(32), Yellow(33), Blue(34), Purple(35), Cyan(36), White(37), 
	BrightBlack(90), BrightRed(91), BrightGreen(92), BrightYellow(93), BrightBlue(94), BrightPurple(95), BrightCyan(96), BrightWhite(97);
	
	private int id;
	private AnsiColor(int id) {this.id = id;}

	public int getAnsi(boolean isBackground) {
		int id = this.id;
		if (isBackground) id += 10;
		return id;
	}

	public void brighten() {if(!isBright()) id += 60;}
	public void darken() {if(isBright()) id -= 60;}
	public int getID(){return id;}
	public boolean isBright() {return id >= 90;}
	
	public Color toColor(){
		brighten();
		switch(id){
			case 31: return new Color(125, 0, 0);
			case 32: return new Color(0, 125, 0);
			case 33: return new Color(125, 125, 0);
			case 34: return new Color(0, 0, 125);
			case 35: return new Color(125, 0, 125);
			case 36: return new Color(0, 125, 125);
			case 37: new Color(255, 255, 255);
			case 91: return Color.RED;
			case 92: return Color.GREEN;
			case 93: return Color.YELLOW;
			case 94: return Color.BLUE;
			case 95: return Color.MAGENTA;
			case 96: return Color.CYAN;
			case 97: return Color.WHITE;
		}
		return Color.BLACK;
	}
	public static final AnsiColor fromID(int id){
		for(AnsiColor c : values())
			if(c.getID() == id)
				return c;
		return Black;
	}
}