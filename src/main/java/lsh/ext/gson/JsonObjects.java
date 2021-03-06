package lsh.ext.gson;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Provides miscellaneous {@link JsonObject} utility methods.
 *
 * @author Lyubomyr Shaydariv
 * @since 0-SNAPSHOT
 */
public final class JsonObjects {

	private JsonObjects() {
	}

	/**
	 * @return A new empty JSON object.
	 *
	 * @since 0-SNAPSHOT
	 */
	@Nonnull
	public static JsonObject of() {
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
	public static JsonObject of(
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
	public static JsonObject of(
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
	public static JsonObject of(
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
	public static JsonObject of(
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
	public static JsonObject of(
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

	public static JsonObject from(final Map<String, ? extends JsonElement> map) {
		final JsonObject jsonObject = new JsonObject();
		for ( final Map.Entry<String, ? extends JsonElement> e : map.entrySet() ) {
			jsonObject.add(e.getKey(), e.getValue());
		}
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
	 * @param jsonObject JSON object to put into the view
	 *
	 * @return An immutable map view for the given JSON object
	 *
	 * @since 0-SNAPSHOT
	 */
	public static Map<String, JsonElement> asImmutableMap(final JsonObject jsonObject) {
		return new ImmutableJsonObjectMap(jsonObject);
	}

	/**
	 * @param jsonObject JSON object to put into the view
	 *
	 * @return A mutable map view for the given JSON object
	 *
	 * @since 0-SNAPSHOT
	 */
	public static Map<String, JsonElement> asMutableMap(final JsonObject jsonObject) {
		return new MutableJsonObjectMap(jsonObject);
	}

	private abstract static class AbstractJsonObjectMap
			extends AbstractMap<String, JsonElement> {

		private final JsonObject jsonObject;

		protected AbstractJsonObjectMap(final JsonObject jsonObject) {
			this.jsonObject = jsonObject;
		}

		protected abstract Set<Entry<String, JsonElement>> tryEntrySet(JsonObject jsonObject);

		protected abstract JsonElement tryPut(JsonObject jsonObject, String key, JsonElement jsonElement);

		@Override
		public final Set<Entry<String, JsonElement>> entrySet() {
			return tryEntrySet(jsonObject);
		}

		@Override
		public final JsonElement put(final String key, final JsonElement jsonElement) {
			return tryPut(jsonObject, key, jsonElement);
		}

	}

	private static final class ImmutableJsonObjectMap
			extends AbstractJsonObjectMap {

		private ImmutableJsonObjectMap(final JsonObject jsonObject) {
			super(jsonObject);
		}

		@Override
		protected Set<Entry<String, JsonElement>> tryEntrySet(final JsonObject jsonObject) {
			return new ImmutableJsonObjectSet(jsonObject);
		}

		@Override
		protected JsonElement tryPut(final JsonObject jsonObject, final String key, final JsonElement jsonElement) {
			throw new UnsupportedOperationException();
		}

	}

	private static final class MutableJsonObjectMap
			extends AbstractJsonObjectMap {

		private MutableJsonObjectMap(final JsonObject jsonObject) {
			super(jsonObject);
		}

		@Override
		protected Set<Entry<String, JsonElement>> tryEntrySet(final JsonObject jsonObject) {
			return new MutableJsonObjectSet(jsonObject);
		}

		@Override
		protected JsonElement tryPut(final JsonObject jsonObject, final String key, final JsonElement jsonElement) {
			final JsonElement previous = jsonObject.get(key);
			jsonObject.add(key, jsonElement);
			return previous;
		}

	}

	private abstract static class AbstractJsonObjectSet
			extends AbstractSet<Map.Entry<String, JsonElement>> {

		private final JsonObject jsonObject;

		protected AbstractJsonObjectSet(final JsonObject jsonObject) {
			this.jsonObject = jsonObject;
		}

		protected abstract Iterator<Map.Entry<String, JsonElement>> getIterator(JsonObject jsonObject);

		@Override
		public final Iterator<Map.Entry<String, JsonElement>> iterator() {
			return getIterator(jsonObject);
		}

		@Override
		public final int size() {
			return jsonObject.size();
		}

	}

	private static final class ImmutableJsonObjectSet
			extends AbstractJsonObjectSet {

		private ImmutableJsonObjectSet(final JsonObject jsonObject) {
			super(jsonObject);
		}

		@Override
		protected Iterator<Map.Entry<String, JsonElement>> getIterator(final JsonObject jsonObject) {
			final Iterator<Map.Entry<String, JsonElement>> iterator = jsonObject.entrySet().iterator();
			return new Iterator<Map.Entry<String, JsonElement>>() {
				@Override
				public boolean hasNext() {
					return iterator.hasNext();
				}

				@Override
				public Map.Entry<String, JsonElement> next() {
					return iterator.next();
				}
			};
		}

	}

	private static final class MutableJsonObjectSet
			extends AbstractJsonObjectSet {

		private MutableJsonObjectSet(final JsonObject jsonObject) {
			super(jsonObject);
		}

		@Override
		protected Iterator<Map.Entry<String, JsonElement>> getIterator(final JsonObject jsonObject) {
			return jsonObject.entrySet().iterator();
		}

	}

}
