package lsh.ext.gson;

import com.google.gson.JsonPrimitive;
import lombok.experimental.UtilityClass;

/**
 * Provides miscellaneous {@link JsonPrimitive} utility methods.
 *
 * @author Lyubomyr Shaydariv
 */
@UtilityClass
public final class JsonPrimitives {

	/**
	 * @param b
	 * 		A boolean value.
	 *
	 * @return A new JSON primitive value for {@code true} or {@code false}.
	 */
	public static JsonPrimitive of(final Boolean b) {
		return new JsonPrimitive(b);
	}

	/**
	 * @param n
	 * 		A numeric value.
	 *
	 * @return A new JSON primitive for numbers.
	 */
	public static JsonPrimitive of(final Number n) {
		return new JsonPrimitive(n);
	}

	/**
	 * @param s
	 * 		A string value.
	 *
	 * @return A new JSON primitive for strings.
	 */
	public static JsonPrimitive of(final String s) {
		return new JsonPrimitive(s);
	}

	/**
	 * @param c
	 * 		A character value.
	 *
	 * @return A new JSON primitive for characters.
	 */
	public static JsonPrimitive of(final Character c) {
		return new JsonPrimitive(c);
	}

}
