package lsh.ext.gson.adapters;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lsh.ext.gson.AutoCloseableEnumerations;
import lsh.ext.gson.IAutoCloseableEnumeration;

/**
 * <p>
 * Type adapter for {@link IAutoCloseableEnumeration<E>}. Enumerations are supposed to read and write JSON arrays only.
 * </p>
 * <p>
 * <b>CAUTION</b> Note that the {@link #read(JsonReader)} method returns a closeable iterator that must be closed manually.
 * </p>
 *
 * @author Lyubomyr Shaydariv
 * @see AutoCloseableEnumerationTypeAdapterFactory
 * @since 0-SNAPSHOT
 */
public final class AutoCloseableEnumerationTypeAdapter<E>
		extends TypeAdapter<IAutoCloseableEnumeration<E>> {

	private final TypeAdapter<E> elementTypeAdapter;

	private AutoCloseableEnumerationTypeAdapter(final TypeAdapter<E> elementTypeAdapter) {
		this.elementTypeAdapter = elementTypeAdapter;
	}

	/**
	 * @param elementTypeAdapter Element type adapter
	 * @param <E>                Iterator element type
	 *
	 * @return An instance of {@link AutoCloseableEnumerationTypeAdapter}.
	 */
	public static <E> TypeAdapter<IAutoCloseableEnumeration<E>> get(final TypeAdapter<E> elementTypeAdapter) {
		return new AutoCloseableEnumerationTypeAdapter<>(elementTypeAdapter)
				.nullSafe();
	}

	@Override
	@SuppressWarnings("resource")
	public void write(final JsonWriter out, final IAutoCloseableEnumeration<E> enumeration)
			throws IOException {
		out.beginArray();
		while ( enumeration.hasMoreElements() ) {
			final E element = enumeration.nextElement();
			elementTypeAdapter.write(out, element);
		}
		out.endArray();
	}

	@Override
	public IAutoCloseableEnumeration<E> read(final JsonReader in) {
		return AutoCloseableEnumerations.from(JsonReaderIterator.get(elementTypeAdapter, in));
	}

}
