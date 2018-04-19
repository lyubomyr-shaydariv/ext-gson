package lsh.ext.gson.adapters;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Iterator;

import com.google.gson.Gson;
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

	private final Type elementType;
	private final Gson gson;

	private AutoCloseableIteratorTypeAdapter(final Type elementType, final Gson gson) {
		this.elementType = elementType;
		this.gson = gson;
	}

	/**
	 * @param elementType Element type
	 * @param gson        Gson instance
	 * @param <E>         Iterator element type
	 *
	 * @return An instance of {@link AutoCloseableIteratorTypeAdapter}.
	 */
	public static <E> TypeAdapter<IAutoCloseableIterator<E>> get(final Type elementType, final Gson gson) {
		return new AutoCloseableIteratorTypeAdapter<E>(elementType, gson)
				.nullSafe();
	}

	@Override
	@SuppressWarnings("resource")
	public void write(final JsonWriter out, final IAutoCloseableIterator<E> iterator)
			throws IOException {
		out.beginArray();
		while ( iterator.hasNext() ) {
			final E next = iterator.next();
			gson.toJson(next, elementType, out);
		}
		out.endArray();
	}

	@Override
	public IAutoCloseableIterator<E> read(final JsonReader in) {
		return JsonReaderIterator.get(elementType, gson, in);
	}

}
