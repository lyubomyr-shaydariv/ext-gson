package lsh.ext.gson;

import javax.annotation.Nonnull;

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
	@Nonnull
	@SuppressWarnings("ConstantConditions")
	public static JsonPrimitive of(@Nonnull final Boolean b) {
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
	@Nonnull
	public static JsonPrimitive of(@Nonnull final Number n) {
		return new JsonPrimitive(n);
	}

	/**
	 * @param s A string value.
	 *
	 * @return A new JSON primitive for strings.
	 */
	@Nonnull
	public static JsonPrimitive of(@Nonnull final String s) {
		return new JsonPrimitive(s);
	}

	/**
	 * @param c A character value.
	 *
	 * @return A new JSON primitive for characters.
	 */
	@Nonnull
	public static JsonPrimitive of(@Nonnull final Character c) {
		return new JsonPrimitive(c);
	}

}
