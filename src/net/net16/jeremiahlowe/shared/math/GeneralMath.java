package net.net16.jeremiahlowe.shared.math;

import java.util.Random;

public class GeneralMath {
	public static float PI = (float) java.lang.Math.PI;
	public static float TWO_PI = 2 * (float) java.lang.Math.PI;
	
	private static final Random rng = new Random();
	
	//Gets the square root of a float
	public static final float sqrt(float of) {
		return (float) java.lang.Math.sqrt(of);
	}
	//Gets the maximum of two numbers
	public static final float max(float a, float b) {
		return a > b ? a : b;
	}
	//Gets the maximum of two numbers
	public static final float min(float a, float b) {
		return a < b ? a : b;
	}
	//Wraps an integer around a maximum value
	public static final int wrap(int val, int max) {
		while (val >= max)
			val -= max;
		return val;
	}
	public static final float random() {
		return random(1);
	}
	public static final float random(float max) {
		return random(0, max);
	}
	public static final float random(float min, float max) {
		return rng.nextFloat() * (max - min) + min;
	}
	public static final void setRandomSeed(long seed) {
		rng.setSeed(seed);
	}
	public static final Random getRandom() {
		return rng;
	}
	public static float normalizeAngle(float angle) {
		while(angle < 0)
			angle += TWO_PI;
		while(angle >= TWO_PI)
			angle -= TWO_PI;
		return angle;
	}
	public static final boolean between(long max, long min, long val){
		if(val >= max || val <= min) return false;
		else return true;
	}
	public static final boolean between(int max, int min, int val){
		if(val >= max || val <= min) return false;
		else return true;
	}
	public static final boolean between(float max, float min, float val){
		if(val >= max || val <= min) return false;
		else return true;
	}
	public static final boolean between(double max, double min, double val){
		if(val >= max || val <= min) return false;
		else return true;
	}
	public static double clamp(double min, double max, double val){
		if(val > max) return max;
		else if(val < min) return min;
		else return val;
	}
	public static long clamp(long min, long max, long val){
		if(val > max) return max;
		else if(val < min) return min;
		else return val;
	}
	public static float clamp(float min, float max, float val){
		if(val > max) return max;
		else if(val < min) return min;
		else return val;
	}
	public static int clamp(int min, int max, int val){
		if(val > max) return max;
		else if(val < min) return min;
		else return val;
	}
	public static final boolean betweenOrEqual(double max, double min, double val){
		if(val > max || val < min) return false;
		else return true;
	}
	public static final boolean betweenOrEqual(float max, float min, float val){
		if(val > max || val < min) return false;
		else return true;
	}
	public static final boolean betweenOrEqual(int max, int min, int val){
		if(val > max || val < min) return false;
		else return true;
	}
	public static final boolean betweenOrEqual(long max, long min, long val){
		if(val > max || val < min) return false;
		else return true;
	}
}
