package net.net16.jeremiahlowe.shared.math;

public class DoubleVector {
	public double x, y, z;
	
	public DoubleVector(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public DoubleVector(double x, double y) {
		this(x, y, 0);
	}
	public DoubleVector(double x) {
		this(x, 0, 0);
	}
	public DoubleVector() {
		this(0, 0, 0);
	}
	
	public double mag() {
		return Math.sqrt(magSq());
	}
	public double magSq() {
		return x*x + y*y + z*z;
	}
	public double dist(DoubleVector to) {
		return Math.sqrt(distSq(to));
	}
	public double distSq(DoubleVector to) {
		double x2 = to.x - x, y2 = to.y - y;
		x2 *= x2; y2 *= y2;
		return x2 + y2;
	}
	public DoubleVector normalize() {
		double m = mag();
		if(m == 0) {
			x = 0;
			y = 0;
			z = 0;
		}
		else {
			if(m < 0) m *= -1;
			div(new DoubleVector(m, m, m));
		}
		return this;
	}
	public DoubleVector copy() {
		return new DoubleVector(x, y, z);
	}
	
	public DoubleVector div(double by) {
		x /= by;
		y /= by;
		z /= by;
		return this;
	}
	public DoubleVector mult(double by) {
		x *= by;
		y *= by;
		z *= by;
		return this;
	}
	public DoubleVector add(double by) {
		x += by;
		y += by;
		z += by;
		return this;
	}
	public DoubleVector sub(double by) {
		x -= by;
		y -= by;
		z -= by;
		return this;
	}
	public DoubleVector div(DoubleVector by) {
		x /= by.x;
		y /= by.y;
		z /= by.z;
		return this;
	}
	public DoubleVector mult(DoubleVector by) {
		x *= by.x;
		y *= by.y;
		z *= by.z;
		return this;
	}
	public DoubleVector add(DoubleVector by) {
		x += by.x;
		y += by.y;
		z += by.z;
		return this;
	}
	public DoubleVector sub(DoubleVector by) {
		x -= by.x;
		y -= by.y;
		z -= by.z;
		return this;
	}
	public static DoubleVector add(DoubleVector a, DoubleVector b) {
		return new DoubleVector(a.x + b.x, a.y + b.y, a.z + b.y);
	}
	public static DoubleVector add(DoubleVector a, DoubleVector... b) {
		double x = a.x, y = a.y, z = a.z;
		for(DoubleVector v : b) {
			if(v != null) {
				x += v.x;
				y += v.y;
				z += v.z;
			}
		}
		return new DoubleVector(x, y, z);
	}
	public static DoubleVector sub(DoubleVector a, DoubleVector b) {
		return new DoubleVector(a.x - b.x, a.y - b.y, a.z - b.y);
	}
	public static DoubleVector sub(DoubleVector a, DoubleVector... b) {
		double x = a.x, y = a.y, z = a.z;
		for(DoubleVector v : b) {
			if(v != null) {
				x -= v.x;
				y -= v.y;
				z -= v.z;
			}
		}
		return new DoubleVector(x, y, z);
	}
	public static DoubleVector mult(DoubleVector a, DoubleVector b) {
		return new DoubleVector(a.x * b.x, a.y * b.y, a.z * b.y);
	}
	public static DoubleVector mult(DoubleVector a, DoubleVector... b) {
		double x = a.x, y = a.y, z = a.z;
		for(DoubleVector v : b) {
			if(v != null) {
				x *= v.x;
				y *= v.y;
				z *= v.z;
			}
		}
		return new DoubleVector(x, y, z);
	}
	public static DoubleVector div(DoubleVector a, DoubleVector b) {
		return new DoubleVector(a.x / b.x, a.y / b.y, a.z / b.y);
	}
	public static DoubleVector div(DoubleVector a, DoubleVector... b) {
		double x = a.x, y = a.y, z = a.z;
		for(DoubleVector v : b) {
			if(v != null) {
				x /= v.x;
				y /= v.y;
				z /= v.z;
			}
		}
		return new DoubleVector(x, y, z);
	}
	public DoubleVector rotate(double a) {
		double nx = x * Math.cos(a) - y * Math.sin(a);
		double ny = x * Math.sin(a) + y * Math.cos(a);
		this.x = nx; this.y = ny;
		return this;
	}
	public double atan2() {
		return Math.atan2(y, x);
	}
	public static DoubleVector fromAngle(double a) {
		return new DoubleVector(Math.cos(a), Math.sin(a));
	}
}
