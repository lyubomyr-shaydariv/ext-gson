package lsh.ext.gson.ext.com.google.common.base;

import java.io.IOException;
import javax.annotation.Nullable;

import com.google.common.base.Optional;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class OptionalTypeAdapter<T>
		extends TypeAdapter<Optional<T>> {

	private final TypeAdapter<T> valueTypeAdapter;

	public static <T> TypeAdapter<Optional<T>> getInstance(final TypeAdapter<T> valueTypeAdapter) {
		return new OptionalTypeAdapter<>(valueTypeAdapter)
				.nullSafe();
	}

	@Override
	public void write(final JsonWriter out, @SuppressWarnings("Guava") final Optional<T> optional)
			throws IOException {
		@Nullable
		final T value = optional.orNull();
		if ( value == null ) {
			out.nullValue();
			return;
		}
		valueTypeAdapter.write(out, value);
	}

	@Override
	@SuppressWarnings("Guava")
	public Optional<T> read(final JsonReader in)
			throws IOException {
		@Nullable
		final T value = valueTypeAdapter.read(in);
		return Optional.fromNullable(value);
	}

}
