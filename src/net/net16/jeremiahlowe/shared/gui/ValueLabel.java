package net.net16.jeremiahlowe.shared.gui;

import javax.swing.JLabel;

public class ValueLabel<T> extends JLabel{
	private T data = null;
	private String unit = null;
	private String name = null;
	private IColorizer<T> colorizer = null;
	
	public ValueLabel(String name, String unit) {
		this(name, null, unit, null);
	}
	public ValueLabel(String name, String unit, IColorizer<T> colorizer) {
		this(name, null, unit, colorizer);
	}
	public ValueLabel(String name, T data, String unit, IColorizer<T> colorizer) {
		super();
		this.colorizer = colorizer;
		this.name = name;
		this.data = data;
		this.unit = unit;
		update();
	}
	
	public void setPrefix(String name) {
		this.name = name;
		update();
	}
	public void setValue(T data) {
		this.data = data;
		update();
	}
	public void setUnit(String unit) {
		this.unit = unit;
		update();
	}
	public String getPrefix() { return name; }
	public T getValue() { return data; }
	public String getUnit() { return unit; }
	
	private void update() {
		String txt = "";
		if(name != null)
			txt += name + ": ";
		if(data != null) {
			txt += data.toString();
			if(unit != null)
				txt += unit;
		} else txt += "N/A";
		if(colorizer != null) {
			super.setBackground(colorizer.getBackground(data));
			super.setForeground(colorizer.getForeground(data));
		}
		super.setText(txt);
	}
}