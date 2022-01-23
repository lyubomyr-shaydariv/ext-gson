package lsh.ext.gson.adapters;

import java.util.Enumeration;
import java.util.Iterator;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;

/**
 * Represents a type adapter factory for {@link Iterator}.
 *
 * @param <E> Element type
 *
 * @author Lyubomyr Shaydariv
 * @see CloseableEnumerationTypeAdapter
 */
public final class CloseableEnumerationTypeAdapterFactory<E>
		extends AbstractCursorTypeAdapterFactory<E> {

	private static final TypeAdapterFactory instance = new CloseableEnumerationTypeAdapterFactory<>();

	private CloseableEnumerationTypeAdapterFactory() {
		super(Enumeration.class);
	}

	/**
	 * @return An instance of {@link CloseableEnumerationTypeAdapterFactory}.
	 */
	public static TypeAdapterFactory getInstance() {
		return instance;
	}

	@Override
	protected TypeAdapter<?> createCursorTypeAdapter(final TypeAdapter<?> elementTypeAdapter) {
		return CloseableEnumerationTypeAdapter.getInstance(elementTypeAdapter);
	}

}
