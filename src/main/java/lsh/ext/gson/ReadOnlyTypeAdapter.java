package lsh.ext.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonWriter;

/**
 * A class representing a type adapter designed for reading purposes only.
 *
 * @author Lyubomyr Shaydariv
 * @since 0-SNAPSHOT
 */
public abstract class ReadOnlyTypeAdapter<T>
		extends TypeAdapter<T> {

	/**
	 * @param out   JSON writer, has no effect
	 * @param value Any value, has no effect
	 *
	 * @throws UnsupportedOperationException Always.
	 */
	@Override
	public final void write(final JsonWriter out, final T value)
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

}
