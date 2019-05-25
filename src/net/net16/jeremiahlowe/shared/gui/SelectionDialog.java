package net.net16.jeremiahlowe.shared.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class SelectionDialog extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JList<String> ports;
	private JButton btnSelect, btnCancel;
	private boolean waiting = true;
	private boolean invalidateSelection = false;
	
	public static void main(String[] args) {
		String[] cookies = {
				"Normal cookie",
				"Chocalate chip",
				"Chocalate flavored",
				"White macodania",
				"White macodania and Chocalate chip",
				"Peanut butter",
				"Peanut butter and chocalate chips",
				"Fudge glazed peanut butter",
				"Peanut butter and white macodania"
		};
		String[] selections = showMultiSelectionDialog(cookies, null, "Please select some cookies!");
		String sc = "";
		for(String s : selections) sc += "-" + s + System.lineSeparator();
		String pt1 = System.lineSeparator() + "You must be a boring, depressed person since you don't like cookies!";
		String pt2 = " They are:" + System.lineSeparator() + sc;
		String msg = "You selected " + selections.length + " cookies!" + (selections.length > 0 ? pt2 : pt1);
		JOptionPane.showMessageDialog(null, msg, "The cookie monster informs you that", JFrame.EXIT_ON_CLOSE);
	}
	//[start] Single-Selection dialog
	public static String showSelectionDialog(String[] items, Component parent, String title, String selectButtonText){		SelectionDialog frame = new SelectionDialog();
		Rectangle r = new Rectangle();
		r.width = 350; r.height = 200;
		if(parent != null){
			Rectangle r2 = parent.getBounds();
			r.x = (r2.x + r2.width / 2) - (r.width / 2);
			r.y = (r2.y + r2.height / 2) - (r.height / 2);
		}
		else{
			Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
			r.x = ss.width / 2 - r.width / 2;
			r.y = ss.height / 2 - r.height / 2;
		}
		frame.setBounds(r);
		frame.setTitle(title);
		frame.btnSelect.setText(selectButtonText);
		DefaultListModel<String> model = new DefaultListModel<String>();
		for(String item : items) model.addElement(item);
		frame.ports.setModel(model);
		frame.setVisible(true);
		frame.waitForSelection();
		if(frame.invalidateSelection) return null;
		else return frame.ports.getSelectedValue();
	}
	public static String showSelectionDialog(List<String> items, Component parent, String title, String selectButtonText){return showSelectionDialog(items.toArray(new String[0]), parent, title, selectButtonText);}
	public static String showSelectionDialog(String[] items, Component parent, String title){return showSelectionDialog(items, parent, title, "Select");}
	public static String showSelectionDialog(List<String> items, Component parent, String title){return showSelectionDialog(items, parent, title, "Select");}
	public static String showSelectionDialog(String[] items, Component parent){return showSelectionDialog(items, parent, "Please select an item", "Select");}
	public static String showSelectionDialog(List<String> items, Component parent){return showSelectionDialog(items, parent, "Please select an item", "Select");}
	public static String showSelectionDialog(String[] items){return showSelectionDialog(items, null, "Please select an item", "Select");}
	public static String showSelectionDialog(List<String> items){return showSelectionDialog(items, null, "Please select an item", "Select");}
	//[end]
	//[start] Multi-Selection dialog
	public static String[] showMultiSelectionDialog(String[] items, Component parent, String title, String selectButtonText){
		SelectionDialog frame = new SelectionDialog();
		Rectangle r = new Rectangle();
		r.width = 350; r.height = 200;
		if(parent != null){
			Rectangle r2 = parent.getBounds();
			r.x = (r2.x + r2.width / 2) - (r.width / 2);
			r.y = (r2.y + r2.height / 2) - (r.height / 2);
		}
		else{
			Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
			r.x = ss.width / 2 - r.width / 2;
			r.y = ss.height / 2 - r.height / 2;
		}
		frame.setBounds(r);
		frame.setTitle(title);
		frame.btnSelect.setText(selectButtonText);
		DefaultListModel<String> model = new DefaultListModel<String>();
		for(String item : items) model.addElement(item);
		frame.ports.setModel(model);
		frame.ports.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		frame.setVisible(true);
		frame.waitForSelection();
		if(frame.invalidateSelection) return new String[0];
		else return frame.ports.getSelectedValuesList().toArray(new String[0]);
	}
	public static String[] showMultiSelectionDialog(List<String> items, Component parent, String title, String selectButtonText){return showMultiSelectionDialog(items.toArray(new String[0]), parent, title, selectButtonText);}
	public static String[] showMultiSelectionDialog(String[] items, Component parent, String title){return showMultiSelectionDialog(items, parent, title, "Select");}
	public static String[] showMultiSelectionDialog(List<String> items, Component parent, String title){return showMultiSelectionDialog(items, parent, title, "Select");}
	public static String[] showMultiSelectionDialog(String[] items, Component parent){return showMultiSelectionDialog(items, parent, "Please select an item", "Select");}
	public static String[] showMultiSelectionDialog(List<String> items, Component parent){return showMultiSelectionDialog(items, parent, "Please select an item", "Select");}
	public static String[] showMultiSelectionDialog(String[] items){return showMultiSelectionDialog(items, null, "Please select an item", "Select");}
	public static String[] showMultiSelectionDialog(List<String> items){return showMultiSelectionDialog(items, null, "Please select an item", "Select");}
	//[end]
	private SelectionDialog() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				if(waiting) invalidateSelection = true;
				waiting = false;
			}
		});
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 373, 186);
		setAlwaysOnTop(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		ports = new JList<String>();
		ports.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if(ports.getSelectedIndex() != -1) btnSelect.setEnabled(true);
				else btnSelect.setEnabled(false);
			}
		});
		ports.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(ports);
		Box horizontalBox = Box.createHorizontalBox();
		contentPane.add(horizontalBox, BorderLayout.SOUTH);
		btnSelect = new JButton("Select");
		btnSelect.setEnabled(false);
		JFrame ref = this;
		btnSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				waiting = false;
				invalidateSelection = false;
				ref.dispatchEvent(new WindowEvent(ref, WindowEvent.WINDOW_CLOSING));
			}
		});
		horizontalBox.add(btnSelect);
		Component horizontalStrut = Box.createHorizontalStrut(5);
		horizontalBox.add(horizontalStrut);
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				waiting = false;
				invalidateSelection = true;
				ref.dispatchEvent(new WindowEvent(ref, WindowEvent.WINDOW_CLOSING));
			}
		});
		horizontalBox.add(btnCancel);
	}
	public void waitForSelection(){while(waiting){try{Thread.sleep(50);}catch(InterruptedException e) {}}}
}
