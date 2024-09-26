package lsh.ext.gson.adapters;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.function.Function;
import java.util.function.Predicate;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class EncodedTypeTypeAdapter<T, TT extends Type>
		extends TypeAdapter<T> {

	private final Gson gson;
	private final String typePropertyName;
	private final String valuePropertyName;
	private final Function<Object, ? extends TT> resolveType;
	private final Function<? super TT, String> encode;
	private final Function<? super String, ? extends TT> decode;
	private final Predicate<String> guard;

	public static <T, TT extends Type> TypeAdapter<T> getInstance(
			final Gson gson,
			final String typePropertyName,
			final String valuePropertyName,
			final Function<Object, ? extends TT> resolveType,
			final Function<? super TT, String> encode,
			final Function<? super String, ? extends TT> decode,
			final Predicate<String> guard
	) {
		return new EncodedTypeTypeAdapter<T, TT>(gson, typePropertyName, valuePropertyName, resolveType, encode, decode, guard)
				.nullSafe();
	}

	@Override
	public void write(final JsonWriter out, final T value)
			throws IOException {
		out.beginObject();
		out.name(typePropertyName);
		final TT type = resolveType.apply(value);
		out.value(encode.apply(type));
		out.name(valuePropertyName);
		gson.toJson(value, type, out);
		out.endObject();
	}

	@Override
	public T read(final JsonReader in)
			throws IOException {
		in.beginObject();
		requireName(typePropertyName, in);
		final String typeName = in.nextString();
		if ( !guard.test(typeName) ) {
			throw new IOException("Type name " + typeName + " is not allowed");
		}
		requireName(valuePropertyName, in);
		final T value = gson.fromJson(in, decode.apply(typeName));
		in.endObject();
		return value;
	}

	private static void requireName(final String expectedName, final JsonReader in)
			throws IOException {
		final String actualName = in.nextName();
		if ( !actualName.equals(expectedName) ) {
			throw new MalformedJsonException("Expected " + expectedName + " but was " + actualName + " at " + in.getPath());
		}
	}

}
