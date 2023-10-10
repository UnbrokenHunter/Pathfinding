package Utilities;

import java.util.Objects;

/**
 * Represents a 2D vector with x and y coordinates.
 */
public class Vector2 {

	/**
	 * A static Vector2 that contains the value: (x: 1, y: 1)
	 */
	public static final Vector2 one = new Vector2(1, 1);

	/**
	 * A static Vector2 that contains the value: (x: 0, y: 0)
	 */
	public static final Vector2 zero = new Vector2(0, 0);

	/**
	 * A static Vector2 that contains the value: (x: 0, y: 1)
	 */
	public static final Vector2 up = new Vector2(0, 1);

	/**
	 * A static Vector2 that contains the value: (x: 0, y: -1)
	 */
	public static final Vector2 down = new Vector2(0, -1);

	/**
	 * A static Vector2 that contains the value: (x: -1, y: 0)
	 */
	public static final Vector2 left = new Vector2(-1, 0);

	/**
	 * A static Vector2 that contains the value: (x: 1, y: 0)
	 */
	public static final Vector2 right = new Vector2(1, 0);

	public int x;
	public int y;

	/**
	 * Default constructor. Initializes the vector with both x and y set to 0.
	 */
	public Vector2() {
		this.x = 0;
		this.y = 0;
	}

	/**
	 * Parameterized constructor. Initializes the vector with the provided x and y
	 * values.
	 *
	 * @param x The x-coordinate of the vector.
	 * @param y The y-coordinate of the vector.
	 */
	public Vector2(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Add the vectors together.
	 * 
	 * @param vector2 the value to be added to the original.
	 * @return Returns a new Vector2 that has the original value, plus the new
	 *         value.
	 */
	public Vector2 plus(Vector2 vector2) {
		return new Vector2(x + vector2.x, y + vector2.y);
	}

	/**
	 * Subtracts the vectors.
	 * 
	 * @param vector2 the value to be subtracted from the original.
	 * @return Returns a new Vector2 that has the original value, minus the new
	 *         value.
	 */
	public Vector2 minus(Vector2 vector2) {
		return new Vector2(x - vector2.x, y - vector2.y);
	}

	/**
	 * Multiplies both x and y by the number.
	 * 
	 * @param num The number to multiply the vector by.
	 * @return Returns a new Vector2, with each value being multiplied by the number
	 *         in the parameter.
	 */
	public Vector2 mult(int num) {
		return new Vector2(x * num, y * num);
	}

	/**
	 * Divides both x and y by the number.
	 * 
	 * @param num The number to divide the vector by.
	 * @return Returns a new Vector2, with each value being divided by the number
	 *         in the parameter.
	 */
	public Vector2 div(int num) {
		return new Vector2(x / num, y / num);
	}

	/**
	 * Computes the magnitude (length) of the vector.
	 * 
	 * <p>
	 * The magnitude is the Euclidean distance from this vector to the origin
	 * (0, 0). It provides a measure of the "length" or "size" of the vector,
	 * irrespective of its direction. The magnitude is always non-negative and
	 * gives the straight-line distance between the point represented by this vector
	 * and the origin.
	 * </p>
	 * 
	 * <p>
	 * This implementation leverages the {@link #distance(Vector2, Vector2)}
	 * method by calculating the distance between the vector and the origin,
	 * represented by {@code Vector2.zero}.
	 * </p>
	 *
	 * @return The magnitude of the vector.
	 */
	public double magnitude() {
		return distance(Vector2.zero, this);
	}

	/**
	 * Calculates the Euclidean distance between two points represented by
	 * {@code Vector2} objects.
	 * The distance is computed using the formula:
	 * distance = sqrt((end.x - start.x)^2 + (end.y - start.y)^2).
	 * 
	 * @param start The starting point represented as a Vector2 object. Must not be
	 *              null.
	 * @param end   The ending point represented as a Vector2 object. Must not be
	 *              null.
	 * @return The Euclidean distance between the two points.
	 * @throws IllegalArgumentException if either {@code start} or {@code end} is
	 *                                  null.
	 */
	public static double distance(Vector2 start, Vector2 end) {

		if (start == null || end == null) {
			throw new IllegalArgumentException("Vectors must not be null");
		}

		var x = Math.pow((end.x - start.x), 2);
		var y = Math.pow((end.y - start.y), 2);

		return Math.sqrt(x + y);
	}

	/**
	 * Binarizes the components of the vector.
	 * <p>
	 * This method converts each component of the vector to its corresponding sign:
	 * - 1 if the component is positive
	 * - -1 if the component is negative
	 * - 0 if the component is zero
	 * </p>
	 * 
	 * @return A new {@code Vector2} object where each component is set to its
	 *         corresponding sign.
	 *         If both components of the original vector are zero, the method
	 *         returns a zero vector.
	 */
	public Vector2 binarize() {

		if (x == 0 && y == 0) {
			return Vector2.zero;
		}

		return new Vector2((int) Math.signum(x), (int) Math.signum(y));
	}

	/**
	 * Returns a hash code value for this Vector2 object.
	 * This implementation is based on the x and y coordinates.
	 *
	 * @return A hash code value for this object.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	/**
	 * Compares this vector to the specified object for equality.
	 * The result is true if and only if the argument is not null
	 * and is a Vector2 object that represents the same (x, y) coordinate as this
	 * object.
	 *
	 * @param obj The object to compare with.
	 * @return True if the given object represents a Vector2 equivalent to this
	 *         vector, false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Vector2 vector2 = (Vector2) obj;
		return x == vector2.x && y == vector2.y;
	}

	/**
	 * Returns a string representation of this Vector2 object.
	 * The string is in the format "(x, y)".
	 *
	 * @return A string representation of this vector in the format "(x, y)".
	 */
	@Override
	public String toString() {
		return "(" + this.x + ", " + this.y + ")";
	}

}
