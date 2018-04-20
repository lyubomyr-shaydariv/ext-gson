package lsh.ext.gson.adapters;

import java.io.IOException;
import java.util.Iterator;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lsh.ext.gson.AutoCloseableIterators;
import lsh.ext.gson.IAutoCloseableIterator;

/**
 * <p>
 * Type adapter for {@link Iterator}. Iterators are supposed to read and write JSON arrays only.
 * </p>
 * <p>
 * <b>CAUTION</b> Note that the {@link #read(JsonReader)} method returns a closeable iterator that must be closed manually.
 * </p>
 *
 * @author Lyubomyr Shaydariv
 * @see AutoCloseableIteratorTypeAdapterFactory
 * @see AutoCloseableIterators
 * @since 0-SNAPSHOT
 */
public final class AutoCloseableIteratorTypeAdapter<E>
		extends TypeAdapter<IAutoCloseableIterator<E>> {

	private final TypeAdapter<E> elementTypeAdapter;

	private AutoCloseableIteratorTypeAdapter(final TypeAdapter<E> elementTypeAdapter) {
		this.elementTypeAdapter = elementTypeAdapter;
	}

	/**
	 * @param elementTypeAdapter Element type adapter
	 * @param <E>                Iterator element type
	 *
	 * @return An instance of {@link AutoCloseableIteratorTypeAdapter}.
	 */
	public static <E> TypeAdapter<IAutoCloseableIterator<E>> get(final TypeAdapter<E> elementTypeAdapter) {
		return new AutoCloseableIteratorTypeAdapter<>(elementTypeAdapter)
				.nullSafe();
	}

	@Override
	@SuppressWarnings("resource")
	public void write(final JsonWriter out, final IAutoCloseableIterator<E> iterator)
			throws IOException {
		out.beginArray();
		while ( iterator.hasNext() ) {
			final E element = iterator.next();
			elementTypeAdapter.write(out, element);
		}
		out.endArray();
	}

	@Override
	public IAutoCloseableIterator<E> read(final JsonReader in) {
		return JsonReaderIterator.get(elementTypeAdapter, in);
	}

}
