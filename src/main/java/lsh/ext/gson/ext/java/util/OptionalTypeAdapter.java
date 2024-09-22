package lsh.ext.gson.ext.java.util;

import java.io.IOException;
import java.util.Optional;
import javax.annotation.Nullable;

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
	@SuppressWarnings("OptionalAssignedToNull")
	public void write(final JsonWriter out, @Nullable final Optional<T> optional)
			throws IOException {
		if ( optional == null ) {
			out.nullValue();
			return;
		}
		@Nullable
		final T value = optional.orElse(null);
		if ( value == null ) {
			out.nullValue();
			return;
		}
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
