package net.net16.jeremiahlowe.shared.math;

public class Vector {
	public float x, y, z;
	
	public Vector(Vector v) {
		this(v.x, v.y, v.z);
	}
	public Vector(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public Vector(float x, float y) {
		this(x, y, 0);
	}
	public Vector(float x) {
		this(x, 0, 0);
	}
	public Vector() {
		this(0, 0, 0);
	}
	
	public float mag() {
		return (float)Math.sqrt(magSq());
	}
	public float magSq() {
		return x*x + y*y + z*z;
	}
	public float dist(Vector to) {
		return (float)Math.sqrt(distSq(to));
	}
	public float distSq(Vector to) {
		float x2 = to.x - x, y2 = to.y - y;
		x2 *= x2; y2 *= y2;
		return x2 + y2;
	}
	public Vector normalize() {
		float m = mag();
		if(m == 0) {
			x = 0;
			y = 0;
			z = 0;
		}
		else {
			if(m < 0) m *= -1;
			div(new Vector(m, m, m));
		}
		return this;
	}
	public Vector copy() {
		return new Vector(x, y, z);
	}
	
	public Vector div(float by) {
		x /= by;
		y /= by;
		z /= by;
		return this;
	}
	public Vector mult(float by) {
		x *= by;
		y *= by;
		z *= by;
		return this;
	}
	public Vector add(float by) {
		x += by;
		y += by;
		z += by;
		return this;
	}
	public Vector sub(float by) {
		x -= by;
		y -= by;
		z -= by;
		return this;
	}
	public Vector div(Vector by) {
		x /= by.x;
		y /= by.y;
		z /= by.z;
		return this;
	}
	public Vector mult(Vector by) {
		x *= by.x;
		y *= by.y;
		z *= by.z;
		return this;
	}
	public Vector add(Vector by) {
		x += by.x;
		y += by.y;
		z += by.z;
		return this;
	}
	public Vector sub(Vector by) {
		x -= by.x;
		y -= by.y;
		z -= by.z;
		return this;
	}
	public static Vector add(Vector a, Vector b) {
		return new Vector(a.x + b.x, a.y + b.y, a.z + b.y);
	}
	public static Vector add(Vector a, Vector... b) {
		float x = a.x, y = a.y, z = a.z;
		for(Vector v : b) {
			if(v != null) {
				x += v.x;
				y += v.y;
				z += v.z;
			}
		}
		return new Vector(x, y, z);
	}
	public static Vector sub(Vector a, Vector b) {
		return new Vector(a.x - b.x, a.y - b.y, a.z - b.y);
	}
	public static Vector sub(Vector a, Vector... b) {
		float x = a.x, y = a.y, z = a.z;
		for(Vector v : b) {
			if(v != null) {
				x -= v.x;
				y -= v.y;
				z -= v.z;
			}
		}
		return new Vector(x, y, z);
	}
	public static Vector mult(Vector a, Vector b) {
		return new Vector(a.x * b.x, a.y * b.y, a.z * b.y);
	}
	public static Vector mult(Vector a, Vector... b) {
		float x = a.x, y = a.y, z = a.z;
		for(Vector v : b) {
			if(v != null) {
				x *= v.x;
				y *= v.y;
				z *= v.z;
			}
		}
		return new Vector(x, y, z);
	}
	public static Vector div(Vector a, Vector b) {
		return new Vector(a.x / b.x, a.y / b.y, a.z / b.y);
	}
	public static Vector div(Vector a, Vector... b) {
		float x = a.x, y = a.y, z = a.z;
		for(Vector v : b) {
			if(v != null) {
				x /= v.x;
				y /= v.y;
				z /= v.z;
			}
		}
		return new Vector(x, y, z);
	}
	public Vector rotate(float a) {
		float nx = x * (float)Math.cos(a) - y * (float)Math.sin(a);
		float ny = x * (float)Math.sin(a) + y * (float)Math.cos(a);
		this.x = nx; this.y = ny;
		return this;
	}
	public float atan2() {
		return (float)Math.atan2(y, x);
	}
	public static Vector fromAngle(float a) {
		return new Vector((float)Math.cos(a), (float)Math.sin(a));
	}
	public int getXInt() {
		return (int) x;
	}
	public int getYInt() {
		return (int) y;
	}
	public int getZInt() {
		return (int) z;
	}
	public Vector negate() {
		x *= -1;
		y *= -1;
		z *= -1;
		return this;
	}
	public Vector translate(Vector by) {
		x += by.x;
		y += by.y;
		z += by.z;
		return this;
	}
	
	@Override
	public String toString() {
		return "Vector: [" + x + ", " + y + ", " + z + "]";
	}
}
