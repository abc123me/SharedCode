package net.net16.jeremiahlowe.shared.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.*;

import net.net16.jeremiahlowe.shared.ETimeUnit;
import net.net16.jeremiahlowe.shared.SwingUtility;

public class GenericDialogs {
	public static void main(String[] args) {
		char[] pwd = showPasswordField();
		for(int i = 0; i < pwd.length; i++)
			System.out.print(pwd[i]);
		System.out.println();
	}
	
	private static JFrame initJF(String t, int w, int h) {
		JFrame jf = new JFrame(t);
		jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jf.setSize(w, h);
		SwingUtility.centerJFrame(jf);
		return jf;
	}
	public static char[] showPasswordField() {
		JFrame jf = initJF("Password?", 300, 150);
		jf.setLayout(new BorderLayout());
		Box panel = Box.createVerticalBox();
		panel.add(Box.createVerticalGlue());
		panel.add(new JLabel("Password:"));
		JPasswordField passField = new JPasswordField();
		JCheckBox showPassordChkBox = new JCheckBox("Show password");
		showPassordChkBox.addActionListener((ActionEvent e) -> {
			passField.setEchoChar(showPassordChkBox.isSelected() ? '\u0000' : '*');
		});
		panel.add(passField);
		panel.add(showPassordChkBox);
		jf.add(panel, BorderLayout.NORTH);
		JButtonTracked btnCancel = new JButtonTracked("Cancel");
		JButtonTracked btnSubmit = new JButtonTracked("OK");
		Box controlBx = Box.createHorizontalBox();
		controlBx.add(btnSubmit);
		controlBx.add(btnCancel);
		jf.add(controlBx, BorderLayout.SOUTH);
		jf.setVisible(true);
		char[] out = null;
		try {
			while(out == null) {
				if(!jf.isDisplayable()) { return null; }
				if(btnSubmit.wasPressed()) out = passField.getPassword();
				else if(btnCancel.wasPressed()) break;
				Thread.sleep(50);
			}
		} catch (InterruptedException e) {}
		jf.dispose();
		return out;
	}
	public static InetAddress showHostnameDialog() {
		JFrame jf = initJF("Please enter a hostname/IP!", 300, 200);
		jf.setLayout(new BoxLayout(jf.getContentPane(), BoxLayout.Y_AXIS));
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
		InetAddress addr = null;
		try {
			while(addr == null) {
				if(!jf.isDisplayable()) { return null; }
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
	public static Long showDelayDialogMS(long min, long max) { return showTimeDialog(min, max, "delay"); }
	public static Long showTimeDialog(long min, long max, String verb) {
		JFrame jf = initJF("Please choose a " + verb + "!", 300, 200);
		jf.setLayout(new BorderLayout());
		
		JPanel selBox = new JPanel();
		selBox.setLayout(new GridLayout(2, 2));
		JComboBox<ETimeUnit> unitSpinner = new JComboBox<ETimeUnit>(ETimeUnit.values());
		JSpinner amountSpinner = new JSpinner(new SpinnerNumberModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 10));
		selBox.add(new JLabel("Amount")); selBox.add(amountSpinner);
		selBox.add(new JLabel("Unit"));   selBox.add(unitSpinner);
		jf.add(selBox, BorderLayout.CENTER);
		
		JButtonTracked btnCancel = new JButtonTracked("Cancel");
		JButtonTracked btnSubmit = new JButtonTracked("OK");
		Box controlBx = Box.createHorizontalBox();
		controlBx.add(btnSubmit);
		controlBx.add(btnCancel);
		jf.add(controlBx, BorderLayout.SOUTH);
		
		jf.setVisible(true);
		Long out = null;
		try {
			while(out == null) {
				if(!jf.isDisplayable()) { return null; }
				if(btnSubmit.wasPressed()) {
					Integer spr = (Integer) amountSpinner.getValue();
					if(spr == null)	continue;
					long val = spr.longValue();
					String err = null;
					ETimeUnit unit = (ETimeUnit) unitSpinner.getSelectedItem();
					val *= unit.milliseconds;
					if(val < min || val > max) {
						err = "Value is ";
						long lim = min;
						if(val < min) err += "less then minimum of ";
						else { err = "over the maximum of "; lim = max; }
						err += String.format("%f %s", lim / (float) unit.milliseconds, unit.toString().toLowerCase());
					}
					if(err == null) out = Long.valueOf(val);
					else JOptionPane.showMessageDialog(jf, err);
		
				} else if(btnCancel.wasPressed()) break;
				Thread.sleep(50);
			}
		} catch (InterruptedException e) {}
		jf.dispose();
		return out;
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