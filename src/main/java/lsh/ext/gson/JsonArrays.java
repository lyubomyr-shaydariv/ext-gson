package lsh.ext.gson;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.experimental.UtilityClass;

/**
 * Provides miscellaneous {@link JsonArray} utility methods.
 *
 * @author Lyubomyr Shaydariv
 */
@UtilityClass
public final class JsonArrays {

	/**
	 * @return A new empty JSON array.
	 */
	public static JsonArray of() {
		return new JsonArray();
	}

	/**
	 * @param e1 Element 1.
	 *
	 * @return A new JSON array with one element.
	 */
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
	 */
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
	 */
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
	 */
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
	 */
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
	 */
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
	 */
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
	 */
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
	 */
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
	 */
	public static JsonArrayAccumulator arrayWith() {
		return new JsonArrayAccumulator();
	}

	/**
	 * Represents a {@link JsonArray} accumulator. Unlike a builder, the accumulator does not create a new object in with its final method, but accumulates a
	 * certain state with builder-like syntax.
	 */
	public static final class JsonArrayAccumulator {

		private final JsonArray jsonArray = new JsonArray();

		private JsonArrayAccumulator() {
		}

		/**
		 * @param jsonElement A JSON element.
		 *
		 * @return Self with the new element appended to the end of the array.
		 */
		public JsonArrayAccumulator add(final JsonElement jsonElement) {
			jsonArray.add(jsonElement);
			return this;
		}

		/**
		 * @param b A boolean value.
		 *
		 * @return Self with the new element appended to the end of the array.
		 */
		public JsonArrayAccumulator add(final Boolean b) {
			jsonArray.add(b);
			return this;
		}

		/**
		 * @param c A character value.
		 *
		 * @return Self with the new element appended to the end of the array.
		 */
		public JsonArrayAccumulator add(final Character c) {
			jsonArray.add(c);
			return this;
		}

		/**
		 * @param n A numeric value.
		 *
		 * @return Self with the new element appended to the end of the array.
		 */
		public JsonArrayAccumulator add(final Number n) {
			jsonArray.add(n);
			return this;
		}

		/**
		 * @param s A string value.
		 *
		 * @return Self with the new element appended to the end of the array.
		 */
		public JsonArrayAccumulator add(final String s) {
			jsonArray.add(s);
			return this;
		}

		/**
		 * @param jsonArray A JSON array.
		 *
		 * @return Self with all the new elements from the input array appended to the end of the array.
		 */
		public JsonArrayAccumulator addAll(final JsonArray jsonArray) {
			this.jsonArray.addAll(jsonArray);
			return this;
		}

		/**
		 * @return The accumulated {@link JsonArray} instance.
		 */
		@SuppressFBWarnings("EI_EXPOSE_REP")
		public JsonArray get() {
			return jsonArray;
		}

	}

	/**
	 * @param jsonArray JSON array to put into the view
	 *
	 * @return An immutable list view for the given JSON array
	 */
	public static List<JsonElement> asImmutableList(final JsonArray jsonArray) {
		return new ImmutableJsonArrayList(jsonArray);
	}

	/**
	 * @param jsonArray JSON array to put into the view
	 *
	 * @return A mutable list view for the given JSON array
	 */
	public static List<JsonElement> asMutableList(final JsonArray jsonArray) {
		return new MutableJsonArrayList(jsonArray);
	}

	private abstract static class AbstractJsonArrayList
			extends AbstractList<JsonElement> {

		private final JsonArray jsonArray;

		protected AbstractJsonArrayList(final JsonArray jsonArray) {
			this.jsonArray = jsonArray;
		}

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
				if ( object instanceof JsonElement ) {
					jsonArray.remove((JsonElement) object);
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
