package lsh.ext.gson.adapters;

import java.util.Iterator;
import javax.annotation.Nonnull;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;

/**
 * Represents a type adapter factory for {@link Iterator}.
 *
 * @param <E> Element type
 *
 * @author Lyubomyr Shaydariv
 * @see CloseableIteratorTypeAdapter
 * @since 0-SNAPSHOT
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
	public static TypeAdapterFactory get() {
		return instance;
	}

	@Nonnull
	@Override
	protected TypeAdapter<?> createCursorTypeAdapter(@Nonnull final TypeAdapter<?> elementTypeAdapter) {
		return CloseableIteratorTypeAdapter.get(elementTypeAdapter);
	}

}
