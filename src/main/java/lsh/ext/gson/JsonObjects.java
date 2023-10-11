package lsh.ext.gson;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class JsonObjects {

	public static JsonObject of() {
		return new JsonObject();
	}

	public static JsonObject of(
			final String k1, @Nullable final JsonElement v1
	) {
		final JsonObject jsonObject = new JsonObject();
		jsonObject.add(k1, v1);
		return jsonObject;
	}

	public static JsonObject of(
			final String k1, @Nullable final JsonElement v1,
			final String k2, @Nullable final JsonElement v2
	) {
		final JsonObject jsonObject = new JsonObject();
		jsonObject.add(k1, v1);
		jsonObject.add(k2, v2);
		return jsonObject;
	}

	public static JsonObject of(
			final String k1, @Nullable final JsonElement v1,
			final String k2, @Nullable final JsonElement v2,
			final String k3, @Nullable final JsonElement v3
	) {
		final JsonObject jsonObject = new JsonObject();
		jsonObject.add(k1, v1);
		jsonObject.add(k2, v2);
		jsonObject.add(k3, v3);
		return jsonObject;
	}

	public static JsonObject of(
			final String k1, @Nullable final JsonElement v1,
			final String k2, @Nullable final JsonElement v2,
			final String k3, @Nullable final JsonElement v3,
			final String k4, @Nullable final JsonElement v4
	) {
		final JsonObject jsonObject = new JsonObject();
		jsonObject.add(k1, v1);
		jsonObject.add(k2, v2);
		jsonObject.add(k3, v3);
		jsonObject.add(k4, v4);
		return jsonObject;
	}

	@SuppressWarnings("checkstyle:ParameterNumber")
	public static JsonObject of(
			final String k1, @Nullable final JsonElement v1,
			final String k2, @Nullable final JsonElement v2,
			final String k3, @Nullable final JsonElement v3,
			final String k4, @Nullable final JsonElement v4,
			final String k5, @Nullable final JsonElement v5
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

	public static JsonObject mergeIntoNew(final JsonObject left, final JsonObject right) {
		return mergeIntoNew(left, right, IJsonObjectMergePredicate.replace);
	}

	public static JsonObject mergeIntoNew(final JsonObject left, final JsonObject right, final IJsonObjectMergePredicate predicate) {
		final JsonObject merged = new JsonObject();
		for ( final Map.Entry<String, JsonElement> leftEntry : left.entrySet() ) {
			merged.add(leftEntry.getKey(), leftEntry.getValue());
		}
		mergeIntoLeft(merged, right, predicate);
		return merged;
	}

	public static JsonObject mergeIntoLeft(final JsonObject left, final JsonObject right) {
		return mergeIntoLeft(left, right, IJsonObjectMergePredicate.replace);
	}

	public static JsonObject mergeIntoLeft(final JsonObject left, final JsonObject right, final IJsonObjectMergePredicate predicate) {
		for ( final Map.Entry<String, JsonElement> rightEntry : right.entrySet() ) {
			final String key = rightEntry.getKey();
			final JsonElement leftValue = left.get(key);
			final JsonElement rightValue = rightEntry.getValue();
			if ( predicate.canReplace(key, left, leftValue, right, rightValue) ) {
				left.add(key, rightValue);
			}
		}
		return left;
	}

	public static Map<String, JsonElement> asImmutableMap(final JsonObject jsonObject) {
		return new ImmutableJsonObjectMap(jsonObject);
	}

	public static Map<String, JsonElement> asMutableMap(final JsonObject jsonObject) {
		return new MutableJsonObjectMap(jsonObject);
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	private abstract static class AbstractJsonObjectMap
			extends AbstractMap<String, JsonElement> {

		private final JsonObject jsonObject;

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

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	private abstract static class AbstractJsonObjectSet
			extends AbstractSet<Map.Entry<String, JsonElement>> {

		private final JsonObject jsonObject;

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
			return UnmodifiableIterator.of(jsonObject.entrySet().iterator());
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
