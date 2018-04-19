package lsh.ext.gson;

import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

/**
 * Provides miscellaneous {@link JsonElement} utility methods.
 *
 * @author Lyubomyr Shaydariv
 * @since 0-SNAPSHOT
 */
public final class JsonElements {

	private static final JsonPrimitive falsePrimitive = new JsonPrimitive(false);
	private static final JsonPrimitive truePrimitive = new JsonPrimitive(true);

	private JsonElements() {
	}

	/**
	 * @return {@link JsonNull#INSTANCE}.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nonnull
	public static JsonNull from() {
		return JsonNull.INSTANCE;
	}

	/**
	 * @param b A boolean value.
	 *
	 * @return A new JSON primitive value for {@code true} or {@code false}.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nonnull
	@SuppressWarnings("ConstantConditions")
	public static JsonPrimitive from(@Nonnull final Boolean b) {
		final boolean hasValue = b != null;
		return hasValue
				? b ? truePrimitive : falsePrimitive
				: new JsonPrimitive(b); // Propagate the default behavior
	}

	/**
	 * @param n A numeric value.
	 *
	 * @return A new JSON primitive for numbers.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nonnull
	public static JsonPrimitive from(@Nonnull final Number n) {
		return new JsonPrimitive(n);
	}

	/**
	 * @param s A string value.
	 *
	 * @return A new JSON primitive for strings.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nonnull
	public static JsonPrimitive from(@Nonnull final String s) {
		return new JsonPrimitive(s);
	}

	/**
	 * @param c A character value.
	 *
	 * @return A new JSON primitive for characters.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nonnull
	public static JsonPrimitive from(@Nonnull final Character c) {
		return new JsonPrimitive(c);
	}

	/**
	 * @return A new empty JSON object.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nonnull
	public static JsonObject object() {
		return new JsonObject();
	}

	/**
	 * @param k1 Key 1 name.
	 * @param v1 Value 1.
	 *
	 * @return A new JSON object with one key-value pair.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nonnull
	public static JsonObject object(
			@Nonnull final String k1, @Nullable final JsonElement v1
	) {
		final JsonObject jsonObject = new JsonObject();
		jsonObject.add(k1, v1);
		return jsonObject;
	}

	/**
	 * @param k1 Key 1 name.
	 * @param v1 Value 1.
	 * @param k2 Key 2 name.
	 * @param v2 Value 2.
	 *
	 * @return A new JSON object with two key-value pairs.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nonnull
	public static JsonObject object(
			@Nonnull final String k1, @Nullable final JsonElement v1,
			@Nonnull final String k2, @Nullable final JsonElement v2
	) {
		final JsonObject jsonObject = new JsonObject();
		jsonObject.add(k1, v1);
		jsonObject.add(k2, v2);
		return jsonObject;
	}

	/**
	 * @param k1 Key 1 name.
	 * @param v1 Value 1.
	 * @param k2 Key 2 name.
	 * @param v2 Value 2.
	 * @param k3 Key 3 name.
	 * @param v3 Value 3.
	 *
	 * @return A new JSON object with three key-value pairs.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nonnull
	public static JsonObject object(
			@Nonnull final String k1, @Nullable final JsonElement v1,
			@Nonnull final String k2, @Nullable final JsonElement v2,
			@Nonnull final String k3, @Nullable final JsonElement v3
	) {
		final JsonObject jsonObject = new JsonObject();
		jsonObject.add(k1, v1);
		jsonObject.add(k2, v2);
		jsonObject.add(k3, v3);
		return jsonObject;
	}

	/**
	 * @param k1 Key 1 name.
	 * @param v1 Value 1.
	 * @param k2 Key 2 name.
	 * @param v2 Value 2.
	 * @param k3 Key 3 name.
	 * @param v3 Value 3.
	 * @param k4 Key 4 name.
	 * @param v4 Value 4.
	 *
	 * @return A new JSON object with four key-value pairs.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nonnull
	public static JsonObject object(
			@Nonnull final String k1, @Nullable final JsonElement v1,
			@Nonnull final String k2, @Nullable final JsonElement v2,
			@Nonnull final String k3, @Nullable final JsonElement v3,
			@Nonnull final String k4, @Nullable final JsonElement v4
	) {
		final JsonObject jsonObject = new JsonObject();
		jsonObject.add(k1, v1);
		jsonObject.add(k2, v2);
		jsonObject.add(k3, v3);
		jsonObject.add(k4, v4);
		return jsonObject;
	}

	/**
	 * @param k1 Key 1 name.
	 * @param v1 Value 1.
	 * @param k2 Key 2 name.
	 * @param v2 Value 2.
	 * @param k3 Key 3 name.
	 * @param v3 Value 3.
	 * @param k4 Key 4 name.
	 * @param v4 Value 4.
	 * @param k5 Key 5 name.
	 * @param v5 Value 5.
	 *
	 * @return A new JSON object with five key-value pairs.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nonnull
	public static JsonObject object(
			@Nonnull final String k1, @Nullable final JsonElement v1,
			@Nonnull final String k2, @Nullable final JsonElement v2,
			@Nonnull final String k3, @Nullable final JsonElement v3,
			@Nonnull final String k4, @Nullable final JsonElement v4,
			@Nonnull final String k5, @Nullable final JsonElement v5
	) {
		final JsonObject jsonObject = new JsonObject();
		jsonObject.add(k1, v1);
		jsonObject.add(k2, v2);
		jsonObject.add(k3, v3);
		jsonObject.add(k4, v4);
		jsonObject.add(k5, v5);
		return jsonObject;
	}

	/**
	 * @return An accumulator instance to accumulate {@link JsonObject} instances.
	 *
	 * @since 0-SNAPSHOT
	 */
	public static JsonObjectAccumulator objectWith() {
		return new JsonObjectAccumulator();
	}

