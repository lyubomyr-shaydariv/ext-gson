package lsh.ext.gson.adapters;

import java.util.Enumeration;
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
 * @see CloseableEnumerationTypeAdapter
 * @since 0-SNAPSHOT
 */
public final class CloseableEnumerationTypeAdapterFactory<E>
		extends AbstractCursorTypeAdapterFactory<E> {

	private static final TypeAdapterFactory defaultInstance = new CloseableEnumerationTypeAdapterFactory<>();

	private CloseableEnumerationTypeAdapterFactory() {
		super(Enumeration.class);
	}

	/**
	 * @return An instance of {@link CloseableEnumerationTypeAdapterFactory}.
	 */
	public static TypeAdapterFactory getDefaultInstance() {
		return defaultInstance;
	}

	@Nonnull
	@Override
	protected TypeAdapter<?> createCursorTypeAdapter(@Nonnull final TypeAdapter<?> elementTypeAdapter) {
		return CloseableEnumerationTypeAdapter.get(elementTypeAdapter);
	}

}
