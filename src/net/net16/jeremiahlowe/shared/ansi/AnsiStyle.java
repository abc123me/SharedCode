package net.net16.jeremiahlowe.shared.ansi;

public enum AnsiStyle{
	Reset(0), Bold(1), Italic(3), Underlined(4), Strikethrough(9);
	private int id;
	private AnsiStyle(int id){this.id = id;}
	public int getAnsi(){return id;}
	public static AnsiStyle fromID(int id) {
		switch(id){
			case 0: return Reset;
			case 1: return Bold;
			case 3: return Italic;
			case 4: return Underlined;
			case 9: return Strikethrough;
			default: return null;
		}
	}
}
