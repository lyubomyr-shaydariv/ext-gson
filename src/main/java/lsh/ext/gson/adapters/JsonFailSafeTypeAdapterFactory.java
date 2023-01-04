package lsh.ext.gson.adapters;

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
import lombok.RequiredArgsConstructor;

/**
 * This type adapter factory skips malformed JSON values. For example, consider the following mapping:
 *
 * <pre>
 *     class Foo { Bar bar; }
 *     class Bar { }
 * </pre>
 *
 * And the following JSON:
 *
 * <pre>
 *     {"bar": []}
 * </pre>
 *
 * The {@code bar} cannot be an array, therefore adding the type adapter factory will assign {@code null} to the `bar` field once JSON parsing fails.
 *
 * @author Lyubomyr Shaydariv
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonFailSafeTypeAdapterFactory
		implements TypeAdapterFactory {

	private static final TypeAdapterFactory instance = new JsonFailSafeTypeAdapterFactory();

	/**
	 * @return An instance of {@link JsonFailSafeTypeAdapterFactory}.
	 */
	static TypeAdapterFactory getInstance() {
		return instance;
	}

	@Override
	@Nullable
	public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
		if ( typeToken.getRawType().isPrimitive() ) {
			return null;
		}
		final TypeAdapter<T> delegateTypeAdapter = gson.getAdapter(typeToken);
		return new JsonFailSafeTypeAdapter<>(delegateTypeAdapter);
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	private static final class JsonFailSafeTypeAdapter<T>
			extends TypeAdapter<T> {

		private final TypeAdapter<T> delegateTypeAdapter;

		@Override
		public void write(final JsonWriter out, final T value)
				throws IOException {
			delegateTypeAdapter.write(out, value);
		}

		@Override
		public T read(final JsonReader in)
				throws IOException {
			try {
				return delegateTypeAdapter.read(in);
			} catch ( final MalformedJsonException | RuntimeException ignored ) {
				return fallback(in);
			}
		}

		@Nullable
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
