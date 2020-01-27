package net.net16.jeremiahlowe.telem;

public class TelemetryNet {
	public static final String KEYS_REQUEST =	"keys";
	public static final String GET_REQUEST =	"get";
	public static final String SET_REQUEST =	"set";
	public static final String EXIT_REQUEST =	"exit";
	public static final String FAIL_RESP =		"NAK";
	public static final String OK_RESP =		"ACK";
	public static final char NEWLINE =			'\n';
	public static final char SEPERATOR =		' ';
	public static final char ESCAPE =			'\\';
	public static final char NEWLINE_ESC 	=	'n';
	public static final char SEPERATOR_ESC =	's';
	
	public static String frame(String... bits) {
		String out = "";
		for(int i = 0; i < bits.length; i++)
			out += encodeStr(bits[i]) + SEPERATOR;
		return out.substring(0, out.length() - 1) + NEWLINE;
	}
	public static String[] unframe(String raw) {
		String[] out = raw.split(" ");
		for(int i = 0; i < out.length; i++)
			out[i] = decodeStr(out[i]);
		return out;
	}
	public static String encodeStr(String in) {
		if(in == null)
			return null;
		String out = "";
		for(int i = 0; i < in.length(); i++) {
			char c = in.charAt(i);
			if(c == NEWLINE)
				out += ESCAPE + "" + NEWLINE_ESC;
			else if(c == SEPERATOR)
				out += ESCAPE + "" + SEPERATOR_ESC;
			else if(c == ESCAPE)
				out += ESCAPE + "" + ESCAPE;
			else
				out += c;
		}
		return out;
	}
	public static String decodeStr(String in) {
		if(in == null)
			return null;
		String out = "";
		boolean skip = false;
		for(int i = 0; i < in.length(); i++) {
			char c = in.charAt(i);
			if(skip) {
				skip = false;
				continue;
			}
			if(c == ESCAPE) {
				if(i == in.length() - 1)
					throw new RuntimeException("Unterminated escape sequence");
				char n = in.charAt(i + 1);
				if(n == NEWLINE_ESC)
					out += NEWLINE;
				else if(n == SEPERATOR_ESC)
					out += SEPERATOR;
				else if(n == ESCAPE)
					out += ESCAPE;
				else throw new RuntimeException("Invalid escape sequence: " + ESCAPE + n);
				skip = true;
			} else out += c;
		}
		return out;
	}
}
