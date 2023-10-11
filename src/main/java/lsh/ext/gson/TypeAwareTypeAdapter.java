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

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class TypeAwareTypeAdapter<T>
		extends TypeAdapter<T> {

	private final Gson gson;
	private final String typePropertyName;
	private final String valuePropertyName;

	public static <T> TypeAdapter<T> getInstance(final Gson gson, final String typePropertyName, final String valuePropertyName) {
		return new TypeAwareTypeAdapter<T>(gson, typePropertyName, valuePropertyName)
				.nullSafe();
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
	@SuppressWarnings("checkstyle:CyclomaticComplexity")
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
