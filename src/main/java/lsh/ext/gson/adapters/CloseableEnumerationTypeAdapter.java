package lsh.ext.gson.adapters;

import java.util.Iterator;
import javax.annotation.Nonnull;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import lsh.ext.gson.CloseableEnumerations;
import lsh.ext.gson.CloseableIterators;
import lsh.ext.gson.ICloseableEnumeration;
import lsh.ext.gson.ICloseableIterator;

/**
 * <p>
 * Type adapter for {@link ICloseableEnumeration <E>}. Enumerations are supposed to read and write JSON arrays only.
 * </p>
 * <p>
 * <b>CAUTION</b> Note that the {@link #read(JsonReader)} method returns a closeable iterator that must be closed manually.
 * </p>
 *
 * @author Lyubomyr Shaydariv
 * @see CloseableEnumerationTypeAdapterFactory
 * @since 0-SNAPSHOT
 */
public final class CloseableEnumerationTypeAdapter<E>
		extends AbstractCursorTypeAdapter<ICloseableEnumeration<? extends E>, E> {

	private CloseableEnumerationTypeAdapter(final TypeAdapter<E> elementTypeAdapter) {
		super(elementTypeAdapter);
	}

	/**
	 * @param elementTypeAdapter Element type adapter
	 * @param <E>                Iterator element type
	 *
	 * @return An instance of {@link CloseableEnumerationTypeAdapter}.
	 */
	public static <E> TypeAdapter<ICloseableEnumeration<? extends E>> get(final TypeAdapter<E> elementTypeAdapter) {
		return new CloseableEnumerationTypeAdapter<>(elementTypeAdapter)
				.nullSafe();
	}

	@Nonnull
	@Override
	protected Iterator<? extends E> toIterator(@Nonnull final ICloseableEnumeration<? extends E> enumeration) {
		return CloseableIterators.from(enumeration);
	}

	@Nonnull
	@Override
	protected ICloseableEnumeration<E> fromIterator(@Nonnull final ICloseableIterator<? extends E> iterator) {
		return CloseableEnumerations.from(iterator);
	}

}
