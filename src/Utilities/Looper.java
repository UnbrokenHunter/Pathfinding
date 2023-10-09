package Utilities;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * <h1>A Class that allows for the easy creation of GameLoops/Runnable Loops.
 * </h1>
 * 
 * <p>
 * 
 * To use, Simply create an object of this class. For the parameters, pass in an
 * object of the calling class, and the name of the method to be called.
 * 
 * <p>
 * <p>
 * 
 * <b>Note: This class uses Java Reflection, and there for it may be unstable.
 * </b>
 * 
 * @see <a href =
 *      "https://www.oracle.com/technical-resources/articles/java/javareflection.html">
 *      Reflection </a>
 * 
 * @author Hunter
 * @version 1.0
 * @since 2022-12-3
 * 
 */
public class Looper implements Runnable {

	private double FPS = 60;
	private static double startTime = 0;

	private final Thread thread;

	private Object obj;
	private String origin;
	private Method methods;

	/**
	 * Creates A Runnable Loop that will call the given method a default of 60 times
	 * per second.
	 * 
	 * @param object An object of the class that contains the given method
	 * @param method The String name of the method to be looped
	 * @param PFS    A double of the desired Frames per Second for the loop to run
	 *               at. If no FPS is given, FPS is set to 60.
	 * 
	 */
	public Looper(Object object, String methods, int FPS) {
		this(object, methods);
		this.FPS = FPS;
	}

	/**
	 * Creates a Runnable Loop that will call the given method a default of 60 times
	 * per second.
	 * 
	 * @param object An object of the class that contains the given method
	 * @param method The String name of the method to be looped
	 * 
	 */
	public Looper(Object object, String method) {

		try {

			// Getting the Calling Class
			boolean initilized = false;
			StackTraceElement[] stElem = Thread.currentThread().getStackTrace();
			for (int i = 1; i < stElem.length; i++) {
				StackTraceElement ste = stElem[i];
				if (!ste.getClassName().equals(Looper.class.getName())
						&& ste.getClassName().indexOf("java.lang.Thread") != 0 && !initilized) {
					this.origin = ste.getClassName();
					initilized = true;
				}
			}

			// Making sure it is not null
			if (!initilized) {
				System.out.println("Failed to get the Class. Program Terminated");
				System.exit(0);
			}

			// Finding the desired method
			boolean methodFound = false;
			Method[] m = Class.forName(origin).getDeclaredMethods();
			for (Method methods : m) {
				if (methods.getName().equals(method)) {
					this.methods = methods;
					methodFound = true;
				}
			}

			// Making sure Method exists
			if (!methodFound) {
				System.out.println("Failed to find the given Method. Try checking the spelling.");
				System.exit(0);
			}

			// Setting the calling object
			obj = object;

		} catch (Exception e) {
			e.printStackTrace();
		}

		// Starting the Thread
		thread = new Thread(this);
		thread.start();
	}

	/**
	 * <b>You are advised not to call this method.</b> Calling run the inputed
	 * method twice every frame.
	 */
	@Override
	public void run() {

		startTime = System.currentTimeMillis();

		double drawInterval = 1000000000 / GetFPS();
		double nextDrawTime = System.nanoTime() + drawInterval;

		while (thread != null) {

			try {

				// Calling the Method
				methods.invoke(obj);

				double remainingTime = nextDrawTime - System.nanoTime();

				remainingTime = remainingTime / 1000000;

				// If the Refresh took too long, then there is 0 time left
				if (remainingTime < 0) {
					remainingTime = 0;
				}

				// Pause the program
				Thread.sleep((long) remainingTime);

				nextDrawTime += drawInterval;

			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				System.out.println("Cannot access this method.");
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

		}

	}

	/**
	 * @return the FPS
	 */
	public double GetFPS() {
		return FPS;
	}

	/**
	 * @param FPS the FPS to set
	 */
	public void SetFPS(double FPS) {
		this.FPS = FPS;
	}

	public static double GetTime() {
		return (System.currentTimeMillis() - startTime) / 1000;
	}

	public static void ResetTime() {
		startTime = System.currentTimeMillis();
	}

}
