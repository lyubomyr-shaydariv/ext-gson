package lsh.ext.gson.adapters;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lsh.ext.gson.CloseableEnumerations;
import lsh.ext.gson.ICloseableEnumeration;

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
		extends TypeAdapter<ICloseableEnumeration<E>> {

	private final TypeAdapter<E> elementTypeAdapter;

	private CloseableEnumerationTypeAdapter(final TypeAdapter<E> elementTypeAdapter) {
		this.elementTypeAdapter = elementTypeAdapter;
	}

	/**
	 * @param elementTypeAdapter Element type adapter
	 * @param <E>                Iterator element type
	 *
	 * @return An instance of {@link CloseableEnumerationTypeAdapter}.
	 */
	public static <E> TypeAdapter<ICloseableEnumeration<E>> get(final TypeAdapter<E> elementTypeAdapter) {
		return new CloseableEnumerationTypeAdapter<>(elementTypeAdapter)
				.nullSafe();
	}

	@Override
	@SuppressWarnings("resource")
	public void write(final JsonWriter out, final ICloseableEnumeration<E> enumeration)
			throws IOException {
		out.beginArray();
		while ( enumeration.hasMoreElements() ) {
			final E element = enumeration.nextElement();
			elementTypeAdapter.write(out, element);
		}
		out.endArray();
	}

	@Override
	public ICloseableEnumeration<E> read(final JsonReader in) {
		return CloseableEnumerations.from(JsonReaderIterator.get(elementTypeAdapter, in));
	}

}
