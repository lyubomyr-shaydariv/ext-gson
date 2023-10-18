package lsh.ext.gson;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * Represents a type adapter that should store its values in single strings.
 *
 * @param <T>
 * 		Any type
 */
public abstract class AbstractStringTypeAdapter<T>
		extends TypeAdapter<T> {

	/**
	 * @param text
	 * 		A string to convert to a value
	 *
	 * @return An object parsed from string.
	 */
	protected abstract T fromString(String text);

	/**
	 * @param value
	 * 		A value to convert to a string
	 *
	 * @return A string generated from the value.
	 */
	protected abstract String toString(T value);

	@Override
	public final T read(final JsonReader in)
			throws IOException {
		return fromString(in.nextString());
	}

	@Override
	public final void write(final JsonWriter out, final T value)
			throws IOException {
		out.value(toString(value));
	}

}
