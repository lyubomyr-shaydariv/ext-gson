package lsh.ext.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonWriter;

/**
 * A class representing a type adapter designed for reading purposes only.
 *
 * @author Lyubomyr Shaydariv
 */
public abstract class AbstractReadOnlyTypeAdapter<T>
		extends TypeAdapter<T> {

	/**
	 * @param out
	 * 		JSON writer, has no effect
	 * @param value
	 * 		Any value, has no effect
	 */
	@Override
	public final void write(final JsonWriter out, final T value) {
		throw new UnsupportedOperationException();
	}

}
