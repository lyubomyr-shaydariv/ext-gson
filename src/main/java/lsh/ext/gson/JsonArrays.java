package lsh.ext.gson;

import java.util.Collection;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

/**
 * Provides miscellaneous {@link JsonArray} utility methods.
 *
 * @author Lyubomyr Shaydariv
 * @since 0-SNAPSHOT
 */
public final class JsonArrays {

	private JsonArrays() {
	}

	/**
	 * @return A new empty JSON array.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nonnull
	public static JsonArray of() {
		return new JsonArray();
	}

	/**
	 * @param e1 Element 1.
	 *
	 * @return A new JSON array with one element.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nonnull
	public static JsonArray of(
			@Nullable final JsonElement e1
	) {
		final JsonArray jsonArray = new JsonArray();
		jsonArray.add(e1);
		return jsonArray;
	}

	/**
	 * @param e1 Element 1.
	 * @param e2 Element 2.
	 *
	 * @return A new JSON array with two elements.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nonnull
	public static JsonArray of(
			@Nullable final JsonElement e1,
			@Nullable final JsonElement e2
	) {
		final JsonArray jsonArray = new JsonArray();
		jsonArray.add(e1);
		jsonArray.add(e2);
		return jsonArray;
	}

	/**
	 * @param e1 Element 1.
	 * @param e2 Element 2.
	 * @param e3 Element 3.
	 *
	 * @return A new JSON array with three elements.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nonnull
	public static JsonArray of(
			@Nullable final JsonElement e1,
			@Nullable final JsonElement e2,
			@Nullable final JsonElement e3
	) {
		final JsonArray jsonArray = new JsonArray();
		jsonArray.add(e1);
		jsonArray.add(e2);
		jsonArray.add(e3);
		return jsonArray;
	}

	/**
	 * @param e1 Element 1.
	 * @param e2 Element 2.
	 * @param e3 Element 3.
	 * @param e4 Element 4.
	 *
	 * @return A new JSON array with four elements.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nonnull
	public static JsonArray of(
			@Nullable final JsonElement e1,
			@Nullable final JsonElement e2,
			@Nullable final JsonElement e3,
			@Nullable final JsonElement e4
	) {
		final JsonArray jsonArray = new JsonArray();
		jsonArray.add(e1);
		jsonArray.add(e2);
		jsonArray.add(e3);
		jsonArray.add(e4);
		return jsonArray;
	}

	/**
	 * @param e1 Element 1.
	 * @param e2 Element 2.
	 * @param e3 Element 3.
	 * @param e4 Element 4.
	 * @param e5 Element 5.
	 *
	 * @return A new JSON array with five elements.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nonnull
	public static JsonArray of(
			@Nullable final JsonElement e1,
			@Nullable final JsonElement e2,
			@Nullable final JsonElement e3,
			@Nullable final JsonElement e4,
			@Nullable final JsonElement e5
	) {
		final JsonArray jsonArray = new JsonArray();
		jsonArray.add(e1);
		jsonArray.add(e2);
		jsonArray.add(e3);
		jsonArray.add(e4);
		jsonArray.add(e5);
		return jsonArray;
	}

	/**
	 * @param e1 Element 1.
	 * @param e2 Element 2.
	 * @param e3 Element 3.
	 * @param e4 Element 4.
	 * @param e5 Element 5.
	 * @param e6 Element 6.
	 *
	 * @return A new JSON array with six elements.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nonnull
	public static JsonArray of(
			@Nullable final JsonElement e1,
			@Nullable final JsonElement e2,
			@Nullable final JsonElement e3,
			@Nullable final JsonElement e4,
			@Nullable final JsonElement e5,
			@Nullable final JsonElement e6
	) {
		final JsonArray jsonArray = new JsonArray();
		jsonArray.add(e1);
		jsonArray.add(e2);
		jsonArray.add(e3);
		jsonArray.add(e4);
		jsonArray.add(e5);
		jsonArray.add(e6);
		return jsonArray;
	}

	/**
	 * @param e1 Element 1.
	 * @param e2 Element 2.
	 * @param e3 Element 3.
	 * @param e4 Element 4.
	 * @param e5 Element 5.
	 * @param e6 Element 6.
	 * @param e7 Element 7.
	 *
	 * @return A new JSON array with seven elements.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nonnull
	public static JsonArray of(
			@Nullable final JsonElement e1,
			@Nullable final JsonElement e2,
			@Nullable final JsonElement e3,
			@Nullable final JsonElement e4,
			@Nullable final JsonElement e5,
			@Nullable final JsonElement e6,
			@Nullable final JsonElement e7
	) {
		final JsonArray jsonArray = new JsonArray();
		jsonArray.add(e1);
		jsonArray.add(e2);
		jsonArray.add(e3);
		jsonArray.add(e4);
		jsonArray.add(e5);
		jsonArray.add(e6);
		jsonArray.add(e7);
		return jsonArray;
	}

	/**
	 * @param e1 Element 1.
	 * @param e2 Element 2.
	 * @param e3 Element 3.
	 * @param e4 Element 4.
	 * @param e5 Element 5.
	 * @param e6 Element 6.
	 * @param e7 Element 7.
	 * @param e8 Element 8.
	 *
	 * @return A new JSON array with eight elements.
	 *
	 * @since 0-SNAPSHOT
	 */
	public static JsonArray of(
			@Nullable final JsonElement e1,
			@Nullable final JsonElement e2,
			@Nullable final JsonElement e3,
			@Nullable final JsonElement e4,
			@Nullable final JsonElement e5,
			@Nullable final JsonElement e6,
			@Nullable final JsonElement e7,
			@Nullable final JsonElement e8
	) {
		final JsonArray jsonArray = new JsonArray();
		jsonArray.add(e1);
		jsonArray.add(e2);
		jsonArray.add(e3);
		jsonArray.add(e4);
		jsonArray.add(e5);
		jsonArray.add(e6);
		jsonArray.add(e7);
		jsonArray.add(e8);
		return jsonArray;
	}

