package lsh.ext.gson.ext.java;

import java.util.Iterator;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.stream.JsonReader;
import lombok.Getter;
import lsh.ext.gson.AbstractCursorTypeAdapterFactory;

/**
 * Represents a type adapter factory for {@link Iterator}.
 *
 * @param <E>
 * 		Element type
 *
 * @author Lyubomyr Shaydariv
 */
public final class CloseableIteratorTypeAdapterFactory<E>
		extends AbstractCursorTypeAdapterFactory<E> {

	@Getter
	private static final TypeAdapterFactory instance = new CloseableIteratorTypeAdapterFactory<>();

	private CloseableIteratorTypeAdapterFactory() {
		super(Iterator.class);
	}

	@Override
	protected TypeAdapter<?> createCursorTypeAdapter(final TypeAdapter<?> elementTypeAdapter) {
		return Adapter.getInstance(elementTypeAdapter);
	}

	/**
	 * Type adapter for {@link Iterator}. Iterators are supposed to read and write JSON arrays only.
	 *
	 * <p>
	 * <b>CAUTION</b> Note that the {@link #read(JsonReader)} method returns a closeable iterator that must be closed manually.
	 * </p>
	 *
	 * @author Lyubomyr Shaydariv
	 */
	public static final class Adapter<E>
			extends AbstractCursorTypeAdapterFactory.Adapter<ICloseableIterator<E>, E> {

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
		public static <E> TypeAdapter<ICloseableIterator<E>> getInstance(final TypeAdapter<E> elementTypeAdapter) {
			return new Adapter<>(elementTypeAdapter);
		}

		@Override
		protected Iterator<E> toIterator(final ICloseableIterator<E> iterator) {
			return iterator;
		}

		@Override
		protected ICloseableIterator<E> fromIterator(final ICloseableIterator<E> iterator) {
			return iterator;
		}

	}

}
