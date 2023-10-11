package lsh.ext.gson;

import java.io.IOException;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonFailSafeTypeAdapterFactory
		implements TypeAdapterFactory {

	@Getter
	private static final TypeAdapterFactory instance = new JsonFailSafeTypeAdapterFactory();

	@Override
	@Nullable
	public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
		if ( typeToken.getRawType().isPrimitive() ) {
			return null;
		}
		final TypeAdapter<T> typeAdapter = gson.getAdapter(typeToken);
		return JsonFailSafeTypeAdapter.getInstance(typeAdapter);
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	private static final class JsonFailSafeTypeAdapter<T>
			extends TypeAdapter<T> {

		private final TypeAdapter<T> typeAdapter;

		private static <T> TypeAdapter<T> getInstance(final TypeAdapter<T> typeAdapter) {
			return new JsonFailSafeTypeAdapter<T>(typeAdapter)
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
				return fallback(in);
			}
		}

		@Nullable
		@SuppressWarnings("checkstyle:CyclomaticComplexity")
		private static <T> T fallback(final JsonReader in)
				throws IOException {
			final JsonToken jsonToken = in.peek();
			switch ( jsonToken ) {
			case BEGIN_ARRAY:
			case BEGIN_OBJECT:
			case NAME:
			case STRING:
			case NUMBER:
			case BOOLEAN:
			case NULL:
				in.skipValue();
				break;
			case END_ARRAY:
				in.endArray();
				break;
			case END_OBJECT:
				in.endObject();
				break;
			case END_DOCUMENT:
				// do nothing
				break;
			default:
				throw new AssertionError(jsonToken);
			}
			return null;
		}

	}

}
