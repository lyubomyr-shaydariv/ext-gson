package lsh.ext.gson;

import java.io.IOException;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class JsonAsTypedValue<T, TT extends Type>
			extends AbstractEncodingTypeAdapter<T, T> {

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
			return new JsonAsTypedValue<T, TT>(gson, typePropertyName, valuePropertyName, typeResolver, typeEncoder, guard)
					.nullSafe();
		}

		@Override
		protected T encode(final T value) {
			return value;
		}

		@Override
		protected void writeEncoded(final JsonWriter out, final T value)
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
		protected T readEncoded(final JsonReader in)
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

		@Override
		protected T decode(final T encodedValue) {
			return encodedValue;
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

			ITypeResolver<Class<?>> forClass = Object::getClass;

		}

		public interface ITypeEncoder<T extends Type> {

			String encode(T type);

			T decode(String typeName);

			ITypeEncoder<Class<?>> forUnsafeClass = new ITypeEncoder<>() {
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

}
