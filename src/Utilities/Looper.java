package Utilities;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * <h3>Looper - Game Loop Utility</h3>
 *
 * The `Looper` class facilitates the creation of game loops or any other loop
 * that requires running a method at a fixed frame rate. It wraps the loop logic
 * inside a dedicated thread, ensuring that the target frame rate is achieved as
 * closely as possible.
 *
 * <p>
 * <b>Usage:</b>
 * To utilize the `Looper`, instantiate it with the method of the class you want
 * to call continuously. The Looper will automatically begin executing the loop
 * upon instantiation.
 * </p>
 *
 * <p>
 * <b>Example:</b>
 * <code>
 * Looper myLooper = new Looper(this, "myUpdateMethod", 60);
 * </code>
 * This will call `myUpdateMethod` in the current object at approximately 60
 * frames
 * per second.
 * </p>
 *
 * <p>
 * <b>Note:</b> This class leverages Java's reflection capabilities to call the
 * desired method. While powerful, reflection introduces some runtime overhead.
 * Therefore, performance-critical applications might require an alternative
 * implementation approach.
 * </p>
 *
 * @see <a href=
 *      "https://www.oracle.com/technical-resources/articles/java/javareflection.html">Reflection
 *      in Java</a>
 * @see <a href=
 *      "https://docs.oracle.com/javase/8/docs/api/java/lang/Runnable.html">Runnable
 *      Interfaces in Java
 *
 * @author Hunter
 * @version 1.0
 * @since 2022-12-3
 */
public final class Looper implements Runnable {

	// The target object containing the method to be looped.
	private final Object obj;

	// The method to be called on each loop iteration.
	private final Method method;

	// The desired frames per second.
	private double FPS = 60;

	// The dedicated thread for the loop.
	private Thread thread;

	// Flag to control the loop's running state.
	private volatile static boolean isRunning = false;

	// Time tracker for loop duration.
	private static double startTime = System.currentTimeMillis();

	/**
	 * Constructs a new Looper with the specified method and frame rate.
	 *
	 * @param object     The object containing the method to loop.
	 * @param methodName The name of the method to loop.
	 * @param fps        The desired frame rate.
	 */
	public Looper(Object object, String methodName, double fps) {
		this.obj = object;
		try {
			this.method = object.getClass().getDeclaredMethod(methodName);
			this.method.setAccessible(true);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("Method '" + methodName + "' not found in object " + object.getClass().getName(),
					e);
		}
		this.FPS = fps;
		startLoop();
	}

	/**
	 * Constructs a new Looper with the specified method and a default frame rate of
	 * 60 FPS.
	 *
	 * @param object     The object containing the method to loop.
	 * @param methodName The name of the method to loop.
	 */
	public Looper(Object object, String methodName) {
		this(object, methodName, 60);
	}

	/**
	 * Returns the current frame rate of the loop.
	 *
	 * @return The current frame rate.
	 */
	public synchronized double getFPS() {
		return FPS;
	}

	/**
	 * Sets the desired frame rate for the loop.
	 *
	 * @param fps The desired frame rate.
	 */
	public synchronized void setFPS(double fps) {
		this.FPS = fps;
	}

	/**
	 * Retrieves the elapsed time since the loop started, in seconds.
	 *
	 * @return The elapsed time in seconds.
	 */
	public static double getTime() {
		return (System.currentTimeMillis() - startTime) / 1000.0;
	}

	/**
	 * Resets the internal timer.
	 */
	public static void resetTime() {
		startTime = System.currentTimeMillis();
	}

	/**
	 * Initiates the loop. If the loop is already running, this method does nothing.
	 */
	public void startLoop() {
		if (!isRunning) {
			isRunning = true;
			thread = new Thread(this);
			thread.start();
		}
	}

	/**
	 * Stops the loop. If the loop is not running, this method does nothing.
	 */
	public void stopLoop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new RuntimeException("Error stopping the loop: ", e);
		}
	}

	/**
	 * Checks if the loop is currently running.
	 *
	 * @return True if the loop is running, false otherwise.
	 */
	public static boolean isLoopRunning() {
		return isRunning;
	}

	/**
	 * Main loop logic responsible for repeatedly invoking the specified method at
	 * the set frame rate.
	 * <p>
	 * This method encapsulates the core functionality of the Looper. At the start
	 * of each cycle,
	 * the method computes the desired interval based on the set FPS (frames per
	 * second). It then
	 * checks the elapsed time to maintain a steady execution pace, ensuring the
	 * specified method
	 * is called at the desired frequency.
	 * </p>
	 * <p>
	 * If the desired frame rate is too high or the invoked method takes too long to
	 * execute,
	 * the method prints a warning to adjust the FPS or optimize the loop method.
	 * </p>
	 * <p>
	 * <b>IMPORTANT:</b> This method is invoked automatically when a new instance of
	 * the Looper
	 * is created, thanks to the dedicated threading mechanism. Manual calls to this
	 * method
	 * are <b>STRONGLY DISCOURAGED</b>, as they can disrupt the loop's timing or
	 * cause other
	 * unintended side effects. Instead, rely on the Looper's automated control
	 * flow.
	 * </p>
	 *
	 * @see Looper#startLoop()
	 * @see Looper#stopLoop()
	 *
	 * @throws RuntimeException If there's an error invoking the specified method or
	 *                          if the
	 *                          Looper's execution is interrupted unexpectedly.
	 */
	@Override
	public void run() {
		// Determine the interval of time (in nanoseconds) between each loop iteration
		// based on the desired FPS.
		double drawInterval = 1000000000 / getFPS();

		// Compute the next scheduled execution time for the method in nanoseconds.
		double nextDrawTime = System.nanoTime() + drawInterval;

		// Continue looping as long as the loop is set to be running.
		while (isRunning) {
			try {
				// Use reflection to invoke the specified method on the given object.
				method.invoke(obj);

				// Calculate the remaining time (in nanoseconds) before the next loop iteration.
				double remainingTime = nextDrawTime - System.nanoTime();
				// Convert the remaining time to milliseconds for the Thread.sleep() call.
				remainingTime /= 1000000;

				// If there's no time left (or we're behind), set remaining time to zero and
				// warn about the unachievable FPS.
				if (remainingTime < 0) {
					remainingTime = 0;
					System.out.println("Warning: Desired FPS is not achievable. Adjust FPS or optimize loop method.");
				}

				// Sleep the thread for the remaining time, ensuring the loop runs at the
				// desired FPS.
				Thread.sleep((long) remainingTime);

				// Adjust the next scheduled execution time by adding the interval.
				nextDrawTime += drawInterval;

				// Catch exceptions that might occur when invoking the method using reflection.
			} catch (InvocationTargetException | IllegalAccessException e) {
				// If an exception occurs, stop the loop and rethrow as a runtime exception.
				stopLoop();
				throw new RuntimeException("Error in the Looper execution: ", e);

				// Catch interruptions to the loop (e.g., from external threads).
			} catch (InterruptedException e) {
				// If the loop is interrupted, re-interrupt the current thread and throw a
				// runtime exception.
				Thread.currentThread().interrupt();
				throw new RuntimeException("Looper was interrupted: ", e);
			}
		}
	}
}
