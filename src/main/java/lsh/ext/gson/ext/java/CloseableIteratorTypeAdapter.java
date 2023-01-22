package lsh.ext.gson.ext.java;

import java.util.Iterator;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import lsh.ext.gson.ICloseableIterator;
import lsh.ext.gson.adapters.AbstractCursorTypeAdapter;

/**
 * Type adapter for {@link Iterator}. Iterators are supposed to read and write JSON arrays only.
 *
 * <p>
 * <b>CAUTION</b> Note that the {@link #read(JsonReader)} method returns a closeable iterator that must be closed manually.
 * </p>
 *
 * @author Lyubomyr Shaydariv
 * @see CloseableIteratorTypeAdapterFactory
 * @see CloseableIterators
 */
public final class CloseableIteratorTypeAdapter<E>
		extends AbstractCursorTypeAdapter<ICloseableIterator<E>, E> {

	private CloseableIteratorTypeAdapter(final TypeAdapter<E> elementTypeAdapter) {
		super(elementTypeAdapter);
	}

	/**
	 * @param elementTypeAdapter
	 * 		Element type adapter
	 * @param <E>
	 * 		Iterator element type
	 *
	 * @return An instance of {@link CloseableIteratorTypeAdapter}.
	 */
	public static <E> TypeAdapter<ICloseableIterator<E>> getInstance(final TypeAdapter<E> elementTypeAdapter) {
		return new CloseableIteratorTypeAdapter<>(elementTypeAdapter);
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
