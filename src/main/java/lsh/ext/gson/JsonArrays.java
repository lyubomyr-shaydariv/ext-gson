package lsh.ext.gson;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class JsonArrays {

	public static JsonArray of() {
		return new JsonArray();
	}

	public static JsonArray of(
			@Nullable final JsonElement e1
	) {
		final JsonArray jsonArray = new JsonArray();
		jsonArray.add(e1);
		return jsonArray;
	}

	public static JsonArray of(
			@Nullable final JsonElement e1,
			@Nullable final JsonElement e2
	) {
		final JsonArray jsonArray = new JsonArray();
		jsonArray.add(e1);
		jsonArray.add(e2);
		return jsonArray;
	}

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

	public static JsonArray of(final JsonElement... jsonElements) {
		final JsonArray jsonArray = new JsonArray(jsonElements.length);
		for ( final JsonElement e : jsonElements ) {
			jsonArray.add(e);
		}
		return jsonArray;
	}

	public static JsonArray from(final Iterable<? extends JsonElement> jsonElements) {
		final JsonArray jsonArray = new JsonArray();
		for ( final JsonElement jsonElement : jsonElements ) {
			jsonArray.add(jsonElement);
		}
		return jsonArray;
	}

	public static List<JsonElement> asImmutableList(final JsonArray jsonArray) {
		return new ImmutableJsonArrayList(jsonArray);
	}

	public static List<JsonElement> asMutableList(final JsonArray jsonArray) {
		return new MutableJsonArrayList(jsonArray);
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	private abstract static class AbstractJsonArrayList
			extends AbstractList<JsonElement> {

		private final JsonArray jsonArray;

		protected abstract boolean tryAdd(JsonArray jsonArray, JsonElement jsonElement);

		protected abstract void tryAdd(JsonArray jsonArray, int index, JsonElement jsonElement);

		protected abstract boolean tryAddAll(JsonArray jsonArray, Collection<? extends JsonElement> jsonElements);

		protected abstract boolean tryAddAll(JsonArray jsonArray, int index, Collection<? extends JsonElement> jsonElements);

		protected abstract void tryClear(JsonArray jsonArray);

		protected abstract boolean tryRemovePreflight(Object object);

		protected abstract boolean tryRemove(JsonArray jsonArray, JsonElement jsonElement);

		protected abstract JsonElement tryRemove(JsonArray jsonArray, int index);

		protected abstract boolean tryRemoveAll(JsonArray jsonArray, Collection<?> objects);

		protected abstract boolean tryRetainAll(JsonArray jsonArray, Collection<?> objects);

		protected abstract JsonElement trySet(JsonArray jsonArray, int index, JsonElement jsonElement);

		@Override
		public final JsonElement get(final int index) {
			return jsonArray.get(index);
		}

		@Override
		public final int size() {
			return jsonArray.size();
		}

		@Override
		public final boolean add(final JsonElement jsonElement) {
			return tryAdd(jsonArray, jsonElement);
		}

		@Override
		public final void add(final int index, final JsonElement jsonElement) {
			tryAdd(jsonArray, index, jsonElement);
		}

		@Override
		public final boolean addAll(final Collection<? extends JsonElement> jsonElements) {
			return tryAddAll(jsonArray, jsonElements);
		}

		@Override
		public final boolean addAll(final int index, final Collection<? extends JsonElement> jsonElements) {
			return tryAddAll(jsonArray, index, jsonElements);
		}

		@Override
		public final void clear() {
			tryClear(jsonArray);
		}

		@Override
		public final boolean remove(final Object object) {
			if ( !tryRemovePreflight(object) ) {
				return false;
			}
			return tryRemove(jsonArray, (JsonElement) object);
		}

		@Override
		public final JsonElement remove(final int index) {
			return tryRemove(jsonArray, index);
		}

		@Override
		public final boolean removeAll(final Collection<?> objects) {
			return tryRemoveAll(jsonArray, objects);
		}

		@Override
		public final boolean retainAll(final Collection<?> objects) {
			return tryRetainAll(jsonArray, objects);
		}

		@Override
		public final JsonElement set(final int index, final JsonElement jsonElement) {
			return trySet(jsonArray, index, jsonElement);
		}

	}

	private static final class ImmutableJsonArrayList
			extends AbstractJsonArrayList {

		private ImmutableJsonArrayList(final JsonArray jsonArray) {
			super(jsonArray);
		}

		@Override
		protected boolean tryAdd(final JsonArray jsonArray, final JsonElement jsonElement) {
			throw new UnsupportedOperationException();
		}

		@Override
		protected void tryAdd(final JsonArray jsonArray, final int index, final JsonElement jsonElement) {
			throw new UnsupportedOperationException();
		}

		@Override
		protected boolean tryAddAll(final JsonArray jsonArray, final Collection<? extends JsonElement> jsonElements) {
			throw new UnsupportedOperationException();
		}

		@Override
		protected boolean tryAddAll(final JsonArray jsonArray, final int index, final Collection<? extends JsonElement> jsonElements) {
			throw new UnsupportedOperationException();
		}

		@Override
		protected void tryClear(final JsonArray jsonArray) {
			throw new UnsupportedOperationException();
		}

		@Override
		protected boolean tryRemovePreflight(final Object object) {
			throw new UnsupportedOperationException();
		}

		@Override
		protected boolean tryRemove(final JsonArray jsonArray, final JsonElement jsonElement) {
			throw new UnsupportedOperationException();
		}

		@Override
		protected JsonElement tryRemove(final JsonArray jsonArray, final int index) {
			throw new UnsupportedOperationException();
		}

		@Override
		protected boolean tryRemoveAll(final JsonArray jsonArray, final Collection<?> objects) {
			throw new UnsupportedOperationException();
		}

		@Override
		protected boolean tryRetainAll(final JsonArray jsonArray, final Collection<?> objects) {
			throw new UnsupportedOperationException();
		}

		@Override
		protected JsonElement trySet(final JsonArray jsonArray, final int index, final JsonElement jsonElement) {
			throw new UnsupportedOperationException();
		}

	}

	private static final class MutableJsonArrayList
			extends AbstractJsonArrayList {

		private MutableJsonArrayList(final JsonArray jsonArray) {
			super(jsonArray);
		}

		@Override
		protected boolean tryAdd(final JsonArray jsonArray, final JsonElement jsonElement) {
			final int sizeBefore = jsonArray.size();
			jsonArray.add(jsonElement);
			final int sizeAfter = jsonArray.size();
			if ( sizeAfter - sizeBefore != 1 ) {
				throw new AssertionError();
			}
			return true;
		}

		@Override
		protected void tryAdd(final JsonArray jsonArray, final int index, final JsonElement jsonElement) {
			final Iterator<JsonElement> iterator = jsonArray.iterator();
			for ( int i = 0; i < index; i++ ) {
				iterator.next();
			}
			final Collection<JsonElement> tail = new ArrayList<>();
			final int size = jsonArray.size();
			for ( int i = index; i < size; i++ ) {
				tail.add(jsonArray.remove(index));
			}
			jsonArray.add(jsonElement);
			for ( final JsonElement element : tail ) {
				jsonArray.add(element);
			}
		}

		@Override
		protected boolean tryAddAll(final JsonArray jsonArray, final Collection<? extends JsonElement> jsonElements) {
			final int sizeBefore = jsonArray.size();
			jsonArray.addAll(from(jsonElements));
			final int sizeAfter = jsonArray.size();
			if ( sizeAfter - sizeBefore != jsonElements.size() ) {
				throw new AssertionError();
			}
			return true;
		}

		@Override
		protected boolean tryAddAll(final JsonArray jsonArray, final int index, final Collection<? extends JsonElement> jsonElements) {
			final Iterator<JsonElement> iterator = jsonArray.iterator();
			for ( int i = 0; i < index; i++ ) {
				iterator.next();
			}
			final Collection<JsonElement> tail = new ArrayList<>();
			final int sizeBefore = jsonArray.size();
			for ( int i = index; i < sizeBefore; i++ ) {
				tail.add(jsonArray.remove(index));
			}
			for ( final JsonElement jsonElement : jsonElements ) {
				jsonArray.add(jsonElement);
			}
			for ( final JsonElement element : tail ) {
				jsonArray.add(element);
			}
			final int sizeAfter = jsonArray.size();
			if ( sizeAfter - sizeBefore != jsonElements.size() ) {
				throw new AssertionError();
			}
			return true;
		}

		@Override
		protected void tryClear(final JsonArray jsonArray) {
			final int last = jsonArray.size() - 1;
			for ( int i = last; i >= 0; i-- ) {
				jsonArray.remove(i);
			}
		}

		@Override
		protected boolean tryRemovePreflight(final Object object) {
			return object instanceof JsonElement;
		}

		@Override
		protected boolean tryRemove(final JsonArray jsonArray, final JsonElement jsonElement) {
			return jsonArray.remove(jsonElement);
		}

		@Override
		protected JsonElement tryRemove(final JsonArray jsonArray, final int index) {
			return jsonArray.remove(index);
		}

		@Override
		protected boolean tryRemoveAll(final JsonArray jsonArray, final Collection<?> objects) {
			final int sizeBefore = jsonArray.size();
			for ( final Object object : objects ) {
				if ( object instanceof final JsonElement jsonElement ) {
					jsonArray.remove(jsonElement);
				}
			}
			final int sizeAfter = jsonArray.size();
			return sizeAfter != sizeBefore;
		}

		@Override
		protected boolean tryRetainAll(final JsonArray jsonArray, final Collection<?> objects) {
			final Collection<JsonElement> jsonElementsToRemove = new ArrayList<>();
			for ( final JsonElement jsonElement : jsonArray ) {
				if ( !objects.contains(jsonElement) ) {
					jsonElementsToRemove.add(jsonElement);
				}
			}
			return removeAll(jsonElementsToRemove);
		}

		@Override
		protected JsonElement trySet(final JsonArray jsonArray, final int index, final JsonElement jsonElement) {
			return jsonArray.set(index, jsonElement);
		}

	}

}
