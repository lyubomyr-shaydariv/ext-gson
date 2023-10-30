package lsh.ext.gson;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
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
	private final ITypeResolver typeResolver;

	public static <T> TypeAdapter<T> getInstance(final Gson gson, final String typePropertyName, final String valuePropertyName, final ITypeResolver typeResolver) {
		return new TypeAwareTypeAdapter<T>(gson, typePropertyName, valuePropertyName, typeResolver)
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
		in.beginObject();
		requireName(typePropertyName, in);
		final String typeName = in.nextString();
		requireName(valuePropertyName, in);
		final T value = gson.fromJson(in, typeResolver.resolveType(typeName));
		in.endObject();
		return value;
	}

	private static void requireName(final String expectedName, final JsonReader reader)
			throws IOException {
		final String actualName = reader.nextName();
		if ( !actualName.equals(expectedName) ) {
			throw new MalformedJsonException("Expected " + expectedName + " but was " + actualName);
		}
	}

	public interface ITypeResolver {

		Class<?> resolveType(String typeName);

	}

}
