package lsh.ext.gson.adapters;

import java.io.IOException;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * Represents a type adapter that should store its values in single strings. This class is {@code null}-safe and does not require its descendants to be wrapped
 * in {@link TypeAdapter#nullSafe()}.
 *
 * @param <T> Any type
 *
 * @author Lyubomyr Shaydariv
 */
public abstract class AbstractStringTypeAdapter<T>
		extends TypeAdapter<T> {

	/**
	 * @param text A string to convert to a value
	 *
	 * @return An object parsed from string.
	 */
	protected abstract T fromString(String text);

	/**
	 * @param value A value to convert to a string
	 *
	 * @return A string generated from the value.
	 */
	protected abstract String toString(T value);

	@Override
	@Nullable
	public final T read(final JsonReader in)
			throws IOException {
		if ( in.peek() == JsonToken.NULL ) {
			return null;
		}
		return fromString(in.nextString());
	}

	@Override
	public final void write(final JsonWriter out, @Nullable final T value)
			throws IOException {
		if ( value == null ) {
			out.nullValue();
			return;
		}
		out.value(toString(value));
	}

}
