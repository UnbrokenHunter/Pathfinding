package Utilities;

public class DrawButtons {

	private static int lastX = 0;
	private static int lastY = 0;

	public static void drawNeat(int x, int y) {

		if (lastX == 0) {
			lastX = x;
			lastY = y;

		} else {

			System.out.println("// Type");
			System.out.println("if(" + lastX + " < me.getX() && me.getX() < " + x + ") {");
			System.out.println("	if(" + lastY + " < me.getY() && me.getY() < " + y + ")");
			System.out.println("\n}");

			System.out.println("\n--------------------------\n");

			lastX = 0;
		}
	}
	
	public static void draw(int x, int y) {

		if (lastX == 0) {
			lastX = x;
			lastY = y;

		} else {

			System.out.println("// Type");
			System.out.println("if((" + lastX + " < me.getX() && me.getX() < " + x + ") && (" + lastY + " < me.getY() && me.getY() < " + y  + ") ){");
			System.out.println("\n}");

			lastX = 0;
		}
	}


}