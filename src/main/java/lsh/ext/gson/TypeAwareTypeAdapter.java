package lsh.ext.gson;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * Represents a type adapter to hold both object type name and object value. Example result JSON document:
 *
 * <pre>
 * {
 *     "$T": "foo.bar.Baz",
 *     "$V": {}
 * }
 * </pre>
 *
 * @author Lyubomyr Shaydariv
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class TypeAwareTypeAdapter<T>
		extends TypeAdapter<T> {

	private final Gson gson;
	private final String typePropertyName;
	private final String valuePropertyName;

	/**
	 * @param gson
	 * 		Gson instance
	 * @param typePropertyName
	 * 		Type property name
	 * @param valuePropertyName
	 * 		Value property name
	 * @param <T>
	 * 		Any type
	 *
	 * @return An instance of {@link TypeAwareTypeAdapter}.
	 */
	public static <T> TypeAdapter<T> getInstance(final Gson gson, final String typePropertyName, final String valuePropertyName) {
		return new TypeAwareTypeAdapter<>(gson, typePropertyName, valuePropertyName);
	}

	@Override
	public void write(final JsonWriter out, final Object value)
			throws IOException {
		final Class<?> type = value.getClass();
		out.beginObject();
		out.name(typePropertyName);
		out.value(type.getTypeName());
		out.name(valuePropertyName);
		gson.toJson(value, type, out);
		out.endObject();
	}

	@Override
	public T read(final JsonReader in)
			throws IOException {
		final JsonToken token = in.peek();
		switch ( token ) {
		case NULL:
			throw new AssertionError();
		case BEGIN_OBJECT:
			return parseObject(in);
		case BEGIN_ARRAY:
		case END_ARRAY:
		case END_OBJECT:
		case NAME:
		case STRING:
		case NUMBER:
		case BOOLEAN:
		case END_DOCUMENT:
			throw new MalformedJsonException("Unexpected " + token + " at " + in);
		default:
			throw new AssertionError(token);
		}
	}

	private T parseObject(final JsonReader in)
			throws IOException {
		try {
			in.beginObject();
			requireName(typePropertyName, in);
			final Class<?> type = Class.forName(in.nextString());
			requireName(valuePropertyName, in);
			final T value = gson.fromJson(in, type);
			in.endObject();
			return value;
		} catch ( final ClassNotFoundException ex ) {
			throw new IOException(ex);
		}
	}

	private static void requireName(final String expectedName, final JsonReader reader)
			throws IOException {
		final String actualName = reader.nextName();
		if ( !actualName.equals(expectedName) ) {
			throw new MalformedJsonException("Expected " + expectedName + " but was " + actualName);
		}
	}

}
