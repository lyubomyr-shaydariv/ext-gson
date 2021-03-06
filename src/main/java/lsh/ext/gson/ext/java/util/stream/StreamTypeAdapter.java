package lsh.ext.gson.ext.java.util.stream;

import java.util.Iterator;
import java.util.stream.Stream;
import javax.annotation.Nonnull;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import lsh.ext.gson.ICloseableIterator;
import lsh.ext.gson.adapters.AbstractCursorTypeAdapter;

/**
 * <p>
 * Type adapter for {@link Stream}. Streams are supposed to read and write JSON arrays only.
 * </p>
 * <p>
 * <b>CAUTION</b> Note that the {@link #read(JsonReader)} method returns a closeable iterator that must be closed manually.
 * </p>
 *
 * @author Lyubomyr Shaydariv
 * @see StreamTypeAdapterFactory
 * @since 0-SNAPSHOT
 */
public final class StreamTypeAdapter<E>
		extends AbstractCursorTypeAdapter<Stream<E>, E> {

	private StreamTypeAdapter(final TypeAdapter<E> elementTypeAdapter) {
		super(elementTypeAdapter);
	}

	/**
	 * @param elementTypeAdapter Element type adapter
	 * @param <E>                Iterator element type
	 *
	 * @return An instance of {@link StreamTypeAdapter}.
	 */
	public static <E> TypeAdapter<Stream<E>> create(final TypeAdapter<E> elementTypeAdapter) {
		return new StreamTypeAdapter<>(elementTypeAdapter)
				.nullSafe();
	}

	@Nonnull
	@Override
	protected Iterator<E> toIterator(@Nonnull final Stream<E> stream) {
		return stream.iterator();
	}

	@Nonnull
	@Override
	protected Stream<E> fromIterator(@Nonnull final ICloseableIterator<E> iterator) {
		return Streams.from(iterator);
	}

}
