package net.net16.jeremiahlowe.shared.math;

public class VectorMath {
	//Returns the 1, 0, -1 identity of a vector
	public static final Vector identity(Vector of) {
		Vector out = new Vector(0, 0, 0);
		if (of.x != 0)
			out.x = of.x > 0 ? 1 : -1;
		if (of.y != 0)
			out.y = of.y > 0 ? 1 : -1;
		if (of.z != 0)
			out.z = of.z > 0 ? 1 : -1;
		return out;
	}
	//Negates a vector
	public static final Vector negate(Vector of) {
		Vector out = new Vector();
		out.x = of.x * -1;
		out.y = of.y * -1;
		out.z = of.z * -1;
		return out;
	}
	//Gets the maximum positions of 2 vectors
	public static final Vector max(Vector a, Vector b) {
		Vector o = new Vector();
		o.x = GeneralMath.max(a.x, b.x);
		o.y = GeneralMath.max(a.y, b.y);
		o.z = GeneralMath.max(a.z, b.z);
		return o;
	}
	//Gets the minimum positions of 2 vectors
	public static final Vector min(Vector a, Vector b) {
		Vector o = new Vector();
		o.x = GeneralMath.min(a.x, b.x);
		o.y = GeneralMath.min(a.y, b.y);
		o.z = GeneralMath.min(a.z, b.z);
		return o;
	}
	//Limits the positions of a pvector to a minimum and maximum vector
	public static final Vector clamp(Vector p, Vector min, Vector max) {
		Vector o = new Vector();
		o.x = GeneralMath.clamp(p.x, min.x, max.x);
		o.y = GeneralMath.clamp(p.y, min.y, max.y);
		o.z = GeneralMath.clamp(p.z, min.z, max.z);
		return o;
	}
	//Checks if a point is inside of a rectange
	public static final boolean inside(Vector pos, Vector start, Vector end) {
		Vector max = new Vector(max(start, end));
		Vector min = new Vector(min(start, end));
		if (pos.x >= min.x && pos.x <= max.x)
			if (pos.y >= min.y && pos.y <= max.y)
				return true;
		return false;
	}
	//Gets the slope between two vectors
	public static final float slope(Vector v1, Vector v2) {
		float x = v2.x - v1.x, y = v2.y - v1.y;
		if (x == 0) {
			if (y > 0)
				return Float.POSITIVE_INFINITY;
			else
				return Float.NEGATIVE_INFINITY;
		}
		return y / x;
	}
	//Checks if a point is inside a polygon (defined by its verticies
	//This will work on any convex polygon, however is untested on concave polygons
	public static final boolean insidePolygon(Vector test, Vector... ve) {
		if (ve.length < 3)
			throw new RuntimeException("Polygon must have 3 or more verticies!");
		float a = 0, ta = polygonArea(ve);
		for (int i = 0; i < ve.length; i++)
			a += polygonArea(ve[i], test, ve[GeneralMath.wrap(i + 1, ve.length)]);
		return a == ta;
	}
	//Checks to see how far a point is from a polygon
	public static final float distFromPolygon(Vector test, Vector... ve) {
		return GeneralMath.sqrt(dist2FromPolygon(test, ve));
	}
	//Checks to see how far a point is from a polygon, returns the dist^2
	public static final float dist2FromPolygon(Vector test, Vector... ve) {
		if (ve.length < 3)
			throw new RuntimeException("Polygon must have 3 or more verticies!");
		int cv1 = -1, cv2 = -1;
		float md = Float.MAX_VALUE, md2 = Float.MAX_VALUE;
		for (int i = 0; i < ve.length; i++) {
			float d2 = dist2(test, ve[i]);
			if (d2 < md) {
				md = d2;
				cv1 = i;
			}
		}
		for (int i = 0; i < ve.length; i++) {
			float d2 = dist2(test, ve[i]);
			if (cv1 == i)
				continue;
			if (d2 < md2) {
				md2 = d2;
				cv2 = i;
			}
		}
		if (cv1 < 0 || cv2 < 0)
			throw new RuntimeException(String.format("What the literal fuck happened? (cv1 < 0 || cv2 < 0) cv1=%d cv2=%d", cv1, cv2));
		Vector a = ve[cv1], b = ve[cv2];
		float mx = b.x - a.x, my = b.y - a.y;
		Vector e;
		if (mx == 0) {
			float m = mx / my;
			float xi = a.x - m * a.y;
			e = new Vector(test.y * m + xi, test.y);
		} else {
			float m = my / mx;
			float yi = a.y - m * a.x;
			e = new Vector(test.x, test.x * m + yi);
		}
		e = clamp(e, min(a, b), max(a, b));
		return dist2(test, e);
	}
	//Gets a polygons area using the shoelace formula (https://en.wikipedia.org/wiki/Shoelace_formula)
	//This will work on any convex polygon, however is untested on concave polygons
	public static final float polygonArea(Vector... ve) {
		if (ve.length < 3)
			throw new RuntimeException("Polygon must have 3 or more verticies!");
		float a = 0;
		for (int j = 1; j >= -1; j -= 2) {
			for (int i = 0; i < ve.length; i++) {
				int x = i, y = GeneralMath.wrap(i + 1, ve.length);
				if (j < 0) {
					int t = x;
					x = y;
					y = t;
				}
				a += j * ve[x].x * ve[y].y;
			}
		}
		if (a < 0)
			a *= -0.5;
		else
			a *= 0.5;
		return a;
	}
	//Midpoint of two pvectors
	public static final Vector mid(Vector a, Vector b) {
		return Vector.add(a, b).div(2);
	}
	//Squared distance bettwen 2 vectors
	public static final float dist2(Vector a, Vector b) {
		float x = b.x - a.x, y = b.y - a.y;
		x *= x;
		y *= y;
		return x + y;
	}
	public static final Vector getBoundingBox(Vector... verticies){
		Vector out = new Vector(Float.MIN_VALUE, Float.MIN_VALUE);
		for(int i = 0; i < verticies.length; i++){
			if(verticies[i].x > out.x) out.x = verticies[i].x;
			if(verticies[i].y > out.y) out.y = verticies[i].y;
		}
		return out;
	}
	public static final Vector getCenter(int x, int y, int w, int h){
		return new Vector((x + w) / 2, (y + h) / 2);
	}
	public static final Vector getMidpoint(Vector... verticies){
		Vector origin = new Vector();
		for(int i = 0; i < verticies.length; i++) origin.translate(verticies[i]);
		origin.x /= verticies.length; origin.y /= verticies.length;
		return origin;
	}
	public static final Vector[] rotateVerticiesAround(Rotation rot, Vector origin, Vector... verticies){
		if(rot.getAngleDegrees() == 360 || rot.getAngleDegrees() == 0) return verticies;
		else{
			Vector[] out = new Vector[verticies.length];
			float angle = rot.getAngleRadians();
			for(int i = 0; i < out.length; i++){
				double x1 = verticies[i].x - origin.x, y1 = verticies[i].y - origin.y;
				double x2 = x1 * Math.cos(angle) - y1 * Math.sin(angle);
				double y2 = x1 * Math.sin(angle) + y1 * Math.cos(angle);
				out[i] = new Vector();
				out[i].x = (float) x2 + origin.x;
				out[i].y = (float) y2 + origin.y;
			}
			return out;
		}
	}
	public static final Vector[] rotateVerticiesAroundMidpoint(Rotation rot, Vector... verticies){
		return rotateVerticiesAround(rot, getMidpoint(verticies), verticies);
	}
	public static final double slopeXY(Vector a, Vector b, boolean highNumOverInf){
		float x = b.x - a.x, y = a.y - b.y;
		if(y == 0){
			if(highNumOverInf) return 1e10;
			else return Double.POSITIVE_INFINITY;
		}
		return x / y;
	}
	public static final float slopeYX(Vector a, Vector b, boolean highNumOverInf){
		float x = b.x - a.x, y = a.y - b.y;
		if(x == 0){
			if(highNumOverInf) return Float.MAX_VALUE;
			else return Float.POSITIVE_INFINITY;
		}
		return y / x;
	}
	public static final float yIntercept(Vector a, Vector b){
		float m = slopeYX(a, b, false);
		return a.y - (m * b.x);
	}
	public static final Vector[] combineXYArrays(float[] x, float[] y){
		if(x.length != y.length)
			throw new RuntimeException("X and Y Arrays must be same in size");
		int len = x.length;
		Vector[] out = new Vector[len];
		for(int i = 0; i < len; i++)
			out[i] = new Vector(x[i], y[i]);
		return out;
	}
}
