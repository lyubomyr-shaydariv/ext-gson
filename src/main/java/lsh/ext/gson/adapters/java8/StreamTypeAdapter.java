package lsh.ext.gson.adapters.java8;

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
		extends AbstractCursorTypeAdapter<Stream<? extends E>, E> {

	private StreamTypeAdapter(final TypeAdapter<E> elementTypeAdapter) {
		super(elementTypeAdapter);
	}

	/**
	 * @param elementTypeAdapter Element type adapter
	 * @param <E>                Iterator element type
	 *
	 * @return An instance of {@link StreamTypeAdapter}.
	 */
	public static <E> TypeAdapter<Stream<? extends E>> get(final TypeAdapter<E> elementTypeAdapter) {
		return new StreamTypeAdapter<>(elementTypeAdapter)
				.nullSafe();
	}

	@Nonnull
	@Override
	protected Iterator<? extends E> toIterator(@Nonnull final Stream<? extends E> stream) {
		return stream.iterator();
	}

	@Nonnull
	@Override
	protected Stream<? extends E> fromIterator(@Nonnull final ICloseableIterator<? extends E> iterator) {
		return Streams.from(iterator);
	}

}