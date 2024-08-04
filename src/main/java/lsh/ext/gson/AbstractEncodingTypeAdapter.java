package lsh.ext.gson;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

public abstract class AbstractEncodingTypeAdapter<T, U>
		extends TypeAdapter<T> {

	protected abstract U encode(T value)
			throws IOException;

	protected abstract void writeEncoded(JsonWriter out, U encodedValue)
			throws IOException;

	protected abstract U readEncoded(JsonReader in)
			throws IOException;

	protected abstract T decode(U encodedValue)
			throws IOException;

	@Override
	public final void write(final JsonWriter out, final T value)
			throws IOException {
		writeEncoded(out, encode(value));
	}

	@Override
	public final T read(final JsonReader in)
			throws IOException {
		final U encodedValue = readEncoded(in);
		return decode(encodedValue);
	}

	public static final class JsonAsString<T>
			extends AbstractEncodingTypeAdapter<T, String> {

		private final TypeAdapter<T> typeAdapter;

		private JsonAsString(final TypeAdapter<T> typeAdapter) {
			this.typeAdapter = typeAdapter;
		}

		public static <T> TypeAdapter<T> getInstance(final TypeAdapter<T> typeAdapter) {
			return new JsonAsString<>(typeAdapter);
		}

		@Override
		protected String encode(final T value) {
			return typeAdapter.toJson(value);
		}

		@Override
		protected void writeEncoded(final JsonWriter out, final String encodedValue)
				throws IOException {
			out.value(encodedValue);
		}

		@Override
		protected String readEncoded(final JsonReader in)
				throws IOException {
			return in.nextString();
		}

		@Override
		protected T decode(final String encodedValue)
				throws IOException {
			return typeAdapter.fromJson(encodedValue);
		}

		@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
		public static final class Factory
				implements TypeAdapterFactory {

			@Override
			public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
				final TypeAdapter<T> typeAdapter = gson.getAdapter(typeToken);
				return getInstance(typeAdapter);
			}

		}

	}

}
