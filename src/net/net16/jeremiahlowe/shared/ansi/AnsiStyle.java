package net.net16.jeremiahlowe.shared.ansi;

@Deprecated
public enum AnsiStyle{
	Reset(0), Bright(1), Dark(2), Italic(3), Underlined(4), Strikethrough(9);
	private int id;
	private AnsiStyle(int id){this.id = id;}
	public int getAnsi(){return id;}
	public static AnsiStyle fromID(int id) {
		switch(id){
			case 0: return Reset;
			case 1: return Bright;
			case 2: return Dark;
			case 3: return Italic;
			case 4: return Underlined;
			case 9: return Strikethrough;
			default: return null;
		}
	}
}
