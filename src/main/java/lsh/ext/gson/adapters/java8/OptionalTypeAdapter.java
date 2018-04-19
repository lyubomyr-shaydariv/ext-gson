package lsh.ext.gson.adapters.java8;

import java.io.IOException;
import java.util.Optional;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * Represents a type adapter for {@link Optional} from Google Guava.
 *
 * @author Lyubomyr Shaydariv
 * @see OptionalTypeAdapterFactory
 * @since 0-SNAPSHOT
 */
public final class OptionalTypeAdapter<T>
		extends TypeAdapter<Optional<T>> {

	private final TypeAdapter<T> valueTypeAdapter;

	private OptionalTypeAdapter(final TypeAdapter<T> valueTypeAdapter) {
		this.valueTypeAdapter = valueTypeAdapter;
	}

	/**
	 * Returns an {@link Optional} type adapter.
	 *
	 * @param valueTypeAdapter {@link Optional} backed value type adapter
	 * @param <T>              Optional type parameterization
	 *
	 * @return Type adapter instance
	 *
	 * @since 0-SNAPSHOT
	 */
	public static <T> TypeAdapter<Optional<T>> get(final TypeAdapter<T> valueTypeAdapter) {
		return new OptionalTypeAdapter<>(valueTypeAdapter);
	}

	@Override
	@SuppressWarnings("resource")
	public void write(final JsonWriter out, final Optional<T> optional)
			throws IOException {
		@Nullable
		final T value = optional.orElse(null);
		valueTypeAdapter.write(out, value);
	}

	@Override
	public Optional<T> read(final JsonReader in)
			throws IOException {
		@Nullable
		final T value = valueTypeAdapter.read(in);
		return Optional.ofNullable(value);
	}

}