	/**
	 * @return A new empty JSON array.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nonnull
	public static JsonArray array() {
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
	public static JsonArray array(
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
	public static JsonArray array(
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
	public static JsonArray array(
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
	public static JsonArray array(
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
	public static JsonArray array(
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
	public static JsonArray array(
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
	public static JsonArray array(
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
	public static JsonArray array(
			final JsonElement e1,
			final JsonElement e2,
			final JsonElement e3,
			final JsonElement e4,
			final JsonElement e5,
			final JsonElement e6,
			final JsonElement e7,
			final JsonElement e8
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
	public static JsonArray array(
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
	public static JsonArray array(
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

	/**
	 * @return An accumulator instance to accumulate {@link JsonArray} instances.
	 *
	 * @since 0-SNAPSHOT
	 */
	public static JsonArrayAccumulator arrayWith() {
		return new JsonArrayAccumulator();
	}

	/**
	 * @param left  Left
	 * @param right Right
	 *
	 * @return A new {@link JsonObject} containing a merged result of both {@link JsonObject} arguments.
	 *
	 * @since 0-SNAPSHOT
	 */
	public static JsonObject mergeIntoNew(final JsonObject left, final JsonObject right) {
		return mergeIntoNew(left, right, JsonObjectMergePredicates.alwaysReplaceLeft());
	}

	/**
	 * @param left      Left
	 * @param right     Right
	 * @param predicate Predicate
	 *
	 * @return A new {@link JsonObject} containing a merged result of both {@link JsonObject} arguments.
	 *
	 * @since 0-SNAPSHOT
	 */
	public static JsonObject mergeIntoNew(final JsonObject left, final JsonObject right, final IJsonObjectMergePredicate predicate) {
		final JsonObject merged = new JsonObject();
		for ( final Map.Entry<String, JsonElement> leftEntry : left.entrySet() ) {
			merged.add(leftEntry.getKey(), leftEntry.getValue());
		}
		mergeIntoLeft(merged, right, predicate);
		return merged;
	}

	/**
	 * @param left  Left
	 * @param right Right
	 *
	 * @return The {@code left} object with the {@code right} object merged into.
	 *
	 * @see #mergeIntoLeft(JsonObject, JsonObject, IJsonObjectMergePredicate)
	 * @since 0-SNAPSHOT
	 */
	public static JsonObject mergeIntoLeft(final JsonObject left, final JsonObject right) {
		return mergeIntoLeft(left, right, JsonObjectMergePredicates.alwaysReplaceLeft());
	}

	/**
	 * @param left      Left
	 * @param right     Right
	 * @param predicate Predicate
	 *
	 * @return The {@code left} object with the {@code right} object merged into.
	 *
	 * @see #mergeIntoLeft(JsonObject, JsonObject)
	 * @since 0-SNAPSHOT
	 */
	public static JsonObject mergeIntoLeft(final JsonObject left, final JsonObject right, final IJsonObjectMergePredicate predicate) {
		for ( final Map.Entry<String, JsonElement> rightEntry : right.entrySet() ) {
			final String key = rightEntry.getKey();
			final JsonElement leftValue = left.get(key);
			final JsonElement rightValue = rightEntry.getValue();
			if ( predicate.replace(key, left, leftValue, right, rightValue) ) {
				left.add(key, rightValue);
			}
		}
		return left;
	}

	/**
	 * Represents a {@link JsonObject} accumulator. Unlike a builder, the accumulator does not create a new object in with its final method, but accumulates
	 * a certain state with builder-like syntax.
	 *
	 * @since 0-SNAPSHOT
	 */
	public static final class JsonObjectAccumulator {

		private final JsonObject jsonObject = new JsonObject();

		private JsonObjectAccumulator() {
		}

		/**
		 * @param property    Property name.
		 * @param jsonElement A JSON element.
		 *
		 * @return Self with a new key/value pair.
		 *
		 * @since 0-SNAPSHOT
		 */
		public JsonObjectAccumulator add(final String property, final JsonElement jsonElement) {
			jsonObject.add(property, jsonElement);
			return this;
		}

		/**
		 * @param property Property name.
		 * @param b        A boolean value.
		 *
		 * @return Self with a new key/value pair.
		 *
		 * @since 0-SNAPSHOT
		 */
		public JsonObjectAccumulator add(final String property, final Boolean b) {
			jsonObject.addProperty(property, b);
			return this;
		}

		/**
		 * @param property Property name.
		 * @param c        A character value.
		 *
		 * @return Self with a new key/value pair.
		 *
		 * @since 0-SNAPSHOT
		 */
		public JsonObjectAccumulator add(final String property, final Character c) {
			jsonObject.addProperty(property, c);
			return this;
		}

		/**
		 * @param property Property name.
		 * @param n        A numeric value.
		 *
		 * @return Self with a new key/value pair.
		 *
		 * @since 0-SNAPSHOT
		 */
		public JsonObjectAccumulator add(final String property, final Number n) {
			jsonObject.addProperty(property, n);
			return this;
		}

		/**
		 * @param property Property name.
		 * @param s        A string value.
		 *
		 * @return Self with a new key/value pair.
		 *
		 * @since 0-SNAPSHOT
		 */
		public JsonObjectAccumulator add(final String property, final String s) {
			jsonObject.addProperty(property, s);
			return this;
		}

		/**
		 * @return The accumulated {@link JsonObject} instance.
		 *
		 * @since 0-SNAPSHOT
		 */
		public JsonObject get() {
			return jsonObject;
		}

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
