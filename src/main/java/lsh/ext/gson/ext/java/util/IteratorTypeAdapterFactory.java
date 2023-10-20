package lsh.ext.gson.ext.java.util;

import java.util.Iterator;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import lombok.Getter;
import lsh.ext.gson.AbstractCursorTypeAdapterFactory;

/**
 * Represents a type adapter factory for {@link Iterator}.
 *
 * @param <E>
 * 		Element type
 */
public final class IteratorTypeAdapterFactory<E>
		extends AbstractCursorTypeAdapterFactory<E> {

	@Getter
	private static final TypeAdapterFactory instance = new IteratorTypeAdapterFactory<>();

	private IteratorTypeAdapterFactory() {
		super(Iterator.class);
	}

	@Override
	protected TypeAdapter<?> createCursorTypeAdapter(final TypeAdapter<?> elementTypeAdapter) {
		return Adapter.getInstance(elementTypeAdapter);
	}

	/**
	 * Type adapter for {@link Iterator}. Iterators are supposed to read and write JSON arrays only.
	 */
	public static final class Adapter<E>
			extends AbstractCursorTypeAdapterFactory.Adapter<Iterator<E>, E> {

		private Adapter(final TypeAdapter<E> elementTypeAdapter) {
			super(elementTypeAdapter);
		}

		/**
		 * @param elementTypeAdapter
		 * 		Element type adapter
		 * @param <E>
		 * 		Iterator element type
		 *
		 * @return An instance of {@link Adapter}.
		 */
		public static <E> TypeAdapter<Iterator<E>> getInstance(final TypeAdapter<E> elementTypeAdapter) {
			return new Adapter<>(elementTypeAdapter)
					.nullSafe();
		}

		@Override
		protected Iterator<E> toIterator(final Iterator<E> iterator) {
			return iterator;
		}

		@Override
		protected Iterator<E> fromIterator(final Iterator<E> iterator) {
			return iterator;
		}

	}

}
