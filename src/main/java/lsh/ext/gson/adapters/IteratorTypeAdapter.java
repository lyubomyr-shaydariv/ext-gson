package lsh.ext.gson.adapters;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import static lsh.ext.gson.adapters.JsonReaderIterator.getJsonReaderIterator;

/**
 * <p>
 * Type adapter for {@link Iterator}. Iterators are supposed to read and write JSON arrays only.
 * </p>
 * <p>
 * <b>CAUTION</b> Note that the {@link #read(JsonReader)} method returns a closeable iterator that must be closed manually.
 * </p>
 *
 * @author Lyubomyr Shaydariv
 * @see IteratorTypeAdapterFactory
 * @see CloseableIterators
 * @since 0-SNAPSHOT
 */
public final class IteratorTypeAdapter<E>
		extends TypeAdapter<Iterator<E>> {

	private final Type elementType;
	private final Gson gson;

	private IteratorTypeAdapter(final Type elementType, final Gson gson) {
		this.elementType = elementType;
		this.gson = gson;
	}

	/**
	 * @param elementType Element type
	 * @param gson        Gson instance
	 * @param <E>         Iterator element type
	 *
	 * @return An instance of {@link IteratorTypeAdapter}.
	 */
	public static <E> TypeAdapter<Iterator<E>> getIteratorTypeAdapter(final Type elementType, final Gson gson) {
		return new IteratorTypeAdapter<>(elementType, gson);
	}

	@Override
	@SuppressWarnings("resource")
	public void write(final JsonWriter out, final Iterator<E> iterator)
			throws IOException {
		out.beginArray();
		while ( iterator.hasNext() ) {
			final E next = iterator.next();
			gson.toJson(next, elementType, out);
		}
		out.endArray();
	}

	@Override
	public Iterator<E> read(final JsonReader in) {
		return getJsonReaderIterator(elementType, gson, in);
	}

}
