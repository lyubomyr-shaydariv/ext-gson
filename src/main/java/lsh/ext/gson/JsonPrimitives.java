package lsh.ext.gson;

import com.google.gson.JsonPrimitive;

/**
 * Provides miscellaneous {@link JsonPrimitive} utility methods.
 *
 * @author Lyubomyr Shaydariv
 */
public final class JsonPrimitives {

	private static final JsonPrimitive falsePrimitive = new JsonPrimitive(false);
	private static final JsonPrimitive truePrimitive = new JsonPrimitive(true);

	private JsonPrimitives() {
	}

	/**
	 * @param b A boolean value.
	 *
	 * @return A new JSON primitive value for {@code true} or {@code false}.
	 */
	@SuppressWarnings("ConstantConditions")
	public static JsonPrimitive of(final Boolean b) {
		final boolean hasValue = b != null;
		return hasValue
				? b ? truePrimitive : falsePrimitive
				: new JsonPrimitive(b); // Propagate the default behavior
	}

	/**
	 * @param n A numeric value.
	 *
	 * @return A new JSON primitive for numbers.
	 */
	public static JsonPrimitive of(final Number n) {
		return new JsonPrimitive(n);
	}

	/**
	 * @param s A string value.
	 *
	 * @return A new JSON primitive for strings.
	 */
	public static JsonPrimitive of(final String s) {
		return new JsonPrimitive(s);
	}

	/**
	 * @param c A character value.
	 *
	 * @return A new JSON primitive for characters.
	 */
	public static JsonPrimitive of(final Character c) {
		return new JsonPrimitive(c);
	}

}
