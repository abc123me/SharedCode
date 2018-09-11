package net.net16.jeremiahlowe.shared.math;

public class Viewport {
	public float x, y, w, h;

	public Viewport() {
		this(0, 0);
	}
	public Viewport(float w, float h) {
		this(w, h, 0, 0);
	}
	public Viewport(float w, float h, float x, float y) {
		this.w = w;
		this.h = h;
		setOffset(x, y);
	}

	public void translate(float x, float y) {
		this.x += x;
		this.y += y;
	}
	public void setOffset(float x, float y) {
		this.x = x;
		this.y = y;
	}
	public void setOffset(Vector off) {
		setOffset(off.x, off.y);
	}
	public float transformX(float x, Viewport to) {
		return ((x - this.x) / w) * to.w + to.x;
	}
	public float transformY(float y, Viewport to) {
		return ((y - this.y) / h) * to.h + to.y;
	}
	public Vector transform(Vector v, Viewport to) {
		float outX = transformX(v.x, to);
		float outY = transformY(v.y, to);
		return new Vector(outX, outY);
	}
	public Vector transformIgnoreOffset(Vector v, Viewport to) {
		float outX = (v.x / w) * to.w;
		float outY = (v.y / h) * to.h;
		return new Vector(outX, outY);
	}
	public Vector getSize() {
		return new Vector(w, h);
	}
	public Vector getOffset() {
		return new Vector(x, y);
	}
	public Vector center() {
		return new Vector(w / 2, h / 2);
	}
	public float aspRatio() {
		float a = w / h;
		if(a < 0) a *= -1;
		return a;
	}
	public void zoom(float a) {
		w += a;
		h += a;
	}
	public void zoom(float a, float aspRatio) {
		w += a * aspRatio;
		h += a;
	}
	public Vector[] transformAll(Viewport screen, Vector... ve) {
		Vector[] out = new Vector[ve.length];
		for(int i = 0; i < ve.length; i++)
			out[i] = transform(ve[i], screen);
		return out;
	}
	public Viewport translate(Vector by) {
		x += by.x;
		y += by.y;
		return this;
	}
	public Vector range() {
		return new Vector(y - h / 2, y + h / 2);
	}
	public Vector domain() {
		return new Vector(x - w / 2, x + w / 2);
	}
	public float absHeight() {
		if(h < 0)
			return h * -1;
		return h;
	}
	public float absWidth() {
		if(w < 0)
			return w * -1;
		return w;
	}
}
