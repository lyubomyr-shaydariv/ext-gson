package lsh.ext.gson;

import java.util.Map.Entry;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.TypeAdapter;

import static lsh.ext.gson.JsonObjectMergePredicates.alwaysReplaceLeft;

/**
 * Provides miscellaneous {@link TypeAdapter} utility methods.
 *
 * @author Lyubomyr Shaydariv
 * @since 0-SNAPSHOT
 */
public final class JsonElements {

	private JsonElements() {
	}

	/**
	 * Represents a JSON {@code null}.
	 *
	 * @return {@link JsonNull#INSTANCE} singleton.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nonnull
	public static JsonNull jsonNull() {
		return JsonNull.INSTANCE;
	}

	/**
	 * Represents a JSON primitive value {@code true} or {@code false}.
	 *
	 * @param b A boolean value.
	 *
	 * @return A new JSON primitive.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nonnull
	public static JsonPrimitive jsonPrimitive(@Nonnull final Boolean b) {
		return new JsonPrimitive(b);
	}

	/**
	 * Represents a JSON primitive numeric value.
	 *
	 * @param n A numeric value.
	 *
	 * @return A new JSON primitive.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nonnull
	public static JsonPrimitive jsonPrimitive(@Nonnull final Number n) {
		return new JsonPrimitive(n);
	}

	/**
	 * Represents a JSON primitive string value.
	 *
	 * @param s A string value.
	 *
	 * @return A new JSON primitive.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nonnull
	public static JsonPrimitive jsonPrimitive(@Nonnull final String s) {
		return new JsonPrimitive(s);
	}

	/**
	 * Represents a JSON primitive single character value.
	 *
	 * @param c A character value.
	 *
	 * @return A new JSON primitive.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nonnull
	public static JsonPrimitive jsonPrimitive(@Nonnull final Character c) {
		return new JsonPrimitive(c);
	}

	/**
	 * Creates a new JSON object: {@code {}}.
	 *
	 * @return An empty JSON object.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nonnull
	public static JsonObject jsonObject() {
		return new JsonObject();
	}

	/**
	 * Creates a new JSON object: {@code {k1: v1}}.
	 *
	 * @param k1 Key 1 name.
	 * @param v1 Value 1.
	 *
	 * @return A JSON object with one key-value pair.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nonnull
	public static JsonObject jsonObject(
			@Nonnull final String k1, @Nullable final JsonElement v1
	) {
		final JsonObject jsonObject = new JsonObject();
		jsonObject.add(k1, v1);
		return jsonObject;
	}

	/**
	 * Creates a new JSON object: {@code {k1: v1, k2: v2}}.
	 *
	 * @param k1 Key 1 name.
	 * @param v1 Value 1.
	 * @param k2 Key 2 name.
	 * @param v2 Value 2.
	 *
	 * @return A JSON object with two key-value pairs.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nonnull
	public static JsonObject jsonObject(
			@Nonnull final String k1, @Nullable final JsonElement v1,
			@Nonnull final String k2, @Nullable final JsonElement v2
	) {
		final JsonObject jsonObject = new JsonObject();
		jsonObject.add(k1, v1);
		jsonObject.add(k2, v2);
		return jsonObject;
	}

	/**
	 * Creates a new JSON object: {@code {k1: v1, k2: v2, k3: v3}}.
	 *
	 * @param k1 Key 1 name.
	 * @param v1 Value 1.
	 * @param k2 Key 2 name.
	 * @param v2 Value 2.
	 * @param k3 Key 3 name.
	 * @param v3 Value 3.
	 *
	 * @return A JSON object with three key-value pairs.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nonnull
	public static JsonObject jsonObject(
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
	 * Creates a new JSON object: {@code {k1: v1, k2: v2, k3: v3, k4: v4}}.
	 *
	 * @param k1 Key 1 name.
	 * @param v1 Value 1.
	 * @param k2 Key 2 name.
	 * @param v2 Value 2.
	 * @param k3 Key 3 name.
	 * @param v3 Value 3.
	 * @param k4 Key 4 name.
	 * @param v4 Value 4.
	 *
	 * @return A JSON object with four key-value pairs.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nonnull
	public static JsonObject jsonObject(
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
	 * Creates a new JSON object: {@code {k1: v1, k2: v2, k3: v3, k4: v4, k5: v5}}.
	 *
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
	 * @return A JSON object with five key-value pairs.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nonnull
	public static JsonObject jsonObject(
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
	public static JsonObjectAccumulator jsonObjectWith() {
		return new JsonObjectAccumulator();
	}

	/**
	 * Creates a new JSON array: {@code []}.
	 *
	 * @return An empty JSON array.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nonnull
	public static JsonArray jsonArray() {
		return new JsonArray();
	}

	/**
	 * Creates a new JSON array: {@code [e1]}.
	 *
	 * @param e1 Element 1.
	 *
	 * @return A JSON array with one element.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nonnull
	public static JsonArray jsonArray(
			@Nullable final JsonElement e1
	) {
		final JsonArray jsonArray = new JsonArray();
		jsonArray.add(e1);
		return jsonArray;
	}

	/**
	 * Creates a new JSON array: {@code [e1, e2]}.
	 *
	 * @param e1 Element 1.
	 * @param e2 Element 2.
	 *
	 * @return A JSON array with two elements.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nonnull
	public static JsonArray jsonArray(
			@Nullable final JsonElement e1,
			@Nullable final JsonElement e2
	) {
		final JsonArray jsonArray = new JsonArray();
		jsonArray.add(e1);
		jsonArray.add(e2);
		return jsonArray;
	}

	/**
	 * Creates a new JSON array: {@code [e1, e2, e3]}.
	 *
	 * @param e1 Element 1.
	 * @param e2 Element 2.
	 * @param e3 Element 3.
	 *
	 * @return A JSON array with three elements.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nonnull
	public static JsonArray jsonArray(
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
	 * Creates a new JSON array: {@code [e1, e2, e3, e4]}.
	 *
	 * @param e1 Element 1.
	 * @param e2 Element 2.
	 * @param e3 Element 3.
	 * @param e4 Element 4.
	 *
	 * @return A JSON array with four elements.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nonnull
	public static JsonArray jsonArray(
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
	 * Creates a new JSON array: {@code [e1, e2, e3, e4, e5]}.
	 *
	 * @param e1 Element 1.
	 * @param e2 Element 2.
	 * @param e3 Element 3.
	 * @param e4 Element 4.
	 * @param e5 Element 5.
	 *
	 * @return A JSON array with five elements.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nonnull
	public static JsonArray jsonArray(
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
	 * Creates a new JSON array: {@code [e1, e2, e3, e4, e5, e6]}.
	 *
	 * @param e1 Element 1.
	 * @param e2 Element 2.
	 * @param e3 Element 3.
	 * @param e4 Element 4.
	 * @param e5 Element 5.
	 * @param e6 Element 6.
	 *
	 * @return A JSON array with six elements.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nonnull
	public static JsonArray jsonArray(
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
	 * Creates a new JSON array: {@code [e1, e2, e3, e4, e5, e6, e7]}.
	 *
	 * @param e1 Element 1.
	 * @param e2 Element 2.
	 * @param e3 Element 3.
	 * @param e4 Element 4.
	 * @param e5 Element 5.
	 * @param e6 Element 6.
	 * @param e7 Element 7.
	 *
	 * @return A JSON array with seven elements.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nonnull
	public static JsonArray jsonArray(
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
	 * Creates a new JSON array: {@code [e1, e2, e3, e4, e5, e6, e7, e8]}.
	 *
	 * @param e1 Element 1.
	 * @param e2 Element 2.
	 * @param e3 Element 3.
	 * @param e4 Element 4.
	 * @param e5 Element 5.
	 * @param e6 Element 6.
	 * @param e7 Element 7.
	 * @param e8 Element 8.
	 *
	 * @return A JSON array with eight elements.
	 *
	 * @since 0-SNAPSHOT
	 */
	public static JsonArray jsonArray(
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
	 * Creates a new JSON array: {@code [e1, e2, e3, e4, e5, e6, e7, e8, e9]}.
	 *
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
	 * @return A JSON array with nine elements.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nonnull
	public static JsonArray jsonArray(
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
	 * Creates a new JSON array: {@code [e1, e2, e3, e4, e5, e6, e7, e8, e9, e10]}.
	 *
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
	 * @return A JSON array with ten elements.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nonnull
	public static JsonArray jsonArray(
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
	public static JsonArrayAccumulator jsonArrayWith() {
		return new JsonArrayAccumulator();
	}

	/**
	 * Merges two {@link JsonObject} objects, left and right, into a new {@link JsonObject} object on common keys using the {@link JsonObjectMergePredicates#alwaysReplaceLeft()} strategy.
	 *
	 * @param left  Left
	 * @param right Right
	 *
	 * @return A new {@link JsonObject} containing a merged result of both {@link JsonObject} arguments.
	 *
	 * @since 0-SNAPSHOT
	 */
	public static JsonObject mergeIntoNew(final JsonObject left, final JsonObject right) {
		return mergeIntoNew(left, right, alwaysReplaceLeft());
	}

	/**
	 * Merges two {@link JsonObject} objects, left and right, into a new {@link JsonObject} object on common keys.
	 *
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
		for ( final Entry<String, JsonElement> leftEntry : left.entrySet() ) {
			merged.add(leftEntry.getKey(), leftEntry.getValue());
		}
		mergeIntoLeft(merged, right, predicate);
		return merged;
	}

	/**
	 * Merges the right {@link JsonObject} into the left {@link JsonObject} on common keys using the {@link JsonObjectMergePredicates#alwaysReplaceLeft()} strategy.
	 *
	 * @param left  Left
	 * @param right Right
	 *
	 * @return The modified {@code left} parameter.
	 *
	 * @see #mergeIntoLeft(JsonObject, JsonObject, IJsonObjectMergePredicate)
	 * @since 0-SNAPSHOT
	 */
	public static JsonObject mergeIntoLeft(final JsonObject left, final JsonObject right) {
		return mergeIntoLeft(left, right, alwaysReplaceLeft());
	}

	/**
	 * Merges the right {@link JsonObject} into the left {@link JsonObject} on common keys.
	 *
	 * @param left      Left
	 * @param right     Right
	 * @param predicate Predicate
	 *
	 * @return The modified {@code left} parameter.
	 *
	 * @see #mergeIntoLeft(JsonObject, JsonObject)
	 * @since 0-SNAPSHOT
	 */
	public static JsonObject mergeIntoLeft(final JsonObject left, final JsonObject right, final IJsonObjectMergePredicate predicate) {
		for ( final Entry<String, JsonElement> rightEntry : right.entrySet() ) {
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
		 * Adds a JSON element to the accumulated object.
		 *
		 * @param property    Property name.
		 * @param jsonElement A JSON element.
		 *
		 * @return Self.
		 *
		 * @since 0-SNAPSHOT
		 */
		public JsonObjectAccumulator add(final String property, final JsonElement jsonElement) {
			jsonObject.add(property, jsonElement);
			return this;
		}

		/**
		 * Adds a boolean to the accumulated object.
		 *
		 * @param property Property name.
		 * @param b        A boolean value.
		 *
		 * @return Self.
		 *
		 * @since 0-SNAPSHOT
		 */
		public JsonObjectAccumulator add(final String property, final Boolean b) {
			jsonObject.addProperty(property, b);
			return this;
		}

		/**
		 * Adds a single character to the accumulated object.
		 *
		 * @param property Property name.
		 * @param c        A character value.
		 *
		 * @return Self.
		 *
		 * @since 0-SNAPSHOT
		 */
		public JsonObjectAccumulator add(final String property, final Character c) {
			jsonObject.addProperty(property, c);
			return this;
		}

		/**
		 * Adds a numeric value to the accumulated object.
		 *
		 * @param property Property name.
		 * @param n        A numeric value.
		 *
		 * @return Self.
		 *
		 * @since 0-SNAPSHOT
		 */
		public JsonObjectAccumulator add(final String property, final Number n) {
			jsonObject.addProperty(property, n);
			return this;
		}

		/**
		 * Adds a string to the accumulated object.
		 *
		 * @param property Property name.
		 * @param s        A string value.
		 *
		 * @return Self.
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
		public JsonObject getJsonObject() {
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
		 * Adds a JSON element to the accumulated array.
		 *
		 * @param jsonElement A JSON element.
		 *
		 * @return Self.
		 *
		 * @since 0-SNAPSHOT
		 */
		public JsonArrayAccumulator add(final JsonElement jsonElement) {
			jsonArray.add(jsonElement);
			return this;
		}

		/**
		 * Adds a boolean to the accumulated array.
		 *
		 * @param b A boolean value.
		 *
		 * @return Self.
		 *
		 * @since 0-SNAPSHOT
		 */
		public JsonArrayAccumulator add(final Boolean b) {
			jsonArray.add(b);
			return this;
		}

		/**
		 * Adds a single character to the accumulated array.
		 *
		 * @param c A character value.
		 *
		 * @return Self.
		 *
		 * @since 0-SNAPSHOT
		 */
		public JsonArrayAccumulator add(final Character c) {
			jsonArray.add(c);
			return this;
		}

		/**
		 * Adds a numeric value to the accumulated array.
		 *
		 * @param n A numeric value.
		 *
		 * @return Self.
		 *
		 * @since 0-SNAPSHOT
		 */
		public JsonArrayAccumulator add(final Number n) {
			jsonArray.add(n);
			return this;
		}

		/**
		 * Adds a string to the accumulated array.
		 *
		 * @param s A string value.
		 *
		 * @return Self.
		 *
		 * @since 0-SNAPSHOT
		 */
		public JsonArrayAccumulator add(final String s) {
			jsonArray.add(s);
			return this;
		}

		/**
		 * Adds all elements from the given JSON array to the accumulated array.
		 *
		 * @param jsonArray A JSON array.
		 *
		 * @return Self.
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
		public JsonArray getJsonArray() {
			return jsonArray;
		}

	}

}
