package lsh.ext.gson.adapters;

import java.util.Iterator;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;

/**
 * Represents a type adapter factory for {@link Iterator}.
 *
 * @param <E> Element type
 *
 * @author Lyubomyr Shaydariv
 * @see CloseableIteratorTypeAdapter
 */
public final class CloseableIteratorTypeAdapterFactory<E>
		extends AbstractCursorTypeAdapterFactory<E> {

	private static final TypeAdapterFactory defaultInstance = new CloseableIteratorTypeAdapterFactory<>();

	private CloseableIteratorTypeAdapterFactory() {
		super(Iterator.class);
	}

	/**
	 * @return An instance of {@link CloseableIteratorTypeAdapterFactory}.
	 */
	public static TypeAdapterFactory getDefaultInstance() {
		return defaultInstance;
	}

	@Override
	protected TypeAdapter<?> createCursorTypeAdapter(final TypeAdapter<?> elementTypeAdapter) {
		return CloseableIteratorTypeAdapter.get(elementTypeAdapter);
	}

}
