package net.net16.jeremiahlowe.shared.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.*;

import net.net16.jeremiahlowe.shared.SwingUtility;

public class GenericDialogs {
	public static void main(String[] args) {
		InetAddress ip = showHostnameDialog();
		if(ip == null) System.out.println("No IP!");
		else System.out.println(ip);
	}
	
	public static InetAddress showHostnameDialog() {
		JFrame jf = new JFrame("Please enter a hostname/IP!");
		jf.setLayout(new BoxLayout(jf.getContentPane(), BoxLayout.Y_AXIS));
		jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jf.setSize(500, 200);

		
		JLabel lblFormat = new JLabel("Hostname/IP address:");
		jf.add(lblFormat);
		
		JTextField field = new JTextField("localhost");
		jf.add(field);
		
		JButtonTracked btnCancel = new JButtonTracked("Cancel");
		JButtonTracked btnSubmit = new JButtonTracked("OK");
		Box controlBx = Box.createHorizontalBox();
		controlBx.add(btnSubmit);
		controlBx.add(btnCancel);
		jf.add(controlBx);
		
		jf.setVisible(true);
		SwingUtility.centerJFrame(jf);
		
		InetAddress addr = null;
		try {
			while(addr == null) {
				if(btnSubmit.wasPressed()) {
					try { addr = InetAddress.getByName(field.getText()); }
					catch (UnknownHostException e) { JOptionPane.showMessageDialog(jf, "Unknown host, try again!"); }
				} else if(btnCancel.wasPressed()) break;
				Thread.sleep(50);
			}
		} catch (InterruptedException e) {}
		jf.dispose();
		return addr;
	}
}
class JButtonTracked extends JButton implements ActionListener {
	private static final long serialVersionUID = 6058743910947545883L;
	
	private boolean was_pressed = false;
	
	public JButtonTracked(String text) {
		super(text);
		addActionListener(this);
	}

	@Override public void actionPerformed(ActionEvent arg0) {
		was_pressed = true;
	}
	
	public boolean wasPressed() {
		if(was_pressed) {
			was_pressed = false;
			return true;
		} else return false;
	}
}