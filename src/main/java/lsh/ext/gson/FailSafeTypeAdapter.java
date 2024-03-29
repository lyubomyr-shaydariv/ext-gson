package lsh.ext.gson;

import java.io.IOException;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
final class FailSafeTypeAdapter<T>
		extends TypeAdapter<T> {

	private final TypeAdapter<T> typeAdapter;

	private static <T> TypeAdapter<T> getInstance(final TypeAdapter<T> typeAdapter) {
		return new FailSafeTypeAdapter<T>(typeAdapter)
				.nullSafe();
	}

	@Override
	public void write(final JsonWriter out, final T value)
			throws IOException {
		typeAdapter.write(out, value);
	}

	@Override
	@Nullable
	public T read(final JsonReader in)
			throws IOException {
		try {
			return typeAdapter.read(in);
		} catch ( final MalformedJsonException | RuntimeException ignored ) {
			JsonReaders.skipValue(in);
			return null;
		}
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Factory
			implements TypeAdapterFactory {

		@Getter
		private static final TypeAdapterFactory instance = new Factory();

		@Override
		@Nullable
		public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
			if ( typeToken.getRawType().isPrimitive() ) {
				return null;
			}
			final TypeAdapter<T> typeAdapter = gson.getAdapter(typeToken);
			return FailSafeTypeAdapter.getInstance(typeAdapter);
		}

	}

}
