package lsh.ext.gson.domain.unix;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import edu.umd.cs.findbugs.annotations.Nullable;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractTypeAdapterFactory;
import lsh.ext.gson.ITypeAdapterFactory;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class UnixTimeTypeAdapter<T>
		extends TypeAdapter<T> {

	public interface IConverter<T> {

		long toSeconds(T value);

		T fromSeconds(long s);

	}

	private final IConverter<T> converter;

	public static <T> TypeAdapter<T> getInstance(final IConverter<T> converter) {
		return new UnixTimeTypeAdapter<T>(converter)
				.nullSafe();
	}

	@Override
	public T read(final JsonReader in)
			throws IOException {
		return converter.fromSeconds(in.nextLong());
	}

	@Override
	public void write(final JsonWriter out, final T value)
			throws IOException {
		out.value(converter.toSeconds(value));
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Factory<T>
			extends AbstractTypeAdapterFactory<T> {

		private final Class<T> klass;
		private final IConverter<T> converter;

		public static <T> ITypeAdapterFactory<T> getInstance(
				final Class<T> klass,
				final IConverter<T> converter
		) {
			return new Factory<>(klass, converter);
		}

		@Override
		@Nullable
		protected TypeAdapter<T> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
			if ( !klass.isAssignableFrom(typeToken.getRawType()) ) {
				return null;
			}
			return UnixTimeTypeAdapter.getInstance(converter);
		}

	}

}