	/**
	 * @param e1 Element 1.
	 * @param e2 Element 2.
	 * @param e3 Element 3.
	 * @param e4 Element 4.
	 * @param e5 Element 5.
	 * @param e6 Element 6.
	 * @param e7 Element 7.
	 * @param e8 Element 8.
	 * @param e9 Element 9.
	 *
	 * @return A new JSON array with nine elements.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nonnull
	public static JsonArray of(
			@Nullable final JsonElement e1,
			@Nullable final JsonElement e2,
			@Nullable final JsonElement e3,
			@Nullable final JsonElement e4,
			@Nullable final JsonElement e5,
			@Nullable final JsonElement e6,
			@Nullable final JsonElement e7,
			@Nullable final JsonElement e8,
			@Nullable final JsonElement e9
	) {
		final JsonArray jsonArray = new JsonArray();
		jsonArray.add(e1);
		jsonArray.add(e2);
		jsonArray.add(e3);
		jsonArray.add(e4);
		jsonArray.add(e5);
		jsonArray.add(e6);
		jsonArray.add(e7);
		jsonArray.add(e8);
		jsonArray.add(e9);
		return jsonArray;
	}

	/**
	 * @param e1  Element 1.
	 * @param e2  Element 2.
	 * @param e3  Element 3.
	 * @param e4  Element 4.
	 * @param e5  Element 5.
	 * @param e6  Element 6.
	 * @param e7  Element 7.
	 * @param e8  Element 8.
	 * @param e9  Element 9.
	 * @param e10 Element 10.
	 *
	 * @return A new JSON array with ten elements.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nonnull
	public static JsonArray of(
			@Nullable final JsonElement e1,
			@Nullable final JsonElement e2,
			@Nullable final JsonElement e3,
			@Nullable final JsonElement e4,
			@Nullable final JsonElement e5,
			@Nullable final JsonElement e6,
			@Nullable final JsonElement e7,
			@Nullable final JsonElement e8,
			@Nullable final JsonElement e9,
			@Nullable final JsonElement e10
	) {
		final JsonArray jsonArray = new JsonArray();
		jsonArray.add(e1);
		jsonArray.add(e2);
		jsonArray.add(e3);
		jsonArray.add(e4);
		jsonArray.add(e5);
		jsonArray.add(e6);
		jsonArray.add(e7);
		jsonArray.add(e8);
		jsonArray.add(e9);
		jsonArray.add(e10);
		return jsonArray;
	}

	public static JsonArray from(final Iterable<? extends JsonElement> jsonElements) {
		final JsonArray jsonArray = new JsonArray();
		for ( final JsonElement jsonElement : jsonElements ) {
			jsonArray.add(jsonElement);
		}
		return jsonArray;
	}

	public static JsonArray from(final Collection<? extends JsonElement> jsonElements) {
		final JsonArray jsonArray = new JsonArray(jsonElements.size());
		for ( final JsonElement jsonElement : jsonElements ) {
			jsonArray.add(jsonElement);
		}
		return jsonArray;
	}

	/**
	 * @return An accumulator instance to accumulate {@link JsonArray} instances.
	 *
	 * @since 0-SNAPSHOT
	 */
	public static JsonArrayAccumulator arrayWith() {
		return new JsonArrayAccumulator();
	}

