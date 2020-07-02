package net.net16.jeremiahlowe.shared;

public class TextUtility {
	public static final String clampLength(String in, int len){return clampLength(in, len, false);}
	public static final String clampLength(String in, int len, boolean dots){
		if(in.length() > len) return in.substring(0, len) + (dots ? "..." : "");
		else return in;
	}
	public static final String removeWhitespace(String in){
		String out = "";
		for(int i = 0; i < in.length(); i++){
			char c = in.charAt(i);
			if(c == ' ' || c == '\n' || c == '\r' || c == '\t') continue;
			else out += c;
		}
		return out;
	}
	public static final boolean isAlphanumeric(char c){
		return (isAlphabetical(c) || isNumeric(c));
	}
	public static final boolean isAlphanumeric(String s){
		char[] sc = s.toCharArray();
		for(char c : sc) if(!isAlphanumeric(c)) return false;
		return true;
	}
	public static final boolean isAlphabetical(char c){
		boolean upper = c >= 'A' && c <= 'Z';
		boolean lower = c >= 'a' && c <= 'z';
		return (upper || lower);
	}
	public static final boolean isAlphabetical(String s){
		char[] sc = s.toCharArray();
		for(char c : sc) if(!isAlphabetical(c)) return false;
		return true;
	}
	public static final boolean isNumeric(char c, boolean includeDecimal){
		return (c >= '0' && c <= '9' || (includeDecimal && c == '.'));
	}
	public static final boolean isNumeric(String s, boolean includeDecimal){
		char[] sc = s.toCharArray();
		for(char c : sc) if(!isNumeric(c, includeDecimal)) return false;
		return true;
	}
	public static final boolean isNumeric(char c){return isNumeric(c, false);}
	public static final boolean isNumeric(String s){return isNumeric(s, false);}
	public static final int count(String text, char regex){
		char[] chars = text.toCharArray();
		int ocurrences = 0;
		for(char c : chars) if(c == regex) ocurrences++;
		return ocurrences;
	}
	public static String numberToString(double num, int digits){
		if(digits < 0) throw new RuntimeException("Digits must be >= 0!");
		String[] parts = String.valueOf(num).split("[.]");
		if(parts[1].matches("0")) return parts[0];
		if(parts[1].length() > digits){
			String end = parts[1].substring(0, digits);
			while(end.endsWith("0")) end = end.substring(0, end.length() - 1);
			return parts[0] + '.' + end;
		}
		return parts[0] + '.' + parts[1];
	}
	public final static String secsToStr(int cs) {
		int h = 0, m = 0, s = 0;
		h = cs / 3600;
		m = (cs % 3600) / 60;
		s = cs % 60;
		String out = "";
		if(cs >= 3600)
			out += String.format("%d:", h);
		out += String.format("%02d:", m);
		out += String.format("%02d", s);
		return out;
	}
	public final static String combineStringArray(String[] in, int start, int stop) { 
		return combineStringArray(in, start, stop, "", "");
	}
	public final static String combineStringArray(String[] in, int start, int stop, String sep) { 
		return combineStringArray(in, start, stop, sep, "");
	}
	public final static String combineStringArray(String[] in, int start, int stop, String sep, String end) {
		String out = "";
		if(stop < 0) stop = in.length;
		for (int i = start; i < stop; i++)
			out += in[i] + sep;
		if(sep == end) return out;
		out = out.substring(0, out.length() - sep.length());
		return out + end;
	}
}
