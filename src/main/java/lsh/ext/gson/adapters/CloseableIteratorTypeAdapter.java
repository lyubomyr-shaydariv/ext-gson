package lsh.ext.gson.adapters;

import java.io.IOException;
import java.util.Iterator;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lsh.ext.gson.CloseableIterators;
import lsh.ext.gson.ICloseableIterator;

/**
 * <p>
 * Type adapter for {@link Iterator}. Iterators are supposed to read and write JSON arrays only.
 * </p>
 * <p>
 * <b>CAUTION</b> Note that the {@link #read(JsonReader)} method returns a closeable iterator that must be closed manually.
 * </p>
 *
 * @author Lyubomyr Shaydariv
 * @see CloseableIteratorTypeAdapterFactory
 * @see CloseableIterators
 * @since 0-SNAPSHOT
 */
public final class CloseableIteratorTypeAdapter<E>
		extends TypeAdapter<ICloseableIterator<E>> {

	private final TypeAdapter<E> elementTypeAdapter;

	private CloseableIteratorTypeAdapter(final TypeAdapter<E> elementTypeAdapter) {
		this.elementTypeAdapter = elementTypeAdapter;
	}

	/**
	 * @param elementTypeAdapter Element type adapter
	 * @param <E>                Iterator element type
	 *
	 * @return An instance of {@link CloseableIteratorTypeAdapter}.
	 */
	public static <E> TypeAdapter<ICloseableIterator<E>> get(final TypeAdapter<E> elementTypeAdapter) {
		return new CloseableIteratorTypeAdapter<>(elementTypeAdapter)
				.nullSafe();
	}

	@Override
	@SuppressWarnings("resource")
	public void write(final JsonWriter out, final ICloseableIterator<E> iterator)
			throws IOException {
		out.beginArray();
		while ( iterator.hasNext() ) {
			final E element = iterator.next();
			elementTypeAdapter.write(out, element);
		}
		out.endArray();
	}

	@Override
	public ICloseableIterator<E> read(final JsonReader in) {
		return JsonReaderIterator.get(elementTypeAdapter, in);
	}

}
