package lsh.ext.gson;

import java.io.IOException;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

// TODO does this need to extend AbstractEncodingTypeAdapter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class TypeEncoderTypeAdapter<T, TT extends Type>
		extends TypeAdapter<T> {

	private final Gson gson;
	private final String typePropertyName;
	private final String valuePropertyName;
	private final ITypeResolver<? extends TT> typeResolver;
	private final ITypeEncoder<TT> typeEncoder;
	private final ITypeEncoder.IGuard guard;

	public static <T, TT extends Type> TypeAdapter<T> getInstance(
			final Gson gson,
			final String typePropertyName,
			final String valuePropertyName,
			final ITypeResolver<? extends TT> typeResolver,
			final ITypeEncoder<TT> typeEncoder,
			final ITypeEncoder.IGuard guard
	) {
		return new TypeEncoderTypeAdapter<T, TT>(gson, typePropertyName, valuePropertyName, typeResolver, typeEncoder, guard)
				.nullSafe();
	}

	@Override
	public void write(final JsonWriter out, final Object value)
			throws IOException {
		out.beginObject();
		out.name(typePropertyName);
		final TT type = typeResolver.typeOf(value);
		out.value(typeEncoder.encode(type));
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
		if ( !guard.passes(typeName) ) {
			throw new IOException("Type name " + typeName + " is not allowed");
		}
		requireName(valuePropertyName, in);
		final T value = gson.fromJson(in, typeEncoder.decode(typeName));
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

	public interface ITypeResolver<T extends Type> {

		T typeOf(Object o);

		TypeEncoderTypeAdapter.ITypeResolver<Class<?>> forClass = Object::getClass;

	}

	public interface ITypeEncoder<T extends Type> {

		String encode(T type);

		T decode(String typeName);

		TypeEncoderTypeAdapter.ITypeEncoder<Class<?>> forUnsafeClass = new TypeEncoderTypeAdapter.ITypeEncoder<>() {
			@Override
			public String encode(final Class<?> type) {
				return type.getTypeName();
			}

			@Override
			public Class<?> decode(final String typeName) {
				try {
					return Class.forName(typeName);
				} catch ( final ClassNotFoundException ex ) {
					throw new RuntimeException(ex);
				}
			}
		};

		interface IGuard {

			boolean passes(String typeName);

			IGuard weak = name -> true;

		}

	}

}
