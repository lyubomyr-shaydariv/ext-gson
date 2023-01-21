package lsh.ext.gson.ext.java;

import java.util.Iterator;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import lsh.ext.gson.adapters.AbstractCursorTypeAdapterFactory;

/**
 * Represents a type adapter factory for {@link Iterator}.
 *
 * @param <E>
 * 		Element type
 *
 * @author Lyubomyr Shaydariv
 * @see CloseableIteratorTypeAdapter
 */
public final class CloseableIteratorTypeAdapterFactory<E>
		extends AbstractCursorTypeAdapterFactory<E> {

	private static final TypeAdapterFactory instance = new CloseableIteratorTypeAdapterFactory<>();

	private CloseableIteratorTypeAdapterFactory() {
		super(Iterator.class);
	}

	/**
	 * @return An instance of {@link CloseableIteratorTypeAdapterFactory}.
	 */
	public static TypeAdapterFactory getInstance() {
		return instance;
	}

	@Override
	protected TypeAdapter<?> createCursorTypeAdapter(final TypeAdapter<?> elementTypeAdapter) {
		return CloseableIteratorTypeAdapter.getInstance(elementTypeAdapter);
	}

}
