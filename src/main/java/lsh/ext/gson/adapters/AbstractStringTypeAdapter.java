package lsh.ext.gson.adapters;

import java.io.IOException;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * <p>
 * Represents a type adapter that should store its values in single strings. This class is {@code null}-safe and does not require its descendants to be wrapped
 * in {@link TypeAdapter#nullSafe()}.
 * </p>
 *
 * @param <T>
 *
 * @author Lyubomyr Shaydariv
 * @since 0-SNAPSHOT
 */
public abstract class AbstractStringTypeAdapter<T>
		extends TypeAdapter<T> {

	/**
	 * @param string A string to convert to a value
	 *
	 * @return An object parsed from string.
	 */
	@Nonnull
	protected abstract T fromString(@Nonnull String string);

	/**
	 * @param value A value to convert to a string
	 *
	 * @return A string generated from the value.
	 */
	@Nonnull
	protected abstract String toString(@Nonnull T value);

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
	@SuppressWarnings("resource")
	public final void write(final JsonWriter out, @Nullable final T value)
			throws IOException {
		if ( value == null ) {
			out.nullValue();
		} else {
			out.value(toString(value));
		}
	}

}
