package net.net16.jeremiahlowe.shared;

public class Color {
	public static final Color BLACK = new Color(0, 0, 0);
	public static final Color RED = new Color(255, 0, 0);
	public static final Color GREEN = new Color(0, 255, 0);
	public static final Color BLUE = new Color(0, 0, 255);
	public static final Color YELLOW = new Color(255, 255, 0);
	public static final Color AQUA = new Color(0, 255, 255);
	public static final Color MAGENTA = new Color(255, 0, 255);
	public static final Color WHITE = new Color(255, 255, 255);
	public static final Color GRAY = new Color(127, 127, 127);
	
	public static final int ALPHA_OPAQUE = 0xFF;
	public static final int ALPHA_TRANSPARENT = 0x00;
	
	private byte r, g, b, a;
	private int argb;
	
	public Color() {
		this(0);
	}
	public Color(int grey) {
		this(grey, grey, grey);
	}
	public Color(Color c) {
		this.r = c.r; 
		this.g = c.g;
		this.b = c.b; 
		this.a = c.a;
		generateARGB();
	}
	public Color(Color c, int a) {
		this.r = c.r; 
		this.g = c.g;
		this.b = c.b;
		this.a = (byte) a;
		generateARGB();
	}
	public Color(int r, int g, int b) {
		this(r, g, b, ALPHA_OPAQUE);
	}
	public Color(int r, int g, int b, int a) {
		this.r = (byte) r;
		this.g = (byte) g;
		this.b = (byte) b;
		this.a = (byte) a;
		generateARGB();
	}
	
	private void generateARGB() {
		argb = a << 24;
		argb += r << 16;
		argb += g << 8;
		argb += b;
	}
	
	public int argb() {
		return argb;
	}
	
	public void r(int v) {
		r = (byte) v;
		generateARGB();
	}
	public void g(int v) {
		g = (byte) v;
		generateARGB();
	}
	public void b(int v) {
		b = (byte) v;
		generateARGB();
	}
	public void a(int v) {
		a = (byte) v;
		generateARGB();
	}
	public int r() {
		return Byte.toUnsignedInt(r);
	}
	public int g() {
		return Byte.toUnsignedInt(g);
	}
	public int b() {
		return Byte.toUnsignedInt(b);
	}
	public int a() {
		return Byte.toUnsignedInt(a);
	}
	public byte rb() {
		return r;
	}
	public byte gb() {
		return g;
	}
	public byte bb() {
		return b;
	}
	public byte ab() {
		return a;
	}
	
	public java.awt.Color toAwtColor() {
		return new java.awt.Color(r(), g(), b(), a());
	}
	public java.awt.Color toAwtColorNoAlpha() {
		return new java.awt.Color(r(), g(), b());
	}
}
