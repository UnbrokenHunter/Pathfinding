package Utilities;

import java.util.Objects;

public class Vector2 {

	public static final Vector2 one = new Vector2(1, 1);
	public static final Vector2 zero = new Vector2(0, 0);

	public static final Vector2 up = new Vector2(0, 1);
	public static final Vector2 down = new Vector2(0, -1);
	public static final Vector2 left = new Vector2(-1, 0);
	public static final Vector2 right = new Vector2(1, 0);

	public int x;
	public int y;

	public Vector2() {
		this.x = 0;
		this.y = 0;
	}

	public Vector2(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Vector2 plus(Vector2 vector2) {
		return new Vector2(x + vector2.x, y + vector2.y);
	}

	public Vector2 minus(Vector2 vector2) {
		return new Vector2(x - vector2.x, y - vector2.y);
	}

	public Vector2 mult(int num) {
		return new Vector2(x * num, y * num);
	}

	public Vector2 div(int num) {
		return new Vector2(x / num, y / num);
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Vector2 vector2 = (Vector2) obj;
		return x == vector2.x && y == vector2.y;
	}

	@Override
	public String toString() {
		return "(" + this.x + ", " + this.y + ")";
	}

}
