package net.net16.jeremiahlowe.shared.gui;

import java.awt.Color;

public interface IColorizer<T> {
	public Color getBackground(T value);
	public Color getForeground(T value);
	
	public abstract class NoBackgroundColorizer<T> implements IColorizer<T> {
		@Override public Color getBackground(T value) { return null; }
	}
	public class RangeColorizer extends NoBackgroundColorizer<Number> {
		private final Number min, max, warnMin, warnMax;
		public RangeColorizer(Number min, Number max) {
			this(min, max, null, null);
		}
		public RangeColorizer(Number min, Number max, Number warnMin, Number warnMax) {
			this.min = min;
			this.max = max;
			this.warnMax = warnMax;
			this.warnMin = warnMin;
		}
		@Override public Color getForeground(Number val) {
			if(val == null)
				return null;
			float vf = val.floatValue();
			if(min != null && vf < min.floatValue())
				return Color.RED.darker();
			if(max != null && vf > max.floatValue())
				return Color.RED.darker();
			if(warnMin != null && vf < warnMin.floatValue())
				return Color.ORANGE.darker();
			if(warnMax != null && vf > warnMax.floatValue())
				return Color.ORANGE.darker();
			return Color.GREEN.darker();
		}
	}
	public class TemperatureColorizer extends NoBackgroundColorizer<Number> {
		public int tOverheat = 70;
		public int tHot = 50;
		public int tWarm = 30;
		public int tRoom = 20;
		public int tCool = 10;
		public int tCold = 0;
		@Override public Color getForeground(Number value) {
			if(value == null)
				return null;
			float t = value.floatValue();
			if(t > tOverheat)
				return Color.RED.darker();
			if(t > tHot)
				return Color.ORANGE.darker();
			if(t > tWarm)
				return Color.YELLOW.darker();
			if(t > tRoom)
				return Color.GREEN.darker();
			if(t > tCool)
				return Color.BLUE.darker();
			if(t > tCold)
				return Color.ORANGE.darker();
			return Color.RED.darker();
		}
	}
}
