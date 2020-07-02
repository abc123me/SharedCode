package net.net16.jeremiahlowe.shared.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

public class BrowseButton extends JButton implements ActionListener {
	private static final int MODE_MASK = 0xF;
	public static final int FILE_OR_DIRECTORY = 0;
	public static final int DIRECTORY = 1;
	public static final int FILE = 2;
	
	public static final int MULTIPLE_SELECT = 0x80;
	public static final int DIRECTORIES = DIRECTORY | MULTIPLE_SELECT;
	public static final int FILES = FILE | MULTIPLE_SELECT;
	public static final int ANYTHING = FILE_OR_DIRECTORY | MULTIPLE_SELECT;
	
	private final JTextField field;
	private int mode;
	private JFileChooser fc;
	
	public BrowseButton(int mode, JTextField field) {
		super("...");
		this.field = field;
		super.addActionListener(this);
		fc = new JFileChooser();
		fc.addActionListener(this);
		setMode(mode);
	}

	public void setMode(int mode) {
		fc.setMultiSelectionEnabled((mode & MULTIPLE_SELECT) != 0);
		switch(mode & MODE_MASK) {
			case DIRECTORY: fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); break; 
			case FILE: fc.setFileSelectionMode(JFileChooser.FILES_ONLY); break;
			default: fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); break;
		}
		this.mode = mode;
	}
	public int getMode() { return mode; }
	
	@Override public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == fc)
			field.setText(fc.getSelectedFile().toPath().toString());
		else
			fc.showOpenDialog(field.getParent());
	}
	@Override public void addActionListener(ActionListener l) {
		fc.addActionListener(l);
	}
	@Override public void removeActionListener(ActionListener l) {
		fc.removeActionListener(l);
	}
}
