package net.net16.jeremiahlowe.telem;

import java.io.*;
import java.net.*;
import java.util.*;

import static net.net16.jeremiahlowe.telem.TelemetryNet.*;

public class TelemetryClient {
	public static void main(String[] args) throws UnknownHostException, IOException {
		TelemetryClient tc = new TelemetryClient("localhost", 1234);
		for(String s : tc.keys())
			System.out.println(s + ": " + tc.get(s));
		tc.close();
	}
	
	private Socket base;
	private InputStream in;
	private OutputStream out;
	private byte[] buf;
	
	public TelemetryClient(String ip, int port) throws UnknownHostException, IOException {
		this(ip, port, 4096);
	}
	public TelemetryClient(String ip, int port, int size) throws UnknownHostException, IOException {
		base = new Socket(ip, port);
		out = base.getOutputStream();
		in = base.getInputStream();
		buf = new byte[size];
	}

	public String set(String key, String val) {
		String tmp = frame(SET_REQUEST, key, val);
		tmp = send_recv_ack(tmp);
		return decodeStr(tmp);
	}
	public String get(String key) {
		String tmp = frame(GET_REQUEST, key);
		tmp = send_recv_ack(tmp);
		return decodeStr(tmp);
	}
	public Collection<String> keys() {
		ArrayList<String> out = new ArrayList<String>();
		String pkt = frame(KEYS_REQUEST);
		String[] resp = unframe(send_recv_ack(pkt));
		for(String r : resp)
			out.add(r);
		out.trimToSize();
		return out;
	}
	public int size() { return keys().size(); }
	public void close() {
		String pkt = frame(EXIT_REQUEST);
		send_recv(pkt);
		try { base.close();
		} catch (IOException e) {}
	}
	
	private String recv_msg(byte[] buf) throws IOException {
		String out = "";
		int got = 0;
		do {
			got = in.read(buf);
			for(int i = 0; i < got; i++)
				out += (char) buf[i];
		} while(got == buf.length);
		return out;
	}
	private String send_recv(String pkt) {
		try {
			out.write(pkt.getBytes());
			return recv_msg(buf);
		} catch(IOException ioe) {
			return null;
		}
	}
	private String send_recv_ack(String pkt) {
		String[] got = send_recv(pkt).split("" + NEWLINE);
		boolean ack = got.length > 1 && got[0].contentEquals(OK_RESP);
		if(!ack) {
			String msg = "Packet send_recv failed";
			if(got.length > 1) msg += ": " + got[1];
			else msg += "!";
			throw new RuntimeException(msg);
		}
		if(got.length > 1) return got[1];
		return "";
	}
}
