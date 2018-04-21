package lsh.ext.gson.adapters.java8;

import java.io.IOException;
import java.util.Iterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lsh.ext.gson.CloseableIterators;
import lsh.ext.gson.ICloseableIterator;
import lsh.ext.gson.adapters.JsonReaderIterator;

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
		extends TypeAdapter<Stream<E>> {

	private final TypeAdapter<E> elementTypeAdapter;

	private StreamTypeAdapter(final TypeAdapter<E> elementTypeAdapter) {
		this.elementTypeAdapter = elementTypeAdapter;
	}

	/**
	 * @param elementTypeAdapter Element type adapter
	 * @param <E>                Iterator element type
	 *
	 * @return An instance of {@link StreamTypeAdapter}.
	 */
	public static <E> TypeAdapter<Stream<E>> get(final TypeAdapter<E> elementTypeAdapter) {
		return new StreamTypeAdapter<>(elementTypeAdapter)
				.nullSafe();
	}

	@Override
	@SuppressWarnings("resource")
	public void write(final JsonWriter out, final Stream<E> stream)
			throws IOException {
		out.beginArray();
		final Iterator<E> iterator = stream.iterator();
		while ( iterator.hasNext() ) {
			final E element = iterator.next();
			elementTypeAdapter.write(out, element);
		}
		out.endArray();
	}

	@Override
	public Stream<E> read(final JsonReader in) {
		@SuppressWarnings("resource")
		final ICloseableIterator<E> iterator = JsonReaderIterator.get(elementTypeAdapter, in);
		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, 0), false)
				.onClose(() -> {
					try {
						CloseableIterators.tryClose(iterator);
					} catch ( final Exception ex ) {
						throw new RuntimeException(ex);
					}
				});
	}

}
