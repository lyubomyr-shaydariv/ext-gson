package lsh.ext.gson.adapters;

import java.io.IOException;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * Represents an abstract type adapter factory for <i>optional</i> wrappers.
 *
 * @param <O> Optional type
 * @param <T> Value held by optional type
 *
 * @author Lyubomyr Shaydariv
 */
public abstract class AbstractOptionalTypeAdapter<O, T>
		extends TypeAdapter<O> {

	private final TypeAdapter<T> valueTypeAdapter;

	protected AbstractOptionalTypeAdapter(final TypeAdapter<T> valueTypeAdapter) {
		this.valueTypeAdapter = valueTypeAdapter;
	}

	/**
	 * @param optional Optional to get a value from
	 *
	 * @return A value from the given optional.
	 */
	@Nullable
	protected abstract T fromOptional(@Nonnull O optional);

	/**
	 * @param value Value to get an optional from
	 *
	 * @return An optional from the given value.
	 */
	@Nonnull
	protected abstract O toOptional(@Nullable T value);

	@Override
	public final void write(final JsonWriter out, final O optional)
			throws IOException {
		@Nullable
		final T value = fromOptional(optional);
		valueTypeAdapter.write(out, value);
	}

	@Override
	public final O read(final JsonReader in)
			throws IOException {
		@Nullable
		final T value = valueTypeAdapter.read(in);
		return toOptional(value);
	}

}