	/**
	 * Represents a {@link JsonArray} accumulator. Unlike a builder, the accumulator does not create a new object in with its final method, but accumulates
	 * a certain state with builder-like syntax.
	 *
	 * @since 0-SNAPSHOT
	 */
	public static final class JsonArrayAccumulator {

		private final JsonArray jsonArray = new JsonArray();

		private JsonArrayAccumulator() {
		}

		/**
		 * @param jsonElement A JSON element.
		 *
		 * @return Self with the new element appended to the end of the array.
		 *
		 * @since 0-SNAPSHOT
		 */
		public JsonArrayAccumulator add(final JsonElement jsonElement) {
			jsonArray.add(jsonElement);
			return this;
		}

		/**
		 * @param b A boolean value.
		 *
		 * @return Self with the new element appended to the end of the array.
		 *
		 * @since 0-SNAPSHOT
		 */
		public JsonArrayAccumulator add(final Boolean b) {
			jsonArray.add(b);
			return this;
		}

		/**
		 * @param c A character value.
		 *
		 * @return Self with the new element appended to the end of the array.
		 *
		 * @since 0-SNAPSHOT
		 */
		public JsonArrayAccumulator add(final Character c) {
			jsonArray.add(c);
			return this;
		}

		/**
		 * @param n A numeric value.
		 *
		 * @return Self with the new element appended to the end of the array.
		 *
		 * @since 0-SNAPSHOT
		 */
		public JsonArrayAccumulator add(final Number n) {
			jsonArray.add(n);
			return this;
		}

		/**
		 * @param s A string value.
		 *
		 * @return Self with the new element appended to the end of the array.
		 *
		 * @since 0-SNAPSHOT
		 */
		public JsonArrayAccumulator add(final String s) {
			jsonArray.add(s);
			return this;
		}

		/**
		 * @param jsonArray A JSON array.
		 *
		 * @return Self with all the new elements from the input array appended to the end of the array.
		 *
		 * @since 0-SNAPSHOT
		 */
		public JsonArrayAccumulator addAll(final JsonArray jsonArray) {
			this.jsonArray.addAll(jsonArray);
			return this;
		}

		/**
		 * @return The accumulated {@link JsonArray} instance.
		 *
		 * @since 0-SNAPSHOT
		 */
		public JsonArray get() {
			return jsonArray;
		}

	}

}
