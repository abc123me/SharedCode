package net.net16.jeremiahlowe.shared;

import java.awt.*;

import javax.swing.*;

public class SwingUtility {
	public static final byte SEVEN_SEGMENT             = 0b000;
	public static final byte SEVEN_SEGMENT_DP_RIGHT    = 0b001;
	public static final byte SEVEN_SEGMENT_DP_LEFT     = 0b011;
	public static final byte FOURTEEN_SEGMENT          = 0b100;
	public static final byte FOURTEEN_SEGMENT_DP_RIGHT = 0b101;
	public static final byte FOURTEEN_SEGMENT_DP_LEFT  = 0b111;
	
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
	 * Draws a hex digit from 0 to F (15). The 5th bit of the number can be used to turn off/on the decimal point.
	 * @param g     Graphics backend to draw with
	 * @param style The style used for the drawSegmentDisplay function
	 * @param num   Number to draw, the 5th bit is used to turn off the decimal point
	 * @param fc    Foreground color
	 * @param bc    Background color, can be null if not desired
	 * @parma tc    Text color
	 * @param x     X position
	 * @param y     Y position
	 * @param w     Width
	 * @param h     Height
	 */
	public static final void drawHexDigit(GraphicsBackend g, byte style, int num, Color fc, Color bc, Color tc, int x, int y, int w, int h) {
		short segs = 0;
		boolean $14seg = (style & 4) != 0;
		switch(num & 0xF) {
		    /*                 PGFEDCBA      */
			case 0x0: segs = 0b00111111; break;
			case 0x1: segs = 0b00000110; break;
			case 0x2: segs = 0b01011011; break;
			case 0x3: segs = 0b01001111; break;
			case 0x4: segs = 0b01100110; break;
			case 0x5: segs = 0b01101101; break;
			case 0x6: segs = 0b01111101; break;
			case 0x7: segs = 0b00000111; break;
			case 0x8: segs = 0b01111111; break;
			case 0x9: segs = 0b01101111; break;
			case 0xA: segs = 0b01110111; break;
			case 0xB: segs = 0b01111100; break;
			case 0xC: segs = 0b00111001; break;
			case 0xD: segs = 0b01011110; break;
			case 0xE: segs = 0b01111001; break;
			case 0xF: segs = 0b01110001; break;
		}
		if($14seg) {
			switch(num & 0xF) {
				/*                 HIJKLMgPGFEDCBA */
				case 0x0: segs = 0b100001000111111; break;
				case 0x5: segs = 0b000001100101001; break;
				case 0xB: segs = 0b010010001001111; break;
				case 0xD: segs = 0b010010000001111; break;
				case 0xE: segs = 0b000000100111001; break;
				case 0xF: segs = 0b000000100110001; break;
				case 0x3: break;
				default: // If 14 segment and G, turn both G lights on
					if((segs & 0b01000000) != 0)
						segs |= 0b100000000;
					break;
			}
		}
		if((num & 32) != 0) segs |= 128;
		if(bc != null) {
			g.setColor(bc);
			g.fillRect(x, y, w, h);
		}
		drawSegmentDisplay(g, tc, fc, style, segs, x, y, w, h);
	}
	/**
	 * Draws an ASCII character.
	 * @param g     Graphics backend to draw with
	 * @param style The style used for the drawSegmentDisplay function
	 * @param num   Number to draw, the 5th bit is used to turn off the decimal point
	 * @param fc    Foreground color
	 * @param bc    Background color, can be null if not desired
	 * @parma tc    Text color
	 * @param x     X position
	 * @param y     Y position
	 * @param w     Width
	 * @param h     Height
	 */
	public static final void drawAsciiCharacter(GraphicsBackend g, byte style, char c, boolean dp, Color fc, Color bc, Color tc, int x, int y, int w, int h) {
		if(c >= '0' && c <= '9') {
			c -= '0'; if(dp) c |= 32;
			style |= 4;
			drawHexDigit(g, style, (int) c, fc, bc, tc, x, y, w, h);
			return;
		}
		short segs = 0;
		switch(c) {
			/*                 HIJKLMgPGFEDCBA */
			case 'A': segs = 0b000000101110111; break;
			case 'B': segs = 0b010010001001111; break;
			case 'C': segs = 0b000000000111001; break;
			case 'D': segs = 0b010010000001111; break;
			case 'E': segs = 0b000000100111001; break;
			case 'F': segs = 0b000000100110001; break;
			case 'G': segs = 0b000000001111101; break;
			case 'H': segs = 0b000000101110110; break;
			case 'I': segs = 0b010010000001001; break;
			case 'J': segs = 0b000000000011110; break;
			case 'K': segs = 0b001001100110000; break;
			case 'L': segs = 0b000000000111000; break;
			case 'M': segs = 0b101000000110110; break;
			case 'N': segs = 0b100001000110110; break;
			case 'O': segs = 0b000000000111111; break;
			case 'P': segs = 0b000000101110011; break;
			case 'Q': segs = 0b000001000111111; break;
			case 'R': segs = 0b000001101110011; break;
			case 'S': segs = 0b000000101101101; break;
			case 'T': segs = 0b010010000000001; break;
			case 'U': segs = 0b000000000111110; break;
			case 'V': segs = 0b001100000110000; break;
			case 'W': segs = 0b000101000110110; break;
			case 'X': segs = 0b101101000000000; break;
			case 'Y': segs = 0b000000101101110; break;
			case 'Z': segs = 0b001100000001001; break;
			/*                 HIJKLMgPGFEDCBA */
			case 'a': segs = 0b000010100011000; break;
			case 'b': segs = 0b000100001001110; break;
			case 'c': segs = 0b000000101011000; break;
			case 'd': segs = 0b000001100111000; break;
			case 'e': segs = 0b000100100011000; break;
			case 'f': segs = 0b001010101000000; break;
			case 'g': segs = 0b001000001001110; break;
			case 'h': segs = 0b000010100110000; break;
			case 'i': segs = 0b000010000000000; break;
			case 'j': segs = 0b010100000010000; break;
			case 'k': segs = 0b011011000000000; break;
			case 'l': segs = 0b000000000110000; break;
			case 'm': segs = 0b000010101010100; break;
			case 'n': segs = 0b000010100010000; break;
			case 'o': segs = 0b000000101011100; break;
			case 'p': segs = 0b100000100110000; break;
			case 'q': segs = 0b001000001000110; break;
			case 'r': segs = 0b000000100010000; break;
			case 's': segs = 0b000001001001000; break;
			case 't': segs = 0b000000100111000; break;
			case 'u': segs = 0b000000000011100; break;
			case 'v': segs = 0b000100000010000; break;
			case 'w': segs = 0b000101000010100; break;
			case 'x': segs = 0b101101000000000; break;
			case 'y': segs = 0b010000001001110; break;
			case 'z': segs = 0b000100100001000; break;
			/*                 HIJKLMgPGFEDCBA */
			case '[': segs = 0b000000000111001; break;
			case ']': segs = 0b000000000001111; break;
			case '{': segs = 0b100100100001001; break;
			case '}': segs = 0b001001001001001; break;
			case '(': segs = 0b001001000000000; break;
			case ')': segs = 0b100100000000000; break;
			case '<': segs = 0b001001000000000; break;
			case '>': segs = 0b000100100000000; break;
			case '|': segs = 0b010010000000000; break;
			case '/': segs = 0b001100000000000; break;
			case '\\':segs = 0b100001000000000; break;
			case '+': segs = 0b010010101000000; break;
			case '-': segs = 0b000000101000000; break;
			case '*': segs = 0b111111101000000; break;
			case '=': segs = 0b000000101001000; break;
			case '_': segs = 0b000000000001000; break;
			case ',': segs = 0b000100000000000; break;
			case '`': segs = 0b100000000000000; break;
			case '\'':segs = 0b010000000000000; break;
			case '\"':segs = 0b010000000100000; break;
			case ' ': segs = 0b000000000000000; break;
			case '$': segs = 0b010010101101101; break;
			case '?': segs = 0b000010001000011; break;
			case '^': segs = 0b000101000000000; break;
			case '%': segs = 0b111111101100100; break;
			case '&': segs = 0b110001100011001; break;
			case '#': segs = 0b010010101001110; break;
			case '@': segs = 0b000010100011111; break;
			/*                 HIJKLMgPGFEDCBA */
			default:  segs = 0b000000000000000; break;
		}
		if(dp) segs |= 128;
		if(bc != null) {
			g.setColor(bc);
			g.fillRect(x, y, w, h);
		}
		drawSegmentDisplay(g, tc, fc, style, segs, x, y, w, h);
	}
	/**
	 * Draws a 7/14 segment display, using a specified style and segment mask.
	 * The style is a 8 bit mask
	 * Bit 0 - DP visibility (0 - Invisible, 1 - visible)
	 * Bit 1 - DP direction (0 - right, 2 - left)
	 * Bit 2 - 14 (4) / 7 (0) segment display
	 * @param g     Graphics to draw with
	 * @param a     Color 1
	 * @param b     Color 2
	 * @param style Style to use
	 * @param num   Digit mask to show in this order: HIJKLMgPGFEDCBA (P is decimal point on/off, g is left, G is right)
	 * @param x     X position
	 * @param y     Y position
	 * @param w     Width
	 * @param h     Height
	 */
	public static void drawSegmentDisplay(GraphicsBackend g, Color a, Color b, byte style, short num, int x, int y, int w, int h) {
		int h16 = h / 16, h8 = h / 8;
		int segS = h16, w4 = w / 4, w2 = w / 2;
		int y1 = h8 + y, y1b = y1 + segS;
		int y2 = h8 * 4 + y, y2b = y2 + segS;
		int y3 = h8 * 7 + y;
		int x1 = w4 + x, x2 = (w - w4) + x;
		int segS2 = y2 - y1b;
		if((style & 4) != 0) {
			int segSh = segS / 2, w4_ss2 = w4 - segSh;
			int x3 = x + w / 2 + segSh, x4 = x + w / 2 - segSh;
			/*                       HIJKLMGPGFEDCBA      */
			drawSegment(g, ((num & 0b000000100000000) != 0) ? a : b, x1, y2, w4_ss2, segS);                  // GL
			drawSegment(g, ((num & 0b000000001000000) != 0) ? a : b, x3, y2, w4_ss2, segS);                  // GR
			drawSegment(g, ((num & 0b010000000000000) != 0) ? a : b, x4, y1b + segSh, segS, segS2 - segSh); // I
			drawSegment(g, ((num & 0b000010000000000) != 0) ? a : b, x4, y2b, segS, segS2 - segSh);         // L
			drawDiagSeg(g, ((num & 0b000100000000000) != 0) ? a : b, x1, y2b, w4_ss2, segS2, segS, true);   // K
			drawDiagSeg(g, ((num & 0b100000000000000) != 0) ? a : b, x1, y1b, w4_ss2, segS2, segS, false);  // H
			drawDiagSeg(g, ((num & 0b001000000000000) != 0) ? a : b, x3, y1b, w4_ss2, segS2, segS, true);   // J
			drawDiagSeg(g, ((num & 0b000001000000000) != 0) ? a : b, x3, y2b, w4_ss2, segS2, segS, false);  // M
		} else drawSegment(g, ((num & 0b01000000) != 0) ? a : b, x1, y2, w2, segS); // G
		drawOuterSegments(g, a, b, num, x1, x2, y1, y2, y3, y1b, y2b, w2, segS, segS2);
		drawDP(g, ((num & 0b10000000) != 0) ? a : b, style, x, y3, segS, x2);
	}
	private static void drawDP(GraphicsBackend g, Color c, byte style, int x, int y, int s, int x2) {
		if((style & 1) != 0) {
			g.setColor(c);
			int dpX = ((style & 2) != 0) ? x + s : x2 +  s;
			g.fillEllipse(dpX, y, s, s);
		}
	}
	private static void drawOuterSegments(GraphicsBackend g, Color a, Color b, int num, int x1, int x2, int y1, int y2, int y3, int y1b, int y2b, int w2, int segS, int segS2) {
		/*                       PGFEDCBA     (G is ignored) */
		drawSegment(g, ((num & 0b00000001) != 0) ? a : b, x1, y1, w2, segS); // A
		drawSegment(g, ((num & 0b00001000) != 0) ? a : b, x1, y3, w2, segS); // D
		drawSegment(g, ((num & 0b00000010) != 0) ? a : b, x2, y1b, segS, segS2); // B
		drawSegment(g, ((num & 0b00000100) != 0) ? a : b, x2, y2b, segS, segS2); // C
		drawSegment(g, ((num & 0b00100000) != 0) ? a : b, x1 - segS, y1b, segS, segS2); // F
		drawSegment(g, ((num & 0b00010000) != 0) ? a : b, x1 - segS, y2b, segS, segS2); // E
	}
	private static void drawSegment(GraphicsBackend g, Color c, int x, int y, int w, int h) {
		g.setColor(c);
		final int np = 8;
		int m = w < h ? w : h; m /= 3;
		int[] xp = new int[] {m, w - m, w, w, w - m, m, 0, 0};
		int[] yp = new int[] {0, 0, m, h - m, h, h, h - m, m};
		for(int i = 0; i < np; i++) {
			xp[i] += x;
			yp[i] += y;
		}
		g.fillPolygon(xp, yp, np);
	}
	private static void drawDiagSeg(GraphicsBackend g, Color c, int x, int y, int w, int h, int s, boolean f) {
		g.setColor(c);
		final int np = 4;
		int m = s / 3;
		h -= m; w -= m;
		x += m / 2;
		int a = f ? h : m;
		int b = f ? m : h;
		int[] xp = new int[] {0, s, w, w - s};
		int[] yp = new int[] {a, a, b, b};
		for(int i = 0; i < np; i++) {
			xp[i] += x;
			yp[i] += y;
		}
		g.fillPolygon(xp, yp, np);
	}
}
